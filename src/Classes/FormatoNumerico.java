/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 *
 * @author ATENEA
 */
public class FormatoNumerico {

    public String AsignarFormatoNumerico(String num) {
        DecimalFormat df = new DecimalFormat("###,###.##");
        df.getNumberInstance(Locale.US);
        return df.format(Float.parseFloat(num));
    }

    public String RemoverFormatoNumerico(String datos) {
        String resp = datos.replace(",", "");
        return resp;
    }

    public String AsignarFormatoMoneda(String num) {
        DecimalFormat df = new DecimalFormat("$###,###.##");
        df.getNumberInstance(Locale.US);
        return df.format(Float.parseFloat(num));
    }

    public String RemoverFormatoMoneda(String datos) {
        String resp = datos.replace("$", "").replace(",", "");
        return resp;
    }

    public void testDecimalFormat(String num) {
        System.out.println("AsignarFormatoNumerico " + AsignarFormatoNumerico(num));
        System.out.println("RemoverFormatoNumerico " + RemoverFormatoNumerico(AsignarFormatoNumerico(num)));
        System.out.println("");
        System.out.println("AsignarFormatoMoneda " + AsignarFormatoMoneda(num));
        System.out.println("RemoverFormatoMoneda " + RemoverFormatoMoneda(AsignarFormatoMoneda(num)));
    }
}
