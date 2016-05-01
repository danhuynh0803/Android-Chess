package group54.androidchess.chess;

public class Bishop extends Piece {
	

	
	public Bishop(String pieceName, String pieceColor){
		this.pieceName = pieceName;
		this.pieceColor = pieceColor;
	}
	
	
	//@Override
	public boolean legitMove(Piece[][] gameBoard, int originalRow, 
			int originalColumn, int finalRow, int finalColumn){
		
		int rowDiff = (finalRow - originalRow);
		int colDiff = (finalColumn - originalColumn);
		
		//to go down right diagonally
			if(  (rowDiff>0) && (colDiff>0) ){
				while( (originalRow != finalRow) && (originalColumn != finalColumn) ){
					originalRow++;
					originalColumn++;
					//to capture
					if(originalRow==finalRow &&originalColumn == finalColumn )//&&
							//(gameBoard[originalRow][originalColumn].pieceColor == "white" ||
							//gameBoard[originalRow][originalColumn].pieceColor == "black")){
					{return true;
						
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
				
				
		return false;
	}


	//@Override
	public boolean placeCheck(Piece[][] gameBoard, int currentRow, int currentCol) throws Exception{
		
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
					
					//to go down right diagonally
						if(  (rowDiff>0) && (colDiff>0) ){
							while( (currentRow != kingRow) && (currentCol != kingCol) ){
								currentRow++;
								currentCol++;
								//to capture
								if(currentRow==kingRow &&currentCol == kingCol )//&&
										//(gameBoard[currentRow][currentCol].pieceColor == "white" ||
										//gameBoard[currentRow][currentCol].pieceColor == "black")){
								{return true;
									
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
										
			}catch(Exception e){
				//Just to ignore the out of range errors
			}
		
		return false;
	}

}
