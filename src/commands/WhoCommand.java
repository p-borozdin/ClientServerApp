package commands;

import repository.TalkersRepository;
import talker_thread.TalkerThread;

public class WhoCommand implements Command {
    private static final String COMMAND = "/who";
    private final TalkersRepository talkersRepository;
    private final TalkerThread sender;

    public WhoCommand(TalkersRepository repository, TalkerThread sender) {
        talkersRepository = repository;
        this.sender = sender;
    }

    @Override
    public boolean isApplicable(String inputLine) {
        return inputLine.equals(COMMAND);
    }

    @Override
    public void execute(String inputLine) {
        sender.send("The list of all talkers:");
        for (var name : talkersRepository.allTalkers())
            sender.send("\t" + name);
    }
}
