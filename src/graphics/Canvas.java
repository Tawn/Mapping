package graphics;

/**
 * Canvas (GUI)
 * @author Thanh Kha
 *
 * The canvas class primarily takes the given graph with 
 * the given edges, edge weights, nodes, and adj list then
 * displays the following content using formulas shown below.
 * 
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JPanel;

import djikstra.Djikstras;
import graphing.Edge;
import graphing.Graph;
import graphing.Node;

public class Canvas extends JPanel {
	private static final long serialVersionUID = 1L;
	private Graph graph;
	private Map <String, Node> nodes;
	private Map <String, Edge> edges;
	private Map <String, Double> weights;
	private Map <String, ArrayList<String>> adjacency;
	private Map<String, String> parent;
	private String[] args;
	
	// Constructor
	public Canvas(Graph g, String[] args) {
		this.graph = g;
		this.args = args;
		nodes = graph.getNodes();
		edges = graph.getEdges();
		weights = graph.getWeights();
		adjacency = graph.getAdjacencyList();
		this.setBackground(Color.BLACK);
	}
	
	// Drawing
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setMap(g);
	
		switch(args.length) {
		case 2: // 1. file.txt 2. --show
			if (args[1].equals("--show")) {
				drawMap(g);
				break;
			}
		case 4:// 1. file.txt 2. --directions 3. start 4. end
			if(args[1].equals("--directions") && nodes.containsKey(args[2]) && nodes.containsKey(args[3])) {
				drawMap(g);
				drawPath(g, args[2], args[3]);
				break;
			}
		case 5: // 1. file.txt 2. --show 3. --directions 4. start 5. end
			if(args[2].equals("--directions") && nodes.containsKey(args[3]) && nodes.containsKey(args[4])) {
				drawMap(g);
				drawPath(g, args[3], args[4]);
				break;
			}
		default:
			System.out.println("Invalid Input");
			break;
		}

	}
	
	
	// haversine algorithm
	private double distance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(Math.toRadians(lat1)) * 
					  Math.sin(Math.toRadians(lat2)) + 
					  Math.cos(Math.toRadians(lat1)) * 
					  Math.cos(Math.toRadians(lat2)) * 
					  Math.cos(Math.toRadians(theta));
		dist = Math.acos(dist);
		dist = Math.toDegrees(dist);
		dist = dist * 60 * 1.1515;
		return (dist);
		
	}
	
	public void drawPath(Graphics g, String start, String end) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.RED);
		
		Djikstras algorithm = new Djikstras(graph);	
		parent = algorithm.shortestPath(start, end); // Start
		
		String cur = end; // Destination
		String next = parent.get(cur);
		String output = end;
		double dim = getWidth()/90;
		
		// Starting location mark
		g2d.fill(new Ellipse2D.Double(nodes.get(cur).getX() - 
																	dim/2, nodes.get(cur).getY() - 
																	dim/2, 
																	dim, 
																	dim));
		double distance = 0;
		
		// Get Path with distance (miles)
		while(next != null) {
			output = next + " -> " + output;
			
			// Next two intersections
			Node node1 = nodes.get(cur);
			Node node2 = nodes.get(next);
			
			// Distance
			distance += distance(node1.getLatitude(), node1.getLongitude(),
													 node2.getLatitude(),node2.getLongitude());
			
			g2d.draw(new Line2D.Double(node1.getX(), node1.getY(), node2.getX(), node2.getY()));
			cur = next;
			next = parent.get(next);
		}
			// Destination mark
			g2d.fill(new Ellipse2D.Double(nodes.get(cur).getX() - 
																		dim/2, nodes.get(cur).getY() - 
																		dim/2, 
																		dim, 
																		dim));
			
			// Print path and distance
			System.out.println("Shortest Path: " + output);
			System.out.println("distance: " + distance + " miles");
	}
	
	// Map Drawing
	public void drawMap(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.WHITE);
		
		// Set Locations
		for(String s : nodes.keySet()) {
			Node current = nodes.get(s);
			g2d.draw(new Ellipse2D.Double(current.getX(), current.getY(), 1, 1));
		}
		
		// Set Roads
		for(String s : edges.keySet()) {
			Edge current = edges.get(s);
			Node node1 = nodes.get(current.getLoc1());
			Node node2 = nodes.get(current.getLoc2());
			g2d.draw(new Line2D.Double(node1.getX(), node1.getY(), node2.getX(), node2.getY()));
		}	
		
	}
	
	// Draw Map and setup intersection and roads (nodes and edges)
	public void setMap(Graphics g) {
		double[] dim = graph.getDimension();
		double left = dim[0]; double right = dim[1];
		double top = dim[2]; double bottom = dim[3];
	
		for(String s : nodes.keySet()) {
			Node current = nodes.get(s);
			
			/**
			 * @author Thanh 
			 * This formula does:
			 * 
			 * distX = sqrt( (current latitude - minimum latitude)^2 )/
			 * 				 sqrt( (maximum latitude - minimum latitude)^2 ) * Frame Width
			 * distY = sqrt( (current longitude - minimum longitude) ^2 )/
			 * 				 sqrt( (maximum longitude - minimum longitude) ^2 ) * Frame Height
			 * 
			 * Which gives us the scaling ratio of the of the JFrame.
			 */
			
			// Latitude
			double curX = current.getLatitude();
			double distX = Math.sqrt(Math.pow(curX - left, 2))/
										 Math.sqrt(Math.pow(right - left, 2));
			distX *= getWidth();
			
			// Longitutde
			double curY = current.getLongitude();
			double distY = Math.sqrt(Math.pow(curY - bottom, 2))/
					 					 Math.sqrt(Math.pow(top - bottom, 2));
			distY *= getHeight();
			current.setX(distX); current.setY(distY);
		}
		
		for(String s : edges.keySet()) {
			Edge current = edges.get(s);
			Node node1 = nodes.get(current.getLoc1());
			Node node2 = nodes.get(current.getLoc2());
			
			// Add edge weights
			double distance = Math.pow(node2.getX() - node1.getX(), 2) 
											+ Math.pow(node2.getY() - node2.getX(), 2);
			distance = Math.sqrt(distance);
			
			// Draw Lines
			weights.put(node1.getID() + node2.getID(), distance);
			weights.put(node2.getID() + node1.getID(), distance);
		}
		
	}
	
}
