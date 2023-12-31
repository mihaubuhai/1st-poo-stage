package player.commands;


import fileio.input.EpisodeInput;
import fileio.input.SongInput;
import playlist.commands.Playlist;

/**
 *      Aceasta clasa este folosita pentru afisarea output-ului comenzii "status";
 */
public class Stats {
    private String name;
    private int remainedTime;
    private String repeat;
    private boolean shuffle, paused;

    public Stats(final Stats newStats) {
        setName(newStats.getName());
        setRemainedTime(newStats.getRemainedTime());
        setRepeat(newStats.getRepeat());
        setShuffle(newStats.getShuffle());
        setPaused(newStats.getPaused());
    }

    public Stats() {
        setRepeat("No Repeat");
        setName("");
    }

    /** Setter */
    public void setName(final String name) {
        this.name = name;
    }

    /** Getter */
    public String getName() {
        return name;
    }

    /** Setter */
    public void setRemainedTime(final int time) {
        remainedTime = time;
    }

//    public void changeRemainedTime(int time) { remainedTime += time; }

    /** Getter */
    public int getRemainedTime() {
        return remainedTime;
    }

    /** Setter */
    public void setShuffle(final boolean shuffle) {
        this.shuffle = shuffle;
    }

    /** Getter */
    public boolean getShuffle() {
        return shuffle;
    }

    /** Setter */
    public void setPaused(final boolean paused) {
        this.paused = paused;
    }

    /** Getter */
    public boolean getPaused() {
        return paused;
    }

    /** Setter */
    public void setRepeat(final String mode) {
        repeat = mode;
    }

    /** Getter */
    public String getRepeat() {
        return repeat;
    }

    /** Aceasta metoda initializeaza campurile corespunzator player-ului, mai exact ce ruleaza */
    public void setFields(final Load loadInfo) {
        /*  1 - melodie   2 - podcast  3 - playlist   */
        final int songId = 1;
        final int podcastId = 2;
        if (loadInfo.getSelectInfo().getResultType() == songId) {
            /* Se incarca o melodie */
            SongInput songInfo = loadInfo.getSelectInfo().getSong();
            setRemainedTime(songInfo.getDuration());
            setName(songInfo.getName());
        } else if (loadInfo.getSelectInfo().getResultType() == podcastId) {
            /* Se incarca un episod de podcast */
            EpisodeInput episodeInfo = loadInfo.getSelectInfo().getPodcast().getEpisodes().get(0);
            setRemainedTime(episodeInfo.getDuration());
            setName(episodeInfo.getName());
        } else {
            /* Se incarca o melodie dintr-un playlist */
            Playlist playlist = loadInfo.getSelectInfo().getPlaylist();
            SongInput songInfo = playlist.getDetails().getSongs().get(0);
            setRemainedTime(songInfo.getDuration());
            setName(songInfo.getName());
        }
    }

}
