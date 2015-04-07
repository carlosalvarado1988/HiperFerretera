/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package requerimiento;

import java.awt.event.KeyEvent;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.*;

/**
 *
 * @author kike
 */
public final class RequerimientoForm extends javax.swing.JFrame {
    
    DefaultTableModel modelo;
    static int intusuario;
    static int[] comboint;
    static int[] comboreq;
    static int[] prodarray;
    static int idusuario;

    /**
     * Creates new form RequerimientoForm
     */
    public RequerimientoForm() {
        initComponents();
        this.setLocationRelativeTo(null);
        btnactualizar.setEnabled(false);
        btncancelarupdate.setEnabled(false);
        comboset("SELECT id_proveedor, nombre FROM proveedores ORDER BY id_proveedor");
        comboreq("SELECT id_requerimiento, numero FROM requerimiento ORDER BY id_requerimiento");
        Cargar("");
        showclient();
        showreq();
        if (cmbproveedor.getSelectedIndex() == -1){
        JOptionPane.showMessageDialog(null,"Debe ingresar un Proveedor al Catalogo de Proveedores");
        }
            

    }

    public static void SNum(KeyEvent e, String txt) {
        char c = e.getKeyChar();
        String b = Character.toString(c);
        if (Character.isDigit(c) || b.equals(".")) {
            if (b.equals(".") && txt.contains(".")){
                e.consume();
            }
        } else {
            e.consume();
        }
    }

    public boolean Noempty(String prod, String cant, String compneto, String compiva, String ventneto, String ventiva) {
        boolean b = true;
        if (prod.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Necesita ingresar el Nombre del Producto");
            b = false;
        } else if (cant.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Necesita ingresar la Cantidad");
            b = false;
        } else if (compneto.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Necesita ingresar un Precio de Compra Neto");
            b = false;
        } else if (compiva.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Necesita ingresar un Precio de Compra con IVA");
            b = false;
        } else if (ventneto.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Necesita ingresar un Precio de Venta Neto");
            b = false;
        } else if (ventiva.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Necesita ingresar un de Precio de Venta con IVA");
            b = false;
        }
        return b;
    }

    public static void setusuario(int user) {
        intusuario = user;
        if (intusuario == 0) {
            btnadmin.setVisible(true);
        } else {
            btnadmin.setVisible(false);
        }
    }

    public static int[] arrayreq() {
        return comboreq;
    }

    static void showclient() {
        if (cmbreq.getSelectedIndex() == -1) {
            labelcliente.setText("");
        } else {
            int req = comboreq[cmbreq.getSelectedIndex()];
            Conexion cn = new Conexion();
            ResultSet rs = cn.select("select cliente from requerimiento where id_requerimiento = " + req);
            try {
                rs.last();
                labelcliente.setText(rs.getString(1));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }

    static void showreq() {
        if (cmbreq.getSelectedIndex() == -1) {
            labelnumero.setText("");
        } else {
            int req = comboreq[cmbreq.getSelectedIndex()];
            Conexion cn = new Conexion();
            ResultSet rs = cn.select("select numero from requerimiento where id_requerimiento = " + req);
            try {
                rs.last();
                labelnumero.setText(rs.getString(1));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }

    /**
     *
     * @param SQL
     */
    public static void comboset(String SQL) {
        metodos me = new metodos();
        cmbproveedor.setModel(me.combo(SQL, 2));
        comboint = me.comboarray(SQL, 1);
    }

    public static void comboreq(String SQL) {
        metodos me = new metodos();
        cmbreq.setModel(me.combo(SQL, 2));
        comboreq = me.comboarray(SQL, 1);
    }

    void clear() {
        txtproducto.setText("");
        txtpreciocompra.setText("");
        txtcantidad.setText("");
        txtpreciocompra.setText("");
        txtcompraneto.setText("");
        txtcompraiva.setText("");
        txtventaneto.setText("");
        txtventaiva.setText("");

    }

    void Actualizar() {
        Conexion mysql = new Conexion();
        Connection cn = mysql.Conectar();
        String prod;
        int provee;
        double cant, gan, compneto, compiva, ventneto, ventiva;

        prod = txtproducto.getText();
        provee = comboint[cmbproveedor.getSelectedIndex()];
        cant = Double.parseDouble(txtcantidad.getText());
        gan = Double.parseDouble(this.cmbganancia.getSelectedItem().toString());
        compneto = Double.parseDouble(txtcompraneto.getText());
        compiva = Double.parseDouble(txtcompraiva.getText());
        ventneto = Double.parseDouble(txtventaneto.getText());
        ventiva = Double.parseDouble(txtventaiva.getText());

        String sSQL = "UPDATE producto SET id_proveedor = ?, nombre = ?, cantidad = ?,"
                + "compra_neto = ?, compra_iva = ?, venta_neto = ?,"
                + "venta_iva = ?, ganancia = ? WHERE id_producto = " + id_actualizar;

        String mensaje = "Los datos se han Modificado";
        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, provee);
            pst.setString(2, prod);
            pst.setDouble(3, cant);
            pst.setDouble(4, compneto);
            pst.setDouble(5, compiva);
            pst.setDouble(6, ventneto);
            pst.setDouble(7, ventiva);
            pst.setDouble(8, gan);

            int n = pst.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(null, mensaje);
                clear();
                Cargar("");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    void Insertar() {
        Conexion mysql = new Conexion();
        Connection cn = mysql.Conectar();
        String prod;
        int provee;
        int id_req;
        double cant, gan, compneto, compiva, ventneto, ventiva;

        prod = txtproducto.getText();
        provee = comboint[cmbproveedor.getSelectedIndex()];
        cant = Double.parseDouble(txtcantidad.getText());
        gan = Double.parseDouble(this.cmbganancia.getSelectedItem().toString());
        compneto = Double.parseDouble(txtcompraneto.getText());
        compiva = Double.parseDouble(txtcompraiva.getText());
        ventneto = Double.parseDouble(txtventaneto.getText());
        ventiva = Double.parseDouble(txtventaiva.getText());
        id_req = comboreq[cmbreq.getSelectedIndex()];

        String sSQL = "INSERT INTO PRODUCTO (id_proveedor, nombre, cantidad, compra_neto, compra_iva, venta_neto, venta_iva, ganancia, id_requerimiento)"
                + "VALUES(? , ? , ? , ? , ? , ? , ?, ?, ?)";

        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, provee);
            pst.setString(2, prod);
            pst.setDouble(3, cant);
            pst.setDouble(4, compneto);
            pst.setDouble(5, compiva);
            pst.setDouble(6, ventneto);
            pst.setDouble(7, ventiva);
            pst.setDouble(8, gan);
            pst.setInt(9, id_req);

            int n = pst.executeUpdate();
            if (n > 0) {

                clear();
                Cargar("");
            } else {
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    String id_eliminar;

    public void Eliminar(String id) {

        Conexion mysql = new Conexion();
        Connection cn = mysql.Conectar();

        String sSql = "DELETE FROM producto WHERE id_producto =" + id;
        try {
            Statement st = cn.createStatement();
            st.executeUpdate(sSql);

            JOptionPane.showMessageDialog(null, " El Registro ha sido Borrado");
            Cargar("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void Calcular() {
        if (txtpreciocompra.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Necesita ingresar un Precio de Compra");
        } else {
            metodos mt = new metodos();

            double pc = Double.parseDouble(txtpreciocompra.getText());
            double gan = Double.parseDouble(this.cmbganancia.getSelectedItem().toString());
            double pn, pni, pv, pvi;
            if (chkiva.isSelected()) {
                pni = pc;
                pn = pni / 1.13;
                pv = (pn * (gan / 100)) + pn;
                pvi = pv * 1.13;
            } else {
                pn = pc;
                pni = pn * 1.13;
                pv = (pn * (gan / 100)) + pn;
                pvi = pv * 1.13;

            }
            txtcompraneto.setText(Double.toString(mt.Redondear(pn)));
            txtcompraiva.setText(Double.toString(mt.Redondear(pni)));
            txtventaneto.setText(Double.toString(mt.Redondear(pv)));
            txtventaiva.setText(Double.toString(mt.Redondear(pvi)));
        }
    }

    static void Cargar(String valor) {
        metodos mt = new metodos();
        int id_req;
        if (cmbreq.getSelectedIndex() == -1) {
            id_req = 0;
        } else {
            id_req = comboreq[cmbreq.getSelectedIndex()];
        }
        String SQL = "SELECT @i := @i + 1 as '#', proveedores.nombre AS Proveedor, producto.nombre AS Descripción, cantidad AS Cantidad, compra_neto AS 'Compra Neto', compra_iva AS 'Compra Iva', venta_neto AS 'Venta Neto', venta_iva AS 'Venta Iva', ganancia AS Ganancia FROM producto, proveedores, (select @i := 0) as id WHERE producto.id_proveedor = proveedores.id_proveedor AND producto.nombre like '%" + valor + "%'AND id_requerimiento = " + id_req;
        prodarray = mt.comboarray("SELECT @i := @i + 1 as '#', id_producto AS ID, proveedores.nombre AS Proveedor, producto.nombre AS Descripción, cantidad AS Cantidad, compra_neto AS 'Compra Neto', compra_iva AS 'Compra Iva', venta_neto AS 'Venta Neto', venta_iva AS 'Venta Iva', ganancia AS Ganancia FROM producto, proveedores, (select @i := 0) as id WHERE producto.id_proveedor = proveedores.id_proveedor AND producto.nombre like '%" + valor + "%'AND id_requerimiento = " + id_req, 2);
        tbconsulta.setModel(mt.Consulta(SQL));
        tbconsulta.getTableHeader().setReorderingAllowed(false);
    }
    String id_actualizar = "";

    void ModificarRegistro(int id) {
        String nombre = "", cantidad = "", compra_neto = "", compra_iva = "", venta_neto = "", venta_iva = "";
        Conexion mysql = new Conexion();
        Connection cn = mysql.Conectar();
        String sSQL = "SELECT nombre, cantidad, compra_neto, compra_iva, venta_neto, venta_iva FROM producto WHERE id_producto = " + id;
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while (rs.next()) {
                nombre = rs.getString("nombre");
                cantidad = rs.getString("cantidad");
                compra_neto = rs.getString("compra_neto");
                compra_iva = rs.getString("compra_iva");
                venta_neto = rs.getString("venta_neto");
                venta_iva = rs.getString("venta_iva");
            }
            txtproducto.setText(nombre);
            txtcantidad.setText(cantidad);
            txtcompraneto.setText(compra_neto);
            txtcompraiva.setText(compra_iva);
            txtventaneto.setText(venta_neto);
            txtventaiva.setText(venta_iva);
            id_actualizar = String.valueOf(id);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        mneditar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtproducto = new javax.swing.JTextField();
        cmbproveedor = new javax.swing.JComboBox();
        txtpreciocompra = new javax.swing.JTextField();
        txtcantidad = new javax.swing.JTextField();
        chkiva = new javax.swing.JCheckBox();
        btncalcular = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtcompraneto = new javax.swing.JTextField();
        txtcompraiva = new javax.swing.JTextField();
        txtventaneto = new javax.swing.JTextField();
        txtventaiva = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        btnagregar = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        btneliminarreg = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        cmbganancia = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbconsulta = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                if (colIndex==0){
                    return false;
                }
                return true;
            }
        };
        txtbuscar = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        btnactualizar = new javax.swing.JButton();
        btncancelarupdate = new javax.swing.JButton();
        cmbreq = new javax.swing.JComboBox();
        btncatalgprovee = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        labelcliente = new javax.swing.JLabel();
        labelnumero = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnadmin = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        bnInsertarNuevo = new javax.swing.JMenuItem();
        mnabrir = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        mneditar.setText("Modificar");
        mneditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mneditarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(mneditar);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SEPv1.0");

        jPanel1.setBackground(new java.awt.Color(199, 193, 193));
        jPanel1.setPreferredSize(new java.awt.Dimension(596, 685));

        jLabel1.setFont(new java.awt.Font("Swis721 Blk BT", 0, 12)); // NOI18N
        jLabel1.setText("REQUERIMIENTO N°");

        jLabel2.setText("PRODUCTO:");

        jLabel3.setText("PROVEEDOR:");

        jLabel4.setText("CANTIDAD:");

        jLabel5.setText("PRECIO DE COMPRA: $");

        jLabel6.setText("GANANCIA:");

        txtproducto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtproducto.setName("txtproducto"); // NOI18N
        txtproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtproductoActionPerformed(evt);
            }
        });

        cmbproveedor.setName("txtproveedor"); // NOI18N
        cmbproveedor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbproveedorItemStateChanged(evt);
            }
        });
        cmbproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbproveedorActionPerformed(evt);
            }
        });

        txtpreciocompra.setName("txtpreciocompra"); // NOI18N
        txtpreciocompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpreciocompraActionPerformed(evt);
            }
        });
        txtpreciocompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtpreciocompraKeyTyped(evt);
            }
        });

        txtcantidad.setName("txtcantidad"); // NOI18N
        txtcantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcantidadActionPerformed(evt);
            }
        });
        txtcantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcantidadKeyTyped(evt);
            }
        });

        chkiva.setBackground(new java.awt.Color(199, 193, 193));
        chkiva.setText("IVA INCLUIDO");
        chkiva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkivaActionPerformed(evt);
            }
        });

        btncalcular.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btncalcular.setText("<html> CALCULAR<br> PRECIOS </html>");
        btncalcular.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btncalcularMousePressed(evt);
            }
        });
        btncalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncalcularActionPerformed(evt);
            }
        });

        jLabel7.setText("RESULTADO DE CALCULOS");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("PRECIO DE COMPRA:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("PRECIOS DE VENTA:");

        jLabel10.setText("NETO: $");

        jLabel11.setText("NETO+IVA: $");

        jLabel12.setText("NETO: $ ");

        jLabel13.setText("NETO+IVA: $");

        txtcompraneto.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtcompraneto.setName("txtnetocompra"); // NOI18N
        txtcompraneto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcompranetoActionPerformed(evt);
            }
        });
        txtcompraneto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcompranetoKeyTyped(evt);
            }
        });

        txtcompraiva.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtcompraiva.setName("txtivacompra"); // NOI18N
        txtcompraiva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcompraivaKeyTyped(evt);
            }
        });

        txtventaneto.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtventaneto.setName("txtnetoventa"); // NOI18N
        txtventaneto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtventanetoActionPerformed(evt);
            }
        });
        txtventaneto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtventanetoKeyTyped(evt);
            }
        });

        txtventaiva.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtventaiva.setName("txtivaventa"); // NOI18N
        txtventaiva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtventaivaKeyTyped(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Swis721 Blk BT", 0, 12)); // NOI18N
        jLabel14.setText("INGRESO DE PRODUCTOS");

        btnagregar.setText("AGREGAR A LISTA");
        btnagregar.setName("cmdagregarlista"); // NOI18N
        btnagregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregarActionPerformed(evt);
            }
        });

        btneliminarreg.setText("Eliminar un registro");
        btneliminarreg.setName("cmdeliminar"); // NOI18N
        btneliminarreg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarregActionPerformed(evt);
            }
        });

        jLabel15.setText("PRESENTACION A CLIENTE:");

        jButton5.setText("Consumidor Final");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Consumidor Credito Fiscal");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel16.setText("INFORMACION A USUARIO:");

        jButton7.setText("Orden a Proveedor");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel17.setText("%");

        cmbganancia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60", "65", "70", "75", "80", "85", "90", "95", "100", "105", "110", "115", "120", "125", "130", "135", "140", "145", "150", "155", "160", "165", "170", "175", "180", "185", "190", "195", "200" }));
        cmbganancia.setName("cmbganancia"); // NOI18N
        cmbganancia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbgananciaActionPerformed(evt);
            }
        });
        cmbganancia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbgananciaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cmbgananciaKeyTyped(evt);
            }
        });

        tbconsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbconsulta.setComponentPopupMenu(jPopupMenu1);
        jScrollPane1.setViewportView(tbconsulta);

        txtbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbuscarActionPerformed(evt);
            }
        });
        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtbuscarKeyTyped(evt);
            }
        });

        jLabel18.setText("Buscar Producto: ");

        btnactualizar.setText("Actualizar");
        btnactualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnactualizarActionPerformed(evt);
            }
        });

        btncancelarupdate.setText("Cancelar");
        btncancelarupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelarupdateActionPerformed(evt);
            }
        });

        cmbreq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbreqActionPerformed(evt);
            }
        });

        btncatalgprovee.setText("Catálogo de Proveedores");
        btncatalgprovee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncatalgproveeActionPerformed(evt);
            }
        });

        jLabel19.setText("CLIENTE: ");

        jLabel20.setText("REQUERIMIENTO: ");

        labelcliente.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        labelcliente.setText("--------------------------");

        labelnumero.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        labelnumero.setText("---------------------------");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setToolTipText("");

        btnadmin.setText("ADMINISTRADOR");
        btnadmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnadminActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 988, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btneliminarreg, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGap(38, 38, 38)
                                                        .addComponent(jLabel16))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(192, 192, 192)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(39, 39, 39)
                                        .addComponent(jLabel19)
                                        .addGap(18, 18, 18)
                                        .addComponent(labelcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel20)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(labelnumero, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(102, 102, 102)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btncatalgprovee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnadmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(88, 88, 88)))))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(138, 138, 138)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cmbreq, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(15, 15, 15)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtpreciocompra, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(chkiva)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btncalcular, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbganancia, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel17)))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel11)
                                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtcompraneto)
                                                .addComponent(txtcompraiva, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel7)
                                            .addGap(1, 1, 1)))
                                    .addGap(30, 30, 30))
                                .addComponent(btnagregar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel8))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(jLabel9)
                                .addContainerGap())
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btncancelarupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(94, 94, 94))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtventaneto, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(159, 159, 159))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtventaiva, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(159, 159, 159))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbreq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cmbproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtpreciocompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkiva))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmbganancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel17))
                            .addComponent(jLabel6))
                        .addGap(32, 32, 32))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtcompraneto, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel10))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel12)
                                        .addComponent(txtventaneto, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel11)
                                        .addComponent(txtcompraiva, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtventaiva, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel13)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnagregar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnactualizar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btncancelarupdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btncalcular, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btneliminarreg)
                                .addGap(15, 15, 15)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel15)))
                            .addComponent(btncatalgprovee, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelcliente)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5)
                            .addComponent(jButton7)
                            .addComponent(jLabel20)
                            .addComponent(labelnumero)
                            .addComponent(btnadmin, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(100, 100, 100))
        );

        jMenu1.setText("Archivo");

        bnInsertarNuevo.setText("Inciar un Nuevo Requerimiento");
        bnInsertarNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnInsertarNuevoActionPerformed(evt);
            }
        });
        jMenu1.add(bnInsertarNuevo);

        mnabrir.setText("Modificar o Eliminar un Requerimiento");
        mnabrir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                mnabrirMousePressed(evt);
            }
        });
        mnabrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnabrirActionPerformed(evt);
            }
        });
        jMenu1.add(mnabrir);

        jMenuItem2.setText("Salir");
        jMenuItem2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem2MousePressed(evt);
            }
        });
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1007, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    String action = "insertar";
    private void mneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mneditarActionPerformed
        int filasel;
        int id;
        try {
            filasel = tbconsulta.getSelectedRow();
            if (filasel == -1) {
                JOptionPane.showMessageDialog(null, "No se ha seleccionado ningun Producto");
            } else {

                action = "Modificar";
                modelo = (DefaultTableModel) tbconsulta.getModel();
                id = prodarray[tbconsulta.getSelectedRow()];
                ModificarRegistro(id);
                btnagregar.setEnabled(false);
                btnactualizar.setEnabled(true);
                btncancelarupdate.setEnabled(true);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }//GEN-LAST:event_mneditarActionPerformed

    private void mnabrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnabrirActionPerformed
    }//GEN-LAST:event_mnabrirActionPerformed

    private void mnabrirMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnabrirMousePressed
        new EliminarRequerimiento(this, true).setVisible(true);
    }//GEN-LAST:event_mnabrirMousePressed

    private void jMenuItem2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem2MousePressed
        this.dispose();
    }//GEN-LAST:event_jMenuItem2MousePressed

    private void bnInsertarNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnInsertarNuevoActionPerformed
        new NuevoRequerimiento(this, true).setVisible(true);
        NuevoRequerimiento.setusuario(idusuario);
    }//GEN-LAST:event_bnInsertarNuevoActionPerformed

    private void btncatalgproveeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncatalgproveeActionPerformed
        new Proveedores(this, true).setVisible(true);

    }//GEN-LAST:event_btncatalgproveeActionPerformed

    private void cmbreqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbreqActionPerformed
        Cargar("");
        showclient();
        showreq();
    }//GEN-LAST:event_cmbreqActionPerformed

    private void btncancelarupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarupdateActionPerformed
        btnagregar.setEnabled(true);
        btnactualizar.setEnabled(false);
        btncancelarupdate.setEnabled(false);
        clear();
    }//GEN-LAST:event_btncancelarupdateActionPerformed

    private void btnactualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizarActionPerformed
        if (cmbreq.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Debe crear un nuevo requerimiento");
        } else {
            boolean empty = Noempty(txtproducto.getText(), txtcantidad.getText(), txtcompraneto.getText(), txtcompraiva.getText(), txtventaneto.getText(), txtventaiva.getText());
            if (empty) {

                if (cmbproveedor.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un Proveedor a su Catalogo");
                } else {
                    Actualizar();
                    btnagregar.setEnabled(true);
                    btnactualizar.setEnabled(false);
                    btncancelarupdate.setEnabled(false);
                }
            }
        }
    }//GEN-LAST:event_btnactualizarActionPerformed
    String valor = "";
    private void txtbuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyTyped
        valor = txtbuscar.getText();
        Cargar(valor);
    }//GEN-LAST:event_txtbuscarKeyTyped

    private void txtbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbuscarActionPerformed
    }//GEN-LAST:event_txtbuscarActionPerformed

    private void cmbgananciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbgananciaActionPerformed
    }//GEN-LAST:event_cmbgananciaActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if (cmbreq.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Debe crear un nuevo requerimiento");
        } else {
            new PresentacionProveedor(this, true).setVisible(true);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if (cmbreq.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Debe crear un nuevo requerimiento");
        } else {
            new ConsumidorCreditoFiscal(this, true).setVisible(true);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (cmbreq.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Debe crear un nuevo requerimiento");
        } else {
            new ConsumidorFinal(this, true).setVisible(true);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btneliminarregActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarregActionPerformed
        int filasel;
        filasel = tbconsulta.getSelectedRow();
        if (filasel == -1) {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningun registro");
        } else {
            int p = JOptionPane.showConfirmDialog(null, "¿Desea Eliminar el registro?", "Borrar Registro", JOptionPane.YES_NO_OPTION);
            if (p == 0) {
                int id = prodarray[tbconsulta.getSelectedRow()];
                Eliminar(String.valueOf(id));
                Cargar("");
            }
        }
    }//GEN-LAST:event_btneliminarregActionPerformed

    private void btnagregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregarActionPerformed
        if (cmbreq.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Debe crear un nuevo requerimiento");
        } else {
            boolean empty = Noempty(txtproducto.getText(), txtcantidad.getText(), txtcompraneto.getText(), txtcompraiva.getText(), txtventaneto.getText(), txtventaiva.getText());
            if (empty) {

                if (cmbproveedor.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un Proveedor a su Catalogo");
                } else {
                    Insertar();
                }
            } else {
            }
        }
    }//GEN-LAST:event_btnagregarActionPerformed

    private void btncalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncalcularActionPerformed
        Calcular();
    }//GEN-LAST:event_btncalcularActionPerformed

    private void btncalcularMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btncalcularMousePressed
    }//GEN-LAST:event_btncalcularMousePressed

    private void chkivaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkivaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkivaActionPerformed

    private void txtcantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcantidadActionPerformed
        txtcantidad.transferFocus();
    }//GEN-LAST:event_txtcantidadActionPerformed

    private void txtpreciocompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpreciocompraActionPerformed
        txtpreciocompra.transferFocus();
    }//GEN-LAST:event_txtpreciocompraActionPerformed

    private void cmbproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbproveedorActionPerformed
    }//GEN-LAST:event_cmbproveedorActionPerformed

    private void cmbproveedorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbproveedorItemStateChanged
        cmbproveedor.transferFocus();
    }//GEN-LAST:event_cmbproveedorItemStateChanged

    private void txtproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtproductoActionPerformed
        txtproducto.transferFocus();
    }//GEN-LAST:event_txtproductoActionPerformed

    private void btnadminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnadminActionPerformed
        new Administrador(this, true).setVisible(true);
    }//GEN-LAST:event_btnadminActionPerformed

    private void txtcompranetoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcompranetoKeyTyped
        SNum(evt, txtcompraneto.getText());
    }//GEN-LAST:event_txtcompranetoKeyTyped

    private void txtcompranetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcompranetoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcompranetoActionPerformed

    private void cmbgananciaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbgananciaKeyTyped
    }//GEN-LAST:event_cmbgananciaKeyTyped

    private void txtcantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidadKeyTyped
        SNum(evt, txtcantidad.getText());
    }//GEN-LAST:event_txtcantidadKeyTyped

    private void txtpreciocompraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpreciocompraKeyTyped
        SNum(evt, txtpreciocompra.getText());
    }//GEN-LAST:event_txtpreciocompraKeyTyped

    private void txtventanetoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtventanetoKeyTyped
        SNum(evt, txtventaneto.getText());
    }//GEN-LAST:event_txtventanetoKeyTyped

    private void txtventaivaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtventaivaKeyTyped
        SNum(evt, txtventaiva.getText());
    }//GEN-LAST:event_txtventaivaKeyTyped

    private void txtcompraivaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcompraivaKeyTyped
        SNum(evt, txtcompraiva.getText());
    }//GEN-LAST:event_txtcompraivaKeyTyped

    private void cmbgananciaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbgananciaKeyPressed
    }//GEN-LAST:event_cmbgananciaKeyPressed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void txtventanetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtventanetoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtventanetoActionPerformed
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
            java.util.logging.Logger.getLogger(RequerimientoForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RequerimientoForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RequerimientoForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RequerimientoForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RequerimientoForm().setVisible(true);
            }
        });
    }
    /**
     *
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem bnInsertarNuevo;
    private javax.swing.JButton btnactualizar;
    private static javax.swing.JButton btnadmin;
    private javax.swing.JButton btnagregar;
    private javax.swing.JButton btncalcular;
    private javax.swing.JButton btncancelarupdate;
    private javax.swing.JButton btncatalgprovee;
    private javax.swing.JButton btneliminarreg;
    private javax.swing.JCheckBox chkiva;
    private javax.swing.JComboBox cmbganancia;
    public static javax.swing.JComboBox cmbproveedor;
    public static javax.swing.JComboBox cmbreq;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private static javax.swing.JLabel labelcliente;
    private static javax.swing.JLabel labelnumero;
    private javax.swing.JMenu mnabrir;
    private javax.swing.JMenuItem mneditar;
    public static javax.swing.JTable tbconsulta;
    private javax.swing.JTextField txtbuscar;
    private javax.swing.JTextField txtcantidad;
    private javax.swing.JTextField txtcompraiva;
    private javax.swing.JTextField txtcompraneto;
    private javax.swing.JTextField txtpreciocompra;
    private javax.swing.JTextField txtproducto;
    private javax.swing.JTextField txtventaiva;
    private javax.swing.JTextField txtventaneto;
    // End of variables declaration//GEN-END:variables
}
