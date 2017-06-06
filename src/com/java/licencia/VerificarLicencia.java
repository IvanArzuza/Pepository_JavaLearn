/**
 * 
 */
package com.java.licencia;
/**
 * @author ivana
 *
 */
public class VerificarLicencia {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try{
			String v_sistOp = System.getProperty("os.name");
			Boolean v_macRegistrada = false;
			
			// Verificar Sistema Operativo
			if(v_sistOp.startsWith("Windows")){				
				System.out.println("Sistema Operativo: " + v_sistOp);
				
				// Obtener Dirección Mac del Sistema Windows
				String v_dirMac = ObtenerWindowsDirMac.ObtenerDir();
				System.out.println("Dirección Mac: " + v_dirMac);
				
				//Verificar licencia
				v_macRegistrada = VerificarMacRegistrada.VerificaExcel(v_dirMac);
				System.out.println("Mac (" + v_dirMac + ") registrada en archivo excel local: " + v_macRegistrada);
				
			}else if(v_sistOp.startsWith("Linux")){
				System.out.println("Sistema Operativo: " + v_sistOp);
				
				// Obtener Dirección Mac del Sistema Linux
				String v_dirMac = ObtenerWindowsDirMac.ObtenerDir();
				System.out.println("Dirección Mac: " + v_dirMac);
				
				//Verificar licencia
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
}