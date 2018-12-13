package graphing;

/**
 * Node (Locations)
 * @author Thanh Kha
 *
 * Each of the vertices will be the provided intersection/road
 * followed by their location using longitutde and latitude 
 */

public class Node {
	
	// Longitutde and Lattitudes per location provided
	private double longitude, latitude, x, y;
	private String ID;
	
	// Constructor 
	public Node(String ID, double longitude, double latitude) {
		this.ID = ID; 
		this.longitude = longitude;
		this.latitude = latitude;
		// Map Coordinates
		this.x = 0.0; 
		this.y = 0.0;
	}
	
	// Getter function of each vertex
	public String getID() {
		return this.ID;
	}
	
	public double getLongitude() {
		return this.longitude;
	}
	
	public double getLatitude() {
		return this.latitude;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	public double getX() {
		return this.x;
	}
	public double getY() {
		return this.y;
	}

}
