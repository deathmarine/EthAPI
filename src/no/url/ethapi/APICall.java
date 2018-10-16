package no.url.ethapi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.simple.parser.JSONParser;

public class APICall {
	String raw_data;
	JSONParser parser = new JSONParser(); 
	
	public APICall(String data) {
		this.raw_data = data;
	}

	/**
	 * Preform a one time connection at a specific IP and port 
	 * 
	 * @param ip_address
	 * @param port
	 * @param command
	 * @return String - JSON
	 * @throws UnknownHostException
	 */
	public static String connect(String ip_address, int port, String command) throws UnknownHostException {
		try {
			Socket sock = new Socket(InetAddress.getByName(ip_address), port);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));

			bw.write(command);
			bw.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			String line = br.readLine();
			br.close();
			bw.close();
			sock.close();
			return line;
		} catch (IOException e) {
			System.out.println("Unable to connect to "+ip_address+":"+port);
			e.printStackTrace();
		}
		return null;
	}
}
