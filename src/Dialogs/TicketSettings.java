/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialogs;

import Classes.Configurations;
import Classes.FormatoNumerico;
import Classes.PDFDocs;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author OSBAL
 */
public class TicketSettings extends javax.swing.JDialog {

    /**
     * Creates new form Opciones_Caja
     */
    boolean ok = false;
    int continuar;

    public boolean isOk() {
        return ok;
    }

    public TicketSettings(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        txta_comment.setTransferHandler(null);
        this.setTitle("Configurar Nota de Venta");
        combo_printers.requestFocus();
        txta_comment.setLineWrap(true);
        this.setLocationRelativeTo(null);
    }

    public void asign_datos() {
        cargar_impresoras();
        cargar_imagen(cmbSizes.getSelectedItem().toString());
    }

    public void cargar_datos_antiguos() {
        cargar_impresoras();
        cargar_imagen(cmbSizes.getSelectedItem().toString());
        String[] datos = new Configurations().getTicketConfigs();        
        switch (datos[1]) {
            case "3":
                cmbSizes.setSelectedIndex(0);
                break;
            case "2":
                cmbSizes.setSelectedIndex(1);
                break;
            case "1":
                cmbSizes.setSelectedIndex(2);
                break;
        }
        txta_comment.setText(datos[2]);
    }

    public int getContinuar() {
        return continuar;
    }

    public void cargar_imagen(String tamaño) {
        try {
            switch (tamaño) {
                case "Hoja Carta":
                    ImageIcon fot = new ImageIcon(new File("iconos\\PaperSizes\\LETTER.jpg").getAbsolutePath());
                    Icon icono = new ImageIcon(fot.getImage().getScaledInstance(150, lbl_hoja.getHeight(), Image.SCALE_AREA_AVERAGING));
                    lbl_hoja.setIcon(icono);
                    break;
                case "Hoja 80 mm":
                    fot = new ImageIcon(new File("iconos\\PaperSizes\\8MM.jpg").getAbsolutePath());
                    icono = new ImageIcon(fot.getImage().getScaledInstance(100, lbl_hoja.getHeight(), Image.SCALE_SMOOTH));
                    lbl_hoja.setIcon(icono);
                    break;
                case "Hoja 50 mm":
                    fot = new ImageIcon(new File("iconos\\PaperSizes\\5MM.jpg").getAbsolutePath());
                    icono = new ImageIcon(fot.getImage().getScaledInstance(60, lbl_hoja.getHeight(), Image.SCALE_REPLICATE));
                    lbl_hoja.setIcon(icono);
                    break;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void asign_imp_pred(String imp) {
        PrintService[] ps = PrintServiceLookup.lookupPrintServices(null, null);
        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

    }

    public void cargar_impresoras() {
        combo_printers.removeAllItems();
        combo_printers.addItem("Imprimir como PDF");
        combo_printers.addItem("No Imprimir");
        PrintService[] ps = PrintServiceLookup.lookupPrintServices(null, null);
        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
        for (PrintService p : ps) {
            combo_printers.addItem(p.getName());
            if ((p.getName().equals(defaultPrintService.getName()))) {
                combo_printers.setSelectedItem(p.getName());
            }
        }
        asignar_defaultprinter();
    }

    public void asignar_defaultprinter() {
        String[] dticket = new Configurations().getTicketConfigs();
        for (int i = 0; i < combo_printers.getItemCount(); i++) {
            String impresora_posicion = combo_printers.getItemAt(i);
            if (impresora_posicion.equals(dticket[0])) {
                combo_printers.setSelectedIndex(i);
                break;
            }
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

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lbl_hoja = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmbSizes = new javax.swing.JComboBox<>();
        btnGuardar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txta_comment = new javax.swing.JTextArea();
        btnCerrar = new javax.swing.JButton();
        combo_printers = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        lblTestPrinter = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Opciones de Register Cash");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel3.setText("Impresora asignada");

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lbl_hoja.setForeground(new java.awt.Color(255, 255, 255));
        lbl_hoja.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_hoja, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_hoja, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel6.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel6.setText("Tamaño de hoja...");

        cmbSizes.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbSizes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hoja 50 mm", "Hoja 80 mm", "Hoja Carta" }));
        cmbSizes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSizesItemStateChanged(evt);
            }
        });
        cmbSizes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSizesActionPerformed(evt);
            }
        });

        btnGuardar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel7.setText("Terminos y Condiciones");

        txta_comment.setColumns(20);
        txta_comment.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txta_comment.setRows(5);
        txta_comment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txta_commentKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(txta_comment);

        btnCerrar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnCerrar.setText("CANCELAR");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        combo_printers.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "sin items" }));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CONFIGURA TU NOTA DE VENTA");

        lblTestPrinter.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblTestPrinter.setText("Pagina de prueba");
        lblTestPrinter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTestPrinterMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblTestPrinterMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblTestPrinterMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(btnGuardar)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCerrar))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(combo_printers, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel6)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(cmbSizes, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel3)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(lblTestPrinter))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(lblTestPrinter))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combo_printers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cmbSizes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbSizesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSizesActionPerformed
//        cargar_imagen(combo_sizes.getSelectedItem().toString());
    }//GEN-LAST:event_cmbSizesActionPerformed

    public boolean compCacillas() {
        boolean resp = false;
        if ("".equals(txta_comment.getText().replace(" ", ""))) {
            txta_comment.requestFocus();
            resp = true;
        }
        return resp;
    }

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        Save();
    }//GEN-LAST:event_btnGuardarActionPerformed

    public void Save() {
        if (!compCacillas()) {
            int dialogButton = JOptionPane.showConfirmDialog(null, "<html><h1>DESEA ACTUALIZAR LA INFORMACION ACTUAL?  </h1><font SIZE=5><p> Verifique bien sus datos antes de continuar…</font></html>", "", JOptionPane.OK_CANCEL_OPTION);
            if (dialogButton == JOptionPane.OK_OPTION) {
                String impresora = combo_printers.getSelectedItem().toString();
                int t_imp;
                switch (cmbSizes.getSelectedItem().toString()) {
                    case "Hoja Carta":
                        t_imp = 1;
                        break;
                    case "Hoja 80 mm":
                        t_imp = 2;
                        break;
                    case "Hoja 50 mm":
                        t_imp = 3;
                        break;
                    default:
                        t_imp = 0;
                        break;
                }
                String coment = txta_comment.getText().replace(",", "");
                String nuevo_reg = impresora.replace(',', ' ') + "," + t_imp + "," + coment.replace(',', ' ') + ",";
                new Configurations().setTicketConfigs(nuevo_reg);
                JOptionPane.showMessageDialog(null, "<html><h1>DATOS GUARDADOS</h1><font SIZE=5><p> Datos guardados correctamente</font></html>", "", JOptionPane.INFORMATION_MESSAGE);
                ok = true;
                this.dispose();
            }
        }
    }

    private void cmbSizesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSizesItemStateChanged
        cargar_imagen(cmbSizes.getSelectedItem().toString());
    }//GEN-LAST:event_cmbSizesItemStateChanged

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        continuar = 2;
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void txta_commentKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txta_commentKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB || evt.getKeyCode() == KeyEvent.VK_ENTER) {
            evt.consume();
            Save();
        }
    }//GEN-LAST:event_txta_commentKeyPressed

    private void lblTestPrinterMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTestPrinterMouseEntered
        lblTestPrinter.setForeground(Color.BLUE);
    }//GEN-LAST:event_lblTestPrinterMouseEntered

    private void lblTestPrinterMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTestPrinterMouseExited
        lblTestPrinter.setForeground(Color.BLACK);
    }//GEN-LAST:event_lblTestPrinterMouseExited

    public String[] getTestTableDishes() {
        String[] resp = new String[10];
        for (int i = 0; i < resp.length; i++) {
            //String temp1 = tableData.getValueAt(i, 1) + "," + tableData.getValueAt(i, 2) + "," + tableData.getValueAt(i, 3) + "," + tableData.getValueAt(i, 4) + ",";
            String temp = "PRODUCTO " + (i + 1) + ",9999.99,1,9999.99";
            resp[i] = temp;
        }
        return resp;
    }

    private void lblTestPrinterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTestPrinterMouseClicked
        try {
            lblTestPrinter.setForeground(Color.YELLOW);
            String impresora = combo_printers.getSelectedItem().toString();
            if (!impresora.equals("No Imprimir")) {
                String[] productos = getTestTableDishes();
                String ticketData = "ID_SALETICKET,"
                        + "USER,"
                        + new FormatoNumerico().RemoverFormatoNumerico("99999.99") + ","
                        + "MESA PRUEBA,"
                        + "ID_CLIENTE,"
                        + "99999.99";
                new PDFDocs().ImprimirNotadePrueba(ticketData.split(","), productos, impresora, cmbSizes.getSelectedIndex());
            }
            lblTestPrinter.setForeground(Color.BLACK);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_lblTestPrinterMouseClicked

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
            java.util.logging.Logger.getLogger(TicketSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TicketSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TicketSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TicketSettings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TicketSettings dialog = new TicketSettings(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbSizes;
    private javax.swing.JComboBox<String> combo_printers;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTestPrinter;
    private javax.swing.JLabel lbl_hoja;
    private javax.swing.JTextArea txta_comment;
    // End of variables declaration//GEN-END:variables
}