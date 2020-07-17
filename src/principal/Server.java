package principal;
import java.io.IOException;
import java.net.*;

public class Server {
	
	private ServerSocket serverSoket;

	private void criarServer(int porta) throws IOException {
		serverSoket = new ServerSocket(porta);
	}
	
	private Socket esperaConexao() throws IOException {
		Socket socket = serverSoket.accept();
		return socket;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Server server = new Server();
			server.criarServer(80);
			Socket socket = server.esperaConexao();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
