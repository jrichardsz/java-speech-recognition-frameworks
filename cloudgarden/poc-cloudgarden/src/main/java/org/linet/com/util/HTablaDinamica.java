/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.linet.com.util;

/**
 *
 * @author RCMLEONI-271828
 */

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

/*************************************************************************************
Modelo de datos para un JTable.
Permite añadir y eliminar filas.
**************************************************************************************/
public class HTablaDinamica extends AbstractTableModel {
   Vector filas = new Vector();          // Vector de filas (vector de vectores)
   Vector columnas = new Vector();       // Vector de columnas

   /*** Constructor: carga datos ***/
   public HTablaDinamica() { cargar_datos(); }

   /**********************************************
    * Carga de datos
    ************************************************/
   void cargar_datos() {

       /**** Creo el vector que define las columnas ***/
       columnas.add( (String) "Campos");
       columnas.add( (String) "Denominacion");
       columnas.add( (String) "Validar");
       columnas.add( (String) "Tipo de Dato");
       columnas.add( (String) "PK");


    }

    /*************** getColumnName() *********************/
    public String getColumnName( int c ) {
       return (String) columnas.elementAt( c );
    }

    /*************** getColumnCount() ******************/
    public int getColumnCount() {
       return columnas.size();
    }

    /****************** getRowCount() *******************/
    public int getRowCount() {
       return filas.size();
    }

    /*******************  setValueAt() *********************/
    /*** Llamada automáticamente cuando termina la edición de una celda ***/
    public void setValueAt( Object valor, int fila, int col ) {
       Vector v = (Vector) filas.elementAt(fila);
       v.set( col, valor);
    }

    /*******************  getValueAt() *********************/
    public Object getValueAt( int fila, int col ) {
       Vector v = (Vector) filas.elementAt(fila);
       return v.elementAt( col );
    }

    /*** Para que todas las celdas sean editables ****/
    public boolean isCellEditable( int fila, int col ) { return true; }

    public Vector getColumnas() {
        return columnas;
    }

    public void setColumnas(Vector columnas) {
        this.columnas = columnas;
    }

    public Vector getFilas() {
        return filas;
    }

    public void setFilas(Vector filas) {
        this.filas = filas;
    }

 }    /////////////////////////// Fin de clase HTablaDinamica \\\\\\\\\\\\\\\\\\\\\\\\ºº
