package repository;

import talker_thread.TalkerThread;

import java.util.HashMap;
import java.util.Set;

public class TalkersRepository {
    private final HashMap<String, TalkerThread> talkers = new HashMap<>();

    public void addTalker(String name, TalkerThread talkerThread) {
        talkers.put(name, talkerThread);

        talkerThread.setDaemon(true);
        talkerThread.start();
    }

    public boolean hasTalker(String name) {
        return talkers.containsKey(name);
    }

    public Set<String> allTalkers() {
        return talkers.keySet();
    }

    public TalkerThread getTalker(String name) {
        return talkers.get(name);
    }

}
