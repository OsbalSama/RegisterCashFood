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
 * @author ATENEA
 */
public class Clients {

    String archivo = "source\\DBS_0003.dat";

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

    public List<String> getDBClientsbyDesc(String pista) {
        List<String> busqueda = new ArrayList<>();
        try {
            List<String> database = getDBClients();
            if (!database.isEmpty()) {
                for (int i = 0; i < database.size(); i++) {
                    String[] temp = database.get(i).split(",");
                    String idClient = temp[0].toLowerCase();
                    String Nombre = temp[1].toLowerCase();
                    String Dir = temp[2].toLowerCase();
                    String Tel = temp[3].toLowerCase();

                    String indicio = pista.toLowerCase();
                    int intIndex0 = idClient.indexOf(indicio);
                    int intIndex1 = Nombre.indexOf(indicio);
                    int intIndex2 = Dir.indexOf(indicio);
                    int intIndex3 = Tel.indexOf(indicio);
                    if (intIndex0 != - 1 || intIndex1 != - 1 || intIndex2 != - 1 || intIndex3 != - 1) {
                        busqueda.add(database.get(i));
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return busqueda;
    }

    public List<String> getDBClients() {
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

    public String genID() {
        try {
            String id = "CLT_";
            for (int i = 0; i < 4; i++) {
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
            List<String> datos = getDBClients();
            if (datos != null) {
                for (int i = 0; i < datos.size(); i++) {
                    if (datos.get(i).split(",")[0].equals(id)) {
                        resp = true;
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public String[] getClientbyID(String id) {
        String resp = "";
        try {
            List<String> database = getDBClients();
            if (!database.isEmpty()) {
                for (int i = 0; i < database.size(); i++) {
                    if (id.equals(database.get(i).split(",")[0])) {
                        resp = database.get(i);
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

    public boolean updateClient(String nuevo_reg) {
        try {
            List<String> database = getDBClients();
            FileWriter w = new FileWriter(getArchivo());
            BufferedWriter bw = new BufferedWriter(w);
            for (int i = 0; i < database.size(); i++) {
                String[] nreg = nuevo_reg.split(",");
                if (database.get(i).split(",")[0].equals(nreg[0])) {
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

    public boolean insClient(String nuevo_reg) {
        try {
            if (getDBClients().isEmpty()) {
                FileWriter w = new FileWriter(this.getArchivo());
                BufferedWriter bw = new BufferedWriter(w);
                bw.write(new Encryptor().EncryptData(nuevo_reg));
                bw.newLine();
                bw.close();
            } else {
                List<String> database = getDBClients();
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

    public boolean DelClient(String ClientID) {
        try {
            List<String> allUsers = getDBClients();
            BufferedWriter bw = new BufferedWriter(new FileWriter(getArchivo()));
            for (int i = 0; i < allUsers.size(); i++) {
                if (!ClientID.equals(allUsers.get(i).split(",")[0])) {
                    bw.write(new Encryptor().EncryptData(allUsers.get(i)));
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

    public String getClienttoString(String idClient) {
        String resp = "";
        String[] cliente = new Clients().getClientbyID(idClient);
        resp = "CLIENT: " + cliente[1] + "\n"
                + "DIR: " + cliente[2] + "\n"
                + "TEL: " + cliente[3] + "\n";
        return resp;
    }
}
