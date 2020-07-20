package principal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Server {

	private ServerSocket serverSoket;

	private void criarServer(int porta) throws IOException {
		// serviço de escuta
		serverSoket = new ServerSocket(porta);
		System.out.println("Servidor iniciado na porta: " + porta);

	}

	private Socket esperaConexao() throws IOException {
		// canal de comunicação para o serviço
		Socket socket = serverSoket.accept();
		return socket;

	}

	private void tratamento(Socket socket) throws IOException, NoSuchAlgorithmException {
		// protocolo
		// cria stream de entrada e saida
		// trata a conversa entre o cliete e servidor

		try {
			System.out.println("Cliente conectado no ip: " + socket.getInetAddress().getHostAddress());
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream imput = new ObjectInputStream(socket.getInputStream());

			// protocolo
			// cliente envia senha
			// cliente recebe ela criptografada

			String ms = imput.readUTF();
			System.out.println("Mesagem recebida");
			
			output.writeUTF(digest(ms));
			output.flush();

			output.close();
			imput.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Erro no ip: " + socket.getInetAddress().getHostAddress());
			System.out.println("Erro: " + e.getMessage());
		} finally {
			fechaSocket(socket);
		}
	}

	private void fechaSocket(Socket socket) throws IOException {
		socket.close();
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub

		try {

			Server server = new Server();
			System.out.println("Aguardando conexão...");
			server.criarServer(55555);
			while (true) {
				Socket socket = server.esperaConexao();
				System.out.println("Cliente conectado.");
				server.tratamento(socket);
				System.out.println("Cliente finalizado.");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String digest(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		//O código abaixo utiliza o algorítmo SHA-256 para codificar um array de bytes em um array de tamanho fixo (16 bytes). Cada byte desse array é convertido para uma versão hexadecimal:
		MessageDigest algoritmo = MessageDigest.getInstance("SHA-256");
		byte digestMessage[] = algoritmo.digest(password.getBytes("UTF-8"));
		StringBuilder hexPassword = new StringBuilder();
		for (byte aByte : digestMessage) {
			hexPassword.append(String.format("%02X", 0xFF & aByte));
		}
		return hexPassword.toString();
	}

}
