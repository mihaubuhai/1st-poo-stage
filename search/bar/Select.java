package search.bar;

import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import input.commands.CommandIn;
import main.UserInfo;
import output.result.ResultOut;
import playlist.commands.Playlist;

import java.util.ArrayList;

public class Select {
    private boolean search_done;                // <-- Daca s-a efectuat search anterior
    private boolean selected;                       // <-- Daca s-a selectat ceva
    private String user;
    private SongInput song;
    private PodcastInput podcast;
    private Playlist playlist;
    private ArrayList<String> search_result;
    private int result_type;                        // <-- Contorizeaza ce anume este incarcat in player

    public Select() {}

    public Select(Select otherSelect) {
        result_type = otherSelect.getResult_type();
        setSearch_done(otherSelect.getSearch_done());
        setSelected(otherSelect.getSelected());
        setUser(otherSelect.getUser());
        setSong(otherSelect.getSong());
        setPodcast(otherSelect.getPodcast());
        setPlaylist(otherSelect.getPlaylist());
        setSearch_result(otherSelect.getSearch_result());
    }

    public void setResult_type(CommandIn command) {
        /* 1 - song     2 - podcast     3 - playlist */
        if (command.getType().contains("song")) {
            result_type = 1;
        } else if (command.getType().contains("podcast")) {
            result_type = 2;
        } else {
            result_type = 3;
        }
    }

    public int getResult_type() { return result_type; }

    public void setSearch_done(boolean search_done) {
        this.search_done = search_done;
    }

    public boolean getSearch_done() {
        return search_done;
    }

    public void setSelected(boolean status) {
        this.selected = status;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSong(SongInput song) {
        this.song = song;
    }

    public SongInput getSong() {
        return song;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setPodcast(PodcastInput podcast) {
        this.podcast = podcast;
    }

    public PodcastInput getPodcast() {
        return podcast;
    }

    public void setSearch_result(ArrayList<String> result) {
        search_result = result;
    }

    public ArrayList<String> getSearch_result() {
        return search_result;
    }

    public void setPlaylist(Playlist playlist) { this.playlist = playlist; }

    public Playlist getPlaylist() { return playlist; }

    /**
     *    Aceasta metoda implementeaza comanda "select"
     *    Aceasta returneaza o clasa pe tiparul output-ului comenzii.
     * */
    public ResultOut select_func(CommandIn command, LibraryInput library, UserInfo user) {
        /* Declarare + initializare valoare de retur a metodei */
        ResultOut result = new ResultOut(command);

        if (command.getItemNumber() > getSearch_result().size()) {
            result.setMessage("The selected ID is too high.");
        } else if (!getSearch_done() || getSearch_result().size() < 1) {
            result.setMessage("Please conduct a search before making a selection.");
        } else {
            setSelected(true);    // In acest moment, melodia / podcast este selectata si gata pentru "load", "like" ..
            if (result_type == 1) {
                for (SongInput iter: library.getSongs()) {
                                                        /* vvvv SUBJECT TO CHANGE vvvv */
                    /*   v--- melodia din librarie             v----- rezultatele lui "search"*/
                    if (iter.getName().contains(getSearch_result().get(command.getItemNumber()-1))) {
                                                                                            /*   ^----- elementul din rezultatele lui "search" de pe pozitia scrisa in comanda */
                        setSong(iter);      // <--- salvam referinta catre melodia selectata
                        result.setMessage("Successfully selected " + iter.getName() + ".");
                        break;
                    }
                }
            } else if (result_type == 2) {
                /* Similar cu cazul "result_type == 1" */
                for (PodcastInput iter: library.getPodcasts()) {
                    if (iter.getName().contains(getSearch_result().get(command.getItemNumber()-1))) {
                        setPodcast(iter);
                        result.setMessage("Successfully selected " + iter.getName() + ".");
                        break;
                    }
                }
            } else {
                /*
                *   Se foloseste itemNumber din command
                *   Iteram prin lista de playlisturi si cautam pe cel desemnat de itemNumber
                */
                for (Playlist playlist: user.getPlaylists()) {
                    String namePlaylist = playlist.getDetails().getName();
                    String potentialPlaylistName = getSearch_result().get(command.getItemNumber()-1);
                    if (namePlaylist.equals(potentialPlaylistName)) {
                        setPlaylist(playlist);
                        result.setMessage("Successfully selected " + playlist.getDetails().getName() + ".");
                        break;
                    }
                }
            }
        }

        return result;
    }
}
