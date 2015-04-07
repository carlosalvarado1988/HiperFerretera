/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package requerimiento;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.swing.JTable;
import jxl.format.Alignment;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author Administrador
 */
public class Exportar {

    private File file;
    private List<JTable> tabla;
    private List<String> nom_files;

    /**
     *
     * @param file
     * @param tabla
     * @param nom_files
     * @throws Exception
     */
    public void Exporter(File file, List<JTable> tabla, List<String> nom_files) throws Exception {
        this.file = file;
        this.tabla = tabla;
        this.nom_files = nom_files;
        if (nom_files.size() != tabla.size()) {
            throw new Exception("Error");
        }
    }
    public static Alignment JUSTIFY;

    public boolean export(Double total, Double totalnoiva, Double totaliva) {
        try {

            try (DataOutputStream out = new DataOutputStream(new FileOutputStream(file))) {
                WritableWorkbook w = Workbook.createWorkbook(out);
                for (int index = 0; index < tabla.size(); index++) {
                    JTable table = tabla.get(index);
                    WritableSheet s = w.createSheet(nom_files.get(index), 0);
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        if (i == 2) {
                            s.setColumnView(i, 20);
                        }
                        for (int j = -1; j < table.getRowCount(); j++) {
                            if (j == -1) {
                                s.addCell(new Label(i, j + 1, table.getColumnName(i).toString()));
                            } else if (i == 2) {
                                Object object = table.getValueAt(j, i);
                                String ob = String.valueOf(object);
                                s.addCell(new Label(i, j + 1, ob));
                            } else {
                                Object object = table.getValueAt(j, i);
                                Double ob = Double.parseDouble(object.toString());
                                s.addCell(new Number(i, j + 1, ob));
                            }
                            if (j == table.getRowCount() - 1) {
                                if (totalnoiva == null && totaliva == null) {
                                    if (i == table.getColumnCount() - 2) {
                                        s.addCell(new Label(i, j + 3, "Total"));
                                    } else if (i == table.getColumnCount() - 1) {
                                        s.addCell(new Number(i, j + 3, total));
                                    }
                                } else {
                                    if (i == table.getColumnCount() - 2) {
                                        s.addCell(new Label(i, j + 3, "Suma total"));
                                        s.addCell(new Label(i, j + 4, "IVA"));
                                        s.addCell(new Label(i, j + 5, "Total a pagar"));

                                    } else if (i == table.getColumnCount() - 1) {
                                        s.addCell(new Number(i, j + 3, totalnoiva));
                                        s.addCell(new Number(i, j + 4, totaliva));
                                        s.addCell(new Number(i, j + 5, total));
                                    }
                                }
                            }
                        }
                    }
                }
                w.write();
                w.close();
                out.close();
            }
            return true;
        } catch (IOException | WriteException e) {
            return false;
        }
    }
}
