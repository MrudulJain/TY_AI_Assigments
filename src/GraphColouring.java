import java.util.*;

public class GraphColouring
{
    static class Node
    {
        String name;
        List<Edge> edgeList;

        Node(String name)
        {
            this.name = name;
            this.edgeList = new ArrayList<>();
        }

        public static class Edge
        {
            Node nextNode;  // node which is connected to the current Node by the edge
            Edge(Node nextNode){
                this.nextNode = nextNode;
            }
        }

        void addEdge(Node NextNode)
        {
            Edge NewEdge = new Edge(NextNode);
            edgeList.add(NewEdge);
        }
    }

    private static final String[] colour = {
            "", "ORANGE", "GREEN", "RED", "BLUE", "YELLOW", "WHITE",
            "BLACK", "BROWN", "PINK", "PURPLE", "VIOLET"
    }; // change colours as you want

    public static void ColouringFun(Map<String, Node> nodeMap)
    {
        Map<String, Integer> result = new HashMap<>(); // Stores colour of each Node in Graph
        for(String CurrentNode : nodeMap.keySet())  // For each Node present in the graph
        {
            Set<Integer> takenColours = new TreeSet<>(); // Stores the colours that are already taken by adjacent vertices of current Node
            for (Node.Edge e : nodeMap.get(CurrentNode).edgeList) // Getting all edges from the edgeList of Current Node
            {
                Node nextNode = e.nextNode; // Getting the node connected to the edge
                if (result.containsKey(nextNode.name))
                    takenColours.add(result.get(nextNode.name));
            }

            // check for the first free color
            int colour = 1;
            for (Integer c: takenColours)
            {
                if (colour != c) {
                    break;
                }
                colour++;
            }
            // assign the first available colour to the CurrentNode
            result.put(CurrentNode, colour);
        }

        // Printing the Graph
        for(String CurrentNode : nodeMap.keySet())
        {
            System.out.println("The color assigned to Node " + CurrentNode + " is "
                    + colour[result.get(CurrentNode)]);
        }
    }

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        Map<String, Node> map = new HashMap<>();

        System.out.print("Enter the number of Nodes in the graph: ");
        int numberOfNodes = scanner.nextInt();
        for(int i=1; i<=numberOfNodes; i++)
        {
            System.out.printf("Enter Name of node %d : ", i);
            String name = scanner.next();
            Node newNode = new Node(name);
            map.put(name, newNode);
        }

        System.out.print("Enter the number of Edges in the graph: ");
        int numberOfEdges = scanner.nextInt();
        for(int i = 1; i <= numberOfEdges ; i++)
        {
            System.out.print("Enter the source vertex for Edge " +i+ ": ");
            String src = scanner.next();
            System.out.print("Enter the destination vertex for Edge " +i+ ": ");
            String dest = scanner.next();
            map.get(src).addEdge(map.get(dest)); // getting source node from map and then adding destination node to its edgeList
            map.get(dest).addEdge(map.get(src)); // for graph colouring, graph must be undirected, so that we can reference the colour of the previous node
        }
        ColouringFun(map);
    }
}
