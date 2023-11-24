package player.commands;

import input.commands.CommandIn;
import main.UserInfo;
import output.result.ResultOut;
import playlist.commands.Playlist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *      Scopul clasei este de a executa comanda "shuffle"
 * */
public final class Shuffle {
    private static Shuffle instance = null;

    private Shuffle() {

    }

    /** Metoda pentru intoarcerea unei instante a acestei clase */
    public static Shuffle getInstance() {
        if (instance == null) {
            instance = new Shuffle();
        }
        return instance;
    }

    /** Metoda creeaza vectorul de indici amestecati */
    private ArrayList<Integer> createShuffleArray(final int playlistSize, final int seed) {
        ArrayList<Integer> result = new ArrayList<>();

        /* Cream vectorul de indici */
        for (int i = 0; i < playlistSize; ++i) {
            result.add(i);
        }

        /* Amestecam valorile vectorului de indici */
        Collections.shuffle(result, new Random(seed));

        return result;
    }

    /** Aceasta metoda executa comanda "shuffle" */
    public ResultOut shuffleFunc(final CommandIn command, final UserInfo user) {
        ResultOut result = new ResultOut(command);
        Player currentPlayer = user.getPlayer();
        final int playlistID = 3;

        /* Verifica daca player este gol */
        if (currentPlayer == null || currentPlayer.getLoadInfo() == null) {
            result.setMessage("Please load a source before using the shuffle function.");
        } else if (currentPlayer.getLoadInfo().getSelectInfo().getResultType() != playlistID) {
            /* Se verifica daca player ruleaza altceva decat un playlist */
            result.setMessage("The loaded source is not a playlist.");
        } else {
            /* Player-ul ruleaza un playlist; verificam daca exista shuffle activ */
            Playlist playlist = currentPlayer.getLoadInfo().getSelectInfo().getPlaylist();

            if (currentPlayer.getStats().getShuffle()) {
                /* Se dezactiveaza shuffle */
                user.updateRemainedTime(command);
                if (user.getPlayer().getLoadInfo() == null) {
                    result.setMessage("Please load a source before using the shuffle function.");
                    return result;
                }
                currentPlayer.getStats().setShuffle(false);
                playlist.setShuffleIndices(null);
                result.setMessage("Shuffle function deactivated successfully.");
            } else {
                /* Se activeaza functia "shuffle" si cream vectorul de indici amestecati */
                int playlistSize = playlist.getDetails().getSongs().size();
                playlist.setShuffleIndices(createShuffleArray(playlistSize, command.getSeed()));
                currentPlayer.getStats().setShuffle(true);
                currentPlayer.getLoadInfo().getSelectInfo().getPlaylist().findIdxSong(user);
                result.setMessage("Shuffle function activated successfully.");
            }
        }

        return result;
    }


}
