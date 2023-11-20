package player.commands;

import input.commands.CommandIn;
import output.result.ResultStatus;

public class Player {
    private Load loadInfo;           // <-- Ajutator pentru rularea player-ului
    private Stats stats;                // <-- Statisicile player-ului (la un moment) care vor fi scrise la output-ul comenzii "status"
    private int lastLoadTime;     // <-- Tine cont de timpul ultimei comenzi

    public Player(Load info, int currentTime) {
        setLoadInfo(info);
        /*
            Creeam prematur clasa pentru comanda "status" pentru
            facilitarea calculului campului "remainedTime".
        */
        setStats(info);
        setLastLoadTime(currentTime);
    }

    public void setLastLoadTime(int currentTime) { lastLoadTime = currentTime; }

    public void setLoadInfo(Load info) { loadInfo = info; }

    public Load getLoadInfo() { return loadInfo; }

    public void setStats(Load loadInfo) {
        stats = new Stats();
        stats.setFields(loadInfo);
    }

    public Stats getStats() { return stats; }

    private void removeStats() {
        stats = new Stats();
        stats.setPaused(true);
    }

    public int findRemainedTime(int currentTime) {
        return stats.getRemainedTime() - (currentTime - lastLoadTime);
    }

    public void updateRemainedTime(CommandIn command) {
        if (!stats.getPaused()) {
            stats.setRemainedTime(findRemainedTime(command.getTimestamp()));
        }
        /* Se schimba "LastLoadTime", deoarece el reprezinta timpul ultimei comenzi */
        setLastLoadTime(command.getTimestamp());
    }

    public ResultStatus statusFunc(CommandIn command) {
        ResultStatus output = new ResultStatus(command);

        /* Pana la comanda de status, timpul s-a scurs, deci trebuie modificat */
        updateRemainedTime(command);

        /* Verificam daca este melodie ceea ce este selectat; altfel punem in player piesa urmatoare */
        if (stats.getRemainedTime() <= 0) {
            if (loadInfo.getSelectInfo().getResult_type() == 1) {
                removeStats();
            }
            //Adaugam pt podcast / playlist
        }

        output.setStats(stats);
        return output;
    }
}
