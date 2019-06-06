package sample.ServerConnection.Strategy;

import sample.ServerConnection.Search;
import sample.ServerConnection.Session;
import sample.ServerConnection.Tokenizer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ListStrategy implements Strategy{
    @Override
    public void reply(Session session, String parthToServerDir, String... parameters) throws IOException {
        List<File> fileList = Search.getListOfFilesWithPermissionCheck(parthToServerDir,parameters[0]);
        List<String> listOfNames = fileList.stream()
                .map(a -> a.getName())
                .collect(Collectors.toList());
        String message = Tokenizer.create().tokenize(listOfNames);
        session.sendMessage(message);
    }
}
