package Postman;

import com.sun.javafx.iio.ios.IosDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.stream.Collectors;
//SEND|USER|NAMEOFFILE|SIZEOFFILE
//DOWN|USER|FILENAME|SIZEOFFILE
//SHARE|USER|TOUSER
//LIST|USER
public class Connection {

    private Socket socket;
    private Logger log = LoggerFactory.getLogger(getClass());
    private Connection() throws IOException{
            this.socket = new Socket("localhost",6022386);
    }

    static public Connection factory() throws IOException{
        Connection tmp = tmp = new Connection();
        return tmp;
    }

    public void shutDown() throws IOException{
        socket.close();
        log.info("socket colse");
    }

    public void sendMessage(String  message) throws IOException{
        log.info("sending message");
            DataOutputStream os =new DataOutputStream(this.socket.getOutputStream());
            os.writeUTF(message);
        log.info("sending end");
    }
    public String getMessage() throws IOException{
        log.info("getting message");
        DataInputStream is =new DataInputStream(this.socket.getInputStream());
        String message = is.readUTF();
        log.info("getting end");
         return message;
    }
    public void sendFile(String message, File file) throws IOException{
        log.info("sending file");
        byte [] mybytearray  = new byte [(int)file.length()];
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(mybytearray,0,mybytearray.length);
        OutputStream os = socket.getOutputStream();
        os.write(mybytearray,0,mybytearray.length);
        os.flush();
        log.info("sending end");
    }

    public void downloadFile(String message, String filePath) throws IOException{
        log.info("downloadding file");
        sendMessage(message);
        String[] response = getMessage().split("|");
        byte [] mybytearray  = new byte [Integer.parseInt(response[1])];
        InputStream is = socket.getInputStream();
        FileOutputStream fos = new FileOutputStream(filePath+"\\"+response[0]);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        int bytesRead = is.read(mybytearray,0,mybytearray.length);
        int current = bytesRead;
        do {
            bytesRead =
                    is.read(mybytearray, current, (mybytearray.length-current));
            if(bytesRead >= 0) current += bytesRead;
        } while(bytesRead > -1);
        bos.write(mybytearray, 0 , current);
        bos.flush();
        log.info("downloadding end");
    }
}
