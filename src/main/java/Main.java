import entity.Pesel;
import service.PeselService;

import java.util.ArrayList;
import java.util.List;

class Main {
    public static void main(String[] args) {
        PeselService peselService = PeselService.getInstance();

        String[] peselToAdd = new String[11];
        peselToAdd[0] = "99990364949";
        peselToAdd[1] = "12346578912";
        peselToAdd[2] = "55887";
        peselToAdd[3] = "58020324806";
        peselToAdd[4] = "15301229056";
        peselToAdd[5] = "42081227467";
        peselToAdd[6] = "56062213902";
        peselToAdd[7] = "3112046243";
        peselToAdd[8] = "14032590895";
        peselToAdd[9] = "14300710";

        List<Pesel> peselList = new ArrayList<>();

        for (String pesel : peselToAdd) {
            try {
                peselList.add(peselService.addPesel(pesel));
            } catch (Exception exc) {
                System.out.println(exc.toString());
            }
        }
        for (
                Pesel pesel : peselList) {
            System.out.println(pesel.toString());
        }
    }
}