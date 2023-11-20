package playlist.commands;

import fileio.input.SongInput;

import java.util.ArrayList;

public class PlaylistEssentials {
    private String name;
    private ArrayList<SongInput> songs;
    private String visibility;
    private int followers;

    public PlaylistEssentials() {
        songs = new ArrayList<>();
        setVisibility("public");
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public void addSong(SongInput song) { songs.add(song); }

    public ArrayList<SongInput> getSongs() { return songs; }

    public void setVisibility(String visibility) { this.visibility = visibility; }

    public String getVisibility() { return visibility; }

    public void addFollower() { followers += 1; }

    public int getFollowers() { return followers; }
}
