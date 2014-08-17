package org.linet.com.util.archivos;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import javax.activation.MimetypesFileTypeMap;

public class ArchivoUtil {

    public static String getRutaGuardarArchivo(String titulo, String formato) {


        String rutaGuardar = null;
        JFileChooser accesoArchivo = null;
        FileNameExtensionFilter filter = null;
        accesoArchivo = new JFileChooser();
        filter = new FileNameExtensionFilter("Archivos " + formato, formato, formato);

        accesoArchivo.setFileFilter(filter);
        int indicador = 0;



        try {
            accesoArchivo.setDialogTitle(titulo);
            indicador = accesoArchivo.showSaveDialog(null);
            if (indicador == JFileChooser.APPROVE_OPTION) {
                rutaGuardar = accesoArchivo.getSelectedFile().getAbsolutePath();
            } else {
                rutaGuardar = null;
            }
        } catch (Exception e) {
        }

        return rutaGuardar;
    }

    public static String getRutaGuardarArchivoConSeleccion(String titulo, String formato, File f) {
        String rutaGuardar = null;
        JFileChooser accesoArchivo = null;
        FileNameExtensionFilter filter = null;
        accesoArchivo = new JFileChooser();
        filter = new FileNameExtensionFilter("Archivos " + formato, formato, formato);
        accesoArchivo.setFileFilter(filter);
        accesoArchivo.setSelectedFile(f);
        int indicador = 0;
        try {
            accesoArchivo.setDialogTitle(titulo);
            indicador = accesoArchivo.showSaveDialog(null);
            if (indicador == JFileChooser.APPROVE_OPTION) {
                rutaGuardar = accesoArchivo.getSelectedFile().getAbsolutePath();
            } else {
                rutaGuardar = null;
            }
        } catch (Exception e) {
        }
        return rutaGuardar;
    }

    public static String getRutaAbrirArchivo(String titulo, String formato) {
        String rutaAbrir = null;
        JFileChooser accesoArchivo = null;
        FileNameExtensionFilter filter = null;
        accesoArchivo = new JFileChooser();
        filter = new FileNameExtensionFilter("Archivos " + formato, formato, formato);
        accesoArchivo.setFileFilter(filter);
        accesoArchivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int indicador = 0;
        try {
            accesoArchivo.setDialogTitle(titulo);
            indicador = accesoArchivo.showOpenDialog(null);
            if (indicador == JFileChooser.APPROVE_OPTION) {
                rutaAbrir = accesoArchivo.getSelectedFile().getAbsolutePath();
            } else {
                rutaAbrir = null;
            }

        } catch (Exception e) {
        }
        return rutaAbrir;
    }

    public static String getRutaAbrirDirectorio(String titulo) {
        String rutaAbrir = null;
        JFileChooser accesoArchivo = null;
        accesoArchivo = new JFileChooser();
        accesoArchivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int indicador = 0;
        try {
            accesoArchivo.setDialogTitle(titulo);
            indicador = accesoArchivo.showOpenDialog(null);
            if (indicador == JFileChooser.APPROVE_OPTION) {
                rutaAbrir = accesoArchivo.getSelectedFile().getAbsolutePath();
            } else {
                rutaAbrir = null;
            }

        } catch (Exception e) {
        }
        return rutaAbrir;
    }

    public static String getPathDirectorioEjecucion() {
        String path = null;
        try {
            path = new File(".").getCanonicalPath();
        } catch (IOException ex) {
        }
        return path;
    }

    public static String getOnlyExtension(File f) {
        return new MimetypesFileTypeMap().getContentType(f);
    }

    public static String getOnlyNameFile(File f) {
        int slahIndex = f.getAbsolutePath().lastIndexOf("/");
        int puntoIndex = f.getAbsolutePath().lastIndexOf(".", slahIndex);
        if (puntoIndex == -1) {
            return f.getAbsolutePath().substring(slahIndex + 1);
        } else {
            return f.getAbsolutePath().substring(slahIndex + 1, puntoIndex);
        }
    }

    public static ArrayList<String> simulaDirMsDos(String path, String fechaFormat, String token_separador_registro) {

        File directorio = new File(path);
        String[] ficheros = directorio.list();
        ArrayList<String> filas = new ArrayList<String>();
        int nivel = 0;

        SimpleDateFormat format = new SimpleDateFormat(fechaFormat);

        for (int i = 0; i < ficheros.length; i++) {
            try {

                File tmp = new File(path + File.separator + ficheros[i]);
                if (tmp.isFile()) {
                    Date d = new Date();
                    d.setTime(tmp.lastModified());
                    filas.add(getOnlyPath(tmp.getAbsolutePath()) + token_separador_registro + tmp.getName() + token_separador_registro + getOnlyExtension(tmp.getAbsolutePath()) + token_separador_registro + format.format(d));
                } else if (tmp.isDirectory()) {
                    listaFilesDirectorios(tmp, path + File.separator + ficheros[i], nivel, format, token_separador_registro, filas);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return filas;
    }

    public static void listaFilesDirectorios(File directorio, String path, int nivel, SimpleDateFormat format, String token_separador_registro, ArrayList<String> filas) {
        nivel++;
        String[] ficheros = directorio.list();

        for (int i = 0; i < ficheros.length; i++) {
            try {

                File tmp = new File(path + File.separator + ficheros[i]);
                if (tmp.isFile()) {
                    Date d = new Date();
                    d.setTime(tmp.lastModified());
                    filas.add(getOnlyPath(tmp.getAbsolutePath()) + token_separador_registro + tmp.getName() + token_separador_registro + getOnlyExtension(tmp.getAbsolutePath()) + token_separador_registro + format.format(d));
                } else if (tmp.isDirectory()) {
                    listaFilesDirectorios(tmp, path + File.separator + ficheros[i], nivel, format, token_separador_registro, filas);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getOnlyNombreFile(String path) {

        int ind_separador = path.lastIndexOf(File.separator);
        return path.substring(ind_separador + 1, path.length());
    }

    public static String getOnlyPath(String path) {

        int ind_separador = path.lastIndexOf(File.separator);
        return path.substring(0, ind_separador);
    }

    public static String getOnlyExtension(String path) {

        int ind_separador = path.lastIndexOf(".");
        return path.substring(ind_separador + 1, path.length());
    }

    public static void writeBeanToXml(String path, Object bean) {
        XMLEncoder encoder = null;
        try {
            // Serialize object into XML
            encoder = new XMLEncoder(new BufferedOutputStream(
                    new FileOutputStream(path)));
            encoder.writeObject(bean);
            encoder.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (encoder != null) {
                encoder.close();
            }
        }
    }

    public static Object readBeanFromXml(String path) {
        FileInputStream fis = null;
        Object aplicacion = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(path);
            bis = new BufferedInputStream(fis);
            XMLDecoder xmlDecoder = new XMLDecoder(bis);
            aplicacion = (Object) xmlDecoder.readObject();


        } catch (FileNotFoundException ex) {
            Logger.getLogger(ArchivoUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (fis != null) {
                try {
                    fis.close();


                } catch (IOException ex) {
                    Logger.getLogger(ArchivoUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (bis != null) {
                try {
                    bis.close();


                } catch (IOException ex) {
                    Logger.getLogger(ArchivoUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }


        return aplicacion;

    }

    public static void serializaObjeto(String archivo, Object obj) {
        ObjectOutputStream salida = null;

        try {
            salida = new ObjectOutputStream(new FileOutputStream(archivo));
            salida.writeObject(obj);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (salida != null) {
                try {
                    salida.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    public static Object deserializaObjeto(String archivo) {
        ObjectInputStream entrada = null;
        Object out = null;

        try {
            entrada = new ObjectInputStream(new FileInputStream(archivo));
            out = entrada.readObject();
            entrada.close();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (entrada != null) {
                try {
                    entrada.close();

                } catch (IOException ex1) {
                }

            }

        }
        return out;
    }

    public static String retrocedeNivelesPath(String path, int nivelesRetrocer) {
        int ind = 0;
        if (path.charAt(path.length() - 1) == File.separator.charAt(0)) {// encontro que el path enviado tiene un separator al final
            ind = path.lastIndexOf(File.separator);
            path = path.substring(0, ind);
        }

        for (int n = 0; n < nivelesRetrocer; n++) {
            ind = path.lastIndexOf(File.separator);
            if (ind > 0) {// encontro el ultimo separator
                path = path.substring(0, ind);
            }
        }
        return path;
    }

    public static boolean compareFilesUsingBufferedInputStream(String filePath1, String filePath2) {

        File f1 = null;
        File f2 = null;

        BufferedInputStream bis1 = null;
        BufferedInputStream bis2 = null;


        try {
            f1 = new File(filePath1);
            f2 = new File(filePath2);
            if (f1.length() == f2.length()) {
                bis1 = new BufferedInputStream(new FileInputStream(f1));
                bis2 = new BufferedInputStream(new FileInputStream(f2));

                while (true) {
                    int a = bis1.read();
                    int b = bis2.read();
                    if (a != b) {
                        return false;
                    }
                    if (a == -1) {
                        return true;
                    }
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis1 != null) {
                try {
                    bis1.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (bis2 != null) {
                try {
                    bis2.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return false;
    }

    public static boolean compareFilesUsingFileInputStream(String filePath1, String filePath2) {
        try {
            File f1 = new File(filePath1);
            File f2 = new File(filePath2);
            if (f1.length() == f2.length()) {
                FileInputStream fis1 = new FileInputStream(f1);
                FileInputStream fis2 = new FileInputStream(f2);

                while (true) {
                    int a = fis1.read();
                    int b = fis2.read();
                    if (a != b) {
                        return false;
                    }
                    if (a == -1) {
                        return true;
                    }
                }
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void deleteFile(String filePath) {
        File f = new File(filePath);
        f.delete();
    }
    
    public static Vector read(String url) {
        Vector temp = new Vector();
        FileReader fr = null;
        BufferedReader entrada = null;
        try {
            fr = new FileReader(url);
            entrada = new BufferedReader(fr);
            String s;
            while ((s = entrada.readLine()) != null) {
                temp.add(s);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fr.close();
                entrada.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }


        }
        return temp;
    }   
    
    public static void write(String url, Vector<String> fileReady) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        
        try {
            fw = new FileWriter(url);
            bw = new BufferedWriter(fw);
            for (String fila : fileReady) {
                bw.write(fila + "\n");
            }
            
        } catch (java.io.IOException ioex) {
            ioex.printStackTrace();
        }finally{
            
            if(bw!=null){
                try {
                    bw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            
             if(fw!=null){
                try {
                   fw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            
            
        }
    }    
    
    public static void readContentFileSystemOut(String url) {
        FileReader fr = null;
        BufferedReader entrada = null;
        try {
            fr = new FileReader(url);
            entrada = new BufferedReader(fr);
            String s;
            while ((s = entrada.readLine()) != null) {
                System.out.println("" + s);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fr.close();
                entrada.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }    
}
//    public static File getFileDirectorioEjecucion() {
//        /*if (WORKING_DIRECTORY == null) {
//        try {
//        URL url = ArchivoUtil.class.getResource("/root.txt");
//        //System.out.println(url);
//        if (url.getProtocol().equals("file")) {
//        File f = new File(url.toURI());
//        f = f.getParentFile().getParentFile().getParentFile();
//        WORKING_DIRECTORY = f;
//        } else if (url.getProtocol().equals("jar")) {
//        String expected = "!/root.txt";
//        String s = url.toString();
//        s = s.substring(4);
//        s = s.substring(0, s.length() - expected.length());
//        File f = new File(new URL(s).toURI());
//        f = f.getParentFile();
//        WORKING_DIRECTORY = f;
//        }
//        } catch (Exception e) {
//        WORKING_DIRECTORY = new File(".");
//        }
//        }*/
//        return new File(getPathDirectorioEjecucion());
//    }
