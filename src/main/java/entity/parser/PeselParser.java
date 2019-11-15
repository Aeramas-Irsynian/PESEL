package entity.parser;

public class PeselParser {
    private static final int LEGIT_PESEL_LENGTH = 11;

    public static Integer[] parsePesel(String pesel) {
        Integer[] convertedPesel = new Integer[LEGIT_PESEL_LENGTH];
        for (int i = 0; i < LEGIT_PESEL_LENGTH; i++) {
            convertedPesel[i] = Integer.parseInt(pesel.substring(i, i + 1));
        }
        return convertedPesel;
    }

    public static Integer parseYear(Integer[] peselConverted) {
        Integer year = 1900 + (peselConverted[0] * 10) + peselConverted[1];
        if (peselConverted[2] >= 2 && peselConverted[2] < 8) {
            year += (int) Math.floor(peselConverted[2] / 2.0) * 100;
        } else if (peselConverted[2] >= 8) {
            year -= 100;
        }
        return year;
    }

    public static Integer parseMonth(Integer[] peselConverted) {
        return ((peselConverted[2] * 10) + peselConverted[3]) % 10 == 0 ? 10 : (peselConverted[2] * 10) + peselConverted[3] % 10;
    }

    public static Integer parseDay(Integer[] peselConverted) {
        return (peselConverted[4] * 10) + peselConverted[5];
    }

    public static String parseSpelledMonth(Integer[] peselConverted) {
        String[] monthsToSpell = {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthsToSpell[parseMonth(peselConverted)];
    }

    public static Integer parseCheckDigit(Integer[] peselConverted) {
        Integer tempComputationDigit = 0;
        final Integer[] VALIDATORS = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};

        for (int i = 0; i < VALIDATORS.length; i++) {
            tempComputationDigit += (peselConverted[i] * VALIDATORS[i]);
        }
        return computeCheckDigit(tempComputationDigit);
    }

    private static Integer computeCheckDigit(Integer tempComputationDigit) {
        tempComputationDigit %= 10;
        return tempComputationDigit == 0 ? tempComputationDigit : 10 - tempComputationDigit;
    }

    public static String parseGender(Integer[] peselConverted) {
        return peselConverted[9] % 2 == 0 ? "female" : "male";
    }
}
