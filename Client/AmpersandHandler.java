import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AmpersandHandler extends Thread {
	
	String[] commandAndValue;
	String input = "";		
	ObjectOutputStream outputStreamAmper = null;
	ObjectInputStream inputStreamAmper = null;
	Socket socket;
	String commandId;
	public AmpersandHandler(String input, Socket socket) {
		this.input = input;
		String[] commandAndValue = input.split(" ");
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			outputStreamAmper = new  ObjectOutputStream(socket.getOutputStream());
			inputStreamAmper = new ObjectInputStream(socket.getInputStream());
		
		switch(commandAndValue[0]) {
		
			case "get&"://client receives file from server
				//1) Send Command to Server
				outputStreamAmper.writeObject(input);
				//2) Receive CommandId from Server
				commandId = (String) inputStreamAmper.readObject();
			
				System.out.println(commandId);
				break;
	
			case "put&"://client sends file to server
				//1) Send Command to Server
				outputStreamAmper.writeObject(input);
				//2) Receive CommandId from Server
				commandId = (String) inputStreamAmper.readObject();
				System.out.println(commandId);
				break;
		}
		
		
	} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	
}
