package battership;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class RightGridComputer {
	public static Integer coordx = 0;
	public static Integer coordy = 0;
	public static JPanel main(){
		JPanel panel = new JPanel();
		JButton button[][] = new JButton[BSVariables.gridSize][BSVariables.gridSize];
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
						button[x][y].setBackground(new Color(91,162,255));
						button[x][y].setForeground(Color.BLACK);
						button[x][y].setBorderPainted(true);
						button[x][y].setFocusPainted(false);
						button[x][y].setName(coordx.toString() + ":" + coordy.toString());
						button[x][y].addActionListener(new ActionListener(){
							@Override
							public void actionPerformed(ActionEvent e) {
								String[] name = ((JComponent) e.getSource()).getName().split(":");
								Integer x = Integer.valueOf(name[0]);
								Integer y = Integer.valueOf(name[1]);
								if(BSVariables.debug == true){
									System.out.println(x + "" + y);
								}
								if(BSVariables.playingGame == true && ComputerLogic.playerTurn == true){
										if(BSVariables.compGrid[x][y] == 1){
											((JComponent) e.getSource()).setBackground(Color.RED);
											BSVariables.compGrid[x][y] = 3;
											ComputerLogic.playerTurn = false;
											ComputerLogic.computerTurn();
										}else if(BSVariables.compGrid[x][y] == 0){
											((JComponent) e.getSource()).setBackground(Color.GRAY);
											BSVariables.compGrid[x][y] = 4;
											ComputerLogic.playerTurn = false;
											ComputerLogic.computerTurn();
										}										
								}
							}
						});
					}
				}
			}
		}catch(IndexOutOfBoundsException e){
			System.out.println(e.getMessage());
		}
		if(BSVariables.debug == true){
			for(int i = 0; i < BSVariables.gridSize; i++)
			{
				for(int j = 0; j < BSVariables.gridSize; j++)
				{
					System.out.printf("%5d ", BSVariables.compGrid[i][j]);
				}
			}
		}
		return panel;	
	}
	abstract class TimerActionListener implements ActionListener{
		public void actionPrformed(ActionEvent e){
			System.out.println("AcListener");
		}
	}
}

