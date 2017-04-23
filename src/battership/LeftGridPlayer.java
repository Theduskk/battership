package battership;


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class LeftGridPlayer {
	private static int carrier = BSVariables.getShips("carrier");
	private static int battleship = BSVariables.getShips("battleship");
	private static int cruiser = BSVariables.getShips("cruiser");
	private static int destroyer = BSVariables.getShips("destroyer");
	public static Color emptyCell = new Color(91,162,255);
	protected static boolean placingShip = false;
	/*
	 * First click variables when placing ships.
	 */
	protected static int baseX;
	protected static int baseY;
	/*
	 * Difference between XY & First Click XY when placing the second point of the ship
	 */
	protected static int diffX;
	protected static int diffY;

	public static JPanel panel = new JPanel();
	public static JButton button[][] = new JButton[BSVariables.gridSize][BSVariables.gridSize];
	private static int dcounter=1;
	private static int crcounter=1;
	private static int cacounter=1;
	private static int bcounter=1;
	public static JPanel main(){
		Integer coordx = 0;
		Integer coordy = 0;
		panel.setLayout(new GridLayout(BSVariables.gridSize,BSVariables.gridSize));
		panel.setBackground(Color.BLUE);
		try{
			for(int x = 0; x < BSVariables.gridSize; x++){
				for(int y = 0; y < BSVariables.gridSize; y++){
					panel.add(button[x][y] = new JButton("X"));
					coordx = x;
					coordy = y;
					{	
						button[x][y].setVisible(true);
						button[x][y].setBackground(emptyCell);
						button[x][y].setOpaque(true);
						button[x][y].setForeground(Color.BLACK);
						button[x][y].setBorderPainted(true);
						button[x][y].setFocusPainted(false);
						button[x][y].setName(coordx.toString() + ":" + coordy.toString());
						button[x][y].addActionListener(new ActionListener(){
							@Override
							public void actionPerformed(ActionEvent e) {
								//Lines to get the button name, or the X/Y coord.
								String[] name = ((JComponent) e.getSource()).getName().split(":");
								Integer x = Integer.valueOf(name[0]);
								Integer y = Integer.valueOf(name[1]);
								int posorneg = 0; //Value to show whether to add or subtract from the original X/Y point. 0 - Subtract | 1 - Add
								boolean t;
								if(BSVariables.debug == true){
									System.out.println(x + "" + y);
								}
								if(carrier == 0 && battleship == 0 && cruiser == 0 && destroyer == 0){//This code should be unreachable
									System.out.println("Player clicked left grid");
								}else{
									if(BSVariables.playerGrid[x][y] == 0 && placingShip == false){
										//The first click on an empty cell, sets this one as the default selected cell by saving its coordinates.
										((JComponent) e.getSource()).setBackground(Color.blue);
										BSVariables.playerGrid[x][y] = 1;
										baseX = x;
										baseY = y;
										placingShip = true;
									}else if(BSVariables.playerGrid[x][y] == BSVariables.playerGrid[x][y] && BSVariables.playerGrid[x][y] == 1 && placingShip == true){
										button[baseX][baseY].setBackground(emptyCell);
										placingShip = false;
									}else if(BSVariables.playerGrid[x][y] == 1 && placingShip == true){
										BSVariables.playerGrid[baseX][baseY] = 0;
										button[baseX][baseY].setBackground(emptyCell);
										placingShip = false;
									}else if(BSVariables.playerGrid[x][y] == 0 && placingShip == true){
										/*
										 * Checks vertical ship placing
										 */
										while(placingShip == true){
											if(baseX == x){
												t = false;
												if(baseY > y){
													diffY = baseY-y;
													posorneg = 0;
												}else if(baseY < y){
													diffY = y - baseY;
													posorneg = 1;
												}
												if(checkOverlap(baseX, baseY, x, y, posorneg) == true){
													button[baseX][baseY].setBackground(emptyCell);
													BSVariables.playerGrid[baseX][baseY] = 0;
													if(BSVariables.debug == true){
														System.out.println("Ship overlap detected");
													}
													placingShip = false;
													break;
												}
												switch(diffY){
												//Case 1 for Destroyer placing
												case 1:
													if(destroyer == 0){
														//No more destroyers left to place.
														if(BSVariables.debug == true){
															System.out.println(BSVariables.getTime() + "Out of Destroyers");
														}
														BSVariables.playerGrid[baseX][baseY] = 0;
														button[baseX][baseY].setBackground(emptyCell);
														placingShip = false;
														break;
													}else{
														BSVariables.playerGrid[x][y] = 1;
														BSVariables.playerGrid[baseX][baseY] = 1;
														button[baseX][baseY].setBackground(Color.ORANGE);
														button[x][y].setBackground(Color.ORANGE);
														addDestroyer(x,y);
														destroyer--;	
														placingShip = false;
														ShipContents.playerShipsTotal++;
														CheckAllShipstoStartGame();
														
													}
													break;
													//Case 2 for Cruiser placing
												case 2:
													if(cruiser == 0){
														//No more cruisers left to place.
														if(BSVariables.debug == true){
															System.out.println(BSVariables.getTime() + "Out of Cruisers");
														}
														placingShip = false;
														BSVariables.playerGrid[baseX][baseY] = 0;
														button[baseX][baseY].setBackground(emptyCell);
														break;
													}
													addCruiser(x,y,t, posorneg);
													cruiser--;
													placingShip = false;
													ShipContents.playerShipsTotal++;
													CheckAllShipstoStartGame();
													break;
													//Case 3 Battleship placing
												case 3:
													if(battleship == 0){
														//Battleships count reduced to 0, none left to place.
														if(BSVariables.debug == true){
															System.out.println(BSVariables.getTime() + "Out of Battleships" );
														}
														placingShip = false;
														BSVariables.playerGrid[baseX][baseY] = 0;
														button[baseX][baseY].setBackground(emptyCell);
														break;
													}
													addBattleship(x,y,t,posorneg);
													battleship--;
													placingShip = false;
													ShipContents.playerShipsTotal++;
													CheckAllShipstoStartGame();
													break;
													//Case 4 Carrier placing
												case 4:
													if(carrier == 0){
														if(BSVariables.debug == true){
															System.out.println(BSVariables.getTime() + "Out of Carriers" );
														}
														placingShip = false;
														BSVariables.playerGrid[baseX][baseY] = 0;
														button[baseX][baseY].setBackground(emptyCell);
														break;
													}
													addCarrier(x,y,t,posorneg);
													carrier--;
													placingShip = false;
													ShipContents.playerShipsTotal++;
													CheckAllShipstoStartGame();
												}
												break;
											}else if(baseY == y){
												/*
												 * Checks horizontal ship placing.
												 */
												t = true;
												if(baseX > x){
													diffX = baseX - x;
													posorneg = 0;
												}else if(baseX < x){
													diffX = x - baseX;
													posorneg = 1;
												}
												switch(diffX){
												//Destroyer placing
												case 1:
													if(destroyer == 0){
														//No more destroyers left to place.
														System.out.println(BSVariables.getTime() + "Out of Destroyers");
														placingShip = false;
														BSVariables.playerGrid[baseX][baseY] = 0;
														button[baseX][baseY].setBackground(emptyCell);
														break;
													}else{
														BSVariables.playerGrid[x][y] = 1;
														BSVariables.playerGrid[baseX][baseY] = 1;
														button[baseX][baseY].setBackground(Color.orange);
														button[x][y].setBackground(Color.ORANGE);
														addDestroyer(x,y);
														destroyer--;
														placingShip = false;
														ShipContents.playerShipsTotal++;
														CheckAllShipstoStartGame();
													}										
													break;
													//Cruiser Placing
												case 2:
													if(cruiser == 0){
														//No more cruisers left to place.
														System.out.println(BSVariables.getTime() + "Out of Cruisers");
														placingShip = false;
														BSVariables.playerGrid[baseX][baseY] = 0;
														button[baseX][baseY].setBackground(emptyCell);
														break;
													}
													addCruiser(x,y,t, posorneg);
													cruiser--;
													placingShip = false;
													ShipContents.playerShipsTotal++;
													CheckAllShipstoStartGame();
													break;
													//Battleship placing
												case 3:
													if(battleship == 0){
														System.out.println(BSVariables.getTime() + "Out of Battleships" );
														placingShip = false;
														BSVariables.playerGrid[baseX][baseY] = 0;
														button[baseX][baseY].setBackground(emptyCell);
														break;
													}else{
														addBattleship(x,y,t,posorneg);
														battleship--;
														placingShip = false;
														ShipContents.playerShipsTotal++;
														CheckAllShipstoStartGame();
														break;
													}
													//Carrier placing
												case 4:
													if(carrier == 0){
														System.out.println(BSVariables.getTime() + "Out of Carriers" );
														placingShip = false;
														BSVariables.playerGrid[baseX][baseY] = 0;
														button[baseX][baseY].setBackground(emptyCell);
														break;
													}
													addCarrier(x,y,t,posorneg);
													carrier--;
													placingShip = false;
													ShipContents.playerShipsTotal++;
													CheckAllShipstoStartGame();
												}break;
											}else{
												BSVariables.playerGrid[baseX][baseY] = 0;
												button[baseX][baseY].setBackground(emptyCell);
												placingShip = false;
											}
										}

									}}
							}
						}
								);
					}
				}
			}
		}catch(IndexOutOfBoundsException e){
			System.out.println(e.getMessage());
		}
		return panel;	
	}
	public static void onHit(int x, int y){
		button[x][y].setBackground(Color.red);
		BSVariables.playerGrid[x][y] = 2;
		Ship sh = ShipContents.getPlayerShipfromcell(false);
		ComputerLogic.playerTurn = true;	
		if(sh.getShipHits() == sh.getLength()){
			LeftGridPlayer.onSunk(sh);
		}
	}
	public static void onMiss(int x, int y){
		button[x][y].setBackground(Color.gray);
		ComputerLogic.playerTurn = true;
	}
	public static void onSunk(Ship currentShip){
		System.out.println("Onsunk loop started");
		try{
			for(int i = 0; i < currentShip.activeCells.size(); i++){//Loops through active cells, sets all to Black to indicate a sunk ship.
				int x = currentShip.activeCells.get(i)[0];//Gets the x cell from the first coordinate pair
				int y = currentShip.activeCells.get(i)[1];//Gets the y cell from the first coordinate pair

				JButton currentButton = button[x][y];
				currentButton.setBackground(Color.black);				
				for( ActionListener al : currentButton.getActionListeners() ) { //Removes all ActionListeners from the button, preventing further action.
					currentButton.removeActionListener( al );
				}
			}
		}
		catch(Exception e){
			System.out.println("Exception found at function onSunk: " + e.getMessage());
		}
		ShipContents.playerShipsTotal--;
		ComputerLogic.lastHit.clear();
		ComputerLogic.compHit = false;
		ComputerLogic.playerTurn = true;
	}
	public static void addDestroyer(int x, int y){
		Destroyer d = new Destroyer();
		d.setName("d"+dcounter);
		d.setFriendorFoe(0);
		d.setShipState(0);
		int[] c1 = new int[]{baseX, baseY};
		int[] c2 = new int[]{x, y};
		ArrayList<int[]> coord = new ArrayList<int[]>();
		coord.add(c1);
		coord.add(c2);
		d.setActiveCells(coord);
		ShipContents.addPlayerDestroyers(d);
		if(BSVariables.debug == true){
			System.out.println(BSVariables.getTime());
			System.out.println("Ship added: " + d.getType());
			System.out.println("Cellsactivated: " + coord.size());
			System.out.println("At coords:");
			System.out.println(d.getName() +"x1:" + coord.get(0)[0]);
			System.out.println(d.getName() +"y1:" + coord.get(0)[1]);
			System.out.println(d.getName() +"x2:" + coord.get(1)[0]);
			System.out.println(d.getName() +"y2:" + coord.get(1)[1]);
		}	
		dcounter++;
	}
	public static void addCruiser(int x, int y, boolean b, int posorneg){
		int[] first = new int[]{baseX,baseY};
		ArrayList<int[]> coord = new ArrayList<int[]>();
		Cruiser c = new Cruiser();
		c.setName("c"+crcounter);
		c.setFriendorFoe(0);
		c.setShipState(0);
		coord.add(first);
		button[baseX][baseY].setBackground(Color.ORANGE);
		if(b == true){
			for(int i = 0; i <diffX; i++){
				if(posorneg == 0){	
					int[] cz = new int[]{x+i,y};
					BSVariables.playerGrid[(x+i)][y] = 1;
					button[(x+i)][y].setBackground(Color.ORANGE);
					coord.add(cz);
				}else{
					int[] cz = new int[]{x-i,y};
					BSVariables.playerGrid[(x-i)][y] = 1;
					button[(x-i)][y].setBackground(Color.ORANGE);
					coord.add(cz);
				}
			}
		}else{			
			for(int i = 0; i <diffY; i++){
				if(posorneg == 0){	
					int[] cz = new int[]{x,y+i};
					BSVariables.playerGrid[x][y+i] = 1;
					button[x][y+i].setBackground(Color.ORANGE);
					coord.add(cz);
				}else{
					int[] cz = new int[]{x,y-i};
					BSVariables.playerGrid[x][y-i] = 1;
					button[x][y-i].setBackground(Color.ORANGE);
					coord.add(cz);
				}		
			}			
		}
		c.setActiveCells(coord);
		ShipContents.addPlayerCruisers(c);
		if(BSVariables.debug == true){
			System.out.println(BSVariables.getTime());
			System.out.println("Ship added: " + c.getType());
			System.out.println("Cellsactivated: " + coord.size());
			System.out.println("At coords:");
			System.out.println(c.getName() +"x1:" + coord.get(0)[0]);
			System.out.println(c.getName() +"y1:" + coord.get(0)[1]);
			System.out.println(c.getName() +"x2:" + coord.get(1)[0]);
			System.out.println(c.getName() +"y2:" + coord.get(1)[1]);
			System.out.println(c.getName() +"x3:" + coord.get(2)[0]);
			System.out.println(c.getName() +"y3:" + coord.get(2)[1]);
		}	
		crcounter++;
	}
	public static void addBattleship(int x, int y, boolean b1, int posorneg){
		int[] first = new int[]{baseX,baseY};
		ArrayList<int[]> coord = new ArrayList<int[]>();
		Battleship b = new Battleship();
		b.setName("b"+bcounter);
		b.setFriendorFoe(0);
		b.setShipState(0);
		coord.add(first);
		button[baseX][baseY].setBackground(Color.ORANGE);
		if(b1 == true){
			for(int i = 0; i <diffX; i++){
				if(posorneg == 0){	
					int[] cz = new int[]{x+i,y};
					BSVariables.playerGrid[(x+i)][y] = 1;
					button[(x+i)][y].setBackground(Color.ORANGE);
					coord.add(cz);
				}else{
					int[] cz = new int[]{x-i,y};
					BSVariables.playerGrid[(x-i)][y] = 1;
					button[(x-i)][y].setBackground(Color.ORANGE);
					coord.add(cz);
				}
			}
		}else{			
			for(int i = 0; i <diffY; i++){
				if(posorneg == 0){	
					int[] cz = new int[]{x,y+i};
					BSVariables.playerGrid[x][y+i] = 1;
					button[x][y+i].setBackground(Color.ORANGE);
					coord.add(cz);
				}else{
					int[] cz = new int[]{x,y-i};
					BSVariables.playerGrid[x][y-i] = 1;
					button[x][y-i].setBackground(Color.ORANGE);
					coord.add(cz);
				}		
			}
		}
		b.setActiveCells(coord);
		ShipContents.addPlayerBattleships(b);
		if(BSVariables.debug == true){
			System.out.println(BSVariables.getTime());
			System.out.println("Ship added: " + b.getType());
			System.out.println("Cellsactivated: " + coord.size());
			System.out.println("At coords:");
			System.out.println(b.getName() +"x1:" + coord.get(0)[0]);
			System.out.println(b.getName() +"y1:" + coord.get(0)[1]);
			System.out.println(b.getName() +"x2:" + coord.get(1)[0]);
			System.out.println(b.getName() +"y2:" + coord.get(1)[1]);
			System.out.println(b.getName() +"x3:" + coord.get(2)[0]);
			System.out.println(b.getName() +"y3:" + coord.get(2)[1]);
			System.out.println(b.getName() +"x4:" + coord.get(3)[0]);
			System.out.println(b.getName() +"y4:" + coord.get(3)[1]);
		}
		bcounter++;
	}
	public static void addCarrier(int x, int y, boolean b, int posorneg){
		int[] first = new int[]{baseX,baseY};
		ArrayList<int[]> coord = new ArrayList<int[]>();
		Carrier ca = new Carrier();
		ca.setName("ca"+cacounter);
		ca.setFriendorFoe(0);
		ca.setShipState(0);
		coord.add(first);
		button[baseX][baseY].setBackground(Color.ORANGE);
		if(b == true){
			for(int i = 0; i <diffX; i++){
				if(posorneg == 0){	
					int[] cz = new int[]{x+i,y};
					BSVariables.playerGrid[(x+i)][y] = 1;
					button[(x+i)][y].setBackground(Color.ORANGE);
					coord.add(cz);
				}else{
					try{
						int[] cz = new int[]{x-i,y};
						BSVariables.playerGrid[(x-i)][y] = 1;
						button[x-i][y].setBackground(Color.ORANGE);
						coord.add(cz);
					}catch(IndexOutOfBoundsException l){
						System.out.println(BSVariables.getTime() + "Detected out of bounds");
						int[] cz = new int[]{x,y};
						BSVariables.playerGrid[x][y] = 1;
						button[x][y].setBackground(Color.ORANGE);
						coord.add(cz);
					}
				}
			}
		}else{			
			for(int i = 0; i <diffY; i++){
				if(posorneg == 0){	
					int[] cz = new int[]{x,y+i};
					BSVariables.playerGrid[x][y+i] = 1;
					button[x][y+i].setBackground(Color.ORANGE);
					coord.add(cz);
				}else{				
					try{
						int[] cz = new int[]{x,y-i};
						BSVariables.playerGrid[(x)][y-i] = 1;
						button[x][y-i].setBackground(Color.ORANGE);
						coord.add(cz);
					}catch(IndexOutOfBoundsException l){
						System.out.println(BSVariables.getTime() + "Detected out of bounds");
						int[] cz = new int[]{x,y};
						BSVariables.playerGrid[x][y] = 1;
						button[x][y].setBackground(Color.ORANGE);
						coord.add(cz);
					}

				}		
			}
		}
		ca.setActiveCells(coord);
		ShipContents.addPlayerCarriers(ca);
		if(BSVariables.debug == true){
			System.out.println(BSVariables.getTime());
			System.out.println("Ship added: " + ca.getType());
			System.out.println("Cellsactivated: " + coord.size());
			System.out.println("At coords:");
			System.out.println(ca.getName() +"x1:" + coord.get(0)[0]);
			System.out.println(ca.getName() +"y1:" + coord.get(0)[1]);
			System.out.println(ca.getName() +"x2:" + coord.get(1)[0]);
			System.out.println(ca.getName() +"y2:" + coord.get(1)[1]);
			System.out.println(ca.getName() +"x3:" + coord.get(2)[0]);
			System.out.println(ca.getName() +"y3:" + coord.get(2)[1]);
			System.out.println(ca.getName() +"x4:" + coord.get(3)[0]);
			System.out.println(ca.getName() +"y4:" + coord.get(3)[1]);
			System.out.println(ca.getName() +"x5:" + coord.get(4)[0]);
			System.out.println(ca.getName() +"y5:" + coord.get(4)[1]);
		}
		cacounter++;
	}
	public static boolean checkOverlap(int x, int y, int x2, int y2, int posorneg){ //Checks individual cells for overlap of other objects
		if((x - x2) == 1 || (y - y2) == 1){
			return false;
		}
		if(x == x2){
			if(posorneg == 1){//Check positive cell distributions
				if(BSVariables.debug == true){
					System.out.println("Checking positive Cell Distribution on Y: ");
				}
				for(int chkY = (y+1); chkY <= y2; chkY++){
					if(BSVariables.playerGrid[x][chkY] == 1){
						return true; //Returns true on active cell detection
					}
					continue;			
				}
			}
			else if(posorneg == 0){ //Checks negative cell distributions
				if(BSVariables.debug == true){
					System.out.println("Checking negative Cell Distribution on Y2:");
				}
				for(int chkY = (y-1); chkY >= y2; chkY--){
					if(BSVariables.playerGrid[x][chkY] == 1){
						return true; //Returns true on active cell detection		
					}else{
						continue;	
					}
				}
			}else if(y == y2){
				if(posorneg == 1){//Check positive cell distributions
					if(BSVariables.debug == true){
						System.out.println("Checking positive Cell Distribution on X:");
					}
					for(int chkX = (x+1); chkX <= x2; chkX++){
						if(BSVariables.playerGrid[chkX][y] == 1){
							return true; //Returns true on active cell detection
						}
						continue;		
					}
				}
			}
			else if(posorneg == 0){ //Checks negative cell distributions
				if(BSVariables.debug == true){
					System.out.println("Checking negative Cell Distribution on X2:");
				}
				for(int chkX = (x-1); chkX >= x; chkX--){
					if(BSVariables.playerGrid[chkX][y] == 1){
						return true; //Returns true on active cell detection		

					}continue;	
				}
			}
		}	
		return false;
	}
	public static void CheckAllShipstoStartGame(){
		if(carrier == 0 && battleship == 0 && cruiser == 0 && destroyer == 0){
			System.out.println(BSVariables.getTime());
			System.out.println("Starting Game");
			BSVariables.playingGame = true;
			ComputerLogic.playerTurn = true;
		}
	}
}