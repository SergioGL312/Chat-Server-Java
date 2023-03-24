package v2;

import java.io.*;

public class Server extends Conexion {

	public Server() throws IOException { super("servidor");	}

	public void startSever() {
		while (true) {
			try {
	            
				cSocket = sSocket.accept();
				
				clientes.add(cSocket);

				DataInputStream entrada = new DataInputStream(cSocket.getInputStream());

				ChatClientHandler chat = new ChatClientHandler(cSocket, clientes);

				Thread hilo = new Thread(chat);
				
				hilo.start();

			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
