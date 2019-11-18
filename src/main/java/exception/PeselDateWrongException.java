package exception;

public class PeselDateWrongException extends Exception {
    private String pesel;

    public PeselDateWrongException(String pesel) {
        this.pesel = pesel;
    }

    @Override
    public String toString() {
        return "Pesel {pesel=" + pesel +
                "} PESEL DATE IS WRONG";
    }
}
