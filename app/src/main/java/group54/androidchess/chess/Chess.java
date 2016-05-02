/**
 * 
 */
package group54.androidchess.chess;

import java.util.Scanner;

import group54.androidchess.Tile;

/**
 * @author Ammar Hussain
 * @author Danny Huynh
 *
 */
public class Chess {
	
	
	public static boolean onlyKingRemaining(Piece[][] gameBoard)
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
						System.out.println(gameBoard[x][y].pieceName);
						return false;
					}
				}
			}
		}
		return true;
	}
	/**
	 * @throws Exception most out of range errors
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String turn = "white";//initiate with white pieces
		int oRow; //original row
		int oCol; //original col
		int fRow; //final row
		int fCol; //final col
		boolean askedForDraw = false; //checks for draw
		boolean kingInCheck = false; //if king is in check
		int counterForCheck = 0;
		boolean drawFormat = false; //checks the format of draw?
		boolean firstMoveChangeBack = false; //keeps track of first move of a pawn, castle or king
		boolean enpassantChangeBack = false; //keeps track of enpassant of a pawn
		boolean whiteEnpassantAllowed = false; //if enpassant is allowed on white Pawn
		boolean blackEnpassantAllowed = false; //if enpassant is allowed on black Pawn
		
		// Create gameboard and player instances
		GameBoard board = new GameBoard();
		Player whitePlayer = new Player("white");
		Player blackPlayer = new Player("black");
		
		
		System.out.println("");
		//System.out.println(board.gameBoard[1][0].pieceName);
		//System.out.println(board.gameBoard[1][0].legitMove(board.gameBoard, 1, 0, 2, 0));
		System.out.println(turn+"'s"+" move:");
		
		while(turn == "white" || turn == "black"){
			// When both white and black only have their king's left, the result is a draw		
			if (whitePlayer.onlyKingRemaining(board.gameBoard) && blackPlayer.onlyKingRemaining(board.gameBoard))
			{
				System.out.println("The game ends in a Draw as both players only have their King remaining!");
				turn = null;
				return;
			}
			
		Scanner scan = new Scanner(System.in);
		String move = scan.nextLine();
		drawFormat = false;
		

		//System.out.println("your move is:" + move);
		try{
			//to resign
			if(move.equals("resign")) {
				System.out.println(turn + " player has resigned");
				turn = null;
				scan.close();
				return;
			}
			//if a player responds back with a draw
			if(askedForDraw==true){
				if(askedForDraw == true && move.equals("draw")){
					System.out.println("The game ends in a Draw!");
					turn = null;
					scan.close();
					return;
				}
				else{askedForDraw = false;
				}
			}
						
			//makes sure the move string has a legit entry
			if(move.length()!=5 && (move.contains("draw?")) && !move.endsWith("?") ){
					// && !move.endsWith("?"))  ){
				System.out.println("Error: Not a Correct Move, Try Again! draw");
				drawFormat = true;
			}
			
			//For Promotion and Draw
			if(move.length()!=5 && (!move.contains("draw?") && !move.contains("R") && !move.contains("N")
					&& !move.contains("B") && !move.contains("Q") )){
				System.out.println("Error: Not a Correct Move, Try Again! draw2");
				drawFormat = true;
			}
					
			//stores the draw for the user and makes boolean true for later usage
			if(move.contains("draw?")){
				askedForDraw = true;
			}
			
			
			//assigns the initial Column
			oCol = toColumn(move.charAt(0));
			if(oCol == 10 ){
				System.out.println("Error: Not a Correct Move, Try again! oCol");
			}
			
			//assigns the initial Row
			oRow = toRow(move.charAt(1));
			if(oRow == 10){
				System.out.println("Error: Not a Correct Move, Try again! oRow");
			}
			
			//assigns the Final Column
			fCol = toColumn(move.charAt(3));
			if(fCol == 10 ){
				System.out.println("Error: Not a Correct Move, Try again! fCol");
			}
			
			//assigns the Final Row
			fRow = toRow(move.charAt(4));
			if(fRow == 10){
				System.out.println("Error: Not a Correct Move, Try again! fRow");
			}
									
			//may not need this!
			//checks to see if the original spot is empty
			if(board.gameBoard[oRow][oCol].pieceName.equals("##") ||
					board.gameBoard[oRow][oCol].pieceName.equals("  ")){
				System.out.println("Error: Original Spot does not contain a piece");
			}
			//assigns the empty spots color to the players to avoid null piece name errors
			if(board.gameBoard[fRow][fCol].pieceColor == null ){
				board.gameBoard[fRow][fCol].pieceColor ="neutral";//toggle thru the color to avoid errors
			}
			//to keep track of the first move boolean
			if(board.gameBoard[oRow][oCol].firstMove == true){
				firstMoveChangeBack = true;
				
			}
			if(board.gameBoard[oRow][oCol].Enpassant == false){
				enpassantChangeBack = true;
			}
			
			//checks to see if the original spot is current player's piece
			if(board.gameBoard[oRow][oCol].pieceColor == null ||
					!board.gameBoard[oRow][oCol].pieceColor.equals(turn)  
					//board.gameBoard[oRow][oCol].pieceColor.equals(null))
					){
				System.out.println("Error: This is not "+ turn+"'s piece!" + " Try Again!");
			}
			//if draw format is wrong
			else if(drawFormat == true){
				System.out.println("Error: Please type draw? correctly");
			}
			
			
			//checks to see if the final spot is occupied by your own piece
			else if(board.gameBoard[fRow][fCol].pieceColor.equals(turn)){
				System.out.println("Error: You Cannot Kill your own piece! Try Again!");
			}

			
			//checks if it is a valid move
	/*		else if(board.gameBoard[oRow][oCol].legitMove(board.gameBoard, oRow, oCol, fRow, fCol) == true){
				// Check if the piece is a king
				if (board.gameBoard[oRow][oCol].pieceName.equals("wK") || board.gameBoard[oRow][oCol].pieceName.equals("bK"))
				{
					King king = (King) board.gameBoard[oRow][oCol];
					if (king.isCastling())
					{
						// Check which side the king is castling by comparing the difference between columns
						int colDiff = fCol - oCol; 
						if (colDiff > 0)
						{
							// Castle the right-side rook by moving king to final position 
							board.gameBoard[8][8] = board.gameBoard[fRow][fCol];
							board.gameBoard[fRow][fCol] = board.gameBoard[oRow][oCol];
							board.gameBoard[oRow][oCol] = toEmptySpot(board.gameBoard, oRow, oCol);
							
							// Move right-side rook to left of king at it's final position
							int rookCol = 7;
							board.gameBoard[8][8] = board.gameBoard[fRow][rookCol];
							board.gameBoard[fRow][fCol - 1] = board.gameBoard[oRow][rookCol];
							board.gameBoard[oRow][rookCol] = toEmptySpot(board.gameBoard, oRow, rookCol);
							
						}
						else if (colDiff < 0) 
						{
							// Castle the left-side rook by moving king to final position 
							board.gameBoard[8][8] = board.gameBoard[fRow][fCol];
							board.gameBoard[fRow][fCol] = board.gameBoard[oRow][oCol];
							board.gameBoard[oRow][oCol] = toEmptySpot(board.gameBoard, oRow, oCol);
							
							// Move left-side rook to right of king at it's final position
							int rookCol = 0;
							board.gameBoard[8][8] = board.gameBoard[fRow][rookCol];
							board.gameBoard[fRow][fCol + 1] = board.gameBoard[oRow][rookCol];
							board.gameBoard[oRow][rookCol] = toEmptySpot(board.gameBoard, oRow, rookCol);
						}
						king.removeCastle();
					}
					// Move normally if king is not castling
					else 
					{
						board.gameBoard[8][8] = board.gameBoard[fRow][fCol]; //temp holder
						board.gameBoard[fRow][fCol] = board.gameBoard[oRow][oCol];
						board.gameBoard[oRow][oCol] = toEmptySpot(board.gameBoard, oRow, oCol);
					}
				}
				// Move non-king pieces by arranging the spots on the board using a temporary board
				else 
				{
					board.gameBoard[8][8] = board.gameBoard[fRow][fCol]; //temp holder
					board.gameBoard[fRow][fCol] = board.gameBoard[oRow][oCol];
					board.gameBoard[oRow][oCol] = toEmptySpot(board.gameBoard, oRow, oCol);
				}
				_________________________________________________________
				//enpassant will be available for this turn only
				if(whiteEnpassantAllowed==true){
					//if enpassant is allowed to kill white pawn
					if(board.gameBoard[fRow][fCol].pieceColor=="black"){
						if(fRow-1==4){
							if(board.gameBoard[fRow-1][fCol].pieceColor=="white"
								&& board.gameBoard[fRow-1][fCol].Enpassant == true){
							board.gameBoard[fRow-1][fCol] = toEmptySpot(board.gameBoard,fRow-1, fCol);
							}
						}
						else{
							int x = 4;
							for(int y = 0; y<board.gameBoard[x].length-1; y++){
								board.gameBoard[x][y].Enpassant=false;
							}
						}
					}
					whiteEnpassantAllowed=false;
				}
				if(blackEnpassantAllowed==true){	
					//if enpassant is allowed to kill black pawn
					if(board.gameBoard[fRow][fCol].pieceColor=="white"){
						if(fRow+1==3){
							if(board.gameBoard[fRow+1][fCol].pieceColor=="black"
									&& board.gameBoard[fRow+1][fRow].Enpassant == true){
							board.gameBoard[fRow+1][fCol] = toEmptySpot(board.gameBoard,fRow+1, fCol);
							}
						}
						else{
							int x = 3;
							for(int y = 0; y<board.gameBoard[x].length-1; y++){
								board.gameBoard[x][y].Enpassant=false;
							}
						}
					}
					blackEnpassantAllowed = false;
				}
								
				

						//board.gameBoard[fRow][fCol] = board.gameBoard[oRow][oCol];
						outerloop:
						for(int x = 0; x<board.gameBoard.length-1; x++){//skip last row
							for(int y =0; y<board.gameBoard[x].length-1; y++){//skip last column
								if(board.gameBoard[x][y].pieceColor != turn){
									if(board.gameBoard[x][y].placeCheck(board.gameBoard, x, y)==true){
										System.out.println(turn+"'s king is still in check");
										//take back the move since king is still in check
																				
										board.gameBoard[oRow][oCol] = board.gameBoard[fRow][fCol];
										board.gameBoard[fRow][fCol] = board.gameBoard[8][8]; 
										board.gameBoard[8][8] = toEmptySpot(board.gameBoard, 8, 8);
										//gives the piece the ability to make first move again
										if(firstMoveChangeBack == true){
											board.gameBoard[oRow][oCol].firstMove = true;
										}
										//go back to false enpassant, since move was taken back
										if(enpassantChangeBack == true){
											board.gameBoard[oRow][oCol].Enpassant = false;
										}
										break outerloop;//exits the entire loop
										//	counterForCheck++;
									}
								}
								
							}
						}//kingInCheck = false;//after king moves
						//turn = switchTurn(turn);
				}
				//if move occurs, promotion is provided but not allowed
				if(board.gameBoard[fRow][fCol].pieceColor == turn){
					//System.out.println(oRow);
					//System.out.println(fRow);
					//if( (oRow!=1 && fRow!=0) && (oRow!=6 && fRow!=7) ){
					if( (oRow!=1 || fRow!=0) && (oRow!=6 || fRow!=7) ){
						if((move.contains("R") || move.contains("N")
									||move.contains("B") || move.contains("Q")) ){
							//if(board.gameBoard[fRow][fCol].pieceName.contains("p")){

								//take back the move since promotion cannot occur
								board.gameBoard[oRow][oCol] = board.gameBoard[fRow][fCol];
								board.gameBoard[fRow][fCol] = board.gameBoard[8][8];
								board.gameBoard[8][8] = toEmptySpot(board.gameBoard, 8, 8);
								System.out.println("Invalid Move: Promotion is not allowed here");
								//gives the piece the ability to make first move again
								if(firstMoveChangeBack == true){
									board.gameBoard[oRow][oCol].firstMove = true;
								}
								//enpassant needs to be changed back to false
								if(enpassantChangeBack == true){
									board.gameBoard[oRow][oCol].Enpassant = false;
								}
								//take back the draw? request as well
								if(askedForDraw == true){
									askedForDraw = false;
								}
							//}
							
						}
					}
					
				}
				
				
				
				//so if the move does occur, check opponent piece for a check on ur own king
				if(board.gameBoard[fRow][fCol].pieceColor == turn){
					
					kingInCheck = false;
					outerloop:
						for(int x = 0; x<board.gameBoard.length-1; x++){//skip last row
							for(int y =0; y<board.gameBoard[x].length-1; y++){//skip last column
								if(board.gameBoard[x][y].pieceColor != turn){
									if(board.gameBoard[x][y].placeCheck(board.gameBoard, x, y) == true ){
										System.out.println("Invalid Move: puts own king in check");
										//take back the move since king is in check
										
										// If move was a king and castling 
										// set rook and king back to original positions
										if (board.gameBoard[fRow][fCol].pieceName.equals(turn.charAt(0) + "K") &&
												Math.abs(fCol - oCol) == 2)
										{
											// Place king back into position
											board.gameBoard[oRow][oCol] = board.gameBoard[fRow][fCol];
											board.gameBoard[fRow][fCol] = board.gameBoard[8][8];
											board.gameBoard[8][8] = toEmptySpot(board.gameBoard, 8, 8);
											
											int rookCol = 0;													
											// Place right rook back into position
											if (fCol - oCol > 0)
											{	
												rookCol = 7;
												board.gameBoard[oRow][rookCol] = board.gameBoard[fRow][fCol - 1];
												board.gameBoard[fRow][fCol - 1] = board.gameBoard[8][8];
												// Make king's former position to empty
												board.gameBoard[fRow][fCol] = board.gameBoard[8][8];
												board.gameBoard[8][8] = toEmptySpot(board.gameBoard, 8, 8);
												
											}
											// Place left rook back into position
											else if (fCol - oCol < 0)
											{
												rookCol = 0;
												board.gameBoard[oRow][rookCol] = board.gameBoard[fRow][fCol + 1];
												board.gameBoard[fRow][fCol + 1] = board.gameBoard[8][8];
												// Make king's former position to empty
												board.gameBoard[fRow][fCol] = board.gameBoard[8][8];
												board.gameBoard[8][8] = toEmptySpot(board.gameBoard, 8, 8);
											}
											
										}
										// For non-king pieces and kings that do not castle
										else 
										{
											board.gameBoard[oRow][oCol] = board.gameBoard[fRow][fCol];
											board.gameBoard[fRow][fCol] = board.gameBoard[8][8];
											board.gameBoard[8][8] = toEmptySpot(board.gameBoard, 8, 8);
										}
										//gives the piece the ability to make first move again
										if(firstMoveChangeBack == true){
											board.gameBoard[oRow][oCol].firstMove = true;
										}
										//Enpassant need to become false again
										if(enpassantChangeBack == true){
											board.gameBoard[oRow][oCol].Enpassant = false;
										}
										
										break outerloop;//exits the entire loop
										
									}
								}
							}
						}
				}
				//if move does occur and it's a promotion for a pawn
				if(board.gameBoard[fRow][fCol].pieceColor == turn &&( ((oRow==1) && (fRow==0)) 
						|| ((oRow==6) && (fRow==7)) )){
					char color = getTurnChar(turn);
					if(board.gameBoard[fRow][fCol].pieceName.contains("p")){
						
						if(move.contains("R")){
							board.gameBoard[fRow][fCol]= new Rook(color+"R",turn);							
						}
						else if(move.contains("N")){
							board.gameBoard[fRow][fCol]= new Knight(color+"N",turn);
						}
						else if(move.contains("B")){
							board.gameBoard[fRow][fCol]= new Bishop(color+"B",turn);
						}
						else if(move.contains("Q")){
							board.gameBoard[fRow][fCol]= new Queen(color+"Q",turn);
						}
						else{
							board.gameBoard[fRow][fCol]= new Queen(color+"Q",turn);
						}
					}//if it is now a pawn
					else if(!board.gameBoard[fRow][fCol].pieceName.contains("p")){
						if(move.contains("R") || move.contains("N") || move.contains("B") ||
								move.contains("Q")){
						//take back the move since promotion cannot occur
						board.gameBoard[oRow][oCol] = board.gameBoard[fRow][fCol];
						board.gameBoard[fRow][fCol] = board.gameBoard[8][8];
						board.gameBoard[8][8] = toEmptySpot(board.gameBoard, 8, 8);
						System.out.println("Invalid Move: Promotion is not allowed here");
						//gives the piece the ability to make first move again
							if(firstMoveChangeBack == true){
								board.gameBoard[oRow][oCol].firstMove = true;
							}
							//enpassant needs to become false again
							if(enpassantChangeBack == true){
								board.gameBoard[oRow][oCol].Enpassant = false;
							}
						}
					}
				}
				
				
				//if the piece does move, then check to see if that piece can place check on opponent
				if(board.gameBoard[fRow][fCol].pieceColor == turn){
					if(board.gameBoard[fRow][fCol].placeCheck(board.gameBoard, fRow, fCol) == true ){
						String tempTurn = board.gameBoard[fRow][fCol].pieceColor;
						tempTurn = switchTurn(tempTurn);
						System.out.println(board.gameBoard[fRow][fCol].pieceName +" placed a check on "
								+ tempTurn +"'s King");
						kingInCheck = true;
						
						// Find the location of the enemy king currently in check
						int kingRow = getKingRow(board.gameBoard, tempTurn);
						int kingCol = getKingCol(board.gameBoard, tempTurn);
						King king = (King) board.gameBoard[kingRow][kingCol];
						// Set check to true to prevent castling
						king.setCheck(true);
					}
					//add method here for checkmate
					
					
					
				}
				//checks if enpassant will be available next move
				if(board.gameBoard[fRow][fCol].pieceColor == turn){
					if(board.gameBoard[fRow][fCol].Enpassant==true){
						if(turn=="white"){
							whiteEnpassantAllowed=true;
						}
						else if(turn=="black"){
							blackEnpassantAllowed=true;
						}
					}
				}
				//if no errors occur above, then end the turn
				if(board.gameBoard[fRow][fCol].pieceColor == turn){
					turn = switchTurn(turn);
				}
			
			}*/
			else{
				System.out.println("Error: Not a Valid Move! Try Again!!!");
			}
		}catch (Exception e){System.out.println("Out of Range Error: Enter a Valid Move!");}
			
			
			//this will replace the original piece with either white space or black space
			//board.gameBoard[oRow][oCol] = toEmptySpot(board.gameBoard, oRow, oCol);
			//System.out.println(board.gameBoard[oRow][oCol].pieceName);
			//
			board.gameBoard[8][8] = toEmptySpot(board.gameBoard, 8, 8); //change temp back to normal
			System.out.println("");
			printLatestBoard(board.gameBoard);
			System.out.println("");
			System.out.println(turn+"'s"+" move:");
			
			
			
		}//while loop ends here

		
		
		
	}
	/**
	 * 
	 * @param turn determines which player's turn it is
	 * @return the other player's turn
	 */
	private static String switchTurn(String turn){
		if(turn == "white"){
			return "black";
		}
		else{
			return "white";
		}
	}
	/**
	 * 
	 * @param turn current color of the player
	 * @return first character of the color
	 */
	private static char getTurnChar(String turn){
		if(turn == "white"){
			return 'w';
		}
		else{
			return 'b';
		}
	}
	
	/**
	 * 
	 * @param var takes in a character at a certain index
	 * @return the equivalent column to the character or 10 to indicate an error
	 */
	private static int toColumn(char var) {
		//assigns the initial columns
		
		if(var=='a'){
			return 0;
		}
		else if(var =='b'){
			return 1;
		}
		else if(var =='c'){
			return 2;
		}
		else if(var =='d'){
			return 3;
		}
		else if(var =='e'){
			return 4;
		}
		else if(var =='f'){
			return 5;
		}
		else if(var =='g'){
			return 6;
		}
		else if(var =='h'){
			return 7;
		}
		else{
			return 10;
		}
		
	}
	/**
	 * 
	 * @param var takes in a character at a certain index
	 * @return the equivalent row to the character or 10 to indicate an error
	 */
	//assigns the initial columns
	private static int toRow(char var){ 
		
		if(var=='8'){
			return 0;
		}
		else if(var =='7'){
			return 1;
		}
		else if(var =='6'){
			return 2;
		}
		else if(var =='5'){
			return 3;
		}
		else if(var =='4'){
			return 4;
		}
		else if(var =='3'){
			return 5;
		}
		else if(var =='2'){
			return 6;
		}
		else if(var =='1'){
			return 7;
		}
		else{
			return 10;
		}
		
	}
	/**
	 * 
	 * @param gameBoard1 the current game board
	 * @param row the original row
	 * @param column the original column
	 * @return new empty spots either white space or black space
	 */
	private static Piece toEmptySpot(Piece [][] gameBoard1, int row, int column){
		Piece [][] gameBoard = new Piece [9][9];
		for(int x = 0; x<7; x=x+2){
			for(int y=1; y<gameBoard[x].length-1; y=y+2){
				gameBoard[x][y] =new BlackSpaces("##");
					
			}
		}
		
		for(int x =1; x<8; x=x+2){
			for(int y=0; y<gameBoard[x].length-1;y=y+2){
				gameBoard[x][y] = new BlackSpaces("##");
			}
		}
		
		for(int x =0; x<gameBoard.length; x++){
			for(int y=0; y<gameBoard[x].length;y++){
				if(gameBoard[x][y]==null){
					gameBoard[x][y]=new WhiteSpaces("  "); //prints white spaces
				}
			}
			
		}
		gameBoard1[row][column] =gameBoard[row][column];
		
		return gameBoard1[row][column];
		
		
	}
	/**
	 * 
	 * @param gameBoard2 the current game board
	 */
	private static void printLatestBoard(Piece [][] gameBoard2){
		for(int x = 0; x<gameBoard2.length; x++){
			for(int y =0; y<gameBoard2[x].length; y++){
				System.out.print(gameBoard2[x][y].pieceName + " ");
				if(y==8){
					System.out.println("");
				}
			}
		}
		
	}
	
	public static int getKingRow(Tile[][] gameBoard, String kingColor){
		
		for(int x=0; x<gameBoard.length; x++){
			for(int y=0; y<gameBoard[x].length; y++){
				if(gameBoard[x][y].pieceName.contains("K")
						&& gameBoard[x][y].pieceColor.equals(kingColor)) {
					return x;
				}
			}
		}
		return 0;
		
	}
	
	public static int getKingCol(Tile[][] gameBoard, String kingColor){
		
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
	
	
	
	

}
