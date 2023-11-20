package output.result;

import input.commands.CommandIn;

import java.util.ArrayList;

/**
 *      Aceasta clasa este folosita pentru output-ul comenzii
 *      "showPreferedSongs"
 * */

public class ResultPreferedSongs extends ResultCommand {
    private ArrayList<String> result;

    public ResultPreferedSongs(CommandIn command, ArrayList<String> result) {
        super(command);
        setResult(result);
    }

    public void setResult(ArrayList<String> result) { this.result = result; }

    public ArrayList<String> getResult()  { return result; }

}
