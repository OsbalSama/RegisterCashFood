/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author ATENEA
 */
public class CheckUpdate {

//    configurations -	DBS_0001
//    db_categorias - 	DBS_0002
//    db_clientes -	DBS_0003
//    db_dishes - 	DBS_0004
//    db_edores -	DBS_0005
//    db_empresa - 	DBS_0006
//    db_orders - 	DBS_0007
//    db_productos_vendidos =DBS_0008
//    db_sesiones - 	DBS_0009
//    db_usuarios - 	DBS_0010
    File configurations = new File("source\\DBS_0001.dat");
    File db_categorias = new File("source\\DBS_0002.dat");
    File db_clientes = new File("source\\DBS_0003.dat");
    File db_dishes = new File("source\\DBS_0004.dat");
    File db_edores = new File("source\\DBS_0005.dat");
    File db_empresa = new File("source\\DBS_0006.dat");
    File db_orders = new File("source\\DBS_0007.dat");
    File db_productos_vendidos = new File("source\\DBS_0008.dat");
    File db_sesiones = new File("source\\DBS_0009.dat");
    File db_usuarios = new File("source\\DBS_0010.dat");
    File carpetaImagenes = new File("source\\images\\");

    File EdoRes = new File("docs\\EdoRes\\");    
    File temp = new File("docs\\temp\\");
    File Tickets_Compras = new File("docs\\Tickets_Compras\\");

    public boolean AlreadyInstalled() {
        boolean resp = false;
        try {
            if (db_categorias.exists() && db_edores.exists() && db_productos_vendidos.exists() && db_sesiones.exists()
                    && db_usuarios.exists()  && configurations.exists()) {
                resp = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public void newInstallSystem() {
        try {
            createDataBaseFiles();
            createdbEmpresa();
            createFormatoExcelFile();
            createOtherFiles();
            createConfigurationsFile();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void copiarnonImageDish() {
        try {
            String rutaDestino = "source\\images\\nonImage.png";
            new File(rutaDestino).createNewFile();
            Path destino = Paths.get(rutaDestino);
            String rutaOrigen = "iconos\\nonImage.png";
            Path origen = Paths.get(rutaOrigen);
            Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void createOtherFiles() {
        try {
            new File("docs\\temp\\").mkdir();
            new File("docs\\Tickets_Compras\\").mkdir();
            new File("docs\\EdoRes\\").mkdir();
            new File("source\\images\\").mkdir();
            copiarnonImageDish();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void createFormatoExcelFile() {
        try {
            new File("docs\\").mkdir();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void createDataBaseFiles() {
        try {
            new File("source\\").mkdir();
            db_categorias.createNewFile();
            db_edores.createNewFile();
            db_productos_vendidos.createNewFile();
            db_sesiones.createNewFile();
            db_usuarios.createNewFile();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void createConfigurationsFile() {
        try {
            configurations.createNewFile();
            List<String> DatosConfig = new ArrayList();
            DatosConfig.add("desactivado,default,default@gmail.com,VERSION DE PRUEBA,0,");
            DatosConfig.add("No Imprimir,2,TERMINOS Y CONDICIONES,");
            DatosConfig.add("1,");
            FileWriter save = new FileWriter(configurations);
            BufferedWriter bw = new BufferedWriter(save);
            for (int i = 0; i < DatosConfig.size(); i++) {
                bw.write(new Encryptor().EncryptData(DatosConfig.get(i)));
                bw.newLine();
            }
            bw.close();
            save.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void createdbEmpresa() {
        try {
            db_empresa.createNewFile();
            FileWriter save = new FileWriter(db_empresa);
            BufferedWriter bw = new BufferedWriter(save);
            bw.write(new Encryptor().EncryptData("123456,EMPRESA,SIN RFC,ENCARGADO,ENRIQUE SEGOBIANO,231-174-1481,"));
            bw.close();
            save.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

}
