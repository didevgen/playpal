package commands;

/**
 *Base interface for commands. (Action pattern)
 */
public interface Command {
    /**
     * Base method of the pattern
     * @param arg may be various for different targets. In most cases it can be String type
     */
    void execute(Object arg);
}
