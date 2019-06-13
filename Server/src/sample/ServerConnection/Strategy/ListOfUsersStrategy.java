package sample.ServerConnection.Strategy;

import sample.ServerConnection.FileControllerSingleton;
import sample.ServerConnection.SemaphoreSingleton;
import sample.ServerConnection.Session;
import sample.ServerConnection.Tokenizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class ListOfUsersStrategy implements Strategy{
    @Override
    public void reply(Session session, String pathToServerDir, String... parameters) throws IOException {
        List<File> csvs = FileControllerSingleton.getInstance().listOfCsv;
        List<String> listOfUsers = new ArrayList<>();
        for(int i=0 ; i<csvs.size();i++){
            Semaphore readerSemaphore = SemaphoreSingleton.getReadersSemaphore(i);
            try{
                readerSemaphore.acquire();
                Scanner scanner = new Scanner(new FileInputStream(csvs.get(i)));
                while(scanner.hasNextLine()){
                   String line = scanner.nextLine();
                   String[] parametersOfFile =  line.split(";");
                   if(parametersOfFile.length>=3){
                       String[] listOfPermmisions = parametersOfFile[2].split(",");
                       for(String user: listOfPermmisions){
                           if(!listOfUsers.contains(user))
                               listOfUsers.add(user);
                       }
                   }
                 if(!listOfUsers.contains(parametersOfFile[1]))
                    listOfUsers.add(parametersOfFile[1]);
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            } finally {
                readerSemaphore.release();;
            }
        }
        String usersString = Tokenizer.create().tokenize(listOfUsers);
        session.sendMessage(usersString);
    }
}
