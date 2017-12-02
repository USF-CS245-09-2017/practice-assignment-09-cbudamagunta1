import java.util.ArrayList;

public class GraphAdjMatrix implements Graph{

//////////////////////////////////////////////////////////////	
	
	class Vertex{
		Object value;
		Vertex connection;
		
		public Vertex() {
			value = null;
			connection = null;
		}
	}

//////////////////////////////////////////////////////////////

	int[][]edges;
	Vertex[] vertices;

//////////////////////////////////////////////////////////////

	public GraphAdjMatrix(int size) {
		edges = new int[size][size];
		vertices = new Vertex[size];
		
		for(int i=0; i<size; i++) {
			vertices[i] = new Vertex();
			vertices[i].value = new Integer(i);
			
			for(int j=0; j<size; j++) {
				edges[i][j] = -1;
			}
			
		}
	}

//////////////////////////////////////////////////////////////

	public void addEdge(int v1, int v2) {
		edges[v1][v2] = 1;
	}
	
//////////////////////////////////////////////////////////////

	public void addEdge(int v1, int v2, int weight) {
		edges[v1][v2] = weight;
	}
	
//////////////////////////////////////////////////////////////
	
	public void clearEdges() {
		for(int i=0; i<vertices.length; i++) {
			for(int j=0; j<vertices.length; j++) {
				edges[i][j] = -1;
			}
		}
	}
	
//////////////////////////////////////////////////////////////

	public int getEdge(int v1, int v2) {
		return edges[v1][v2];
	}
	
//////////////////////////////////////////////////////////////
	
	public int[] neighbors(int vertex) {
		
		int[] neighbors = new int[outDegree(vertex)];
		int position = 0;
		
		for(int i=0; i<edges.length; i++) {
			if(edges[vertex][i]!=-1) {
				neighbors[position] = i;
				position++;
			}
		}
		return neighbors;
	}

//////////////////////////////////////////////////////////////
	
	public int outDegree(int vertex) {
		int degree = 0;
		for(int i=0; i<edges.length; i++) {
			if(edges[vertex][i]!=-1) {
				degree++;
			}
		}
		return degree;
	}
	
	
	public int inDegree(int vertex) {
		int degree = 0;
		for(int i=0; i<edges.length; i++) {
			if(edges[i][vertex]!=-1) {
				degree++;
			}
		}
		return degree;
	}
	
//////////////////////////////////////////////////////////////

	public void topologicalSort() {
		
		int[] incident = new int[edges.length];
		for(int i=0; i<edges.length; i++) {
			incident[i] = inDegree(i);
		}
		
		ArrayList<Integer> sequence = new ArrayList<Integer>();
		
		for(int i=0; i<edges.length; i++) {
			
			int vertex = findZero(incident);
			
			if(vertex!=-1) {
				sequence.add(vertex);
				
				incident[vertex] = -1;
				
				for(int j=0; j<edges.length; j++) {
					if(edges[vertex][j]!=-1) {
						incident[j]--;
					}
				}
			}
		}
		
		for(int i=0; i<sequence.size(); i++) {
			System.out.println(sequence.get(i));
		}
		
	}
	
	public int findZero(int[] incident) {
		for(int i = 0; i<incident.length; i++) {
			if(incident[i]==0) {
				return i;
			}
		}
		return -1;
	}
	
//////////////////////////////////////////////////////////////
	
	//Prim's Algorithm
	public int createSpanningTree() {

		//Condition variables
		int next = 0;
		int minCost = 10000000;
		int totalUnknown = vertices.length;
		
		//Keep track of all three arrays
		boolean[] known = new boolean[vertices.length];
		int[] cost = new int[vertices.length];
		int[] path = new int[vertices.length];
		
		//Initialize all three arrays
		for(int m=0; m<vertices.length; m++) {
			known[m] = false;
			cost[m] = -1;
			path[m] = -1;
		}
		
		//Starting at zero
		known[0] = true;
		cost[0] = 0;
		path[0] = 0;
		
		//Use counter to keep track of how many are known, then when all nodes are known end the condition
		while(totalUnknown>0) {
			
			known[next] = true;
			totalUnknown--;
			
			for(int j=0; j<edges[next].length; j++) {
				if(edges[next][j]!=-1) {
					//If vertex is not already known
					if(known[j] == false) {
						cost[j] = edges[next][j];
						path[j] = next;
					}
					//If vertex is known already, check if this new connection is lower
					if(edges[next][j]<cost[j]) {
						cost[j] = edges[next][j];
						path[j] = next;
					}
				}
			}
			
			//Find next lowest cost
			for(int l=0; l<vertices.length; l++) {
				if(!known[l]) {
					if(cost[l]<minCost && cost[l] != -1) {
						minCost = cost[l];
						next = l;
					}
				}
			}	
		}
		
		//Return the overall cost
		int sum = 0;
		clearEdges();
		for(int k=0; k<vertices.length; k++) {
			addEdge(path[k], k, cost[k]);
			sum+=cost[k];
		}
		return sum;
	}

//////////////////////////////////////////////////////////////
	
}
