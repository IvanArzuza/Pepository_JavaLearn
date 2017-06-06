/**
 * Manejo de Excel
 */
package com.java.licencia;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
/**
 * @author ivana
 * 
 * ARCHIVO EXCEL LOCAL
 * Libreria POI:
 * https://poi.apache.org/download.html
 * http://poi.apache.org/download.html
 * 
 * SpeadSheet -> 
 * http://aprendiendo-software.blogspot.com.co/2013/01/tutorial-google-spreadsheets-y-java.html
 * https://www.youtube.com/watch?v=nzb9R_oIS94
 * 
 * SpeadSheet - API
 * https://developers.google.com/sheets/
 * https://developers.google.com/sheets/api/v3/
 */
public class TestVerificaMacConExcel {
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		// Archivo Excel local
		
		try{
			// Especificar directorio y archivo
			FileInputStream archivo_xls = new FileInputStream(new File("D:/archivosivana/Eclipse-NWDS/eclipse/java_workspace/JavaLearn/docs/LicenciaDirMac.xls"));
			
			/*
			 * Objeto contenedor del archivo excel
			 */
			@SuppressWarnings("resource")
			HSSFWorkbook libroTrabajo = new HSSFWorkbook(archivo_xls);
			
			/*
			 * Obtener hoja a procesar segun el indice (Ejercicio con un libro especifico)
			 * Objener las filas (iterador) de la hoja a recorrer
			 */
			HSSFSheet hoja = libroTrabajo.getSheetAt(0);
			Iterator<Row> filas = hoja.iterator();
			
			System.out.println(filas.next().getOutlineLevel());
			System.out.println(filas.getClass().getFields().length);
			System.out.println(filas.next().getRowNum());
			
			//Recorrido sobre el libro de trabajo			
			while(filas.hasNext()){
				// 
				HSSFRow fila = (HSSFRow) filas.next();
				//Iterator<Cell> celdas = fila.cellIterator();
				Iterator<Cell> columnas = fila.cellIterator();
							
				//Imprime valor de cada celda
				/*
				while(celdas.hasNext()){
					HSSFCell celda = (HSSFCell) celdas.next();
					System.out.println("<Indice> " + celda.getRowIndex() + " <Valor> " + celda.getStringCellValue());
					//System.out.println("<Indice-> " + (celda.getRowIndex()+1) + " <Valor-> " + celda.getColumnIndex());
				}*/
				HSSFCell columna = (HSSFCell) columnas.next();
				System.out.println("<Indice> " + columna.getRowIndex() + " <Valor> " + columna.getStringCellValue());
				/*
				while(columnas.hasNext()){
					//HSSFCell celda = (HSSFCell) columnas.next();
					//System.out.println("<Indice-> " + celda.getRowIndex() + " <Valor-> " + celda.getStringCellValue());
					System.out.println("<Indice-> " + columna.getRowIndex() + " <Valor-> " + columna.getStringCellValue());
				}
				*/
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}