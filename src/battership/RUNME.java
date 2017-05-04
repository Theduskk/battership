package battership;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RUNME {
	final static JFrame welcomeFrame = new JFrame("BanterShip");
	final static JPanel body = new JPanel(new GridLayout(0,2));
	public static void main(String[] args) {
		//Enable to add full screen window
		//		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		//		int width = (int) (screen.getWidth()); 
		//		int height = (int) (screen.getHeight()); 
		//		main.setPreferredSize(new Dimension(width,height));
		//Set the main styles.
		body.setBackground(new Color(128,172,230));
		body.setBorder(BorderFactory.createEmptyBorder(15, 15,15, 15)); 
		//Add a welcome message in the first grid reference.
		JLabel welcome = new JLabel("Welcome to my Battleships game!");
		body.add(welcome);
		welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		welcomeFrame.add(body);
		addStartButton();
		addOptions();
		addQuitButton();
		welcomeFrame.setVisible(true);
		welcomeFrame.pack();
	}
	private static void addStartButton(){
		JButton startButt = new JButton("Start");
		body.add(startButt);
		startButt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				welcomeFrame.setVisible(false);
				ComputerLogic.playerTurn = false;
				BSVariables.gridsInitialise();
				ComputerLogic.placeAllComputerShips();
				//Do PrimeWindow.mainFrame(); last otherwise certain elements won't be set when generated.
				PrimeWindow.mainFrame();

			}
		});
	}
	private static void addQuitButton(){
		JButton quitButt = new JButton("Quit");
		body.add(quitButt);
		quitButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.out.println(BSVariables.getTime() +"Quitting game");
				System.exit(0);
			}
		});
	}
	private static void addOptions(){ //Below function is to add the Settings segment on the opening window.
		JPanel optionsPanel = new JPanel(new GridLayout(0,2));
		JLabel settinglabel = new JLabel("SETTING");
		JLabel valuelabel = new JLabel("VALUE");
		JLabel gridSizeLabel = new JLabel("GridSize: " + BSVariables.gridSize + " x " + BSVariables.gridSize);	
		JLabel difficultyLabel = new JLabel("Difficulty: " + 50);
		JSlider difficultySlider = new JSlider(JSlider.HORIZONTAL);
		//		settingsMenu.setMnemonic(KeyEvent.VK_F3);
		optionsPanel.add(settinglabel);
		optionsPanel.add(valuelabel);
		optionsPanel.add(difficultyLabel);
		optionsPanel.add(difficultySlider);

		difficultySlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				JSlider diffSlider = (JSlider) evt.getSource();
				String level = " ";
				if (!diffSlider.getValueIsAdjusting()) {
					int value = diffSlider.getValue();
					if(value == 0){
						level = "TOO EASY";
					}else if(value <25){
						level = "EASY";
					}else if(value <= 50){
						level = "MEDIUM";
					}else if(value <= 75){
						level = "HARD";
					}else if(value <= 99){
						level = "VERY HARD";
					}else{
						level = "IMPOSSIBLE";
					}
					if(value == 0){
						value++;
						BSVariables.diffLevel = value;
						System.out.println(BSVariables.getTime() + "Difficulty set: " +(value));
						difficultyLabel.setText("Difficulty: " + value + " " + level);
					}
					BSVariables.diffLevel = value;
					System.out.println(BSVariables.getTime() + "Difficulty set: " +value);
					difficultyLabel.setText("Difficulty: " + value  + " " + level);
				}	
			}
		});
		optionsPanel.add(gridSizeLabel);
		JSlider gridSlider = new JSlider(10,20,10); //This code allows the user to change the 10x10 gridsize. Minimum 10-Maximum 20.
		optionsPanel.add(gridSlider); 
		gridSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				JSlider gridSlider = (JSlider) evt.getSource();
				if (!gridSlider.getValueIsAdjusting()) {
					int value = gridSlider.getValue();
					if(value == 0){
						value++;
						BSVariables.gridSize = value;
						System.out.println(BSVariables.getTime() +"Grid level set: " +(value));
						gridSizeLabel.setText("GridSize: " + BSVariables.gridSize + " x " + BSVariables.gridSize);
						//						PrimeWindow.resetGrids();
					}			
					BSVariables.gridSize = value;
					System.out.println(BSVariables.getTime() + "Grid level set: " +(value));
					gridSizeLabel.setText("GridSize: " + BSVariables.gridSize + " x " + BSVariables.gridSize);
					//					PrimeWindow.resetGrids();
				}	
			}
		});

		String[] shipList = {"carrier","battleship","cruiser","destroyer"};
		for(int x = 0; x < shipList.length; x++){
			JLabel amount = new JLabel(shipList[x] + " Quantity: " + BSVariables.getShips(shipList[x]));
			JButton shipName = new JButton("" + shipList[x]);
			JSlider shipSlider = new JSlider(0,10,BSVariables.getShips(shipList[x]));
			String str = shipList[x];
			shipSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent evt) {
					JSlider shipSlider = (JSlider) evt.getSource();
					if (!shipSlider.getValueIsAdjusting()) {
						int value = shipSlider.getValue();
						BSVariables.setShips(str, value);
						amount.setText(str + " Quantity: " + value);
						//						PrimeWindow.resetGrids();
					}	
				}
			});
			optionsPanel.add(amount);
			optionsPanel.add(shipSlider);
			body.add(optionsPanel);
		}
	}
}