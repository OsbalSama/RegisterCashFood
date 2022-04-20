/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Dialogs.ProgresBarData;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author OSBAL
 */
public class CSVData {

    public List<String> getDatabaseCSV(String ruta) {
        List<String> resp = new ArrayList<>();
        try {
            String line = "";
            BufferedReader br = new BufferedReader(new FileReader(ruta));
            while ((line = br.readLine()) != null) {
                if (line.split(",").length > 0) {
                    resp.add(line);
                }
            }
            br.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public void ExportDB() {
        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivo CSV (Delimitado por comas)", "csv", "CSV");
        chooser.setFileFilter(filtro);
        chooser.setSelectedFile(new File("Register_Cash Inventario " + new SimpleDateFormat("dd-MM-YYY").format(new Date()) + ".csv"));
        int retrival = chooser.showSaveDialog(null);
        if (retrival == JFileChooser.APPROVE_OPTION) {
            try {
                File archivo = new File(chooser.getSelectedFile().getAbsolutePath());
                if (archivo.exists()) {
                    if (JOptionPane.showConfirmDialog(null, "Este archivo ya existe... \ndesea reemplazarlo?", "¡Atencion!", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                        archivo.delete();
                        if (archivo.createNewFile()) {
                            ProgresBarData pbd = new ProgresBarData(null, true);
                            pbd.SetExportData(archivo.getAbsolutePath());
                            pbd.iniciarExportData();
                            pbd.show();
                        }
                    }
                } else {
                    if (archivo.createNewFile()) {
                        ProgresBarData pbd = new ProgresBarData(null, true);
                        pbd.SetExportData(archivo.getAbsolutePath());
                        pbd.iniciarExportData();
                        pbd.show();
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public String[] getHeaderCSV(String ruta) {
        String resp = "";
        try {
            List<String> database = getDatabaseCSV(ruta);
            resp = database.get(0);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        if (resp.equals("")) {
            return null;
        } else {
            return resp.split(",");
        }
    }

    public boolean isEmptyFile(String ruta) {
        boolean resp = true;
        List<String> database = getDatabaseCSV(ruta);
        if (database.isEmpty()) {
            resp = false;
        }
        return resp;
    }

    public void CreateCSVFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivo CSV (Delimitado por comas)", "csv", "CSV");
        chooser.setFileFilter(filtro);
        chooser.setSelectedFile(new File("Plantilla Register_Cash_Food"));
        int retrival = chooser.showSaveDialog(null);
        if (retrival == JFileChooser.APPROVE_OPTION) {
            try {
                File archivo = new File(chooser.getSelectedFile().getAbsolutePath() + ".csv");
                if (archivo.createNewFile()) {
                    FileWriter w = new FileWriter(archivo);
                    BufferedWriter bw = new BufferedWriter(w);
                    bw.write("Codigo,Titulo,Descripcion,Categoria,Precio,");
                    bw.newLine();
                    bw.close();
                    JOptionPane.showMessageDialog(null, "<html><h1>¡PLANTILLA CREADA CORRECTAMENTE!</h1>"
                            + "<font SIZE=5><p>Clic para cerrar...</font></html>", "¡Terminado!", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void saveDataBase(String ruta, int[] cselect) {
        ProgresBarData pbd = new ProgresBarData(null, true);
        pbd.SetImportData(ruta, cselect);
        pbd.iniciarImportData();
        pbd.show();
    }

    public String getCelContent(String[] fila, int posicion) {
        String resp = "";
        try {
            for (int i = 0; i < fila.length; i++) {
                if (posicion == i) {
                    String textCol = fila[i].replace(',', ' ');
                    if (textCol.replace(" ", "").length() > 0 || !textCol.replace(" ", "").equals("")) {
                        new Encryptor().clearText(resp = fila[i].replace(',', ' '));
                    } else {
                        new Encryptor().clearText(resp = "CELDA VACIA");
                    }
                    break;
                }
            }
            if (resp.equals("")) {
                resp = "CELDA VACIA";
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public String getError(String ROW) {
        String resp = "";
        String[] fila = ROW.split(",");
        if (!isText(fila[1])) {
            resp = "“" + fila[1] + "”  no es un texto valido";
        } else if (!isText(fila[2])) {
            resp = "“" + fila[2] + "”  no es un texto valido";
        } else if (!isText(fila[3])) {
            resp = "“" + fila[3] + "”  no es un texto valido";
        } else if (!isText(fila[4])) {
            resp = "“" + fila[4] + "”  no es un texto valido";
        } else if (!isDouble(fila[5]) || !isInteger(fila[5])) {
            resp = "“" + fila[5] + "”  no es una cantidad valida";
        } else if (!isDouble(fila[6]) || !isInteger(fila[6])) {
            resp = "“" + fila[6] + "”  no es una cantidad valida";
        } else if (!isDouble(fila[7]) || !isInteger(fila[7])) {
            resp = "“" + fila[7] + "”  no es una cantidad valida";
        } else {
            resp = "Error desconocido";
        }
        return resp;
    }

    public boolean repetida(String fila, List lista) {
        boolean resp = false;
        if (!lista.isEmpty()) {
            String[] filaEvaluar = fila.split(",");
            for (int i = 0; i < lista.size(); i++) {
                String[] temp = lista.get(i).toString().split(",");
                if (filaEvaluar[0].equals(temp[0])) {
                    resp = true;
                    break;
                }
            }
        }
        return resp;
    }

    public String getRowContent(String[] fila, int[] csel) {
        String resp = "";
        for (int j = 0; j < csel.length; j++) {
            switch (j) {
                case 0:
                    if (csel[j] == 0) {
                        resp += new Dishes().genID() + ",";
                    } else {
                        String content = getCelContent(fila, csel[j] - 1);
                        if (!content.equals("CELDA VACIA")) {
                            resp += getCelContent(fila, csel[j] - 1).replace(" ", "-").replace(",", "") + ",";
                        } else {
                            resp += new Dishes().genID() + ",";
                        }
                        break;
                    }
                    break;
                case 2:
                    if (csel[j] == 0) {
                        resp += "SIN DESCRIPCION,";
                    } else {
                        resp += getCelContent(fila, csel[j] - 1).replace(",", "") + ",";
                        break;
                    }
                    break;

                case 3:
                    if (csel[j] == 0) {
                        resp += "SIN CATEGORIA,";
                    } else {
                        resp += getCelContent(fila, csel[j] - 1).replace(",", "") + ",";
                        break;
                    }
                    break;
//                case 5:
//                    if (csel[j] == 0) {
//                        resp += "SIN IMAGEN,";
//                    } else {
//                        resp += getCelContent(fila, csel[j] - 1).replace(",", "") + ",";
//                        break;
//                    }
//                    break;
                default:
                    resp += getCelContent(fila, csel[j]).replace(",", "").replace("$", "") + ",";
                    break;
            }
        }
        resp += new SimpleDateFormat("dd-MM-YYYY").format(new Date()) + ",";
        return resp;
    }

    public boolean RowFormat(String content, int[] cselect) {
        boolean resp = false;
        String[] fila = content.split(",");
        if (isText(fila[1]) && isText(fila[2])
                && isText(fila[3]) && isDouble(fila[4])
                && isText(fila[5])) {
            resp = true;
        }
        return resp;
    }

    public boolean isText(String texto) {
        try {
            String valueOf = String.valueOf(texto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isDouble(String texto) {
        try {
            Double.parseDouble(texto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isInteger(String texto) {
        try {
            Integer.parseInt(texto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void addTempFile(String ruta, List<String> database) {
        try {
            FileWriter w = new FileWriter(ruta);
            BufferedWriter bw = new BufferedWriter(w);
            bw.write("Codigo,Titulo,Descripcion,Categoria,Precio,Fecha de Creacion,");
            bw.newLine();
            for (int i = 0; i < database.size(); i++) {
                bw.write(database.get(i));
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
