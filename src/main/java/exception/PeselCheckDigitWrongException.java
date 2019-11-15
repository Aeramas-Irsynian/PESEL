package exception;

public class PeselCheckDigitWrongException extends Exception {
    private String pesel;
    private int checkDigit;
    private int lastPeselNumber;

    public PeselCheckDigitWrongException(String pesel, int checkDigit, int lastPeselNumber) {
        this.pesel = pesel;
        this.checkDigit = checkDigit;
        this.lastPeselNumber = lastPeselNumber;
    }

    public String toString() {
        return "Pesel {pesel=" + pesel +
                ", checkDigit=" + checkDigit +
                ", lastPeselNumber=" + lastPeselNumber +
                "} PESEL CHECK DIGIT IS WRONG";
    }
}