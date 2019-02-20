import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		Socket socket = null;
		ObjectOutputStream outputStream = null;
		ObjectInputStream inputStream = null;
		int nPort = 9876;
		int tPort = 9877;
		Scanner s = new Scanner(System.in);
		String currentWorkingDirectory = "myftp>";
		socket = new Socket("127.0.0.1", nPort);		
		String input = "";		
		AmpersandHandler ampersandHandlerThread;
	
		outputStream = new  ObjectOutputStream(socket.getOutputStream());
		inputStream = new ObjectInputStream(socket.getInputStream());
		while(!input.equals("quit")) {
			System.out.print(currentWorkingDirectory);
			input = s.nextLine();
			String[] commandAndValue = input.split(" ");
				
			switch(commandAndValue[0]) {
			
				case "get"://client receidves file from server
					outputStream.writeObject(input);
					byte[] arr = new byte[inputStream.readInt()];
					
					FileOutputStream fos = new FileOutputStream(commandAndValue[1]);
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					
					inputStream.read(arr,0,arr.length);
					bos.write(arr,0,arr.length);
				
					
					bos.close();
					fos.close();
					break;
		
				case "put"://client sends file to server
					outputStream.writeObject(input);
					File myfile = new File(commandAndValue[1]);
					arr = new byte[(int)myfile.length()];
					BufferedInputStream br = new BufferedInputStream(new FileInputStream(myfile));
					br.read(arr,0,arr.length);
					outputStream.writeInt((int)myfile.length());
					outputStream.write(arr,0,arr.length);	
					
					br.close();
					outputStream.flush();
					break;
	
				case "get&":
				case "put&":
					ampersandHandlerThread = new AmpersandHandler(commandAndValue, socket);
					ampersandHandlerThread.start();
					
					break;
					
				case "terminate":
					//Relay on tPort
					Socket socketTerminate = new Socket("127.0.0.1", tPort);
					ObjectOutputStream outputStreamTerminate = new  ObjectOutputStream(socketTerminate.getOutputStream());
					outputStreamTerminate.writeObject("terminate");
					
					//also kill thread executing get& or put& and clear all garbage
					
					//..logic yet to be implemented
				break;
				default:
					outputStream.writeObject(input);
					String message = (String) inputStream.readObject();
					System.out.println(message);
					if(message.equals("quit")) {
						System.out.println("GoodBye!");
						socket.close();
					}
			}
					
		}
			outputStream.close();
			inputStream.close();
			Thread.sleep(1);
		
			
		
		
	}

}