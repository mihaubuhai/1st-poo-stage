package playlist.commands;

import fileio.input.LibraryInput;
import fileio.input.SongInput;
import input.commands.CommandIn;
import main.UserInfo;
import output.result.ResultOut;
import player.commands.Load;
import player.commands.Player;

import java.util.ArrayList;

public class AddRemove {
    public static ResultOut addRemoveInPlaylist(UserInfo user, CommandIn command, ArrayList<SongInput> librarySongs) {
        ResultOut result = new ResultOut(command);

        /* Verificam daca player-ul ruleaza */
        if (user.getPlayer() == null) {
            result.setMessage("Please load a source before adding to or removing from the playlist.");
            return result;
        }
        /* Verificam daca player-ul ruleaza o melodie (prin resultType al selectInfo ne dam seama) */
        if (user.getPlayer().getLoadInfo().getSelectInfo().getResult_type() != 1) {
            result.setMessage("The loaded source is not a song.");
            return result;
        }
        /* Verificam daca prin comanda s-a dat un playlistId invalid */
        if (user.getPlaylists().size() < command.getPlaylistId() - 1) {
            result.setMessage("The specified playlist does not exist.");
            return result;
        }

        Playlist playlist = user.getPlaylists().get(command.getPlaylistId() - 1);
        String currentSong = user.getPlayer().getLoadInfo().getSelectInfo().getSong().getName();
        /* Iteram prin lista de melodii ale playlist-ului si cautam melodia care este incarcata in player */
        for (SongInput song: playlist.getDetails().getSongs()) {
            if (song.getName().contains(currentSong)) {
                playlist.getDetails().getSongs().remove(song);
                result.setMessage("Successfully removed from playlist.");
                return result;
            }
        }

        /* Ajunsi aici inseamna ca melodia nu se gaseste in playlist, asa ca trebuie adaugata */
        for (SongInput song: librarySongs) {
            if (song.getName().contains(currentSong)) {
                playlist.getDetails().addSong(song);
                break;
            }
        }
        result.setMessage("Successfully added to playlist.");
        return result;
    }
}
