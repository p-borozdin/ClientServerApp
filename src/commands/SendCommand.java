package commands;

import repository.TalkersRepository;
import talker_thread.TalkerThread;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SendCommand implements Command {
    private final Pattern PATTERN = Pattern.compile("^/send +(?<talker>\\w+) +(?<message>.*)");
    private final TalkersRepository talkersRepository;
    private final TalkerThread sender;

    public SendCommand(TalkersRepository repository, TalkerThread sender) {
        this.talkersRepository = repository;
        this.sender = sender;
    }

    @Override
    public boolean isApplicable(String inputLine) {
        return inputLine.startsWith("/send");
    }

    @Override
    public void execute(String inputLine) {
        Matcher matcher = PATTERN.matcher(inputLine);
        if (matcher.find()) {
            String talkerName = matcher.group("talker");
            String message = matcher.group("message");
            if (talkersRepository.hasTalker(talkerName))
                talkersRepository.getTalker(talkerName).send(message);
            else
                sender.send("No such user: " + talkerName);
        }
    }
}
