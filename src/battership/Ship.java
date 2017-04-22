package battership;

import java.util.ArrayList;

public class Ship {
	private  String name;
	private  String type;	
	private  int length;
	private  int shipHits;
	/*
	 * 0 for a freshly spawned ship
	 * 1 for damaged
	 * 2 for sunk
	 */
	private  int shipState;
	/*Friend or foe variable ((Basically player or computer))
	 * 0 - Friend 
	 * 1- Foe
	 */
	private  int friendorfoe;
	public ArrayList<int[]> activeCells;
	public void setActiveCells(ArrayList<int[]> coord){
		this.activeCells = coord;
	}

	public void setName(String x){
		name = x;
	}
	public String getName(){
		return name;
	}
	public String getType(){
		return type;
	}
	public int getLength(){
		return length;
	}
	public  void setType(String x){
		type = x;
	}
	public void setLength(int x){
		length = x;
	}
	public void setShipState(int x){
		shipState = x;
	}
	public int getShipState(){
		return shipState;
	}
	public void setFriendorFoe(int x){
		friendorfoe = x;
	}
	public int getFoF(){
		return friendorfoe;
	}
	public int getShipHits(){
		return shipHits;
	}
	public void addShipHits(int x){
		shipHits = shipHits + x;
	}

}
