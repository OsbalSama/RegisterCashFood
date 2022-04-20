/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;

/**
 *
 * @author MANUEL
 */
public class CorteZ {

    String archivo = "source\\DBS_0005.dat";

    public List getDBEdoResHoy() {
        List<String> resp = new ArrayList<String>();
        try {
            String[] allreg = getDBEdoRes();
            if (allreg != null) {
                String hoy = new SimpleDateFormat("dd-MM-YYYY").format(new Date());
                for (int i = 0; i < allreg.length; i++) {
                    String[] regInd = allreg[i].split(",");
                    if (hoy.equals(regInd[2])) {
                        resp.add(allreg[i]);
                    }
                }
            }
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
    
    public String[] getDBEdoRes() {
        String resp = "";
        try {
            String cadena;
            FileReader f = new FileReader(this.archivo);
            BufferedReader b = new BufferedReader(f);
            while ((cadena = b.readLine()) != null) {
                resp += new Encryptor().DecryptData(cadena) + "<<";
            }
            b.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error:" + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        if (resp.equals("")) {
            return null;
        } else {
            return resp.split("<<");
        }
    }

    public String genID() {
        try {
            String id = "";
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
            String[] datos = getDBEdoRes();
            if (datos != null) {
                for (int i = 0; i < datos.length; i++) {
                    String[] sesion = datos[i].split(",");
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

    public void processEdoRes(String id, String user) {
        try {
            String fecha = new SimpleDateFormat("dd-MM-YYYY").format(new Date());
            String nuevo_registro = "";
            String idedores = id;
            nuevo_registro += idedores + ",";
            nuevo_registro += user + ",";
            nuevo_registro += new SimpleDateFormat("dd-MM-YYYY").format(new Date()) + ",";
            nuevo_registro += new SimpleDateFormat("HH:mm").format(new Date()) + ",";
            setEdoRes(nuevo_registro);
//            String id_usr_date_hour = idedores + "," + user + "," + new SimpleDateFormat("dd-MM-YYYY").format(new Date()) + "," + new SimpleDateFormat("HH:mm").format(new Date()) + ",";
//            new PDFDocs().PrintEdoResTicket(id_usr_date_hour.split(","), new Configurations().getDefaultPrinter());
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setEdoRes(String nuevo_reg) {
        nuevo_reg = new Encryptor().EncryptData(nuevo_reg);
        try {
            String[] database = getDBEdoRes();
            if (database == null) {
                FileWriter w = new FileWriter(this.archivo);
                BufferedWriter bw = new BufferedWriter(w);
                bw.write(nuevo_reg);
                bw.newLine();
                bw.close();
            } else {
                FileWriter w = new FileWriter(this.archivo);
                BufferedWriter bw = new BufferedWriter(w);
                for (int i = 0; i < database.length; i++) {
                    bw.write(new Encryptor().EncryptData(database[i]));
                    bw.newLine();
                }
                bw.write(nuevo_reg);
                bw.newLine();
                bw.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String[] genRegistrosEdoRes() {
        String resp = "";
        List dbProdVendidos = new SaleProdcts().getDBProdVend();
        for (int i = 0; i < dbProdVendidos.size(); i++) {
            String[] ProdVendido = dbProdVendidos.get(i).toString().split(",");
            if (ProdVendido[7].equals(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))) {
                for (int j = 2; j < ProdVendido.length; j++) {
                    resp += ProdVendido[j] + ",";
                }
                resp += "_";
            }
        }
        return resp.split("_");
    }

    public String getTotalRegEdoRes(String[] regEdoRes) {
        double total = 0;
        for (int i = 0; i < regEdoRes.length; i++) {
            String[] temp = regEdoRes[i].split(",");
            total = total + Double.parseDouble(temp[3]);
        }
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        return format.format(total);
    }

}
