package v2;

import java.io.*;
import java.net.*;
import java.util.*;

public abstract class Conexion {

	private final int PORT = 1090;
	
	private final String HOST = "localhost";
	
	protected String msgServidor;
	
	protected ServerSocket sSocket;
	
	protected Socket cSocket;
	
	protected Vector<Socket> clientes = new Vector<Socket>();
	
	public Conexion(String tipo) throws IOException {
		
		if (tipo.equalsIgnoreCase("servidor")) {
			
			sSocket = new ServerSocket(PORT);
			
			System.out.println("Esperando peticiones...");
			
			cSocket = new Socket();
			
		}
	}

}
