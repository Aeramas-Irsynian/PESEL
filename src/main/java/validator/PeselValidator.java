package validator;

import entity.parser.PeselParser;
import exception.PeselCheckDigitWrongException;
import exception.PeselDateWrongException;
import exception.PeselLengthWrongException;

public class PeselValidator {
    private final Integer LEGIT_PESEL_LENGTH = 11;
    private Integer LAST_PESEL_NUMBER;

    private static PeselValidator instance = null;
    private Integer[] convertedPesel;
    private Integer month, day, year, checkDigit;

    private PeselValidator() {

    }

    public static PeselValidator getInstance() {
        if (instance == null) {
            instance = new PeselValidator();
        }
        return instance;
    }

    public boolean isPeselCorrect(String pesel) throws PeselLengthWrongException, PeselDateWrongException, PeselCheckDigitWrongException {
        if (isPeselLenghtWrong(pesel)) {
            throw new PeselLengthWrongException(pesel);
        }
        initializePeselData(pesel);
        if (isPeselDateWrong()) {
            throw new PeselDateWrongException(pesel);
        }
        if (isCheckDigitWrong()) {
            throw new PeselCheckDigitWrongException(pesel, checkDigit, LAST_PESEL_NUMBER);
        }
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

    private void initializePeselData(String pesel) {
        convertedPesel = PeselParser.parsePesel(pesel);
        month = PeselParser.parseMonth(convertedPesel);
        day = PeselParser.parseDay(convertedPesel);
        year = PeselParser.parseYear(convertedPesel);
        checkDigit = PeselParser.parseCheckDigit(convertedPesel);
        LAST_PESEL_NUMBER = convertedPesel[10];
    }

    private boolean isPeselMonthWrong() {
        return month < 1 | month > 12;
    }

    private boolean isPeselDayWrong() {
        if (month == 31) {
            return !isCorrectMonthFor31Days();
        }
        if (month == 2) {
            return !is2ndMonthHasCorrectDaysConsideringLeapYear();
        }
        return isMonthBoundariesWrong();
    }

    private boolean isCorrectMonthFor31Days() {
        Integer[] monthsValidFor31Days = {1, 3, 5, 7, 8, 10, 12};
        boolean isMonthValid = false;
        for (Integer validMonth : monthsValidFor31Days) {
            if (month.equals(validMonth)) {
                isMonthValid = true;
                break;
            }
        }
        return isMonthValid;
    }

    private boolean is2ndMonthHasCorrectDaysConsideringLeapYear() {
        if (isPeselHasLeapYear()) {
            return day == 29;
        }
        return day >= 1 && day < 29;
    }

    private boolean isPeselHasLeapYear() {
        return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
    }

    private boolean isMonthBoundariesWrong() {
        return day < 1 | day > 31;
    }

    private boolean isCheckDigitWrong() {
        return !checkDigit.equals(LAST_PESEL_NUMBER);
    }
}
