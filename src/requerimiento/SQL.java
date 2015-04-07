/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package requerimiento;


/**
 *
 * @author Daniel
 */
public class SQL {

    public String select(String[] columnas, String[] tablas, String[] condicion, String orderby, boolean contar) {
        String SQLfinal = "SELECT ";
        if (contar) {
            SQLfinal = SQLfinal + "@i := @i + 1 as '#' ";
        }
        if (columnas == null) {
            SQLfinal = SQLfinal + " * ";
        } else {
            for (int c = 0; c < columnas.length; c++) {
                if (c == 0) {
                    SQLfinal = SQLfinal + " " + columnas[c];
                } else {
                    SQLfinal = SQLfinal + ", " + columnas[c];
                }
            }
        }
        SQLfinal = SQLfinal + " FROM";
        if (contar) {
            SQLfinal = SQLfinal + " (select @i := 0) as id,";
        }
        for (int c = 0; c < tablas.length; c++) {
            if (c == 0) {
                SQLfinal = SQLfinal + " " + tablas[c];
            } else {
                SQLfinal = SQLfinal + ", " + tablas[c];
            }
        }
        if (condicion != null) {
            SQLfinal = SQLfinal + " WHERE";
            for (int c = 0; c < condicion.length; c++) {
                if (c == condicion.length - 1) {
                    SQLfinal = SQLfinal + " " + condicion[c];
                } else {
                    SQLfinal = SQLfinal + " " + condicion[c] + " AND ";
                }
            }
        }
        if (orderby != null) {
            SQLfinal = SQLfinal + " ORDER BY " + orderby;
        }
        return SQLfinal;
    }

    public String insert(String tabla, String[] columnas, String[] valores) {
        String SQLfinal = "INSERT INTO " + tabla;
        if (columnas != null) {
            for (int c = 0; c < columnas.length; c++) {
                if (c == 0) {
                    SQLfinal = SQLfinal + " (" + columnas[c];
                } else if (c == columnas.length - 1) {
                    SQLfinal = SQLfinal + ", " + columnas[c] + ")";
                } else {
                    SQLfinal = SQLfinal + ", " + columnas[c];
                }
            }
        }
        SQLfinal = SQLfinal + " VALUES";
        for (int c = 0; c < valores.length; c++) {
            if (c == 0) {
                SQLfinal = SQLfinal + " (" + valores[c];
            } else if (c == valores.length - 1) {
                SQLfinal = SQLfinal + ", " + valores[c] + ")";
            } else {
                SQLfinal = SQLfinal + ", " + valores[c];
            }
        }
        return SQLfinal;
    }

    public String delete(String tabla, String[] condiciones) {
        String SQLfinal = "DELETE FROM " + tabla;
        if (condiciones != null) {
            SQLfinal = SQLfinal + " WHERE";
            for (int c = 0; c < condiciones.length; c++) {
                if (c == condiciones.length - 1) {
                    SQLfinal = SQLfinal + " " + condiciones[c];
                } else {
                    SQLfinal = SQLfinal + " " + condiciones[c] + " AND ";
                }
            }
        }
        return SQLfinal;
    }

    public String update(String tabla, String[] columna, String[] tipo, String[] valor, String[] condiciones) {
        String SQLfinal = "UPDATE " + tabla + " SET";
        for (int c = 0; c < columna.length; c++) {
            if (!tipo[c].equals("VARCHAR")){
            if (c == 0) {
                SQLfinal = SQLfinal + " " + columna[c] + "=" + valor[c];
            } else {
                SQLfinal = SQLfinal + ", " + columna[c] + "=" + valor[c];
            }
            } else
                if (c == 0) {
                SQLfinal = SQLfinal + " " + columna[c] + "= '" + valor[c]+"'";
            } else {
                SQLfinal = SQLfinal + ", " + columna[c] + "= '" + valor[c]+"'";
            }
        }
        if (condiciones != null){
            SQLfinal= SQLfinal + " WHERE";
            for (int c=0; c<condiciones.length; c++){
                if (c == condiciones.length - 1) {
                    SQLfinal = SQLfinal + " " + condiciones[c];
                } else {
                    SQLfinal = SQLfinal + " " + condiciones[c] + " AND ";
                }
            }
        }
        return SQLfinal;
    }
}
