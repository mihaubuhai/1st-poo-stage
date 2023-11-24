package playlist.commands;

import fileio.input.SongInput;
import input.commands.CommandIn;
import main.UserInfo;
import output.result.ResultOut;

import java.util.ArrayList;

public class Playlist {
    private PlaylistEssentials details;
    private String userName;
    private int idxSong;
    private ArrayList<Integer> shuffleIndices;
    private int timeCreation;  // <--- camp folosit pentru sortarea topului de playlist-uri

    public Playlist(final CommandIn command) {
        details = new PlaylistEssentials();
        setUser(command.getUsername());
        details.setName(command.getPlaylistName());
        timeCreation = command.getTimestamp();
        idxSong = 1;     // <--- Tine cont de a cata melodie din playlist ruleaza la un moment dat
    }

    /** Getter */
    public int getTimeCreation() {
        return timeCreation;
    }

    /** Getter */
    public PlaylistEssentials getDetails() {
        return details;
    }

    /** Setter */
    public void setUser(final String username) {
        userName = username;
    }

    /** Getter */
    public String getUser() {
        return userName;
    }

    /** Setter */
    public void setIdxSong(final int idx) {
        idxSong = idx;
    }

    /** Getter */
    public int getIdxSong() {
        return idxSong;
    }

    /** Setter */
    public void setShuffleIndices(final ArrayList<Integer> shuffles) {
        shuffleIndices = shuffles;
    }

    /** Getter */
    public ArrayList<Integer> getShuffleIndices() {
        return shuffleIndices;
    }

    /**             ---- Metoda va fi apelata de playlist-ul user-ului curent ----
     *      Metoda de mai jos are rolul de a gasi campul "idxSong" aferent
     *      Acest camp stocheaza indicele urmatoarei melodii care ar trebui sa se ruleze ..
     *      .. relativ la cea care se ruleaza in player
     * */
    public int findIdxSong(final UserInfo user) {
        /* Cautam indicele melodiei care ruleaza */
        int currentIdxSong = 0;
        String currentSongName = user.getPlayer().getStats().getName();
        for (SongInput song: getDetails().getSongs()) {
            if (song.getName().equals(currentSongName)) {
                /* S-a gasit melodia, aflam indicele si iesim din for */
                currentIdxSong = getDetails().getSongs().indexOf(song);
                break;
            }
        }
        /* currentIdxSong contine indicele melodiei din playlist care ruleaza */

        String repeatMode = user.getPlayer().getStats().getRepeat().toLowerCase();
        /* Verificam starea shuffle */
        if (!user.getPlayer().getStats().getShuffle()) {
            /* Verificam daca s-a ajuns la final de playlist */
            Playlist usersPlaylist = user.getPlayer().getLoadInfo().getSelectInfo().getPlaylist();
            if (currentIdxSong == usersPlaylist.getDetails().getSongs().size() - 1) {
                /* S-a ajuns la final de playlist, verificam starea de repeat */
                if (repeatMode.contains("no")) {
                    return -1;
                } else {
                    /* Se disting 2 cazuri: "Repeat All" si "Repeat Current Song" */
                    if (repeatMode.contains("all")) {
                        return 0;
                    }
                    return currentIdxSong;
                }
            }
            /* Melodia urmatoare va fi evident urmatoarea dupa indice */
            return currentIdxSong + 1;
        } else {
            /* Shuffle este activ, vom cauta in vectorul de indici indicele curent */
            int currShuffledIdx = shuffleIndices.indexOf(currentIdxSong);
            /*
                "currShuffledIdx" va contine indicele din "shuffleIndices" ..
                .. aferent melodiei care ruleaza
            */

            if (currShuffledIdx == shuffleIndices.size() - 1) {
                /* Se poate ca "currShuffleIdx" sa fie ultimul indice din  "shuffleIndices" */
                /* S-a ajuns la final de playlist, verificam starea de repeat */
                if (repeatMode.contains("no")) {
                    return -1;
                } else {
                    /* Se disting 2 cazuri: "Repeat All" si "Repeat Current Song" */
                    if (repeatMode.contains("all")) {
                        return shuffleIndices.get(0);
                    }
                    return currentIdxSong;
                }
            }

            return shuffleIndices.get(currShuffledIdx + 1);
        }
    }

    /**
     *      Metoda de mai jos implementeaza comanda "switchVisibility"
     * */
    public static ResultOut switchVisibility(final CommandIn command, final UserInfo currentUser) {
        ResultOut result = new ResultOut(command);
        int noPlaylists = currentUser.getPlaylists().size();

        /* Verificam daca s-a dat in comanda un ID invalid */
        if (command.getPlaylistId() > noPlaylists) {
            result.setMessage("The specified playlist ID is too high.");
        } else {
            Playlist currentPlaylist = currentUser.getPlaylists().get(command.getPlaylistId() - 1);
            if (currentPlaylist.getDetails().getVisibility().equals("public")) {
                currentPlaylist.getDetails().setVisibility("private");
                result.setMessage("Visibility status updated successfully to private.");
            } else {
                currentPlaylist.getDetails().setVisibility("public");
                result.setMessage("Visibility status updated successfully to public.");
            }
        }

        return result;
    }

}
