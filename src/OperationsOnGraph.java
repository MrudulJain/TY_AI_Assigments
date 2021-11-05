import java.util.*;
import java.util.List;

public class OperationsOnGraph
{
    public static class Edge
    {
        int src, dest;
        Edge(int src, int dest)
        {
            this.src = src;
            this.dest= dest;
        }
    }
    public static class Graph
    {
        List<List<Integer>> adjList;
        List<Integer> inDegree; //Only for directed edges
        int numberOfVertices;
        int numberOfEdges;

        Graph(List<Edge> edgeList, int numberOfEdges, int numberOfVertices, int check)
        {
            this.numberOfEdges = numberOfEdges;
            this.numberOfVertices = numberOfVertices;
            adjList = new ArrayList<>();
            inDegree = new ArrayList<>();

            for(int i = 0; i <=numberOfVertices; i++){
                adjList.add(new ArrayList<>()); // adding a row in adjacency List for each vertex
                inDegree.add(0);
            }
            if(check == 0) // undirected
            {
                for (Edge edge : edgeList) {
                    /* if edges' source and destination vertices are not put in consecutively starting from 0,
                    this statement will give index out of bounds exception */
                    adjList.get(edge.src).add(edge.dest);
                    adjList.get(edge.dest).add(edge.src);
                }
            }
            else if(check == 1) // directed
            {
                for (Edge edge : edgeList) {
                   /* if edges' source and destination vertices are not put in consecutively starting from 0,
                    this statement will give index out of bounds exception */
                    adjList.get(edge.src).add(edge.dest);
                    inDegree.set(edge.dest, edge.dest + 1); //modifying inDegree of each vertex
                }
            }
        }

        /** UTILITY FUNCTIONS **/

        void dfs(int curr_node, Boolean[] visited)
        {
            if(visited[curr_node])
            {
                return;
            }
            visited[curr_node] = true;
            System.out.print(curr_node +"-->");
            for(Integer child : adjList.get(curr_node))
            {
                dfs(child, visited);
            }
        }

        void bfs(Boolean[] visited, int start)
        {
            Queue <Integer> queue = new LinkedList<>();
            queue.add(start);
            visited[start] = true;
            while (!queue.isEmpty())
            {
                System.out.print(queue.peek() + "-->");
                int temp = queue.remove();
                for (Integer child : adjList.get(temp))
                    if(!visited[child])
                    {
                        queue.add(child);
                        visited[child] = true;
                    }
            }
            System.out.print("null");
        }

        boolean checkIfBipartite(int start, Boolean[] visited, boolean[] colour)
        {
            //colours --> black and white represented by 1 & 0
            for(Integer child : adjList.get(start))
            {
                if(!visited[child])
                {
                    colour[child] = !colour[start]; // colouring the child
                    visited[child] = true;
                    if(!checkIfBipartite(child, visited, colour))
                        return false;
                }
                else if(colour[start] == colour[child])
                    return false;
            }
            return true;
        }

        boolean checkIfDAG()
        {
            return true;
        }
    }

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the no.of Vertices in the graph");
        int numberOfVertices = sc.nextInt();
        System.out.println("Enter the no.of Edges in the graph");
        int numberOfEdges = sc.nextInt();
        System.out.println("If the graph is undirected enter 0, and if it is directed enter 1");
        int choice = sc.nextInt();
        while(choice != 1 && choice != 0)
        {
            System.out.println("Wrong Input");
            choice = sc.nextInt();
        }
        List<Edge> edgeList = new ArrayList<>();
        System.out.println("Enter the source and destination vertex for each edge. Start with Source = 0 and enter consecutive integers");
        for(int i =0; i<numberOfEdges; i++)
        {
            System.out.print("Source:");
            int src = sc.nextInt();
            System.out.print("Destination:");
            int dest = sc.nextInt();
            edgeList.add(new Edge(src, dest));
            // ALT --> edgeList.add(new Edge(scanner.nextInt(), scanner.nextInt()));
        }
        Graph graph = new Graph(edgeList, numberOfEdges, numberOfVertices, choice);
        Boolean[] visited = new Boolean[numberOfVertices];
        Arrays.fill(visited, false); // initializing all elements in boolean array to false

        /************************************************************************************************/
        // MAIN MENU
        boolean validChoice = false;
        boolean result;
        while (!validChoice) {
            Arrays.fill(visited, false);
            System.out.println("\n---------------------");
            System.out.println("Menu");
            System.out.println("---------------------");
            System.out.println("Enter any of the following options");
            //System.out.println("0.Exit \n1.DFS \n2.BFS \n3.Check If Bipartite \n4.Check if DAG \n5.Check If Tree");
            System.out.println("0.Exit \n1.DFS \n2.BFS \n3.Check If Bipartite");
            System.out.println("---------------------");
            System.out.print("Your selection: ");
            int option = sc.nextInt();
            switch (option) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    System.out.println("DFS Of Created Graph:");
                    for (int i = 0; i < numberOfVertices; i++) {
                        if (!visited[i]) {
                            graph.dfs(i, visited);
                            System.out.print("\n");
                        }
                    }
                    // validChoice = true;
                    break;
                case 2:
                    System.out.println("BFS Of Created Graph:");
                    graph.bfs(visited, edgeList.get(0).src);  // passing the first vertex in the edge List
                    // validChoice = true;
                    break;
                case 3:
                    boolean[] colour = new boolean[numberOfVertices]; // value defaults to false
                    result = graph.checkIfBipartite(0, visited, colour);
                    if(result) {System.out.println("The graph is Bipartite");}
                    else {System.out.println("The graph is NOT Bipartite");}
                    // validChoice = true;
                    break;
                case 4:
                    if(choice == 0)
                    {
                        System.out.println("Graph is Undirected, hence its not DAG");
                        break;
                    }
                    result = graph.checkIfDAG();
                    if(!result)
                        System.out.println("Graph is NOT DAG");
                    else System.out.println("Graph is DAG");
                    //validChoice = true;
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Incorrect option selected, please enter the correct statement");
                    break;
            }
        }
        sc.close();
    }
}
