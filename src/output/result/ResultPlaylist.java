package output.result;

import input.commands.CommandIn;
import playlist.commands.PlaylistEssentials;

import java.util.ArrayList;

public class ResultPlaylist extends ResultCommand {
    private ArrayList<PlaylistEssentials> result;

    public ResultPlaylist(CommandIn command) {
        super(command);
        result = new ArrayList<>();
    }

    public ArrayList<PlaylistEssentials> getResult() { return result; }

}
