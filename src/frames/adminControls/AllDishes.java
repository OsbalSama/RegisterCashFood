/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames.adminControls;

import Classes.CatProvClass;
import Classes.Configurations;
import Classes.Dishes;
import Dialogs.newDish;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author OSBAL
 */
public class AllDishes extends javax.swing.JFrame {

    /**
     * Creates new form AllDishes
     */
    DefaultTableModel modelo;

    public AllDishes() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setIconImage(new Configurations().getImageIcon().getImage());
        this.setLocationRelativeTo(null);
        btn_cerrar.requestFocus();
        loadTabla();
        cmbFiltro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                newActionPerformed();
            }
        });
    }
    
    public void loadTabla(){
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
        modelo.addColumn("Codigo");
        modelo.addColumn("Titulo");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Categoria");
        modelo.addColumn("Precio");
        modelo.addColumn("Fecha de registro");
        tblDatos.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent Mouse_evt) {
                JTable table = (JTable) Mouse_evt.getSource();
                Point point = Mouse_evt.getPoint();
                int row = table.rowAtPoint(point);
                if (Mouse_evt.getClickCount() == 2) {
                    try {
                        String id = tblDatos.getValueAt(tblDatos.getSelectedRow(), 0).toString();
                        newDish ui = new newDish(null, true);
                        ui.updateDish(id);
                        ui.show();
                        modelo.setRowCount(0);
                        newActionPerformed();
                    } catch (Exception e) {
//                        JOptionPane.showMessageDialog(null, "Error: " + e, "??ERROR!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        tblDatos.setModel(modelo);
    }
    

    public void loadCats() {
        List Cats = new CatProvClass().getDBCat();
        cmbFiltro.removeAllItems();
        cmbFiltro.addItem("TODOS");
        if (!Cats.isEmpty()) {
            for (int i = 0; i < Cats.size(); i++) {
                cmbFiltro.addItem(Cats.get(i).toString());
            }
        } else {
            cmbFiltro.addItem("SIN CATEGORIAS");
        }
    }

    public void newActionPerformed() {
        if (cmbFiltro.getItemCount() > 0) {
            if (cmbFiltro.getSelectedIndex() > 0) {
                aplicarFiltro(cmbFiltro.getSelectedItem().toString());
            } else if (cmbFiltro.getSelectedIndex() == 0){
                showAllDishes();
            }
        }
    }

    public void aplicarFiltro(String Pista) {
        try {
            modelo.setRowCount(0);
            List datos = new Dishes().getDishesByType(Pista);
            if (!datos.isEmpty()) {
                for (int i = 0; i < datos.size(); i++) {
                    modelo.addRow(datos.get(i).toString().split(","));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error 1: " + e, "??ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showAllDishes() {
        modelo.setRowCount(0);
        tblDatos.setRowHeight(70);
        List<String> datos = new Dishes().getDBDishes();
        try {
            if (!datos.isEmpty()) {
                for (int i = 0; i < datos.size(); i++) {
                    modelo.addRow(datos.get(i).split(","));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "??ERROR!", JOptionPane.ERROR_MESSAGE);
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

        jLabel2 = new javax.swing.JLabel();
        btn_cerrar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDatos = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        cmbFiltro = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setText("TODOS LOS PLATILLOS");

        btn_cerrar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btn_cerrar.setText("CERRAR");
        btn_cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cerrarActionPerformed(evt);
            }
        });

        tblDatos.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        tblDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblDatos.setRowHeight(70);
        jScrollPane2.setViewportView(tblDatos);

        jButton1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton1.setText("NUEVO PLATILLO");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        cmbFiltro.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        cmbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODOS", "COMIDAS", "BEBIDAS", "POSTRES", "OTROS" }));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setText("GRUPO");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cerrar))
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_cerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_cerrarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        newDish nd = new newDish(this, true);
        nd.InsertDish();
        nd.show();
        newActionPerformed();
    }//GEN-LAST:event_jButton1ActionPerformed
    public void refrershTable() {
        modelo.setRowCount(0);
        loadCats();
        showAllDishes();
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
            java.util.logging.Logger.getLogger(AllDishes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AllDishes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AllDishes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AllDishes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AllDishes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cerrar;
    private javax.swing.JComboBox<String> cmbFiltro;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblDatos;
    // End of variables declaration//GEN-END:variables
}
