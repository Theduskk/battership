package battership;

import java.util.ArrayList;

public class ShipContents {
	public static ArrayList<Ship> allPlayerCarriers = new ArrayList<Ship>();
	public static ArrayList<Ship> allPlayerBattleships = new ArrayList<Ship>();
	public static ArrayList<Ship> allPlayerCruisers = new ArrayList<Ship>();
	public static ArrayList<Ship> allPlayerDestroyers = new ArrayList<Ship>();

	public static void addPlayerCarriers(Ship o){
		allPlayerCarriers.add(o);
	}
	public static void addPlayerBattleships(Ship o){
		allPlayerBattleships.add(o);
	}
	public static void addPlayerCruisers(Ship o){
		allPlayerCruisers.add(o);
	}
	public static void addPlayerDestroyers(Ship o){
		allPlayerDestroyers.add(o);
	}
	public static int playerShipsTotal;
	/*
	 * getPlayerShipfromcell is used by the computer to detect which ship object has just been hit, as the random hits picks a random live cell and not a ship.
	 */
	public static Ship getPlayerShipfromcell(boolean shiphit){ //Function for Computer detection of ship objects.
		if(BSVariables.debug == true){
			System.out.println(BSVariables.getTime());
			System.out.println("Searching for ship coords");
			System.out.println("DS: " + allPlayerDestroyers.size());
			System.out.println("CR: " + allPlayerCruisers.size());
			System.out.println("BA: " + allPlayerBattleships.size());
			System.out.println("CA: " + allPlayerCarriers.size());
		}
		Ship currentShip;
		int x1 = ComputerLogic.lastHit.get(0)[0];
		int y1 = ComputerLogic.lastHit.get(0)[1];
		System.out.println("Finding ship");		
		/*
		 * The following lines scan through all the Ship Object arrays to find the one which has the last hit coordinates.
		 */
		while(true){
			if(allPlayerDestroyers.size() == 0){
				System.out.println("Destroyer Array Size 0");
				break;
			}else if(allPlayerDestroyers.size() == 1){
				currentShip = allPlayerDestroyers.get(0);
				if(scanShips(x1,y1,currentShip, shiphit) == true){
					return currentShip;
				}
			}else{
				System.out.println("Running d else");
				for(int i = 0; i <= allPlayerDestroyers.size(); i++){ 
					currentShip = allPlayerDestroyers.get(i); 
					if(scanShips(x1,y1,currentShip, shiphit) == true){
						return currentShip;
					}
				}
			}break;
		}
		while(true){
			if(allPlayerCruisers.size() == 0){
				System.out.println("Cruiser Array Size 0");
				break;
			}else if(allPlayerCruisers.size() == 1){
				currentShip = allPlayerCruisers.get(0);
				if(scanShips(x1,y1,currentShip, shiphit) == true){
					return currentShip;
				}break;
			}else{
				for(int i = 0; i <= allPlayerCruisers.size(); i++){ 
					currentShip = allPlayerCruisers.get(i); 
					if(scanShips(x1,y1,currentShip, shiphit) == true){
						return currentShip;
					}
				}break;
			}
		}
		while(true){
			if(allPlayerBattleships.size() == 0){
				System.out.println("Battleship Array Size 0");
				break;
			}else if(allPlayerBattleships.size() == 1){
				currentShip = allPlayerBattleships.get(0); //Calls the individual ship Object from the array
				if(scanShips(x1,y1,currentShip, shiphit) == true){
					return currentShip;
				}break;
			}else{
				for(int i = 0; i <= allPlayerBattleships.size(); i++){ //Opens the Destroyer array
					currentShip = allPlayerBattleships.get(i); //Calls the individual ship Object from the array
					if(scanShips(x1,y1,currentShip, shiphit) == true){
						return currentShip;
					}
				}break;
			}
		}
		while(true){
			if( allPlayerCarriers.size() == 0){
				System.out.println("Carrier Array Size 0");
				break;
			}else if(allPlayerCarriers.size() == 1){
				currentShip = allPlayerCarriers.get(0); //Calls the individual ship Object from the array
				if(scanShips(x1,y1,currentShip, shiphit) == true){
					return currentShip;
				}break;
			}else{
				for(int i = 0; i <= allPlayerCarriers.size(); i++){ //Opens the Destroyer array
					currentShip = allPlayerCarriers.get(i); //Calls the individual ship Object from the array
					if(scanShips(x1,y1,currentShip, shiphit) == true){
						return currentShip;
					}
				}break;
			}
		}
			System.out.println(BSVariables.getTime() + " - Error in ShipContents.getShipfromarray");	
		return null;
	}
	private static boolean scanShips(int x1, int y1,Ship currentShip, boolean shiphit){
		int x = 0;
		int y = 0;
		for(int i = 0; i < currentShip.activeCells.size(); i++){
			x = currentShip.activeCells.get(i)[0];
			y = currentShip.activeCells.get(i)[1];
			System.out.println("Trying: " + currentShip.getName()+ " x: " + x + " y: " + y);
			System.out.println(x1 + " " + x+ "  " + y1 + " " + y);
			if(x == x1 && y == y1){
				System.out.println("Found ship: " + currentShip.getType() + " " + currentShip.getName());
				if(shiphit == true){
					currentShip.addShipHits(1);
					System.out.println("Ship hits: " + currentShip.getShipHits());
					if(currentShip.getShipHits() == currentShip.getLength()){
						LeftGridPlayer.onSunk(currentShip);
					}
				}return true;
			}else{
				continue;
			}
		}
		return false;
	}
	public static ArrayList<Ship> allCompCarriers = new ArrayList<Ship>();
	public static ArrayList<Ship> allCompBattleships = new ArrayList<Ship>();
	public static ArrayList<Ship> allCompCruisers = new ArrayList<Ship>();
	public static ArrayList<Ship> allCompDestroyers = new ArrayList<Ship>();


	public static void addCompCarrier(Ship o){
		allCompCarriers.add(o);
	}
	public static void addCompBattleships(Ship o){

		allCompBattleships.add(o);
	}
	public static void addCompCruisers(Ship o){
		allCompCruisers.add(o);
	}
	public static void addCompDestroyers(Ship o){
		allCompDestroyers.add(o);
	}
	public static int computerShipsTotal(){
		int a = allCompCarriers.size() + allCompBattleships.size() + allCompCruisers.size() + allCompDestroyers.size();
		return a;
	}
}
