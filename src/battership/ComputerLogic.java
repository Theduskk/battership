package battership;

import java.util.ArrayList;
import java.util.Random;


public class ComputerLogic {
	protected static boolean compHit = false;
	protected static boolean compPreviousHit = false;
	protected static boolean playerTurn= false;
	public static ArrayList<int[]> lastHit = new ArrayList<int[]>();
	private static int hitpos;
	private static int size = BSVariables.gridSize;
	public static int totalComputerHits = 0;
	public static int randomCoord(){
		Random rand = new Random();
		int ran = rand.nextInt(size);
		return ran;	
	}
	public static void afterHit(){
		System.out.println("Computer afterHit() started");
		Random rand = new Random();
		int ran = rand.nextInt(100);
		int x = lastHit.get(0)[0];
		int y = lastHit.get(0)[1];
		Ship currentShip = ShipContents.getPlayerShipfromcell(true);
		/* Checked to see if the computer scored a hit.
		 * Then takes the starting cell to checks the ones surrounding it.
		 * Once a positive cell has been found, return the coordinates.
		 * Else if it fails the level check, initialise the random miss.
		 */
		if(ran < BSVariables.diffLevel){ //Computer hit ship
			int x1 = 0;
			int y1 = 0;	
			int n =  (int) Math.floor(Math.random()*2);
			for(int i = 0; i < currentShip.activeCells.size(); i++){
				x1 = currentShip.activeCells.get(i)[0];
				y1 = currentShip.activeCells.get(i)[1];
				if(x == x1 && y == y1){
					hitpos = i;
				}else{
					continue;
				}
			}
			if(hitpos == 0){
				x1 = currentShip.activeCells.get(1)[0];
				y1 = currentShip.activeCells.get(1)[1];
				LeftGridPlayer.onHit(x1, y1);
			}else if(hitpos == currentShip.getLength()){
				x1 = currentShip.activeCells.get(hitpos-1)[0];
				y1 = currentShip.activeCells.get(hitpos-1)[1];
				LeftGridPlayer.onHit(x1, y1);
			}else{
				if(n == 0){
					try{
					x1 = currentShip.activeCells.get(hitpos-1)[0];
					y1 = currentShip.activeCells.get(hitpos-1)[1];
					LeftGridPlayer.onHit(x1, y1);
					}catch(IndexOutOfBoundsException e){
						System.out.println(e.getMessage() + "@ComputerLogic.AfterHit - 60");
						x1 = currentShip.activeCells.get(hitpos+1)[0];
						y1 = currentShip.activeCells.get(hitpos+1)[1];
						LeftGridPlayer.onHit(x1, y1);
					}
				}else if(n == 1){
					try{
					x1 = currentShip.activeCells.get(hitpos+1)[0];
					y1 = currentShip.activeCells.get(hitpos+1)[1];
					LeftGridPlayer.onHit(x1, y1);
					}catch(IndexOutOfBoundsException e){
						System.out.println(e.getMessage() +  "@Computer Logic.AfterHit- 71");
						x1 = currentShip.activeCells.get(hitpos-1)[0];
						y1 = currentShip.activeCells.get(hitpos-1)[1];
						LeftGridPlayer.onHit(x1, y1);
					}
				}
			}
		}else{
			try{
				if(BSVariables.playerGrid[x+1][y] == 0){
					LeftGridPlayer.onMiss(x+1, y);
				}else{
					LeftGridPlayer.onMiss(x, y+1);
				}
			}catch(Exception e){
				System.out.println(e.getMessage());
				if(BSVariables.playerGrid[x-1][y] == 0){
					LeftGridPlayer.onMiss(x-1, y);
				}else{
					LeftGridPlayer.onMiss(x, y-1);
				}
			}
		}if(currentShip.getShipHits() >= currentShip.getLength()){
			LeftGridPlayer.onSunk(currentShip);
		}
	}
	public static boolean blindFire(){
		Random rand = new Random();
		int ran = rand.nextInt(100);
		if(BSVariables.debug == true){
			System.out.println("Computer tried blindFire()");
		}
		try{
			/*Variable on difficulty level
			 * Level is a percentage chance to hit.
			 * Level 100 == 100% chance to hit.
			 * Level 10 == 10% chance to hit.
			 */
			if(ran < BSVariables.diffLevel){ //If True the AI has hit.
				System.out.println("Computer scored a hit");
				while(true){
					/*Pulls random grid references until a live cell is found. 
					 * Once found it marks it at hit/dead from the function ShipContents.getPlayerShipfromcell()
					 * Otherwise runs completeMiss()
					 */
					int x = randomCoord();
					int y = randomCoord();
					if(BSVariables.getPlayerSingleCellDetails(x, y) == 1){
						if(BSVariables.debug == true){
							System.out.println("Found live cell at: " + x + "" + y);
						}
						int[] c1 = new int[]{x, y};
						lastHit.add(c1);
						LeftGridPlayer.onHit(x, y);
						compHit = true;
						totalComputerHits++;
						break;
					}
				}
			}else{
				System.out.println("Computer missed");
				while(completeMiss() == false){

				}
				compHit = false;
				compPreviousHit = true;
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return true;
	}
	public static boolean completeMiss(){
		while(true){
			/*
			 * Random X/Y coord.
			 */
			int x = randomCoord();
			int y = randomCoord();
			if(BSVariables.getPlayerSingleCellDetails(x,y) == 0){			
				LeftGridPlayer.onMiss(x, y);
				playerTurn = true;
				break;
			}
		}
		return true;
	}
	public static void computerTurn(){
		System.out.println("Total Ships: " + ShipContents.playerShipsTotal);
		if(ShipContents.playerShipsTotal == 0){
			System.out.println("No more ships");
		}else{
			System.out.println("Got here: Computer Turn");
			if(compHit == true){//If the Computer just hit.	
				ComputerLogic.afterHit();
				playerTurn = true;
			}else{//Otherwise try  hitting
				ComputerLogic.blindFire();
				playerTurn = true;
			}System.out.println("Computer turn finished");
			playerTurn = true;
		}
	};
	public static void placeCarriers(){
		for(int x = 0; x < BSVariables.carrier; x++){
			Random rand = new Random();
			int ranx = rand.nextInt(size);
			int rany = rand.nextInt(size);
			int orientation = rand.nextInt(1); // 0 for Horizontal 1 for Vertical
			int cell = BSVariables.getComputerSingleCellDetails(ranx, rany);
			while(true){
				if(cell == 0){
					if(orientation == 0){
						if(ranx < 3){ //Simple logic to avoid placing a ship off the edge of the playable area. ((Left Edge))
							ranx = 3;
							continue;
						}
						else if(ranx > (size-3)){ //Simple logic to avoid placing a ship off the edge of the playable area. ((Right Edge))
							ranx = size-3;
							continue;
						}
						/*
						 *Sets the randomised cell grid to "Alive" for the first Aircraft carrier. 
						 */
						BSVariables.setCompGridCell(ranx, rany, 1);
						BSVariables.setCompGridCell(ranx-1, rany, 1);
						BSVariables.setCompGridCell(ranx-2, rany, 1);
						BSVariables.setCompGridCell(ranx+1, rany, 1);
						BSVariables.setCompGridCell(ranx+2, rany, 1);
						Carrier ca = new Carrier();
						ca.setName("ca"+x);
						ca.setFriendorFoe(1);
						ca.setShipState(0);
						int[] c1 = new int[]{ranx, rany};
						int[] c2 = new int[]{ranx-1, rany};
						int[] c3 = new int[]{ranx-2, rany};
						int[] c4 = new int[]{ranx+1, rany};
						int[] c5 = new int[]{ranx+2, rany};
						ArrayList<int[]> coord = new ArrayList<int[]>();
						coord.add(c1);
						coord.add(c2);
						coord.add(c3);
						coord.add(c4);
						coord.add(c5);
						ca.setActiveCells(coord);
						ShipContents.addCompCarrier(ca);
						break;
					}else if(orientation == 1){
						if(rany < 3){
							rany = 3;
							continue;
						}
						else if(rany > (size-3)){
							rany = size-3;
							continue;
						}
						BSVariables.setCompGridCell(ranx, rany, 1);
						BSVariables.setCompGridCell(ranx, rany-1, 1);
						BSVariables.setCompGridCell(ranx, rany-2, 1);
						BSVariables.setCompGridCell(ranx, rany+1, 1);
						BSVariables.setCompGridCell(ranx, rany+2, 1);
						Carrier ca = new Carrier();
						ca.setName("ca"+x);
						ca.setFriendorFoe(1);
						ca.setShipState(0);
						int[] c1 = new int[]{ranx, rany};
						int[] c2 = new int[]{ranx, rany-1};
						int[] c3 = new int[]{ranx, rany-2};
						int[] c4 = new int[]{ranx, rany+1};
						int[] c5 = new int[]{ranx, rany+2};
						ArrayList<int[]> coord = new ArrayList<int[]>();
						coord.add(c1);
						coord.add(c2);
						coord.add(c3);
						coord.add(c4);
						coord.add(c5);
						ca.setActiveCells(coord);
						ShipContents.addCompCarrier(ca);
						break;
					}
				}
			}		
		}
	}
	public static void placeBattleships(){
		for(int x = 0; x < BSVariables.battleship; x++){
			Random rand = new Random();
			int ranx = rand.nextInt(size);
			int rany = rand.nextInt(size);
			int orientation = rand.nextInt(1); // 0 for Horizontal 1 for Vertical
			int cell = BSVariables.getComputerSingleCellDetails(ranx, rany);
			while(true){
				if(cell == 0){
					if(orientation == 0){
						if(ranx < 2){ //Simple logic to avoid placing a ship off the edge of the playable area. ((Left Edge))
							ranx = 2;
							continue;
						}
						else if(ranx > (size-3)){ //Simple logic to avoid placing a ship off the edge of the playable area. ((Right Edge))
							ranx = size-3;
							continue;
						}
						/*
						 *Sets the randomised cell grid to "Alive" for the first Aircraft carrier. 
						 */
						BSVariables.setCompGridCell(ranx, rany, 1);
						BSVariables.setCompGridCell(ranx-1, rany, 1);
						BSVariables.setCompGridCell(ranx+1, rany, 1);
						BSVariables.setCompGridCell(ranx+2, rany, 1);
						Battleship b = new Battleship();
						b.setName("b"+x);
						b.setFriendorFoe(1);
						b.setShipState(0);
						int[] c1 = new int[]{ranx, rany};
						int[] c2 = new int[]{ranx-1, rany};
						int[] c3 = new int[]{ranx+1, rany};
						int[] c4 = new int[]{ranx+2, rany};
						ArrayList<int[]> coord = new ArrayList<int[]>();
						coord.add(c1);
						coord.add(c2);
						coord.add(c3);
						coord.add(c4);
						b.setActiveCells(coord);
						ShipContents.addCompBattleships(b);
						break;
					}else if(orientation == 1){
						if(rany < 2){
							rany = 2;
							continue;
						}
						else if(rany > (size-3)){
							rany = size-3;
							continue;
						}
						BSVariables.setCompGridCell(ranx, rany, 1);
						BSVariables.setCompGridCell(ranx, rany-1, 1);
						BSVariables.setCompGridCell(ranx, rany+1, 1);
						BSVariables.setCompGridCell(ranx, rany+2, 1);
						Battleship b = new Battleship();
						b.setName("b"+x);
						b.setFriendorFoe(1);
						b.setShipState(0);
						int[] c1 = new int[]{ranx, rany};
						int[] c2 = new int[]{ranx, rany-1};
						int[] c3 = new int[]{ranx, rany+1};
						int[] c4 = new int[]{ranx, rany+2};
						ArrayList<int[]> coord = new ArrayList<int[]>();
						coord.add(c1);
						coord.add(c2);
						coord.add(c3);
						coord.add(c4);
						b.setActiveCells(coord);
						ShipContents.addCompBattleships(b);
						break;
					}
				}
			}		
		}
	}
	public static void placeCruisers(){
		for(int x = 0; x < BSVariables.cruiser; x++){
			Random rand = new Random();
			int ranx = randomCoord();
			int rany = randomCoord();
			int orientation = rand.nextInt(1); // 0 for Horizontal 1 for Vertical
			int cell = BSVariables.getComputerSingleCellDetails(ranx, rany);
			while(true){
				if(cell == 0){
					if(orientation == 0){
						if(ranx < 2){ //Simple logic to avoid placing a ship off the edge of the playable area. ((Left Edge))
							ranx = randomCoord();
							rany = randomCoord();
							continue;
						}
						else if(ranx > (size-2)){ //Simple logic to avoid placing a ship off the edge of the playable area. ((Right Edge))
							ranx = randomCoord();
							rany = randomCoord();
							continue;
						}
						/*
						 *Sets the randomised cell grid to "Alive" for the first Aircraft carrier. 
						 */
						BSVariables.setCompGridCell(ranx, rany, 1);
						BSVariables.setCompGridCell(ranx-1, rany, 1);
						BSVariables.setCompGridCell(ranx+1, rany, 1);
						Cruiser c = new Cruiser();
						c.setName("c"+x);
						c.setFriendorFoe(1);
						c.setShipState(0);
						int[] c1 = new int[]{ranx, rany};
						int[] c2 = new int[]{ranx-1, rany-1};
						int[] c3 = new int[]{ranx+1, rany+1};
						ArrayList<int[]> coord = new ArrayList<int[]>();
						coord.add(c1);
						coord.add(c2);
						coord.add(c3);
						c.setActiveCells(coord);
						ShipContents.addCompCruisers(c);
						break;
					}else if(orientation == 1){
						if(rany < 2){
							ranx = randomCoord();
							rany = randomCoord();
							continue;
						}
						else if(rany > (size-2)){
							ranx = randomCoord();
							rany = randomCoord();
							continue;
						}
						BSVariables.setCompGridCell(ranx, rany, 1);
						BSVariables.setCompGridCell(ranx, rany-1, 1);
						BSVariables.setCompGridCell(ranx, rany+1, 1);
						Cruiser c = new Cruiser();
						c.setName("c"+x);
						c.setFriendorFoe(1);
						c.setShipState(0);
						int[] c1 = new int[]{ranx, rany};
						int[] c2 = new int[]{ranx, rany-1};
						int[] c3 = new int[]{ranx, rany+1};
						ArrayList<int[]> coord = new ArrayList<int[]>();
						coord.add(c1);
						coord.add(c2);
						coord.add(c3);
						c.setActiveCells(coord);
						ShipContents.addCompCruisers(c);
						break;
					}
				}
			}		
		}
	}
	public static void placeDestroyers(){
		for(int x = 0; x < BSVariables.destroyer; x++){
			Random rand = new Random();
			int ranx = randomCoord();
			int rany = randomCoord();
			int orientation = rand.nextInt(1); // 0 for Horizontal 1 for Vertical
			int cell = BSVariables.getComputerSingleCellDetails(ranx, rany);
			while(true){
				if(cell == 0){
					if(orientation == 0){
						if(ranx == 0){ //Simple logic to avoid placing a ship off the edge of the playable area. ((Left Edge))
							BSVariables.setCompGridCell(ranx, rany, 1);
							BSVariables.setCompGridCell(ranx+1, rany, 1);
							Destroyer d = new Destroyer();
							d.setName("d"+x);
							d.setFriendorFoe(1);
							d.setShipState(0);
							int[] c1 = new int[]{ranx, rany};
							int[] c2 = new int[]{ranx+1, rany};
							ArrayList<int[]> coord = new ArrayList<int[]>();
							coord.add(c1);
							coord.add(c2);
							d.setActiveCells(coord);
							ShipContents.addCompDestroyers(d);
							break;
						}
						else if(ranx <= (size-2)){ //Simple logic to avoid placing a ship off the edge of the playable area. ((Right Edge))
							BSVariables.setCompGridCell(ranx, rany, 1);
							BSVariables.setCompGridCell(ranx+1, rany, 1);
							Destroyer d = new Destroyer();
							d.setName("d"+x);
							d.setFriendorFoe(1);
							d.setShipState(0);
							int[] c1 = new int[]{ranx, rany};
							int[] c2 = new int[]{ranx+1, rany};
							ArrayList<int[]> coord = new ArrayList<int[]>();
							coord.add(c1);
							coord.add(c2);
							d.setActiveCells(coord);
							ShipContents.addCompDestroyers(d);
							break;
						}
						else{
							ranx = randomCoord();
							rany = randomCoord();
							continue;
						}
					}else if(orientation == 1){
						if(rany == 0){
							BSVariables.setCompGridCell(ranx, rany, 1);
							BSVariables.setCompGridCell(ranx, rany+1, 1);
							Destroyer d = new Destroyer();
							d.setName("d"+x);
							d.setFriendorFoe(1);
							d.setShipState(0);
							int[] c1 = new int[]{ranx, rany};
							int[] c2 = new int[]{ranx, rany+1};
							ArrayList<int[]> coord = new ArrayList<int[]>();
							coord.add(c1);
							coord.add(c2);
							d.setActiveCells(coord);
							ShipContents.addCompDestroyers(d);
							break;
						}
						else if(rany <= (size-2)){
							BSVariables.setCompGridCell(ranx, rany, 1);
							BSVariables.setCompGridCell(ranx, rany+1, 1);
							Destroyer d = new Destroyer();
							d.setName("d"+x);
							d.setFriendorFoe(1);
							d.setShipState(0);
							int[] c1 = new int[]{ranx, rany};
							int[] c2 = new int[]{ranx, rany+1};
							ArrayList<int[]> coord = new ArrayList<int[]>();
							coord.add(c1);
							coord.add(c2);
							d.setActiveCells(coord);
							ShipContents.addCompDestroyers(d);
							break;
						}else{
							ranx = randomCoord();
							rany = randomCoord();
							continue;
						}
					}
				}
			}		
		}
	}
	public static void placeAllComputerShips(){
		ComputerLogic.placeCarriers();
		ComputerLogic.placeBattleships();
		ComputerLogic.placeCruisers();
		ComputerLogic.placeDestroyers();
		PrimeWindow.updateCompShipsPanel();
		if(BSVariables.debug == true){
			System.out.println(ShipContents.computerShipsTotal() + " computer ships have been created");
		}
	}
}