package Postman;

public class Postman {
    private  String dirPath;
    private  String userName;

    public String getDirPath(){
        return dirPath;
    }

    public String getUserName(){
        return userName;
    }

    public Postman(String userName, String dirPath){
        this.userName = userName;
        this.dirPath = dirPath;
    }

    public void initialize(){

    }
}
