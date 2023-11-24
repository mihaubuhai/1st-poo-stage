package output.result;

import playlist.commands.PlaylistEssentials;

import java.util.ArrayList;

/**
 *      Intrucat clasa folosita in program "PlaylistEssentials" foloseste ...
 *      ... ArrayList<Songs> in loc de ArrayList<String> pentru retinerea melodiilor din playlist,
 *      Clasa de dedesubt este necesara pentru executarea comenzii de afisare a playlist-urilor
 * */
public class PlaylistOutput {
    private String name;
    private ArrayList<String> songs;
    private String visibility;
    private int followers;

    public PlaylistOutput(final PlaylistEssentials playlist) {
        name = playlist.getName();
        visibility = playlist.getVisibility();
        followers = playlist.getFollowers();
        songs = new ArrayList<>();
        playlist.getSongs().forEach(song -> songs.add(song.getName()));
    }

    /** Getter */
    public String getName() {
        return name;
    }

    /** Getter */
    public ArrayList<String> getSongs() {
        return songs;
    }

    /** Getter */
    public String getVisibility() {
        return visibility;
    }

    /** Getter */
    public int getFollowers() {
        return followers;
    }
}
