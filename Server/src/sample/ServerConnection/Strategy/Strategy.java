package sample.ServerConnection.Strategy;

import sample.ServerConnection.Session;

import java.io.IOException;

public interface Strategy {
    void reply(Session session, String pathToServerDir, String... parameters) throws IOException;
}
