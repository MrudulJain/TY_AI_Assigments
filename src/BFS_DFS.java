import java.util.*;

public class BFS_DFS
{
        public static class Edge
        {
            int src;
            int dest;
            int hval;   // heuristic value

            Edge(int src, int dest, int hval)
            {
                this.src = src;
                this.dest = dest;
                this.hval = hval;
            }
        }

        public static class Graph
        {
            List<List<Integer>> adjList = new ArrayList<>();
            int numberOfEdges, numberOfVertices;
            Graph(int e, int v, List<Edge> edgeList) {
                this.numberOfEdges = e;
                this.numberOfVertices = v;
                for (int i = 0; i < v; i++)
                    adjList.add(new ArrayList<>()); // adding a row in adjacency List for each vertex

                for (Edge edge : edgeList) {
                    adjList.get(edge.src).add(edge.dest);
                    adjList.get(edge.dest).add(edge.src);
                }
            }

            /** DFS, BFS IMPLEMENTATION BEGINS HERE **/
            void dfs(boolean visited[],int curr_node)
            {
                if(visited[curr_node] == true)
                    return;

                visited[curr_node] = true;
                System.out.print(curr_node +" ");

                for(Integer adjNode : adjList.get(curr_node))
                    dfs(visited, adjNode);
            }

            void bfs(boolean visited[], int startNode)
            {
                LinkedList<Integer> queue = new LinkedList<>();
                queue.add(startNode);
                visited[startNode] = true;

                while (!queue.isEmpty()) {
                    int node = queue.poll(); //  returns the element at the head of the Queue. It returns null when the Queue is empty.
                    System.out.print(node +" ");
                    for (Integer e : adjList.get(node)) {
                        if(!visited[e]) {
                            queue.add(e);
                            visited[e] = true;
                        }
                    }
                }
            }
        }

        public static void main(String[] args) {
            int numberOfEdges, numberOfVertices;
            int src, dest, hval;
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter the no of vertices in the graph: ");
            numberOfVertices = sc.nextInt();
            System.out.print("Enter the no of edges in the graph: ");
            numberOfEdges = sc.nextInt();
            List<Edge> edgeList = new ArrayList<>(numberOfEdges);
            for (int i = 0; i < numberOfEdges; i++)
            {
                System.out.print("Enter source vertex (Make sure it starts with 0 and goes in ascending order): ");
                src = sc.nextInt();
                System.out.print("Enter destination vertex: ");
                dest = sc.nextInt();
                System.out.print("Enter heuristic value of the edge: ");
                hval = sc.nextInt();
                edgeList.add(new Edge(src, dest, hval));
            }

            Graph graph = new Graph(numberOfEdges, numberOfVertices, edgeList);
            boolean[] visited = new boolean[numberOfVertices];

            // DFS Function Call
            System.out.println("DFS of Given Graph is:");
            for (int i=0; i < numberOfVertices; i++)
                graph.dfs(visited, i);
            //BFS Function Call
            System.out.println("\nBFS of Given Graph is:");
            Arrays.fill(visited, false);
            for(int i=0; i <numberOfVertices; i++)
            {
                if(visited[i] == false)
                    graph.bfs(visited,i);
            }

        }
}
