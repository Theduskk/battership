package battership;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class BSVariables {
	//TESTING/DEVELOPMENT ONLY. SET TO FALSE FOR NO CONSOLE RESPONSES
	public static boolean debug = true;
	//Default grid size.
	public static int gridSize = 10; //Default 10 can be changed by gridsize slider 
	public static int diffLevel = 50; //default 50 can be changed by the difficulty slider
	/*
	 * Background X/Y arrays which contain the location of the active cells
	 * and the current "state" of the cells, 
	 * 0 for empty
	 * 1 for live
	 * 2 for hit
	 * 3 for sunk
	 */
	public static int[][] playerGrid;
	public static int[][] compGrid;
	/*Defines quantity of all ships
	 * 	this level has been set for both teams.
	 * 	can be modified in the settings tab.
	 */
	public static int carrier = 0; //Default 3
	public static int battleship = 0; //Default 4
	public static int cruiser = 2; //Default 5
	public static int destroyer = 0; //Default 6
	/*
	 * Array lists to contain the information behind the active location
	 * of the ships in play.
	 */

	protected static boolean playingGame = false;

	
	public static String getTime(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String str = sdf.format(cal.getTime()) + "-";
		return str;
	}
	public static int getShips(String x) {
		if(x.equalsIgnoreCase("carrier")){
			return carrier;
		}else if(x.equalsIgnoreCase("battleship")){
			return battleship;
		}else if(x.equalsIgnoreCase("cruiser")){
			return cruiser;
		}else if(x.equalsIgnoreCase("destroyer")){
			return destroyer;
		}
		System.out.println("Err BSVariables getShips: " + x);
		return 0;
	}
	public static void setShips(String x, int i) {
		try{
		if(x.equalsIgnoreCase("carrier")){
			carrier = i;
		}else if(x.equalsIgnoreCase("battleship")){
			battleship = i;
		}else if(x.equalsIgnoreCase("cruiser")){
			cruiser = i;
		}else if(x.equalsIgnoreCase("destroyer")){
			destroyer = i;
		}
		System.out.println("Err BSVariables setShips");
	}catch(Exception e){
		System.out.println(e.getMessage());
	}
	}
	public static void gridsInitialise(){
		playerGrid = new int[gridSize][gridSize];
		compGrid = new int[gridSize][gridSize];
	}
	public static int getPlayerSingleCellDetails(int x, int y){
		int details = playerGrid[x][y];
		return details;
	}
	public static int getComputerSingleCellDetails(int x, int y){
		int details = playerGrid[x][y];
		return details;
	}
	public static void setPlayerGridCell(int x, int y, int z){
		playerGrid[x][y] = z;
	}
	public static void setCompGridCell(int x, int y, int z){
		compGrid[x][y] = z;
	}
}
