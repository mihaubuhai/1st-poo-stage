package player.commands;

import fileio.input.SongInput;
import main.UserInfo;
import output.result.ResultOut;
import playlist.commands.Playlist;
import search.bar.Select;

import java.util.ArrayList;

public class Load {
    private boolean loaded;
    private Select selectInfo;

    public Load(final Select selectInfo) {
        this.selectInfo = new Select(selectInfo);
        this.loaded = false;
    }

    /** Setter */
    public void setLoaded(final boolean loaded) {
        this.loaded = loaded;
    }

    /** Getter */
    public boolean getLoaded() {
        return loaded;
    }

    /** Getter */
    public Select getSelectInfo() {
        return selectInfo;
    }


    /** Aceasta metoda implementeaza comanda "load" */
    public void loadFunc(final ResultOut result,
                                        final UserInfo currentUser,
                                        final ArrayList<Player> oldPodcastPlayers) {

        final int playlistID = 3;
        final int podcastId = 2;
        /* Verificam daca ceea ce s-a selectat este un podcast din cele salvate */
        Select currentSelectInfo = currentUser.getPlayer().getLoadInfo().getSelectInfo();
        if (currentSelectInfo.getResultType() == podcastId) {
            for (Player oldPodcastPlayer: oldPodcastPlayers) {
                String currentPodcastName = currentSelectInfo.getPodcast().getName();
                Select storedSelectInfo = oldPodcastPlayer.getLoadInfo().getSelectInfo();
                String storedPodcastName = storedSelectInfo.getPodcast().getName();
                if (currentPodcastName.equals(storedPodcastName)) {
                    /*
                        S-a selectat un podcast care a rulat la un moment dat,
                        deci va trebui sa ruleze de unde a fost stopat
                    */
                    currentUser.setPlayer(oldPodcastPlayer);
                    result.setMessage("Playback loaded successfully.");
                    return;
                }
            }
            /* Se poate intampla ca podcast-ul selectat sa nu fi fost incarcat inainte */
            currentUser.getPlayer().setStats(this);
        }

        Playlist playlist = getSelectInfo().getPlaylist();
        /* In cazul in care ceea ce este selectat este un playlist */
        if (getSelectInfo().getResultType() == playlistID) {
            /* Verificam daca playlist este gol */
            ArrayList<SongInput> playlistSongs = playlist.getDetails().getSongs();
            if (playlistSongs.isEmpty()) {
                /* Selectia curenta este invalida, trebuie eliberat player-ul */
                currentUser.setPlayer(null);
                result.setMessage("You can't load an empty audio collection!");
                return;
            }
            currentUser.getPlayer().setStats(this);
        }

        setLoaded(true);
        result.setMessage("Playback loaded successfully.");
    }

}
