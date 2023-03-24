package v2;

import java.io.*;
import java.util.*;
import java.net.*;

public class ChatClientHandler implements Runnable {
	
	private Socket socket;
	private Vector<Socket> clientes;
	private DataInputStream entrada;
	
	public ChatClientHandler(Socket socket, Vector<Socket> clientes) throws IOException {
		this.socket = socket;
		
		this.clientes = clientes;
		
		entrada = new DataInputStream(this.socket.getInputStream());
	}
	
	private void enviaMsg(String msg) {
		DataOutputStream netOut;
		for (Socket socket : clientes) {
			try {
				netOut = new DataOutputStream(socket.getOutputStream());
				
				netOut.writeUTF(msg);
				
			} catch (IOException e) {
				
				System.out.println("[X] Error al enviar msg");
			
			}
		}
	}

	private static final String MENSAJE = "m";
	
	private static final String JOINED = "j";
	
	private static final String DELIM = "^";
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				String msg = entrada.readUTF();
				
				if (msg.startsWith(MENSAJE)) {
					
					enviaMsg(msg);
					
				} else {
					String res = "m^Server";
					
					InetAddress miIp = InetAddress.getLocalHost();
					
					res += miIp.getHostAddress() + "^-^";
					
					StringTokenizer st = new StringTokenizer(msg, DELIM);
					
					st.nextToken();
					
					String usrIp = st.nextToken();
					
					String[] token = usrIp.split("@");
					
					// m^server@127.0.0.1^-^sfdjlk
					res += token[0];
					
					if (msg.startsWith(JOINED)) {
						res += " has joined from " + socket.getRemoteSocketAddress() + "^";
						
						enviaMsg(res);
						
					} else {
						res += " has parted from " + socket.getRemoteSocketAddress() + "^";
						
						clientes.remove(socket);
						
						enviaMsg(res);
						
						return;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				clientes.remove(socket);
				
				e.printStackTrace();
				
				return;
			}
		}
	}

}
