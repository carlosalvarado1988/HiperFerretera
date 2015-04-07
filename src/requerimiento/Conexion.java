/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package requerimiento;

import java.sql.*;
import javax.swing.JOptionPane;

public class Conexion {

    public String db = "requerimiento";
    public String url = "jdbc:mysql://localhost:3306/" + db;
    String user = "root";
    String pass = "";

    public Conexion() {
    }

    public Connection Conectar() {
        Connection link = null;
        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            link = DriverManager.getConnection(this.url, this.user, this.pass);
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return link;
    }

    public ResultSet select(String sqlcad) {
        ResultSet rs = null;
        try {
            Connection cnx = Conectar();
            Statement sttm = cnx.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = sttm.executeQuery(sqlcad);
           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        return rs;
    }
}
