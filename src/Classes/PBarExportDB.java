/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 *
 * @author OSBAL
 */
public class PBarExportDB extends SwingWorker<Integer, String> {

    /**
     * @return the lblDescripcion
     */
    public JLabel getLblDescripcion() {
        return lblDescripcion;
    }

    /**
     * @param lblDescripcion the lblDescripcion to set
     */
    public void setLblDescripcion(JLabel lblDescripcion) {
        this.lblDescripcion = lblDescripcion;
    }

    /**
     * @return the txtAreaProceso
     */
    public JTextArea getTxtAreaProceso() {
        return txtAreaProceso;
    }

    /**
     * @param txtAreaProceso the txtAreaProceso to set
     */
    public void setTxtAreaProceso(JTextArea txtAreaProceso) {
        this.txtAreaProceso = txtAreaProceso;
    }

    /**
     * @return the BarraDeCarga
     */
    public JProgressBar getBarraDeCarga() {
        return BarraDeCarga;
    }

    /**
     * @param BarraDeCarga the BarraDeCarga to set
     */
    public void setBarraDeCarga(JProgressBar BarraDeCarga) {
        this.BarraDeCarga = BarraDeCarga;
    }

    /**
     * @return the BotonCerrar
     */
    public JButton getBotonCerrar() {
        return BotonCerrar;
    }

    /**
     * @param BotonCerrar the BotonCerrar to set
     */
    public void setBotonCerrar(JButton BotonCerrar) {
        this.BotonCerrar = BotonCerrar;
    }

    public String getRutaDestino() {
        return rutaDestino;
    }

    public void setRutaDestino(String rutaDestino) {
        this.rutaDestino = rutaDestino;
    }

    private JLabel lblDescripcion;
    private JTextArea txtAreaProceso;
    private JProgressBar BarraDeCarga;
    private JButton BotonCerrar;

    private String rutaDestino;

    public PBarExportDB(JLabel lblDescripcion, JTextArea txtAreaProceso, JProgressBar BarraDeCarga, JButton BotonCerrar, String rutaDestino) {
        this.lblDescripcion = lblDescripcion;
        this.txtAreaProceso = txtAreaProceso;
        this.BarraDeCarga = BarraDeCarga;
        this.BotonCerrar = BotonCerrar;
        this.rutaDestino = rutaDestino;
    }

    @Override
    protected Integer doInBackground() throws Exception {
        //AQUI VA EL PROCESO
        getLblDescripcion().setText("PROCESANDO DATOS...");
        getTxtAreaProceso().setCaretPosition(getTxtAreaProceso().getDocument().getLength());
        getBarraDeCarga().setIndeterminate(true);
        getLblDescripcion().setText("CREANDO NUEVOS ARCHIVOS...");
        getTxtAreaProceso().append("Creando nuevos archivos...\n");
        getTxtAreaProceso().setCaretPosition(getTxtAreaProceso().getDocument().getLength());
        getTxtAreaProceso().append("Archivos creados correctamente...\n\n");
        getTxtAreaProceso().setCaretPosition(getTxtAreaProceso().getDocument().getLength());
        List<String> inventario = new Dishes().getDBDishes();
        getBarraDeCarga().setIndeterminate(false);
        int revisadas = 0;
        int exportadas = 0;
        int noexportadas = 0;
        try {
            getBarraDeCarga().setMinimum(0);
            getBarraDeCarga().setMaximum(inventario.size());
            FileWriter w = new FileWriter(getRutaDestino());
            BufferedWriter bw = new BufferedWriter(w);
            bw.write("Codigo,Titulo,Descripcion,Categoria,Precio,F-Registro");
            bw.newLine();
            for (int i = 0; i < inventario.size(); i++) {
                try {
                    bw.write(inventario.get(i));
                bw.newLine();
                exportadas++;
                getTxtAreaProceso().append("Producto [" + (i + 1) + "] exportado correctamente...\n");
                getTxtAreaProceso().setCaretPosition(getTxtAreaProceso().getDocument().getLength());
                } catch (Exception e) {
                    getTxtAreaProceso().append("Producto [" + (i + 1) + "] no se pudo exportar: " + e + "...\n");
                    noexportadas++;
                    getTxtAreaProceso().setCaretPosition(getTxtAreaProceso().getDocument().getLength());
                }
                
            }
            bw.close();
            w.close();
//            List<String> lista = new ArrayList();
//            getLblDescripcion().setText("EXPORTANDO DATOS...");
//            for (int i = 0; i < inventario.size(); i++) {
//                try {
//                    lista.add(inventario.get(i));
//                    exportadas++;
//                    getTxtAreaProceso().append("Producto [" + (i + 1) + "] exportado correctamente...\n");
//                    getTxtAreaProceso().setCaretPosition(getTxtAreaProceso().getDocument().getLength());
//                } catch (Exception e) {
//                    
//                }
//
//                //AQUI ANADIR EL PRODUCTO                
//                revisadas++;
//                getBarraDeCarga().setValue(i);
//            }
////            new CSVData().addTempFile(getTemporal().toString(), lista);
//            Files.copy(getTemporal(), getDestino(), StandardCopyOption.REPLACE_EXISTING);
//            new File(getTemporal().toString()).delete();
        } catch (Exception e) {
            getTxtAreaProceso().append("\n\nHA OCURRIDO UN ERROR AL EXPORTAR DATOS: " + e + "\n\n");
        } finally {
            getBarraDeCarga().setValue(inventario.size());
            getLblDescripcion().setText("--PROCESO TERMINADO--");
            getTxtAreaProceso().append("\n--BASE DE DATOS EXORTADA CORRECTAMENTE--\n\n"
                    + "TOTAL FILAS REVISADAS: " + revisadas + "\n"
                    + "FILAS EXPORTADAS: " + exportadas + "\n"
                    + "FILAS NO EXPORTADAS: " + noexportadas + "\n\n");
            getTxtAreaProceso().setCaretPosition(getTxtAreaProceso().getDocument().getLength());
            getBotonCerrar().setEnabled(true);
            getBotonCerrar().requestFocus();
        }
        return 0;
    }

}
