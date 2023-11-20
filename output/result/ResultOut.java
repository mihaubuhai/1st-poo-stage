package output.result;

/*
    "ResultOut" este o clasa conceputa pentru a fi folosita la
    scrierea rezultatelor comenzilor de tipul "select", "load".
 */

import input.commands.CommandIn;

public class ResultOut extends ResultCommand {
    private String message;

    public ResultOut(CommandIn command) {
        super(command);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
