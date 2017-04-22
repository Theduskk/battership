package battership;


public class BSMain {

	public static void main(String[] args) {
		ComputerLogic.playerTurn = false;
		BSVariables.gridsInitialise();
		PrimeWindow.mainFrame();
		ComputerLogic.placeAllComputerShips();
	}
}



