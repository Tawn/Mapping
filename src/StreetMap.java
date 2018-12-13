/**
 * Main Method 
 * @author Thanh Kha
 *
 * This class will initialize the graph with the following
 * input file. Then display the content with the canvas (JPanel)
 * and JFrame.
 * 
 */

import javax.swing.JFrame;

import graphics.Canvas;
import graphing.Graph;

public class StreetMap {
	
	public static void main(String[] args) {

		if(args[0].equals("ur.txt") || args[0].equals("monroe.txt") || args[0].equals("nys.txt")) {
			String filename = "res/" + args[0];
			Graph g = new Graph(filename);
			
			// Setup Map
			Canvas map = new Canvas(g, args);
			
			// JFrame setup
			JFrame f = new JFrame(filename);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setSize(800, 800);
			f.add(map);
			f.setVisible(true);
			
		} else { 
			System.out.println("Need valid inputs");
		}
	}

}
