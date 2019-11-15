import exception.PeselCheckDigitWrongException;
import exception.PeselDateWrongException;
import exception.PeselLengthWrongException;

class OLD_PESEL {
    private String pesel, monthSpelled, gender;
    private int[] peselProcessed = new int[11];
    private int checkDigit, year, month, day;

    private final int LEGIT_PESEL_LENGTH = 11;
    private int LAST_PESEL_NUMBER;

    public OLD_PESEL(String pesel) {
        try {
            checkPeselLengthCorrectness(pesel);
            initializePeselProcessedAndLastNumber(pesel);
            initializeAndComputeCheckDigit();
            checkCheckDigitCorrectness(pesel, checkDigit, LAST_PESEL_NUMBER);
            initializeAndCheckCorrectnessOfDateVariables(peselProcessed, pesel);
            initializeGender(peselProcessed);
            this.pesel = pesel;
        } catch (PeselLengthWrongException | PeselCheckDigitWrongException | PeselDateWrongException exc) {
            System.out.println(exc);
        }
    }

    private void checkPeselLengthCorrectness(String pesel) throws PeselLengthWrongException {
        if (pesel.length() != LEGIT_PESEL_LENGTH) {
            throw new PeselLengthWrongException(pesel);
        }
    }

    private void initializePeselProcessedAndLastNumber(String pesel) {
        for (int i = 0; i < LEGIT_PESEL_LENGTH; i++) {
            peselProcessed[i] = Integer.parseInt(pesel.substring(i, i + 1));
        }
        LAST_PESEL_NUMBER = peselProcessed[10];
    }

    private void initializeAndComputeCheckDigit() {
        int tempComputationDigit = 0;
        final int[] VALIDATORS = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};

        for (int i = 0; i < VALIDATORS.length; i++) {
            tempComputationDigit += (peselProcessed[i] * VALIDATORS[i]);
        }
        checkDigit = computeCheckDigit(tempComputationDigit);
    }

    private int computeCheckDigit(int tempComputationDigit) {
        tempComputationDigit %= 10;
        tempComputationDigit = tempComputationDigit == 0 ? tempComputationDigit : 10 - tempComputationDigit;
        return tempComputationDigit;
    }

    private void checkCheckDigitCorrectness(String pesel, int checkDigit, int LAST_PESEL_NUMBER) throws PeselCheckDigitWrongException {
        if (checkDigit != LAST_PESEL_NUMBER) throw new PeselCheckDigitWrongException(pesel, checkDigit, LAST_PESEL_NUMBER);
    }

    private void initializeAndCheckCorrectnessOfDateVariables(int[] peselProcessed, String pesel) throws PeselDateWrongException {
        initializeYearMonthDay(peselProcessed);
        standardiseMonthValue(month);
        checkMonthCorrectness(month, pesel);
        initializeMonthSpelled(month);
        computeFullYear(year, peselProcessed);
        checkDayCorrectness(day, month, checkIfLeapYear(year), pesel);
    }

    private void initializeYearMonthDay(int[] peselProcessed) {
        this.year = (peselProcessed[0] * 10) + peselProcessed[1];
        this.month = (peselProcessed[2] * 10) + peselProcessed[3];
        this.day = (peselProcessed[4] * 10) + peselProcessed[5];
    }

    private void standardiseMonthValue(int month) {
        this.month = month % 10 == 0 ? 10 : month % 10;
    }

    private void checkMonthCorrectness(int month, String pesel) throws PeselDateWrongException {
        if (!(month >= 1 && month <= 12)) {
            throw new PeselDateWrongException(pesel);
        }
    }

    private void initializeMonthSpelled(int month) {
        String[] monthsToSpell = {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        this.monthSpelled = monthsToSpell[month];
    }

    private void computeFullYear(int year, int[] peselProcessed) {
        this.year = 1900 + year;
        if (peselProcessed[2] >= 2 && peselProcessed[2] < 8) {
            this.year += (int) Math.floor(peselProcessed[2] / 2.0) * 100;
        } else if (peselProcessed[2] >= 8) {
            this.year -= 100;
        }
    }

    private boolean checkIfLeapYear(int year) {
        return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
    }

    private void checkDayCorrectness(int day, int month, boolean leapYear, String pesel) throws PeselDateWrongException {
        if (day == 31) {
            checkMonthsWith31Days(month, pesel);
        }
        if (month == 2) {
            check2ndMonthAgainstLeapYear(day, leapYear, pesel);
        }
        checkMonthBoundaries(day, pesel);
    }

    private void checkMonthsWith31Days(int month, String pesel) throws PeselDateWrongException {
        int[] temp_months_to_compare = {1, 3, 5, 7, 8, 10, 12};
        boolean temp31DaysPassed = false;
        for (var x : temp_months_to_compare)
            if (month == x) {
                temp31DaysPassed = true;
                break;
            }
        if (!temp31DaysPassed) throw new PeselDateWrongException(pesel);
    }

    private void check2ndMonthAgainstLeapYear(int day, boolean leapYear, String pesel) throws PeselDateWrongException {
        if (!((day >= 1 && day < 29) || (day == 29 && leapYear))) {
            throw new PeselDateWrongException(pesel);
        }
    }

    private void checkMonthBoundaries(int day, String pesel) throws PeselDateWrongException {
        if (!(day >= 1 && day <= 30)) {
            throw new PeselDateWrongException(pesel);
        }
    }


    private void initializeGender(int[] peselProcessed) {
        gender = peselProcessed[9] % 2 == 0 ? "female" : "male";
    }

    @Override
    public String toString() {
        return "Pesel {pesel=" + pesel +
                ", day=" + day +
                ", month=" + month +
                ", monthSpelled='" + monthSpelled + '\'' +
                ", year=" + year +
                ", gender='" + gender + '\'' +
                '}';
    }

    int getCheckDigit() {
        return checkDigit;
    }

    String getPesel() {
        return pesel;
    }

    int getDay() {
        return day;
    }

    String getMonthSpelled() {
        return monthSpelled;
    }

    int getMonth() {
        return month;
    }

    int getYear() {
        return year;
    }

    String getGender() {
        return gender;
    }
}