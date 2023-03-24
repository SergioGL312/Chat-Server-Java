package v2;

import java.io.*;

public class Client extends Conexion{
	
	public Client() throws IOException { super("cliente"); }
	
	public void startClient() {
		
		try {
			salidaServidor = new DataOutputStream(cSocket.getOutputStream());
			
			salidaServidor.writeUTF(msgServidor);
			
			salidaServidor.close();
			
			cSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage() + "Error en cliente");
		}

	}

}
