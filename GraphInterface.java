import java.util.Stack;

public interface GraphInterface {
    void addEdge(String source, String destination, int weight, String id);

    int size();

    void print();
    void quickestPathFind(String origin, String destination);
    void leastTransfers(String origin, String destination);


}