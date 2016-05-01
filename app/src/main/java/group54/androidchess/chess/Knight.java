package group54.androidchess.chess;

import group54.androidchess.Tile;

/**
 * @author Ammar Hussain
 * @author Danny Huynh
 */

public class Knight extends Piece {
		
	public Knight(String pieceName, String pieceColor){
		this.pieceName = pieceName;
		this.pieceColor = pieceColor;
	}
	
	
	
/**
 * @param gameBoard:
 * @param originalRow: Current row of piece
 * @param originalColumn: Current col of piece 
 * @param finalRow: Move piece to this row 
 * @param finalColumn: Move piece to this col
 * 
 * @return boolean: true if move is legitimate
 */
	public boolean legitMove(Tile[][] gameBoard, int originalRow,
							 int originalColumn, int finalRow, int finalColumn)
	{
		// Check if final position is the same as original position (piece did not move) 
		if ((finalRow == originalRow) && (finalColumn == originalColumn))
			return false; 
		
		// Check if final position is within the gameBoard 
		if ((finalRow > gameBoard.length - 1) || finalRow < 0)
			return false; 

		else if ((finalColumn > gameBoard[finalRow].length - 1) || finalColumn < 0)
			return false;
		
		// Check if position is L-shaped from all directions
		int rowDiff = Math.abs(finalRow - originalRow);
		int colDiff = Math.abs(finalColumn - originalColumn);
		if ((rowDiff == 2 && colDiff != 1) || (rowDiff == 1 && colDiff != 2))
		{
			return false;
		}
			
		else if((rowDiff != 1 && colDiff == 2) || (rowDiff != 2 && colDiff == 1))
		{
			return false;
		}
		
		// Check if the final position is currently being occupied by a piece of the same color
		if (gameBoard[finalRow][finalColumn].pieceColor == this.pieceColor)
		{
			return false; 
		}
		
		return true;
	}



	//@Override
	/**
	 * Check if enemy king is placed within check by this piece
	 */
	public boolean placeCheck(Tile[][] gameBoard, int currentRow, int currentCol) {
		// Find row and column of enemy king
		String enemyColor; 
		if (this.pieceColor.equals("white")) 
			enemyColor = "black";
		else 
			enemyColor = "white";
		
		int enemyKingRow = Chess.getKingRow(gameBoard, enemyColor);
		int enemyKingCol = Chess.getKingCol(gameBoard, enemyColor);
		
		// Compare distance of row and col between enemy king and knight
		if ((Math.abs(enemyKingRow - currentRow) == 2) && (Math.abs(enemyKingCol - currentCol) == 1))
		{
			return true;
		}
		else if ((Math.abs(enemyKingRow - currentRow) == 1) && (Math.abs(enemyKingCol - currentCol) == 2))
		{
			return true;
		}
		
		return false; 
	}
	

}
