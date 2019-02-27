import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;

public class CommandHandler extends Thread {
	 Socket socket;
	 int clientNo;	
	 String workingDirectory;
	 int tPort;
	 int commandId;
  HashMap<Integer,String> processTable;
	
	public CommandHandler(Socket inSocket, int count, int tPort, HashMap<Integer, String> processTable) throws ClassNotFoundException, IOException {
		socket = inSocket;
		clientNo = count;
		this.tPort = tPort;
		this.processTable = processTable;
		commandId = 1;
		workingDirectory = System.getProperty("user.dir");//curemt workimg dirctory
	}
	@Override
	public void run() {
		// TODO Auto-generated constructor stub
			try {
				ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
				
			while(true) {
				//get command(request) from client
				String command = (String) inputStream.readObject();
		
				String[] commandAndValue = command.split(" ");
				if(commandAndValue[0].equals("quit")) {
					
					 outputStream.writeObject("quit");
					 inputStream.close();
					 outputStream.close();
					 socket.close();
					 break;
				}
					
				System.out.println("command recieved - > at client no ->"+clientNo + " - > " + command);
				
			
				workingDirectory.concat("/");
				switch(commandAndValue[0]) {
				
				case "get" : //Server send file to client
					processTable.put(commandId, "Running " + command); //1 is running
					outputStream.writeObject(String.valueOf(commandId++));
					File myfile = new File(commandAndValue[1]);
					int length=(int) myfile.length();
					int counter=0;
					byte[] a = new byte[length];
					int rem=length%1000;
					int limit=length-rem;
					BufferedInputStream br = new BufferedInputStream(new FileInputStream(myfile));
					br.read(a,0,a.length);
					outputStream.writeInt(length);
					if(length>=1000)
					{
						while(counter<limit)
						{
							outputStream.write(a,counter,1000);
							counter+=1000;
							outputStream.flush();
						}
						if(rem!=0)
						{
							outputStream.write(a,counter,rem);
						}
					}					
					else
					{
						outputStream.write(a,counter,length);
					}
					
//					File myfile = new File(workingDirectory+"/"+commandAndValue[1]);
//					byte[] arr = new byte[(int)myfile.length()];
//					
//					BufferedInputStream br = new BufferedInputStream(new FileInputStream(myfile));
//					br.read(arr,0,arr.length);
//				//	System.out.println(arr.length);
//					outputStream.writeInt((int)myfile.length());
//					outputStream.write(arr,0,arr.length);	
					
					outputStream.flush();
					br.close();
					break;
					
				case "put" ://Server recieves file from client
					processTable.put(commandId, "Running " + command); //1 is running
					outputStream.writeObject(String.valueOf(commandId++));
					int l=inputStream.readInt();
					a = new byte[l];
					counter=0;
					rem=l%1000;
					limit=l-rem;
					FileOutputStream fos = new FileOutputStream(commandAndValue[1]);
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					if(l>=1000)
					{
						while(counter<limit)
						{
							inputStream.read(a,counter,1000);
							counter+=1000;
						}
						if(rem!=0)
						{
							System.out.println("in if");
							//counter-=1000;
							System.out.println("The value of counter in IF is "+counter);
							inputStream.read(a,counter,rem);
						}
					}					
					else
					{
						inputStream.read(a,counter,l);
					}
				//	System.out.println("wait1");
//					arr = new byte[inputStream.readInt()];
//					
//					FileOutputStream fos = new FileOutputStream(commandAndValue[1]);
//					BufferedOutputStream bos = new BufferedOutputStream(fos);
//					
//					inputStream.read(arr,0,arr.length);
					bos.write(a,0,a.length);
					bos.close();
					fos.close();
					break;
				
				case "delete":
					 File file = new File(commandAndValue[1]);
				        if(file.delete()){
				        	outputStream.writeObject(commandAndValue[1]+ " File deleted");
				        }else 
				        	outputStream.writeObject("File" + commandAndValue[1] + "doesn't exists in project root directory");
					break;
					
				case "ls":
					
					File folder = new File(workingDirectory);
					File[] listOfFiles = folder.listFiles();
					String list = "";
					for (int i = 0; i < listOfFiles.length; i++) {
					  
					  list = list + listOfFiles[i].getName() + "\n";
				
					}
					outputStream.writeObject(list);
					break;
				
				case "cd":
						if(commandAndValue[1].equals(".."))
						{
            System.out.println("Inside cd..");
							int index = workingDirectory.lastIndexOf('/');
						    workingDirectory = workingDirectory.substring(0,index);
						    outputStream.writeObject("changing working directory");
						}
						else{
							workingDirectory = workingDirectory + "/" + commandAndValue[1];
							outputStream.writeObject("changing working directory");
						}
						
					break;
				case "mkdir":
					
					boolean fileCreated = new File(workingDirectory + "/" + commandAndValue[1]).mkdirs();
					if(fileCreated)
						outputStream.writeObject("Directory "+ commandAndValue[1] +" created successfully");
					else
						outputStream.writeObject("Opps! Directory cannot be created. May be directory name already exists");
					break;
				
				case "quit":
					 break;
					 
				case "pwd":
					outputStream.writeObject(workingDirectory);
					break;
				default:
					outputStream.writeObject("-bash: "+ command +" : command not found");
				
				}
				 
			
			}
			
			 
			
		
		}
		catch(Exception e) {
				e.printStackTrace();
			}
		}
	}