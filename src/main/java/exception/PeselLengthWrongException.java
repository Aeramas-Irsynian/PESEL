package exception;

public class PeselLengthWrongException extends Exception {
    private String pesel;
    private int peselSize;

    public PeselLengthWrongException(String pesel) {
        peselSize = pesel.length();
        this.pesel = pesel;
    }

    @Override
    public String toString() {
        return "Pesel {pesel=" + pesel +
                ", peselSize=" + peselSize +
                "} PESEL SIZE IS WRONG";
    }
}