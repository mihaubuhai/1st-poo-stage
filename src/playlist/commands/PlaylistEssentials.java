package playlist.commands;

import fileio.input.SongInput;

import java.util.ArrayList;

/**
 * Clasa folosita pentru afisarea output-ului comenzii "showPlaylist",
 *  dar si pe parcursul programului este des utilizata.
 * */
public class PlaylistEssentials {
    private String name;
    private ArrayList<SongInput> songs;
    private String visibility;
    private int followers;

    public PlaylistEssentials() {
        songs = new ArrayList<>();
        setVisibility("public");
    }

    /** Setter */
    public void setName(final String name) {
        this.name = name;
    }

    /** Getter */
    public String getName() {
        return name;
    }

    /** Setter */
    public void addSong(final SongInput song) {
        songs.add(song);
    }

    /** Getter */
    public ArrayList<SongInput> getSongs() {
        return songs;
    }

    /** Setter */
    public void setVisibility(final String visibility) {
        this.visibility = visibility;
    }

    /** Getter */
    public String getVisibility() {
        return visibility;
    }

    /** Metoda ce incrementeaza numarul de "followers" */
    public void incFollower() {
        followers += 1;
    }

    /** Metoda ce decremenetaza numarul de "followers" */
    public void decFollowers() {
        if (followers > 0) {
            followers -= 1;
        }
    }

    /** Getter */
    public int getFollowers() {
        return followers;
    }
}
