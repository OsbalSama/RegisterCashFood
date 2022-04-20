/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames.generalControls;

import Classes.*;
import java.awt.Image;
import java.io.File;
import java.net.ServerSocket;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author terra
 */
public class Loading extends javax.swing.JFrame {

    /**
     * Creates new form ld
     */
    private static ServerSocket SERVER_SOCKET;
    int ServerSocket = 1304;

    public Loading() {
        try {
            this.setUndecorated(true);
            initComponents();
            setIconImage(new Configurations().getImageIcon().getImage());
            cargar_logo();
            this.setLocationRelativeTo(null);
            miHilo.start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    Thread miHilo = new Thread() {
        @Override
        public void run() {
            try {
                temporizador();
                SERVER_SOCKET = new ServerSocket(ServerSocket);
//                new Configurations().resetTryalDays();
//                new Configurations().resetSerial();                
                if (new CheckUpdate().AlreadyInstalled()) {
                    startSystem();
                } else {
                    new CheckUpdate().newInstallSystem();
                    startSystem();
                }
            } catch (java.net.BindException e) {
                JOptionPane.showMessageDialog(null, "<html><h1>--SISTEMA YA ABIERTO--</h1><font SIZE=5><p>Clic para cerrar<p></font></html>", "¡Ya abierto!", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
    };

    public void startSystem() {
        if (new Configurations().isActivated() && !new Configurations().isResetApp()) {
            dispose();
            Login cs = new Login();
            cs.show();
        } else if (new Configurations().isActivated() && new Configurations().isResetApp()) {
            ConfigApp ids = new ConfigApp();
            ids.InicioSistema();
            dispose();
        } else if (!new Configurations().isActivated() && new Configurations().getTryalDays() < 30 && !new Configurations().isResetApp()) {
            int resp = JOptionPane.showConfirmDialog(null, "<html><h1>¡VERSIÓN DE PRUEBA EN EJECUCIÓN!</h1><p><font SIZE=5><center>Ingrese el serial de activación para desbloquear los<p>beneficios de forma permanente</center></font><p><div align='right'><h3>" + (30 - new Configurations().getTryalDays()) + "/30 días disponibles</h3></div><p><font SIZE=5><p>¿Desea activar ahora?</font></html>", "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                new ConfigApp().activarApp();
                dispose();
                Login cs = new Login();
                cs.show();
            } else {
                dispose();
                Login cs = new Login();
                cs.show();
            }
        } else if (!new Configurations().isActivated() && new Configurations().getTryalDays() < 30 && new Configurations().isResetApp()) {
            int resp = JOptionPane.showConfirmDialog(null, "<html><h1>¡VERSIÓN DE PRUEBA EN EJECUCIÓN!</h1><p><font SIZE=5><center>Ingrese el serial de activación para desbloquear los<p>beneficios de forma permanente</center></font><p><div align='right'><h3>" + (30 - new Configurations().getTryalDays()) + "/30 días disponibles</h3></div><p><font SIZE=5><p>¿Desea activar ahora?</font></html>", "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                new ConfigApp().activarApp();
                ConfigApp ids = new ConfigApp();
                ids.InicioSistema();
                dispose();
            } else {
                ConfigApp ids = new ConfigApp();
                ids.InicioSistema();
                dispose();
            }
        } else {
            int resp = JOptionPane.showConfirmDialog(null, "<html><h1>¡VERSIÓN DE PRUEBA EN EJECUCIÓN!</h1><p><font SIZE=5><center>Ingrese el serial de activación para desbloquear los<p>beneficios de forma permanente</center></font><p><div align='right'><h3>" + (30 - new Configurations().getTryalDays()) + "/30 días disponibles</h3></div><p><font SIZE=5><p>¿Desea activar ahora?</font></html>", "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                new ConfigApp().activarApp();
                dispose();
                Login cs = new Login();
                cs.show();
            } else {
                System.exit(0);
            }
        }
    }

    public void cargar_logo() {
        try {
            File f = new File("iconos\\ldng.jpg");
            Image img = ImageIO.read(f);
            ldng.setIcon(new ImageIcon(img.getScaledInstance(ldng.getWidth(), ldng.getHeight(), Image.SCALE_DEFAULT)));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void temporizador() throws InterruptedException {
        for (int j = 2; j >= 0; j--) {
            Thread.sleep(990);
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

        ldng = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ldng, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ldng, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(Loading.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Loading.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Loading.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Loading.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Loading().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ldng;
    // End of variables declaration//GEN-END:variables
}
