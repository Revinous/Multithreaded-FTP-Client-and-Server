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
			     commandId = (String) inputStream.readObject();
			    System.out.println("Thread Running in Background. Please use the following CommandID to terminate the command : "+commandId);
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
	
			case "put&"://client sends file to server
      terminated = false;
				//1) Send Command to Server
					outputStream.writeObject("put "+commandAndValue[1]);
					//2) Receive CommandId from Server
					commandId = (String) inputStream.readObject();
					  System.out.println("Thread Running in Background. Please use the following CommandID to terminate the command : "+commandId);
					File myfile = new File(commandAndValue[1]);
					length=(int) myfile.length();
           System.out.println("writing length");
           outputStream.writeInt(length);
					System.out.println("written length");
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
              System.out.println("Put Operation Terminated by user!");
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
		}
		
		
	} catch (IOException | ClassNotFoundException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	
}
