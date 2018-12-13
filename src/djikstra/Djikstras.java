package djikstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import graphing.Graph;
import graphing.Node;

/**
 * Djikstra's Algorithm
 * @author Tkha
 * 
 * This class will take in a graph and return all shortest path 
 * from a given start location to all other locations. The shortest 
 * path takes the shortest accumulated route of its neighbor until
 * it reaches its destination. 
 * 
 * Taking in a graph and outputting a mapping of parents 
 * from end to start. 
 *
 */

public class Djikstras {
	private Map <String, Node> nodes;
	private Map <String, Double> weights;
	private Map <String, ArrayList<String>> adjacency;
	
	private Map <String, Boolean> visited = new HashMap<>();
	private Map <String, Double> value = new HashMap<>();
	private Map <String, String> parent = new HashMap<>();
		
	public Djikstras(Graph graph) {
		nodes = graph.getNodes();
		weights = graph.getWeights();
		adjacency = graph.getAdjacencyList();
		
	}
	
	public Map <String, String> shortestPath(String u, String dest) {
		visited = new HashMap<>();
		value = new HashMap<>();
		// Initialize variables
		for(String s : nodes.keySet()) {
			visited.put(s, false);
			value.put(s, Double.MAX_VALUE);
		}
		
		visited.put(u, true);
		value.put(u, 0.0);
		djikstra(u, dest);
		return parent;
	}
	private PriorityQueue <Double> q;
	private Map<Double, String> q2;
	public void djikstra(String u, String dest) {
		q = new PriorityQueue<>();
		q2 = new HashMap<>();
		String cur = u;
				
		while (visited.get(dest) == false) {
			visited.put(cur, true);
			for(String s : adjacency.get(cur)) { // out-neighbors String ArrayList
				if(visited.get(s) == false) {
					double curVal = value.get(cur);
					double destVal = value.get(s); 
					double edgeWeight = weights.get(cur + s);
					
					if(curVal + edgeWeight < destVal) {
						q.add(curVal + edgeWeight); // add elem to queue
						q2.put(curVal + edgeWeight, s); // map elem
						value.put(s, curVal + edgeWeight);
						parent.put(s, cur);
					}
				}
			}// end of for
			// get next elem
			Double next = q.poll();
			cur = q2.get(next);
			
		} // end of while
		
	}
	
	
 
}
