package sample.ServerConnection.Strategy;

import sample.ServerConnection.Search;
import sample.ServerConnection.Session;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.io.File;
import java.io.IOException;

public class DownStrategy  implements Strategy{
    Logger log = LoggerFactory.getLogger(getClass().getName());
    @Override
    public void reply(Session session, String parthToServerDir, String... parameters) throws IOException {
        log.info("w strategi");
       File fileToSend = Search.getFile(parameters[2]);
        log.info("użyto getFileWithCheckPermission");
       session.sendFile(fileToSend);
        log.info("użyto session.sendFile");
    }
}
