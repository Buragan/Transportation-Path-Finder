import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Stack;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        int count = 1;
        boolean flag = false;
        String csvDosya = "C:\\Users\\excalıbur\\OneDrive\\Desktop\\Paris_RER_Metro.csv";
        ArrayList<Metro> metroLines = new ArrayList<Metro>();
        DirectedGraph graph = new DirectedGraph();
        DirectedGraph metrograph = new DirectedGraph();
        Metro nextMetro = new Metro(new ArrayList<Station>(), "0");
        int metroCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(csvDosya))) {// read the file
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8) {
                    if ((count != 1 && parts[3].equals("1"))) {
                        metroLines.add(metroCount, nextMetro);
                        metroCount++;
                        nextMetro = new Metro(new ArrayList<Station>(), Integer.toString(metroCount));
                    }
                    if (flag) {
                        String stop_id = parts[0];
                        String stop_name = parts[1];
                        Integer arrival_time = Integer.parseInt(parts[2]);
                        Integer stop_sequence = Integer.parseInt(parts[3]);
                        Integer direction_id = Integer.parseInt(parts[4]);
                        String route_short_name = parts[5];
                        String route_long_name = parts[6];
                        String route_type = parts[7];
                        nextMetro.setMetroname(route_short_name);
                        Station station = new Station(stop_id, stop_name, arrival_time, stop_sequence, direction_id, route_short_name);
                        nextMetro.addStation(station);
                        int vertexAdded = 0;
                        int metroLinesLoop = 0;
                        while (metroLinesLoop != metroCount)
                        {
                            for (int k = 0; k < metroLines.get(metroLinesLoop).getStations().size(); k++) {
                                if (metroLines.get(metroLinesLoop).getStationName(k) == station.getStopName()) {
                                    vertexAdded += 1;
                                }
                            }
                            metroLinesLoop++;
                        }
                        nextMetro.setMetroname(nextMetro.getMetroname() + " towards " + nextMetro.getStations().get(nextMetro.getStations().size()-1).getStopName());
                        if (vertexAdded == 0) {
                            graph.getVertices().put(station.getStopName(), new Vertex(station));//durak vertex ekleme

                        }
                        graph.getVertices().get(station.getStopName()).getStation().addMetro(nextMetro);
                        count++;
                    }
                    if (!flag)
                        flag = true;
                }
            }
            metroLines.add(metroCount, nextMetro);
        } catch (FileNotFoundException e) {
            // If you can't find the file
            System.err.println("Dosya bulunamadı: " + e.getMessage());
        }
        for (int i = 0; i < metroLines.size(); i++) {
            for (int j = 0; j < metroLines.get(i).getStations().size() - 1; j++) {
                graph.addEdge(metroLines.get(i).getStation(j), metroLines.get(i).getStation(j + 1), Math.abs(metroLines.get(i).getStation(j + 1).getArrivalTime() - metroLines.get(i).getStation(j).getArrivalTime()), metroLines.get(i));
            }
        }
        long startTimeMain = System.currentTimeMillis();
        String test = "C:\\Users\\excalıbur\\OneDrive\\Desktop\\Test100.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(test))) {// read the file
            String line;
            int counters = 0;
            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");
                String firststate = parts[0];
                String secondstate = parts[1];
                String choice = parts[2];
                if(counters!=0)
                    System.out.print(counters + ") ");

                if(choice.equals("0")){

                    graph.fewerStops(firststate, secondstate);


                }
                String a ="";
                if(counters==53)
                    a ="abc";
                if(choice.equals("1")){

                    graph.quickestPathFind(firststate, secondstate);
                }

                counters++;

                System.out.println();
                System.out.println();
                System.out.println();
            }


        }
        long endTimeMain = System.currentTimeMillis();
        System.out.println(endTimeMain-startTimeMain);

        String firststate="";
        String  secondstate="";
        while (true) {
            while(true) {
                Scanner scan = new Scanner(System.in);
                System.out.println("\n Name of departure station: ");
                firststate = scan.nextLine();

                if(graph.getVertices().get(firststate) !=null )
                    break;
                System.out.println("Station not found please write again");
            }
            while(true) {
                Scanner scan1 = new Scanner(System.in);
                System.out.println("\n Name of destination station: ");
                secondstate = scan1.nextLine();
                if(graph.getVertices().get(secondstate) !=null )
                    break;
                System.out.println("Station not found please write again");
            }
            boolean validchoice = false;

            while (!validchoice) {
                Scanner scan2 = new Scanner(System.in);
                System.out.println("\n Preference: (1 for shortest time, 2 for fewer stops, 3 for least transfers)");
                String choice = scan2.nextLine();
                if (choice.equals("1")) {
                    System.out.println();
                    graph.quickestPathFind(firststate, secondstate);
                    validchoice = true;
                } else if (choice.equals("2")) {
                    System.out.println();
                    graph.fewerStops(firststate, secondstate);
                    validchoice = true;
                }
                else if (choice.equals("3")) {
                    System.out.println();
                    graph.leastTransfers(firststate, secondstate);
                    validchoice = true;
                }else {
                    System.out.println("Enter a valid choice");
                }
            }
        }
    }

    private static void printPath(Stack<String> path) {
        if (path.isEmpty()) {
            System.out.println("Ulaşım yok.");
            return;
        }

        System.out.println("Yol:");
        String currentStation = path.pop();
        System.out.print(currentStation);

        while (!path.isEmpty()) {
            System.out.print(" -> ");
            currentStation = path.pop();
            System.out.print(currentStation);
        }

        System.out.println();  // Yeni satıra geç
    }

}