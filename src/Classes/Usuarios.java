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
public class Usuarios {

    String archivo = "source\\DBS_0010.dat";
//

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public List<String> getDBUsers() {
        List<String> resp = new ArrayList<>();
        try {
            String line = "";
            BufferedReader br = new BufferedReader(new FileReader(getArchivo()));
            while ((line = br.readLine()) != null) {
                if (line.length() > 0) {
                    resp.add(new Encryptor().DecryptData(line));
                }
            }
            br.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public void ClearDB() {
        try {
            new File(getArchivo()).delete();
            new File(getArchivo()).createNewFile();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addRootUser() {
        try {
            String nreg = "123456,root,root,Administrador,";
            InsertRootUser(nreg);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void InsertRootUser(String nuevo_reg) {
        try {
            FileWriter w = new FileWriter(getArchivo());
            BufferedWriter bw = new BufferedWriter(w);
            bw.write(new Encryptor().EncryptData(nuevo_reg));
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String[] findUser(String id) {
        System.out.println("findUser");
        String[] resp = null;
        try {
            List<String> datos = getDBUsers();
            for (int i = 0; i < datos.size(); i++) {
                String[] temp = datos.get(i).split(",");
                if (temp[0].equals(id)) {
                    System.out.println("match "+temp[0]+" "+id);
                    resp = temp;
                    break;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }

    public String[] findUserbyName(String User) {
        System.out.println("findUserbyName");
        List<String> datos = getDBUsers();
        try {
            for (int i = 0; i < datos.size(); i++) {
                String[] temp = datos.get(i).split(",");
                if (temp[1].equals(User)) {
                    return datos.get(i).split(",");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public String genID() {
        System.out.println("genID");
        String id = "";
        try {
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
        System.out.println("existID");
//        boolean resp = false;
        try {
            if (findUser(id) != null) {
                return true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return false;
//        return resp;
    }

    public boolean existUser(String User) {
        System.out.println("existUser");
        try {
            if (findUserbyName(User) != null) {
                return true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public boolean InsertUser(String nuevo_reg) {
        System.out.println("InsertUser");
        try {
            List<String> datos = getDBUsers();
            datos.add(nuevo_reg);
            FileWriter w = new FileWriter(getArchivo());
            BufferedWriter bw = new BufferedWriter(w);
            for (int i = 0; i < datos.size(); i++) {
                bw.write(new Encryptor().EncryptData(datos.get(i)));
                bw.newLine();
            }
            bw.newLine();
            bw.close();

            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public boolean UpdateUser(String nuevo_reg) {
        System.out.println("UpdateUser");
        try {
            List<String> datos = getDBUsers();
            String[] nreg = nuevo_reg.split(",");
            FileWriter w = new FileWriter(getArchivo());
            BufferedWriter bw = new BufferedWriter(w);
            for (int i = 0; i < datos.size(); i++) {
                String[] reg_ind = datos.get(i).split(",");
                if (reg_ind[0].equals(nreg[0])) {
                    bw.write(new Encryptor().EncryptData(nuevo_reg));
                    bw.newLine();
                } else {
                    bw.write(new Encryptor().EncryptData(datos.get(i)));
                    bw.newLine();
                }
            }
            bw.close();
            bw.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public boolean DelUser(String userID) {
        System.out.println("DelUser");
        try {
            List<String> datos = getDBUsers();
            BufferedWriter bw = new BufferedWriter(new FileWriter(getArchivo()));
            for (int i = 0; i < datos.size(); i++) {
                String[] user = datos.get(i).split(",");
                if (!userID.equals(user[0])) {
                    bw.write(new Encryptor().EncryptData(datos.get(i)));
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

    public boolean confirmar_acceso(String usuario, String contraseña) {
        System.out.println("confirmar_acceso");
        boolean resp = false;
        try {
            String[] userFinded = findUserbyName(usuario);
            if (userFinded != null) {
                if (contraseña.equals(userFinded[2])) {
                    resp = true;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "¡ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return resp;
    }
}
