/**
 * 
 */
package com.java.licencia;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;

/**
 * @author ivan
 *
 */
public class TestDirMacWindows {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			// Mostar Sistema Operativo e IP del Sistema
			System.out.println("Sistema Operativo : " + System.getProperty("os.name"));
			System.out.println("Dirección IP : " + InetAddress.getLocalHost().getHostAddress());
			
			// Ejecutar comando CMD
			Process cmd = Runtime.getRuntime().exec("ipconfig /all");
			InputStreamReader leerEntrada = new InputStreamReader(cmd.getInputStream());
			BufferedReader leerBufer = new BufferedReader(leerEntrada);
			
			// Salida de la ejecución
			String linea;
			boolean bandera = false;
			String dirMac;
			while((linea = leerBufer.readLine()) != null){
				// Obtener Direccion Mac
				if(linea.equals("Adaptador de LAN inal mbrica Wi-Fi:")) //
					bandera = true;
				if(bandera == true){
					if(linea.contains("Direcci¢n f¡sica. . . . . . . . . . . . . : ")){
						dirMac = linea.substring(linea.indexOf(":") + 2);
						System.out.println("Dirección Mac: " + dirMac);
						bandera = false;
					}
				}
			}
			leerBufer.close();
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
}
