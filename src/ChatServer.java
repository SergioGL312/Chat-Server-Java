
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.io.IOException;

public class ChatServer implements Runnable {

	public static final int PORT = 1090;

	private static Vector<Socket> clientes = new Vector<Socket>();

	public ChatServer() {

	}

	public void principal() {
		ServerSocket sSocket;
		try {
			sSocket = new ServerSocket(PORT);
			System.out.println("Esperando peticiones...");
		} catch (Exception ex) {
			System.out.println(ex.toString());
			System.out.println("No se pudo abrir el servicio " + " para aceptar conexiones");
			return;
		}
		while (true) {
			try {
				Socket socket;
				// Se acepta la conexion de un cliente
				socket = sSocket.accept();

				clientes.add(socket);

				HiloChat hiloChat = new HiloChat(socket, clientes);

				Thread miHilo = new Thread(hiloChat); // Es lo mismo que this

				miHilo.start();

			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChatServer cs = new ChatServer();
		cs.principal();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ServerSocket sSocket;
		try {
			sSocket = new ServerSocket(PORT);
			System.out.println("Esperando peticiones...");
		} catch (Exception ex) {
			System.out.println(ex.toString());
			System.out.println("No se pudo abrir el servicio " + " para aceptar conexiones");
			return;
		}
		while (true) {
			try {
				Socket socket;
				// Se acepta la conexion de un cliente
				socket = sSocket.accept();
				DataInputStream netIn = new DataInputStream(socket.getInputStream());
				DataOutputStream netOut = new DataOutputStream(socket.getOutputStream());

				String msg = netIn.readUTF();
				System.out.println(msg);
				String res = "m^server@127.0.0.1^-^alguien entro al chat^";

				netOut.writeUTF(res);

			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

}
