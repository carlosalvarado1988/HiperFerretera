/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package requerimiento;

import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Daniel
 */
public class metodos {

    public DefaultTableModel Consulta(String SQL) {
        DefaultTableModel mod = new DefaultTableModel();
        Conexion con = new Conexion();
        ResultSet rs = con.select(SQL);
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int cc = 1; cc < rsmd.getColumnCount() + 1; cc++) {
                mod.addColumn(rsmd.getColumnLabel(cc));
            }
            while (rs.next()) {
                Object[] fila = new Object[rsmd.getColumnCount()];
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                mod.addRow(fila);
            }
        } catch (Exception e) {
        }
        return mod;
    }

    public int[] comboarray(String SQL, int col) {
        Conexion con = new Conexion();
        ResultSet rs = con.select(SQL);
        int rows;
        rows = 0;
        try {
            while (rs.next()) {
                rows = rs.getRow();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        int[] ar = new int[rows];
        try {
            rs.beforeFirst();
            while (rs.next()) {
                ar[rs.getRow() - 1] = rs.getInt(col);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return ar;
    }

    public DefaultComboBoxModel combo(String SQL, int col) {
        DefaultComboBoxModel mod = new DefaultComboBoxModel();
        Conexion con = new Conexion();
        ResultSet rs = con.select(SQL);
        try {
            while (rs.next()) {
                mod.addElement(rs.getString(col));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return mod;
    }

    public double Redondear(double numero) {
        return Math.rint(numero * 100) / 100;
    }

    public void Insertar(String SQL) {
        Conexion mysql = new Conexion();
        Connection cn = mysql.Conectar();
        try {
            Statement sttm = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            sttm.executeUpdate(SQL);
            cn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);

        }

    }
    
    void consumesp(KeyEvent evt) {
        char c = evt.getKeyChar();
        if (Character.isWhitespace(c)) {
            evt.consume();
        }
    }
    
    public void exportar(JTable tabla, String reqid, String total, String totalnoiva, String totaliva){
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar Archivo");
        chooser.setMultiSelectionEnabled(false);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            List<JTable> tb = new ArrayList<>();
            List<String> nom = new ArrayList<>();
            tb.add(tabla);
            nom.add("Requerimiento " +RequerimientoForm.cmbreq.getSelectedItem().toString());
            String file = chooser.getSelectedFile().toString().concat(".xls");

            try {
                requerimiento.Exportar e = new Exportar();
                e.Exporter(new File(file), tb, nom);
                if (totalnoiva == null && totaliva == null){
                if (e.export(Double.parseDouble(total), null, null)) {
                    JOptionPane.showMessageDialog(null, "Los datos fueron exportados a Excel.", "Hiperferretera", JOptionPane.INFORMATION_MESSAGE);
                }
                } else{
                    if (e.export(Double.parseDouble(total), Double.parseDouble(totalnoiva), Double.parseDouble(totaliva))) {
                    JOptionPane.showMessageDialog(null, "Los datos fueron exportados a Excel.", "Hiperferretera", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex, "Hiperferretera", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "Hubo un error", "Hiperferretera", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
