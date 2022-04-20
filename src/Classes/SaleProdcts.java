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
 * @author terra
 */
public class SaleProdcts {

    String archivo = "source\\DBS_0008.dat";

    
    
    
    public List getProdVendByID(String ID) {
        List<String> resp = new ArrayList<String>();
        try {
            List<String> database = getDBProdVend();
            if (!database.isEmpty()) {
                for (int i = 0; i < database.size(); i++) {
                    String[] temp = database.get(i).split(",");                    
                    if (temp[1].equals(ID)) {
                        resp.add(database.get(i));
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public List getDBProdVend() {
        List<String> resp = new ArrayList<String>();
        try {
            String cadena;
            FileReader f = new FileReader(this.archivo);
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

    public boolean ventasHoy() {
        List ventasHoy = getDBProdVendHoy();
        return !ventasHoy.isEmpty();
    }

    public List getDBProdVendHoy() {
        List<String> database = getDBProdVend();
        List<String> resp = new ArrayList<String>();
        try {

            if (!database.isEmpty()) {
                String hoy = new SimpleDateFormat("dd-MM-YYYY").format(new Date());
                for (int i = 0; i < database.size(); i++) {
                    String[] regInd = database.get(i).split(",");
                    if (hoy.equals(regInd[7])) {
                        resp.add(database.get(i));
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
            String id = "";
            for (int i = 0; i < 10; i++) {
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
            List<String> database = getDBProdVend();
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

    public boolean updateProdVend(String nuevo_reg) {
        try {
            List database = getDBProdVend();
            FileWriter w = new FileWriter(this.archivo);
            BufferedWriter bw = new BufferedWriter(w);
            for (int i = 0; i < database.size(); i++) {
                String[] oldReg = database.get(i).toString().split(",");
                String[] nreg = nuevo_reg.split(",");
                if (oldReg[0].equals(nreg[0])) {
                    bw.write(new Encryptor().EncryptData(nuevo_reg));
                    bw.newLine();
                } else {
                    bw.write(new Encryptor().EncryptData(database.get(i).toString()));
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
    
    public void insProdVend(String nuevo_reg) {
        nuevo_reg = new Encryptor().EncryptData(nuevo_reg);
        List<String> database = getDBProdVend();
        try {
            if (database.isEmpty()) {
                FileWriter w = new FileWriter(this.archivo);
                BufferedWriter bw = new BufferedWriter(w);
                bw.write(nuevo_reg);
                bw.newLine();
                bw.close();
            } else {
                FileWriter w = new FileWriter(this.archivo);
                BufferedWriter bw = new BufferedWriter(w);
                for (int i = 0; i < database.size(); i++) {
                    bw.write(new Encryptor().EncryptData(database.get(i)));
                    bw.newLine();
                }
                bw.write(nuevo_reg);
                bw.newLine();
                bw.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error:" + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
