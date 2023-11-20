package input.commands;

import java.util.ArrayList;

public class Filters {
    private String name;
    private String album;
    private ArrayList<String> tags;
    private String lyrics;
    private String genre;
    private String releaseYear;
    private String artist;
    private String owner;               // Pentru playlist / podcast
    private int notnullfields = 0;   // Pentru comanda "search"

    public int getNonNullFields() {
            return notnullfields;
    }

    public Filters() {
        tags = new ArrayList<>();
    }

    public void setAlbum(String album) {
        this.album = album;
        notnullfields++;
    }

    public void setArtist(String artist) {
        this.artist = artist;
        notnullfields++;
    }

    public void setGenre(String genre) {
        this.genre = genre;
        notnullfields++;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
        notnullfields++;
    }

    public void setOwner(String owner) {
        this.owner = owner;
        notnullfields++;
    }

    public void setName(String name) {
        this.name = name;
        notnullfields++;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
        notnullfields++;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
        notnullfields++;
    }

    public String getOwner() {
        return owner;
    }

    public String getArtist() {
        return artist;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public String getLyrics() {
        return lyrics;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public String getAlbum() {
        return album;
    }

    public String getName() {
        return name;
    }
}
