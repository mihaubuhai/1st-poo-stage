package player.commands;

import input.commands.CommandIn;
import main.UserInfo;
import output.result.ResultOut;

import java.util.ArrayList;

/**
 *      Aceasta clasa va fi folosita pentru definirea listei "topLikedSongs"
 *      Fiecare clasa contine numele unei melodii si numarul de user care o apreciaza
 *      La comanda "GetTop5Songs", lista de mai sus va fi sortata in functie de campul "users"
 * */

public class Like {
    private String songName;            /* Numele melodiei */
    private int users;                          /* Numarul de user care apreciaza melodia */

    public Like(String song) {
        setSongName(song);
        incrementNoUsers();
    }

    public void setSongName(String song) { songName = song; }

    public String getSongName() { return songName; }

    public void incrementNoUsers() { users += 1; }

    public void decrementNoUsers() {
        if (users > 0) {            /* Pentru evitarea stocarii unor valori negative */
            users -= 1;
        }
    }

    public int getUsers() { return users; }

    public static ResultOut likeCommand(ArrayList<Like> topLikedSongs, UserInfo currentUser, CommandIn command) {
        ResultOut result = new ResultOut(command);

        /* Verificam daca player ruleaza */
        if (currentUser.getPlayer() == null || !currentUser.getPlayer().getLoadInfo().getLoaded()) {
            result.setMessage("Please load a source before liking or unliking.");
        } else if (currentUser.getPlayer().getLoadInfo().getSelectInfo().getResult_type() == 2) {
            /* Verificam daca player ruleaza un podcast */
            result.setMessage("Loaded source is not a song.");
        } else {
            String currentSong = currentUser.getPlayer().getStats().getName();
            boolean deletedSong = false;

            /* Verificam daca in lista de aprecieri ale user-ului se gaseste melodia care ruleaza */
            for (String song: currentUser.getLikedSongs()) {
                if (song.equals(currentSong)) {
                    /* Daca se gaseste, o vom elimina si marcam aceasta eliminare */
                    currentUser.getLikedSongs().remove(song);
                    /* Iteram prin lista "topLikedSongs" si cautam melodia din player */
                    for (Like iter: topLikedSongs) {
                        /* Daca se gaseste aceasta melodie in lista de topLikedSongs, vom decrementa numarul de users care o apreciaza */
                        if (iter.getSongName().equals(currentSong)) {
                            iter.decrementNoUsers();
                            deletedSong = true;
                            break;
                        }
                    }

                    /* Se intampla ca melodia sa nu existe in lista de topLikedSongs */
                    if (!deletedSong) {
                        topLikedSongs.add(new Like(currentSong));       // <-- o adaugam in lista
                    }
                    deletedSong = true;
                    break;
                }
            }

            if (!deletedSong) {
                /* Daca in urma for-ului de mai sus, deletedSong nu se schimba, inseamna ca melodia nu a fost apreciata */
                currentUser.getLikedSongs().add(currentSong);
                result.setMessage("Like registered successfully.");
            } else {
                result.setMessage("Unlike registered successfully.");
            }
        }
        return result;
    }
}
