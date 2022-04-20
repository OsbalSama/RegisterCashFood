/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames.adminControls;

import Classes.*;
import Dialogs.RePrint;
import Dialogs.viewCorteCaja;
import Dialogs.viewOrder;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author terra
 */
public final class AllOrders extends javax.swing.JFrame {

    /**
     * Creates new form AllUsers
     */
    DefaultTableModel modelo;
    String user;
    List<String> BUSQUEDA = new ArrayList<>();

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setDates() {
        try {
            Date f1 = new Date();
            dateFin.setDate(f1);
            dateInicio.setDate(f1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadTabla() {
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int filas, int columnas) {
                return columnas < 0;
            }
        };
        modelo.addColumn("Id Orden");
        modelo.addColumn("Mesa");
        modelo.addColumn("Total");
        modelo.addColumn("Envio");
        modelo.addColumn("Estado");
        modelo.addColumn("Cliente");
        modelo.addColumn("Fecha");
        modelo.addColumn("Hora");
        tblDatos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent Mouse_evt) {
                JTable table = (JTable) Mouse_evt.getSource();
                Point point = Mouse_evt.getPoint();
                int row = table.rowAtPoint(point);
                if (Mouse_evt.getClickCount() == 2) {
                    try {
                        String id = tblDatos.getValueAt(tblDatos.getSelectedRow(), 0).toString();
                        viewOrder ui = new viewOrder(null, true);
                        ui.loadOrder(id);
                        ui.show();
                        modelo.setRowCount(0);
                        AplicarCombos();
                    } catch (Exception e) {
//                        JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (Mouse_evt.getClickCount() == 1) {
                    if (Mouse_evt.getButton() == MouseEvent.BUTTON3) {
                        int r = tblDatos.rowAtPoint(Mouse_evt.getPoint());
                        if (r >= 0 && r < tblDatos.getRowCount()) {
                            tblDatos.setRowSelectionInterval(r, r);
                        }
                        popMenuOrdenes.show(Mouse_evt.getComponent(), Mouse_evt.getX(), Mouse_evt.getY());
                    }
                }
            }
        });
        tblDatos.setModel(modelo);
        tblDatos.setRowHeight(70);
    }

    public AllOrders() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("Registro de Ordenes");
        setIconImage(new Configurations().getImageIcon().getImage());
        this.setLocationRelativeTo(null);
        btn_cerrar.requestFocus();
        loadTabla();
        cmbFiltro.addActionListener((ActionEvent event) -> {
            AplicarCombos();
        });
    }

    public void loadByCats(String filtro) {
        modelo.setRowCount(0);
        if (!BUSQUEDA.isEmpty() && modelo != null) {
            for (int i = 0; i < BUSQUEDA.size(); i++) {
                String[] temp = BUSQUEDA.get(i).split(",");
                if (temp[4].equals(filtro)) {
                    modelo.addRow(BUSQUEDA.get(i).split(","));
                }
            }
        }
    }

    public void AplicarCombos() {

        if (cmbFiltro.getSelectedIndex() > 0) {
            String busqueda = cmbFiltro.getSelectedItem().toString();
            loadByCats(busqueda);
        } else {
            loadDefaults();
        }
    }

    public void FiltroSoloPista(String Pista) {
        try {
            modelo.setRowCount(0);
            List datos = new Orders().getOrderbyState(Pista);
            if (!datos.isEmpty()) {
                for (int i = 0; i < datos.size(); i++) {
                    modelo.addRow(datos.get(i).toString().split(","));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error 1: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
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

        popMenuOrdenes = new javax.swing.JPopupMenu();
        MenuReImprimir = new javax.swing.JMenuItem();
        jLabel2 = new javax.swing.JLabel();
        btn_cerrar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDatos = new javax.swing.JTable();
        cmbFiltro = new javax.swing.JComboBox<>();
        btnResults = new javax.swing.JButton();
        dateFin = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        dateInicio = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        MenuReImprimir.setLabel("Imprimir Ticket");
        MenuReImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuReImprimirActionPerformed(evt);
            }
        });
        popMenuOrdenes.add(MenuReImprimir);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Mostrar todas las cuentas");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setText("ORDENES REGISTRADAS");

        btn_cerrar.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btn_cerrar.setText("CERRAR");
        btn_cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cerrarActionPerformed(evt);
            }
        });

        tblDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblDatos.setRowHeight(70);
        jScrollPane2.setViewportView(tblDatos);

        cmbFiltro.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        cmbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODOS", "COBRADO", "PENDIENTE", "CANCELADO" }));

        btnResults.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnResults.setText("VER CORTE Z");
        btnResults.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResultsActionPerformed(evt);
            }
        });

        dateFin.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        dateFin.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateFinPropertyChange(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel4.setText("DE");

        dateInicio.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        dateInicio.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateInicioPropertyChange(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("A");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel6.setText("ESTADO");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(dateFin, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnResults)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_cerrar)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateFin, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_cerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnResults, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_cerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_cerrarActionPerformed

    private void btnResultsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResultsActionPerformed
        newResults();
    }//GEN-LAST:event_btnResultsActionPerformed

    private void MenuReImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuReImprimirActionPerformed
        try {
            String ruta = "docs\\Tickets_Compras\\" + tblDatos.getValueAt(tblDatos.getSelectedRow(), 0).toString() + ".pdf";
            File F = new File(ruta);
            if (F.exists()) {
                RePrint ri = new RePrint(null, true);
                ri.asign_datos();
                ri.show();
                if (ri.isResp()) {
                    new PDFDocs().PrintDocument(ruta, ri.getImpresora());
                }
            } else {
                JOptionPane.showMessageDialog(null, "El archivo no existe, no se puede reimprimir: ", "¡Precaucion!", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_MenuReImprimirActionPerformed

    private void dateInicioPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateInicioPropertyChange
        BuscarRegistros();
    }//GEN-LAST:event_dateInicioPropertyChange

    private void dateFinPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateFinPropertyChange
        BuscarRegistros();
    }//GEN-LAST:event_dateFinPropertyChange

    public void newResults() {
        if (tblDatos.getRowCount() > 0) {
            viewCorteCaja ner = new viewCorteCaja(this, true);
            String data = getUser() + ","
                    + new SimpleDateFormat("dd-MM-YYYY").format(new Date()) + ","
                    + new SimpleDateFormat("HH:mm").format(new Date()) + ","
                    + new SimpleDateFormat("dd-MM-YYYY").format(dateFin.getDate()) + ",";
            ner.setData(data.split(","), getDatosTabla());
            if (dateFin.getDate().compareTo(dateInicio.getDate()) > 0) {
                ner.setDataRange("ORDENES DEL -->" + new SimpleDateFormat("dd-MM-YYYY").format(dateInicio.getDate()) + "    AL -->" + new SimpleDateFormat("dd-MM-YYYY").format(dateFin.getDate()));
            } else if (dateFin.getDate().compareTo(dateInicio.getDate()) < 0) {
                ner.setDataRange("ORDENES DEL -->" + new SimpleDateFormat("dd-MM-YYYY").format(dateFin.getDate()) + "   AL -->" + new SimpleDateFormat("dd-MM-YYYY").format(dateInicio.getDate()));
            } else {
                ner.setDataRange("ORDENES DEL DIA -->" + new SimpleDateFormat("dd-MM-YYYY").format(dateFin.getDate()));
            }
            ner.show();
        }
    }

    public List<String> getDatesBetweenTwoMore(Date dateIn, Date dateEnd) {
        List<String> resp = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(dateIn);
        while (calendar.getTime().before(dateEnd) || calendar.getTime().equals(dateEnd)) {
            Date result = calendar.getTime();
            resp.add(new SimpleDateFormat("dd-MM-YYYY").format(result));
            calendar.add(Calendar.DATE, 1);
        }
        return resp;
    }

    public void inicialize() {
        setDates();
        BuscarRegistros();
    }

    public void getVentasHoy() {
        if (modelo != null) {
            modelo.setRowCount(0);
        }
        List database = new Orders().getDBOrdersByDate(new SimpleDateFormat("dd-MM-YYYY").format(dateFin.getDate()));
        if (!database.isEmpty()) {
            for (int i = 0; i < database.size(); i++) {
                modelo.addRow(database.get(i).toString().split(","));
            }
        }
    }

    public void loadDefaults() {
        modelo.setRowCount(0);
        if (!BUSQUEDA.isEmpty() && modelo != null) {
            for (int i = 0; i < BUSQUEDA.size(); i++) {
                modelo.addRow(BUSQUEDA.get(i).split(","));
            }
        }
    }

    public void BuscarRegistros() {
        try {
            buscarDatos();
            if (cmbFiltro.getSelectedIndex() > 0) {
                if (!BUSQUEDA.isEmpty() && modelo != null) {
                    modelo.setRowCount(0);
                    for (int i = 0; i < BUSQUEDA.size(); i++) {
                        if (cmbFiltro.getSelectedItem().toString().equals(BUSQUEDA.get(i).split(",")[3])) {
                            modelo.addRow(BUSQUEDA.get(i).split(","));
                        }
                    }
                }
            } else {
                if (!BUSQUEDA.isEmpty() && modelo != null) {
                    modelo.setRowCount(0);
                    for (int i = 0; i < BUSQUEDA.size(); i++) {
                        modelo.addRow(BUSQUEDA.get(i).split(","));
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void buscarDatos() {
        if ((dateFin.getDate() != null && dateInicio.getDate() != null)) {
            if (dateFin.getDate().compareTo(dateInicio.getDate()) > 0) {
                List<String> dates = getDatesBetweenTwoMore(dateInicio.getDate(), dateFin.getDate());
                if (!dates.isEmpty()) {
                    BUSQUEDA = new Orders().getDBOrdersByDateRange(dates);

                }
            } else if (dateFin.getDate().compareTo(dateInicio.getDate()) < 0) {
                List<String> dates = getDatesBetweenTwoMore(dateFin.getDate(), dateInicio.getDate());
                if (!dates.isEmpty()) {
                    BUSQUEDA = new Orders().getDBOrdersByDateRange(dates);
                }
            } else if (dateFin.getDate().compareTo(dateInicio.getDate()) == 0) {
                BUSQUEDA = new Orders().getDBOrdersByDate(new SimpleDateFormat("dd-MM-YYYY").format(dateFin.getDate()));
            }
        }
    }

    public String[] getDatosTabla() {
        String[] resp = new String[tblDatos.getRowCount()];
        for (int i = 0; i < tblDatos.getRowCount(); i++) {
            String temp = "";
            for (int j = 0; j < tblDatos.getColumnCount(); j++) {
                temp += tblDatos.getValueAt(i, j) + ",";
            }
            resp[i] = temp;
        }
        return resp;
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
            java.util.logging.Logger.getLogger(AllOrders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AllOrders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AllOrders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AllOrders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AllOrders().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem MenuReImprimir;
    private javax.swing.JButton btnResults;
    private javax.swing.JButton btn_cerrar;
    private javax.swing.JComboBox<String> cmbFiltro;
    private com.toedter.calendar.JDateChooser dateFin;
    private com.toedter.calendar.JDateChooser dateInicio;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu popMenuOrdenes;
    private javax.swing.JTable tblDatos;
    // End of variables declaration//GEN-END:variables
}
