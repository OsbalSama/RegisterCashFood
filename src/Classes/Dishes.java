/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author OSBAL
 */
public class Dishes {

    String archivo = "source\\DBS_0004.dat";
    String RutaImagenes = "source\\images\\";

    public String getRutaImagenes() {
        return RutaImagenes;
    }

    public void setRutaImagenes(String RutaImagenes) {
        this.RutaImagenes = RutaImagenes;
    }

    public String getArchivo() {
        return archivo;
    }

    public void ClearDB() {
        try {
            new File(getArchivo()).delete();
            new File(getArchivo()).createNewFile();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ImageIcon getDishIcon(String ref, int Width, int Height) {
        File f = new File(getRutaImagenes() + ref + ".png");
        Image newimg;
        if (f.exists()) {
            ImageIcon icon = new ImageIcon(f.getAbsolutePath());
            Image img = icon.getImage();
            newimg = img.getScaledInstance(Width, Height, java.awt.Image.SCALE_DEFAULT);
        } else {
            ImageIcon icon = new ImageIcon(getRutaImagenes()+"nonImage.png");
            Image img = icon.getImage();
            newimg = img.getScaledInstance(Width, Height, java.awt.Image.SCALE_DEFAULT);
        }
        return new ImageIcon(newimg);
    }

    public boolean existPhoto(String route){
        if (new File(route).exists()) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean addDishPhoto(String idDish) {
        boolean resp = false;
        try {
            JFileChooser chooser = new JFileChooser();            
            chooser.setFileFilter(new FileNameExtensionFilter("AImagenes PNG", "PNG", "png"));
            chooser.setAcceptAllFileFilterUsed(false);
            int retrival = chooser.showSaveDialog(null);
            if (retrival == JFileChooser.APPROVE_OPTION) {
                String rutaDestino = getRutaImagenes() + idDish + ".png";
                new File(rutaDestino).createNewFile();
                Path destino = Paths.get(rutaDestino);
                String rutaOrigen = chooser.getSelectedFile().getAbsolutePath();
                Path origen = Paths.get(rutaOrigen);
                Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
                resp = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar imagen: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public List getDishesByType(String Type) {
        List<String> resp = new ArrayList<String>();
        try {
            List<String> database = getDBDishes();
            if (database != null) {
                for (int i = 0; i < database.size(); i++) {
                    String[] temp = database.get(i).split(",");
                    if (Type.equals(temp[3])) {
                        resp.add(database.get(i));
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error 0: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public String getDishbyID(String id) {
        String resp = "";
        try {
            List<String> database = getDBDishes();
            if (!database.isEmpty()) {
                for (int i = 0; i < database.size(); i++) {
                    String[] temp = database.get(i).split(",");
                    if (id.equals(temp[0])) {
                        resp = database.get(i);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public List<String> getDBDishes() {
        List<String> resp = new ArrayList();
        try {
            String cadena;
            FileReader f = new FileReader(getArchivo());
            BufferedReader b = new BufferedReader(f);
            while ((cadena = b.readLine()) != null) {
                resp.add(new Encryptor().DecryptData(cadena));
            }
            b.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public String genID() {
        String id = "";
        try {
            for (int i = 0; i < 6; i++) {
                id += (int) (Math.random() * 10);
            }
            if (existID(id)) {
                genID();
            } else {
                return id;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public boolean existID(String id) {
        boolean resp = false;
        try {
            List<String> database = getDBDishes();
            if (!database.isEmpty()) {
                for (int i = 0; i < database.size(); i++) {
                    String[] sesion = database.get(i).split(",");
                    if (sesion[0].equals(id)) {
                        resp = true;
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public boolean InsDish(String nuevo_reg) {
        try {
            List<String> database = getDBDishes();
            if (database.isEmpty()) {
                FileWriter w = new FileWriter(this.getArchivo());
                BufferedWriter bw = new BufferedWriter(w);
                bw.write(new Encryptor().EncryptData(nuevo_reg));
                bw.newLine();
                bw.close();
            } else {
                FileWriter w = new FileWriter(this.getArchivo());
                BufferedWriter bw = new BufferedWriter(w);
                for (int i = 0; i < database.size(); i++) {
                    bw.write(new Encryptor().EncryptData(database.get(i)));
                    bw.newLine();
                }
                bw.write(new Encryptor().EncryptData(nuevo_reg));
                bw.newLine();
                bw.close();
                return true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public boolean updateDish(String nuevo_reg) {
        try {
            List<String> database = getDBDishes();
            FileWriter w = new FileWriter(this.getArchivo());
            BufferedWriter bw = new BufferedWriter(w);
            for (int i = 0; i < database.size(); i++) {
                String[] reg_ind = database.get(i).split(",");
                String[] nreg = nuevo_reg.split(",");
                if (reg_ind[0].equals(nreg[0])) {
                    bw.write(new Encryptor().EncryptData(nuevo_reg));
                    bw.newLine();
                } else {
                    bw.write(new Encryptor().EncryptData(database.get(i)));
                    bw.newLine();
                }
            }
            bw.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean delDish(String dishID) {
        try {
            List<String> allData = getDBDishes();
            BufferedWriter bw = new BufferedWriter(new FileWriter(this.getArchivo()));
            for (int i = 0; i < allData.size(); i++) {
                String[] usuario_db = allData.get(i).split(",");
                if (!dishID.equals(usuario_db[0])) {
                    bw.write(new Encryptor().EncryptData(allData.get(i)));
                    bw.newLine();
                }
            }
            bw.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

}
