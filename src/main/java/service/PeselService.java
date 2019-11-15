package service;

import entity.Pesel;
import entity.parser.PeselParser;
import exception.PeselCheckDigitWrongException;
import exception.PeselDateWrongException;
import exception.PeselLengthWrongException;
import validator.PeselValidator;

public class PeselService {
    private static PeselValidator peselValidator = PeselValidator.getInstance();
    private static PeselService instance = null;

    private PeselService() {

    }

    public static PeselService getInstance() {
        if (instance == null) {
            instance = new PeselService();
        }
        return instance;
    }

    public Pesel addPesel(String pesel) throws PeselLengthWrongException, PeselDateWrongException, PeselCheckDigitWrongException {
        if (peselValidator.isPeselCorrect(pesel)) {
            Integer[] convertedPesel = PeselParser.parsePesel(pesel);
            Pesel newPesel = new Pesel(
                    pesel,
                    PeselParser.parseSpelledMonth(convertedPesel),
                    PeselParser.parseGender(convertedPesel),
                    PeselParser.parseCheckDigit(convertedPesel),
                    PeselParser.parseYear(convertedPesel),
                    PeselParser.parseMonth(convertedPesel),
                    PeselParser.parseDay(convertedPesel)
            );
            return newPesel;
        }
        return null;
    }
}
