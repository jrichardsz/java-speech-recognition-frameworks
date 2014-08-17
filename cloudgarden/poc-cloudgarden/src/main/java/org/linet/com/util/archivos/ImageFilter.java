package org.linet.com.util.archivos;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author RLC-RM
 */
public class ImageFilter extends FileFilter {

//Accept all directories and all gif, jpg, tiff, or png files.
    public boolean accept(File f) {

        if (f.isDirectory()) {
            return true;
        }
        String s = f.getName();
        String extension;
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            extension = s.substring(i + 1).toLowerCase();
            if (extension.equals("tiff")
                    || extension.equals("tif")
                    || extension.equals("gif")
                    || extension.equals("jpeg")
                    || extension.equals("jpg")
                    || extension.equals("pgm")
                    || extension.equals("bmp")
                    || extension.equals("png")) {
                return true;
            }
        }

        return false;

    }

//The description of this filter
    public String getDescription() {
        return "Solo Imagenes";
    }
}
