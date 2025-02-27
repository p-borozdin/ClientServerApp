package talker_thread;

import commands.Command;
import commands.SendCommand;
import commands.WhoCommand;
import repository.TalkersRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class TalkerThread extends Thread {
    private final BufferedReader in;
    private final PrintWriter out;
    private final ArrayList<Command> commands = new ArrayList<>();

    public TalkerThread(Socket clientSocket, TalkersRepository talkersRepository) throws IOException {
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // What client sends
        out = new PrintWriter(clientSocket.getOutputStream(), true); // What client receives

        commands.add(new WhoCommand(talkersRepository, this));
        commands.add(new SendCommand(talkersRepository, this));
    }

    @Override
    public void run() {
        String inputLine;

        try {
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.startsWith("/")) {
                    for (var cmd : commands) {
                        if (cmd.isApplicable(inputLine))
                            cmd.execute(inputLine);
                    }
                }
                else
                    out.println("echo: " + inputLine);
            }
        }
        catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    public String auth() {
        out.print("Enter your name: ");
        out.println();

        try {
            return in.readLine();
        }
        catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    public void send(String message) {
        out.println(message);
    }
}
