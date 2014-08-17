/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linet.com.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import org.w3c.dom.*;

/**
 *
 * @author ext_synps03
 */
public class ReadXmlWsdlUtil {

    public static Vector<String> obtieneElementsTypesCabecera(Document unDoc, String targetNamespace) {

        Vector<String> res = null;
        try {
            Document doc = unDoc;
            // normalize text representation
            doc.getDocumentElement().normalize();

            NodeList listOfNodos_nivel_1 = doc.getElementsByTagName("schema");
//            int totalNodosIni = listOfNodos_nivel_1.getLength();
            for (int s = 0; s < listOfNodos_nivel_1.getLength(); s++) {

                Node node_nivel_1_s = listOfNodos_nivel_1.item(s);

                if (node_nivel_1_s.getNodeType() == Node.ELEMENT_NODE) {
                    Element element_s_nivel_1 = (Element) node_nivel_1_s;

                    if (element_s_nivel_1.getAttribute("targetNamespace").equals(targetNamespace)) {

                        NodeList listOfNodos_nivel_2 = element_s_nivel_1.getElementsByTagName("complexType");
                        Element element_1_nivel_2 = (Element) listOfNodos_nivel_2.item(0);


                        if (element_1_nivel_2.getAttribute("name").equals("Cabecera")) {
                            NodeList listOfNodos_nivel_3 = element_1_nivel_2.getElementsByTagName("sequence");
                            Element element_1_nivel_3 = (Element) listOfNodos_nivel_3.item(0);
                            NodeList listOfNodos_nivel_4 = element_1_nivel_3.getElementsByTagName("element");
                            //iteramos para obtener los element
                            res = new Vector();
                            for (int i = 0; i < listOfNodos_nivel_4.getLength(); i++) {// iteramos le nivel 4
                                Element element_i_nivel_4 = (Element) listOfNodos_nivel_4.item(i);
                                String nam = element_i_nivel_4.getAttribute("name");
                                String type = element_i_nivel_4.getAttribute("type");
                                type = limpiaTipo(type);
                                res.add(HandlerUtil.conviertePrimeraLetraMayuscula(nam) + "," + type);
                            }
                        }
                    }
                }//end of if clause
            }//end of for loop with s var

        } catch (Exception err) {
//            System.out.println("** Parsing error" + ", line " + err.getLineNumber() + ", uri " + err.getSystemId());
            //System.out.println(" " + err.getMessage());

        }

        return res;
    }

    public static Vector<String> obtieneElementTypesDetalle(Document unDoc, String targetNamespace, String atributo) {
        //System.out.println("************************************************\n" +
               // "obtieneElementTypesDetalle:targetNamespace:" + targetNamespace);
        Vector<String> res = null;
        HashMap<String, Element> mapOfComplexType = new HashMap<String, Element>();
        Vector<String> tipos_complejos = new Vector<String>();
        Vector<String> nombres_complejos = new Vector<String>();
        try {
            Document doc = unDoc;
            // normalize text representation
            doc.getDocumentElement().normalize();
//            System.out.println("Root element of the doc is " +doc.getDocumentElement().getNodeName());
            NodeList listOfNodos_nivel_1 = doc.getElementsByTagName("schema");
            int totalNodosIni = listOfNodos_nivel_1.getLength();
            //System.out.println("Total no of schema : " + totalNodosIni);

            for (int s = 0; s < listOfNodos_nivel_1.getLength(); s++) {

                Node node_nivel_1_s = listOfNodos_nivel_1.item(s);

                if (node_nivel_1_s.getNodeType() == Node.ELEMENT_NODE) {
                    Element element_s_nivel_1 = (Element) node_nivel_1_s;

                    if (element_s_nivel_1.getAttribute("targetNamespace").equals(targetNamespace)) {// si es igual al namespace requerido

                        //System.out.println("schema:" + s + ":targetNamespace:" + element_s_nivel_1.getAttribute("targetNamespace"));
                        NodeList listOfNodos_nivel_2 = element_s_nivel_1.getElementsByTagName("complexType");

                        //System.out.println("num complexType:" + listOfNodos_nivel_2.getLength());

                        for (int i = 0; i < listOfNodos_nivel_2.getLength(); i++) {// iteramos los nodos : 1.i
                            Element element_i_nivel_2 = (Element) listOfNodos_nivel_2.item(i);
                            if (element_i_nivel_2.getAttribute("name").equals(atributo)) {
                                //System.out.println(atributo + " encontrados");
                                NodeList listOfNodos_nivel_3 = element_i_nivel_2.getElementsByTagName("sequence");
                                Element element_1_nivel_3 = (Element) listOfNodos_nivel_3.item(0);//obtiene el primer y unico nodo hijo de un nivel 3
                                //System.out.println("num sequence:" + listOfNodos_nivel_3.getLength());
                                NodeList listOfNodos_nivel_4 = element_1_nivel_3.getElementsByTagName("element");
                                //System.out.println("num element:" + listOfNodos_nivel_4.getLength());
                                //iteramos para obtener los element
                                res = new Vector();
                                for (int j = 0; j < listOfNodos_nivel_4.getLength(); j++) {
                                    Element element_j_nivel_4 = (Element) listOfNodos_nivel_4.item(j);
                                    //System.out.println("element:" + element_j_nivel_4.getAttribute("name"));
                                    String nam = element_j_nivel_4.getAttribute("name");
                                    String type = element_j_nivel_4.getAttribute("type");
                                    //type = limpiaTipo(type);

                                    if (esCampoSimple(type)) {// si es array todavia no lo inserto
                                        type = limpiaTipo(type);
                                        res.add(HandlerUtil.conviertePrimeraLetraMayuscula(nam) + "," + type);
                                        //System.out.println(nam + "," + type);
                                    } else {//si no es array lo inserto
                                        tipos_complejos.add(limpiaTipo(type));
                                        nombres_complejos.add(limpiaTipo(nam));
                                    }
                                }
                            }
                            //guardamos el completype :)
                            //System.out.println("mapOfComplexType.put:" + element_i_nivel_2.getAttribute("name"));
                            mapOfComplexType.put(element_i_nivel_2.getAttribute("name"), element_i_nivel_2);
                        }
                    }
                }//end of if clause
            }//end of for loop with s var

            //iteramos los tipo array:
            //System.out.println("iniciando insercion de objetos complejos ...");
            int tab =0;
            for(int a=0;a<tipos_complejos.size();a++){
                String type_array = tipos_complejos.get(a);
                //System.out.println(">>>>>>>>>>>>Objeto:" + type_array);
                res.add(HandlerUtil.conviertePrimeraLetraMayuscula(nombres_complejos.get(a)) + "," + type_array);
                insertaCamposOfArray(tab,type_array, mapOfComplexType,res);
            }

            /*for (String type_array : tipos_complejos) {
                System.out.println(">>>>>>>>>>>>Objeto:" + type_array);
                res.add(nam + "," + type);
                insertaCamposOfArray(type_array, mapOfComplexType);
            }*/

            //listamos los completype
//            System.out.println("---------->\tlist of complextypes:");
//            Iterator it = mapOfComplexType.entrySet().iterator();
//            while (it.hasNext()) {
//                Map.Entry pairs = (Map.Entry) it.next();
//                System.out.println(pairs.getKey() + " = " + pairs.getValue());
//            }
        } catch (Exception err) {
//            System.out.println("** Parsing error" + ", line " + err.getLineNumber() + ", uri " + err.getSystemId());
            System.out.println(" " + err.getMessage());

        }

        return res;
    }

    public static void insertaCamposOfArray(int tab,String type_array, HashMap<String, Element> map,Vector<String> result) {
        try {
            tab++;
            //System.out.println("Inciando proceso a:+++++++++++++++> " + type_array);
            //obtenemos el complextype que  contiene al tipo
            Node n_complex = map.get(type_array);
            Element e_complex = (Element) n_complex;
            //entramos un nivel : sequence
            NodeList listOfNodos_sequence = e_complex.getElementsByTagName("sequence");
            Element element_1_sequence = (Element) listOfNodos_sequence.item(0);//obtiene el primer y unico nodo hijo de un nivel 3
            NodeList listOfNodos_elements = element_1_sequence.getElementsByTagName("element");
            //System.out.println("Campos dentro del tipo:" + type_array);
            int led = 0;
            for (int i = 0; i < listOfNodos_elements.getLength(); i++) {
                Element element_i = (Element) listOfNodos_elements.item(i);
                String type = element_i.getAttribute("type");
                String name = element_i.getAttribute("name");
                if (!esCampoSimple(type)) {
                    led++;
                    //System.out.println(HandlerUtil.getTabulacion(tab)+" complejo:->" + name + " -> " + type);
                    result.add(HandlerUtil.getTabulacion(tab)+HandlerUtil.conviertePrimeraLetraMayuscula(name)+","+limpiaTipo(type));
                    insertaCamposOfArray(tab,limpiaTipo(type), map,result);
                } else {
                    //System.out.println(tab+" simple:->" + name + " -> " + type);
                    result.add(HandlerUtil.getTabulacion(tab)+HandlerUtil.conviertePrimeraLetraMayuscula(name)+","+limpiaTipo(type));
                }
            }
        } catch (Exception e) {
        }

    }

    public static String limpiaTipo(String tipo_in) {
        //buscamos el  ":"
        int ind = tipo_in.indexOf(":");

        return tipo_in.substring(ind + 1);

    }

    public static boolean esCampoSimple(String type) {

        if (type.contains("xsd")) {
            // es un campo simple
            return true;
        } else {
            return false;
        }


    }
}
