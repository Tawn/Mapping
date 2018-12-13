package graphing;

/**
 * Edge (Roads) between two nodes (locations)
 * @author Thanh Kha
 *
 * Two locations are connected with an edge (road)
 */

public class Edge {
	
	private String loc1, loc2;
	private String rID;
	private double distance;
	
	public Edge(String rID, String loc1, String loc2, Double distance) {
		this.loc1 = loc1;
		this.loc2 = loc2;
		this.rID = rID;
		this.distance = distance;
	}

	public String getLoc1() {
		return loc1;
	}

	public String getLoc2() {
		return loc2;
	}

	public String getrID() {
		return rID;
	}
	
	public Double getDistance() { 
		return distance;
	}
	
}
