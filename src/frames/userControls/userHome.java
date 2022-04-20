/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames.userControls;

import frames.generalControls.frameOrders;
import frames.adminControls.*;
import frames.generalControls.Login;
import Classes.Configurations;
import Classes.Orders;
import Dialogs.newCliente;
import Dialogs.newDish;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author OSBAL
 */
public class userHome extends javax.swing.JFrame {

    /**
     * Creates new form userHome
     */
    int floor = 1;
    int labelWidth = 5;
    int labelHeigth = 5;
    boolean maximizado = false;
    DefaultTableModel modelo;
    List<String> OrdenesEnCola;
    Thread ActualizarOrdenes = new Thread() {
        @Override
        public void run() {
            try {
                while (true) {
                    inicializeIcons();
                    cargarOrdenesPendientes();
                    cargarListaPendientes();
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
            }
        }
    };
    Thread Reloj = new Thread() {
        @Override
        public void run() {
            try {
                while (true) {
                    Date Hora = new Date();
                    lblHora.setText(new SimpleDateFormat("HH:mm:ss").format(Hora));
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
            }
        }
    };

    public int getLabelWidth() {
        return labelWidth;
    }

    public void setLabelWidth(int labelWidth) {
        this.labelWidth = labelWidth;
    }

    public int getLabelHeigth() {
        return labelHeigth;
    }

    public void setLabelHeigth(int labelHeigth) {
        this.labelHeigth = labelHeigth;
    }

    public void setUnActivated() {
        pnlBase.setBackground(Color.WHITE);
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public userHome() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("BIENVENIDO A REGISTER CASH FOOD");
        this.setIconImage(new Configurations().getImageIcon().getImage());
        this.setLabelHeigth(lblTableIcon0.getHeight());
        this.setLabelWidth(lblTableIcon0.getWidth());
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                Salir();
            }
        });
    }

    public void setFecha() {
        Date Fecha = new Date();
        lblMes.setText(new SimpleDateFormat("MMMM, YYYY").format(Fecha).toUpperCase());
        lblDia.setText(new SimpleDateFormat("dd").format(Fecha));
    }

    public void cargarOrdenesPendientes() {
        List pendientes = new Orders().getAllActiveOrders();
        OrdenesEnCola = new ArrayList();
        if (!pendientes.isEmpty()) {
            for (int i = 0; i < pendientes.size(); i++) {
                String[] registro = pendientes.get(i).toString().split(",");
                String temp = registro[0] + "," + registro[1] + "," + registro[6] + ",";
                OrdenesEnCola.add(temp);
            }
        }
        cargarListaPendientes();
    }

    public void loadTabla() {
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int filas, int columnas) {
                if (columnas < 0) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        modelo.addColumn("PEDIDO");
        modelo.addColumn("MESA");
        modelo.addColumn("HORA");
        tableData.setModel(modelo);
        tableData.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent Mouse_evt) {
                try {
                    if (Mouse_evt.getClickCount() == 1) {
                        String id = tableData.getValueAt(tableData.getSelectedRow(), 0).toString();
                        frameOrders o = new frameOrders();
                        o.loadOrder(new Orders().getOrderbyID(id), lblUser.getText());
                        o.show();
                        if (o.getOrdenParaLista() != null) {
                            OrdenesEnCola.add(o.getOrdenParaLista());
                        }
                        inicializeIcons();
                        cargarOrdenesPendientes();
                        cargarListaPendientes();
                    }
                } catch (Exception e) {
//                        JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        tableData.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void Inicialize(String User, String Permisos) {
        try {
            if (!new Configurations().isActivated()) {
                this.setUnActivated();
            }
            Reloj.start();
            setFecha();
            loadTabla();
            lblUser.setText(User);
            btnDomicilio.requestFocus();
            ActualizarOrdenes.start();
            pnlFloor.setVisible(false);
            loadTablePanels();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadTablePanels() {
        setTablesName();
        cargarOrdenesPendientes();
        cargarListaPendientes();
        inicializeIcons();
        setNewActionPreformeds();
    }

    public void setNewActionPreformeds() {
        SetNewActionPreformed(pnl0, lblMesa0);
        SetNewActionPreformed(pnl1, lblMesa1);
        SetNewActionPreformed(pnl2, lblMesa2);
        SetNewActionPreformed(pnl3, lblMesa3);
        SetNewActionPreformed(pnl4, lblMesa4);
        SetNewActionPreformed(pnl5, lblMesa5);
        SetNewActionPreformed(pnl6, lblMesa6);
        SetNewActionPreformed(pnl7, lblMesa7);
        SetNewActionPreformed(pnl8, lblMesa8);
        SetNewActionPreformed(pnl9, lblMesa9);
        SetNewActionPreformed(pnl10, lblMesa10);
        SetNewActionPreformed(pnl11, lblMesa11);
    }

    public void SetNewActionPreformed(JPanel pnl, JLabel lbl) {

        pnl.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                clicOnPanel(pnl, lbl);
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mousePressed(MouseEvent me) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent me) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent me) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent me) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });

    }

    public void clicOnPanel(JPanel pnl, JLabel lbl) {
        if (!new Orders().getActiveOrdersbyMesa(lbl.getText()).equals("")) {
            if (new Orders().getStateOrder(new Orders().getActiveOrdersbyMesa(lbl.getText()).split(","))) {
                frameOrders o = new frameOrders();
                o.newOrder(lbl.getText(), lblUser.getText());
                o.show();
                if (o.getOrdenParaLista() != null) {
                    OrdenesEnCola.add(o.getOrdenParaLista());
                }
            } else {
                frameOrders o = new frameOrders();
                o.loadOrder(new Orders().getActiveOrdersbyMesa(lbl.getText()).split(","), lblUser.getText());
                o.show();
                if (o.getOrdenParaLista() != null) {
                    OrdenesEnCola.add(o.getOrdenParaLista());
                }
            }
        } else {
            frameOrders o = new frameOrders();
            o.newOrder(lbl.getText(), lblUser.getText());
//            o.setUser(lblUser.getText());
            o.show();
            if (o.getOrdenParaLista() != null) {
                OrdenesEnCola.add(o.getOrdenParaLista());
            }
        }
        inicializeIcons();
        cargarOrdenesPendientes();
        cargarListaPendientes();
    }

    

//    public void cargar_logo() {
//        try {
//            lblLogo.setIcon(new Configurations().getLogo(lblLogo.getWidth(), lblLogo.getHeight()));
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
//        }
//    }

    public void setTablesName() {
        try {
            int p0 = (4 * 3) * (floor - 1);
            int p1 = (4 * 3) * (floor);
            String[] mesas = new String[12];
            int cont = 0;
            for (int i = p0; i < p1; i++) {
                mesas[cont] = "MESA " + (i + 1);
                cont++;
            }
            lblMesa0.setText(mesas[0]);
            lblMesa1.setText(mesas[1]);
            lblMesa2.setText(mesas[2]);
            lblMesa3.setText(mesas[3]);
            lblMesa4.setText(mesas[4]);
            lblMesa5.setText(mesas[5]);
            lblMesa6.setText(mesas[6]);
            lblMesa7.setText(mesas[7]);
            lblMesa8.setText(mesas[8]);
            lblMesa9.setText(mesas[9]);
            lblMesa10.setText(mesas[10]);
            lblMesa11.setText(mesas[11]);
            pnl0.setName(mesas[0]);
            pnl1.setName(mesas[1]);
            pnl2.setName(mesas[2]);
            pnl3.setName(mesas[3]);
            pnl4.setName(mesas[4]);
            pnl5.setName(mesas[5]);
            pnl10.setName(mesas[6]);
            pnl7.setName(mesas[7]);
            pnl8.setName(mesas[8]);
            pnl9.setName(mesas[9]);
            pnl6.setName(mesas[10]);
            pnl11.setName(mesas[11]);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void inicializeIcons() {
        int p0 = (4 * 3) * (floor - 1);
        int p1 = (4 * 3) * (floor);
        String[] mesas = new String[12];
        int cont = 0;
        for (int i = p0; i < p1; i++) {
            mesas[cont] = "MESA " + (i + 1);
            cont++;
        }
        String[] Ordenes = new Orders().getOrders(mesas);
        lblPiso.setText(String.valueOf(getFloor()));
        if (Ordenes[0] == null) {
            lblTableIcon0.setIcon(new Configurations().getTable2Icon(getLabelWidth(), getLabelHeigth()));
        } else {
            lblTableIcon0.setIcon(new Configurations().getTableIcon(getLabelWidth(), getLabelHeigth()));
        }
        if (Ordenes[1] == null) {
            lblTableIcon1.setIcon(new Configurations().getTable2Icon(getLabelWidth(), getLabelHeigth()));
        } else {
            lblTableIcon1.setIcon(new Configurations().getTableIcon(getLabelWidth(), getLabelHeigth()));
        }
        if (Ordenes[2] == null) {
            lblTableIcon2.setIcon(new Configurations().getTable2Icon(getLabelWidth(), getLabelHeigth()));
        } else {
            lblTableIcon2.setIcon(new Configurations().getTableIcon(getLabelWidth(), getLabelHeigth()));
        }
        if (Ordenes[3] == null) {
            lblTableIcon3.setIcon(new Configurations().getTable2Icon(getLabelWidth(), getLabelHeigth()));
        } else {
            lblTableIcon3.setIcon(new Configurations().getTableIcon(getLabelWidth(), getLabelHeigth()));
        }
        if (Ordenes[4] == null) {
            lblTableIcon4.setIcon(new Configurations().getTable2Icon(getLabelWidth(), getLabelHeigth()));
        } else {
            lblTableIcon4.setIcon(new Configurations().getTableIcon(getLabelWidth(), getLabelHeigth()));
        }
        if (Ordenes[5] == null) {
            lblTableIcon5.setIcon(new Configurations().getTable2Icon(getLabelWidth(), getLabelHeigth()));
        } else {
            lblTableIcon5.setIcon(new Configurations().getTableIcon(getLabelWidth(), getLabelHeigth()));
        }
        if (Ordenes[6] == null) {
            lblTableIcon6.setIcon(new Configurations().getTable2Icon(getLabelWidth(), getLabelHeigth()));
        } else {
            lblTableIcon6.setIcon(new Configurations().getTableIcon(getLabelWidth(), getLabelHeigth()));
        }

        if (Ordenes[7] == null) {
            lblTableIcon7.setIcon(new Configurations().getTable2Icon(getLabelWidth(), getLabelHeigth()));
        } else {
            lblTableIcon7.setIcon(new Configurations().getTableIcon(getLabelWidth(), getLabelHeigth()));
        }
        if (Ordenes[8] == null) {
            lblTableIcon8.setIcon(new Configurations().getTable2Icon(getLabelWidth(), getLabelHeigth()));
        } else {
            lblTableIcon8.setIcon(new Configurations().getTableIcon(getLabelWidth(), getLabelHeigth()));
        }
        if (Ordenes[9] == null) {
            lblTableIcon9.setIcon(new Configurations().getTable2Icon(getLabelWidth(), getLabelHeigth()));
        } else {
            lblTableIcon9.setIcon(new Configurations().getTableIcon(getLabelWidth(), getLabelHeigth()));
        }
        if (Ordenes[10] == null) {
            lblTableIcon10.setIcon(new Configurations().getTable2Icon(getLabelWidth(), getLabelHeigth()));
        } else {
            lblTableIcon10.setIcon(new Configurations().getTableIcon(getLabelWidth(), getLabelHeigth()));
        }
        if (Ordenes[11] == null) {
            lblTableIcon11.setIcon(new Configurations().getTable2Icon(getLabelWidth(), getLabelHeigth()));
        } else {
            lblTableIcon11.setIcon(new Configurations().getTableIcon(getLabelWidth(), getLabelHeigth()));
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

        pnlBase = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lblMes = new javax.swing.JLabel();
        lblDia = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        lblHora = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnDomicilio = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableData = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        pnlFloor = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        lblPiso = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pnl0 = new javax.swing.JPanel();
        lblTableIcon0 = new javax.swing.JLabel();
        lblMesa0 = new javax.swing.JLabel();
        pnl4 = new javax.swing.JPanel();
        lblTableIcon4 = new javax.swing.JLabel();
        lblMesa4 = new javax.swing.JLabel();
        pnl8 = new javax.swing.JPanel();
        lblTableIcon8 = new javax.swing.JLabel();
        lblMesa8 = new javax.swing.JLabel();
        pnl1 = new javax.swing.JPanel();
        lblTableIcon1 = new javax.swing.JLabel();
        lblMesa1 = new javax.swing.JLabel();
        pnl5 = new javax.swing.JPanel();
        lblTableIcon5 = new javax.swing.JLabel();
        lblMesa5 = new javax.swing.JLabel();
        pnl9 = new javax.swing.JPanel();
        lblTableIcon9 = new javax.swing.JLabel();
        lblMesa9 = new javax.swing.JLabel();
        pnl2 = new javax.swing.JPanel();
        lblTableIcon2 = new javax.swing.JLabel();
        lblMesa2 = new javax.swing.JLabel();
        pnl6 = new javax.swing.JPanel();
        lblTableIcon6 = new javax.swing.JLabel();
        lblMesa6 = new javax.swing.JLabel();
        pnl10 = new javax.swing.JPanel();
        lblTableIcon10 = new javax.swing.JLabel();
        lblMesa10 = new javax.swing.JLabel();
        pnl3 = new javax.swing.JPanel();
        lblTableIcon3 = new javax.swing.JLabel();
        lblMesa3 = new javax.swing.JLabel();
        pnl7 = new javax.swing.JPanel();
        lblTableIcon7 = new javax.swing.JLabel();
        lblMesa7 = new javax.swing.JLabel();
        pnl11 = new javax.swing.JPanel();
        lblTableIcon11 = new javax.swing.JLabel();
        lblMesa11 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        mitemCerrarSesion = new javax.swing.JMenuItem();
        mitemSalir = new javax.swing.JMenuItem();
        menuAbout = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SESION INICIADA");

        lblUser.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        lblUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUser.setText("USER");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Calendario"));

        lblMes.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        lblMes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMes.setText("SEPTIEMBRE, 2020");

        lblDia.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        lblDia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDia.setText("000");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Reloj"));

        lblHora.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        lblHora.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHora.setText("HH:MM:SS");

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("HORA LOCAL");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHora, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHora, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Pedidos"));
        jPanel3.setFocusTraversalPolicyProvider(true);

        btnDomicilio.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnDomicilio.setText("ORDEN A DOMICILIO");
        btnDomicilio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDomicilioActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("LISTA DE PENDIENTES");
        jLabel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tableData.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        tableData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MESA", "PEDIDO", "HORA"
            }
        ));
        tableData.setRowHeight(80);
        jScrollPane2.setViewportView(tableData);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDomicilio, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder("MESAS"));

        jButton3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton3.setText(">>");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton4.setText("<<");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        lblPiso.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        lblPiso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPiso.setText("000");

        jLabel26.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText(" PISO");

        javax.swing.GroupLayout pnlFloorLayout = new javax.swing.GroupLayout(pnlFloor);
        pnlFloor.setLayout(pnlFloorLayout);
        pnlFloorLayout.setHorizontalGroup(
            pnlFloorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFloorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFloorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPiso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addContainerGap())
        );
        pnlFloorLayout.setVerticalGroup(
            pnlFloorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFloorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFloorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFloorLayout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblPiso)))
                .addContainerGap())
        );

        pnl0.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnl0.setName("MESA 1"); // NOI18N

        lblTableIcon0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblMesa0.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblMesa0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMesa0.setText("000");

        javax.swing.GroupLayout pnl0Layout = new javax.swing.GroupLayout(pnl0);
        pnl0.setLayout(pnl0Layout);
        pnl0Layout.setHorizontalGroup(
            pnl0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl0Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTableIcon0, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(lblMesa0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl0Layout.setVerticalGroup(
            pnl0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl0Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTableIcon0, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMesa0)
                .addContainerGap())
        );

        pnl4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblTableIcon4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblMesa4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblMesa4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMesa4.setText("000");

        javax.swing.GroupLayout pnl4Layout = new javax.swing.GroupLayout(pnl4);
        pnl4.setLayout(pnl4Layout);
        pnl4Layout.setHorizontalGroup(
            pnl4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTableIcon4, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(lblMesa4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl4Layout.setVerticalGroup(
            pnl4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTableIcon4, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMesa4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnl8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblTableIcon8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblMesa8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblMesa8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMesa8.setText("000");

        javax.swing.GroupLayout pnl8Layout = new javax.swing.GroupLayout(pnl8);
        pnl8.setLayout(pnl8Layout);
        pnl8Layout.setHorizontalGroup(
            pnl8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTableIcon8, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(lblMesa8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl8Layout.setVerticalGroup(
            pnl8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTableIcon8, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMesa8)
                .addContainerGap())
        );

        pnl1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnl1.setName("MESA 2"); // NOI18N

        lblTableIcon1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblMesa1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblMesa1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMesa1.setText("000");

        javax.swing.GroupLayout pnl1Layout = new javax.swing.GroupLayout(pnl1);
        pnl1.setLayout(pnl1Layout);
        pnl1Layout.setHorizontalGroup(
            pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTableIcon1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(lblMesa1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl1Layout.setVerticalGroup(
            pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTableIcon1, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMesa1)
                .addContainerGap())
        );

        pnl5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblTableIcon5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblMesa5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblMesa5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMesa5.setText("000");

        javax.swing.GroupLayout pnl5Layout = new javax.swing.GroupLayout(pnl5);
        pnl5.setLayout(pnl5Layout);
        pnl5Layout.setHorizontalGroup(
            pnl5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTableIcon5, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(lblMesa5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl5Layout.setVerticalGroup(
            pnl5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTableIcon5, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMesa5)
                .addContainerGap())
        );

        pnl9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblTableIcon9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblMesa9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblMesa9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMesa9.setText("000");

        javax.swing.GroupLayout pnl9Layout = new javax.swing.GroupLayout(pnl9);
        pnl9.setLayout(pnl9Layout);
        pnl9Layout.setHorizontalGroup(
            pnl9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTableIcon9, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(lblMesa9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl9Layout.setVerticalGroup(
            pnl9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTableIcon9, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMesa9)
                .addContainerGap())
        );

        pnl2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnl2.setName("MESA 3"); // NOI18N

        lblTableIcon2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblMesa2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblMesa2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMesa2.setText("000");

        javax.swing.GroupLayout pnl2Layout = new javax.swing.GroupLayout(pnl2);
        pnl2.setLayout(pnl2Layout);
        pnl2Layout.setHorizontalGroup(
            pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTableIcon2, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(lblMesa2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl2Layout.setVerticalGroup(
            pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTableIcon2, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMesa2)
                .addContainerGap())
        );

        pnl6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblTableIcon6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblMesa6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblMesa6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMesa6.setText("000");

        javax.swing.GroupLayout pnl6Layout = new javax.swing.GroupLayout(pnl6);
        pnl6.setLayout(pnl6Layout);
        pnl6Layout.setHorizontalGroup(
            pnl6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTableIcon6, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(lblMesa6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl6Layout.setVerticalGroup(
            pnl6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTableIcon6, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMesa6)
                .addContainerGap())
        );

        pnl10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblTableIcon10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblMesa10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblMesa10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMesa10.setText("000");

        javax.swing.GroupLayout pnl10Layout = new javax.swing.GroupLayout(pnl10);
        pnl10.setLayout(pnl10Layout);
        pnl10Layout.setHorizontalGroup(
            pnl10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTableIcon10, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(lblMesa10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl10Layout.setVerticalGroup(
            pnl10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTableIcon10, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMesa10)
                .addContainerGap())
        );

        pnl3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnl3.setName("MESA 4"); // NOI18N

        lblTableIcon3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblMesa3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblMesa3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMesa3.setText("000");

        javax.swing.GroupLayout pnl3Layout = new javax.swing.GroupLayout(pnl3);
        pnl3.setLayout(pnl3Layout);
        pnl3Layout.setHorizontalGroup(
            pnl3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTableIcon3, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(lblMesa3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl3Layout.setVerticalGroup(
            pnl3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTableIcon3, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMesa3)
                .addContainerGap())
        );

        pnl7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblTableIcon7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblMesa7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblMesa7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMesa7.setText("000");

        javax.swing.GroupLayout pnl7Layout = new javax.swing.GroupLayout(pnl7);
        pnl7.setLayout(pnl7Layout);
        pnl7Layout.setHorizontalGroup(
            pnl7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTableIcon7, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(lblMesa7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl7Layout.setVerticalGroup(
            pnl7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTableIcon7, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMesa7)
                .addContainerGap())
        );

        pnl11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblTableIcon11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblMesa11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblMesa11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMesa11.setText("000");

        javax.swing.GroupLayout pnl11Layout = new javax.swing.GroupLayout(pnl11);
        pnl11.setLayout(pnl11Layout);
        pnl11Layout.setHorizontalGroup(
            pnl11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTableIcon11, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(lblMesa11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl11Layout.setVerticalGroup(
            pnl11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTableIcon11, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMesa11)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(pnl0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnl2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnl3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(pnl4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnl5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnl6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnl7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(pnl8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnl9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnl10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnl11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(pnlFloor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlFloor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlBaseLayout = new javax.swing.GroupLayout(pnlBase);
        pnlBase.setLayout(pnlBaseLayout);
        pnlBaseLayout.setHorizontalGroup(
            pnlBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBaseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBaseLayout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlBaseLayout.setVerticalGroup(
            pnlBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBaseLayout.createSequentialGroup()
                .addGroup(pnlBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBaseLayout.createSequentialGroup()
                        .addGroup(pnlBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlBaseLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel3.getAccessibleContext().setAccessibleName("PEDIDOS");

        menuArchivo.setText("Archivo");

        mitemCerrarSesion.setText("Cerrar Sesion");
        mitemCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemCerrarSesionActionPerformed(evt);
            }
        });
        menuArchivo.add(mitemCerrarSesion);

        mitemSalir.setText("Salir");
        mitemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitemSalirActionPerformed(evt);
            }
        });
        menuArchivo.add(mitemSalir);

        jMenuBar1.add(menuArchivo);

        menuAbout.setText("Ayuda");

        jMenuItem2.setText("Info. de producto");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        menuAbout.add(jMenuItem2);

        jMenuBar1.add(menuAbout);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mitemCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemCerrarSesionActionPerformed
        cerrarSesion();
    }//GEN-LAST:event_mitemCerrarSesionActionPerformed

    private void mitemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitemSalirActionPerformed
        Salir();
    }//GEN-LAST:event_mitemSalirActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        infoProd();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        upFloor();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        downFloor();
    }//GEN-LAST:event_jButton4ActionPerformed


    private void btnDomicilioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDomicilioActionPerformed
        ordenDomicilio();
    }//GEN-LAST:event_btnDomicilioActionPerformed
    
    public void ordenDomicilio() {
        frameOrders o = new frameOrders();
        o.newOrder("ORDEN A DOMICILIO", lblUser.getText());
        o.show();
        if (o.getOrdenParaLista() != null) {
            OrdenesEnCola.add(o.getOrdenParaLista());
        }
        cargarOrdenesPendientes();
        cargarListaPendientes();
    }

    public void cargarListaPendientes() {
        if (modelo != null) {
            modelo.setRowCount(0);
            if (!OrdenesEnCola.isEmpty()) {
                for (int i = 0; i < OrdenesEnCola.size(); i++) {
                    modelo.addRow(OrdenesEnCola.get(i).split(","));
                }
            }
        }
    }

    public void upFloor() {
        if (getFloor() < 3) {
            setFloor(getFloor() + 1);
            inicializeIcons();
            setTablesName();
        }
    }

    public void downFloor() {
        try {
            if (getFloor() > 1) {
                setFloor(getFloor() - 1);
                inicializeIcons();
                setTablesName();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void infoProd() {
        try {
            String proyectname = "Register Cash Food";
            String version = "4.0";
            JOptionPane.showMessageDialog(null, "<html><h1>Información del producto</h1><font SIZE=5><center><p>***<b>"+proyectname+" v"+version+"</center></b> ***<p><p><b><center>Desarrollado por</b></center><p>Osbaldo Toledo Ramos - PTB en Informática<p>E-Mail: <b>osbaldo.toledoramos@hotmail.com</b><p><center><i>Prepare for Unforeseen Consequences<p></font></html>", "¡Info. de producto!", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.INFORMATION_MESSAGE);
        }
    }    

    public void Salir() {
        int resp = JOptionPane.showConfirmDialog(null, "<html><h1>SALIR DE LA APLICACIÓN</h1><font SIZE=5><p>Se perderán todos los datos que no se hayan guardado… ¿desea continuar? </font></html>", "Salir", JOptionPane.YES_NO_OPTION);
        if (resp == 0) {
            System.exit(resp);
        }
    }

    public void cerrarSesion() {
        int resp = JOptionPane.showConfirmDialog(null, "<html><h1>CERRAR SESION</h1><font SIZE=5><p>Se perderán todos los datos que no se hayan guardado… ¿desea continuar? </font></html>", "Cerrar Sesion!", JOptionPane.YES_NO_OPTION);
        if (resp == 0) {
            dispose();
            Login cs = new Login();
            cs.show();
        }
    }

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
            java.util.logging.Logger.getLogger(userHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(userHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(userHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(userHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new userHome().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDomicilio;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDia;
    private javax.swing.JLabel lblHora;
    private javax.swing.JLabel lblMes;
    private javax.swing.JLabel lblMesa0;
    private javax.swing.JLabel lblMesa1;
    private javax.swing.JLabel lblMesa10;
    private javax.swing.JLabel lblMesa11;
    private javax.swing.JLabel lblMesa2;
    private javax.swing.JLabel lblMesa3;
    private javax.swing.JLabel lblMesa4;
    private javax.swing.JLabel lblMesa5;
    private javax.swing.JLabel lblMesa6;
    private javax.swing.JLabel lblMesa7;
    private javax.swing.JLabel lblMesa8;
    private javax.swing.JLabel lblMesa9;
    private javax.swing.JLabel lblPiso;
    private javax.swing.JLabel lblTableIcon0;
    private javax.swing.JLabel lblTableIcon1;
    private javax.swing.JLabel lblTableIcon10;
    private javax.swing.JLabel lblTableIcon11;
    private javax.swing.JLabel lblTableIcon2;
    private javax.swing.JLabel lblTableIcon3;
    private javax.swing.JLabel lblTableIcon4;
    private javax.swing.JLabel lblTableIcon5;
    private javax.swing.JLabel lblTableIcon6;
    private javax.swing.JLabel lblTableIcon7;
    private javax.swing.JLabel lblTableIcon8;
    private javax.swing.JLabel lblTableIcon9;
    private javax.swing.JLabel lblUser;
    private javax.swing.JMenu menuAbout;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenuItem mitemCerrarSesion;
    private javax.swing.JMenuItem mitemSalir;
    private javax.swing.JPanel pnl0;
    private javax.swing.JPanel pnl1;
    private javax.swing.JPanel pnl10;
    private javax.swing.JPanel pnl11;
    private javax.swing.JPanel pnl2;
    private javax.swing.JPanel pnl3;
    private javax.swing.JPanel pnl4;
    private javax.swing.JPanel pnl5;
    private javax.swing.JPanel pnl6;
    private javax.swing.JPanel pnl7;
    private javax.swing.JPanel pnl8;
    private javax.swing.JPanel pnl9;
    private javax.swing.JPanel pnlBase;
    private javax.swing.JPanel pnlFloor;
    private javax.swing.JTable tableData;
    // End of variables declaration//GEN-END:variables
}
