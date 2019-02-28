import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServerNPort extends Thread {

	 private static ServerSocket server;
	 static int count = 0;
   static Socket socket = null;
	 int processIdOffset;
   int tPort;
   HashMap<Integer,String> processTable;
	
	public ServerNPort(int port, int tPort, HashMap<Integer, String> processTable) throws IOException {

		server = new ServerSocket(port);//server
		this.tPort = tPort;
		this.processIdOffset = this.processIdOffset;
		this.processTable = processTable;
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
	
			thread = new CommandHandler(socket, count, tPort, processTable);
			thread.start();
	}
	catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
	}
			
			
	}

}
		 
		 
}