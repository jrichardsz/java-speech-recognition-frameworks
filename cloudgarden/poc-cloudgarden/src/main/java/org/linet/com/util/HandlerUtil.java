package org.linet.com.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 *
 * - Richard Osmar Leon Ingaruca
 * - Katerin Vanesa Bernal Punil
 * - Instituto Peruano de Energía Nuclear.
 *
 */
public class HandlerUtil {

    public static String getFechaHoraToString() {
        Calendar cal = Calendar.getInstance();
        return (("" + cal.get(Calendar.DATE)).length() == 1 ? "0" + cal.get(Calendar.DATE) : cal.get(Calendar.DATE)) + "-"
                + (("" + (cal.get(Calendar.MONTH) + 1)).length() == 1 ? "0" + (cal.get(Calendar.MONTH) + 1) : (cal.get(Calendar.MONTH) + 1))
                + "-" + cal.get(Calendar.YEAR)
                + " " + (("" + cal.get(Calendar.HOUR_OF_DAY)).length() == 1 ? "0" + cal.get(Calendar.HOUR_OF_DAY) : cal.get(Calendar.HOUR_OF_DAY))
                + "-" + (("" + cal.get(Calendar.MINUTE)).length() == 1 ? "0" + cal.get(Calendar.MINUTE) : cal.get(Calendar.MINUTE)) + "-"
                + (("" + cal.get(Calendar.SECOND)).length() == 1 ? "0" + cal.get(Calendar.SECOND) : cal.get(Calendar.SECOND));
    }

    public static String getTabulacion(int num_tab) {
        String temp = "";

        for (int a = 0; a < num_tab; a++) {
            temp += "\t";
        }

        return temp;
    }

    public static String conviertePrimeraLetraMayuscula(String in) {

        //obtenemso la primera letra
        String prim = in.substring(0, 1);
        prim = prim.toUpperCase();
        return prim + in.substring(1, in.length());
    }

    public static void limpiaVector(Vector v) {
        if (v != null && v.size() > 0) {
            v.clear();
        }
    }

    public static String getFechaToAAMMDD() {
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");        
        return format.format(d);
    }

    public static String setw(int num, String cad) {
        String temp = "";

        if (cad.length() < num) {
            for (int a = 0; a < (num - cad.length()); a++) {
                temp += " ";
            }

            return cad + temp;
        } else {

            int nuevo_lengt = num - 3;

            return cad.substring(0, nuevo_lengt) + "   ";
        }
    }

    public static String[] joinArrays(String[] first, String[] second) {
        List<String> both = new ArrayList<String>(first.length + second.length);
        Collections.addAll(both, first);
        Collections.addAll(both, second);
        return both.toArray(new String[]{});
    }

    public static int indexOfBeanInArrayList(String valueField, Class cls, ArrayList collecion) {
        int ind = 0;
        try {
            Field f[] = cls.getFields();
            for (int b = 0; b < collecion.size(); b++) {
                for (int a = 0; a < f.length; a++) {
                    if (f[a].get(collecion.get(b)) != null) {
                        if (f[a].get(collecion.get(b)).equals(valueField)) {
                            ind = b;
                        }
                    }
                }
            }

            return ind;

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return -1;
    }

    public static int indexOfBeanInArrayList(String field, String valueField, Class cls, ArrayList collecion) {
        int ind = 0;
        
        if(collecion.isEmpty()){
            return -1;
        }
        
        try {
            Field f[] = cls.getFields();
            for (int b = 0; b < collecion.size(); b++) {
                for (int a = 0; a < f.length; a++) {

                    if (f[a].getName().equals(field)) {
                        if (f[a].get(collecion.get(b)) != null) {
                            if (f[a].get(collecion.get(b)).equals(valueField)) {
                                ind = b;
                                 return ind;
                            }
                        }
                    }
                }
            }

           

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return -1;
    }

    public static void printContentVectorStrings(Vector<String> strings) {
        for (String s : strings) {
            System.out.println("" + s);
        }
    }
}
