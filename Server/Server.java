import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.*;
public class Server {
	public static HashMap<Integer,String> processTable;
  public static ReentrantLock lock = new ReentrantLock();
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
     processTable = new HashMap<Integer,String>();
		int nPort = Integer.parseInt(args[0]);
		int tPort = Integer.parseInt(args[1]);
		
		//Start thread to handle normal command at nPort
		
		ServerNPort nThread = new ServerNPort(nPort, tPort, processTable);
		nThread.start();
		
		//Start thread to handle terminate command at tPort
		
		ServerTPort tThread = new ServerTPort(tPort, processTable);
		tThread.start();
		
	}

}
