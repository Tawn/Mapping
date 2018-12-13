package graphing;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Graph = (V, E)
 * @author Thanh Kha
 *
 * The graph will connect all the provided locations with roads
 * Vertices: locations
 * Edges: roads that connect two locations.
 * 
 */
public class Graph {
	
	private Map <String, Node> nodes;
	private Map <String, Edge> edges;
	private Map <String, ArrayList<String>> adjacency;
	private Map <String, Double> weights; // edge weight for "loc1 + loc2"
	private double[] dimension;
	
	// Constructor
	public Graph(String filename) {
		nodes = new HashMap<>();
		edges = new HashMap<>();
		adjacency = new HashMap<>();
		weights = new HashMap<>();
		dimension = new double[4]; // left, right, top, bottom
		read(filename);

	}
	
	// Read in File and add to edges and vertices
	public void read(String filename) {
		// We want to read in the txt files
		BufferedReader file;
		
		// Scaling for mapping
		double left = Double.MAX_VALUE; double right = Integer.MIN_VALUE; 
		double top = Double.MAX_VALUE; double bottom = Double.MIN_VALUE;
		
		// Provide an adjacency list to each mapped nodes
		ArrayList<String> adjNode = new ArrayList<>();
		
		try {	
		file = new BufferedReader(new FileReader(filename));
		// Read and Parse Lines
		String line = file.readLine();
		while(line != null) {
			String [] parse = line.split("\t");
			String type = parse[0];
			String id = parse[1];
			
			// Creating vertex (node) of new location
			if(type.equals("i") && !nodes.containsKey(id)) {
				Double longitude = Double.parseDouble(parse[2]);
				Double latitude = Double.parseDouble(parse[3]);
				Node node = new Node(id, longitude, latitude);
				nodes.put(id, node);
				
				// Adjacency List
				if(!adjacency.keySet().contains(id))
					adjacency.put(id, new ArrayList<>());
				
			// Otherwise edges (edge) for new roads
			} else if (type.equals("r") && !edges.containsKey(id)){ 
				String loc1 = parse[2];
				String loc2 = parse[3];
				
				// Caluclating the distance of edge (road)
				double x1 = nodes.get(loc1).getLatitude();
				double x2 = nodes.get(loc2).getLatitude();
				double y1 = nodes.get(loc1).getLongitude();
				double y2 = nodes.get(loc2).getLongitude();
				double x = Math.pow(x2-x1, 2);
				double y = Math.pow(y2-y1, 2);
				double distance = Math.sqrt(x + y);

				// Min/Max Latitude and Longitudes
				if(x1 < left) { left = x1; } // Leftmost
				if(x2 < left) { left = x2; }
				if(x1 > right) { right = x1;} // Rightmost
				if(x2 > right) { right = x2;}
				if(y1 < top) { top = y1; } // Top
				if(y2 < top) { top = y2; }
				if(y1 > bottom) { bottom = y1; } // Bottom
				if(y2 > bottom) { bottom = y2; }
				
				// Add new edge to HashMap
				Edge edge = new Edge(id, loc1, loc2, distance);
				edges.put(id, edge);
				
				ArrayList<String> a1 = adjacency.get(loc1);
				ArrayList<String> a2 = adjacency.get(loc2);

				if(!a1.contains(loc2)) {
					a1.add(loc2);
					adjacency.put(loc1, a1);
				}
				if(!a2.contains(loc1)) {
					a2.add(loc1);
					adjacency.put(loc2, a2);				}
			}
			// Get next line
			line = file.readLine();
		} // end of while
		file.close();	
		dimension[0] = left; dimension[1] = right; 
		dimension[2] = top; dimension[3] = bottom;
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
	
	public Map<String, Edge> getEdges() {
		return edges;
	}
	public Map<String, Node> getNodes() {
		return nodes;
	}
	public Map<String, ArrayList<String>> getAdjacencyList() {
		return adjacency;
	}
	public Map<String, Double> getWeights() {
		return weights;
	}
	public double[] getDimension() {
		return dimension;
	}

}
