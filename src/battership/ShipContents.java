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
		while(true){
			/*
			 * The following lines scan through all the Ship Object arrays to find the one which has the last hit coordinates.
			 */
			if(allPlayerDestroyers.size() == 1){
				currentShip = allPlayerDestroyers.get(0);
				if(scanShips(x1,y1,currentShip, shiphit) == true){
					return currentShip;
				}
			}else{
				for(int x = 0; x <= allPlayerDestroyers.size(); x++){ 
					currentShip = allPlayerDestroyers.get(x); 
					if(scanShips(x1,y1,currentShip, shiphit) == true){
						return currentShip;
					}
				}
			}
			if(allPlayerCruisers.size() == 1){
				currentShip = allPlayerCruisers.get(0);
				if(scanShips(x1,y1,currentShip, shiphit) == true){
					return currentShip;
				}
			}else{
				for(int x = 0; x <= allPlayerCruisers.size(); x++){ 
					currentShip = allPlayerCruisers.get(x); 
					if(scanShips(x1,y1,currentShip, shiphit) == true){
						return currentShip;
					}
				}
			}
//			if(allPlayerBattleships.size() == 1){
//				currentShip = allPlayerBattleships.get(0); //Calls the individual ship Object from the array
//				if(scanShips(x1,y1,currentShip, shiphit) == true){
//					return currentShip;
//				}
//			}
//			else{
//				for(int x = 0; x <= allPlayerBattleships.size(); x++){ //Opens the Destroyer array
//					currentShip = allPlayerBattleships.get(x); //Calls the individual ship Object from the array
//					if(scanShips(x1,y1,currentShip, shiphit) == true){
//						return currentShip;
//					}
//				}	
//			}
//			if(allPlayerCarriers.size() == 1){
//				currentShip = allPlayerCarriers.get(0); //Calls the individual ship Object from the array
//				if(scanShips(x1,y1,currentShip, shiphit) == true){
//					return currentShip;
//				}
//			}else{
//				for(int x = 0; x <= allPlayerCarriers.size(); x++){ //Opens the Destroyer array
//					currentShip = allPlayerCarriers.get(x); //Calls the individual ship Object from the array
//					if(scanShips(x1,y1,currentShip, shiphit) == true){
//						return currentShip;
//					}
//				}
//			}
			System.out.println(BSVariables.getTime() + " - Error in ShipContents.getShipfromarray");	
		}
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
	public static ArrayList<Object> allCompCarriers = new ArrayList<Object>();
	public static ArrayList<Object> allCompBattleships = new ArrayList<Object>();
	public static ArrayList<Object> allCompCruisers = new ArrayList<Object>();
	public static ArrayList<Object> allCompDestroyers = new ArrayList<Object>();


	public static void addCompCarrier(Object o){
		allCompCarriers.add(o);
	}
	public static void addCompBattleships(Object o){
		allCompCarriers.add(o);
	}
	public static void addCompCruisers(Object o){
		allCompCarriers.add(o);
	}
	public static void addCompDestroyers(Object o){
		allCompCarriers.add(o);
	}
	public static int computerShipsTotal(){
		int a = allCompCarriers.size() + allCompBattleships.size() + allCompCruisers.size() + allCompDestroyers.size();
		return a;
	}
}
