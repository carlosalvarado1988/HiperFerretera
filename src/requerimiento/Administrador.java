/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package requerimiento;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.JOptionPane;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author UFG
 */
public class Administrador extends javax.swing.JDialog {

    Conexion cn = new Conexion();
    SQL cSQL = new SQL();
    metodos mt = new metodos();

    /**
     * Creates new form Administrador
     */
    public Administrador(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        filltable(cmbtablas.getSelectedItem().toString());
        MostrarInsertUsu();
        MostrarEliminar();
            
    }

    

    final void MostrarEliminar() {
        if (cmbtablas.getSelectedIndex() == 0) {
            btneliminartodo.setVisible(false);
        } else {
            btneliminartodo.setVisible(true);
        }
    }

    final void MostrarInsertUsu() {
        if (cmbtablas.getSelectedIndex() == 0) {
            jLabel3.setVisible(true);
            chkprivilegios.setVisible(true);
            jLabel4.setVisible(true);
            txtusuario.setVisible(true);
            btnagregar.setVisible(true);
            jLabel5.setVisible(true);
            txtpassword.setVisible(true);
        } else {
            jLabel3.setVisible(false);
            chkprivilegios.setVisible(false);
            jLabel4.setVisible(false);
            txtusuario.setVisible(false);
            btnagregar.setVisible(false);
            jLabel5.setVisible(false);
            txtpassword.setVisible(false);
        }
    }

    final void filltable(String SQL) {
        String[] aSQL = {SQL};
        tbtablas.setModel(mt.Consulta(cSQL.select(null, aSQL, null, null, false)));
        tbtablas.getTableHeader().setReorderingAllowed(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbtablas = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                if (colIndex==0){
                    return false;
                }
                return true;
            }
        };
        jLabel1 = new javax.swing.JLabel();
        cmbtablas = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        btneliminar = new javax.swing.JButton();
        btnmodificar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtusuario = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnagregar = new javax.swing.JButton();
        txtpassword = new javax.swing.JTextField();
        chkprivilegios = new javax.swing.JCheckBox();
        btnsalir = new javax.swing.JButton();
        btneliminartodo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ADMINISTRADOR");

        tbtablas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tbtablas);

        jLabel1.setText("ADMINISTRADOR");

        cmbtablas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "usuarios", "proveedores", "producto", "requerimiento" }));
        cmbtablas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbtablasActionPerformed(evt);
            }
        });

        jLabel2.setText("Ver Tabla: ");

        btneliminar.setText("Eliminar");
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });

        btnmodificar.setText("Modificar");
        btnmodificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmodificarActionPerformed(evt);
            }
        });

        jLabel3.setText("Agregar Nuevo Usuario");

        txtusuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtusuarioActionPerformed(evt);
            }
        });
        txtusuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtusuarioKeyTyped(evt);
            }
        });

        jLabel4.setText("Usuario:");

        jLabel5.setText("Contraseña:");

        btnagregar.setText("Agregar");
        btnagregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregarActionPerformed(evt);
            }
        });

        txtpassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtpasswordKeyTyped(evt);
            }
        });

        chkprivilegios.setText("Privilegios de Administrador");

        btnsalir.setText("Salir");
        btnsalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalirActionPerformed(evt);
            }
        });

        btneliminartodo.setText("Eliminar todo");
        btneliminartodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminartodoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbtablas, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btneliminartodo)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btneliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnsalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnmodificar)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtpassword))
                        .addGap(18, 18, 18)
                        .addComponent(btnagregar)
                        .addGap(36, 36, 36))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chkprivilegios)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbtablas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(btneliminartodo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btneliminar)
                    .addComponent(btnmodificar)
                    .addComponent(jLabel3)
                    .addComponent(chkprivilegios))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnagregar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtpassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnsalir)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbtablasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbtablasActionPerformed
        filltable(cmbtablas.getSelectedItem().toString());
        MostrarInsertUsu();
        MostrarEliminar();
    }//GEN-LAST:event_cmbtablasActionPerformed

    private void btnagregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregarActionPerformed
        try {
            if (txtusuario.getText().isEmpty() || txtpassword.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un nombre de usuario y contraseña");
            } else {
                ResultSet rs = cn.select("Select usuario from usuarios where usuario = '" + txtusuario.getText() + "'");
                rs.last();
                if (rs.getRow() > 0) {
                    JOptionPane.showMessageDialog(null, "Ya existe un usuario con ese nombre");
                } else {
                    String p = "1";
                    if (chkprivilegios.isSelected()) {
                        p = "0";
                    }
                    String tab = "usuarios";
                    String[] col = {"usuario", "password", "privilegio"};
                    String[] val = {"'" + txtusuario.getText() + "'", "'" + txtpassword.getText() + "'", p};
                    mt.Insertar(cSQL.insert(tab, col, val));
                    filltable(cmbtablas.getSelectedItem().toString());
                    txtusuario.setText("");
                    txtpassword.setText("");
                }
            }
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_btnagregarActionPerformed

    private void btnsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnsalirActionPerformed

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        int filasel;
        filasel = tbtablas.getSelectedRow();
        if (filasel == -1) {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningun registro");
        } else {
            int p = JOptionPane.showConfirmDialog(null, "¿Desea Eliminar el Registro Seleccionado?", "Borrar Registro", JOptionPane.YES_NO_OPTION);
            if (p == 0) {
                if (tbtablas.getValueAt(tbtablas.getSelectedRow(), 0) == 1 && cmbtablas.getSelectedItem().equals("usuarios")) {
                    JOptionPane.showMessageDialog(null, "No puede eliminar al Administrador");
                } else {
                    String cond[] = {tbtablas.getColumnName(0) + "=" + tbtablas.getValueAt(tbtablas.getSelectedRow(), 0)};
                    mt.Insertar(cSQL.delete(cmbtablas.getSelectedItem().toString(), cond));
                    filltable(cmbtablas.getSelectedItem().toString());
                    RequerimientoForm.comboset("SELECT id_proveedor, nombre FROM proveedores ORDER BY id_proveedor");
                    RequerimientoForm.comboreq("SELECT id_requerimiento, numero FROM requerimiento ORDER BY id_requerimiento");
                    RequerimientoForm.Cargar("");
                    RequerimientoForm.showclient();
                    RequerimientoForm.showreq();
                }
            }
        }
    }//GEN-LAST:event_btneliminarActionPerformed

    private void btneliminartodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminartodoActionPerformed

        int p = JOptionPane.showConfirmDialog(null, "¿Desea Eliminar todos los registros de la tabla " + cmbtablas.getSelectedItem() + "?", "Borrar Registro", JOptionPane.YES_NO_OPTION);
        if (p == 0) {

            mt.Insertar(cSQL.delete(cmbtablas.getSelectedItem().toString(), null));
            filltable(cmbtablas.getSelectedItem().toString());
            RequerimientoForm.comboset("SELECT id_proveedor, nombre FROM proveedores ORDER BY id_proveedor");
            RequerimientoForm.comboreq("SELECT id_requerimiento, numero FROM requerimiento ORDER BY id_requerimiento");
            RequerimientoForm.Cargar("");
            RequerimientoForm.showclient();
            RequerimientoForm.showreq();
        }

    }//GEN-LAST:event_btneliminartodoActionPerformed

    private void btnmodificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmodificarActionPerformed
        int filasel;
        filasel = tbtablas.getSelectedRow();
        if (filasel == -1) {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningun registro");
        } else {
            if (tbtablas.isEditing()) {
                TableCellEditor tce = tbtablas.getCellEditor();
                tce.stopCellEditing();
            }
            try {
                Conexion con = new Conexion();

                String[] tipos = new String[tbtablas.getColumnCount()];
                String[] columnas = new String[tbtablas.getColumnCount()];
                String[] valores = new String[tbtablas.getColumnCount()];
                String[] condicion = {tbtablas.getColumnName(0) + "=" + tbtablas.getValueAt(tbtablas.getSelectedRow(), 0).toString()};
                String[] aSQL = {cmbtablas.getSelectedItem().toString()};
                ResultSet rs = con.select(cSQL.select(null, aSQL, null, null, false));
                ResultSetMetaData rsmd = rs.getMetaData();
                for (int c = 0; c < tbtablas.getColumnCount(); c++) {
                    tipos[c] = rsmd.getColumnTypeName(c + 1);
                    columnas[c] = tbtablas.getColumnName(c);
                    valores[c] = tbtablas.getValueAt(tbtablas.getSelectedRow(), c).toString();
                }

                String sql = cSQL.update(cmbtablas.getSelectedItem().toString(), columnas, tipos, valores, condicion);
                mt.Insertar(sql);
                filltable(cmbtablas.getSelectedItem().toString());

                RequerimientoForm.showclient();
                RequerimientoForm.showreq();
                RequerimientoForm.comboset("SELECT id_proveedor, nombre FROM proveedores ORDER BY id_proveedor");
                RequerimientoForm.comboreq("SELECT id_requerimiento, numero FROM requerimiento ORDER BY id_requerimiento");
                RequerimientoForm.Cargar("");
            } catch (Exception e) {
            }
        }

    }//GEN-LAST:event_btnmodificarActionPerformed

    private void txtusuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtusuarioActionPerformed
    }//GEN-LAST:event_txtusuarioActionPerformed

    private void txtusuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtusuarioKeyTyped
        mt.consumesp(evt);
    }//GEN-LAST:event_txtusuarioKeyTyped

    private void txtpasswordKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpasswordKeyTyped
        mt.consumesp(evt);
    }//GEN-LAST:event_txtpasswordKeyTyped
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;




                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Administrador.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Administrador.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Administrador.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Administrador.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Administrador dialog = new Administrador(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnagregar;
    private javax.swing.JButton btneliminar;
    private javax.swing.JButton btneliminartodo;
    private javax.swing.JButton btnmodificar;
    private javax.swing.JButton btnsalir;
    private javax.swing.JCheckBox chkprivilegios;
    private javax.swing.JComboBox cmbtablas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbtablas;
    private javax.swing.JTextField txtpassword;
    private javax.swing.JTextField txtusuario;
    // End of variables declaration//GEN-END:variables
}