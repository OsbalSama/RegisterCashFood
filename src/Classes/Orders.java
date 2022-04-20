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
 * @author Viernes
 */
public class Orders {

    String archivo = "source\\DBS_0007.dat";

    public String getArchivo() {
        return archivo;
    }
    
    public List getOrdersByClient(String idClient) {
        List<String> resp = new ArrayList<String>();
        try {
            List<String> database = getDBOrders();
            if (!database.isEmpty()) {
                for (int i = 0; i < database.size(); i++) {
                    String[] temp = database.get(i).split(",");                    
                    if (temp[5].equals(idClient)) {
                        resp.add(database.get(i));
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public List getDBOrdersByDate(String fecha) {
        List<String> database = new ArrayList<>();
        try {
            String cadena;
            FileReader f = new FileReader(this.archivo);
            BufferedReader b = new BufferedReader(f);
            while ((cadena = b.readLine()) != null) {
                String[] temp = new Encryptor().DecryptData(cadena).split(",");
                if (temp[6].equals(fecha)) {
                    database.add(new Encryptor().DecryptData(cadena));
                }
            }
            b.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return database;
    }

    public List getDBOrdersByDateRange(List<String> fechas) {
        List<String> database = new ArrayList<>();
        try {
            String cadena;
            FileReader f = new FileReader(this.archivo);
            BufferedReader b = new BufferedReader(f);
            while ((cadena = b.readLine()) != null) {
                String[] temp = new Encryptor().DecryptData(cadena).split(",");
                if (fechas.contains(temp[6])) {
                    database.add(new Encryptor().DecryptData(cadena));
                }
            }
            b.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return database;
    }

    public void ClearDB() {
        try {
            new File(getArchivo()).delete();
            new File(getArchivo()).createNewFile();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean getStateOrder(String[] Orden) {
        boolean resp = false;
        if (!Orden.equals("")) {
            if (Orden[3].equals("PENDIENTE") && Orden[4].equals(new SimpleDateFormat("dd-MM-YYYY").format(new Date()))) {
                resp = true;
            }
        }
        return resp;
    }

    public boolean updateOrder(String nuevo_reg) {
        try {
            List database = getDBOrders();
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

    public String[] getOrders(String[] mesas) {
        String[] resp = new String[mesas.length];
        for (int i = 0; i < mesas.length; i++) {
            if (!getActiveOrdersbyMesa(mesas[i]).equals("")) {
                resp[i] = getActiveOrdersbyMesa(mesas[i]);
            }
        }
        return resp;
    }

    public List getDBOrders() {
        List<String> resp = new ArrayList();
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

    public List getAllActiveOrders() {
        List<String> resp = new ArrayList<>();
        try {
            List database = getDBOrders();
            if (!database.isEmpty()) {
                for (int i = 0; i < database.size(); i++) {
                    String[] temp = database.get(i).toString().split(",");
                    if (temp[4].equals("PENDIENTE")) {
                        resp.add(database.get(i).toString());
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public List getOrderbyDate(String date) {
        List<String> resp = new ArrayList();
        try {
            List database = getDBOrders();
            if (!database.isEmpty()) {
                for (int i = 0; i < database.size(); i++) {
                    String[] temp = database.get(i).toString().split(",");
                    if (date.equals(temp[4])) {
                        resp.add(database.get(i).toString());
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error 0: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public List getOrderbyStateAndDate(String State, String date) {
        List<String> resp = new ArrayList();
        try {
            List database = getDBOrders();
            if (!database.isEmpty()) {
                for (int i = 0; i < database.size(); i++) {
                    String[] temp = database.get(i).toString().split(",");
                    if (State.equals(temp[3]) && date.equals(temp[4])) {
                        resp.add(database.get(i).toString());
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error 0: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public List getOrderbyState(String State) {
        List<String> resp = new ArrayList();
        try {
            List database = getDBOrders();
            if (!database.isEmpty()) {
                for (int i = 0; i < database.size(); i++) {
                    String[] temp = database.get(i).toString().split(",");
                    if (State.equals(temp[3])) {
                        resp.add(database.get(i).toString());
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error 0: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public String[] getOrderbyID(String idOrden) {
        String resp = "";
        try {
            List database = getDBOrders();
            if (!database.isEmpty()) {
                for (int i = 0; i < database.size(); i++) {
                    String[] temp = database.get(i).toString().split(",");
                    if (idOrden.equals(temp[0])) {
                        resp = database.get(i).toString();
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        if (resp.equals("")) {
            return null;
        } else {
            return resp.split(",");
        }
    }

    public String getActiveOrdersbyMesa(String Mesa) {
        String resp = "";
        try {
            List database = getDBOrders();
            if (!database.isEmpty()) {
                for (int i = 0; i < database.size(); i++) {
                    String[] temp = database.get(i).toString().split(",");
                    if (Mesa.equals(temp[1]) && temp[4].equals("PENDIENTE")) {
                        resp = database.get(i).toString();
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public String genID() {
        String id = "ORD_";
        try {
            for (int i = 0; i < 5; i++) {
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
            List database = getDBOrders();
            if (!database.isEmpty()) {
                for (int i = 0; i < database.size(); i++) {
                    String[] temp = database.get(i).toString().split(",");
                    if (temp[0].equals(id)) {
                        resp = true;
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public boolean insOrder(String nuevo_reg) {
        nuevo_reg = new Encryptor().EncryptData(nuevo_reg);
        try {
            List database = getDBOrders();
            if (database.isEmpty()) {
                FileWriter w = new FileWriter(this.getArchivo());
                BufferedWriter bw = new BufferedWriter(w);
                bw.write(nuevo_reg);
                bw.newLine();
                bw.close();
            } else {
                FileWriter w = new FileWriter(this.getArchivo());
                BufferedWriter bw = new BufferedWriter(w);
                for (int i = 0; i < database.size(); i++) {
                    bw.write(new Encryptor().EncryptData(database.get(i).toString()));
                    bw.newLine();
                }
                bw.write(nuevo_reg);
                bw.newLine();
                bw.close();
                return true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

}
