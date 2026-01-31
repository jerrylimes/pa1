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
    public static ArrayList<LinkedList<String>> buildAdjacencyList(ArrayList<String> nodes, ArrayList<Pair> edges) {
        /* adjacency list */
        ArrayList<LinkedList<String>> adjacencyList = new ArrayList<LinkedList<String>>();
        for (int j = 0; j < nodes.size(); j++) {
            /* create empty buckets */
            adjacencyList.add(new LinkedList<String>());
        }
        for (int j = 0; j < edges.size(); j++) {
            /* find the neighbors of every node by traversing the edges list */
            String source = edges.get(j).s;
            int indexOfSource = Integer.parseInt(source.substring(1));
            String target = edges.get(j).t;
            adjacencyList.get(indexOfSource).add(target);
        }
        return adjacencyList;
    }

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
            ArrayList<LinkedList<String>> adj =  buildAdjacencyList(nodes, edges);
            ArrayList<String> bfs = breadthFirstSearchDisconnected(adj);
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
        }
    }
}

enum Color {
    BLACK, WHITE, GRAY
}