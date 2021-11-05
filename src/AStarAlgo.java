import java.util.*;

public class AStarAlgo
{
    public static class Node
    {
        String id;
        int hval; // heuristic value
        List<Edge> edgeList; // list which stores all edges starting from current node
        Node parent = null; //predecessor of current node
        public int f = Integer.MAX_VALUE;
        public int g = Integer.MAX_VALUE;

        Node(String id, int hval)
        {
            this.id = id;
            this.hval = hval;
            this.edgeList = new ArrayList<>(); // every new node will have its own list of edges
        }

        public static class Edge
        {
            int weight;
            Node nextNode; // this is the node which the edge is connecting the original node to

            Edge(int weight, Node nextNode)
            {
                this.weight = weight;
                this.nextNode = nextNode;
            }
        }

        void addEdge(int weight, Node node)
        {
            Edge newEdge = new Edge(weight, node);
            edgeList.add(newEdge);
        }

    }

    public static void main(String[] args)
    {
        int i;
        Scanner scanner = new Scanner(System.in);
        Map<String, Node> map = new HashMap<>();

        System.out.print("Enter the number of Nodes in the graph: ");
        int numberOfNodes = scanner.nextInt();
        for(i=0; i<numberOfNodes; i++)
        {
            System.out.printf("Enter Name of node %d : ", i+1);
            String id = scanner.next();
            System.out.print("Enter the heuristic value of the node: ");
            int hval = scanner.nextInt();
            Node newNode = new Node(id, hval);
            map.put(id, newNode);
        }

        System.out.print("Enter the number of Edges in the graph: ");
        int numberOfEdges = scanner.nextInt();
        System.out.println("Enter Edge info");
        for(i=0; i<numberOfEdges; i++)
        {
            System.out.print("Enter Name of Source Vertex: ");
            String src = scanner.next();
            System.out.print("Enter Name of Destination Vertex: ");
            String dest = scanner.next();
            System.out.print("Enter weight of the edge: ");
            int weight = scanner.nextInt();
            Node srcNode = map.get(src);
            Node destNode = map.get(dest);
            srcNode.addEdge(weight, destNode);
        }

        System.out.print("Enter Name of the Start Vertex for A* : ");
        String src = scanner.next();
        System.out.print("Enter Name of the Final Vertex for A* : ");
        String dest = scanner.next();
        Node srcNode = map.get(src);
        Node destNode = map.get(dest);
        Node rNode = AStar(srcNode, destNode);
        printPath(rNode);
    }

    public static Node AStar(Node start, Node target)
    {
         /* f(n) = g(n) + h(n)
            cost function f(n) = move function g(n) + heuristic function h(n)
            move function g(n) = total cost it took us to get to the nth node from start node (without adding the heuristic value of the nth node)
         */

        Queue<Node> closedList = new LinkedList<>();
        Queue<Node> openList = new LinkedList<>();
        start.g = 0;
        target.hval = 0;
        start.f = start.g + start.hval;
        openList.add(start);

        while(!openList.isEmpty())
        {
            Node currNode = openList.peek(); //getting first element from queue
            if(currNode == target)
                return currNode;

            for(Node.Edge edge : currNode.edgeList)
            {
                Node nextNode = edge.nextNode; //getting node adjacent to current node

                if(!openList.contains(nextNode) && !closedList.contains(nextNode)) {
                    nextNode.parent = currNode;
                    nextNode.g = currNode.g + edge.weight;
                    nextNode.f = nextNode.g + nextNode.hval;
                    openList.add(nextNode);
                }
                else {
                    if(currNode.g + edge.weight < nextNode.g) {
                        nextNode.parent = currNode;
                        nextNode.g = currNode.g + edge.weight;
                        nextNode.f = nextNode.g + nextNode.hval;

                        if(closedList.contains(nextNode)) {
                            closedList.remove(nextNode);
                            openList.add(nextNode);
                        }
                    }
                }
            }
            openList.remove(currNode);
            closedList.add(currNode);
        }

        return null;
    }

    public static void printPath(Node target)
    {
        Node n = target;
        if(n == null)
        {
            System.out.println("No valid path found");
            return;
        }

        System.out.print("The shortest path is: ");
        List<String> ids = new ArrayList<>();

        while(n.parent != null)
        {
            ids.add(n.id);
            n = n.parent;
        }
        ids.add(n.id);
        Collections.reverse(ids);

        for(String id : ids)
            System.out.print(id + " ");

        System.out.print("\nTotal cost is: " +target.g);
    }
}
/*      Printing to check if all nodes have correctly been entered
        for(Integer test : map.keySet())
        {
            System.out.println(test +" "+ map.get(test).id +" "+ map.get(test).hval);
            for(Node.Edge edge : map.get(test).edgeList)
            {
                System.out.println("EdgeList : ");
                System.out.println(edge.weight +" "+ edge.nextNode.id);
            }
        }
*/