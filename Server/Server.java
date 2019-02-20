import java.io.IOException;

public class Server {
	public int[][] processTable = new int[512][512];
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int nPort = Integer.parseInt(args[0]);
		int tPort = Integer.parseInt(args[1]);
		
		//Start thread to handle normal command at nPort
		
		ServerNPort nThread = new ServerNPort(nPort, tPort);
		nThread.start();
		
		//Start thread to handle terminate command at tPort
		
		ServerTPort tThread = new ServerTPort(tPort);
		tThread.start();
		
	}

}
