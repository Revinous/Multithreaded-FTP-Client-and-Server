import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AmpersandHandler implements Runnable {
	
	String[] commandAndValue;
	String input = "";		
	DataOutputStream  outputStreamAmper = null;
	DataInputStream   inputStreamAmper = null;
	Socket socket;
	String commandId;
	public AmpersandHandler(String input, Socket socket) throws IOException {
		this.input = input;
		this.commandAndValue = input.split(" ");
		this.socket = socket;
		System.out.println("Hello In Constrcutor!");
		
	}
	
	public void run() {
		try {
			
		outputStreamAmper = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		inputStreamAmper = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

		switch(commandAndValue[0]) {
			case "get&"://client receives file from server

				//Thread.sleep(10000);
				System.out.println(inputStreamAmper.readChar());
				//System.out.println("in Get&Y");
				
				break;
	
			case "put&"://client sends file to server
				//1) Send Command to Server
			//	outputStreamAmper.writeObject(input);
				//2) Receive CommandId from Server
				//commandId = (String) inputStreamAmper.readObject();
				System.out.println(commandId);
				break;
		}
		
		
	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	
}
