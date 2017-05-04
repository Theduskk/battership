package battership;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.*;

public class Menutab {

	protected static JMenuBar mainMenu(){
		JMenuBar mbar = new JMenuBar();
		mbar.add(Menutab.gameMenu());
		mbar.add(Menutab.settingsMenu());
		//		mbar.add(Menutab.shipMenu());
		return mbar;
	}
	private static JMenu gameMenu(){
		JMenu gameMenu = new JMenu("Game");
		//		gameMenu.setMnemonic(KeyEvent.VK_F1);
		JMenuItem resetButt = new JMenuItem("Restart");
		JMenuItem quitButt = new JMenuItem("Quit");
		gameMenu.add(quitButt);
		quitButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.out.println(BSVariables.getTime() +"Closing game");
				try {
					Thread.sleep(250);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});
		return gameMenu;
	}
	private static JMenu stateMenu(){
		/*THIS NEEDS TO BE IMPLEMENTED 
		 * DO LAST
		 */
		JMenu stateMenu = new JMenu("Save/Load");
		//		stateMenu.setMnemonic(KeyEvent.VK_F2);
		JMenuItem saveButt = new JMenuItem("Save");
		stateMenu.add(saveButt );
		saveButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.out.println(BSVariables.getTime() + "Saving game");
			}
		});
		JMenuItem loadButt = new JMenuItem("Load");
		stateMenu.add(loadButt );
		loadButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.out.println(BSVariables.getTime() + "Loading game");
			}
		});
		return stateMenu;
	}
	private static JMenu settingsMenu(){
		JMenu settingsMenu = new JMenu("Settings");
		JSlider diffSlider = new JSlider(JSlider.HORIZONTAL);
		JMenu diffMenu = new JMenu("Difficulty");
		JLabel level = new JLabel("Level: 50");		
		//		settingsMenu.setMnemonic(KeyEvent.VK_F3);
		settingsMenu.add(diffMenu);
		diffMenu.add(level);
		diffMenu.add(diffSlider); 
		diffSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				JSlider diffSlider = (JSlider) evt.getSource();
				if (!diffSlider.getValueIsAdjusting()) {
					int value = diffSlider.getValue();
					if(value == 0){
						value++;
						BSVariables.diffLevel = value;
						System.out.println(BSVariables.getTime() + "Difficulty set: " +(value));
						level.setText("Level: " + (value));
					}
					BSVariables.diffLevel = value;
					System.out.println(BSVariables.getTime() + "Difficulty set: " +value);
					level.setText("Level: " + value);
				}	
			}
		});
		return settingsMenu;
	}
	/*
	 * Below is the redundant code for changing the amount of ships mid-game, 
	 * for obvious reasons I have decided to remove this factor due to the application having to be restarted.
	 */
	//	private static JMenu shipMenu(){
	//		JMenu shipMenu = new JMenu("Ships");
	//		String[] shipList = {"carrier","battleship","cruiser","destroyer"};
	//		for(int x = 0; x < shipList.length; x++){
	//			JLabel amount = new JLabel(shipList[x] + " Quantity: " + BSVariables.getShips(shipList[x]));
	//			JMenu shipName = new JMenu("" + shipList[x]);
	//			JSlider shipSlider = new JSlider(0,10,BSVariables.getShips(shipList[x]));
	//			String str = shipList[x];
	//			shipName.add(amount);
	//			shipName.add(shipSlider);
	//			shipSlider.addChangeListener(new ChangeListener() {
	//				public void stateChanged(ChangeEvent evt) {
	//					JSlider shipSlider = (JSlider) evt.getSource();
	//					if (!shipSlider.getValueIsAdjusting()) {
	//						int value = shipSlider.getValue();
	//						if(value == 0){
	//							value++;
	//							System.out.println(BSVariables.getTime() + "Ship level set: " +(value));
	//							BSVariables.setShips(str, value);
	//							amount.setText(str + " Quantity: " + value);
	//							//							PrimeWindow.resetGrids();
	//						}			
	//						System.out.println(BSVariables.getTime()+ "Ship level set: " +(value));
	//						BSVariables.setShips(str, value);
	//						amount.setText(str + " Quantity: " + value);
	//						//						PrimeWindow.resetGrids();
	//					}	
	//				}
	//			});
	//			shipMenu.add(shipName);
	//		}
	//		return shipMenu;
	//	}
}
