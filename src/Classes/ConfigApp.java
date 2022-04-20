/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Dialogs.DatEmpresa1;
import Dialogs.DatEmpresa2;
import Dialogs.TicketSettings;
import Dialogs.Serial;
import Dialogs.newUser;
import frames.generalControls.Login;
import javax.swing.JOptionPane;

/**
 *
 * @author OSBAL
 */
public class ConfigApp {

    boolean terminado = false;
    String nombreApp = "Register Cash Food";

    public String getNombreApp() {
        return nombreApp;
    }

    public boolean isTerminado() {
        return terminado;
    }

    public void setTerminado(boolean terminado) {
        this.terminado = terminado;
    }

    public void activarApp() {
        boolean continuar = false;
        while (continuar == false) {
            Serial vs = new Serial(null, true);
            while (vs.getResp() != 1) {
                vs = new Serial(null, true);
                vs.show();
            }
            continuar = true;
        }
        JOptionPane.showMessageDialog(null, "<html><h1>PRODUCTO ACTIVADO...</h1><font SIZE=5><p>¡Bienvenido a " + getNombreApp() + "!<p>Clic para continuar...</font></html>", "¡Bienvenido!", JOptionPane.WARNING_MESSAGE);
    }

    public void InicioSistema() {
        new Configurations().resetLogo();
        JOptionPane.showMessageDialog(null, "<html><h1>Bienvenido a " + getNombreApp() + "!</h1><br>"
                + "<center><font SIZE=5>Para comenzar a trabajar en este Software, es necesario establecer algunas <p>configuraciones importantes…</center></font></html>", "", JOptionPane.INFORMATION_MESSAGE);
        boolean continuar = false;
        new Usuarios().ClearDB();
        new Usuarios().addRootUser();
        while (continuar == false) {
            newUser iu = new newUser(null, true);
            iu.setDatFirstUse();
            iu.show();
            if (iu.isOk() == true) {
                continuar = true;
            } else if (iu.getContinuar() == 2) {
                int resp = JOptionPane.showConfirmDialog(null, "<html><h1>--TERMINAR PROCESO--</h1>"
                        + "<font SIZE=5><p>¿Seguro que desea cancelar?<p></font></html>", "¡Atencion!", JOptionPane.YES_NO_OPTION);
                if (resp == 0) {
                    System.exit(resp);
                }
            }
        }
        continuar = false;
        while (continuar == false) {
            DatEmpresa1 dt = new DatEmpresa1(null, true);
            dt.setDatFirstUse();
            dt.show();
            if (dt.getContinuar() == 1) {
                continuar = true;
            } else if (dt.getContinuar() == 2) {
                int resp = JOptionPane.showConfirmDialog(null, "<html><h1>--TERMINAR PROCESO--</h1>"
                        + "<font SIZE=5><p>¿Seguro que desea cancelar?<p></font></html>", "¡Atencion!", JOptionPane.YES_NO_OPTION);
                if (resp == 0) {
                    System.exit(resp);
                }
            }
        }
        DatEmpresa2 mdt = new DatEmpresa2(null, true);
        mdt.setDatFirstUse();
        mdt.asignar_datos();
        mdt.show();
        continuar = false;
        while (continuar == false) {
            TicketSettings oc = new TicketSettings(null, true);
            oc.setDefaultCloseOperation(0);
            oc.asign_datos();
            oc.show();
            if (oc.isOk() == true) {
                continuar = true;
            } else if (oc.getContinuar() == 2) {
                int resp = JOptionPane.showConfirmDialog(null, "<html><h1>--TERMINAR PROCESO--</h1>"
                        + "<font SIZE=5><p>¿Seguro que desea cancelar?<p></font></html>", "¡Atencion!", JOptionPane.YES_NO_OPTION);
                if (resp == 0) {
                    System.exit(resp);
                }
            }
        }
        //AQUI HAY QUE METER OTRO PROCESO SI HACE FALTA
        //continuar = false;
        //while (continuar == false) {
        //
        //}        
        new SaleProdcts().ClearDB();
        new CatProvClass().ClearDBCats();
        new Clients().ClearDB();
        new Dishes().ClearDB();
        new CorteZ().ClearDB();
        new Orders().ClearDB();
        new SaleProdcts().ClearDB();
        new Sesiones().ClearDB();
        new Configurations().disableResetApp();
        JOptionPane.showMessageDialog(null, "<html><h1>¡BIENVENIDO A " + getNombreApp().toUpperCase() + "!</h1><font SIZE=5>"
                + "<p>Todo listo... ahora si, COMENCEMOS!</font></html>", "¡BIENVENIDO!", JOptionPane.WARNING_MESSAGE);
        Login cs = new Login();
        cs.show();
    }

}
