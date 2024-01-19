import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Vertex {
    private Station station;
    private ArrayList<Edge> edges;
    private Vertex parent;
    private boolean visited;
    private double cost;

    public Vertex(Station name) {
        this.station = name;
        this.edges = new ArrayList<Edge>();
        this.parent = null;
        this.visited = false;
        this.cost = Double.MAX_VALUE;
    }

    public void addEdge(Edge e) {
        edges.add(e);
    }

    public ArrayList<Edge> getEdges() {
        return this.edges;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(String name) {
        this.station = station;
    }

    public Vertex getParent() {
        return parent;
    }

    public void setParent(Vertex parent) {
        this.parent = parent;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void visit() {
        this.visited = true;
    }

    public void unvisit() {
        this.visited = false;
    }

    public boolean isVisited() {
        return this.visited;
    }

    public Vertex getUnvisitedNeighbor() {
        Vertex result = null;

        Iterator<Vertex> neighbors = getNeighborIterator();
        while (neighbors.hasNext() && (result == null))
        {
            Vertex nextNeighbor = neighbors.next();
            if (!nextNeighbor.isVisited())
                result = nextNeighbor;
        } // end while

        return result;
    }

    public boolean hasEdge(String neighbor) {
        boolean found = false;
        Iterator<Vertex> neighbors = getNeighborIterator();
        while (neighbors.hasNext())
        {
            Vertex nextNeighbor = neighbors.next();
            if (nextNeighbor.getStation().getStopName().equalsIgnoreCase(neighbor))
            {
                found = true;
                break;
            }
        } // end while

        return found;
    }

    public Iterator<Vertex> getNeighborIterator()
    {
        return new NeighborIterator();
    } // end getNeighborIterator

    private class NeighborIterator implements Iterator<Vertex>
    {
        int edgeIndex = 0;
        private NeighborIterator()
        {
            edgeIndex = 0;
        } // end default constructor

        public boolean hasNext()
        {
            return edgeIndex < edges.size();
        } // end hasNext

        public Vertex next()
        {
            Vertex nextNeighbor = null;

            if (hasNext())
            {
                nextNeighbor = edges.get(edgeIndex).getDestination();
                edgeIndex++;
            }
            else
                throw new NoSuchElementException();

            return nextNeighbor;
        } // end next

        public void remove()
        {
            throw new UnsupportedOperationException();
        } // end remove
    } // end NeighborIterator
}


