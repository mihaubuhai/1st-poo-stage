package playlist.commands;

import input.commands.CommandIn;

public class Playlist {
    private PlaylistEssentials details;
    private String user;

    public Playlist(CommandIn command) {
        details = new PlaylistEssentials();
        setUser(command.getUsername());
        details.setName(command.getPlaylistName());
    }

    public PlaylistEssentials getDetails() { return details; }

    public void setUser(String username) { user = username; }

    public String getUser() { return user; }

}
