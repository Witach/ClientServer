package sample.ServerConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
public class Session {
    private Socket socket;
    private Logger log = LoggerFactory.getLogger(getClass());
    private Session(Socket socket){
        this.socket = socket;
    }

    static public Session factory(Socket socket){
        Session tmp = tmp = new Session(socket);
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
    public void sendFile(File file) throws IOException{
        log.info("sending file");
        byte [] mybytearray  = new byte [(int)file.length()];
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(mybytearray,0,mybytearray.length);
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeUTF(file.getName()+"|"+file.length());
        os.write(mybytearray,0,mybytearray.length);
        os.flush();
        log.info("sending end");
    }

    public byte[] downloadFile( int sizeOfFile) throws IOException{
        byte [] mybytearray  = new byte [sizeOfFile];
        sendMessage("GO");
        InputStream is = socket.getInputStream();
        int bytesRead = is.read(mybytearray,0,mybytearray.length);
        int current = bytesRead;
        do {
            bytesRead = is.read(mybytearray, current, (mybytearray.length-current));
            log.info("czy nieskończona");
            if(bytesRead > 0) current += bytesRead;
        } while(bytesRead > 0);
        log.info("downloadding end");
        return mybytearray;
    }

}
