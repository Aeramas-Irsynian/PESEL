package validator;

import entity.parser.PeselParser;
import exception.PeselCheckDigitWrongException;
import exception.PeselDateWrongException;
import exception.PeselLengthWrongException;

public class PeselValidator {
    private final Integer LEGIT_PESEL_LENGTH = 11;

    private static PeselValidator instance = null;
    private Integer[] convertedPesel;

    private PeselValidator() {

    }

    public static PeselValidator getInstance() {
        if (instance == null) {
            instance = new PeselValidator();
        }
        return instance;
    }

    public boolean isPeselCorrect(String pesel) throws PeselLengthWrongException, PeselDateWrongException, PeselCheckDigitWrongException {
        if (isPeselLenghtWrong(pesel)) throw new PeselLengthWrongException(pesel);
        convertedPesel = PeselParser.parsePesel(pesel);
        if (isPeselDateWrong()) throw new PeselDateWrongException(pesel);
        if (isCheckDigitWrong()) throw new  PeselCheckDigitWrongException(pesel, PeselParser.parseCheckDigit(convertedPesel), convertedPesel[10]);
        return true;
    }

    private boolean isPeselLenghtWrong(String pesel) {
        return pesel.length() != LEGIT_PESEL_LENGTH;
    }

    private boolean isPeselDateWrong() {
        if (isPeselMonthWrong()) return true;
        if (isPeselDayWrong()) return true;
        return false;
    }

    private boolean isPeselMonthWrong() {
        return PeselParser.parseMonth(convertedPesel) < 1 | PeselParser.parseMonth(convertedPesel) > 12;
    }

    private boolean isPeselDayWrong() {
        if (PeselParser.parseDay(convertedPesel) == 31) {
           return !isCorrectMonthFor31Days();
        }
        if (PeselParser.parseMonth(convertedPesel) == 2) {
            return !is2ndMonthHasCorrectDaysConsideringLeapYear();
        }
        return isMonthBoundariesWrong();
    }

    private boolean isCorrectMonthFor31Days() {
        Integer[] monthsValidFor31Days = {1, 3, 5, 7, 8, 10, 12};
        boolean isMonthValid = false;
        for (Integer validMonth : monthsValidFor31Days) {
            if (PeselParser.parseMonth(convertedPesel).equals(validMonth)) {
                isMonthValid = true;
                break;
            }
        }
        return isMonthValid;
    }

    private boolean is2ndMonthHasCorrectDaysConsideringLeapYear() {
        if(isPeselHasLeapYear()) {
            return PeselParser.parseDay(convertedPesel) == 29;
        }
        return PeselParser.parseDay(convertedPesel) >= 1 && PeselParser.parseDay(convertedPesel) < 29;
    }

    private boolean isPeselHasLeapYear() {
        return ((PeselParser.parseYear(convertedPesel) % 4 == 0 && PeselParser.parseYear(convertedPesel) % 100 != 0) || PeselParser.parseYear(convertedPesel) % 400 == 0);
    }

    private boolean isMonthBoundariesWrong() {
        return PeselParser.parseDay(convertedPesel) < 1 | PeselParser.parseDay(convertedPesel) > 31;
    }

    private boolean isCheckDigitWrong() {
        return !PeselParser.parseCheckDigit(convertedPesel).equals(convertedPesel[10]);
    }
}
