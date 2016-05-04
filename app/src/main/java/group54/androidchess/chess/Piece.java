/**
 * 
 */
package group54.androidchess.chess;

import group54.androidchess.Tile;

/**
 * @author Ammar Hussain
 * @author Danny Huynh
 *
 */
public abstract class Piece {

	
/**
 * {@literal} Name of the Piece selected
 * 
 */
	public String pieceName;	
	
/**
 * {@literal} Color of the Piece selected
 * 
 */
	public String pieceColor;
/**
 * determines if the piece is moving for the first time	
 * 
 */	
	public boolean firstMove;
	public boolean nonfirstMove;
/**
 * determines if the pawn can be captured using Enpassant Rule	
 */
	public boolean Enpassant;
	
	/*
	/**
	 * Index values that correspond to the position of the piece on the gameboard
	 *
	public int row; 
	public int col;
	*/
	
	/**
	 * 
	 * @param gameBoard current chess board
	 * @param originalColumn initial column
	 * @param originalRow initial row
	 * @param finalColumn final column
	 * @param finalRow final row
	 * @return true if the move is legit
	 */
	
	public boolean legitMove(Tile[][] gameBoard, int originalRow,
									  int originalColumn, int finalRow, int finalColumn)throws Exception{return false;}
	
	/**
	 * 
	 * @param gameBoard current chess board
	 * @param currentRow current selected row
	 * @param currentCol current selected column
	 * @return true if the piece puts a check on the opponent king
	 * @throws Exception will ignore any out of range checks
	 */
	
	public boolean placeCheck(Tile[][] gameBoard,int currentRow, int currentCol) throws Exception{return false;}

	/**
	 * 
	 * @param gameBoard the current game board
	 * @param kingColor Color of the King need
	 * @return the Row for the designated color's king
	 */
	public int getKingRow(Piece[][] gameBoard, String kingColor){
		
		for(int x=0; x<gameBoard.length-1; x++){
			for(int y=0; y<gameBoard[x].length-1; y++){
				if(gameBoard[x][y].pieceName.contains("K")
						&& gameBoard[x][y].pieceColor==kingColor){
					return x;
				}
			}
		}
		return 0;
		
	}
	/**
	 * 
	 * @param gameBoard the current game board
	 * @param kingColor Color of the King need
	 * @return the Column for the designated color's king
	 */
	public int getKingCol(Piece[][] gameBoard, String kingColor){
		
		for(int x=0; x<gameBoard.length-1; x++){
			for(int y=0; y<gameBoard[x].length-1; y++){
				if(gameBoard[x][y].pieceName.contains("K")
						&& gameBoard[x][y].pieceColor==kingColor){
					return y;
				}
			}
		}
		return 0;
		
	}
	
	/**
	 * 
	 * @param turn determines which player's turn it is
	 * @return the other player's turn
	 */
	public String changeTurn(String turn){
		if(turn == "white"){
			return "black";
		}
		else{
			return "white";
		}
	}
	
	
	
	
	
}
