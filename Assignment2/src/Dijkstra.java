import java.io.FileNotFoundException;
import java.util.ArrayList;


public class Dijkstra {
	/**
	 * args[0] = filename
	 * args[1] = startnode
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws NumberFormatException 
	 */
	
	public static void main(String[] args) throws NumberFormatException, FileNotFoundException{
			AdjacencyMatrix matrix = new AdjacencyMatrix(false, args[0]);
			System.out.println("Matrix ok");
			new Dijkstra().findPath(matrix, Integer.parseInt(args[1]));
			
	}
	//using Dijkstra's algorithm
	public void findPath(AdjacencyMatrix matrix, int startNode){
		DijkstraNode[] nodes = new DijkstraNode[matrix.nodeCount()];
		ArrayList<Integer> path = new ArrayList<Integer>();//N'
		path.add(startNode);//step 2
		int totalCost = 0;
		
		for(int i = 0;i<nodes.length;i++){//step 3-7
			nodes[i] = new DijkstraNode(i);
			int cost = matrix.cost(startNode, i);
			cost = cost<=0?Integer.MAX_VALUE:cost;
			nodes[i].cost = cost;
			nodes[i].predecessor = startNode;
			System.out.println(nodes[i].index + " costs " + nodes[i].cost);
		}
		
		//step 8
		while(path.size() < matrix.nodeCount()) {
			System.out.println("\n\nStep " + path.size());
			int minCost = Integer.MAX_VALUE;
			int nodeIndex = -1;
			//step 9
			
			for(DijkstraNode n : nodes) {
				if(n.cost < minCost && !path.contains(n.index)) {
					minCost = n.cost;
					nodeIndex = n.index;
				}
				System.out.print(n.index+":"+n.cost+","+n.predecessor+"\t");
			}
			System.out.println();
			//step 10
			path.add(nodeIndex);
			totalCost += matrix.cost(nodes[nodeIndex].predecessor, nodeIndex);
			System.out.println("Added " + nodeIndex);
			//step 11
			for(DijkstraNode n : nodes) {
				if(!path.contains(n.index)) {
					int newCost = matrix.cost(nodeIndex, n.index);
					if(newCost != 0) {
						newCost += minCost;
					} else {
						newCost = Integer.MAX_VALUE;
					}
					if(newCost < n.cost) {
						n.cost = newCost;
						n.predecessor = nodeIndex;
						System.out.println(n.index + " now costs " + n.cost);
					}
				}
			}
		}
		System.out.print("\n\nPath is: ");
		for(Integer i : path) {
			System.out.print(i + " ");
		}
		System.out.println("total Cost: " + totalCost);
	}
	
	private class DijkstraNode {
		final int index;
		int cost;
		int predecessor;
		public DijkstraNode(int index) {
			this.index = index;
		}
	}
	
	/*
		c(x,y): link cost from node x to y;  = ∞ if not direct neighbors
		D(v): current value of cost of path from source to dest. v
		p(v): predecessor node along path from source to v
		N': set of nodes whose least cost path definitively known
		1  Initialization: 
		2    N' = {u} 
		3    for all nodes v 
		4      if v adjacent to u 
		5          then D(v) = c(u,v) 
		6      else D(v) = ∞ 
		7 
		8   Loop 
		9     find w not in N' such that D(w) is a minimum 
		10    add w to N' 
		11    update D(v) for all v adjacent to w and not in N' : 
		12       D(v) = min( D(v), D(w) + c(w,v) ) 
		13     //new cost to v is either old cost to v or known 
		14     //shortest path cost to w plus cost from w to v 
		15  until all nodes in N' 
	 */
}
