import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
public class ServerTPort extends Thread {

	private static ServerSocket server;
	static int count = 0;
	static int[][] commands = new int[512][512];
   HashMap<Integer,String> processTable;
	static Socket socket = null;
	
	public ServerTPort(int port, HashMap<Integer,String> processTable ) throws IOException {
    
    this.processTable = processTable;
		server = new ServerSocket(port);//server
		
	}
	@Override
	public void run() {
		 
	while(true) {
			 count++;
			 System.out.println("--------------------------------------------");
			 
			try {
				socket = server.accept();
				ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			  String commandID = 	(String)inputStream.readObject();
				System.out.println("Terminating Command with ID : " + commandID);
				
     
        System.out.println("Current State of the command "+processTable.get(Integer.parseInt(commandID)));
			  System.out.println("Setting State of the command to terminate");
        String terminateFlag = "terminate ".concat(processTable.get(Integer.parseInt(commandID)));
				processTable.put(Integer.parseInt(commandID),terminateFlag); // change state to terminate (add word terminate)
        System.out.println("Now State of the command "+processTable.get(Integer.parseInt(commandID)));
        
        
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