package v2;

import java.io.*;

public class Server extends Conexion {

	public Server() throws IOException { super("servidor");	}

	public void startSever() {
		while (true) {
			try {
	            
				cSocket = sSocket.accept();

				DataInputStream entrada = new DataInputStream(cSocket.getInputStream());

				salidaServidor = new DataOutputStream(cSocket.getOutputStream());

				msgServidor = entrada.readUTF();

				System.out.println(msgServidor);

				String res = "m^server@127.0.0.1^-^alguien entro al chat^";

				salidaServidor.writeUTF(res);

			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
