package sample.ServerConnection.Strategy;

import sample.ServerConnection.Search;
import sample.ServerConnection.Session;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DownStrategy  implements Strategy{

    @Override
    public void reply(Session session, String parthToServerDir, String... parameters) throws IOException {
       File fileToSend = Search.getFileWithCheckPermission(parthToServerDir,parameters[0],parameters[1]);
       session.sendFile(fileToSend);
    }
}
