// PA1 Skeleton Code
// DSA2, spring 2026

// This code will read in the input, and put the values into lists.  It is up
// to you to properly represent this as a graph -- this code only reads in the
// input properly.


import java.util.*;

class Pair {
	public String s, t;
	Pair(String s, String t) { this.s=s; this.t=t; }
	public String toString() { return s + "->" + t; }
}

public class PA1 {
    /**
     * Method building the adjacency list given nodes and edges in a graph.
     *
     * @param nodes list of nodes
     * @param edges list of edges
     * @return adjacencyList
     */
    public static ArrayList<LinkedList<String>> buildAdjacencyList(ArrayList<String> nodes, ArrayList<Pair> edges) {
        /* adjacency list */
        ArrayList<LinkedList<String>> adjacencyList = new ArrayList<LinkedList<String>>();
        for (int j = 0; j < nodes.size(); j++) {
            /* create empty buckets */
            adjacencyList.add(new LinkedList<String>());
        }
        for (int j = 0; j < edges.size(); j++) {
            /* find the neighbors of every node by traversing the edges list */
            /* bidirectional so source -> target and target -> source are recorded */
            String source = edges.get(j).s;
            String target = edges.get(j).t;
            int indexOfSource = Integer.parseInt(source.substring(1));
            int indexOfTarget = Integer.parseInt(target.substring(1));
            adjacencyList.get(indexOfSource).add(target);
            adjacencyList.get(indexOfTarget).add(source);
        }
        return adjacencyList;
    }

    /**
     * Method doing a breadth-first search on a graph. Colors are used instead of boolean values. This implementation is largely the same as the pseudocode covered in class, except that instead of negative infinity, I used -1 here.
     *
     * @param adjacencyList a graph
     * @param src           node from which the BFS starts
     * @param colors        an array recording which node has been explored (this is necessary because there are graphs that are disconnected in test cases)
     * @param result        tree of result
     */
    public static void breadthFirstSearch(ArrayList<LinkedList<String>> adjacencyList, int src, Color[] colors, ArrayList<String> result) {
        /* now it's time to build the BFS tree! */
        int[] parent = new int[adjacencyList.size()];
        int[] distance = new int[adjacencyList.size()];
        /* Queue is abstract so I randomly chose to use LinkedList */
        Queue<String> exploreLater = new LinkedList<>();
        colors[src] = Color.GRAY;
        distance[src] = 0;
        parent[src] = -1;
        exploreLater.add("v" + src);
        while (exploreLater.size() > 0) {
            String current = exploreLater.poll();
            result.add(current);
            int indexOfCurrent = Integer.parseInt(current.substring(1));
            for (String str : adjacencyList.get(indexOfCurrent)) {
                int indexOfNeighbor = Integer.parseInt(str.substring(1));
                if (colors[indexOfNeighbor] == Color.WHITE) {
                    colors[indexOfNeighbor] = Color.GRAY;
                    distance[indexOfNeighbor] = distance[indexOfCurrent] + 1;
                    parent[indexOfNeighbor] = indexOfCurrent;
                    exploreLater.add(str);
                }
            }
            colors[indexOfCurrent] = Color.BLACK;
        }
    }

    /* portions inspired from https://www.geeksforgeeks.org/dsa/breadth-first-search-or-bfs-for-a-graph/ */

    /**
     * This method calls the above method to account for disconnected graph by going through the <code>adjacencyList</code> and see what nodes need to be explored.
     */
    public static ArrayList<String> breadthFirstSearchDisconnected(ArrayList<LinkedList<String>> adjacencyList) {
        int n = adjacencyList.size();
        Color[] colors = new Color[n];
        Arrays.fill(colors, Color.WHITE);
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (colors[i] == Color.WHITE) {
                breadthFirstSearch(adjacencyList, i, colors, result);
            }
        }
        return result;
    }

    /**
     * Method for querying. A query is similar to doing a BFS starting at a given node, but we stop when we reach the destination.
     * Hence the <code>if</code> block.
     *
     * @param adjacencyList
     * @param start
     * @param destination
     * @return
     */
    public static int[] breadthFirstSearchByVertexIndex(ArrayList<LinkedList<String>> adjacencyList, int start) {
        int n = adjacencyList.size();
        Color[] colors = new Color[n];
        Arrays.fill(colors, Color.WHITE);
        int[] parent = new int[n];
        int[] distance = new int[n];
        Arrays.fill(parent, -1);
        Arrays.fill(distance, -1);
        Queue<Integer> exploreLater = new LinkedList<>();
        colors[start] = Color.GRAY;
        distance[start] = 0;
        exploreLater.add(start);
        while (!exploreLater.isEmpty()) {
            int u = exploreLater.poll();
            for (String neighbor : adjacencyList.get(u)) {
                int v = Integer.parseInt(neighbor.substring(1));
                if (colors[v] == Color.WHITE) {
                    colors[v] = Color.GRAY;
                    parent[v] = u;
                    distance[v] = distance[u] + 1;
                    exploreLater.add(v);
                }
            }
            colors[u] = Color.BLACK;
        }
        return parent;
    }

	public static void main(String[] args) {

		Scanner stdin = new Scanner(System.in);
		int test_cases = stdin.nextInt();

		for ( int i = 0; i < test_cases; i++ ) {
			
			// read in n, e, and q
			int n = stdin.nextInt();
			int e = stdin.nextInt();
			int q = stdin.nextInt();

			// create the list of nodes
			ArrayList<String> nodes = new ArrayList<String>();
			for ( int j = 0; j < n; j++ )
				nodes.add("v"+j);

			// read in the edges
			ArrayList<Pair> edges = new ArrayList<Pair>();
			for ( int j = 0; j < e; j++ )
				edges.add(new Pair(stdin.next(),stdin.next()));

			// read in the queries
			ArrayList<Pair> queries = new ArrayList<Pair>();
			for ( int j = 0; j < q; j++ )
				queries.add(new Pair(stdin.next(),stdin.next()));

			System.out.println("test case " + i + ":");
			System.out.println("there are " + n + " nodes, " + e + " edges, and " + q + " queries");
			System.out.println("the nodes are: " + nodes);
			System.out.println("the edges are: " + edges);
			System.out.println("the queries are: " + queries);
			System.out.println("");

            // YOUR CODE HERE (or called from here)
            ArrayList<LinkedList<String>> adj = buildAdjacencyList(nodes, edges);
            int[][] bfsForest = new int[n][];
            ArrayList<String> bfs = breadthFirstSearchDisconnected(adj);
            Color[] colors = new Color[adj.size()];
            Arrays.fill(colors, Color.WHITE);
            System.out.print("[");
            for (int j = 0; j < adj.size(); j++) {
                if (j == adj.size() - 1) {
                    System.out.print("v" + j + ": " + adj.get(j).toString());
                } else {
                    System.out.print("v" + j + ": " + adj.get(j).toString() + " ");
                }
            }
            System.out.print("]");
            System.out.println("");
            System.out.println(bfs.toString());
            System.out.println("");
            /* now it's time for handling the query! */
            for (int j = 0; j < queries.size(); j++) {
                int source = Integer.parseInt(queries.get(j).s.substring(1));
                int destination = Integer.parseInt(queries.get(j).t.substring(1));
                /* TODO: run breadthFirstSearchByVertexIndex(source) and when == destination, stop and return how many steps counted and the next hop from source */
                if (bfsForest[source] == null) {
                    bfsForest[source] = breadthFirstSearchByVertexIndex(adj, source);
                }
                int[] queryStepsRecord = bfsForest[source];
                if (source == destination) {
                    System.out.println("v" + source + " " + "v" + destination + " 0 None");
                    continue;
                }
                if (source != destination && queryStepsRecord[destination] == -1) {
                    System.out.println("v" + source + " " + "v" + destination + " None None");
                } else {
                    int steps = 0;
                    int cur = destination;
                    while (queryStepsRecord[cur] != source) {
                        cur = queryStepsRecord[cur];
                        steps++;
                    }
                    steps++;
                    System.out.println("v" + source + " " + "v" + destination + " " + steps + " " + "v" + cur);
                }
            }
            System.out.println("");
            System.out.println("");
        }
    }
}

enum Color {
    BLACK, WHITE, GRAY
}