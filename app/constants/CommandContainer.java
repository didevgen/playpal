package constants;

import commands.Command;
import commands.NotifyAllCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {
    private static Map<String, Command> commands = new HashMap<>();

    static {
        commands.put(Commands.NOTIFY_ALL,new NotifyAllCommand());
    }

    public static Command getCommand(String key) {
        Command command = commands.get(key);
        if (command == null) {
            return new Command() {
                @Override
                public void execute(Object args) {
                }
            };
        }
        return command;
    }
}
