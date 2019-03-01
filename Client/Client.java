import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		Socket socket = null;
		ObjectOutputStream outputStream = null;
		ObjectInputStream inputStream = null;
     String serverName = args[0];
		int nPort = Integer.parseInt(args[1]);
		int tPort = Integer.parseInt(args[2]);
		Scanner s = new Scanner(System.in);
		String currentWorkingDirectory = "myftp>";
		socket = new Socket(serverName, nPort);		
		String input = "";		
    String commandId;
		AmpersandHandler ampersandHandlerThread;
	
		outputStream = new  ObjectOutputStream(socket.getOutputStream());
		inputStream = new ObjectInputStream(socket.getInputStream());
		while(!input.equals("quit")) {
			System.out.print(currentWorkingDirectory);
			input = s.nextLine();
			String[] commandAndValue = input.split(" ");
				
			switch(commandAndValue[0]) {
			
				case "get"://client receives file from server
          outputStream.writeObject("get "+commandAndValue[1]);
				//Thread.sleep(10000);
          commandId = (String) inputStream.readObject();
			    System.out.println("CommandID : "+commandId);
  				boolean terminated = false;
				int length=inputStream.readInt();
				byte[] a = new byte[length];
				int counter=0;
				int rem=length%1000;
				int limit=length-rem;
				FileOutputStream fos = new FileOutputStream(commandAndValue[1]);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				if(length>=1000)
				{
					while(counter<limit)
					{
             String state = (String)inputStream.readObject();
             if(state.contains("terminated"))
                 terminated = true;
                 
             if(terminated)
               break;
               
 						inputStream.read(a,counter,1000);
						counter+=1000;
					}
          if(terminated){
        	 File file = new File(commandAndValue[1]);
          file.delete();
          }
          else if(rem!=0)
					{
						inputStream.read(a,counter,rem);
					}
         
          
				}					
				else
				{
					inputStream.read(a,counter,length);
				}
					bos.write(a,0,a.length);
//					byte[] arr = new byte[inputStream.readInt()];
//					FileOutputStream fos = new FileOutputStream(commandAndValue[1]);
//					BufferedOutputStream bos = new BufferedOutputStream(fos);
//					
//					inputStream.read(arr,0,arr.length);
//					bos.write(arr,0,arr.length);
				
					
					bos.close();
					fos.close();
				//System.out.println("in Get&Y");
					break;
		
				case "put"://client sends file to server
         terminated = false;
				//1) Send Command to Server
					outputStream.writeObject("put "+commandAndValue[1]);
					//2) Receive CommandId from Server
					commandId = (String) inputStream.readObject();
					System.out.println("CommandID, Terminate not allowed: "+commandId);
					File myfile = new File(commandAndValue[1]);
					length=(int) myfile.length();
           System.out.println("*Termination not allowed as command not appended with &");
           outputStream.writeInt(length);
          counter=0;
					a = new byte[length];
					rem=length%1000;
					limit=length-rem;
					BufferedInputStream br = new BufferedInputStream(new FileInputStream(myfile));
					br.read(a,0,a.length);
					
           outputStream.flush();
					if(length>=1000)
					{
						while(counter<limit)
						{
             String state = (String)inputStream.readObject();
             if(state.contains("terminated"))
               terminated = true;
             
             if(terminated)
               break;
               
							outputStream.write(a,counter,1000);
							counter+=1000;
							outputStream.flush();
						}
            if(terminated)
            {
              System.out.println("Terminated by user!");
            }
						else if(rem!=0)
						{
							outputStream.write(a,counter,rem);
						}
					}					
					else
					{
						outputStream.write(a,counter,length);
					}
					
//					File myfile = new File(commandAndValue[1]);
//					arr = new byte[(int)myfile.length()];
//					BufferedInputStream br = new BufferedInputStream(new FileInputStream(myfile));
//					br.read(arr,0,arr.length);
//					outputStream.writeInt((int)myfile.length());
//					outputStream.write(arr,0,arr.length);	
					
					br.close();
					outputStream.flush();
					break;
	    
				case "get&":
				case "put&":
					
					AmpersandHandler runnable = new AmpersandHandler(input, nPort);
					Thread thread = new Thread(runnable,"thread");
					thread.start();
          Thread.sleep(100);
					break;
					
                              
				case "terminate":
					//Relay on tPort
					Socket socketTerminate = new Socket("127.0.0.1", tPort);
					ObjectOutputStream outputStreamTerminate = new  ObjectOutputStream(socketTerminate.getOutputStream());
					outputStreamTerminate.writeObject(commandAndValue[1]);
					socketTerminate.close();
         Thread.sleep(1000);
					//also kill thread executing get& or put& and clear all garbage
					
					//..logic yet to be implemented
				break;

				default:
					outputStream.writeObject(input);
					 String message = (String) inputStream.readObject();
					System.out.println(message);
					if(message.equals("quit")) {
						System.out.println("GoodBye!");
    		  outputStream.close();
		    	inputStream.close();
						socket.close();
					}
			}
					
		}
			//outputStream.close();
			//inputStream.close();
			Thread.sleep(1);
		
			
		
		
	}

}
