package com.java.licencia;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class VerificaMacRegistrada {
	
	static Boolean VerificaExcel(String P_dirMac){
		Boolean v_macRegistrada = false;
		
		try{
			// Especificar directorio y archivo
			FileInputStream archivo_xls = new FileInputStream(new File("D:/archivosivana/Eclipse-NWDS/eclipse/java_workspace/JavaLearn/docs/LicenciaDirMac.xls"));
			
			/*
			 * Objeto contenedor del archivo excel
			 */
			//@SuppressWarnings("resource")
			HSSFWorkbook libroTrabajo = new HSSFWorkbook(archivo_xls);
			
			/*
			 * Obtener hoja a procesar segun el indice (Ejercicio con un libro especifico)
			 * Objener las filas (iterador) de la hoja a recorrer
			 */
			HSSFSheet hoja = libroTrabajo.getSheetAt(0);
			Iterator<Row> filas = hoja.iterator();
			
			//Recorrido sobre el libro de trabajo			
			while(filas.hasNext()){
				// 
				HSSFRow fila = (HSSFRow) filas.next();
				Iterator<Cell> celdas = fila.cellIterator();				
							
				//Imprime valor de cada celda
				while(celdas.hasNext()){
					HSSFCell celda = (HSSFCell) celdas.next();
					if(celda.getStringCellValue().equals(P_dirMac)){
						v_macRegistrada = true;
					}					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		return v_macRegistrada;
	}
}