/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.HeadlessException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

/**
 *
 * @author OSBAL
 */
public class PDFDocs {

    Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
    int margenLeft = 5;
    int margenRigth = 5;
    int margenTop = 10;
    int margenBottom = 10;

    public Font getFuente() {
        return fuente;
    }

    public void setFuente(Font fuente) {
        this.fuente = fuente;
    }

    public int getMargenLeft() {
        return margenLeft;
    }

    public void setMargenLeft(int margenLeft) {
        this.margenLeft = margenLeft;
    }

    public int getMargenRigth() {
        return margenRigth;
    }

    public void setMargenRigth(int margenRigth) {
        this.margenRigth = margenRigth;
    }

    public int getMargenTop() {
        return margenTop;
    }

    public void setMargenTop(int margenTop) {
        this.margenTop = margenTop;
    }

    public int getMargenBottom() {
        return margenBottom;
    }

    public void setMargenBottom(int margenBottom) {
        this.margenBottom = margenBottom;
    }

    //HISTORIAL CLIENTE
    public void ImprimirHistorialCliente(String[] ticketData, String[] Historial, String printer, int tamanoHoja) {
        String ruta = "docs\\Tickets_Compras\\Historial_" + ticketData[0] + ".pdf";
        try {
            switch (tamanoHoja) {
                case 0:
                    //p
                    CrearPDFHistorialMiniprinter(ruta, new Rectangle(150, 1000), TablaContenidoHistorialMiniprinter(ticketData, Historial));
                    PrintDocument(ruta, printer);
                    break;
                case 1:
                    //p
                    CrearPDFHistorialMiniprinter(ruta, new Rectangle(200, 1000), TablaContenidoHistorialMiniprinter(ticketData, Historial));
                    PrintDocument(ruta, printer);
                    break;
                case 2:
                    //p
                    CrearPDFHistorialCarta(ruta, PageSize.LETTER, TablaContenidoHistorialCarta(ticketData, Historial), TablaPieDePaginaCorteZ(ticketData));
                    PrintDocument(ruta, printer);
                    break;
            }
        } catch (Exception e) {
            int resp = JOptionPane.showConfirmDialog(null, "<html><h1>¡OOPS NO SE PUDO IMPRIMIR TICKET!</h1><font SIZE=5><p>¿Desea imprimirlo como PDF?</font></html>", "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                PrintDocument(ruta, "Imprimir como PDF");
            }
        }
    }

    //ESTA CLASE SE INVOCA PARA IMPRIMIR UN ESTADO DE RESULTADOS
    public void ImprimirCorteZ(String[] TicketData, String[] Totales, String[] Datos, String printer, int tamanoHoja) {
        String ruta = "docs\\EdoRes\\" + TicketData[0] + ".pdf";
        try {
            switch (tamanoHoja) {
                case 0:
                    CreatePDFMiniPrintEdoRes(ruta, new Rectangle(150, 1000), TablaCorteZMiniprint(TicketData, Totales, Datos));
                    PrintDocument(ruta, printer);
                    break;
                case 1:
                    CreatePDFMiniPrintEdoRes(ruta, new Rectangle(200, 1000), TablaCorteZMiniprint(TicketData, Totales, Datos));
                    PrintDocument(ruta, printer);
                    break;
                case 2:
                    CrearPDFCorteZCarta(ruta, PageSize.LETTER, TablaCorteZCarta(Totales, Datos), TablaPieDePaginaCorteZ(TicketData));
                    PrintDocument(ruta, printer);
                    break;
            }
        } catch (Exception e) {
            int resp = JOptionPane.showConfirmDialog(null, "<html><h1>¡OOPS NO SE PUDO IMPRIMIR TICKET!</h1><font SIZE=5><p>¿Desea imprimirlo como PDF?</font></html>", "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                PrintDocument(ruta, "Imprimir como PDF");
            }
        }
    }

    //imprimir comanda
    public void ImprimirComanda(String[] ticketData, String[] productos, String printer, int tamanoHoja) {
        String ruta = "docs\\Tickets_Compras\\" + ticketData[0] + ".pdf";
        try {
            switch (tamanoHoja) {
                case 0:
                    CrearPDFComandaMiniprinter(ruta, new Rectangle(150, 1000), TablaComandaMiniprinter(ticketData, productos));
                    PrintDocument(ruta, printer);
                    break;
                case 1:
                    CrearPDFComandaMiniprinter(ruta, new Rectangle(200, 1000), TablaComandaMiniprinter(ticketData, productos));
                    PrintDocument(ruta, printer);
                    break;
                case 2:
                    CrearPDFComandaMiniprinter(ruta, new Rectangle(200, 1000), TablaComandaMiniprinter(ticketData, productos));
                    PrintDocument(ruta, printer);
                    break;
            }
            new File(ruta).delete();
        } catch (Exception e) {
            int resp = JOptionPane.showConfirmDialog(null, "<html><h1>¡OOPS NO SE PUDO IMPRIMIR TICKET!</h1><font SIZE=5><p>¿Desea imprimirlo como PDF?</font></html>", "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                PrintDocument(ruta, "Imprimir como PDF");
            }
        }
    }

    //ESTA CLASE SE INVOCA PARA IMPRIMIR UNA NOTA DE PRUEBA
    public void ImprimirNotadePrueba(String[] ticketData, String[] productos, String printer, int tamanoHoja) {
        String ruta = "docs\\Tickets_Compras\\" + ticketData[0] + ".pdf";
        try {
            switch (tamanoHoja) {
                case 0:
                    CrearPDFVentaMiniprinter(ruta, new Rectangle(150, 1000), TablaContenidoVentaMiniprinter(ticketData, productos));
                    PrintDocument(ruta, printer);
                    break;
                case 1:
                    CrearPDFVentaMiniprinter(ruta, new Rectangle(200, 1000), TablaContenidoVentaMiniprinter(ticketData, productos));
                    PrintDocument(ruta, printer);
                    break;
                case 2:
                    CrearPDFVentaCarta(ruta, PageSize.LETTER, TablaContenidoVentaCarta(ticketData, productos), TablaPieDePaginaVentaCarta(ticketData));
                    PrintDocument(ruta, printer);
                    break;
            }
            new File(ruta).delete();
        } catch (Exception e) {
            int resp = JOptionPane.showConfirmDialog(null, "<html><h1>¡OOPS NO SE PUDO IMPRIMIR TICKET!</h1><font SIZE=5><p>¿Desea imprimirlo como PDF?</font></html>", "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                PrintDocument(ruta, "Imprimir como PDF");
            }
        }
    }

    //ESTA CLASE SE INVOCA PARA IMPRIMIR UNA NOTA DE VENTA
    public void ImprimirNoradeVenta(String[] IdUsrTot, String[] productos, String printer, int tamanoHoja) {
        String ruta = "docs\\Tickets_Compras\\" + IdUsrTot[0] + ".pdf";
        try {
            switch (tamanoHoja) {
                case 0:
                    CrearPDFVentaMiniprinter(ruta, new Rectangle(150, 1000), TablaContenidoVentaMiniprinter(IdUsrTot, productos));
                    PrintDocument(ruta, printer);
                    break;
                case 1:
                    CrearPDFVentaMiniprinter(ruta, new Rectangle(200, 1000), TablaContenidoVentaMiniprinter(IdUsrTot, productos));
                    PrintDocument(ruta, printer);
                    break;
                case 2:
                    CrearPDFVentaCarta(ruta, PageSize.LETTER, TablaContenidoVentaCarta(IdUsrTot, productos), TablaPieDePaginaVentaCarta(IdUsrTot));
                    PrintDocument(ruta, printer);
                    break;
            }
        } catch (Exception e) {
            int resp = JOptionPane.showConfirmDialog(null, "<html><h1>¡OOPS NO SE PUDO IMPRIMIR TICKET!</h1><font SIZE=5><p>¿Desea imprimirlo como PDF?</font></html>", "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                PrintDocument(ruta, "Imprimir como PDF");
            }
        }
    }

    //ESCOGER TIPO DE IMPRESION
    public void PrintDocument(String ruta, String impresora) {
        switch (impresora) {
            case "Imprimir como PDF":
                DigitalPrint(ruta);
                break;
            case "No Imprimir":
                JOptionPane.showMessageDialog(null, "<html><h1>VENTA REALIZADA CORRECTAMENTE:</h1><font SIZE=5><p>Clic para cerrar...<p></font></html>", "¡Info. de producto!", JOptionPane.INFORMATION_MESSAGE);
                break;
            default:
                PrintTicket(ruta, impresora);
                break;
        }
    }

    //OBTENER SERVICIO DE IMPRESION BUSCANDOLO POR NOMBRE
    public PrintService getPrintDevice(String nombre) {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService impresora : printServices) {
            if (impresora.getName().contentEquals(nombre)) {
                return impresora;
            }
        }
        return null;
    }

    //IMPRIMIR DOCUMENTO EN FISICO
    public void PrintTicket(String ruta, String printer) {
        try {
            File arch = new File(ruta);
            PDDocument document = PDDocument.load(arch);
            PrintService myPrintService = this.getPrintDevice(printer);
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            printerJob.setPageable(new PDFPageable(document));
            printerJob.setPrintService(myPrintService);
            printerJob.print();
            JOptionPane.showMessageDialog(null, "<html><h1>ARCHIVO IMPRESO CORRECTAMENTE</h1><font SIZE=5><p> Clic para cerrar…</font></html>", "¡Terminado!", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            int resp = JOptionPane.showConfirmDialog(null, "<html><h1>¡OOPS NO SE PUDO IMPRIMIR TICKET!</h1><font SIZE=5><p>¿Desea imprimirlo como PDF?</font></html>", "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                PrintDocument(ruta, "Imprimir como PDF");
            }
        }
    }

    //ESCOGER RUTA DE COPIADO DE ARCHIVO IMPRESO COMO PDF
    public void DigitalPrint(String ruta) {
        Path origen = FileSystems.getDefault().getPath(ruta);
        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos de PDF", "PDF", "pdf");
        chooser.setFileFilter(filtro);
        chooser.setSelectedFile(new File(new File(ruta).getName()));
        int retrival = chooser.showSaveDialog(null);
        if (retrival == JFileChooser.APPROVE_OPTION) {
            try {
                Path destino = FileSystems.getDefault().getPath(chooser.getSelectedFile().getAbsolutePath() + ".pdf");
                if (new File(destino.toString()).exists()) {
                    if (JOptionPane.showConfirmDialog(null, "Este archivo ya existe... \ndesea reemplazarlo?", "¡Atencion!", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                        Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
                        JOptionPane.showMessageDialog(null, "<html><h1>ARCHIVO GUARDADO CORRECTAMENTE</h1><font SIZE=5><p> Clic para cerrar…</font></html>", "¡Terminado!", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
                    JOptionPane.showMessageDialog(null, "<html><h1>ARCHIVO GUARDADO CORRECTAMENTE</h1><font SIZE=5><p> Clic para cerrar…</font></html>", "¡Terminado!", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (HeadlessException | IOException e) {
                JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //CREAR DOCUMENTO PDF HISTORIAL TAMAÑO MINIPRINT
    public void CrearPDFHistorialMiniprinter(String ruta, Rectangle tamaño, PdfPTable tablaContenido) {
        try {
            Document doc = new Document(tamaño, getMargenLeft(), getMargenRigth(), getMargenTop(), getMargenBottom());
            PdfWriter.getInstance(doc, new FileOutputStream(ruta));
            doc.open();
            doc.add(TablaEncabezadoVentaMiniprinter());
            doc.add(tablaContenido);
            doc.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error CrearPDFHistorialMiniprinter: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    //CREAR DOCUMENTO PDF VENTA TAMAÑO MINIPRINT
    public void CrearPDFVentaMiniprinter(String ruta, Rectangle tamaño, PdfPTable tablaContenido) {
        try {
            Document doc = new Document(tamaño, getMargenLeft(), getMargenRigth(), getMargenTop(), getMargenBottom());
            PdfWriter.getInstance(doc, new FileOutputStream(ruta));
            doc.open();
            doc.add(TablaEncabezadoVentaMiniprinter());
            doc.add(tablaContenido);
            doc.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    //CREAR DOCUMENTO PDF COMANDA TAMAÑO MINIPRINT
    public void CrearPDFComandaMiniprinter(String ruta, Rectangle tamaño, PdfPTable tablaContenido) {
        try {
            Document doc = new Document(tamaño, getMargenLeft(), getMargenRigth(), getMargenTop(), getMargenBottom());
            PdfWriter.getInstance(doc, new FileOutputStream(ruta));
            doc.open();
            doc.add(tablaContenido);
            doc.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    //TABLA COMANDA TAMAÑO MINIPRINT
    public PdfPTable TablaComandaMiniprinter(String[] ticketData, String[] productos) {
        //idv_usr_total[0] = ID_REGISTRO
        //idv_usr_total[1] = USUARIO
        //idv_usr_total[2] = 1000 (TOTAL)
        //idv_usr_total[3] = MESA
        //idv_usr_total[4] = ID CLIENTE     
        //idv_usr_total[5] = COSTE ENVIO  
        int tamanoCols = 8;
        PdfPTable table = new PdfPTable(tamanoCols);
        String[] datos_imp = new Configurations().getTicketConfigs();
        table.setWidthPercentage(100);
        PdfPCell celda;
        Paragraph p;
        int tamaño = 6;
        int border = 0;
        try {

            celda = new PdfPCell();
            p = new Paragraph(ticketData[0], FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            celda.setColspan(tamanoCols);
            celda.setBorder(border);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph(ticketData[3], FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            celda.setColspan(tamanoCols);
            celda.setBorder(border);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("PRODUCTOS EN COMANDA", FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            celda.setColspan(tamanoCols);
            celda.setBorder(border);
            table.addCell(celda);

            celda = new PdfPCell();
            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(tamanoCols);
            celda.setBorder(border);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell(new Paragraph("PLATILLO", FontFactory.getFont(getFuente().toString(), tamaño - 2)));
            celda.setBorder(border);
            celda.setColspan(tamanoCols - 1);
            table.addCell(celda);

            celda = new PdfPCell(new Phrase("CANT", FontFactory.getFont(getFuente().toString(), tamaño - 2)));
            celda.setBorder(border);
            table.addCell(celda);

            for (int i = 0; i < productos.length; i++) {
                String[] productoInd = productos[i].split(",");
                celda = new PdfPCell(new Phrase(productoInd[0].toUpperCase(), FontFactory.getFont(getFuente().toString(), tamaño - 2)));
                celda.setBorder(border);
                celda.setColspan(tamanoCols - 1);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(productoInd[2], FontFactory.getFont(getFuente().toString(), tamaño - 2));
                p.setAlignment(Element.ALIGN_CENTER);
                celda.addElement(p);
                celda.setBorder(border);
                table.addCell(celda);
            }//añadir productos

            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(tamanoCols);
            celda.setBorder(border);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell(new Paragraph(" "));
            celda.setColspan(tamanoCols);
            celda.setBorder(2);
            table.addCell(celda);//CORTAR

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return table;
    }

    //CREAR DOCUMENTO PDF VENTA TAMAÑO CARTA EN Register_Cash_v1.0.1\docs\Tickets_Compras
    public void CrearPDFVentaCarta(String ruta, Rectangle tamaño, PdfPTable tablaContenido, PdfPTable tableFooter) {
        try {
            Document doc = new Document(tamaño, getMargenLeft() + 20, getMargenRigth() + 20, getMargenTop(), getMargenBottom());
            PdfWriter pdfw = PdfWriter.getInstance(doc, new FileOutputStream(ruta));
            doc.open();
            doc.add(TablaEncabezadoVentaCarta());
            doc.add(tablaContenido);
            tableFooter.setTotalWidth(doc.right(doc.rightMargin()));
            tableFooter.writeSelectedRows(0, -1, getMargenLeft() + 20, tableFooter.getTotalHeight() + doc.bottom(doc.bottomMargin()), pdfw.getDirectContent());
            doc.close();
        } catch (Exception e) {
            int resp = JOptionPane.showConfirmDialog(null, "<html><h1>¡OOPS NO SE PUDO IMPRIMIR TICKET!</h1><font SIZE=5><p>¿Desea imprimirlo como PDF?</font></html>", "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                PrintDocument(ruta, "Imprimir como PDF");
            }
        }
    }

    //CREAR DOCUMENTO PDF VENTA TAMAÑO CARTA EN Register_Cash_v1.0.1\docs\Tickets_Compras
    public void CrearPDFHistorialCarta(String ruta, Rectangle tamaño, PdfPTable tablaContenido, PdfPTable tableFooter) {
        try {
            Document doc = new Document(tamaño, getMargenLeft(), getMargenRigth(), getMargenTop(), getMargenBottom());
            PdfWriter pdfw = PdfWriter.getInstance(doc, new FileOutputStream(ruta));
            doc.open();
            doc.add(TablaEncabezadoVentaCarta());
            doc.add(tablaContenido);
            tableFooter.setTotalWidth(doc.right(doc.rightMargin()));
            tableFooter.writeSelectedRows(0, -1, getMargenLeft(), tableFooter.getTotalHeight() + doc.bottom(doc.bottomMargin()), pdfw.getDirectContent());
            doc.close();
        } catch (Exception e) {
            int resp = JOptionPane.showConfirmDialog(null, "<html><h1>¡OOPS NO SE PUDO IMPRIMIR TICKET!</h1><font SIZE=5><p>¿Desea imprimirlo como PDF?</font></html>", "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                PrintDocument(ruta, "Imprimir como PDF");
            }
        }
    }

    //TABLA HISTORIAL TAMAÑO MINIPRINT
    public PdfPTable TablaContenidoHistorialMiniprinter(String[] ticketData, String[] productos) {
        //idv_usr_total[0] = ID_CLIENTE
        //idv_usr_total[1] = USUARIO

        PdfPTable table = new PdfPTable(5);
        String[] datos_imp = new Configurations().getTicketConfigs();
        table.setWidthPercentage(100);
        PdfPCell celda;
        Paragraph p;
        int tamaño = 5;
        int border = 0;
        try {
            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(5);
            celda.setBorder(border);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("HISTORIAL CLIENTE", FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            celda.setColspan(5);
            celda.setBorder(border);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph(new Clients().getClienttoString(ticketData[0]), FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            celda.setColspan(5);
            celda.setBorder(border);
            table.addCell(celda);

            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(5);
            celda.setBorder(border);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("ORDEN", FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_LEFT);
            celda.setBorder(border);
            celda.addElement(p);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("MESA", FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_LEFT);
            celda.setBorder(border);
            celda.addElement(p);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("TOTAL", FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.setBorder(border);
            celda.addElement(p);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("ENVIO", FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.setBorder(border);
            celda.addElement(p);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("ESTADO", FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_LEFT);
            celda.setBorder(border);
            celda.addElement(p);
            table.addCell(celda);

            for (String producto : productos) {
                String[] productoInd = producto.split(",");

                celda = new PdfPCell();
                p = new Paragraph(productoInd[0].toUpperCase(), FontFactory.getFont(getFuente().toString(), tamaño - 1));
                p.setAlignment(Element.ALIGN_LEFT);
                celda.setBorder(border);
                celda.addElement(p);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(productoInd[1].toUpperCase(), FontFactory.getFont(getFuente().toString(), tamaño));
                p.setAlignment(Element.ALIGN_LEFT);
                celda.setBorder(border);
                celda.addElement(p);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(new FormatoNumerico().AsignarFormatoMoneda(productoInd[2]), FontFactory.getFont(getFuente().toString(), tamaño));
                p.setAlignment(Element.ALIGN_CENTER);
                celda.setBorder(border);
                celda.addElement(p);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(new FormatoNumerico().AsignarFormatoMoneda(productoInd[3]), FontFactory.getFont(getFuente().toString(), tamaño));
                p.setAlignment(Element.ALIGN_CENTER);
                celda.setBorder(border);
                celda.addElement(p);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(productoInd[4].toUpperCase(), FontFactory.getFont(getFuente().toString(), tamaño - 1));
                p.setAlignment(Element.ALIGN_LEFT);
                celda.setBorder(border);
                celda.addElement(p);
                table.addCell(celda);

            } //añadir productos

            String text = "FECHA: " + new SimpleDateFormat("dd-MM-YYYY").format(new Date()) + ", -- HORA: " + new SimpleDateFormat("HH:mm").format(new Date())
                    + "\nREALIZADO POR: " + ticketData[1].toUpperCase();
            celda = new PdfPCell();
            p = new Paragraph(text, FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.setBorder(border);
            celda.setColspan(5);
            celda.addElement(p);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.setColspan(5);
            celda.setBorder(border);
            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(new Barcodes().ImgBC128(ticketData[0]));
            celda.addElement(image);
            p = new Paragraph("FOLIO: " + ticketData[0], FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            table.addCell(celda);

            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), tamaño - 1)));
            celda.setColspan(5);
            celda.setBorder(2);
            table.addCell(celda);//CORTAR
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error TablaContenidoHistorialMiniprinter: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return table;
    }

    //TABLA VENTA TAMAÑO MINIPRINT
    public PdfPTable TablaContenidoVentaMiniprinter(String[] ticketData, String[] productos) {
        //idv_usr_total[0] = ID_REGISTRO
        //idv_usr_total[1] = USUARIO
        //idv_usr_total[2] = 1000 (TOTAL)
        //idv_usr_total[3] = MESA
        //idv_usr_total[4] = ID CLIENTE     
        //idv_usr_total[5] = COSTE ENVIO      
        int Celdas = 7;
        PdfPTable table = new PdfPTable(Celdas);
        String[] datos_imp = new Configurations().getTicketConfigs();

        table.setWidthPercentage(100);
        PdfPCell celda;
        Paragraph p;
        int tamaño = 6;
        int border = 0;
        try {
            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(Celdas);
            celda.setBorder(border);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("PRODUCTOS VENDIDOS", FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            celda.setColspan(Celdas);
            celda.setBorder(border);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("PLATILLO".toUpperCase(), FontFactory.getFont(getFuente().toString(), tamaño - 2));
            p.setAlignment(Element.ALIGN_LEFT);
            celda.setBorder(border);
            celda.setColspan(Celdas - 3);
            celda.addElement(p);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("P_UNIT", FontFactory.getFont(getFuente().toString(), tamaño - 2));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            celda.setBorder(border);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("CANT", FontFactory.getFont(getFuente().toString(), tamaño - 2));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            celda.setBorder(border);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("TOTAL", FontFactory.getFont(getFuente().toString(), tamaño - 2));
            p.setAlignment(Element.ALIGN_RIGHT);
            celda.addElement(p);
            celda.setBorder(border);
            table.addCell(celda);

            for (int i = 0; i < productos.length; i++) {
                String[] productoInd = productos[i].split(",");

                celda = new PdfPCell();
                p = new Paragraph(productoInd[0].toUpperCase(), FontFactory.getFont(getFuente().toString(), tamaño - 1));
                p.setAlignment(Element.ALIGN_LEFT);
                celda.setBorder(border);
                celda.setColspan(Celdas - 3);
                celda.addElement(p);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(new FormatoNumerico().AsignarFormatoMoneda(productoInd[1]), FontFactory.getFont(getFuente().toString(), tamaño - 1));
                p.setAlignment(Element.ALIGN_CENTER);
                celda.addElement(p);
                celda.setBorder(border);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(productoInd[2], FontFactory.getFont(getFuente().toString(), tamaño - 1));
                p.setAlignment(Element.ALIGN_CENTER);
                celda.addElement(p);
                celda.setBorder(border);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(new FormatoNumerico().AsignarFormatoMoneda(productoInd[3]), FontFactory.getFont(getFuente().toString(), tamaño - 1));
                p.setAlignment(Element.ALIGN_RIGHT);
                celda.addElement(p);
                celda.setBorder(border);
                table.addCell(celda);
            }//añadir productos

            if (Double.parseDouble(ticketData[5]) > 0) {
                celda = new PdfPCell(new Paragraph(" "));
                celda.setColspan(Celdas - 2);
                celda.setBorder(border);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph("Envio: " + new FormatoNumerico().AsignarFormatoMoneda(ticketData[5]), FontFactory.getFont(getFuente().toString(), tamaño));
                p.setAlignment(Element.ALIGN_RIGHT);
                celda.addElement(p);
                celda.setColspan(2);
                celda.setBorder(border);
                table.addCell(celda);
            }

            celda = new PdfPCell(new Paragraph(" "));
            celda.setColspan(Celdas - 2);
            celda.setBorder(border);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("Total: " + new FormatoNumerico().AsignarFormatoMoneda(ticketData[2]), FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_RIGHT);
            celda.addElement(p);
            celda.setColspan(2);
            celda.setBorder(border);
            table.addCell(celda);

            if (new Clients().existID(ticketData[4])) {
                celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
                celda.setColspan(Celdas);
                celda.setBorder(border);
                celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(new Clients().getClienttoString(ticketData[4]), FontFactory.getFont(getFuente().toString(), tamaño));
                p.setAlignment(Element.ALIGN_CENTER);
                celda.addElement(p);
                celda.setColspan(Celdas);
                celda.setBorder(border);
                table.addCell(celda);

                celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
                celda.setColspan(Celdas);
                celda.setBorder(border);
                celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(celda);
            } else {
                celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
                celda.setColspan(Celdas);
                celda.setBorder(border);
                celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph("VENTA REALIZADA EN MOSTRADOR DE TIENDA", FontFactory.getFont(getFuente().toString(), tamaño));
                p.setAlignment(Element.ALIGN_CENTER);
                celda.addElement(p);
                celda.setColspan(Celdas);
                celda.setBorder(border);
                table.addCell(celda);

                celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
                celda.setColspan(Celdas);
                celda.setBorder(border);
                celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(celda);
            }

            p = new Paragraph("FECHA: " + new SimpleDateFormat("dd-MM-YYYY").format(new Date()) + ", -- HORA: " + new SimpleDateFormat("HH:mm").format(new Date())
                    + "\nLE ATENDIO: " + ticketData[1].toUpperCase()
                    + "\nFORMA DE PAGO: EFECTIVO", FontFactory.getFont(getFuente().toString(), tamaño));
            celda = new PdfPCell(p);
            celda.setColspan(Celdas);
            celda.setBorder(border);
            p.setAlignment(Element.ALIGN_CENTER);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.setColspan(Celdas);
            celda.setBorder(border);
            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(new Barcodes().ImgBC128(ticketData[0]));
            celda.addElement(image);
            p = new Paragraph("FOLIO: " + ticketData[0], FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph(datos_imp[2].toUpperCase(), FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            celda.setColspan(Celdas);
            celda.setBorder(border);
            table.addCell(celda);

            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(Celdas);
            celda.setBorder(border);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), tamaño - 1)));
            celda.setColspan(Celdas);
            celda.setBorder(2);
            table.addCell(celda);//CORTAR
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return table;
    }

    //ENCABEZADO TICKET VENTA TAMAÑO MINIPRINT
    public PdfPTable TablaEncabezadoVentaMiniprinter() {
        PdfPTable table = new PdfPTable(4);
        String[] cont = new DatEmpresa().getDatEmpresa();
        table.setWidthPercentage(100);
        PdfPCell celda;
        Paragraph p;
        int tamaño = 6;
        int border = 0;
        try {
            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(4);
            celda.setBorder(border);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            celda.setColspan(4);
            File f;
            if (new File("iconos\\LOGO.png").exists()) {
                f = new File("iconos\\LOGO.png");
            } else {
                f = new File("iconos\\LOGO.png");
            }
            com.itextpdf.text.Image imagen = com.itextpdf.text.Image.getInstance(f.getAbsolutePath());
            imagen.scaleToFit(200, 50);
            imagen.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(imagen);
            celda.setBorder(border);
            table.addCell(celda);//logo 

            celda = new PdfPCell();
            String text = cont[1] + "\n"
                    + "RFC:" + cont[2] + "\n"
                    + "ENCARGADO:" + cont[3] + "\n"
                    + "DIRECCION:" + cont[4] + "\n"
                    + "TELEFONO:" + cont[5] + "\n\n";
            p = new Paragraph(text, FontFactory.getFont(getFuente().toString(), tamaño));
            celda.setColspan(4);
            celda.setBorder(border);
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            table.addCell(celda);//DATOS EMPRESA
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return table;
    }

    //TABLA VENTA TAMAÑO CATRA
    public PdfPTable TablaContenidoVentaCarta(String[] ticketData, String[] productos) {
        //idv_usr_total[0] = ID_REGISTRO
        //idv_usr_total[1] = USUARIO
        //idv_usr_total[2] = 1000 (TOTAL)
        //idv_usr_total[3] = MESA
        //idv_usr_total[4] = ID CLIENTE
        PdfPTable table = new PdfPTable(4);
        int tamaño = 9;
        table.setWidthPercentage(100);
        PdfPCell celda;
        String[] cont = new DatEmpresa().getDatEmpresa();
        try {
            Paragraph p;
            p = new Paragraph("PRODUCTOS VENDIDOS", FontFactory.getFont(getFuente().toString(), tamaño + 3));
            p.setAlignment(Element.ALIGN_CENTER);
            celda = new PdfPCell();
            celda.addElement(p);
            celda.setColspan(4);
            table.addCell(celda);

            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(4);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);//SEPARADOR

            celda = new PdfPCell();
            p = new Paragraph("Producto", FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_LEFT);
            celda.addElement(p);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("Precio", FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("Cantidad", FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("Total", FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_RIGHT);
            celda.addElement(p);
            table.addCell(celda);

            for (int i = 0; i < productos.length; i++) {
                String[] productoInd = productos[i].split(",");

                celda = new PdfPCell();
                p = new Paragraph(productoInd[0], FontFactory.getFont(getFuente().toString(), tamaño));
                p.setAlignment(Element.ALIGN_LEFT);
                celda.addElement(p);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(new FormatoNumerico().AsignarFormatoMoneda(productoInd[1]), FontFactory.getFont(getFuente().toString(), tamaño));
                p.setAlignment(Element.ALIGN_CENTER);
                celda.addElement(p);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(productoInd[2], FontFactory.getFont(getFuente().toString(), tamaño));
                p.setAlignment(Element.ALIGN_CENTER);
                celda.addElement(p);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(new FormatoNumerico().AsignarFormatoMoneda(productoInd[3]), FontFactory.getFont(getFuente().toString(), tamaño));
                p.setAlignment(Element.ALIGN_RIGHT);
                celda.addElement(p);
                table.addCell(celda);
            }

            if (Double.parseDouble(ticketData[5]) > 0) {
                celda = new PdfPCell(new Paragraph(" "));
                celda.setColspan(3);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph("Envio: " + new FormatoNumerico().AsignarFormatoMoneda(ticketData[5]), FontFactory.getFont(getFuente().toString(), tamaño));
                p.setAlignment(Element.ALIGN_RIGHT);
                celda.addElement(p);
                table.addCell(celda);
            }

            celda = new PdfPCell(new Paragraph(" "));
            celda.setColspan(3);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("Total: " + new FormatoNumerico().AsignarFormatoMoneda(ticketData[2]), FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_RIGHT);
            celda.addElement(p);
            table.addCell(celda);

            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(4);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);//SEPARADOR
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return table;
    }

    //TABLA VENTA TAMAÑO CATRA
    public PdfPTable TablaContenidoHistorialCarta(String[] ticketData, String[] productos) {
        //idv_usr_total[0] = ID_CLIENTE
        //idv_usr_total[1] = USUARIO
        PdfPTable table = new PdfPTable(5);
        int tamaño = 9;
        table.setWidthPercentage(100);
        PdfPCell celda;
        String[] cont = new DatEmpresa().getDatEmpresa();
        try {
            Paragraph p = new Paragraph(cont[1] + "\n", FontFactory.getFont(getFuente().toString(), tamaño + 15));
            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(5);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);//SEPARADOR

            p = new Paragraph("HISTORIAL DEL CLIENTE\n"
                    + new Clients().getClienttoString(ticketData[0]), FontFactory.getFont(getFuente().toString(), tamaño + 3));
            p.setAlignment(Element.ALIGN_CENTER);
            celda = new PdfPCell();
            celda.addElement(p);
            celda.setColspan(5);
            table.addCell(celda);
            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(5);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);//SEPARADOR

            table.addCell(new Phrase("ORDEN", FontFactory.getFont(getFuente().toString(), tamaño)));
            table.addCell(new Phrase("MESA", FontFactory.getFont(getFuente().toString(), tamaño)));
            table.addCell(new Phrase("COSTE", FontFactory.getFont(getFuente().toString(), tamaño)));
            table.addCell(new Phrase("ENVIO", FontFactory.getFont(getFuente().toString(), tamaño)));
            table.addCell(new Phrase("ESTADO", FontFactory.getFont(getFuente().toString(), tamaño)));

            for (int i = 0; i < productos.length; i++) {
                String[] productoInd = productos[i].split(",");
                table.addCell(new Phrase(productoInd[0], FontFactory.getFont(getFuente().toString(), tamaño)));
                table.addCell(new Phrase(productoInd[1], FontFactory.getFont(getFuente().toString(), tamaño)));
                table.addCell(new Phrase(new FormatoNumerico().AsignarFormatoMoneda(productoInd[2]), FontFactory.getFont(getFuente().toString(), tamaño)));
                table.addCell(new Phrase(new FormatoNumerico().AsignarFormatoMoneda(productoInd[3]), FontFactory.getFont(getFuente().toString(), tamaño)));
                table.addCell(new Phrase(productoInd[4], FontFactory.getFont(getFuente().toString(), tamaño)));
            }
            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(5);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);//SEPARADOR
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return table;
    }

    //ENCABEZADO TICKET VENTA TAMAÑO CARTA
    public PdfPTable TablaEncabezadoVentaCarta() {
        PdfPTable table = new PdfPTable(4);
        int tamaño = 9;
        table.setWidthPercentage(100);
        PdfPCell celda = new PdfPCell();
        String[] cont = new DatEmpresa().getDatEmpresa();
        try {

            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(4);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);//SEPARADOR ENCABEZADO
            File f;
            if (new File("iconos\\LOGO.png").exists()) {
                f = new File("iconos\\LOGO.png");
            } else {
                f = new File("iconos\\LOGO.png");
            }
            com.itextpdf.text.Image imagen = com.itextpdf.text.Image.getInstance(f.getAbsolutePath());
            imagen.scaleToFit(150, 75);
            imagen.setAlignment(Element.ALIGN_CENTER);
            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(2);
            celda.addElement(imagen);
            table.addCell(celda);// LOGO  

            celda = new PdfPCell();
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setColspan(3);
            Paragraph p = new Paragraph(cont[1] + "\n", FontFactory.getFont(getFuente().toString(), tamaño + 8));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);

            String text = ""
                    + "RFC: " + cont[2] + "\n"
                    + "ENCARGADO: " + cont[3] + "\n"
                    + "DIRECCION: " + cont[4] + "\n"
                    + "TELEFONO: " + cont[5] + "\n\n";
            p = new Paragraph(text, FontFactory.getFont(getFuente().toString(), 6));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            table.addCell(celda);//DATOS EMPRESA

            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(4);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);//SEPARADOR
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return table;
    }

    //PIE DE PAGINA TABLA VENTA TAMAÑO MINIPRINT
    public PdfPTable TablaPieDePaginaHistorialCarta(String[] ticketData) {
        //idv_usr_total[0] = ID_REGISTRO
        //idv_usr_total[1] = USUARIO
        //idv_usr_total[2] = 1000 (TOTAL)
        //idv_usr_total[3] = MESA
        //idv_usr_total[4] = ID CLIENTE 
        PdfPTable table = new PdfPTable(4);
        String[] datos_imp = new Configurations().getTicketConfigs();
        int tamaño = 9;
        Paragraph p;
        table.setWidthPercentage(100);
        try {
            PdfPCell celda;
            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(4);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);//SEPARADOR

            if (new Clients().existID(ticketData[4])) {
                celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(new Clients().getClienttoString(ticketData[4]), FontFactory.getFont(getFuente().toString(), tamaño));
                p.setAlignment(Element.ALIGN_CENTER);
                celda.addElement(p);
                table.addCell(celda);
            } else {
                celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
                celda.setColspan(2);
                table.addCell(celda);
            }

            p = new Paragraph("Fecha: " + new SimpleDateFormat("dd-MM-YYYY").format(new Date()) + ", " + new SimpleDateFormat("HH:mm").format(new Date())
                    + "\nLe atendio: " + ticketData[1]);
            celda = new PdfPCell(p);
            p.setAlignment(Element.ALIGN_CENTER);
            table.addCell(celda);

            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(new Barcodes().ImgBC128(ticketData[0]));
            celda.addElement(image);
            p = new Paragraph("Folio: " + ticketData[0], FontFactory.getFont(getFuente().toString(), tamaño + 3));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            table.addCell(celda);

            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(4);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph(datos_imp[2], FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            celda.setColspan(4);
            table.addCell(celda);

            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(4);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);//SEPARADOR
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return table;
    }

    //PIE DE PAGINA TABLA VENTA TAMAÑO MINIPRINT
    public PdfPTable TablaPieDePaginaVentaCarta(String[] ticketData) {
        //idv_usr_total[0] = ID_REGISTRO
        //idv_usr_total[1] = USUARIO
        //idv_usr_total[2] = 1000 (TOTAL)
        //idv_usr_total[3] = MESA
        //idv_usr_total[4] = ID CLIENTE 
        PdfPTable table = new PdfPTable(4);
        String[] datos_imp = new Configurations().getTicketConfigs();
        int tamaño = 9;
        Paragraph p;
        table.setWidthPercentage(100);
        try {
            PdfPCell celda;
            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(4);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);//SEPARADOR

            if (new Clients().existID(ticketData[4])) {
                celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(new Clients().getClienttoString(ticketData[4]), FontFactory.getFont(getFuente().toString(), tamaño));
                p.setAlignment(Element.ALIGN_CENTER);
                celda.addElement(p);
                table.addCell(celda);
            } else {
                celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
                celda.setColspan(2);
                table.addCell(celda);
            }

            p = new Paragraph("Fecha: " + new SimpleDateFormat("dd-MM-YYYY").format(new Date()) + ", " + new SimpleDateFormat("HH:mm").format(new Date())
                    + "\nLe atendio: " + ticketData[1]
                    + "\nForma de pago: EFECTIVO", FontFactory.getFont(getFuente().toString(), tamaño));
            celda = new PdfPCell(p);
            p.setAlignment(Element.ALIGN_CENTER);
            table.addCell(celda);

            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(new Barcodes().ImgBC128(ticketData[0]));
            celda.addElement(image);
            p = new Paragraph("Folio: " + ticketData[0], FontFactory.getFont(getFuente().toString(), tamaño + 3));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            table.addCell(celda);

            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(4);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph(datos_imp[2], FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            celda.setColspan(4);
            table.addCell(celda);

            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(4);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);//SEPARADOR
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return table;
    }

    //ENCABEZADO TICKET CORTE Z TAMAÑO CARTA
    public PdfPTable TablaEncabezadoCorteZCarta() {
        PdfPTable table = new PdfPTable(4);
        int tamaño = 9;
        table.setWidthPercentage(100);
        PdfPCell celda = new PdfPCell();
        String[] cont = new DatEmpresa().getDatEmpresa();
        try {

            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(4);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);//SEPARADOR ENCABEZADO
            File f;
            if (new File("iconos\\LOGO.png").exists()) {
                f = new File("iconos\\LOGO.png");
            } else {
                f = new File("iconos\\LOGO.png");
            }
            com.itextpdf.text.Image imagen = com.itextpdf.text.Image.getInstance(f.getAbsolutePath());
            imagen.scaleToFit(150, 75);
            imagen.setAlignment(Element.ALIGN_CENTER);
            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(2);
            celda.addElement(imagen);
            table.addCell(celda);// LOGO  

            celda = new PdfPCell();
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setColspan(3);
            Paragraph p = new Paragraph(cont[1] + "\n", FontFactory.getFont(getFuente().toString(), tamaño + 8));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);

            String text = ""
                    + "RFC: " + cont[2] + "\n"
                    + "ENCARGADO: " + cont[3] + "\n"
                    + "DIRECCION: " + cont[4] + "\n"
                    + "TELEFONO: " + cont[5] + "\n\n";
            p = new Paragraph(text, FontFactory.getFont(getFuente().toString(), 6));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            table.addCell(celda);//DATOS EMPRESA

            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(4);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);//SEPARADOR
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return table;
    }

    //PARTES TABLA TAMAÑO CARTA ESTADO DE RESULTADOS
    public PdfPTable TablaCorteZCarta(String[] totales, String[] Datos) {
        int tamano = 8;
        PdfPTable table = new PdfPTable(tamano);
        int tamaño = 8;
        table.setWidthPercentage(100);
        PdfPCell celda;
        String[] cont = new DatEmpresa().getDatEmpresa();
        try {
            Paragraph p;

            p = new Paragraph("CORTE DE CAJA", FontFactory.getFont(getFuente().toString(), tamaño + 3));
            p.setAlignment(Element.ALIGN_CENTER);
            celda = new PdfPCell();
            celda.addElement(p);
            celda.setColspan(tamano);
            table.addCell(celda);

            p = new Paragraph("PRODUCTOS VENDIDOS", FontFactory.getFont(getFuente().toString(), tamaño + 3));
            p.setAlignment(Element.ALIGN_CENTER);
            celda = new PdfPCell();
            celda.addElement(p);
            celda.setColspan(tamano);
            table.addCell(celda);

            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(tamano);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);//SEPARADOR

            celda = new PdfPCell();
            p = new Paragraph("ORDEN", FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_LEFT);
            celda.addElement(p);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("MESA", FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_LEFT);
            celda.addElement(p);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("TOTAL", FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("ENVIO", FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("ESTADO", FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_LEFT);
            celda.addElement(p);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("CLIENTE", FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_LEFT);
            celda.addElement(p);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("FECHA VENTA", FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("HORA VENTA", FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            table.addCell(celda);

            for (int i = 0; i < Datos.length; i++) {
                String[] productoInd = Datos[i].split(",");

                celda = new PdfPCell();
                p = new Paragraph(productoInd[0], FontFactory.getFont(getFuente().toString(), tamaño));
                p.setAlignment(Element.ALIGN_LEFT);
                celda.addElement(p);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(productoInd[1], FontFactory.getFont(getFuente().toString(), tamaño));
                p.setAlignment(Element.ALIGN_LEFT);
                celda.addElement(p);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(new FormatoNumerico().AsignarFormatoMoneda(productoInd[2]), FontFactory.getFont(getFuente().toString(), tamaño));
                p.setAlignment(Element.ALIGN_CENTER);
                celda.addElement(p);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(new FormatoNumerico().AsignarFormatoMoneda(productoInd[3]), FontFactory.getFont(getFuente().toString(), tamaño));
                p.setAlignment(Element.ALIGN_CENTER);
                celda.addElement(p);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(productoInd[4], FontFactory.getFont(getFuente().toString(), tamaño));
                p.setAlignment(Element.ALIGN_LEFT);
                celda.addElement(p);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(productoInd[5], FontFactory.getFont(getFuente().toString(), tamaño));
                p.setAlignment(Element.ALIGN_LEFT);
                celda.addElement(p);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(productoInd[6], FontFactory.getFont(getFuente().toString(), tamaño));
                p.setAlignment(Element.ALIGN_CENTER);
                celda.addElement(p);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(productoInd[7], FontFactory.getFont(getFuente().toString(), tamaño));
                p.setAlignment(Element.ALIGN_CENTER);
                celda.addElement(p);
                table.addCell(celda);
            }

            celda = new PdfPCell(new Paragraph(" "));
            celda.setColspan(tamano - 3);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("TOTAL VENTAS: " + new FormatoNumerico().AsignarFormatoMoneda(totales[0]), FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_RIGHT);
            celda.addElement(p);
            celda.setColspan(tamano);
            table.addCell(celda);

            celda = new PdfPCell(new Paragraph(" "));
            celda.setColspan(tamano - 3);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("TOTAL PENDS: " + new FormatoNumerico().AsignarFormatoMoneda(totales[1]), FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_RIGHT);
            celda.addElement(p);
            celda.setColspan(tamano);
            table.addCell(celda);

//            celda = new PdfPCell(new Phrase("TOTAL PENDS: " + new FormatoNumerico().AsignarFormatoMoneda(totales[1]), FontFactory.getFont(getFuente().toString(), tamaño)));
//            celda.setColspan(tamano);
//            table.addCell(celda);
            celda = new PdfPCell(new Paragraph(" "));
            celda.setColspan(tamano - 3);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("TOTAL CANCEL: " + new FormatoNumerico().AsignarFormatoMoneda(totales[2]), FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_RIGHT);
            celda.addElement(p);
            celda.setColspan(tamano);
            table.addCell(celda);

//            celda = new PdfPCell(new Phrase("TOTAL CANCEL: " + new FormatoNumerico().AsignarFormatoMoneda(totales[2]), FontFactory.getFont(getFuente().toString(), tamaño)));
//            celda.setColspan(tamano);
//            table.addCell(celda);
            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(tamano);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);//SEPARADOR
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return table;
    }

    //PIE DE PAGINA TABLA CORTEZ    
    public PdfPTable TablaPieDePaginaCorteZ(String[] TicketData) {
        //idv_usr_total[0] = ID_REGISTRO
        //idv_usr_total[1] = USUARIO
        PdfPTable table = new PdfPTable(7);
        int tamaño = 9;
        Paragraph p;
        table.setWidthPercentage(100);
        String texto = "Este documento fue realizado por el usuario " + TicketData[1] + " el día " + new SimpleDateFormat("dd-MM-YYYY").format(new Date()) + " a las " + new SimpleDateFormat("HH:MM").format(new Date()) + " hrs";
        try {
            PdfPCell celda;
            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(7);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);//SEPARADOR

            celda = new PdfPCell();
            p = new Paragraph(texto, FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            celda.setColspan(7);
            table.addCell(celda);

            celda = new PdfPCell();
            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), 3)));
            celda.setColspan(7);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);//SEPARADOR
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return table;
    }

    //CREAR DOCUMENTO PDF ESTADO DE RESULTADOS TAMAÑO CARTA EN Register_Cash_v1.0.1\docs\EdoRes
    public void CrearPDFCorteZCarta(String ruta, Rectangle tamaño, PdfPTable tabla, PdfPTable tableFooter) {
        try {
            Document doc = new Document(tamaño, getMargenLeft() + 20, getMargenRigth() + 20, getMargenTop(), getMargenBottom());
            PdfWriter pdfw = PdfWriter.getInstance(doc, new FileOutputStream(ruta));
            doc.open();
            doc.add(TablaEncabezadoCorteZCarta());
            doc.add(tabla);
            tableFooter.setTotalWidth(doc.right(doc.rightMargin()));
            tableFooter.writeSelectedRows(0, -1, getMargenLeft() + 20, tableFooter.getTotalHeight() + doc.bottom(doc.bottomMargin()), pdfw.getDirectContent());
            doc.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    //TABLA TAMAÑO MINIPRINT ESTADO DE RESULTADOS
    public PdfPTable TablaCorteZMiniprint(String[] TicketData, String[] Totales, String[] Datos) {
        //idv_usr_total[0] = ID_REGISTRO
        //idv_usr_total[1] = USUARIO
        //idv_usr_total[2] = 1000 (TOTAL)
        //idv_usr_total[3] = MESA
        //idv_usr_total[4] = ID CLIENTE
        int cols = 8;
        int tamaño = 4;
        int border = 0;
        PdfPTable table = new PdfPTable(cols);
        table.setWidthPercentage(100);
        PdfPCell celda;
        Paragraph p;
        String[] cont = new DatEmpresa().getDatEmpresa();

        try {
            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), tamaño - 1)));
            celda.setColspan(cols);
            celda.setBorder(border);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            File f;
            if (new File("iconos\\LOGO.png").exists()) {
                f = new File("iconos\\LOGO.png");
            } else {
                f = new File("iconos\\LOGO.png");
            }
            com.itextpdf.text.Image imagen = com.itextpdf.text.Image.getInstance(f.getAbsolutePath());
            imagen.scaleToFit(70, 35);
            imagen.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(imagen);
            celda.setColspan(cols);
            celda.setBorder(border);
            table.addCell(celda);//logo 

            celda = new PdfPCell();
            String text = cont[1] + "\n"
                    + "RFC:" + cont[2] + "\n"
                    + "ENCARGADO:" + cont[3] + "\n"
                    + "DIRECCION:" + cont[4] + "\n"
                    + "TELEFONO:" + cont[5] + "\n\n";
            p = new Paragraph(text, FontFactory.getFont(getFuente().toString(), tamaño));
            celda.setColspan(cols);
            celda.setBorder(border);
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            table.addCell(celda);//DATOS EMPRESA

            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), tamaño - 1)));
            celda.setColspan(cols);
            celda.setBorder(border);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("CORTE DE CAJA", FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            celda.setColspan(cols);
            celda.setBorder(border);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("PRODUCTOS VENDIDOS", FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            celda.setColspan(cols);
            celda.setBorder(border);
            table.addCell(celda);

            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), tamaño - 1)));
            celda.setColspan(cols);
            celda.setBorder(border);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("ORDEN", FontFactory.getFont(getFuente().toString(), tamaño - 1));
            p.setAlignment(Element.ALIGN_LEFT);
            celda.addElement(p);
            celda.setBorder(border);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("MESA", FontFactory.getFont(getFuente().toString(), tamaño - 1));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            celda.setBorder(border);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("TOTAL", FontFactory.getFont(getFuente().toString(), tamaño - 1));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            celda.setBorder(border);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("ENVIO", FontFactory.getFont(getFuente().toString(), tamaño - 1));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            celda.setBorder(border);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("ESTADO", FontFactory.getFont(getFuente().toString(), tamaño - 1));
            p.setAlignment(Element.ALIGN_LEFT);
            celda.addElement(p);
            celda.setBorder(border);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("CLIENTE", FontFactory.getFont(getFuente().toString(), tamaño - 1));
            p.setAlignment(Element.ALIGN_LEFT);
            celda.addElement(p);
            celda.setBorder(border);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("FECHA VENTA", FontFactory.getFont(getFuente().toString(), tamaño - 1));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            celda.setBorder(border);
            table.addCell(celda);

            celda = new PdfPCell();
            p = new Paragraph("HORA VENTA", FontFactory.getFont(getFuente().toString(), tamaño - 1));
            p.setAlignment(Element.ALIGN_RIGHT);
            celda.addElement(p);
            celda.setBorder(border);
            table.addCell(celda);

            for (int i = 0; i < Datos.length; i++) {

                String[] productoInd = Datos[i].split(",");
                celda = new PdfPCell();
                p = new Paragraph(productoInd[0], FontFactory.getFont(getFuente().toString(), tamaño - 1));
                p.setAlignment(Element.ALIGN_LEFT);
                celda.addElement(p);
                celda.setBorder(border);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(productoInd[1], FontFactory.getFont(getFuente().toString(), tamaño - 1));
                p.setAlignment(Element.ALIGN_CENTER);
                celda.addElement(p);
                celda.setBorder(border);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(new FormatoNumerico().AsignarFormatoMoneda(productoInd[2]), FontFactory.getFont(getFuente().toString(), tamaño - 1));
                p.setAlignment(Element.ALIGN_CENTER);
                celda.addElement(p);
                celda.setBorder(border);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(new FormatoNumerico().AsignarFormatoMoneda(productoInd[3]), FontFactory.getFont(getFuente().toString(), tamaño - 1));
                p.setAlignment(Element.ALIGN_CENTER);
                celda.addElement(p);
                celda.setBorder(border);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(productoInd[4], FontFactory.getFont(getFuente().toString(), tamaño - 2));
                p.setAlignment(Element.ALIGN_LEFT);
                celda.addElement(p);
                celda.setBorder(border);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(productoInd[5], FontFactory.getFont(getFuente().toString(), tamaño - 2));
                p.setAlignment(Element.ALIGN_LEFT);
                celda.addElement(p);
                celda.setBorder(border);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(productoInd[6], FontFactory.getFont(getFuente().toString(), tamaño - 1));
                p.setAlignment(Element.ALIGN_CENTER);
                celda.addElement(p);
                celda.setBorder(border);
                table.addCell(celda);

                celda = new PdfPCell();
                p = new Paragraph(productoInd[7], FontFactory.getFont(getFuente().toString(), tamaño - 1));
                p.setAlignment(Element.ALIGN_RIGHT);
                celda.addElement(p);
                celda.setBorder(border);
                table.addCell(celda);
            }

            celda = new PdfPCell(new Paragraph(" "));
            celda.setColspan(6);
            celda.setBorder(border);
            table.addCell(celda);
            celda = new PdfPCell();
            p = new Paragraph("TOT VENTAS: " + new FormatoNumerico().AsignarFormatoMoneda(Totales[0]), FontFactory.getFont(getFuente().toString(), tamaño - 1));
            p.setAlignment(Element.ALIGN_RIGHT);
            celda.addElement(p);
            celda.setColspan(2);
            celda.setBorder(border);
            table.addCell(celda);

            celda = new PdfPCell(new Paragraph(" "));
            celda.setColspan(6);
            celda.setBorder(border);
            table.addCell(celda);
            celda = new PdfPCell();
            p = new Paragraph("TOT PENDS: " + new FormatoNumerico().AsignarFormatoMoneda(Totales[1]), FontFactory.getFont(getFuente().toString(), tamaño - 1));
            p.setAlignment(Element.ALIGN_RIGHT);
            celda.addElement(p);
            celda.setColspan(2);
            celda.setBorder(border);
            table.addCell(celda);

            celda = new PdfPCell(new Paragraph(" "));
            celda.setColspan(6);
            celda.setBorder(border);
            table.addCell(celda);
            celda = new PdfPCell();
            p = new Paragraph("TOT CANCEL: " + new FormatoNumerico().AsignarFormatoMoneda(Totales[2]), FontFactory.getFont(getFuente().toString(), tamaño - 1));
            p.setAlignment(Element.ALIGN_RIGHT);
            celda.addElement(p);
            celda.setColspan(2);
            celda.setBorder(border);
            table.addCell(celda);

            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), tamaño - 1)));
            celda.setColspan(cols);
            celda.setBorder(border);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);//SEPARADOR

            String texto = "Este documento fue realizado por el usuario " + TicketData[1] + " el día " + TicketData[2] + " a las " + TicketData[3] + " hrs";
            celda = new PdfPCell();
            p = new Paragraph(texto, FontFactory.getFont(getFuente().toString(), tamaño));
            p.setAlignment(Element.ALIGN_CENTER);
            celda.addElement(p);
            celda.setColspan(cols);
            celda.setBorder(border);
            table.addCell(celda);

            celda = new PdfPCell();
            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), tamaño - 1)));
            celda.setColspan(cols);
            celda.setBorder(border);
            celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(celda);//SEPARADOR

            celda = new PdfPCell(new Phrase(" ", FontFactory.getFont(getFuente().toString(), tamaño - 1)));
            celda.setColspan(cols);
            celda.setBorder(2);
            table.addCell(celda);//CORTAR
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return table;
    }

    //CREAR DOCUMENTO PDF ESTADO DE RESULTADOS TAMAÑO MINIPRINTER EN Register_Cash_v1.0.1\docs\EdoRes
    public void CreatePDFMiniPrintEdoRes(String ruta, Rectangle tamaño, PdfPTable tabla) {
        try {
            Document doc = new Document(tamaño, getMargenLeft(), getMargenRigth(), getMargenTop(), getMargenBottom());
            PdfWriter pdfw = PdfWriter.getInstance(doc, new FileOutputStream(ruta));
            doc.open();
            doc.add(tabla);
            doc.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
