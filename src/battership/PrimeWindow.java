package battership;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.*;


public class PrimeWindow {

	final static JFrame main = new JFrame("BanterShip");
	private static Component PrimPanel = PrimeWindow.createGrids();
	private static JPanel compCurrentShips = new JPanel();
	public static JLabel compCounter = new JLabel();

	public static void mainFrame(){
		JMenuBar bar = Menutab.mainMenu();
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) (screen.getWidth()); 
		int height = (int) (screen.getHeight()); 
		main.setPreferredSize(new Dimension(width,height));
		main.setSize(width,height);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setVisible(true);
		main.setJMenuBar(bar);	
		main.add(PrimPanel);
		main.pack();
	}
	public static Component createGrids(){
		JPanel body = new JPanel(new GridLayout());
		body.setBackground(new Color(128,172,230));
		body.setBorder(BorderFactory.createEmptyBorder(15, 15,15, 15));    
		JSplitPane splitPaneHor = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPaneHor.add(LeftGridPlayer.main()); //Creates a 10x10 grid for the playerGrid
		splitPaneHor.add(RightGridComputer.main()); //Creates a 10x10 grid for the computer 
		splitPaneHor.setResizeWeight(0.5);
		JSplitPane splitPaneVer = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPaneVer.add(splitPaneHor);
		splitPaneVer.add(PrimeWindow.getShips());
		splitPaneVer.setResizeWeight(0.99);
		body.add(splitPaneVer, BorderLayout.CENTER);
		return body;
	}
	public static void resetGrids(){
		System.out.println(BSVariables.getTime() + "Resetting panels");
		try{
			for(int x = 0; x < BSVariables.gridSize; x++){
				for(int y = 0; y < BSVariables.gridSize; y++){
					BSVariables.playerGrid[x][y] = 0;
					LeftGridPlayer.button[x][y].setBackground(LeftGridPlayer.emptyCell);
					LeftGridPlayer.placingShip = false;
				}
			}
		}catch(Exception e){
			System.out.println("Panel reset err:");
			System.out.println(e.getMessage());
		}
		System.out.println(BSVariables.getTime() + "Panels reset complete");
		main.add(PrimPanel);
	}
	public static Component getShips(){
		JPanel pane = new JPanel(new GridLayout(1,2));
		pane.add(PrimeWindow.listedPlayerShips());
		listedComputerShips();
		pane.add(PrimeWindow.compCurrentShips);
		pane.setBackground(new Color(91,162,255));
		return pane;
	}
	public static Component listedPlayerShips(){
		JPanel currentShips = new JPanel(new GridLayout(5,4,15,1));
		currentShips.setBackground(new Color(91,162,255));
		currentShips.add(new JLabel("Player Ships: "));
		currentShips.add(new JLabel(""));
		String[] shipList = {"carrier","battleship","cruiser","destroyer"};
		for(int x = 0; x < shipList.length; x++){
			JLabel label = new JLabel("" + shipList[x] + ": ");
			currentShips.add(label);
			currentShips.add(new JLabel("" +BSVariables.getShips(shipList[x])));
		}
		return currentShips;
	}
	public static void listedComputerShips(){
		JPanel currentShips = new JPanel(new GridLayout(3,4,15,15));
		currentShips.setBackground(new Color(91,162,255));
		currentShips.add(new JLabel(""));
		JLabel compCounter = new JLabel("Total Computer Ships: " + ShipContents.computerShipsTotal());
		currentShips.add(compCounter);
		compCurrentShips = currentShips;
	}
	public static void updateCompShipsPanel(){
		compCounter.setText("Total Computer Ships: " + ShipContents.computerShipsTotal());
	}
}
