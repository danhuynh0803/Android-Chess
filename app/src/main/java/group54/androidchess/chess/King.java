package group54.androidchess.chess;

/**
 * @author Ammar Hussain
 * @author Danny Huynh
 *
 */

public class King extends Piece {	
	
	private boolean willCastle = false;
	private boolean inCheck = false; 
	
	public King(String pieceName, String pieceColor){
		this.pieceName = pieceName;
		this.pieceColor = pieceColor;
		this.firstMove = true;
	}
	
	/**
	 * King can only castle if the following conditions are met: 
	 * - King and rook have not yet moved. 
	 * - Columns between the two are not occupied by any pieces.
	 * - Cannot castle to and from check.
	 * @param gameBoard
	 * @param originalRow
	 * @param originalColumn
	 * @param finalColumn
	 * @return boolean: true if king and rook satisfy castling conditions
	 */
	private boolean canCastle(Piece[][] gameBoard, int originalRow, 
			int originalColumn, int finalColumn)
	{
		// King can only castle if it and the rook has not yet moved
		if (this.firstMove == false || inCheck == true)
			return false;
		
		// rookColumn stores the location of the rook that is castling with the king
		int rookColumn = 0;
		int colDiff = finalColumn - originalColumn; 
		// Check if right side is free of other pieces
		if (colDiff > 0)
		{
			while(originalColumn != finalColumn){
				originalColumn++;
				if(gameBoard[originalRow][originalColumn].pieceColor == "white" ||
						gameBoard[originalRow][originalColumn].pieceColor == "black"){
					return false;
				}
			}
			rookColumn = 7;
		}
		// Check if left side is free of other pieces		
		else if (colDiff < 0)
		{
			while(originalColumn != 1){
				originalColumn--;
				if(gameBoard[originalRow][originalColumn].pieceColor == "white" ||
						gameBoard[originalRow][originalColumn].pieceColor == "black"){
					return false;
				}
			}
			rookColumn = 0;
		}

		// Check if the rooks on that side has moved and is of the same color
		if (this.pieceColor.equals("white"))
		{
			if (gameBoard[originalRow][rookColumn].pieceName.equals("wR") &&
					gameBoard[originalRow][rookColumn].firstMove == true)
				return true; 
		}
		else 
		{
			if (gameBoard[originalRow][rookColumn].pieceName.equals("bR") &&
					gameBoard[originalRow][rookColumn].firstMove == true)
				return true;
		}
		
		return false;
	}

	//@Override
	public boolean legitMove(Piece[][] gameBoard, int originalRow, 
			int originalColumn, int finalRow, int finalColumn){

		// If final column is one of the two castle positions on either side, we check if the king can castle
		if (Math.abs(finalColumn - originalColumn) == 2 && originalRow == finalRow)
		{
			if (canCastle(gameBoard, originalRow, originalColumn, finalColumn))
			{
				this.firstMove = false;
				willCastle = true;
				return true;
			}
			else 
				return false; 
		}
		
		//to go up
		if(originalRow-1 == finalRow && originalColumn==finalColumn){
			this.firstMove = false;
			return true;
		}
		//to go down
		else if(originalRow+1 == finalRow && originalColumn==finalColumn){
			this.firstMove = false;
			return true;
		}
		//to go right
		else if(originalRow == finalRow && originalColumn+1==finalColumn){
			this.firstMove = false;
			return true;
		}
		//to go left
		else if(originalRow == finalRow && originalColumn-1==finalColumn){
			this.firstMove = false;
			return true;
		}
		//to go up right diagonally
		else if(originalRow-1 == finalRow && originalColumn+1==finalColumn){
			this.firstMove = false;
			return true;
		}
		//to go up left diagonally
		else if(originalRow-1 == finalRow && originalColumn-1==finalColumn){
			this.firstMove = false;
			return true;
		}
		//to go down right diagonally
		else if(originalRow+1 == finalRow && originalColumn+1==finalColumn){
			this.firstMove = false;
			return true;
		}
		//to go down left diagonally
		else if(originalRow+1 == finalRow && originalColumn-1==finalColumn){
			this.firstMove = false;
			return true;
		}
		return false;
	}

	//@Override
	public boolean placeCheck(Piece[][] gameBoard, int currentRow, int currentCol) {
		// King can never place enemy king in check and this will always return false
		return false;
	}	
	
	// Check whether the king has received a castle command
	public boolean isCastling()
	{
		return willCastle;
	}
	
	// Prevent king from castling anymore within the game
	public void removeCastle()
	{
		willCastle = false;
	}
	
	public void setCheck(boolean bool)
	{
		inCheck = bool;
	}
	
	/**
	 * If king is in check for all directions than it is checkmate
	 * @return boolean: true if king is in checkmate
	 */
	public boolean isCheckMate(Piece[][] gameBoard, int oRow, int oCol, String color) 
	{
		boolean[] checkArray = new boolean[8];
		// Check if top three spaces of king are free
		// 1. Top left spot: 
		if (oRow + 1 > gameBoard.length -1 || oCol - 1 < 0)
			checkArray[0] = true;
		else
		{
			if (checkmateHelper(gameBoard, oRow - 1, oCol + 1, color))
				checkArray[0] = true;
		}
		// 2. Top spot: 
		if (oRow + 1 > gameBoard.length - 1) 
			checkArray[1] = true;
		else 
		{
			if (checkmateHelper(gameBoard, oRow + 1, oCol, color))
				checkArray[1] = true;
		}
		// 3. Top right spot:
		if (oRow + 1 > gameBoard.length - 1 || oCol + 1 > gameBoard[oRow].length)
		{
			checkArray[2] = true;
		}
		else 
		{
			if (checkmateHelper(gameBoard, oRow +1, oCol + 1, color))
				checkArray[2] = true;
		}
		
		// Check if all spots are being attacked by an enemy piece 
		for (int i = 0; i < checkArray.length; i++)
		{
			// There is at least one position available for king to move safely to
			if (checkArray[i] == false)
				return false;
		}
		return true;
	}
	
	/**
	 * Helper method to call all check methods
	 * @return boolean: true if any of the enemies pieces is attacking that position
	 */
	public boolean checkmateHelper(Piece[][] gameBoard, int oRow, int oCol, String color)
	{
		if (checkByPawn(gameBoard, oRow, oCol, color) || 
				checkByKnight(gameBoard, oRow, oCol, color) ||
				checkByRook(gameBoard, oRow, oCol, color) || 
				checkByBishop(gameBoard, oRow, oCol, color))
		{
			return true;
		}
				
		return false;
	}
	
	/**
	 * Find if that position is being attacked by enemy pawn
	 * @param gameBoard
	 * @param oRow
	 * @param oCol
	 * @param color
	 * @return boolean:
	 */
	public boolean checkByPawn(Piece[][] gameBoard, int oRow, int oCol, String color) 
	{	
		// Depending on our color we only need to check top/bottom side of pawn
		if (color.equals("white"))
		{
			// Check if top side has black pawns 
			if (gameBoard[oRow + 1][oCol - 1].pieceName.equals("bP") ||
					gameBoard[oRow + 1][oCol + 1].pieceName.equals("bP"))
				return true;
		}
		else 
		{
			// Check if bottom side has white pawns 
			if(gameBoard[oRow - 1][oCol - 1].pieceName.equals("wP") ||
					gameBoard[oRow - 1][oCol + 1].pieceName.equals("wP"))
				return true;
		}
	
		return false;
	}

	public boolean checkByKnight(Piece[][] gameBoard, int oRow, int oCol, String color)
	{
		return false;
	}
	
	public boolean checkByRook(Piece[][] gameBoard, int oRow, int oCol, String color)
	{
		String oppositeColor; 
		if (color.equals("white"))
			oppositeColor = "black";
		else 
			oppositeColor = "white";
				
		// Check top
		int topRow = oRow + 1; 
		while (topRow < gameBoard.length - 1)
		{
			if (gameBoard[topRow][oCol].pieceColor.equals(this.pieceColor))
			{
				return false;
			}
			else if (gameBoard[topRow][oCol].pieceColor.equals("oppositeColor"))
			{
				if (gameBoard[topRow][oCol].pieceName.equals(oppositeColor.charAt(0) + "R") ||
						gameBoard[topRow][oCol].pieceName.equals(oppositeColor.charAt(0) + "Q"))
				{
					return true;
				}
			}
			topRow++;
		}
		// Check bottom row
		int botRow = oRow - 1;
		while (botRow > 0)
		{
			if (gameBoard[botRow][oCol].pieceColor.equals(this.pieceColor))
			{
				return false;
			}
			else if (gameBoard[botRow][oCol].pieceColor.equals("oppositeColor"))
			{
				if (gameBoard[topRow][oCol].pieceName.equals(oppositeColor.charAt(0) + "R") ||
						gameBoard[topRow][oCol].pieceName.equals(oppositeColor.charAt(0) + "Q"))
				{
					return true;
				}
			}
			botRow--;
		}
		// Check left columns
		int leftCol = oCol - 1;
		while(leftCol > 0)
		{
			if (gameBoard[oRow][leftCol].pieceColor.equals(this.pieceColor))
			{
				return false;
			}
			else if (gameBoard[oRow][leftCol].pieceColor.equals("oppositeColor"))
			{
				if (gameBoard[oRow][leftCol].pieceName.equals(oppositeColor.charAt(0) + "R") ||
						gameBoard[oRow][leftCol].pieceName.equals(oppositeColor.charAt(0) + "Q"))
				{
					return true;
				}
			}
			leftCol--;
		}
		int rightCol = oCol + 1;
		while(rightCol < gameBoard[oRow].length - 1)
		{
			if (gameBoard[oRow][rightCol].pieceColor.equals(this.pieceColor))
			{
				return false;
			}
			else if (gameBoard[oRow][rightCol].pieceColor.equals("oppositeColor"))
			{
				if (gameBoard[oRow][rightCol].pieceName.equals(oppositeColor.charAt(0) + "R") ||
						gameBoard[oRow][rightCol].pieceName.equals(oppositeColor.charAt(0) + "Q"))
				{
					return true;
				}
			}
			rightCol--;
		}
		
		return false;

	}

	public boolean checkByBishop(Piece[][] gameBoard, int oRow, int oCol, String color)
	{
		return false;
	}
	
}
