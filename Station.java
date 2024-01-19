import java.util.ArrayList;

public class Station {

    private int stop_sequence;
    private String stop_id;
    private String stop_name;
    private int arrival_time;

    private int direction_id;

    private ArrayList<Metro> metroLines;

    private String route_short_name;
    public Station(String stop_id,String stop_name,int arrival_time,int stop_sequence, int direction_id,String route_short_name){
        this.stop_id = stop_id;
        this.stop_name=stop_name;
        this.arrival_time =arrival_time;
        this.stop_sequence = stop_sequence;
        this.direction_id = direction_id;
        this.route_short_name = route_short_name;
        this.metroLines = new ArrayList<Metro>();
    }
    public void addMetro(Metro metroName) {
        if (!metroLines.contains(metroName))
            metroLines.add(metroName);
    }

    public ArrayList<Metro> getMetroLines() {
        return metroLines;
    }

    public int getStopSequence() {
        return stop_sequence;
    }

    public String getStopId() {
        return stop_id;
    }

    public String getStopName() {
        return stop_name;
    }

    public int getArrivalTime() {
        return arrival_time;
    }

    public int getDirectionId() {
        return direction_id;
    }

    public String getRouteShortName() {
        return route_short_name;
    }

    // Setter methods (if needed)
    public void setStopSequence(int stop_sequence) {
        this.stop_sequence = stop_sequence;
    }

    public void setStopId(String stop_id) {
        this.stop_id = stop_id;
    }

    public void setStopName(String stop_name) {
        this.stop_name = stop_name;
    }

    public void setArrivalTime(int arrival_time) {
        this.arrival_time = arrival_time;
    }

    public void setDirectionId(int direction_id) {
        this.direction_id = direction_id;
    }

    public void setRouteShortName(String route_short_name) {
        this.route_short_name = route_short_name;
    }






}

