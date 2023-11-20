package player.commands;

import input.commands.CommandIn;
import output.result.ResultOut;

public class PlayPause {
    public static ResultOut playPauseFunc(Player player, CommandIn command) {
        ResultOut result = new ResultOut(command);

        /*
            In cazul in care se pune pe pauza player-ul, timpul s-a scurs
            pana la comanda de pauza, deci trebuie modificat
        */
        player.updateRemainedTime(command);
        player.getStats().setPaused(player.getStats().getPaused() ^ true);

        if (player.getStats().getPaused()) {
            result.setMessage("Playback paused successfully.");
        } else {
            result.setMessage("Playback resumed successfully.");
        }

        return result;
    }

}
