/**
 * 
 */
package group54.androidchess.chess;

/**
 * @author Ammar Hussain
 * @author Danny Huynh
 *
 */
public class Rook extends Piece {

	
	public Rook(String pieceName, String pieceColor){
		this.pieceName = pieceName;
		this.pieceColor = pieceColor;
		this.firstMove = true;
	}
	
	
	/**
	 * Rooks can only move on a single row or column.
	 */
	@Override
	public boolean legitMove(Piece[][] gameBoard, int originalRow, 
			int originalColumn, int finalRow, int finalColumn)
	{
		
		int rowDiff = (finalRow - originalRow);
		int colDiff = (finalColumn - originalColumn);
		
		//to go right
		if( rowDiff == 0 && colDiff>0){
			while(originalColumn != finalColumn){
				originalColumn++;
				//to capture the piece
				if(originalColumn ==finalColumn){
					return true;
				}
				if(gameBoard[originalRow][originalColumn].pieceColor == "white" ||
						gameBoard[originalRow][originalColumn].pieceColor == "black"){
					return false;
				}
			}
			
		}
		//to go left
		else if( rowDiff == 0 && colDiff<0){
			while(originalColumn !=finalColumn){
				originalColumn--;
				//to capture the piece
				if(originalColumn == finalColumn){
					return true;
				}
				if(gameBoard[originalRow][originalColumn].pieceColor == "white" ||
						gameBoard[originalRow][originalColumn].pieceColor == "black"){
					return false;
				}
			}
		}
		//to go up
		else if( rowDiff<0 && colDiff==0){
			while(originalRow != finalRow){
				originalRow--;
				//to capture the piece
				if(originalRow == finalRow){
					return true;
				}
				if(gameBoard[originalRow][originalColumn].pieceColor == "white" ||
						gameBoard[originalRow][originalColumn].pieceColor == "black"){
					return false;
				}
			}
			
		}
		//to go down
		else if( rowDiff>0 && colDiff==0){
			while(originalRow !=finalRow){
				originalRow++;
				//to capture the piece
				if(originalRow == finalRow){
					return true;
				}
				if(gameBoard[originalRow][originalColumn].pieceColor == "white" ||
						gameBoard[originalRow][originalColumn].pieceColor == "black"){
					return false;
				}
			}
		}
			
			
	return false;
}



	@Override
	public boolean placeCheck(Piece[][] gameBoard, int currentRow, int currentCol) 
	{
		try{
			String currentTurn;
			String oppositeTurn;
			currentTurn = gameBoard[currentRow][currentCol].pieceColor;
			oppositeTurn = changeTurn(currentTurn);
				//get kings position
				int kingRow = getKingRow(gameBoard, oppositeTurn);
				int kingCol = getKingCol(gameBoard, oppositeTurn);
				
				int rowDiff = (kingRow - currentRow);
				int colDiff = (kingCol - currentCol);
				
				//to go right
				if( rowDiff == 0 && colDiff>0){
					while(currentCol != kingCol){
						currentCol++;
						//to capture the piece
						if(currentCol ==kingCol){
							return true;
						}
						if(gameBoard[currentRow][currentCol].pieceColor == "white" ||
								gameBoard[currentRow][currentCol].pieceColor == "black"){
							return false;
						}
					}
					
				}
				//to go left
				else if( rowDiff == 0 && colDiff<0){
					while(currentCol !=kingCol){
						currentCol--;
						//to capture the piece
						if(currentCol == kingCol){
							return true;
						}
						if(gameBoard[currentRow][currentCol].pieceColor == "white" ||
								gameBoard[currentRow][currentCol].pieceColor == "black"){
							return false;
						}
					}
				}
				//to go up
				else if( rowDiff<0 && colDiff==0){
					while(currentRow != kingRow){
						currentRow--;
						//to capture the piece
						if(currentRow == kingRow){
							return true;
						}
						if(gameBoard[currentRow][currentCol].pieceColor == "white" ||
								gameBoard[currentRow][currentCol].pieceColor == "black"){
							return false;
						}
					}
					
				}
				//to go down
				else if( rowDiff>0 && colDiff==0){
					while(currentRow !=kingRow){
						currentRow++;
						//to capture the piece
						if(currentRow == kingRow){
							return true;
						}
						if(gameBoard[currentRow][currentCol].pieceColor == "white" ||
								gameBoard[currentRow][currentCol].pieceColor == "black"){
							return false;
						}
					}
				}
		
					
					
		}catch(Exception e){
			//Just to ignore the out of range errors
		}
	
	return false;
	}
}
