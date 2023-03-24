import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

public class HiloChat implements Runnable {

	private Socket socket;
	private Vector<Socket> clientes;
	private DataInputStream netIn;

	public HiloChat(Socket socket, Vector<Socket> clientes) throws IOException {
		this.socket = socket;
		this.clientes = clientes;
		netIn = new DataInputStream(this.socket.getInputStream());
	}

	private void enviaMsg(String msg) {
		DataOutputStream netOut;
		for (Socket socket : clientes) {
			try {
				netOut = new DataOutputStream(socket.getOutputStream());
				netOut.writeUTF(msg);
			} catch (IOException e) {
				// TODO: handle exception
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				String msg = netIn.readUTF();
				if (msg.startsWith("m")) {
					enviaMsg(msg);
				} else {
					String res = "m^Server";
					InetAddress miIp = InetAddress.getLocalHost();
					res += miIp.getHostAddress() + "^-^";
					StringTokenizer st = new StringTokenizer(msg, "^");
					st.nextToken();
					String usrIp = st.nextToken();
					String[] token = usrIp.split("@");
					// m^server@127.0.0.1^-^sfdjlk
					res += token[0];
					if (msg.startsWith("j")) {
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
