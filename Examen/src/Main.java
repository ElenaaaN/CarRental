import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String csvFile = "4LF622.csv";
        String line = "";
        String csvSplitBy = ",";

        List<Ryanair> _OverOneHourAndFourtyMinutes = new ArrayList<>();
        List<Ryanair> _OverThreeHoursAndTwentyMinutes = new ArrayList<>();
        List<Ryanair> _OverFiveHours = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Skip the header line

            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);

                String NUMAR_ZBOR = data[0].trim();
                String DESTINATIE = data[1].trim();
                String ORIGINE = data[2].trim();
                String DATA_PLECĂRII = data[3].trim();
                String ORA_PLECĂRII = data[4].trim();
                String ORA_SOSIRII = data[5].trim();
                int DURATA_ZBORULUI_MINUTE = Integer.parseInt(data[6].trim());
                int PRET = Integer.parseInt(data[7].trim());
                int LOCURI_DISPONIBILE = Integer.parseInt(data[8].trim());
                String TIP_AVION = data[9].trim();

                Ryanair flight = new Ryanair(NUMAR_ZBOR, DESTINATIE, ORIGINE, DATA_PLECĂRII, ORA_PLECĂRII, ORA_SOSIRII, DURATA_ZBORULUI_MINUTE, PRET, LOCURI_DISPONIBILE, TIP_AVION);

                if (flight.getDURATA_ZBORULUI_MINUTE() > 100) {
                    _OverOneHourAndFourtyMinutes.add(flight);
                }
                if (flight.getDURATA_ZBORULUI_MINUTE() > 200) {
                    _OverThreeHoursAndTwentyMinutes.add(flight);
                }
                if (flight.getDURATA_ZBORULUI_MINUTE() > 300) {
                    _OverFiveHours.add(flight);
                }
            }

            System.out.println("Zborurile din lista _OverThreeHoursAndTwentyMinutes care pleacă după 22/04/2024 (excluzând Airbus A320):");
            for (Ryanair flight : _OverThreeHoursAndTwentyMinutes) {
                if (flight.getDATA_PLECĂRII().compareTo("22/04/2024") >= 0 && !flight.getTIP_AVION().equals("Airbus A320")) {
                    System.out.println(flight);
                }
            }

            System.out.println("\nZborurile din lista _OverFiveHours sortate după preț, descrescător, excluzând originea cu litera 'C':");
            _OverFiveHours.removeIf(flight -> flight.getORIGINE().startsWith("C"));
            _OverFiveHours.sort(Comparator.comparingInt(Ryanair::getPRET).reversed());
            for (Ryanair flight : _OverFiveHours) {
                System.out.println(flight);
            }

            // Gruparea zborurilor după TIP_AVION și excluderea celor cu destinația în Italia
            Map<String, List<Ryanair>> groupedFlights = new HashMap<>();
            for (Ryanair flight : _OverFiveHours) {
                if (!flight.getDESTINATIE().trim().equalsIgnoreCase("Roma") && !flight.getDESTINATIE().trim().equalsIgnoreCase("Milano")) {
                    groupedFlights.computeIfAbsent(flight.getTIP_AVION(), k -> new ArrayList<>()).add(flight);
                }
            }

            System.out.println("\nZborurile grupate după TIP_AVION (excluzând destinația Italia):");
            for (Map.Entry<String, List<Ryanair>> entry : groupedFlights.entrySet()) {
                System.out.println("Tip avion: " + entry.getKey());
                for (Ryanair flight : entry.getValue()) {
                    System.out.println(flight);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
