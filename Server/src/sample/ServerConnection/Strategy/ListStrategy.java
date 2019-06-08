package sample.ServerConnection.Strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.ServerConnection.Search;
import sample.ServerConnection.Session;
import sample.ServerConnection.Tokenizer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ListStrategy implements Strategy{

    Logger log = LoggerFactory.getLogger(getClass().getName());
    @Override
    public void reply(Session session, String parthToServerDir, String... parameters) throws IOException {
        log.info("strategia list");
        List<File> fileList = Search.getListOfFilesWithPermissionCheck(parthToServerDir,parameters[1]);
        log.info("użyto getListOfFilesWithPermissionCheck");
        List<String> listOfNames = fileList.stream()
                .map(a -> a.getName())
                .collect(Collectors.toList());
        log.info("utworzono listOfNames");
        String message = Tokenizer.create().tokenize(listOfNames);
        log.info("użyto tokenizer");
        session.sendMessage(message);
        log.info("użyto session,sendMessage");
    }
}
