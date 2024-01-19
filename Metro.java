import java.util.ArrayList;

public class Metro {


    private ArrayList<Station> stations;
    private String Metroname;

    public Metro(ArrayList<Station>list,String Metroname) {
        this.stations=list;
        this.Metroname = Metroname;
    }

    public ArrayList<Station> getStations() {
        return stations;
    }
    public Station getStation(int index) {return stations.get(index);
    }
    public String getStationName(int index) {
        if (index >= 0 && index < stations.size()) {
            return stations.get(index).getStopName();
        } else {
            return "Invalid index";
        }
    }
    public String getMetroname() { return Metroname;}

    public void setMetroname(String metroname) {
        Metroname = metroname;
    }

    public void addStation(Station station) {
        stations.add(station);
    }

    public String getMetroName() {
        return Metroname;
    }




}

