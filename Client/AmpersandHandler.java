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
		ObjectOutputStream outputStream = null;
		ObjectInputStream inputStream = null;
	Socket socketAmperHandler;
   String commandId;

	public AmpersandHandler(String input, int nPort) throws IOException {
		this.input = input;
		this.commandAndValue = input.split(" ");
    socketAmperHandler = new Socket("127.0.0.1", nPort);
		
	}
	
	public void run() {
		try {
			
			outputStream = new  ObjectOutputStream(socketAmperHandler.getOutputStream());
		  inputStream = new ObjectInputStream(socketAmperHandler.getInputStream());
   
		switch(commandAndValue[0]) {
			case "get&"://client receives file from server
          outputStream.writeObject("get "+commandAndValue[1]);
				//Thread.sleep(10000);
			     commandId = (String) inputStream.readObject();
			    System.out.println("Thread Running in Background. Please use the following CommandID to terminate the command : "+commandId);
					
					byte[] arr = new byte[inputStream.readInt()];
					FileOutputStream fos = new FileOutputStream(commandAndValue[1]);
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					
					inputStream.read(arr,0,arr.length);
					bos.write(arr,0,arr.length);
				
					
					bos.close();
					fos.close();
				//System.out.println("in Get&Y");
				
				break;
	
			case "put&"://client sends file to server
					//1) Send Command to Server
					outputStream.writeObject("put "+commandAndValue[1]);
					//2) Receive CommandId from Server
					commandId = (String) inputStream.readObject();
					System.out.println("Thread Running in Background. Please use the following CommandID to terminate the command : "+commandId);
					
					File myfile = new File(commandAndValue[1]);
					arr = new byte[(int)myfile.length()];
					BufferedInputStream br = new BufferedInputStream(new FileInputStream(myfile));
					br.read(arr,0,arr.length);
					outputStream.writeInt((int)myfile.length());
					outputStream.write(arr,0,arr.length);	
					
					br.close();
					outputStream.flush();
					break;
		}
		
		
	} catch (IOException | ClassNotFoundException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	
}
