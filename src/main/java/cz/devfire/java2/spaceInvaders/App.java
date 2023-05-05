package cz.devfire.java2.spaceInvaders;

import cz.devfire.java2.spaceInvaders.client.Client;
import cz.devfire.java2.spaceInvaders.client.object.other.Utils;
import cz.devfire.java2.spaceInvaders.server.Server;

public class App {

    private Thread server;
    private Client client;

    public static void main(String[] args) {
        App app = new App();
        app.start(args);
    }

    public void start(String[] args) {
        client = new Client();

        server = new Thread(() -> {
            Server.launch(args);
        });

        try {
            server.start();
            server.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            client.start(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void exit() {
        server.interrupt();
        System.exit(0);
    }

}
