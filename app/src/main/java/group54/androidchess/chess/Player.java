package group54.androidchess.chess;

public class Player {

	private int pawnCount;					// Number of pawns remaining 
	private int nonPawnCount; 				// Number of nonpawns including the king
	private String color; 

	public Player(String color) 
	{
		pawnCount = 8; 
		nonPawnCount = 8;
		this.color = color;
	}
	
	/**
	 * Decrement pawnCount in the event one of our pawns are captured by opponent or reached promotion
	 */
	public void DecrementPawn()
	{
		pawnCount--;
	}
	
	/**
	 * Decrement count in the event one of our non-pawns are captured by opponent
	 */
	public void DecrementNonPawn()
	{
		nonPawnCount--;
	}
	
	/**
	 * Increment non-pawn count in the event of promotions
	 */
	public void IncrementNonPawn()
	{
		nonPawnCount++;
	}
	
	/**
	 * Check if the king is the only piece remaining
	 * @param gameBoard: current chess board
	 * @return boolean: true if the only piece remaining is the King
	 */
	public boolean onlyKingRemaining(Piece[][] gameBoard)
	{
		// If the board has any piece other than the kings, return false
		for(int x = 0; x < gameBoard.length-1; x++)
		{
			for(int y =0; y < gameBoard[x].length-1; y++)
			{
				if (gameBoard[x][y] == null || gameBoard[x][y].pieceColor == null)
					continue;
				else if (gameBoard[x][y].pieceColor.equals("white") ||
						 gameBoard[x][y].pieceColor.equals("black"))
				{
					if (!(gameBoard[x][y].pieceName.equals("wK")) &&
							!(gameBoard[x][y].pieceName.equals("bK")))
					{		
						return false;
					}
				}
			}
		}
		return true;
	}
	
	
}
