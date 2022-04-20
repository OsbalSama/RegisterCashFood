/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames.generalControls;

import Classes.CatProvClass;
import Classes.Configurations;
import Classes.Dishes;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author ATENEA
 */
public class Carta extends javax.swing.JDialog {

    /**
     * Creates new form Carta
     */
    String resp;
    int inicio = 0;
    int[] dimenciones = new int[2];

    public int getInicio() {
        return inicio;
    }

    public void setInicio(int inicio) {
        this.inicio = inicio;
    }

    public String getResp() {
        return resp;
    }

    public void setDimenciones() {
        dimenciones[0] = lblImg0.getWidth();
        dimenciones[1] = lblImg0.getHeight();
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public Carta(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.setUndecorated(true);
        initComponents();
        this.setIconImage(new Configurations().getImageIcon().getImage());
        this.setLocationRelativeTo(null);
        this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
        setDimenciones();
    }

    public void inicialize() {
        loadCatsinButtons();
        setJPanelActionPreformeds();
        setSelectedCathegory(btnEsp0);
    }

    public void setJPanelActionPreformeds() {
        setActionPreformed(pnlItem0, lblTitulo0, lblPrecio0);
        setActionPreformed(pnlItem1, lblTitulo1, lblPrecio1);
        setActionPreformed(pnlItem2, lblTitulo2, lblPrecio2);
        setActionPreformed(pnlItem3, lblTitulo3, lblPrecio3);
    }

    public void setActionPreformed(JPanel base, JLabel descripcion, JLabel Precio) {
        base.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                OnClickAction(base, descripcion, Precio);
            }

            @Override
            public void mousePressed(MouseEvent me) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent me) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent me) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent me) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });
    }

    public void resetPageNumber() {
        lblPage.setText("1-1");
    }

    //aaa
    public void OnClickAction(JPanel base, JLabel descripcion, JLabel Precio) {
        try {
            if (base.getBackground() == Color.WHITE) {
                resetAllPanels();
                List DishesinPage = getItemsForPage(new Dishes().getDishesByType(lblSelectedCat.getText()), getPage(lblPage.getText()));
                setDataDishes(DishesinPage);
                setPanelBackground(base, Color.ORANGE);
                resetCant();
                loadDishonResp(base.getName());
            } else if (base.getBackground() == Color.ORANGE) {
                upCant();
                loadDishonResp(base.getName());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void loadDishonResp(String idDish) {
        try {
            String rsp = idDish
                    + "," + txtCant.getText()
                    + ",";
            this.setResp(rsp);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void resetCant() {
        txtCant.setText("1");
        this.setResp(null);
    }

    public void upCant() {
        int cant = Integer.parseInt(txtCant.getText());
        if (cant < 999) {
            txtCant.setText((cant + 1) + "");
        }
    }

    public void downCant() {
        int cant = Integer.parseInt(txtCant.getText());
        if (cant > 1) {
            txtCant.setText((cant - 1) + "");
        }
    }

    public int getPage(String texto) {
        int resp = 0;
        try {
            String[] temp = texto.split("-");
            resp = Integer.parseInt(temp[0]);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loadCarta: " + e);
        }

        return resp;
    }

    public List getItemsForPage(List Dishes, int Page) {
        List<String> resp = new ArrayList<String>();
        try {
            int p0 = 4 * (Page - 1);
            int p1 = 4 * (Page);
            for (int i = 0; i < Dishes.size(); i++) {
                if ((i >= p0 && i < p1)) {
                    resp.add(Dishes.get(i).toString());
                }
            }
            if (resp.size() < 4) {
                for (int i = resp.size(); i < 4; i++) {
                    resp.add("VOID");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e);
        }
        return resp;
    }

    public void loadCatsinButtons() {
        List Cats = new CatProvClass().getDBCat();
        if (Cats.isEmpty()) {
            btnEsp0.setText("SIN CATEGORIAS");
//            btnEsp0.setEnabled(false);
            btnEsp1.setEnabled(false);
            btnEsp2.setEnabled(false);
            btnEsp3.setEnabled(false);
//            btnMas.setEnabled(false);
//            btnMenos.setEnabled(false);
//            txtCant.setEnabled(false);
//            btnAfter.setEnabled(false);
//            btnNext.setEnabled(false);
//            btnUpp.setEnabled(false);
//            btnDown.setEnabled(false);
//            btnSave.setEnabled(false);
            this.dispose();
        } else {
            int indicio = 0;
            String[] datos = new String[4];
            for (int i = getInicio(); i < Cats.size(); i++) {
                if (indicio < 4) {
                    datos[indicio] = Cats.get(i).toString();
                    indicio++;
                } else {
                    break;
                }
            }
            btnEsp0.setText(datos[0]);
            btnEsp1.setText(datos[1]);
            btnEsp2.setText(datos[2]);
            btnEsp3.setText(datos[3]);

            if (datos[0] == null) {
                btnEsp0.setEnabled(false);
            } else {
                btnEsp0.setEnabled(true);
                btnEsp0.setText(datos[0]);
            }
            if (datos[1] == null) {
                btnEsp1.setEnabled(false);
            } else {
                btnEsp1.setEnabled(true);
                btnEsp1.setText(datos[1]);
            }
            if (datos[2] == null) {
                btnEsp2.setEnabled(false);
            } else {
                btnEsp2.setEnabled(true);
                btnEsp2.setText(datos[2]);
            }
            if (datos[3] == null) {
                btnEsp3.setEnabled(false);
            } else {
                btnEsp3.setEnabled(true);
                btnEsp3.setText(datos[3]);
            }
        }
    }

    public int calcPages(List Dishes) {
        int pages = 1;
        if (!Dishes.isEmpty()) {
            int cont = 1;
            for (int i = 0; i < Dishes.size(); i++) {
                if (cont < 4) {
                    cont++;
                } else {
                    pages++;
                    cont = 1;
                }
            }
        }
        return pages;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnDown = new javax.swing.JButton();
        btnUpp = new javax.swing.JButton();
        btnEsp0 = new javax.swing.JButton();
        btnEsp3 = new javax.swing.JButton();
        btnEsp1 = new javax.swing.JButton();
        btnEsp2 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        btnNext = new javax.swing.JButton();
        btnAfter = new javax.swing.JButton();
        lblPage = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        pnlItem0 = new javax.swing.JPanel();
        lblTitulo0 = new javax.swing.JLabel();
        lblImg0 = new javax.swing.JLabel();
        lblPrecio0 = new javax.swing.JLabel();
        lblSelectedCat = new javax.swing.JLabel();
        pnlItem1 = new javax.swing.JPanel();
        lblTitulo1 = new javax.swing.JLabel();
        lblImg1 = new javax.swing.JLabel();
        lblPrecio1 = new javax.swing.JLabel();
        pnlItem2 = new javax.swing.JPanel();
        lblTitulo2 = new javax.swing.JLabel();
        lblImg2 = new javax.swing.JLabel();
        lblPrecio2 = new javax.swing.JLabel();
        pnlItem3 = new javax.swing.JPanel();
        lblTitulo3 = new javax.swing.JLabel();
        lblImg3 = new javax.swing.JLabel();
        lblPrecio3 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        txtCant = new javax.swing.JTextField();
        btnMenos = new javax.swing.JButton();
        btnMas = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        btnDown.setBackground(new java.awt.Color(102, 153, 255));
        btnDown.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        btnDown.setText("BAJAR");
        btnDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownActionPerformed(evt);
            }
        });

        btnUpp.setBackground(new java.awt.Color(102, 153, 255));
        btnUpp.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        btnUpp.setText("SUBIR");
        btnUpp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUppActionPerformed(evt);
            }
        });

        btnEsp0.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnEsp0.setText("SIN CATEGORIA");
        btnEsp0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEsp0ActionPerformed(evt);
            }
        });

        btnEsp3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnEsp3.setText("SIN CATEGORIA");
        btnEsp3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEsp3ActionPerformed(evt);
            }
        });

        btnEsp1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnEsp1.setText("SIN CATEGORIA");
        btnEsp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEsp1ActionPerformed(evt);
            }
        });

        btnEsp2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnEsp2.setText("SIN CATEGORIA");
        btnEsp2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEsp2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEsp0, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEsp1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEsp2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEsp3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpp, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDown, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnUpp, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEsp0, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEsp1, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEsp2, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEsp3, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDown, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnNext.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnAfter.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        btnAfter.setText("<<");
        btnAfter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAfterActionPerformed(evt);
            }
        });

        lblPage.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lblPage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPage.setText("0-0");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAfter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNext)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAfter, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPage, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlItem0.setBackground(new java.awt.Color(255, 255, 255));
        pnlItem0.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblTitulo0.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblTitulo0.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTitulo0.setText("123456789012345678901234567890");
        lblTitulo0.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lblImg0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblPrecio0.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        lblPrecio0.setText("$999,999,999.99");

        javax.swing.GroupLayout pnlItem0Layout = new javax.swing.GroupLayout(pnlItem0);
        pnlItem0.setLayout(pnlItem0Layout);
        pnlItem0Layout.setHorizontalGroup(
            pnlItem0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlItem0Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImg0, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlItem0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlItem0Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblPrecio0)))
                .addContainerGap())
        );
        pnlItem0Layout.setVerticalGroup(
            pnlItem0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlItem0Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlItem0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImg0, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                    .addGroup(pnlItem0Layout.createSequentialGroup()
                        .addComponent(lblTitulo0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPrecio0)))
                .addContainerGap())
        );

        lblSelectedCat.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblSelectedCat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSelectedCat.setText("Categoria Seleccionada");

        pnlItem1.setBackground(new java.awt.Color(255, 255, 255));
        pnlItem1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblTitulo1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblTitulo1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTitulo1.setText("123456789012345678901234567890");
        lblTitulo1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lblImg1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblPrecio1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        lblPrecio1.setText("$999,999,999.99");

        javax.swing.GroupLayout pnlItem1Layout = new javax.swing.GroupLayout(pnlItem1);
        pnlItem1.setLayout(pnlItem1Layout);
        pnlItem1Layout.setHorizontalGroup(
            pnlItem1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlItem1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImg1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlItem1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlItem1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblPrecio1)))
                .addContainerGap())
        );
        pnlItem1Layout.setVerticalGroup(
            pnlItem1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlItem1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlItem1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImg1, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                    .addGroup(pnlItem1Layout.createSequentialGroup()
                        .addComponent(lblTitulo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPrecio1)))
                .addContainerGap())
        );

        pnlItem2.setBackground(new java.awt.Color(255, 255, 255));
        pnlItem2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblTitulo2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblTitulo2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTitulo2.setText("123456789012345678901234567890");
        lblTitulo2.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lblImg2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblPrecio2.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        lblPrecio2.setText("$999,999,999.99");

        javax.swing.GroupLayout pnlItem2Layout = new javax.swing.GroupLayout(pnlItem2);
        pnlItem2.setLayout(pnlItem2Layout);
        pnlItem2Layout.setHorizontalGroup(
            pnlItem2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlItem2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImg2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlItem2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlItem2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblPrecio2)))
                .addContainerGap())
        );
        pnlItem2Layout.setVerticalGroup(
            pnlItem2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlItem2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlItem2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImg2, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                    .addGroup(pnlItem2Layout.createSequentialGroup()
                        .addComponent(lblTitulo2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPrecio2)))
                .addContainerGap())
        );

        pnlItem3.setBackground(new java.awt.Color(255, 255, 255));
        pnlItem3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblTitulo3.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblTitulo3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTitulo3.setText("123456789012345678901234567890");
        lblTitulo3.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lblImg3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblPrecio3.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        lblPrecio3.setText("$999,999,999.99");

        javax.swing.GroupLayout pnlItem3Layout = new javax.swing.GroupLayout(pnlItem3);
        pnlItem3.setLayout(pnlItem3Layout);
        pnlItem3Layout.setHorizontalGroup(
            pnlItem3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlItem3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImg3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlItem3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlItem3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblPrecio3)))
                .addContainerGap())
        );
        pnlItem3Layout.setVerticalGroup(
            pnlItem3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlItem3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlItem3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImg3, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                    .addGroup(pnlItem3Layout.createSequentialGroup()
                        .addComponent(lblTitulo3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPrecio3)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSelectedCat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlItem0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlItem1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlItem2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlItem3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSelectedCat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlItem0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlItem1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlItem2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlItem3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnCancel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnCancel.setText("CANCELAR");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnSave.setText("AGREGAR");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        txtCant.setEditable(false);
        txtCant.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        txtCant.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCant.setText("0");

        btnMenos.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        btnMenos.setText("-");
        btnMenos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenosActionPerformed(evt);
            }
        });

        btnMas.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        btnMas.setText("+");
        btnMas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnMenos, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCant, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMas, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCant, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMenos, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMas, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void loadItemsNextPage() {
        List DishesinPage = getItemsForPage(new Dishes().getDishesByType(lblSelectedCat.getText()), getPage(lblPage.getText()));
        setDataDishes(DishesinPage);
    }

    public void afterPage() {
        int pagInicio = getPage(lblPage.getText());
        int pagFinal = getLastPage(lblPage);
        if (pagInicio > 1) {
            setPaginas((pagInicio - 1), pagFinal);
            loadItemsNextPage();
            resetCant();
        }
    }

    public void nextPage() {
        int pagInicio = getPage(lblPage.getText());
        int pagFinal = getLastPage(lblPage);
        if (pagInicio < pagFinal) {
            setPaginas((pagInicio + 1), pagFinal);
            loadItemsNextPage();
            resetCant();
        }
    }

    public int getLastPage(JLabel Label) {
        int resp = 0;
        String texto = Label.getText().split("-")[1];
        resp = Integer.parseInt(texto);
        return resp;
    }

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        nextPage();
    }//GEN-LAST:event_btnNextActionPerformed


    private void btnAfterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAfterActionPerformed
        afterPage();
    }//GEN-LAST:event_btnAfterActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        setResp(null);
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (getResp() != null && !getResp().equals("")) {
            this.dispose();
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUppActionPerformed
        bajarInicio();
    }//GEN-LAST:event_btnUppActionPerformed

    private void btnDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownActionPerformed
        subirInicio();
    }//GEN-LAST:event_btnDownActionPerformed

    private void btnEsp0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEsp0ActionPerformed
        setSelectedCathegory(btnEsp0);
    }//GEN-LAST:event_btnEsp0ActionPerformed

    private void btnEsp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEsp1ActionPerformed
        setSelectedCathegory(btnEsp1);
    }//GEN-LAST:event_btnEsp1ActionPerformed

    private void btnEsp2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEsp2ActionPerformed
        setSelectedCathegory(btnEsp2);
    }//GEN-LAST:event_btnEsp2ActionPerformed

    private void btnEsp3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEsp3ActionPerformed
        setSelectedCathegory(btnEsp3);
    }//GEN-LAST:event_btnEsp3ActionPerformed

    private void btnMasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasActionPerformed
        if (getResp() != null) {
            upCant();
            loadDishonResp(getResp().split(",")[0]);
        }
    }//GEN-LAST:event_btnMasActionPerformed

    private void btnMenosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenosActionPerformed
        if (getResp() != null) {
            downCant();
            loadDishonResp(getResp().split(",")[0]);
        }
    }//GEN-LAST:event_btnMenosActionPerformed
    public void setSelectedCathegory(JButton boton) {
        lblSelectedCat.setText(boton.getText());
        loadDishesInCart();
        resetCant();
    }

    public void setPaginas(int paginaActual, int paginaFinal) {
        lblPage.setText(paginaActual + "-" + paginaFinal);
    }

    public void setDataDishes(List Datos) {
        setPanelLabelData(Datos.get(0).toString(), pnlItem0, lblTitulo0, lblPrecio0, lblImg0);
        setPanelLabelData(Datos.get(1).toString(), pnlItem1, lblTitulo1, lblPrecio1, lblImg1);
        setPanelLabelData(Datos.get(2).toString(), pnlItem2, lblTitulo2, lblPrecio2, lblImg2);
        setPanelLabelData(Datos.get(3).toString(), pnlItem3, lblTitulo3, lblPrecio3, lblImg3);
    }

    public void setPanelBackground(JPanel Panel, Color clr) {
        Panel.setBackground(clr);
    }

    public void setPanelLabelData(String data, JPanel Panel, JLabel lblTitulo, JLabel lblPrecio, JLabel lblImagen) {
        if (data.equals("VOID")) {
            Panel.setEnabled(false);
            lblTitulo.setText("VACIO");
            Panel.setName("VOID");
            lblPrecio.setText("$0.00");
            setPanelBackground(Panel, Color.GRAY);
            lblImagen.setIcon(null);
        } else {
            Panel.setEnabled(true);
            String[] temp = data.split(",");
            lblTitulo.setText(temp[1]);
            lblPrecio.setText("$" + temp[4]);
            Panel.setName(temp[0]);
            setPanelBackground(Panel, Color.WHITE);
            lblImagen.setIcon(new Dishes().getDishIcon(temp[0], dimenciones[0], dimenciones[1]));
        }
    }

    public void resetAllPanels() {
        resetPanel(pnlItem0, lblTitulo0, lblPrecio0, lblImg0);
        resetPanel(pnlItem1, lblTitulo1, lblPrecio1, lblImg1);
        resetPanel(pnlItem2, lblTitulo2, lblPrecio2, lblImg2);
        resetPanel(pnlItem3, lblTitulo3, lblPrecio3, lblImg3);
    }

    public void loadDishesInCart() {
        resetAllPanels();
        List<String> database = new Dishes().getDishesByType(lblSelectedCat.getText());
        if (!database.isEmpty()) {
            setPaginas(1, calcPages(database));
            List DishesinPage = getItemsForPage(database, getPage(lblPage.getText()));
            setDataDishes(DishesinPage);
        } else {
            resetPageNumber();
            resetAllPanels();
        }
    }

    public void resetPanel(JPanel Panel, JLabel lblTitulo, JLabel lblPrecio, JLabel lblPhoto) {
        lblPhoto.setIcon(null);
        lblTitulo.setText("VACIO");
        lblPrecio.setText("$0.00");
        Panel.setName("VOID");
        setPanelBackground(Panel, Color.GRAY);
    }

    public void loadDishinPanel(String id, JLabel lblImagen, JLabel lblTitulo, JPanel panel) {
        String[] dish = new Dishes().getDishbyID(id).split(",");
        lblTitulo.setText(dish[1]);
        lblImagen.setIcon(new Dishes().getDishIcon(id, lblImagen.getWidth() - 5, lblImagen.getHeight() - 5));
    }

    public void bajarInicio() {
        try {
            if (getInicio() < new CatProvClass().getDBCat().size() && getInicio() > 0) {
                setInicio(getInicio() - 1);
                loadCatsinButtons();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void subirInicio() {
        try {
            if ((getInicio() >= 0 && (getInicio() + 4) < new CatProvClass().getDBCat().size()) && getInicio() < new CatProvClass().getDBCat().size() - 1) {
                setInicio(getInicio() + 1);
                loadCatsinButtons();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Carta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Carta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Carta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Carta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Carta dialog = new Carta(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Carta().setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAfter;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDown;
    private javax.swing.JButton btnEsp0;
    private javax.swing.JButton btnEsp1;
    private javax.swing.JButton btnEsp2;
    private javax.swing.JButton btnEsp3;
    private javax.swing.JButton btnMas;
    private javax.swing.JButton btnMenos;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpp;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblImg0;
    private javax.swing.JLabel lblImg1;
    private javax.swing.JLabel lblImg2;
    private javax.swing.JLabel lblImg3;
    private javax.swing.JLabel lblPage;
    private javax.swing.JLabel lblPrecio0;
    private javax.swing.JLabel lblPrecio1;
    private javax.swing.JLabel lblPrecio2;
    private javax.swing.JLabel lblPrecio3;
    private javax.swing.JLabel lblSelectedCat;
    private javax.swing.JLabel lblTitulo0;
    private javax.swing.JLabel lblTitulo1;
    private javax.swing.JLabel lblTitulo2;
    private javax.swing.JLabel lblTitulo3;
    private javax.swing.JPanel pnlItem0;
    private javax.swing.JPanel pnlItem1;
    private javax.swing.JPanel pnlItem2;
    private javax.swing.JPanel pnlItem3;
    private javax.swing.JTextField txtCant;
    // End of variables declaration//GEN-END:variables
}
