package output.result;

import input.commands.CommandIn;
import player.commands.Stats;

/**
 *      Clasa curenta este folosita pentru output-ul comenzii "status"
 */

public class ResultStatus extends ResultCommand {
    Stats stats;

    public ResultStatus(CommandIn command) {
        super(command);
    }

    public void setStats(Stats status) { this.stats = new Stats(status); }

    public Stats getStats() { return stats; }
}
