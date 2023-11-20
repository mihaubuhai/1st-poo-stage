package input.commands;

import java.util.ArrayList;

public class CommandIn {
    private String command;
    private String username;
    private int timestamp;
    private String type;
    private Filters filters;
    private int itemNumber;
    private int seed;
    private int playlistId;
    private String playlistName;

    public void setCommand(String command) {
        this.command = command;
    }
    public String getCommand() {
        return command;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
    public int getTimestamp() {
        return timestamp;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public void setFilters(Filters filters) {
        this.filters = filters;
    }
    public Filters getFilters() {
        return filters;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }
    public int getItemNumber() {
        return itemNumber;
    }

    public void setSeed(int seed) { this.seed = seed; }
    public int getSeed() { return seed; }

    public void setPlaylistId(int playlistId) { this.playlistId = playlistId; }
    public int getPlaylistId() { return playlistId; }

    public void setPlaylistName(String playlistName) { this.playlistName = playlistName; }
    public String getPlaylistName() { return playlistName; }

    public String getNextCommand(ArrayList<CommandIn> commands) {
        int current_idx = commands.indexOf(this);
        if (current_idx == commands.size() - 1) { return null; }
        return commands.get(current_idx + 1).getCommand();
    }

}
