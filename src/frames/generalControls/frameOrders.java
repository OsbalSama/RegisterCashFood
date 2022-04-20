/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames.generalControls;

import Classes.CatProvClass;
import Classes.Clients;
import Classes.Configurations;
import Classes.Dishes;
import Classes.FormatoNumerico;
import Classes.Orders;
import Classes.PDFDocs;
import Classes.SaleProdcts;
import Dialogs.Cobrar;
import Dialogs.MensajeSiNo;
import Dialogs.RePrint;
import Dialogs.selectClient;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author OSBALDO
 */
public class frameOrders extends javax.swing.JFrame {

    /**
     * Creates new form frameOrders
     */
    DefaultTableModel modelo;
    String OrdenParaLista = null;
    String User;
    Component Principal;

    public Component getPrincipal() {
        return Principal;
    }

    public void setPrincipal(Component Principal) {
        this.Principal = Principal;
    }

    public String getOrdenParaLista() {
        return OrdenParaLista;
    }

    public void setOrdenParaLista(String OrdenParaLista) {
        this.OrdenParaLista = OrdenParaLista;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String User) {
        this.User = User;
    }

    public frameOrders() {
        initComponents();
        this.setIconImage(new Configurations().getImageIcon().getImage());
        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        txtCosteEnvio.setTransferHandler(null);
        loadTabla();
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
        modelo.addColumn("ID REG");
        modelo.addColumn("NO. ORDEN");
        modelo.addColumn("PLATILLO");
        modelo.addColumn("PRECIO");
        modelo.addColumn("CANT");
        modelo.addColumn("TOTAL");
        tableData.setModel(modelo);
        tableData.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent Mouse_evt) {
                JTable table = (JTable) Mouse_evt.getSource();
                Point point = Mouse_evt.getPoint();
                int row = table.rowAtPoint(point);
                if (Mouse_evt.getClickCount() == 2) {
                    delProd(row);
                }
            }
        });
        tableData.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public int calcPages(List Dishes) {
        int pages = 1;
        if (!Dishes.isEmpty()) {
            int cont = 0;
            for (int i = 0; i < Dishes.size(); i++) {
                if (cont < 9) {
                    cont++;
                } else {
                    pages++;
                    cont = 0;
                }
            }
        }
        return pages;
    }

    public boolean existInTable(String NameProd) {
        boolean resp = false;
        for (int i = 0; i < tableData.getRowCount(); i++) {
            String texto = tableData.getValueAt(i, 2).toString();
            if (texto.equals(NameProd)) {
                resp = true;
                break;
            }
        }
        return resp;
    }

    public void updateRegSpecial(String NameProd, int cantid) {
        try {
            for (int i = 0; i < tableData.getRowCount(); i++) {
                String texto = tableData.getValueAt(i, 2).toString();
                if (texto.equals(NameProd)) {
                    double precio = Double.parseDouble(tableData.getValueAt(i, 3).toString());
                    int cant = Integer.parseInt(tableData.getValueAt(i, 4).toString()) + cantid;
                    double total = precio * cant;
                    tableData.setValueAt(cant, i, 4);
                    tableData.setValueAt(new FormatoNumerico().RemoverFormatoMoneda(total + ""), i, 5);
                    break;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateDishCarta(String NameProd) {
        try {
            for (int i = 0; i < tableData.getRowCount(); i++) {
                String texto = tableData.getValueAt(i, 2).toString();
                if (texto.equals(NameProd)) {
                    double precio = Double.parseDouble(tableData.getValueAt(i, 3).toString());
                    int cant = Integer.parseInt(tableData.getValueAt(i, 4).toString()) + 1;
                    double total = precio * cant;
                    tableData.setValueAt(cant, i, 4);
                    tableData.setValueAt(new FormatoNumerico().RemoverFormatoMoneda(total + ""), i, 5);
                    break;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addDishCarta(String idDish) {
        String[] dish = new Dishes().getDishbyID(idDish).split(",");
        if (dish != null) {
            if (existInTable(dish[1])) {
                updateDishCarta(dish[1]);
            } else {
                int cant = 1;
                double total = 1 * Double.parseDouble(new FormatoNumerico().RemoverFormatoMoneda(dish[4]));
                String nreg = new SaleProdcts().genID()
                        + "," + lblIDReg.getText().replace(',', ' ')
                        + "," + dish[1].replace(',', ' ')
                        + "," + new FormatoNumerico().RemoverFormatoMoneda(dish[4])
                        + "," + cant
                        + "," + new FormatoNumerico().RemoverFormatoMoneda(total + "") + ",";
                modelo.addRow(nreg.split(","));
            }
        }
    }

    public void newAtionPreformed(JPanel panel) {
        String name = panel.getName();
        if (!name.equals("VOID")) {
            addDishCarta(panel.getName());
            recalcTotal();
            btnAceptar.requestFocus();
        }
    }

    public void setNewActionPreformed(JPanel panel) {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                newAtionPreformed(panel);
            }
        });
    }

    public void newOrder(String mesa, String User) {
        lblMesa.setText(mesa);
        lblIDReg.setText(new Orders().genID());
        btnCancel.setVisible(false);
        recalcTotal();
        setDefaultClient();
        setUser(User);
    }

    public void loadDishes(String IDOrder) {
        List Dishes = new SaleProdcts().getProdVendByID(IDOrder);
        if (!Dishes.isEmpty()) {
            for (int i = 0; i < Dishes.size(); i++) {
                modelo.addRow(Dishes.get(i).toString().split(","));
            }
        } else {
            JOptionPane.showMessageDialog(null, "ORDEN PERDIDA ", "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setDefaultClient() {
        lblIDCliente.setText("DEFAULT");
        txtaClient.setText("CLIENTE ESTANDAR: "
                + "\n DIRECCION LOCAL"
                + "\n SIN TELEFONO");
    }

    public void loadClient(String idClient) {
        if (new Clients().existID(idClient)) {
            String[] cliente = new Clients().getClientbyID(idClient);
            lblIDCliente.setText(cliente[0]);
            txtaClient.setText(new Clients().getClienttoString(idClient));
        } else {
            setDefaultClient();
        }
    }

    public void loadOrder(String[] OrdenActiva, String User) {
        jLabel1.setText("VER ORDEN ACTIVA");
        btnAceptar.setText("ACTUALIZAR");
        lblIDReg.setText(OrdenActiva[0]);
        lblMesa.setText(OrdenActiva[1]);
        txtTotal.setText(new FormatoNumerico().AsignarFormatoMoneda(OrdenActiva[2]));
        if (OrdenActiva[3].equals("0")) {
            chkEnvio.setSelected(false);
            txtCosteEnvio.setText("$0");
        } else {
            chkEnvio.setSelected(true);
            txtCosteEnvio.setEditable(true);
            txtCosteEnvio.setText(new FormatoNumerico().RemoverFormatoMoneda(OrdenActiva[3]));
        }
        loadDishes(lblIDReg.getText());
        loadClient(OrdenActiva[5]);
        setUser(User);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblMesa = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnCobrar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableData = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        lblIDReg = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCosteEnvio = new javax.swing.JTextField();
        chkEnvio = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        btnImpOrden = new javax.swing.JButton();
        btnImpComanda = new javax.swing.JButton();
        btnCarta = new javax.swing.JButton();
        btnSelClient = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtaClient = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        lblIDCliente = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("NUEVA ORDEN");

        lblMesa.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        lblMesa.setText("NUMERO MESA");

        btnAceptar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnAceptar.setText("GUARDAR");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCerrar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnCerrar.setText("CERRAR");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnCancel.setText("CANCELAR");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnCobrar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnCobrar.setText("COBRAR");
        btnCobrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCobrarActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Pedidos"));

        tableData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PLATILLO", "PRECIO", "CANT", "TOTAL", "ID REG", "ORDEN"
            }
        ));
        tableData.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableData.setRowHeight(70);
        tableData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tableDataKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tableData);

        txtBuscar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });

        btnBuscar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnBuscar.setText("BUSCAR");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel7.setText("PEDIDO NO.");

        lblIDReg.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblIDReg.setText("000000000000");

        txtTotal.setEditable(false);
        txtTotal.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setText("0000.00");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setText("TOTAL ($)");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setText("ENVIO ($)");

        txtCosteEnvio.setEditable(false);
        txtCosteEnvio.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtCosteEnvio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCosteEnvio.setText("$0");
        txtCosteEnvio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCosteEnvioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCosteEnvioKeyTyped(evt);
            }
        });

        chkEnvio.setText("AÑADIR ENVIÓ");
        chkEnvio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEnvioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(chkEnvio))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtBuscar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscar))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblIDReg)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCosteEnvio)
                                    .addComponent(txtTotal))))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblIDReg))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCosteEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkEnvio)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap())
        );

        btnImpOrden.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnImpOrden.setText("IMP CUENTA");
        btnImpOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImpOrdenActionPerformed(evt);
            }
        });

        btnImpComanda.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnImpComanda.setText("IMP COMANDA");
        btnImpComanda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImpComandaActionPerformed(evt);
            }
        });

        btnCarta.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnCarta.setText("ABRIR CARTA");
        btnCarta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCartaActionPerformed(evt);
            }
        });

        btnSelClient.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnSelClient.setText("SELECCIONAR");
        btnSelClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelClientActionPerformed(evt);
            }
        });

        txtaClient.setEditable(false);
        txtaClient.setColumns(20);
        txtaClient.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtaClient.setLineWrap(true);
        txtaClient.setRows(5);
        jScrollPane2.setViewportView(txtaClient);

        jLabel4.setText("CLIENTE");

        lblIDCliente.setText("00000000000");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCarta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnImpOrden, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnImpComanda, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblIDCliente)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSelClient, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnImpOrden, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnImpComanda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCarta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblIDCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSelClient, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(lblMesa)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCobrar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblMesa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCobrar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void recalcTotal() {
        double contador = tableData.getRowCount();
        double Sumatoria = 0;
        if (contador > 0) {
            for (int i = 0; i < contador; i++) {
                Sumatoria += Double.parseDouble(new FormatoNumerico().RemoverFormatoMoneda(modelo.getValueAt(i, 5).toString()));
            }
        }
        if (!txtCosteEnvio.getText().equals("") && Double.parseDouble(new FormatoNumerico().RemoverFormatoMoneda(txtCosteEnvio.getText())) > 0) {
            Sumatoria += Double.parseDouble(new FormatoNumerico().RemoverFormatoMoneda(txtCosteEnvio.getText()));
        }
        if (Sumatoria > 0) {
            txtTotal.setText(new FormatoNumerico().AsignarFormatoMoneda(Sumatoria + ""));
        } else {
            txtTotal.setText(new FormatoNumerico().AsignarFormatoMoneda("0.00"));
        }

    }

    public void addProd(int row) {
        try {
            double precio = Double.parseDouble(new FormatoNumerico().RemoverFormatoMoneda(tableData.getValueAt(row, 3).toString()));
            int cant = Integer.parseInt(tableData.getValueAt(row, 4).toString()) + 1;
            double total = precio * cant;
            tableData.setValueAt(cant, row, 4);
            tableData.setValueAt(new FormatoNumerico().RemoverFormatoMoneda(total + ""), row, 5);
            recalcTotal();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void delProd(int row) {
        try {
            int valor_cantidad = Integer.parseInt(modelo.getValueAt(tableData.getSelectedRow(), 4).toString());
            if (valor_cantidad > 1) {
                double precio = Double.parseDouble(new FormatoNumerico().RemoverFormatoMoneda(tableData.getValueAt(row, 3).toString()));
                int cant = Integer.parseInt(tableData.getValueAt(row, 4).toString()) - 1;
                double total = precio * cant;
                tableData.setValueAt(cant, row, 4);
                tableData.setValueAt(new FormatoNumerico().RemoverFormatoMoneda(total + ""), row, 5);
                recalcTotal();
            } else {
                modelo.removeRow(row);
                recalcTotal();
                btnAceptar.requestFocus();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tableDataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableDataKeyPressed
        try {
            //APRETAR -
            if (evt.getKeyCode() == 107 && tableData.getRowCount() > 0) {
                addProd(tableData.getSelectedRow());
            }

            //APRETAR +
            if (evt.getKeyCode() == 109 && tableData.getRowCount() > 0) {
                delProd(tableData.getSelectedRow());
            }

            //APRETAR SUP
            if (evt.getKeyCode() == KeyEvent.VK_DELETE && tableData.getRowCount() > 0) {
                modelo.removeRow(tableData.getSelectedRow());
                recalcTotal();
            }

        } catch (Exception e) {
            //            JOptionPane.showMessageDialog(this, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tableDataKeyPressed

    public int getPage(JLabel Label) {
        int resp = 0;
        String texto = Label.getText().split("-")[0];
        resp = Integer.parseInt(texto);
        return resp;
    }

    public int getLastPage(JLabel Label) {
        int resp = 0;
        String texto = Label.getText().split("-")[1];
        resp = Integer.parseInt(texto);
        return resp;
    }

    public void setPagina(JLabel label, int pagina) {
        label.setText(pagina + "-" + getLastPage(label));
    }

    public int getPage(String texto) {
        int resp = 0;
        try {
            String[] temp = texto.split("-");
            resp = Integer.parseInt(temp[0]);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loadCarta: " + e);
        }
        return resp;
    }

    public List getItemsForPage(List Dishes, int Page) {
        List<String> resp = new ArrayList<String>();
        try {
            int p0 = 9 * (Page - 1);
            int p1 = 9 * (Page);
            for (int i = 0; i < Dishes.size(); i++) {
                if ((i >= p0 && i < p1)) {
                    resp.add(Dishes.get(i).toString());
                }
            }
            if (resp.size() < 9) {
                for (int i = resp.size(); i < 9; i++) {
                    resp.add("VOID");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e);
        }
        return resp;
    }

    public void setPanelBackground(JPanel Panel, Color clr) {
        Panel.setBackground(clr);
    }

    public void setPanelLabelData(String data, JPanel Panel, JLabel label) {
        if (data.equals("VOID")) {
            label.setText("VACIO");
            Panel.setName("VOID");
            setPanelBackground(Panel, Color.GRAY);
        } else {
            String[] temp = data.split(",");
            label.setText("<html><center>" + temp[1] + "</center></html>");
            Panel.setName(temp[0]);
            setPanelBackground(Panel, Color.WHITE);
        }
    }

    public boolean compCacillas() {
        boolean resp = false;
        if (tableData.getRowCount() == 0) {
            resp = true;
        } else if ("".equals(txtCosteEnvio.getText())) {
            resp = true;
        } else if ("0.00".equals(new FormatoNumerico().RemoverFormatoMoneda(txtTotal.getText()))) {
            resp = true;
        }
        return resp;
    }

    public boolean mostrarMensaje(String titulo, String cont, int tipo) {
        MensajeSiNo mensaje = new MensajeSiNo(this, true);
        mensaje.setData(titulo, cont, tipo);
        mensaje.show();
        return mensaje.isResp();
    }

    public void saveSaledProducts() {
        try {
            int filas = tableData.getRowCount();
            for (int i = 0; i < filas; i++) {
                String nreg = tableData.getValueAt(i, 0) + ","
                        + lblIDReg.getText() + ",";
                int columnas = tableData.getColumnCount();
                for (int j = 2; j < columnas; j++) {
                    nreg += tableData.getValueAt(i, j).toString() + ",";
                }
                new SaleProdcts().insProdVend(nreg);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e);
        }
    }

    public void UpdateSaledProducts() {
        try {
            int filas = tableData.getRowCount();
            if (filas > 0) {
                for (int i = 0; i < filas; i++) {
                    String id = tableData.getValueAt(i, 0).toString();
                    String nreg = id + ","
                            + lblIDReg.getText() + ",";
                    int columnas = tableData.getColumnCount();
                    for (int j = 2; j < columnas; j++) {
                        nreg += tableData.getValueAt(i, j).toString() + ",";
                    }
                    if (!new SaleProdcts().existID(id)) {
                        new SaleProdcts().insProdVend(nreg);
                    } else {
                        new SaleProdcts().updateProdVend(nreg);
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e);
        }
    }

    public void saveProd() {
        if (!compCacillas()) {
            String nreg = lblIDReg.getText()
                    + "," + lblMesa.getText().replace(',', ' ')
                    + "," + new FormatoNumerico().RemoverFormatoMoneda(txtTotal.getText())
                    + "," + new FormatoNumerico().RemoverFormatoMoneda(txtCosteEnvio.getText())
                    + "," + "PENDIENTE"
                    + "," + lblIDCliente.getText()
                    + "," + new SimpleDateFormat("dd-MM-YYYY").format(new Date())
                    + "," + new SimpleDateFormat("hh:mm").format(new Date()) + ",";
            if (btnAceptar.getText().equals("GUARDAR")) {
                try {
                    if (mostrarMensaje("GUARDAR ORDEN", "DESEA GUARDAR LA NUEVA ORDEN?", 0)) {
                        new Orders().insOrder(nreg);
                        saveSaledProducts();
                        setOrdenParaLista(lblIDReg.getText().replace(',', ' ') + "," + lblMesa.getText() + "," + new SimpleDateFormat("hh:mm").format(new Date()) + ",");
                        this.dispose();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error: " + e);
                }
            } else {
                try {
                    if (mostrarMensaje("ACTUALIZAR ORDEN", "DESEA ACTUALIZAR EL REGISTRO SELECCIONADO?", 0)) {
                        new Orders().updateOrder(nreg);
                        UpdateSaledProducts();
                        this.dispose();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error: " + e);
                }
            }
        }
    }

    private void btnSelClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelClientActionPerformed
        setClient();
    }//GEN-LAST:event_btnSelClientActionPerformed

    public void setClient() {
        selectClient sc = new selectClient(this, true);
        sc.showClients();
        sc.show();
        if (sc.getSelectedClient() != null) {
            loadClient(sc.getSelectedClient());
        }
        txtaClient.setCaretPosition(0);
    }

    private void btnCartaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCartaActionPerformed
        if (!new Dishes().getDBDishes().isEmpty()) {
            Carta c = new Carta(null, true);
            c.inicialize();
            c.show();
            if (c.getResp() != null) {
                String[] datos = c.getResp().split(",");
                addDishfromCarta(datos[0], datos[1]);
            }
            recalcTotal();
        } else {
            JOptionPane.showMessageDialog(null, "<html><h1>--SIN INVEVTARIO--</h1><font SIZE=5><p>Para activar la carta debe de tener al menos 1 producto registrado...<p>Clic para cerrar<p></font></html>", "¡Ya abierto!", JOptionPane.WARNING_MESSAGE);
//            JOptionPane.showMessageDialog(this, "No hay categorias registradas");
        }
        btnAceptar.requestFocus();
    }//GEN-LAST:event_btnCartaActionPerformed

    public void addDishfromCarta(String idDish, String cant) {
        String[] dish = new Dishes().getDishbyID(idDish).split(",");
        if (dish != null) {
            if (existInTable(dish[1])) {
                updateRegSpecial(dish[1], Integer.parseInt(cant));
            } else {
                if (existInTable(dish[1])) {
                    updateDishCarta(dish[1]);
                } else {
                    double total = Integer.parseInt(cant) * Double.parseDouble(dish[4]);
                    String nreg = new SaleProdcts().genID()
                            + "," + lblIDReg.getText().replace(',', ' ')
                            + "," + dish[1].replace(',', ' ')
                            + "," + new FormatoNumerico().RemoverFormatoMoneda(dish[4])
                            + "," + cant
                            + "," + new FormatoNumerico().RemoverFormatoMoneda(total + "");
                    modelo.addRow(nreg.split(","));
                }
            }
        }
        recalcTotal();
    }

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        saveProd();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        try {
            if (!compCacillas()) {
                String nreg = lblIDReg.getText()
                        + "," + lblMesa.getText().replace(',', ' ')
                        + "," + new FormatoNumerico().RemoverFormatoMoneda(txtTotal.getText())
                        + "," + new FormatoNumerico().RemoverFormatoMoneda(txtCosteEnvio.getText())
                        + "," + "CANCELADO"
                        + "," + lblIDCliente.getText()
                        + "," + new SimpleDateFormat("dd-MM-YYYY").format(new Date())
                        + "," + new SimpleDateFormat("hh:mm").format(new Date()) + ",";
                if (mostrarMensaje("CANCELAR ORDEN", "DESEA CANCELAR ORDEN?", 0)) {
                    new Orders().updateOrder(nreg);
                    UpdateSaledProducts();
                    mostrarMensaje("CANCELAR ORDEN", "ORDEN CANCELADA CORRECTAMENTE", 3);
                    this.dispose();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e);
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnCobrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCobrarActionPerformed
        CobrarOrden();
    }//GEN-LAST:event_btnCobrarActionPerformed

    private void btnImpOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImpOrdenActionPerformed
        impOrden();
    }//GEN-LAST:event_btnImpOrdenActionPerformed

    private void btnImpComandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImpComandaActionPerformed
        impComanda();
    }//GEN-LAST:event_btnImpComandaActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscarDish();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        buscarDish();
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void chkEnvioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEnvioActionPerformed
        if (chkEnvio.isSelected()) {
            txtCosteEnvio.setEditable(true);
            txtCosteEnvio.setText("");
            txtCosteEnvio.requestFocus();
        } else {
            txtCosteEnvio.setEditable(false);
            txtCosteEnvio.setText("$0");
        }
        recalcTotal();
    }//GEN-LAST:event_chkEnvioActionPerformed

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txtBuscarKeyTyped

    private void txtCosteEnvioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCosteEnvioKeyTyped
        if (!Character.isDigit(evt.getKeyChar()) && evt.getKeyChar() != '.') {
            evt.consume();
        }
        if (evt.getKeyChar() == '.' && txtCosteEnvio.getText().contains(".")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCosteEnvioKeyTyped

    private void txtCosteEnvioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCosteEnvioKeyReleased
        recalcTotal();
    }//GEN-LAST:event_txtCosteEnvioKeyReleased

    public void buscarDish() {
        if (!txtBuscar.getText().replace(" ", "").equals("")) {
            if (!new Dishes().getDishbyID(txtBuscar.getText()).equals("")) {
                addDishCarta(txtBuscar.getText());
                recalcTotal();
            } else {
                JOptionPane.showMessageDialog(null, "<html><h1>ESTE PRODUCTO NO EXISTE</h1>"
                        + "<font SIZE=5><p>Clic para cerrar </font></html>", "¡Advertencia!", JOptionPane.INFORMATION_MESSAGE);
            }
            txtBuscar.setText("");
            txtBuscar.requestFocus();
        }
    }

    public void impComanda() {
        try {
            if (tableData.getRowCount() > 0) {
                RePrint rp = new RePrint(null, true);
                rp.asign_datos();
                rp.show();
                if (rp.isResp()) {
                    String envio = "";
                    if (txtCosteEnvio.getText().equals("")) {
                        envio = 0 + "";
                    } else {
                        envio = new FormatoNumerico().RemoverFormatoMoneda(txtCosteEnvio.getText());
                    }
                    String ticketData = "COMANDA " + lblIDReg.getText() + ","
                            + getUser() + ","
                            + new FormatoNumerico().RemoverFormatoMoneda(txtTotal.getText()) + ","
                            + lblMesa.getText() + ","
                            + lblIDCliente.getText() + ","
                            + envio + ",";
                    String[] productos = getTableDishes();
                    new PDFDocs().ImprimirComanda(ticketData.split(","), productos, rp.getImpresora(), rp.getTamanoHoja());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
    }

    public void impOrden() {
        try {
            if (tableData.getRowCount() > 0) {
                RePrint rp = new RePrint(null, true);
                rp.asign_datos();
                rp.show();
                if (rp.isResp()) {
                    String envio = "";
                    if (txtCosteEnvio.getText().equals("")) {
                        envio = 0 + "";
                    } else {
                        envio = new FormatoNumerico().RemoverFormatoMoneda(txtCosteEnvio.getText());
                    }

                    String ticketData = lblIDReg.getText() + ","
                            + getUser() + ","
                            + new FormatoNumerico().RemoverFormatoMoneda(txtTotal.getText()) + ","
                            + lblMesa.getText() + ","
                            + lblIDCliente.getText() + ","
                            + envio + ",";
                    String[] productos = getTableDishes();
                    new PDFDocs().ImprimirNoradeVenta(ticketData.split(","), productos, rp.getImpresora(), rp.getTamanoHoja());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
    }

    public String[] getTableDishes() {
        String[] resp = new String[tableData.getRowCount()];
        for (int i = 0; i < resp.length; i++) {
            String temp = tableData.getValueAt(i, 2) + "," + tableData.getValueAt(i, 3) + "," + tableData.getValueAt(i, 4) + "," + tableData.getValueAt(i, 5) + ",";
            resp[i] = temp;
        }
        return resp;
    }

    public void CobrarOrden() {
        try {
            if (!compCacillas()) {
                String nreg = lblIDReg.getText()
                        + "," + lblMesa.getText().replace(',', ' ')
                        + "," + new FormatoNumerico().RemoverFormatoMoneda(txtTotal.getText())
                        + "," + new FormatoNumerico().RemoverFormatoMoneda(txtCosteEnvio.getText())
                        + "," + "COBRADO"
                        + "," + lblIDCliente.getText()
                        + "," + new SimpleDateFormat("dd-MM-YYYY").format(new Date())
                        + "," + new SimpleDateFormat("hh:mm").format(new Date()) + ",";
                if (mostrarMensaje("COBRAR ORDEN", "DESEA COBRAR EL REGISTRO SELECCIONADO?", 0)) {
                    Cobrar c = new Cobrar(null, true);
                    c.asign_dat(txtTotal.getText());
                    c.show();
                    if (c.getState() == 1) {
                        if (btnAceptar.getText().equals("GUARDAR")) {
                            new Orders().insOrder(nreg);
                            saveSaledProducts();
                        } else {
                            new Orders().updateOrder(nreg);
                            UpdateSaledProducts();
                        }
                        String ticketData = lblIDReg.getText() + ","
                                + getUser() + ","
                                + new FormatoNumerico().RemoverFormatoMoneda(txtTotal.getText()) + ","
                                + lblMesa.getText() + ","
                                + lblIDCliente.getText() + ","
                                + new FormatoNumerico().RemoverFormatoMoneda(txtCosteEnvio.getText()) + ",";
                        String[] productos = getTableDishes();
                        new PDFDocs().ImprimirNoradeVenta(ticketData.split(","), productos, c.getImpresora(), c.getTamanoHoja());
                        this.dispose();
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e);
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
            java.util.logging.Logger.getLogger(frameOrders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frameOrders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frameOrders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frameOrders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frameOrders().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCarta;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnCobrar;
    private javax.swing.JButton btnImpComanda;
    private javax.swing.JButton btnImpOrden;
    private javax.swing.JButton btnSelClient;
    private javax.swing.JCheckBox chkEnvio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblIDCliente;
    private javax.swing.JLabel lblIDReg;
    private javax.swing.JLabel lblMesa;
    private javax.swing.JTable tableData;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCosteEnvio;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextArea txtaClient;
    // End of variables declaration//GEN-END:variables
}
