/**
 * 
 */
package com.java.verifica_auto.licencia;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.text.ParseException;
import java.util.StringTokenizer;

/**
 * @author ivana
 *
 */
public class DireccionMacWindows {
	/**
	 * @param args
	 */
	private final static String getMacAddress() throws IOException {
		String os = System.getProperty("os.name");
		try {
			if (os.startsWith("Windows")) {
				return windowsParseMacAddress(windowsRunIpConfigCommand());
				/*
				 * } else if (os.startsWith("Linux")) { return
				 * linuxParseMacAddress(linuxRunIfConfigCommand());
				 */
			} else {
				throw new IOException("Sistema operativo desconocido: " + os);
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
			throw new IOException(ex.getMessage());
		}
	}

	private final static String windowsParseMacAddress(String ipConfigResponse) throws ParseException {
		String localHost = null;
		try {
			localHost = InetAddress.getLocalHost().getHostAddress();
			System.out.println(localHost);
		} catch (java.net.UnknownHostException ex) {
			ex.printStackTrace();
			throw new ParseException(ex.getMessage(), 0);
		}

		StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
		String lastMacAddress = null;
		while (tokenizer.hasMoreTokens()) {
			String line = tokenizer.nextToken().trim();

			if (line.endsWith(localHost) && lastMacAddress != null) {
				return lastMacAddress;
			}

			int macAddressPosition = line.indexOf(":");
			if (macAddressPosition <= 0)
				continue;

			String macAddressCandidate = line.substring(macAddressPosition + 1).trim();
			if (windowsIsMacAddress(macAddressCandidate)) {
				lastMacAddress = macAddressCandidate;
				continue;
			}
		}

		ParseException ex = new ParseException("Imposible obtener dirección MAC desde [" + ipConfigResponse + "]", 0);
		ex.printStackTrace();
		throw ex;
	}
	
	private final static boolean windowsIsMacAddress(String macAddressCandidate) {
		if (macAddressCandidate.length() != 17)
			return false;

		return true;
	}
	
	private final static String windowsRunIpConfigCommand() throws IOException {
		Process p = Runtime.getRuntime().exec("ipconfig /all");
		InputStream stdoutStream = new BufferedInputStream(p.getInputStream());

		StringBuffer buffer = new StringBuffer();
		for (;;) {
			int c = stdoutStream.read();
			if (c == -1)
				break;
			buffer.append((char) c);
		}
		String outputText = buffer.toString();

		stdoutStream.close();

		return outputText;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			System.out.println("Sistema Operativo : " + System.getProperty("os.name"));
			System.out.println("Dirección IP : " + InetAddress.getLocalHost().getHostAddress());
			// System.out.println("Dirección Física (MAC): " + getMacAddress());
			//getMacAddress();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
