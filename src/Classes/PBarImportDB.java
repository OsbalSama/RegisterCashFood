package Classes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author OSBAL
 */
public class PBarImportDB extends SwingWorker<Integer, String> {

    /**
     * @return the boton
     */
    public JLabel getLblDescripcion() {
        return lblDescripcion;
    }

    public void setLblDescripcion(JLabel lblDescripcion) {
        this.lblDescripcion = lblDescripcion;
    }

    public JTextArea getTxtAreaProceso() {
        return txtAreaProceso;
    }

    public void setTxtAreaProceso(JTextArea txtAreaProceso) {
        this.txtAreaProceso = txtAreaProceso;
    }

    public JProgressBar getBarraDeCarga() {
        return BarraDeCarga;
    }

    public void setBarraDeCarga(JProgressBar BarraDeCarga) {
        this.BarraDeCarga = BarraDeCarga;
    }

    public JButton getBotonCerrar() {
        return BotonCerrar;
    }

    public void setBotonCerrar(JButton BotonCerrar) {
        this.BotonCerrar = BotonCerrar;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public int[] getColsSeleccionadas() {
        return ColsSeleccionadas;
    }

    public void setColsSeleccionadas(int[] ColsSeleccionadas) {
        this.ColsSeleccionadas = ColsSeleccionadas;
    }

    private JLabel lblDescripcion;
    private JTextArea txtAreaProceso;
    private JProgressBar BarraDeCarga;
    private JButton BotonCerrar;

    String rutaArchivo;
    int[] ColsSeleccionadas;

    public PBarImportDB(JLabel lblDescripcion, JTextArea txtAreaProceso, JProgressBar BarraDeCarga, JButton BotonCerrar, String rutaArchivo, int[] ColsSeleccionadas) {
        this.lblDescripcion = lblDescripcion;
        this.txtAreaProceso = txtAreaProceso;
        this.BarraDeCarga = BarraDeCarga;
        this.BotonCerrar = BotonCerrar;
        this.rutaArchivo = rutaArchivo;
        this.ColsSeleccionadas = ColsSeleccionadas;
    }

    @Override
    protected Integer doInBackground() throws Exception {
        getLblDescripcion().setText("PROCESANDO DATOS...");
        getBarraDeCarga().setIndeterminate(true);
        //AQUI VA EL PROCESO
        int revisadas = 0;
        int aprobadas = 0;
        int noaprobadas = 0;
        int insertadas = 0;
        int noinsertadas = 0;
        try {
            List<String> database = new CSVData().getDatabaseCSV(getRutaArchivo());
            getLblDescripcion().setText("EVALUANDO FILAS PARA A IMPORTAR...");
            getTxtAreaProceso().append("EVALUANDO FILAS PARA A IMPORTAR:\n\n");
            List<String> ListaExcel = new ArrayList<String>();
            for (int i = 1; i < database.size(); i++) {
                String fila = new CSVData().getRowContent(database.get(i).split(","), getColsSeleccionadas());
                try {
                    if (!new CSVData().RowFormat(fila, getColsSeleccionadas())) {
                        noaprobadas++;
                        getTxtAreaProceso().append("Fila [" + i + "] " + new CSVData().getError(fila) + "...\n");
                    } else {
                        if (!new CSVData().repetida(fila, ListaExcel)) {
                            getTxtAreaProceso().append("Fila [" + i + "] Cumple con el formato...\n");
                            ListaExcel.add(fila);
                            aprobadas++;
                        } else {
                            noaprobadas++;
                            getTxtAreaProceso().append("Fila [" + i + "] esta repetida, no se añadira al inventario...\n");
                        }
                    }
                } catch (Exception e) {
                    getTxtAreaProceso().append("Fila [" + i + "] Error al comprobar formato...\n");
                    noaprobadas++;
                }
                revisadas++;
                getTxtAreaProceso().setCaretPosition(getTxtAreaProceso().getDocument().getLength());
            }
            getTxtAreaProceso().append("\n--PROCESO TERMINADO--\n");
            getLblDescripcion().setText("IMPORTANDO PRODUCTOS EN INVENTARIO...");
            if (ListaExcel.isEmpty()) {
                getTxtAreaProceso().append("\n\nLA LISTA PARA IMPORTAR ESTÁ VACÍA... NO SE INSERTARÁ NINGÚN ELEMENTO.\n\n");
            } else {
                getBarraDeCarga().setIndeterminate(false);
                getBarraDeCarga().setMinimum(0);
                getBarraDeCarga().setMaximum(ListaExcel.size());
                new CatProvClass().ClearDBCats();
                getTxtAreaProceso().append("\nAÑADIENDO CATEGORÍAS EN BASE DE DATOS:\n\n");
                getTxtAreaProceso().setCaretPosition(getTxtAreaProceso().getDocument().getLength());
                List<String> cats = new ArrayList();
                for (int i = 0; i < ListaExcel.size(); i++) {
                    String[] reg = ListaExcel.get(i).split(",");
                    if (!cats.contains(reg[3])) {
                        cats.add(reg[3]);
                        getTxtAreaProceso().append("Categoria [" + (i + 1) + "] cargada correctamente...\n");
                        getTxtAreaProceso().setCaretPosition(getTxtAreaProceso().getDocument().getLength());
                    }
                }
                getTxtAreaProceso().append("\n--PROCESO TERMINADO--\n\n");
                getTxtAreaProceso().setCaretPosition(getTxtAreaProceso().getDocument().getLength());
                for (int i = 0; i < cats.size(); i++) {
                    new CatProvClass().insCat(cats.get(i));
                    getTxtAreaProceso().append("Categoria [" + (i + 1) + "] Insertada correctamente...\n");
                    getTxtAreaProceso().setCaretPosition(getTxtAreaProceso().getDocument().getLength());
                }
                getTxtAreaProceso().append("\n--PROCESO TERMINADO--\n\n");
                getTxtAreaProceso().setCaretPosition(getTxtAreaProceso().getDocument().getLength());

//                getTxtAreaProceso().append("\nAÑADIENDO IMAGENES EN BASE DE DATOS:\n\n");
//                getTxtAreaProceso().setCaretPosition(getTxtAreaProceso().getDocument().getLength());
//                List<String> imgs = new ArrayList();
//                for (int i = 0; i < ListaExcel.size(); i++) {
//                    String[] reg = ListaExcel.get(i).split(",");
//                    if (!imgs.contains(reg[5])) {
//                        imgs.add(reg[5]);
//                        getTxtAreaProceso().append("imagen [" + (i + 1) + "] cargada correctamente...\n");
//                        getTxtAreaProceso().setCaretPosition(getTxtAreaProceso().getDocument().getLength());
//                    }
//                }
//                getTxtAreaProceso().append("\n--PROCESO TERMINADO--\n\n");
//                getTxtAreaProceso().setCaretPosition(getTxtAreaProceso().getDocument().getLength());
//                for (int i = 0; i < imgs.size(); i++) {
//                    if (new Dishes().existPhoto(imgs.get(i))) {
//                        //existPhoto
//                        new Dishes().addDishPhoto(imgs.get(i));
//                        getTxtAreaProceso().append("Imagen [" + (i + 1) + "] Insertada correctamente...\n");
//                        getTxtAreaProceso().setCaretPosition(getTxtAreaProceso().getDocument().getLength());
//                    } else {
//                        getTxtAreaProceso().append("Imagen [" + (i + 1) + "] no existe...\n");
//                    }
//                }
//                getTxtAreaProceso().append("\n--PROCESO TERMINADO--\n\n");
//                getTxtAreaProceso().setCaretPosition(getTxtAreaProceso().getDocument().getLength());

//                getTxtAreaProceso().append("\nAÑADIENDO MOSTRADORES EN BASE DE DATOS:\n\n");
//                getTxtAreaProceso().setCaretPosition(getTxtAreaProceso().getDocument().getLength());
//                List<String> mostradores = new ArrayList();
//                for (int i = 0; i < ListaExcel.size(); i++) {
//                    String[] reg = ListaExcel.get(i).split(",");
//                    if (!mostradores.contains(reg[3])) {
//                        mostradores.add(reg[3]);
//                        getTxtAreaProceso().append("Mostrador [" + (i+1) + "] cargado correctamente…...\n");
//                        getTxtAreaProceso().setCaretPosition(getTxtAreaProceso().getDocument().getLength());
//                    }
//                }
//                getTxtAreaProceso().append("\n--PROCESO TERMINADO--\n\n");
//                getTxtAreaProceso().setCaretPosition(getTxtAreaProceso().getDocument().getLength());
//                for (int i = 0; i < mostradores.size(); i++) {
//                    new CatProvClass().insMostrador(mostradores.get(i));
//                    getTxtAreaProceso().append("Mostrador [" + (i+1) + "] Insertado correctamente…...\n");
//                    getTxtAreaProceso().setCaretPosition(getTxtAreaProceso().getDocument().getLength());
//                }
//                getTxtAreaProceso().append("\n--PROCESO TERMINADO--\n");
//                getTxtAreaProceso().setCaretPosition(getTxtAreaProceso().getDocument().getLength());
                getTxtAreaProceso().append("\nAÑADIENDO PLATILLOS AL INVENTARIO:\n\n");
                getTxtAreaProceso().setCaretPosition(getTxtAreaProceso().getDocument().getLength());
                new Dishes().ClearDB();
                int cont = 0;
                for (int i = 0; i < ListaExcel.size(); i++) {
                    try {
                        if (new CSVData().RowFormat(ListaExcel.get(i), getColsSeleccionadas())) {
                            new Dishes().InsDish(ListaExcel.get(i));
                            getTxtAreaProceso().append("Fila [" + (i + 1) + "] Insertada correctamente…...\n");
                            insertadas++;
                        } else {
                            getTxtAreaProceso().append("Fila [" + (i + 1) + "] No se ha insertado...\n");
                            noinsertadas++;
                        }
                    } catch (Exception e) {
                        getTxtAreaProceso().append("Fila [" + (i + 1) + "] Error al insertar: " + e + "\n");
                        noinsertadas++;
                    }
                    getTxtAreaProceso().setCaretPosition(getTxtAreaProceso().getDocument().getLength());
                    cont++;
                    getBarraDeCarga().setValue(cont);
                }
                getBarraDeCarga().setValue(ListaExcel.size());
            }

        } catch (Exception e) {
            getTxtAreaProceso().append("\n\nHA OCURRIDO UN ERROR AL IMPORTAR BASE DE DATOS: " + e + "\n\n");
            getBarraDeCarga().setBackground(Color.RED);
        } finally {
            getLblDescripcion().setText("--PROCESO TERMINADO--");
            getTxtAreaProceso().append("\n--BASE DE DATOS IMPORTADA CORRECTAMENTE--\n\n"
                    + "TOTAL FILAS REVISADAS: " + revisadas + "\n"
                    + "FILAS APROBADAS: " + aprobadas + "\n"
                    + "FILAS IGNORADAS: " + noaprobadas + "\n"
                    + "FILAS INSERTADAS: " + insertadas + "\n"
                    + "FILAS NO INSERTADAS: " + noinsertadas + "\n\n");
            getTxtAreaProceso().setCaretPosition(getTxtAreaProceso().getDocument().getLength());
            getBarraDeCarga().setIndeterminate(false);
            getBotonCerrar().setEnabled(true);
            getBotonCerrar().requestFocus();
        }
        return 0;
    }

}
