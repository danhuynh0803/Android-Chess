package group54.androidchess.chess;


import java.io.Serializable;

import group54.androidchess.Tile;

/**
 * @author Ammar Hussain
 * @author Danny Huynh
 *
 */
public class Queen extends Piece implements Serializable{

	private static final long serialVersionUID = 0L;
	
	public Queen(String pieceName, String pieceColor){
		this.pieceName = pieceName;
		this.pieceColor = pieceColor;
	}
	
	

	//@Override
	public boolean legitMove(Tile[][] gameBoard, int originalRow,
							 int originalColumn, int finalRow, int finalColumn){
		
		int rowDiff = (finalRow - originalRow);
		int colDiff = (finalColumn - originalColumn);
		
		//to go down right diagonally
			if(  (rowDiff>0) && (colDiff>0) ){
				while( (originalRow != finalRow) && (originalColumn != finalColumn) ){
					originalRow++;
					originalColumn++;
					//to capture
					if(originalRow==finalRow &&originalColumn == finalColumn ){
						return true;
					}
					
					//just to move
					else if(gameBoard[originalRow][originalColumn].pieceColor == "white" ||
							gameBoard[originalRow][originalColumn].pieceColor == "black"){
						return false;
					}
				}
				//return true;
				
				
			}
			//to go down left diagonally
			else if( (rowDiff>0) && (colDiff<0)){
				while( (originalRow != finalRow) && (originalColumn != finalColumn) ){
					originalRow++;
					originalColumn--;
					
					//to capture
					if(originalRow==finalRow &&originalColumn == finalColumn){
						return true;
					}
					
					//to move
					else if(gameBoard[originalRow][originalColumn].pieceColor == "white" ||
							gameBoard[originalRow][originalColumn].pieceColor == "black"){
						return false;
					}
				}
				//return true;
			}
			//to go up right diagonally
			else if( (rowDiff<0) && (colDiff>0) ){
			
				while( (originalRow != finalRow) && (originalColumn != finalColumn) ){
					originalRow--;
					originalColumn++;
					
					//to capture
					if(originalRow==finalRow &&originalColumn == finalColumn){
						return true;
					}
					
					//to move
					else if(gameBoard[originalRow][originalColumn].pieceColor == "white" ||
							gameBoard[originalRow][originalColumn].pieceColor == "black"){
						return false;
					}
				}
				//return true;
			}
			//to go up left diagonally
			else if( (rowDiff<0) && (colDiff<0) ){
				while( (originalRow != finalRow) && (originalColumn != finalColumn) ){
					originalRow--;
					originalColumn--;
					
					//to capture
					if(originalRow==finalRow &&originalColumn == finalColumn){
						return true;
					}
					
					//to move
					if(gameBoard[originalRow][originalColumn].pieceColor == "white" ||
							gameBoard[originalRow][originalColumn].pieceColor == "black"){
						return false;						
					}
					
				}
				//return true;
			}
			
			//to go right
			else if( rowDiff == 0 && colDiff>0){
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
		


	//@Override
	public boolean placeCheck(Tile[][] gameBoard, int currentRow, int currentCol) {
		
		try{
			String currentTurn;
			String oppositeTurn;
			currentTurn = gameBoard[currentRow][currentCol].pieceColor;
			oppositeTurn = changeTurn(currentTurn);
				//get kings position
				int kingRow = Chess.getKingRow(gameBoard, oppositeTurn);
				int kingCol = Chess.getKingCol(gameBoard, oppositeTurn);
				
				int rowDiff = (kingRow - currentRow);
				int colDiff = (kingCol - currentCol);
				
				
				//to go down right diagonally
				if(  (rowDiff>0) && (colDiff>0) ){
					while( (currentRow != kingRow) && (currentCol != kingCol) ){
						currentRow++;
						currentCol++;
						//to capture
						if(currentRow==kingRow &&currentCol == kingCol ){
							return true;
						}
						
						//just to move
						else if(gameBoard[currentRow][currentCol].pieceColor == "white" ||
								gameBoard[currentRow][currentCol].pieceColor == "black"){
							return false;
						}
					}
					//return true;
					
					
				}
				//to go down left diagonally
				else if( (rowDiff>0) && (colDiff<0)){
					while( (currentRow != kingRow) && (currentCol != kingCol) ){
						currentRow++;
						currentCol--;
						
						//to capture
						if(currentRow==kingRow &&currentCol == kingCol){
							return true;
						}
						
						//to move
						else if(gameBoard[currentRow][currentCol].pieceColor == "white" ||
								gameBoard[currentRow][currentCol].pieceColor == "black"){
							return false;
						}
					}
					//return true;
				}
				//to go up right diagonally
				else if( (rowDiff<0) && (colDiff>0) ){
				
					while( (currentRow != kingRow) && (currentCol != kingCol) ){
						currentRow--;
						currentCol++;
						
						//to capture
						if(currentRow==kingRow &&currentCol == kingCol){
							return true;
						}
						
						//to move
						else if(gameBoard[currentRow][currentCol].pieceColor == "white" ||
								gameBoard[currentRow][currentCol].pieceColor == "black"){
							return false;
						}
					}
					//return true;
				}
				//to go up left diagonally
				else if( (rowDiff<0) && (colDiff<0) ){
					while( (currentRow != kingRow) && (currentCol != kingCol) ){
						currentRow--;
						currentCol--;
						
						//to capture
						if(currentRow==kingRow &&currentCol == kingCol){
							return true;
						}
						
						//to move
						if(gameBoard[currentRow][currentCol].pieceColor == "white" ||
								gameBoard[currentRow][currentCol].pieceColor == "black"){
							return false;						
						}
						
					}
					//return true;
				}
				
				//to go right
				else if( rowDiff == 0 && colDiff>0){
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
