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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author OSBAL
 */
public class CatProvClass {

    String fichero = "source\\DBS_0002.dat";
//    String fichero2 = "source\\DBS_0009.dat";

    public String getFichero() {
        return fichero;
    }

//    public String getFichero2() {
//        return fichero2;
//    }
    public void ClearDBCats() {
        try {
            new File(getFichero()).delete();
            new File(getFichero()).createNewFile();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List getDBCat() {
        List<String> database = new ArrayList<>();
        try {
            String cadena;
            FileReader f = new FileReader(getFichero());
            BufferedReader b = new BufferedReader(f);
            while ((cadena = b.readLine()) != null) {
                database.add(new Encryptor().DecryptData(cadena));
            }
            b.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return database;
    }

    public boolean existCat(String cat) {
        boolean resp = false;
        List database = getDBCat();
        if (!database.isEmpty()) {
            for (int i = 0; i < database.size(); i++) {
                if (database.get(i).equals(cat)) {
                    resp = true;
                    break;
                }
            }
        }
        return resp;
    }

    public void insCat(String nreg) {
        List database = getDBCat();
        try {
            FileWriter w = new FileWriter(getFichero());
            BufferedWriter bw = new BufferedWriter(w);
            if (!database.isEmpty()) {
                for (int i = 0; i < database.size(); i++) {
                    bw.write(new Encryptor().EncryptData(database.get(i).toString()));
                    bw.newLine();
                }
                bw.write(new Encryptor().EncryptData(nreg));
            } else {
                bw.write(new Encryptor().EncryptData(nreg));
            }
            bw.close();
            w.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void UpdateCat(String OldReg, String nreg) {
        List database = getDBCat();
        try {
            FileWriter w = new FileWriter(getFichero());
            BufferedWriter bw = new BufferedWriter(w);
            if (!database.isEmpty()) {
                for (int i = 0; i < database.size(); i++) {
                    if (database.get(i).equals(OldReg)) {
                        database.set(i, nreg);
                        break;
                    }
                }
                for (int i = 0; i < database.size(); i++) {
                    bw.write(new Encryptor().EncryptData(database.get(i).toString()));
                    bw.newLine();
                }
            }
            bw.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean delCat(String reg) {
        boolean resp = false;
        List database = getDBCat();
        try {
            FileWriter w = new FileWriter(getFichero());
            BufferedWriter bw = new BufferedWriter(w);
            if (!database.isEmpty()) {
                for (int i = 0; i < database.size(); i++) {
                    if (database.get(i).equals(reg)) {
                        database.remove(i);
                        break;
                    }
                }
                for (int i = 0; i < database.size(); i++) {
                    bw.write(new Encryptor().EncryptData(database.get(i).toString()));
                    bw.newLine();
                }
            }
            bw.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }
}
