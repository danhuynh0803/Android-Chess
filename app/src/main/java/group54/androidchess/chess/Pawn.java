/**
 * 
 */
package group54.androidchess.chess;

/**
 * @author Ammar Hussain
 * @author Danny Huynh
 *
 */
public class Pawn extends Piece {
	
	
	public Pawn(String pieceName, String pieceColor){
		this.pieceName = pieceName;
		this.pieceColor = pieceColor;
		this.firstMove = true;
		this.Enpassant = false;
	}
	
	

	@Override
	public boolean legitMove(Piece[][] gameBoard, int originalRow, 
		int originalColumn, int finalRow, int finalColumn){
		// TODO Auto-generated method stub
		
		//only for white pawns
		if(gameBoard[originalRow][originalColumn].pieceColor =="white"){
			//if it is the first move
			if(gameBoard[originalRow][originalColumn].firstMove ==true){
				//to only move 1 rank
				if(  ((originalRow-1) == finalRow ) && (originalColumn== finalColumn) 
						&& (gameBoard[finalRow][finalColumn].pieceColor != "black") ){
					gameBoard[originalRow][originalColumn].firstMove = false;
					return true;
				}
				//to move 2 ranks
				if( originalRow-2 == finalRow && (originalColumn== finalColumn 
						&& (gameBoard[finalRow][finalColumn].pieceColor != "black"))){
					gameBoard[originalRow][originalColumn].firstMove = false;
					gameBoard[originalRow][originalColumn].Enpassant = true;
					return true;
				}
				//to capture in the first move
				else if( (gameBoard[finalRow][finalColumn].pieceColor=="black") &&
						(gameBoard[originalRow-1][originalColumn+1] == gameBoard[finalRow][finalColumn]
						|| gameBoard[originalRow-1][originalColumn-1] == gameBoard[finalRow][finalColumn])){
					gameBoard[originalRow][originalColumn].firstMove = false;
					gameBoard[originalRow][originalColumn].Enpassant = false;
					return true;
				}
				
			}
			//if it is not the first move
			else if(gameBoard[originalRow][originalColumn].firstMove ==false){
				//to move 1 rank
				if(originalRow-1 == finalRow && (originalColumn== finalColumn)
						&& (gameBoard[finalRow][finalColumn].pieceColor != "black")){
					gameBoard[originalRow][originalColumn].Enpassant = false;
					return true;
				}
				//to capture a piece
				else if( (gameBoard[finalRow][finalColumn].pieceColor=="black") &&
						(gameBoard[originalRow-1][originalColumn+1] == gameBoard[finalRow][finalColumn]
						|| gameBoard[originalRow-1][originalColumn-1] == gameBoard[finalRow][finalColumn])){
					gameBoard[originalRow][originalColumn].Enpassant = false;
					return true;
				}
				//to capture via enpassant
				else if( (gameBoard[originalRow-1][originalColumn+1] == gameBoard[finalRow][finalColumn]
						|| gameBoard[originalRow-1][originalColumn-1] == gameBoard[finalRow][finalColumn])
						&&(gameBoard[finalRow+1][finalColumn].Enpassant==true)){
					return true;
					
				}
			}
		}
		//only for black pawns
		else if(gameBoard[originalRow][originalColumn].pieceColor =="black"){
			//if it is the first move
			if(gameBoard[originalRow][originalColumn].firstMove ==true){
				//to only move 1 rank
				if(  (originalRow+1) == finalRow && (originalColumn== finalColumn) 
						&& (gameBoard[finalRow][finalColumn].pieceColor != "white")){
					gameBoard[originalRow][originalColumn].firstMove = false;
					return true;
				}
				//to move 2 ranks
				if( originalRow+2 == finalRow && (originalColumn== finalColumn)
						&& (gameBoard[finalRow][finalColumn].pieceColor != "white")){
					gameBoard[originalRow][originalColumn].firstMove = false;
					gameBoard[originalRow][originalColumn].Enpassant = true;
					return true;
				}
				//to capture in the first move
				else if( (gameBoard[finalRow][finalColumn].pieceColor=="white") &&
						(gameBoard[originalRow+1][originalColumn+1] == gameBoard[finalRow][finalColumn]
						|| gameBoard[originalRow+1][originalColumn-1] == gameBoard[finalRow][finalColumn])){
					gameBoard[originalRow][originalColumn].firstMove = false;
					return true;
				}
				
			}
			//if it is not the first move
			else if(gameBoard[originalRow][originalColumn].firstMove ==false){
				//to only move 1 rank
				if(originalRow+1 == finalRow && (originalColumn== finalColumn)
						&& (gameBoard[finalRow][finalColumn].pieceColor != "white")){
					gameBoard[originalRow][originalColumn].Enpassant = false;
					return true;
				}
				//to capture a piece
				else if( (gameBoard[finalRow][finalColumn].pieceColor=="white") &&
						(gameBoard[originalRow+1][originalColumn+1] == gameBoard[finalRow][finalColumn]
						|| gameBoard[originalRow+1][originalColumn-1] == gameBoard[finalRow][finalColumn])){
					gameBoard[originalRow][originalColumn].Enpassant = false;
					return true;
				}
				//to capture via enpassant
				else if( (gameBoard[originalRow+1][originalColumn+1] == gameBoard[finalRow][finalColumn]
						|| gameBoard[originalRow+1][originalColumn-1] == gameBoard[finalRow][finalColumn])
						&&(gameBoard[finalRow-1][finalColumn].Enpassant==true)){
					return true;
					
				}
			}
		}
	
			return false;
	}



	@Override
	public boolean placeCheck(Piece[][] gameBoard, int currentRow, int currentCol) throws Exception{
		
		try{
			//white pawn placing check on black king
			if(gameBoard[currentRow][currentCol].pieceColor =="white"){
				if( (gameBoard[currentRow-1][currentCol+1].pieceName == "bK")
						|| (gameBoard[currentRow-1][currentCol-1].pieceName == "bK") ){
					return true;
				}
			}
			//black pawn placing check on white king
			else if(gameBoard[currentRow][currentCol].pieceColor =="black"){
				if( (gameBoard[currentRow+1][currentCol+1].pieceName == "wK")
						|| (gameBoard[currentRow+1][currentCol-1].pieceName == "wK") ){
					return true;
				}
			}
		}
		
		catch(Exception a){
			// this exception just takes care of out of bound checks which the method may have to make,
			// no need to print anything out!
			// i.e: a2 a4 would check left for "CHECK" and would fail!
		}
	return false;
	}
	

}
