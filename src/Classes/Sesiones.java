/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author OSBAL
 */
public class Sesiones {

    String archivo = "source\\DBS_0009.dat";

    public List<String> getDBSesiones() {
        List<String> resp = new ArrayList<>();
        try {
            String line = "";
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            while ((line = br.readLine()) != null) {
                if (line.split(",").length > 0) {
                    resp.add(new Encryptor().DecryptData(line));
                }
            }
            br.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
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

    public boolean areLoginsToday() {
        boolean resp = false;
        try {
            List<String> database = getDBSesiones();
            if (!database.isEmpty()) {
                for (int i = 0; i < database.size(); i++) {
                    String[] registro = database.get(i).split(",");
                    if (registro[2].equals(new SimpleDateFormat("dd-MM-YYYY").format(new Date()))) {
                        resp = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public String genID() {
        try {
            String id = "ssn_";
            for (int i = 0; i < 7; i++) {
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
            List<String> datos = getDBSesiones();
            if (!datos.isEmpty()) {
                for (int i = 0; i < datos.size(); i++) {
                    String[] sesion = datos.get(i).split(",");
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

    public void saveRegSession(String id, String usuario) {
        if (existe_reg(new SimpleDateFormat("dd-MM-yyyy").format(new Date()), usuario)) {
            UpdateLogg(usuario, new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        } else {
            saveSession(id, usuario);
        }
    }

    public void saveSession(String id, String usuario) {
        try {
            String nuevo_reg = id + "," + usuario
                    + "," + new SimpleDateFormat("dd-MM-yyyy").format(new Date())
                    + "," + new SimpleDateFormat("hh:mm").format(new Date())
                    + "," + new SimpleDateFormat("hh:mm").format(new Date()) + "," + "1";

            List<String> database = getDBSesiones();
            if (database.isEmpty()) {
                FileWriter w = new FileWriter(getArchivo());
                BufferedWriter bw = new BufferedWriter(w);
                bw.write(new Encryptor().EncryptData(nuevo_reg));
                bw.newLine();
                bw.close();
            } else {
                FileWriter w = new FileWriter(getArchivo());
                BufferedWriter bw = new BufferedWriter(w);
                for (int i = 0; i < database.size(); i++) {
                    bw.write(new Encryptor().EncryptData(database.get(i)));
                    bw.newLine();
                }
                bw.write(new Encryptor().EncryptData(nuevo_reg));
                bw.newLine();
                bw.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean existe_reg(String fecha, String user) {
        boolean resp = false;
        try {
            List<String> database = getDBSesiones();
            if (!database.isEmpty()) {
                for (int i = 0; i < database.size(); i++) {
                    String[] registro = database.get(i).split(",");
                    if (registro[1].equals(user) && registro[2].equals(fecha)) {
                        resp = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public void UpdateLogg(String user, String fecha) {
        try {
            List<String> database = getDBSesiones();
            for (int i = 0; i < database.size(); i++) {
                String[] registro = database.get(i).split(",");
                if (registro[1].equals(user) && registro[2].equals(fecha)) {
                    registro[2] = new SimpleDateFormat("dd-MM-YYYY").format(new Date());//fecha
                    registro[4] = new SimpleDateFormat("hh:mm").format(new Date());//ultima hora
                    registro[5] = (Integer.parseInt(registro[5]) + 1) + "";//sesiones_iniciadas
                    String text = "";
                    for (int j = 0; j < registro.length; j++) {
                        text += registro[j] + ",";
                    }
                    database.set(i, text);
                    break;
                }
            }
            FileWriter w = new FileWriter(getArchivo());
            BufferedWriter bw = new BufferedWriter(w);
            for (int i = 0; i < database.size(); i++) {
                bw.write(new Encryptor().EncryptData(database.get(i)));
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean areLogginsAtDay(String fecha) {
        boolean resp = false;
        try {
            List<String> database = getDBSesiones();
            if (!database.isEmpty()) {
                for (int i = 0; i < database.size(); i++) {
                    String[] registro = database.get(i).split(",");
                    if (registro[2].equals(fecha)) {
                        resp = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }
}
