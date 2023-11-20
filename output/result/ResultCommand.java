package output.result;

import input.commands.CommandIn;

public class ResultCommand {
    private String command;
    private String user;
    private int timestamp;

    public ResultCommand() {}

    public ResultCommand(CommandIn command) {
        setCommand(command.getCommand());
        setUser(command.getUsername());
        setTimestamp(command.getTimestamp());
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
