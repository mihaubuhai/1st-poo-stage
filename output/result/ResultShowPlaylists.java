package output.result;

import input.commands.CommandIn;
import main.UserInfo;

import java.util.ArrayList;

/**
 *      Aceasta clasa este folosita pentru afisarea output-ului comenzii "showPlaylists"
 * */
public class ResultShowPlaylists extends  ResultCommand {
    private ArrayList<PlaylistOutput> result;

    public ResultShowPlaylists(final CommandIn command, final UserInfo user) {
        super(command);
        result = new ArrayList<>();
        user.getPlaylists().forEach(iter -> result.add(new PlaylistOutput(iter.getDetails())));
    }

    /** Getter */
    public ArrayList<PlaylistOutput> getResult() {
        return result;
    }

}
