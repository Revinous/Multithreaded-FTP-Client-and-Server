import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AmpersandHandler extends Thread {
	
	String[] commandAndValue;
	ObjectOutputStream outputStream = null;
	ObjectInputStream inputStream = null;
	public AmpersandHandler(String[] commandAndValue, Socket socket) {
		this.commandAndValue = commandAndValue;
	}
	
	@Override
	public void run() {
		switch(commandAndValue[0]) {
		
			case "get&"://client receidves file from server
				
				break;
	
			case "put&"://client sends file to server
				
				break;
		}
		
		
	}

	
}
