package player.commands;

import fileio.input.SongInput;
import input.commands.CommandIn;
import main.UserInfo;
import output.result.ResultOut;
import search.bar.Select;
import main.AnalyzeCommands;

import java.util.ArrayList;

public class Load {
    private boolean loaded;
    private Select selectInfo;

    public Load(Select selectInfo) {
        this.selectInfo = new Select(selectInfo);
        this.loaded = false;
    }

    public void setLoaded(boolean loaded) { this.loaded = loaded; }

    public boolean getLoaded() { return loaded; }

    public Select getSelectInfo() { return selectInfo; }

    public void load_func(CommandIn command, ResultOut result, UserInfo currentUser) {
        /* In cazul in care ceea ce este selectat este un playlist */
        if (getSelectInfo().getResult_type() == 3) {
            /* Verificam daca playlist este gol */
            ArrayList<SongInput> playlistSongs = getSelectInfo().getPlaylist().getDetails().getSongs();
            if (playlistSongs.isEmpty()) {
                /* Selectia curenta este invalida, trebuie eliberat player-ul */
                currentUser.setPlayer(null);
                result.setMessage("You can't load an empty audio collection!");
                return;
            }
        }

        setLoaded(true);
        result.setMessage("Playback loaded successfully.");
    }

}
