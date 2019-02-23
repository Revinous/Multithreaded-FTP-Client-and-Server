import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AmpersandHandler implements Runnable {
	
	String[] commandAndValue;
	String input = "";		
	ObjectOutputStream outputStreamAmper = null;
	ObjectInputStream inputStreamAmper = null;
	Socket socket;
	String commandId;
	public AmpersandHandler(String input, Socket socket) throws IOException {
		this.input = input;
		this.commandAndValue = input.split(" ");
		this.socket = socket;
		System.out.println(input);
		
	}
	
	public void run() {
		try {
			
		outputStreamAmper = new  ObjectOutputStream(socket.getOutputStream());
		//inputStreamAmper = new ObjectInputStream(socket.getInputStream());
		switch(commandAndValue[0]) {
		
			case "get&"://client receives file from server
				
				Thread.sleep(10000);
				System.out.println(input);
				System.out.println("in Get&Y");
				break;
	
			case "put&"://client sends file to server
				//1) Send Command to Server
				outputStreamAmper.writeObject(input);
				//2) Receive CommandId from Server
				commandId = (String) inputStreamAmper.readObject();
				System.out.println(commandId);
				break;
		}
		
		
	} catch (ClassNotFoundException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	
}
