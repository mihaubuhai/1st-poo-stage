package main;

import player.commands.Player;
import playlist.commands.Playlist;

import java.util.ArrayList;

/**
 *      Aceasta clasa este folosita pentru a executa comenzile corespunzatoare
 *      unui utilizator
 * */

public class UserInfo {
    private String username;
    private Player player;                                          /* Player-ul user-ului */
    private ArrayList<Playlist> playlists;                  /* Tine cont de playlist-urile user-ului */
    private ArrayList<String> likedSongs;                /* Melodiile apreciate de user */

    public UserInfo(String name) {
        setUsername(name);
        playlists = new ArrayList<>();          /* Instantiem lista de playlist-uri */
        likedSongs = new ArrayList<>();
    }

    public void setUsername(String name) { username = name; }

    public String getUsername() { return username; }

    public void setPlayer(Player newPlayer) { player = newPlayer; }

    public Player getPlayer() { return player; }

    public ArrayList<Playlist> getPlaylists() { return playlists; }

    public ArrayList<String> getLikedSongs() { return likedSongs; }

}
