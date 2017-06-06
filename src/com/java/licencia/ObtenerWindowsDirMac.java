/**
 * 
 */
package com.java.licencia;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author ivana
 *
 */
public class ObtenerWindowsDirMac {
	
	static String ObtenerDir(){
		String v_dirMac = "";
		try {
			// Ejecutar comando CMD
			Process cmd = Runtime.getRuntime().exec("ipconfig /all");
			InputStreamReader leerEntrada = new InputStreamReader(cmd.getInputStream());
			BufferedReader leerBufer = new BufferedReader(leerEntrada);
			
			// Salida de la ejecución
			String v_linea;
			boolean v_bandera = false;
			
			while((v_linea = leerBufer.readLine()) != null){
				// Obtener Direccion Mac
				if(v_linea.equals("Adaptador de LAN inal mbrica Wi-Fi:")) //
					v_bandera = true;
				if(v_bandera == true){
					if(v_linea.contains("Direcci¢n f¡sica. . . . . . . . . . . . . : ")){
						v_dirMac = v_linea.substring(v_linea.indexOf(":") + 2);
						v_bandera = false;
					}
				}
			}
			leerBufer.close();
		}catch(Throwable t){
			t.printStackTrace();
		}
		return v_dirMac;
	}
	
}
