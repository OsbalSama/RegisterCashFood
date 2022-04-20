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
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Viernes
 */
public class Configurations {

    //NEW database\\configurations:    
    //configurations[0] = desactivado,default,default@gmail.com,VERSION DE PRUEBA,25, (Datos Licencia)
    //configurations[1] = Imprimir como PDF,2,TERMINOS Y CONDICIONES, (Datos Ticket)   
    //configurations[2] = 0, (Reseteo)
    String archivo = "source\\DBS_0001.dat";

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }
    
    public void resetSerial(){
        String Registro = "desactivado,default,default@gmail.com,VERSION DE PRUEBA,0,";
        setLicData(archivo, archivo, archivo);
        try {
            setAccountData(Registro);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }   
    }
    

    public List<String> getDBConfig() {
        List<String> resp = new ArrayList<>();
        try {
            String cadena;
            FileReader f = new FileReader(getArchivo());
            BufferedReader b = new BufferedReader(f);
            while ((cadena = b.readLine()) != null) {
                resp.add(new Encryptor().DecryptData(cadena));
            }
            b.close();
        } catch (Exception e) {
            
        }
        return resp;
    }

    //Imprimir como PDF,2,TERMINOS Y CONDICIONES, (Datos Ticket)  
    public String getDefaultPrinter() {
        List<String> database = getDBConfig();
        if (database.isEmpty()) {
            return database.get(1).split(",")[0];
        } else {
            return null;
        }
    }

    public String[] getTicketConfigs() {
        String[] resp = null;
        try {
            List<String> database = getDBConfig();
            resp = database.get(1).split(",");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public void setTicketConfigs(String nReg) {
        try {
            List<String> database = getDBConfig();
            database.set(1, nReg);
            FileWriter save = new FileWriter(getArchivo());
            BufferedWriter bw = new BufferedWriter(save);
            for (int i = 0; i < database.size(); i++) {
                bw.write(new Encryptor().EncryptData(database.get(i)));
                bw.newLine();
            }
            bw.close();
            save.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    //desactivado,default,default@gmail.com,VERSION DE PRUEBA,25, (Datos Licencia)
    public boolean isActivated() {
        boolean resp = false;
        try {
            if (getActivationStatus().equals("activado")) {
                resp = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public String getActivationStatus() {
        String resp = null;
        try {
            List<String> database = getDBConfig();
            resp = database.get(0).split(",")[0];
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public void setActivationStatus(String Estado) {
        try {
            List<String> database = getDBConfig();
            String[] oldReg = database.get(0).split(",");
            String nreg = Estado;
            for (int i = 1; i < oldReg.length; i++) {
                nreg += oldReg[i] + ",";
            }
            database.set(0, nreg);
            FileWriter save = new FileWriter(getArchivo());
            BufferedWriter bw = new BufferedWriter(save);
            for (int i = 0; i < database.size(); i++) {
                bw.write(new Encryptor().EncryptData(database.get(i)));
                bw.newLine();
            }
            bw.close();
            save.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String[] getAccountData() {
        String[] resp = null;
        try {
            List<String> database = getDBConfig();
            resp = database.get(0).split(",");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public void setLicData(String user, String email, String serial) {
        try {
            String[] Config = getAccountData();
            Config[0] = "activado";
            Config[1] = user;
            Config[2] = email;
            Config[3] = serial;
            String nreg = "";
            for (int i = 0; i < Config.length; i++) {
                nreg += Config[i] + ",";
            }
            setAccountData(nreg);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setAccountData(String nreg) {
        try {
            List<String> database = getDBConfig();
            database.set(0, nreg);
            FileWriter save = new FileWriter(getArchivo());
            BufferedWriter bw = new BufferedWriter(save);
            for (int i = 0; i < database.size(); i++) {
                bw.write(new Encryptor().EncryptData(database.get(i)));
                bw.newLine();
            }
            bw.close();
            save.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void resetTryalDays() {
        String[] dl = getAccountData();
        dl[4] = "0";
        String nreg = "";
        for (int i = 0; i < dl.length; i++) {
            nreg += dl[i] + ",";
        }
        setAccountData(nreg);
    }

    public void upTryalDays() {
        String[] dl = getAccountData();
        dl[4] = String.valueOf(Integer.parseInt(dl[4]) + 1);
        String nreg = "";
        for (int i = 0; i < dl.length; i++) {
            nreg += dl[i] + ",";
        }
        setAccountData(nreg);
    }

    public int getTryalDays() {
        String[] dl = getAccountData();
        return Integer.parseInt(dl[4]);
    }

    //configurations[2] = 0, (Reseteo)    
    public boolean isResetApp() {
        boolean resp = false;
        try {
            if (getResetApp().equals("1")) {
                resp = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public String getResetApp() {
        String resp = null;
        try {
            List<String> database = getDBConfig();
            resp = database.get(2).split(",")[0];
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public void enableResetApp() {
        try {
            List<String> database = getDBConfig();
            database.set(2, "1");
            FileWriter save = new FileWriter(getArchivo());
            BufferedWriter bw = new BufferedWriter(save);
            for (int i = 0; i < database.size(); i++) {
                bw.write(new Encryptor().EncryptData(database.get(i)));
                bw.newLine();
            }
            bw.close();
            save.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void disableResetApp() {
        try {
            List<String> database = getDBConfig();
            database.set(2, "0");
            FileWriter save = new FileWriter(getArchivo());
            BufferedWriter bw = new BufferedWriter(save);
            for (int i = 0; i < database.size(); i++) {
                bw.write(new Encryptor().EncryptData(database.get(i)));
                bw.newLine();
            }
            bw.close();
            save.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean verifyLicense(String[] serial) {
        int fase = 0;
        boolean resp = true;
        boolean[] cumple = new boolean[serial.length];
        for (int i = 0; i < serial.length; i++) {
            char[] word = serial[i].toCharArray();
            switch (fase) {
                case 0:
                    if (!Character.isDigit(word[0])) {
                        if (!Character.isDigit(word[1])) {
                            if (Character.isDigit(word[2])) {
                                if (Character.isDigit(word[3])) {
                                    cumple[i] = true;
                                } else {
                                    cumple[i] = false;
                                }
                            } else {
                                cumple[i] = false;
                            }
                        } else {
                            cumple[i] = false;
                        }
                    } else {
                        cumple[i] = false;
                    }
                    fase++;
                    break;
                case 1:
                    if (Character.isDigit(word[0])) {
                        if (Character.isDigit(word[1])) {
                            if (!Character.isDigit(word[2])) {
                                if (!Character.isDigit(word[3])) {
                                    cumple[i] = true;
                                } else {
                                    cumple[i] = false;
                                }
                            } else {
                                cumple[i] = false;
                            }
                        } else {
                            cumple[i] = false;
                        }
                    } else {
                        cumple[i] = false;
                    }
                    fase++;
                    break;
                case 2:
                    if (Character.isDigit(word[0])) {
                        if (!Character.isDigit(word[1])) {
                            if (!Character.isDigit(word[2])) {
                                if (!Character.isDigit(word[3])) {
                                    cumple[i] = true;
                                } else {
                                    cumple[i] = false;
                                }
                            } else {
                                cumple[i] = false;
                            }
                        } else {
                            cumple[i] = false;
                        }
                    } else {
                        cumple[i] = false;
                    }
                    fase++;
                    break;
                case 3:
                    if (!Character.isDigit(word[0])) {
                        if (!Character.isDigit(word[1])) {
                            if (!Character.isDigit(word[2])) {
                                if (!Character.isDigit(word[3])) {
                                    cumple[i] = true;
                                } else {
                                    cumple[i] = false;
                                }
                            } else {
                                cumple[i] = false;
                            }
                        } else {
                            cumple[i] = false;
                        }
                    } else {
                        cumple[i] = false;
                    }
                    fase++;
                    break;
                case 4:
                    if (!Character.isDigit(word[0])) {
                        if (Character.isDigit(word[1])) {
                            if (Character.isDigit(word[2])) {
                                if (Character.isDigit(word[3])) {
                                    cumple[i] = true;
                                } else {
                                    cumple[i] = false;
                                }
                            } else {
                                cumple[i] = false;
                            }
                        } else {
                            cumple[i] = false;
                        }
                    } else {
                        cumple[i] = false;
                    }
                    fase++;
                    break;
                case 5:
                    if (!Character.isDigit(word[0])) {
                        if (!Character.isDigit(word[1])) {
                            if (!Character.isDigit(word[2])) {
                                if (Character.isDigit(word[3])) {
                                    if (Character.isDigit(word[4])) {
                                        cumple[i] = true;
                                    } else {
                                        cumple[i] = false;
                                    }
                                } else {
                                    cumple[i] = false;
                                }
                            } else {
                                cumple[i] = false;
                            }
                        } else {
                            cumple[i] = false;
                        }
                    } else {
                        cumple[i] = false;
                    }
                    fase++;
                    break;
            }
        }
        for (int i = 0; i < cumple.length; i++) {
            if (!cumple[i]) {
                return false;
            }
        }
        return resp;
    }
    
    //ICONOS E IMAGENES
    public ImageIcon getAppLogo(int Width, int Height) {
        File f = new File("iconos\\Logo.png");
        ImageIcon icon = new ImageIcon(f.getAbsolutePath());
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(Width, Height, java.awt.Image.SCALE_DEFAULT);
        return new ImageIcon(newimg);
    }

    public ImageIcon getExample(int Width, int Height) {
        File f = new File("iconos\\Example.png");
        ImageIcon icon = new ImageIcon(f.getAbsolutePath());
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(Width, Height, java.awt.Image.SCALE_DEFAULT);
        return new ImageIcon(newimg);
    }

    public ImageIcon getImageIcon() {
        ImageIcon resp = null;
        try {
            File f = new File("iconos\\appPNG.png");
            resp = new ImageIcon(f.getAbsolutePath());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public ImageIcon getMKTIcon(int Width, int Height) {
        File f = new File("iconos\\marketingPNG.png");
        ImageIcon icon = new ImageIcon(f.getAbsolutePath());
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(Width, Height, java.awt.Image.SCALE_DEFAULT);
        return new ImageIcon(newimg);
    }

    public ImageIcon getLogo(int Width, int Height) {
        File f = new File("iconos\\Logo.png");
        ImageIcon icon = new ImageIcon(f.getAbsolutePath());
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(Width, Height, java.awt.Image.SCALE_DEFAULT);
        return new ImageIcon(newimg);
    }

    public ImageIcon getTableIcon(int Width, int Height) {
        File f = new File("iconos\\tableIconPNG.png");
        ImageIcon icon = new ImageIcon(f.getAbsolutePath());
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(Width, Height, java.awt.Image.SCALE_DEFAULT);
        return new ImageIcon(newimg);
    }

    public ImageIcon getTable2Icon(int Width, int Height) {
        File f = new File("iconos\\tableIcon2PNG.png");
        ImageIcon icon = new ImageIcon(f.getAbsolutePath());
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(Width, Height, java.awt.Image.SCALE_DEFAULT);
        return new ImageIcon(newimg);
    }

    public ImageIcon getUserIcon(int Width, int Height) {
        File f = new File("iconos\\user.png");
        ImageIcon icon = new ImageIcon(f.getAbsolutePath());
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(Width, Height, java.awt.Image.SCALE_DEFAULT);
        return new ImageIcon(newimg);
    }

    public void resetLogo() {
        try {
            Path origen = FileSystems.getDefault().getPath("iconos\\Logo.png");
            File f = new File("iconos\\LogoCOPY.png");
            File file2 = new File("iconos\\LOGO.png");
            file2.createNewFile();
            f.renameTo(file2);
            Path destino = FileSystems.getDefault().getPath(f.getAbsolutePath());
            Files.copy(destino, origen, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar imagen: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadLogo() {
        try {
            Path origen = FileSystems.getDefault().getPath("iconos\\Logo.png");
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filtro_PNG = new FileNameExtensionFilter("AImagenes PNG", "PNG", "png");
            chooser.setFileFilter(filtro_PNG);
            chooser.setAcceptAllFileFilterUsed(false);
            int retrival = chooser.showSaveDialog(null);
            if (retrival == JFileChooser.APPROVE_OPTION) {
                File f = new File(chooser.getSelectedFile().getAbsolutePath());
                File file2 = new File("iconos\\LOGO.png");
                file2.createNewFile();
                f.renameTo(file2);
                Path destino = FileSystems.getDefault().getPath(f.getAbsolutePath());
                Files.copy(destino, origen, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar imagen: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
