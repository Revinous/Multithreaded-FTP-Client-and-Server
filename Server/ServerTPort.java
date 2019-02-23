import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTPort extends Thread {

	private static ServerSocket server;
	static int count = 0;
	static int[][] commands = new int[512][512];
	
	static Socket socket = null;
	
	public ServerTPort(int port) throws IOException {

		server = new ServerSocket(port);//server
		
	}
	@Override
	public void run() {
		 
	while(true) {
			 count++;
			 System.out.println("--------------------------------------------");
			 System.out.println("Waiting for client terminate command request");
			try {
				socket = server.accept();
				ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			
				System.out.println("Connected!" + 	inputStream.readObject());
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			System.out.println("Client" + count +"connected");
			
	
	
			
			
	}

}
		 
		 
}