package commands;

public interface Command {
    boolean isApplicable(String inputLine);

    void execute(String inputLine);
}
