/**
 * 
 */
package group54.androidchess.chess;

/**
 * @author Ammar Hussain
 * @author Danny Huynh
 *
 */
public class GameBoard {

	public Piece [][] gameBoard = new Piece [9][9]; //row then column
	
	
	public GameBoard(){//change this later to Piece Object
		//Piece [][] gameBoard = new Piece [9][9]; //row then column

		// Create board and load with non-piece elements
		initializeBoard();
		createAlphaNum();
		createSpaces();
		
		// Create pieces
		createPawns();
		createBishops();
		createKnights();
		createRooks();
		createKings();
		createQueens();
		
		// Print board
		printBoard();
		
	}
	
	private void initializeBoard() 
	{
		for(int x =0; x<gameBoard.length; x++){
			for(int y=0; y<gameBoard[x].length;y++){
				gameBoard[x][y]= null; //prints 1 for now
				 //System.out.printf("%2d", gameBoard[x][y]);
				 
			}
		}
	}
	
	
	private void createPawns()
	{
		//creates the black pawns
		for(int y=0; y<gameBoard[1].length-1; y++){ //print only 8 pawns
			Pawn pawn = new Pawn("bp","black");
			gameBoard[1][y] = pawn;
		}
			
		
		//creates the white pawns
		for(int y=0; y<gameBoard[1].length-1; y++){ //print only 8 pawns
			Pawn pawn = new Pawn("wp","white");
			gameBoard[6][y] = pawn;
		}
	}
	
	private void createSpaces()
	{
		//create the ##(black) spaces for row 2 and 4 starting with row 0 (index)
		for(int x = 2; x<5; x=x+2){
			for(int y=1; y<gameBoard[x].length-1; y=y+2){
				gameBoard[x][y] =new BlackSpaces("##");
			}
		}
			
		//create the ##(black) spaces for row 3 and 5 starting with row 0 (index)
		for(int x =3; x<6; x=x+2){
			for(int y=0; y<gameBoard[x].length-1;y=y+2){
				gameBoard[x][y] = new BlackSpaces("##");
			}
		}
	}	
	
	private void createAlphaNum()
	{
		//create the abc... row
		int numToAlpha = 97;
		for(int x=8; x<gameBoard.length; x++){
			for(int y=0; y<gameBoard[x].length-1; y++){
				gameBoard[x][y] = new AlphaNum(" "+(char)numToAlpha);
				numToAlpha++;
			}
		}
		
		//create the 123... row
		int z = 8;
		for(int x=0; x<gameBoard.length-1; x++){
			for(int y=8; y<gameBoard[x].length; y++){
				gameBoard[x][y] = new AlphaNum(""+z);
				z--;
			}
		}
	}
	
	private void createRooks()
	{
		//create the rooks
		gameBoard[0][0] = new Rook("bR","black");
		gameBoard[0][7] = new Rook("bR","black");
		gameBoard[7][0] = new Rook("wR","white");
		gameBoard[7][7] = new Rook("wR","white");
	}
	
	private void createKnights()
	{
		//create the knights
		gameBoard[0][1] = new Knight("bN", "black");
		gameBoard[0][6] = new Knight("bN", "black");
		gameBoard[7][1] = new Knight("wN", "white");
		gameBoard[7][6] = new Knight("wN", "white");
	}	
	
	private void createBishops()
	{
		//create the Bishop
		gameBoard[0][2] = new Bishop("bB", "black");
		gameBoard[0][5] = new Bishop("bB", "black");
		gameBoard[7][2] = new Bishop("wB", "white");
		gameBoard[7][5] = new Bishop("wB", "white");
	}
	
	private void createKings()
	{
		//create the King
		gameBoard[0][4] = new King("bK", "black");
		gameBoard[7][4] = new King("wK", "white");
	}
	
	private void createQueens() 
	{
		//create the Queen
		gameBoard[0][3] = new Queen("bQ", "black");
		gameBoard[7][3] = new Queen("wQ", "white");
	}
		//prints the board
			
	private void printBoard() 
	{
		for(int x =0; x<gameBoard.length; x++){
			for(int y=0; y<gameBoard[x].length;y++){
				if(gameBoard[x][y]==null){
					gameBoard[x][y]=new WhiteSpaces("  "); //prints white spaces
				}
				
				 System.out.print(gameBoard[x][y].pieceName + " ");
				 if(y==8){
					 System.out.println("");
				 }
			}
		}
		
	}
	
	 	
}
