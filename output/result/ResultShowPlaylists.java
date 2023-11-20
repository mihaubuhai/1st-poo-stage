package output.result;

import fileio.input.SongInput;
import input.commands.CommandIn;
import main.UserInfo;
import playlist.commands.PlaylistEssentials;

import java.util.ArrayList;

/**
 *      Intrucat clasa folosita in program "PlaylistEssentials" foloseste ...
 *      ... ArrayList<Songs> in loc de ArrayList<String> pentru retinerea melodiilor din playlist,
 *      Clasa de dedesubt este necesara pentru executarea comenzii de afisare a playlist-urilor
 * */

class PlaylistOutput {
    private String name;
    private ArrayList<String> songs;
    private String visibility;
    private int followers;

    PlaylistOutput(PlaylistEssentials playlist) {
        name = playlist.getName();
        visibility = playlist.getVisibility();
        followers = playlist.getFollowers();
        songs = new ArrayList<>();
        playlist.getSongs().forEach(song -> songs.add(song.getName()));
    }

    public String getName() { return name; }

    public ArrayList<String> getSongs() { return songs; }

    public String getVisibility() { return visibility; }

    public int getFollowers() { return followers; }
}

/**
 *      Aceasta clasa este folosita pentru afisarea output-ului comenzii "showPlaylists"
 * */

public class ResultShowPlaylists extends  ResultCommand {
    private ArrayList<PlaylistOutput> result;

    public ResultShowPlaylists(CommandIn command, UserInfo user) {
        super(command);
        result = new ArrayList<>();
        user.getPlaylists().forEach(playlist -> result.add(new PlaylistOutput(playlist.getDetails())));
    }

    public ArrayList<PlaylistOutput> getResult() { return result; }

}
