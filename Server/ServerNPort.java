import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerNPort extends Thread {

	private static ServerSocket server;
	static int count = 0;
	static Socket socket = null;
	int tPort;
	public ServerNPort(int port, int tPort) throws IOException {

		server = new ServerSocket(port);//server
		this.tPort = tPort;
	}
	@Override
	public void run() {
		 
	while(true) {
			count++;
			 
			System.out.println("Waiting for client request");
	try {
			socket = server.accept();	
			System.out.println("Client" + count +"connected");
			CommandHandler thread = null;
	
			thread = new CommandHandler(socket, count, tPort);
			thread.start();
	}
	catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
	}
			
			
	}

}
		 
		 
}