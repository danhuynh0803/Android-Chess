/**
 * 
 */
package group54.androidchess.chess;;

/**
 * @author Ammar Hussain
 * @author Danny Huynh
 */
public class BlackSpaces extends Piece {

	
	public BlackSpaces(String pieceName){
		this.pieceName = pieceName;
	}
	
	//@Override
	public boolean legitMove(Piece[][] gameBoard, int originalRow, 
			int originalColumn, int finalRow, int finalColumn){
		// TODO Auto-generated method stub
		return false;
	}

	//@Override
	public boolean placeCheck(Piece[][] gameBoard, int currentRow, int currentCol) {
		// TODO Auto-generated method stub
		return false;
	}

}
