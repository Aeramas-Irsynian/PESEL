package entity;

public class Pesel {
    private String pesel, monthSpelled, gender;
    private Integer checkDigit, year, month, day;

    public Pesel(String pesel, String monthSpelled, String gender, Integer checkDigit, Integer year, Integer month, Integer day) {
        this.pesel = pesel;
        this.monthSpelled = monthSpelled;
        this.gender = gender;
        this.checkDigit = checkDigit;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getPesel() {
        return pesel;
    }

    public String getMonthSpelled() {
        return monthSpelled;
    }

    public String getGender() {
        return gender;
    }

    public Integer getCheckDigit() {
        return checkDigit;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getDay() {
        return day;
    }

    @Override
    public String toString() {
        return "Pesel{" +
                "pesel='" + pesel + '\'' +
                ", checkDigit=" + checkDigit +
                ", day=" + day +
                ", month=" + month +
                ", monthSpelled='" + monthSpelled + '\'' +
                ", year=" + year +
                ", gender='" + gender + '\'' +
                '}';
    }
}
