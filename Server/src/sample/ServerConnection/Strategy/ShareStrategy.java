package sample.ServerConnection.Strategy;

import sample.ServerConnection.Search;
import sample.ServerConnection.Session;

import java.io.File;
import java.io.IOException;

public class ShareStrategy implements Strategy {


    @Override
    public void reply(Session session, String pathToServerDir, String... parameters) throws IOException {
       File csv = Search.getCvsWithFile(pathToServerDir,parameters[0],parameters[1]);
    }
}
