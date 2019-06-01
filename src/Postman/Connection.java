package Postman;

import com.sun.javafx.iio.ios.IosDescriptor;

import java.io.*;
import java.net.Socket;
import java.util.stream.Collectors;
//SEND|USER|NAMEOFFILE|SIZEOFFILE
//DOWN|USER|FILENAME|SIZEOFFILE
//SHARE|USER|TOUSER
//LIST|USER
public class Connection {

    private Socket socket;

    private Connection() throws IOException{
            this.socket = new Socket("localhost",6022386);
    }

    public Connection factory() throws IOException{
        Connection tmp = tmp = new Connection();
        return tmp;
    }

    public void sendMessage(String  message) throws IOException{
            DataOutputStream os =new DataOutputStream(this.socket.getOutputStream());
            os.writeUTF(message);
    }
    public String getMessage() throws IOException{
        DataInputStream is =new DataInputStream(this.socket.getInputStream());
        String message = is.readUTF();
         return message;
    }
    public void sendFile(String message, File file) throws IOException{
        byte [] mybytearray  = new byte [(int)file.length()];
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(mybytearray,0,mybytearray.length);
        OutputStream os = socket.getOutputStream();
        os.write(mybytearray,0,mybytearray.length);
        os.flush();
    }

    public void downloadFile(String message, String filePath) throws IOException{
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
    }
}
