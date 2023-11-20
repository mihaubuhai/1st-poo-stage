package output.result;

import input.commands.CommandIn;

import java.util.ArrayList;

/* Aceasta clasa extinde clasa "ResultOut" pentru comanda "search" */

public class ResultOutSearch extends ResultOut{
    private ArrayList<String> results;

    public ResultOutSearch(CommandIn command) {
        super(command);
        results = new ArrayList<>();
    }

    public ArrayList<String> getResults()  { return results; }

    public void addResult(String result) {
        results.add(result);
    }

}
