package search.bar;
import fileio.input.*;
import input.commands.*;
import main.UserInfo;
import output.result.*;
import playlist.commands.Playlist;

import java.util.ArrayList;

public class Search {
    /**
     *   Aceasta metoda implementeaza comanda "search".
     *   Aceasta returneaza o clasa de forma mesajului de output cautat
     */
    public static ResultOutSearch search_func(LibraryInput library, CommandIn command, UserInfo user) {
        ResultOutSearch output = new ResultOutSearch(command);

        /* Verifica tipul comenzii pentru a utiliza functia de verificare de filtre corespunzator */
        if (command.getType().contains("song")) {
            /* Itereaza prin lista de melodii din librarie */
            for (SongInput song: library.getSongs()) {
                SongInput result = checkFilters_Songs(command.getFilters(), song);

                /*
                *   Se intampla ca melodia curenta sa nu respecte cel putin un filtru impus,
                *   deci functia "checkFilters_Songs" returneaza "null".
                */
                if (result != null) {
                    output.addResult(result.getName());
                }

                /* Precum in cerinta specificat, mai mult de 5 rezultate nu pot fi afisate pentru o cautare. */
                if (output.getResults().size() == 5) {
                    break;
                }
            }

        } else if (command.getType().contains("podcast")) {
            /* Iterare prin lista de podcasturi din librarie */
            for (PodcastInput podcast: library.getPodcasts()) {
                PodcastInput result = checkFilters_Podcasts(command.getFilters(), podcast);

                if (result != null) {
                    output.addResult(result.getName());
                }
                if (output.getResults().size() == 5) {
                    break;
                }
            }
        } else {
            for (Playlist playlist: user.getPlaylists()) {
                Playlist result = checkFilters_Playlist(command.getFilters(), playlist);

                if (result != null) {
                    output.addResult(result.getDetails().getName());
                }
                if (output.getResults().size() == 5) {
                    break;
                }
            }
        }

        output.setMessage("Search returned " + output.getResults().size() + " results");
        return output;
    }

    private static Playlist checkFilters_Playlist(Filters filters, Playlist playlist) {
        int valid_filters = 0;

        if (filters.getOwner() != null) {
            if (playlist.getUser().contains(filters.getOwner())) {
                valid_filters++;
            }
        }

        if (filters.getName() != null) {
            if (playlist.getDetails().getName().contains(filters.getName())) {
                valid_filters++;
            }
        }

        if (valid_filters == filters.getNonNullFields()) {
            return playlist;
        }

        return null;
    }


    /**
    *   Metoda curenta este similara cu "checkFilters_Songs".
    *   Metoda verifica campurile lui "podcast", in parte, relativ la campurile ...
     *  ... conditionarii "filters".
     */
    private static PodcastInput checkFilters_Podcasts(Filters filters, PodcastInput podcast) {
        int valid_filters = 0;

        if (filters.getOwner() != null) {
            if (podcast.getOwner().contains(filters.getOwner())) {
                valid_filters++;
            }
        }

        if (filters.getName() != null) {
            if (podcast.getName().contains(filters.getName())) {
                valid_filters++;
            }
        }

        if (valid_filters == filters.getNonNullFields()) {
            return podcast;
        }
        return null;
    }

    /**
     * Metoda de mai jos verifica daca "song" satisface cautarea conditionata de "filters".
     * Variabila "valid_filters" este raspunzatoare de validarea filtrelor comenzii curente.
     * Adica, daca una din conditiile "impuse" de "filters" nu este respectata, melodia
     * nu satisface cererea utilizatorului, lucru sustinut si bazat pe valoarea "valid_filters",
     * deci aceasta melodie nu va fi adaugata in rezultatul comenzii "search".
     * Este necesar si scopul cautarii ca "valid_filters" sa fie egal cu numarul de campuri nenule ale "filters"
     */
    private static SongInput checkFilters_Songs(Filters filters, SongInput song) {
        int valid_filters = 0;

        if (filters.getName() != null) {
            if (song.getName().startsWith(filters.getName())) {
                valid_filters++;
            }
        }

        if (filters.getAlbum() != null) {
            if (song.getAlbum().contains(filters.getAlbum())) {
                valid_filters++;
            }
        }

        if (!filters.getTags().isEmpty()) {
            int tags_found = 0;

            for (String tag: song.getTags()) {
                for (String filter_tag: filters.getTags()) {
                    if (tag.toLowerCase().equals(filter_tag)) {
                        tags_found++;
                        break;
                    }
                }
            }

            if (tags_found == filters.getTags().size()) {
                valid_filters++;
            }
        }

        if (filters.getLyrics() != null) {
            if (song.getLyrics().toLowerCase().contains(filters.getLyrics())) {
                valid_filters++;
            }
        }

        if (filters.getGenre() != null) {
            if (song.getGenre().toLowerCase().contains(filters.getGenre().toLowerCase())) {
                valid_filters++;
            }
        }

        if (filters.getReleaseYear() != null) {             // filters.getRealeaseYear = ">/<2000"
            String releaseYear = filters.getReleaseYear().substring(1);         // 2000
            if (filters.getReleaseYear().contains("<")) {           // < 2000
                if (song.getReleaseYear() < Integer.parseInt(releaseYear)) {
                    valid_filters++;
                }
            } else {       // > 2000
                if (song.getReleaseYear() > Integer.parseInt(releaseYear)) {
                    valid_filters++;
                }
            }
        }

        if (filters.getArtist() != null) {
            if (song.getArtist().equals(filters.getArtist())) {
                valid_filters++;
            }
        }

        if (valid_filters == filters.getNonNullFields()) {
            return song;
        }
        return null;
    }
}
