package player.commands;


import fileio.input.EpisodeInput;
import fileio.input.SongInput;

/**
 *      Aceasta clasa este folosita pentru afisarea output-ului comenzii "status";
 */

public class Stats {
    private String name;
    private int remainedTime;
    private String repeat;
    private boolean shuffle, paused;

    public Stats(Stats newStats) {
        setName(newStats.getName());
        setRemainedTime(newStats.getRemainedTime());
        setRepeat(newStats.getRepeat());
        setShuffle(newStats.getShuffle());
        setPaused(newStats.getPaused());
    }

    public Stats() { setRepeat("No Repeat"); setName(""); }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public void setRemainedTime(int time) { remainedTime = time; }

    public int getRemainedTime() { return remainedTime; }

    public void setShuffle(boolean shuffle) { this.shuffle = shuffle; }

    public boolean getShuffle() { return shuffle; }

    public void setPaused(boolean paused) { this.paused = paused; }

    public boolean getPaused() { return paused; }

    public void setRepeat(String mode) { repeat = mode; }

    public String getRepeat() { return repeat; }

    public void setFields(Load loadInfo) {
        /*  1 - melodie   2 - podcast  3 - playlist   */
        if (loadInfo.getSelectInfo().getResult_type() == 1) {
            SongInput songInfo = loadInfo.getSelectInfo().getSong();
            setRemainedTime(songInfo.getDuration());
            setName(songInfo.getName());
        } else if (loadInfo.getSelectInfo().getResult_type() == 2) {
            EpisodeInput episodeInfo = loadInfo.getSelectInfo().getPodcast().getEpisodes().get(0);
            setRemainedTime(episodeInfo.getDuration());
            setName(episodeInfo.getName());
        }
    }

}
