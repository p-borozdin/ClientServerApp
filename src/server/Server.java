package server;

import repository.TalkersRepository;
import talker_thread.TalkerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int PORT = 9000;
    private final ServerSocket socket = new ServerSocket(PORT);
    private final TalkersRepository talkersRepository = new TalkersRepository();
    private final Listener listener = new Listener();

    public Server() throws IOException {
        System.out.println("Listening on port " + PORT);
    }

    public void start() {
        listener.start();
    }

    private class Listener extends Thread {
        
        @Override
        public void run() {
            while (true) {
                try {
                    Socket clientSocket = socket.accept();

                    var talkerThread = new TalkerThread(clientSocket, talkersRepository);
                    String name;
                    while (talkersRepository.hasTalker((name = talkerThread.auth())))
                        talkerThread.send("User with this name is already existing");

                    if (name == null)
                        continue;

                    talkersRepository.addTalker(name, talkerThread);
                    System.out.println("Talker \"" + name + "\" added");

                } catch (IOException exc) {
                    throw new RuntimeException(exc);
                }
            }
        }
    }
}
