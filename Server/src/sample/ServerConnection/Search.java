package sample.ServerConnection;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Search {

    public static boolean searchForPermission(File csv, String userName, String fileName){
        InputStream is = null;
        try{
            is = new FileInputStream(csv);
            Scanner scanner = new Scanner(is);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.toUpperCase().contains(fileName.toUpperCase())&&
                        line.toUpperCase().contains(userName.toUpperCase())){
                    is.close();
                    return true;
                }
            }
            is.close();
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    private static File getFile(File dir, String fileName){
        File[] files = dir.listFiles();
        for(File file: files){
            if(file.getName() == fileName){
                return file;
            }
        }
        return null;
    }

    public static File getFileWithCheckPermission(String pathToServerDir, String userName, String fileName){
        File dirServer = new File(pathToServerDir);
        File[] dirsOfServers = dirServer.listFiles();
        File fileToSend = null;
        for(File dir: dirsOfServers){
            if(!dir.isFile()&&Search.searchForPermission(Search.getFile(dir,"info.csv"),userName,fileName)){
                fileToSend = Search.getFile(dir,fileName);
            }
        }
        return fileToSend;
    }

    public static List<File> getListOfFilesWithPermissionCheck(String pathToServeDir, String userName){
        File dirServer = new File(pathToServeDir);
        File[] dirs = dirServer.listFiles();
        List<File> listOfcsv = Arrays.stream(dirs)
                .map(a-> new File(Paths.get(a.getAbsolutePath() + "/info.csv").toString()))
                .collect(Collectors.toList());
        ArrayList<File> fileList = new ArrayList<>();
        for(File file: listOfcsv){
            try(InputStream is = new FileInputStream(file)){
                Scanner scanner = new Scanner(is);
                while(scanner.hasNextLine()){
                    String line = scanner.nextLine().toUpperCase();
                    if(line.contains(userName)){
                        String[] params = line.split(";");
                        File tmp = new File(params[0]);
                        fileList.add(tmp);
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
                System.exit(1);
            }
        }
        return fileList;
    }

    public static File getCvsWithFile(String pathToserverDir, String userName, String fileName){
        File[] dirList = new File(pathToserverDir).listFiles();
        for(File dir: dirList){
                File csv =  new File(Paths.get(dir.getAbsolutePath()+"/info.csv").toString());
                return csv;
        }
        return null;
    }
}
