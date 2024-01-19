
public class Edge {
    private Vertex source;
    private Vertex destination;
    private int weight;

    private Metro metro;

    public Edge(Vertex source, Vertex destination, int weight, Metro metro) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.metro=metro;
    }

    public Vertex getSource() {
        return source;
    }

    public Metro getMetro() { return metro;}

    public void setMetro(Metro metro) {
        this.metro = metro;
    }

    public void setSource(Vertex source) {
        this.source = source;
    }

    public Vertex getDestination() {
        return destination;
    }

    public void setDestination(Vertex destination) {
        this.destination = destination;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}


