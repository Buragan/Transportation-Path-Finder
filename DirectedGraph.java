import java.util.*;

import java.util.ArrayList;
public class DirectedGraph implements GraphInterface{
    private HashMap<String, Vertex> vertices;

    public DirectedGraph() {
        this.vertices = new HashMap<String, Vertex>();
    }

    public void addEdge(Station source, Station destination, int weight, Metro metro) {

        Vertex source_v = vertices.get(source.getStopName());
        Vertex destination_v = vertices.get(destination.getStopName());

        if (source_v != null && destination_v != null && source_v.hasEdge(destination.getStopName())) {
        }
        else
        {
            if (vertices.get(source.getStopName()) == null) {
                source_v = new Vertex(source);
                vertices.put(source.getStopName(), source_v);
            }

            if (vertices.get(destination.getStopName()) == null) {
                destination_v = new Vertex(destination);
                vertices.put(destination.getStopName(), destination_v);
            }

            Edge edge = new Edge(source_v, destination_v, weight, metro);
            source_v.addEdge(edge);
        }
    }
    public Stack<String> fewerStops(String origin, String destination) {
        resetVertices();
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(Vertex::getCost));
        Vertex startVertex = vertices.get(origin);
        startVertex.setCost(0);
        priorityQueue.add(startVertex);

        while (!priorityQueue.isEmpty()) {
            Vertex currentVertex = priorityQueue.poll();
            currentVertex.visit();

            // Eğer endStation'a ulaşıldıysa algoritmayı durdur
            if (currentVertex.getStation().getStopName().equals(destination)) {
                return stackPath(origin, destination); // Yolu döndür
            }

            for (Edge edge : currentVertex.getEdges()) {
                Vertex neighbor = edge.getDestination();
                if (!neighbor.isVisited()) {
                    double newCost = currentVertex.getCost() + 1; // Her durak için maliyeti 1 olarak al
                    if (newCost < neighbor.getCost()) {
                        neighbor.setCost(newCost);
                        neighbor.setParent(currentVertex);
                        priorityQueue.add(neighbor);
                    }
                }
            }
        }

        Stack<String> emptyStack = new Stack<>();
        emptyStack.push("Ulaşım yok.");
        return emptyStack;
    }

    private Stack<String> stackPath(String origin, String destination) {
        Vertex endVertex = vertices.get(destination);

        Stack<String> path = new Stack<>();
        if (endVertex.getCost() == Double.MAX_VALUE) {
            path.push("Ulaşım yok.");
            return path;
        }
        while (endVertex != null) {
            path.push(endVertex.getStation().getStopName());
            endVertex = endVertex.getParent();
        }

        printFewerStop(path);
        return path;
    }

    private void printFewerStop(Stack<String> path) {

        System.out.println("Durak sayısı " + path.size());


        System.out.print("Yol: ");
        Stack<String> tempStack = new Stack<>();
        Stack<String> pathSources = new Stack<>();
        Stack<String> pathDests = new Stack<>();

        int size = path.size();
        for (int i = 0; i < size; i++) {
            tempStack.push(path.pop());
        }

        for (int i = 0; i < size; i++) {
            if (i != 0)
                pathSources.push(tempStack.peek());
            if (i != size - 1)
                pathDests.push(tempStack.peek());
            tempStack.pop();
        }

        Metro currentMetro = new Metro(new ArrayList<Station>(), "a");
        Metro lastMetro = new Metro(new ArrayList<Station>(), "b");
        String lastStop = pathSources.peek();
        String nextStop = pathDests.peek();
        Vertex source = vertices.get(pathSources.pop());
        Vertex dest = vertices.get(pathDests.pop());

        for (int i = 0; i < source.getEdges().size(); i++) {
            if (source.getEdges().get(i).getDestination().equals(dest)) {
                currentMetro = source.getEdges().get(i).getMetro();
            }
        }

        while (!pathSources.isEmpty()) {
            System.out.println();
            System.out.println(currentMetro.getMetroname());
            System.out.print(lastStop);
            int stationCount = 0;
            lastMetro = currentMetro;

            while (currentMetro.equals(lastMetro) && !pathSources.isEmpty()) {
                lastMetro = currentMetro;
                System.out.print(" -> ");
                lastStop = pathSources.peek();
                nextStop = pathDests.peek();
                source = vertices.get(pathSources.pop());
                dest = vertices.get(pathDests.pop());

                for (int i = 0; i < source.getEdges().size(); i++) {
                    if (source.getEdges().get(i).getDestination().equals(dest))
                        currentMetro = source.getEdges().get(i).getMetro();
                }

                System.out.print(lastStop);
                stationCount++;
            }
            if (currentMetro.equals(lastMetro)) {
                if (pathSources.isEmpty()) {
                    System.out.print(" -> " + nextStop);
                    stationCount++;
                }
            }
            else if (pathSources.isEmpty()) {
                System.out.print(" (" + stationCount + " Station");
                if (stationCount>1)
                    System.out.print("s");
                System.out.println(") ");
                System.out.println();
                stationCount=1;
                source = vertices.get(lastStop);
                dest = vertices.get(nextStop);
                for (int i = 0; i < source.getEdges().size(); i++) {
                    if (source.getEdges().get(i).getDestination().equals(dest))
                        currentMetro = source.getEdges().get(i).getMetro();
                }
                System.out.println(currentMetro.getMetroName());
                System.out.print(lastStop + " -> " + nextStop);
            }
            System.out.print(" (" + stationCount + " Station");
            if (stationCount > 1)
                System.out.print("s");
            System.out.println(") ");
        }

        System.out.println();
    }

    public void print() {

        for (Vertex v : vertices.values()) {
            System.out.print(v.getStation().getStopName() + " -> ");
            for (int i = 0; i < v.getEdges().size(); i++){
                System.out.print(v.getEdges().get(i).getDestination().getStation().getStopName() + " / ");
            }
            /*Iterator<Vertex> neighbors = v.getNeighborIterator();
            while (neighbors.hasNext())
            {
                Vertex n = neighbors.next();
                System.out.print(n.getStation().getStopName() + " / ");
            }*/
            System.out.println();
        }
    }
    public void quickestPathFind(String origin, String destination) {
        resetVertices();
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(Vertex::getCost));
        int count = 0;
        Vertex startVertex = vertices.get(origin);
        for (Vertex v : vertices.values()){
            count++;
            if(v.getStation().getStopName().equals(origin))
                startVertex = v;
        }
        startVertex.setCost(0);
        priorityQueue.add(startVertex);

        int ct = 0;
        while (!priorityQueue.isEmpty()) {
            Vertex currentVertex = priorityQueue.poll();
            currentVertex.visit();

            // Eğer endStation'a ulaşıldıysa algoritmayı durdur
            if (currentVertex.getStation().getStopName().equals(destination)) {
                break;
            }

            for (Edge edge : currentVertex.getEdges()) {
                Vertex neighbor = edge.getDestination();
                if (!neighbor.isVisited()) {
                    double newCost = currentVertex.getCost() + edge.getWeight(); // edge.getWeight() trenin seyahat süresi olsun
                    ct+=edge.getWeight();
                    double a = neighbor.getCost();
                    if (newCost < neighbor.getCost()) {
                        neighbor.setCost(newCost);
                        neighbor.setParent(currentVertex);
                        priorityQueue.add(neighbor);
                    }
                }
            }
        }
        printQuickestPath(origin, destination);

    }

    private void printQuickestPath(String origin, String destination) {
        Vertex endVertex = vertices.get(destination);

        if (endVertex.getCost() == Double.MAX_VALUE) {
            System.out.println("Ulaşım yok.");
            return;
        }
        double time = endVertex.getCost() / 60.0;
        System.out.println("En kısa süre: " + time + " dakika");
        System.out.println();
        // Yolu yazdır
        System.out.print("Yol: ");
        Stack<String> path = new Stack<>();
        while (endVertex != null) {
            path.push(endVertex.getStation().getStopName());
            endVertex = endVertex.getParent();


        }
        Stack<String> tempStack = new Stack<>();
        Stack<String> pathSources = new Stack<>();
        Stack<String> pathDests = new Stack<>();
        int size = path.size();
        for (int i = 0; i < size; i++){
            tempStack.push(path.pop());
        }
        for (int i = 0; i < size; i++){
            if (i != 0)
                pathSources.push(tempStack.peek());
            if (i != size-1)
                pathDests.push(tempStack.peek());
            tempStack.pop();
        }
        Metro currentMetro = new Metro(new ArrayList<Station>(), "a");
        Metro lastMetro = new Metro(new ArrayList<Station>(), "b");
        String laststop = pathSources.peek();
        String nextStop = pathDests.peek();
        Vertex source = vertices.get(pathSources.pop());
        Vertex dest = vertices.get(pathDests.pop());
        for (int i = 0; i < source.getEdges().size(); i++){
            if (source.getEdges().get(i).getDestination().equals(dest)) {
                currentMetro = source.getEdges().get(i).getMetro();
            }
        }
        while (!pathSources.isEmpty()) {
            System.out.println();
            System.out.println(currentMetro.getMetroname());
            System.out.print(laststop);
            int stationCount = 0;
            lastMetro=currentMetro;
            while(currentMetro.equals(lastMetro) && !pathSources.isEmpty()) {
                lastMetro=currentMetro;
                System.out.print(" -> ");
                laststop = pathSources.peek();
                nextStop = pathDests.peek();
                source = vertices.get(pathSources.pop());
                dest = vertices.get(pathDests.pop());
                for (int i = 0; i < source.getEdges().size(); i++) {
                    if (source.getEdges().get(i).getDestination().equals(dest))
                        currentMetro = source.getEdges().get(i).getMetro();
                }
                System.out.print(laststop);
                stationCount++;
            }
            if (currentMetro.equals(lastMetro)) {
                if (pathSources.isEmpty()) {
                    System.out.print(" -> " + nextStop);
                    stationCount++;
                }
            }
            else if (pathSources.isEmpty()) {
                System.out.print(" (" + stationCount + " Station");
                if (stationCount>1)
                    System.out.print("s");
                System.out.println(") ");
                System.out.println();
                stationCount=1;
                source = vertices.get(laststop);
                dest = vertices.get(nextStop);
                for (int i = 0; i < source.getEdges().size(); i++) {
                    if (source.getEdges().get(i).getDestination().equals(dest))
                        currentMetro = source.getEdges().get(i).getMetro();
                }
                System.out.println(currentMetro.getMetroName());
                System.out.print(laststop + " -> " + nextStop);
            }
            System.out.print(" (" + stationCount + " Station");
            if (stationCount>1)
                System.out.print("s");
            System.out.println(") ");
        }
        System.out.println();
    }



    public void leastTransfers(String origin, String destination) {
        resetVertices();
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(Vertex::getCost));
        int count = 0;
        Vertex startVertex = vertices.get(origin);
        for (Vertex v : vertices.values()){
            count++;
            if(v.getStation().getStopName().equals(origin))
                startVertex = v;
        }
        startVertex.setCost(0);
        priorityQueue.add(startVertex);
        Metro currentMetro = new Metro(new ArrayList<Station>(), "a");
        Metro lastMetro = new Metro(new ArrayList<Station>(), "b");

        int ct = 0;
        while (!priorityQueue.isEmpty()) {
            Vertex currentVertex = priorityQueue.poll();
            currentVertex.visit();

            // Eğer endStation'a ulaşıldıysa algoritmayı durdur
            if (currentVertex.getStation().getStopName().equals(destination)) {
                break;
            }
            if(currentVertex.getParent()!=null) {
                for (int i = 0; i < currentVertex.getParent().getEdges().size(); i++)
                    for (int j = 0; j < currentVertex.getEdges().size(); j++)
                        if (currentVertex.getParent().getEdges().get(i).getMetro() == currentVertex.getEdges().get(j).getMetro())
                            lastMetro = currentVertex.getEdges().get(j).getMetro();
            }
            double newCost = 0;
            for (Edge edge : currentVertex.getEdges()) {
                Vertex neighbor = edge.getDestination();
                currentMetro=edge.getMetro();
                if (!neighbor.isVisited()) {
                    if(lastMetro.getMetroName().equals("b") || lastMetro.getMetroName().equals(currentMetro.getMetroName()))
                        newCost = currentVertex.getCost() + edge.getWeight(); // edge.getWeight() trenin seyahat süresi olsun
                    else
                        newCost = currentVertex.getCost() + edge.getWeight() + 1000000; // az transferi tercih etmesi için farklı trene geçen durumlarda weighti artırıyoruz
                    ct+=edge.getWeight();
                    double a = neighbor.getCost();
                    if (newCost < neighbor.getCost()) {
                        neighbor.setCost(newCost);
                        neighbor.setParent(currentVertex);
                        priorityQueue.add(neighbor);
                    }
                }
            }
        }
        Vertex endVertex = vertices.get(destination);

        if (endVertex.getCost() == Double.MAX_VALUE) {
            System.out.println("Ulaşım yok.");
            return;
        }
        // Yolu yazdır
        System.out.print("Yol: ");
        Stack<String> path = new Stack<>();
        while (endVertex != null) {
            path.push(endVertex.getStation().getStopName());
            endVertex = endVertex.getParent();


        }
        Stack<String> tempStack = new Stack<>();
        Stack<String> pathSources = new Stack<>();
        Stack<String> pathDests = new Stack<>();
        int size = path.size();
        for (int i = 0; i < size; i++){
            tempStack.push(path.pop());
        }
        for (int i = 0; i < size; i++){
            if (i != 0)
                pathSources.push(tempStack.peek());
            if (i != size-1)
                pathDests.push(tempStack.peek());
            tempStack.pop();
        }
        currentMetro = new Metro(new ArrayList<Station>(), "a");
        lastMetro = new Metro(new ArrayList<Station>(), "b");
        String laststop = pathSources.peek();
        String nextStop = pathDests.peek();
        Vertex source = vertices.get(pathSources.pop());
        Vertex dest = vertices.get(pathDests.pop());
        int transferAmount = 0;
        for (int i = 0; i < source.getEdges().size(); i++){
            if (source.getEdges().get(i).getDestination().equals(dest)) {
                currentMetro = source.getEdges().get(i).getMetro();
            }
        }
        int firstonecount = 1;
        while (!pathSources.isEmpty() || firstonecount==1) {
            firstonecount++;
            System.out.println();
            System.out.println(currentMetro.getMetroname());
            System.out.print(laststop);
            int stationCount = 0;
            lastMetro=currentMetro;
            while(currentMetro.equals(lastMetro) && !pathSources.isEmpty()) {
                lastMetro=currentMetro;
                System.out.print(" -> ");
                laststop = pathSources.peek();
                nextStop = pathDests.peek();
                source = vertices.get(pathSources.pop());
                dest = vertices.get(pathDests.pop());
                for (int i = 0; i < source.getEdges().size(); i++) {
                    if (source.getEdges().get(i).getDestination().equals(dest))
                        currentMetro = source.getEdges().get(i).getMetro();
                }
                System.out.print(laststop);
                stationCount++;
            }
            if (currentMetro!=lastMetro)
                transferAmount++;
            if (currentMetro.equals(lastMetro)) {
                if (pathSources.isEmpty()) {
                    System.out.print(" -> " + nextStop);
                    stationCount++;
                }
            }
            else if (pathSources.isEmpty()) {
                System.out.print(" (" + stationCount + " Station");
                if (stationCount>1)
                    System.out.print("s");
                System.out.println(") ");
                System.out.println();
                stationCount=1;
                source = vertices.get(laststop);
                dest = vertices.get(nextStop);
                for (int i = 0; i < source.getEdges().size(); i++) {
                    if (source.getEdges().get(i).getDestination().equals(dest))
                        currentMetro = source.getEdges().get(i).getMetro();
                }
                System.out.println(currentMetro.getMetroName());
                System.out.print(laststop + " -> " + nextStop);
            }
            System.out.print(" (" + stationCount + " Station");
            if (stationCount>1)
                System.out.print("s");
            System.out.println(") ");
        }
        System.out.println();
        System.out.print("En az transfer sayısı: " + (transferAmount) + " Transfer");
        if (transferAmount>1)
            System.out.print("s");
        System.out.println();
    }

    public Iterable<Vertex> vertices() {
        return vertices.values();
    }

    @Override
    public void addEdge(String source, String destination, int weight, String id) {

    }

    public int size() {
        return vertices.size();
    }

    private void resetVertices() {
        for (Vertex v : vertices.values()) {
            v.unvisit();
            v.setCost(Double.MAX_VALUE);
            v.setParent(null);
        }
    }
    public HashMap<String, Vertex> getVertices() {
        return vertices;
    }

}

