package main;

import fileio.input.LibraryInput;
import input.commands.CommandIn;
import output.result.*;
import player.commands.*;
import playlist.commands.AddRemove;
import playlist.commands.Playlist;
import search.bar.*;

import java.util.ArrayList;

public class AnalyzeCommands {
    public ArrayList<ResultCommand> anaylze_func(LibraryInput library, ArrayList<CommandIn> commands) {
        ArrayList<ResultCommand> result = new ArrayList<>();             /* Output-ul pentru fiecare comanda este stocat aici */
        ArrayList<Select> selected_list = new ArrayList<>();                   /* Tine cont de selectiile user-ilor la un moment dat */
        ArrayList<UserInfo> users = new ArrayList<>();                          /* Lista cu users curenti ai programului */
        ArrayList<Like> topLikedSongs = new ArrayList<>();                  /* Lista cu toate melodiile apreciate (din biblioteca) la un moment dat */

        /* Itereaza prin lista de comenzi si verifica ce tip este aceasta */
        for (CommandIn command: commands) {
            /* Cautam in lista "users" user curent  */
            UserInfo currentUser = null;
            for (UserInfo iter: users) {
                if (iter.getUsername().equals(command.getUsername())) {
                    currentUser = iter;
                    break;
                }
            }
            /* Se intampla ca user curent sa nu existe, deci trebuie adaugat in lista de users */
            if (currentUser == null) {
                currentUser = new UserInfo(command.getUsername());
                users.add(currentUser);
            }

            if (command.getCommand().contains("search")) {
                /* Player-ul pentru user care da "search" trebuie sa dispara !! */
                currentUser.setPlayer(null);

                /* Liniile de mai jos sunt valide deoarece clasa "ResultOutSearch" este o subclasa a clasei "ResultCommand" */
                ResultOutSearch temporary = Search.search_func(library, command, currentUser);
                result.add(temporary);

                /* Ne asiguram ca nu mai exista rezultatul search-ului anterior */
                for (Select iter : selected_list) {
                    if (iter.getUser().contains(command.getUsername())) {
                        selected_list.remove(iter);
                        break;
                    }
                }

                /* Adaug si faptul ca s-a efectuat search */
                Select selected = new Select();
                selected.setSearch_done(true);
                selected.setUser(command.getUsername());
                selected.setSearch_result(temporary.getResults());
                /* Linia de mai sus salveaza rezultatul efectiv al cautarii (nume melodii / podcast) */
                selected.setResult_type(command);

                selected_list.add(selected);
            } else if (command.getCommand().contains("select")) {
                /* Caut nodul din lista "selected_list" corespunzator user-ului ce a dat comanda */
                Select selected = null;
                for (Select iter : selected_list) {
                    if (iter.getUser().contains(command.getUsername())) {
                        selected = iter;
                        break;
                    }
                }

                if (selected != null) {
                    /* Chem functia "select" pt user curent si efectuam selectarea */
                    result.add(selected.select_func(command, library, currentUser));

                    /* Verificam daca urmatoarea comanda este "load" */
                    String following_command = command.getNextCommand(commands);
                    if (following_command != null && following_command.contains("load")) {
                        /* In caz afirmativ, adaugam noul player cu load-ul pt select-ul curent user-ului */
//                        Load tempLoad = new Load(selected);
                        currentUser.setPlayer(new Player(new Load(selected), command.getTimestamp()));

                        /* Eliminam optiunea "select", intrucat nu mai este necesara din acest moment. */
                        selected_list.remove(selected);
                    }
                } else {
                    ResultOut out = new ResultOut(command);
                    out.setMessage("Please conduct a search before making a selection.");
                    result.add(out);
                }
            } else if (command.getCommand().contains("load")) {
                ResultOut resultLoad = new ResultOut(command);
                boolean succes = false;         /* <-- Arata daca s-a dat "load" imediat dupa "search" */

                if (currentUser.getUsername().equals(command.getUsername())) {
                    /* Daca player ruleaza, nu se efectueaza comanda  "load" */
                    if (currentUser.getPlayer() == null || currentUser.getPlayer().getLoadInfo().getLoaded()) {
                        break;
                    }
                    /* Chem functie load */
                    currentUser.getPlayer().getLoadInfo().load_func(command, resultLoad,currentUser);
                    currentUser.getPlayer().setLastLoadTime(command.getTimestamp());
                    succes = true;
                }

                if (!succes) {
                    resultLoad.setMessage("Please select a source before attempting to load.");
                }

                result.add(resultLoad);
            } else if (command.getCommand().contains("status")) {
                /* Caz in care se apeleaza comanda pt player gol (utilizatorul nu ruleaza nimic) */
                if (currentUser.getPlayer() == null) {
                    ResultStatus out = new ResultStatus(command);
                    out.setStats(new Stats());
                    result.add(out);
                } else {
                    result.add(currentUser.getPlayer().statusFunc(command));
                }
            } else if (command.getCommand().contains("playPause")) {
                Player player = currentUser.getPlayer();
                if (player == null) {
                    ResultOut out = new ResultOut(command);
                    out.setMessage("Please load a source before attempting to pause or resume playback.");
                    result.add(out);
                } else {
                    result.add((PlayPause.playPauseFunc(player, command)));
                }
            } else if (command.getCommand().contains("createPlaylist")) {
                ResultOut out = new ResultOut(command);
                boolean isCreated = false;              // <-- Pentru verificare existenta a playlist-ului cu numele in command

                for (Playlist iter: currentUser.getPlaylists()) {
                    if (iter.getDetails().getName().equals(command.getPlaylistName())) {  // <--- Iteram prin lista de playlist-uri si verificam prin nume
                        out.setMessage("A playlist with the same name already exists.");
                        isCreated = true;
                    }
                }

                if (!isCreated) {
                    currentUser.getPlaylists().add(new Playlist(command));     // <-- Cream efectiv playlist-ul
                    out.setMessage("Playlist created successfully.");
                }

                result.add(out);
            } else if (command.getCommand().contains("addRemoveInPlaylist")) {
                result.add(AddRemove.addRemoveInPlaylist(currentUser, command, library.getSongs()));
            } else if (command.getCommand().equals("like")) {
                result.add(Like.likeCommand(topLikedSongs, currentUser, command));
            } else if (command.getCommand().equals("showPreferredSongs")) {
                result.add(new ResultPreferedSongs(command, currentUser.getLikedSongs()));
            } else if (command.getCommand().equals("showPlaylists")) {
                result.add(new ResultShowPlaylists(command, currentUser));
            }

            /* Actualizez pentru orice player (care ruleaza) timpul relativ la penultima comanda data pentru calcularea remainTime al piesei incarcate */
            for (UserInfo updateTimer: users) {
                Player tempPlayer = updateTimer.getPlayer();
                if (tempPlayer != null) {
                    tempPlayer.updateRemainedTime(command);
                }
            }
        }
        return result;
    }
}
