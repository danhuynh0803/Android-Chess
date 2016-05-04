package group54.androidchess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.EventLog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
// Import all associated chess files
import java.util.ArrayList;
import java.util.Stack;

import group54.androidchess.chess.*;

public final class ChessboardView extends View {
    private static final String TAG = ChessboardView.class.getSimpleName();

    private static final int COLS = 8;
    private static final int ROWS = 8;


    public static Tile[][] mTiles;
    public static ArrayList<PieceInfo> tileList;//holds the data

    private Canvas canvas = new Canvas();
    private Rect tileRect;
    int rectHeight= 54;
    int rectWidth = 54;
    Bitmap pic;
    Bitmap picResized;

    int initialX = -1;
    int initialY= -1;
    public boolean firstTime = true;   //to limit creation of more pieces
    public static boolean firstSelect = true; //selecting the first time
    public static boolean isGameOver = false; // prevent selection if game is over

    // Tile coordinates
    private int x0 = 0;
    private int y0 = 0;
    private int squareSize = 0;
    private boolean tileSelected = false;

    public static String currentTurn = "white";
    public static boolean undoAvailable = false;

    // Booleans to keep track of check/promotion/enpassant
    boolean kingInCheck = false;
    boolean firstMoveChangeBack = false;
    boolean enpassantChangeBack = false;
    boolean whiteEnpassantAllowed = false;
    boolean blackEnpassantAllowed = false;

    /** 'true' if black is facing player. */
    private boolean flipped = false;


    int initCol=-1; //for initial Tile within mTiles array
    int initRow=-1;

    int finalCol=-1; //for final Tile within mTiles array
    int finalRow=-1;

    public ChessboardView(final Context context) {
        super(context);
        this.mTiles = new Tile[ROWS][COLS];

        setFocusable(true);

        buildTiles();
    }

    public ChessboardView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        this.mTiles = new Tile[ROWS][COLS];

        setFocusable(true);

        buildTiles();
    }

    private void buildTiles() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                mTiles[r][c] = new Tile(r, c, new WhiteSpaces("Empty"));
            }
        }
    }

    @Override
    protected void onDraw( Canvas canvas) {
        this.canvas = canvas;

        if(firstTime) {
            //create a new list to store movements
            tileList = new ArrayList<PieceInfo>();
            //for Creating Pieces
            buildEmptyTiles();
            createPawns();
            createRooks();
            createKnights();
            createBishops();
            createKings();
            createQueens();

            firstTime=false;
        }else {
            //canvas = new Canvas();
            updateBoard(mTiles);
            //moveQueen();
        }
    }
    public void buildEmptyTiles(){

        final int width = getWidth();                         // Canvas width
        final int height = getHeight();                       // Canvas Height

        this.squareSize = Math.min(
                getSquareSizeWidth(width),
                getSquareSizeHeight(height)
        );

        computeOrigins(width, height);

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                final int xCoord = getXCoord(c);
                final int yCoord = getYCoord(r);

                tileRect = new Rect(
                        xCoord,               // left
                        yCoord,               // top
                        xCoord + squareSize,  // right
                        yCoord + squareSize   // bottom
                );

                mTiles[r][c].setTileRect(tileRect);
                mTiles[r][c].draw(canvas);
            }
        }
    }

    public void onClick(){
        if (isGameOver)
            return;

        if(ChessboardView.undoAvailable){
            // get all the appropriate values

            int tileListSize = tileList.size();
            if(tileList.isEmpty()){
                Toast.makeText(getContext(), "Undo Not Available", Toast.LENGTH_SHORT).show();
                undoAvailable=false;
            }
            else {
                int iRow = tileList.get(tileListSize - 1).getiRow();
                int iCol = tileList.get(tileListSize - 1).getiCol();
                int fRow = tileList.get(tileListSize - 1).getfRow();
                int fCol = tileList.get(tileListSize - 1).getfCol();

                Piece initPiece = tileList.get(tileListSize - 1).getInitialPiece();
                Piece finalPiece = tileList.get(tileListSize - 1).getFinalPiece();

                //move back the piece to it's original spot
                mTiles[iRow][iCol].setPiece(initPiece);
                if (mTiles[iRow][iCol].pieceName.contains("P")) {
                    System.out.println("given Pawn");
                    Piece pawn = (Pawn) mTiles[iRow][iCol].getPiece();
                    // Check if the pawn has not yet made a second move when pressing undo, this resets the pawn's first move boolean
                    System.out.println(pawn.nonfirstMove + "");
                    if (pawn.nonfirstMove == false) {
                        //mTiles[iRow][iCol].firstMove = true;
                        pawn.firstMove = true;
                        pawn.nonfirstMove = false;
                        mTiles[iRow][iCol].setPiece(pawn);
                        mTiles[iRow][iCol].draw(canvas);
                    }
                }

                //remove the final piece and replace it with the original
                mTiles[fRow][fCol].setPiece(finalPiece);

                //if a piece was selected before presing undo, deselect that piece
                for (int x = 0; x < 8; x++) {
                    for (int y = 0; y < 8; y++) {
                        if (mTiles[x][y].selected == true) {
                            mTiles[x][y].selected = false;
                            mTiles[x][y].draw(canvas);
                        }
                    }
                }


                undoAvailable = false;
                updateBoard(mTiles);
                Toast.makeText(getContext(), "Undo Successful", Toast.LENGTH_SHORT).show();
                if (ChessboardView.currentTurn.equals("white")) {
                    ChessActivity.white.setVisibility(View.INVISIBLE);
                    ChessActivity.black.setVisibility(View.VISIBLE);
                    ChessboardView.currentTurn = "black";
                } else {
                    ChessActivity.white.setVisibility(View.VISIBLE);
                    ChessActivity.black.setVisibility(View.INVISIBLE);
                    ChessboardView.currentTurn = "white";
                }
            }

        }
        else{
            Toast.makeText(getContext(), "Undo Not Available", Toast.LENGTH_SHORT).show();

        }

    }

    /**
     * Switch turn after a valid turn from the other player
     * @param turn the current player's turn
     * @return the other player's turn
     */
    public String switchTurn(String turn) {
        if (turn.equals("white")){
            getRootView().findViewById(R.id.whiteTurnTextView).setVisibility(INVISIBLE);
            getRootView().findViewById(R.id.blackTurnTextView).setVisibility(VISIBLE);
            return "black";
        }
        else {
            getRootView().findViewById(R.id.whiteTurnTextView).setVisibility(VISIBLE);
            getRootView().findViewById(R.id.blackTurnTextView).setVisibility(INVISIBLE);
            return "white";
        }
    }

    public String getTurn() {
        return currentTurn;
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        if (isGameOver) {
            return false;
        }
        final int action = event.getAction();
        if(action ==MotionEvent.ACTION_DOWN) {
            Canvas canvas = new Canvas();
            //eventPointerID = event.getPointerId(0);

            //highlight the first selection
            if (firstSelect) {
                initialX = (int) event.getX();
                initialY = (int) event.getY();
                for (int r = 0; r < ROWS; r++) {
                    for (int c = 0; c < COLS; c++) {

                        if (mTiles[r][c].isTouched(initialX, initialY)) {
                            //if an empty tile is selected, pop an error
                            Log.d(TAG, "row: " + r + " col: " + c);
                            if(mTiles[r][c].pieceName.equals("Empty")) {
                                Toast.makeText(getContext(), "Please Select a Piece", Toast.LENGTH_SHORT).show();
                            } else if (!(mTiles[r][c].pieceColor.equals(currentTurn))) {
                                Toast.makeText(getContext(), "Select your own piece!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                // Set tile color currently selected to blue
                                mTiles[r][c].selected = true;
                                mTiles[r][c].draw(canvas);
                                Log.d(TAG, mTiles[r][c].pieceName);
                                firstSelect = false;
                            }
                        }
                    }
                }
            }


            //options for 2nd selection
            else {

                //int eventPointerIndex = event.findPointerIndex(eventPointerID);
                final int finalX = (int) event.getX();
                final int finalY = (int) event.getY();
                //final int initialX = (int) event.getX(eventPointerIndex);
                //final int initialY = (int) event.getY(eventPointerIndex);

                Log.d(TAG,"initial x:"+initialX);
                Log.d(TAG,"initial y:"+initialY);
                Log.d(TAG,"final x:"+finalX);
                Log.d(TAG,"final y:"+finalY);

                //make sure the inital values were set
                if(initialX==-1 || initialY==-1){
                    Toast.makeText(getContext(), "Error Occured, Try Again", Toast.LENGTH_SHORT).show();
                }

                //if both selections were on the same piece, then deselect
                if (finalX == initialX && finalY == initialY) {
                    for (int r = 0; r < ROWS; r++) {
                        for (int c = 0; c < COLS; c++) {

                            if (mTiles[r][c].isTouched(finalX, finalY)) {
                                // Set tile color back to default color
                                mTiles[r][c].selected = false;
                                mTiles[r][c].draw(canvas);
                                mTiles[r][c].handleTouch();
                                firstSelect = true;
                            }
                        }
                    }
                }
                else{
                    for (int r = 0; r < ROWS; r++) {
                        for (int c = 0; c < COLS; c++) {
                            if (mTiles[r][c].isTouched(initialX, initialY)){
                                initCol = c;
                                initRow = r;
                            }
                            if(mTiles[r][c].isTouched(finalX,finalY)){
                                finalCol = c;
                                finalRow = r;
                            }
                        }
                    }
                    //if these values somehow don't get assigned, pop an error
                    if(initCol==-1 ||initRow ==-1 || finalCol ==-1 || finalRow ==-1){
                        Toast.makeText(getContext(), "Error Occured, Try Again", Toast.LENGTH_SHORT).show();
                    }
                    //makes sures to reset the highlight if user tries to kil their own piece
                    else if(mTiles[initRow][initCol].pieceColor.equals(mTiles[finalRow][finalCol].pieceColor)){
                        Toast.makeText(getContext(), "Cannot kill your own piece!", Toast.LENGTH_SHORT).show();
                        mTiles[initRow][initCol].selected = false;
                        mTiles[initRow][initCol].draw(canvas);
                        firstSelect = true;

                    }
                    //if final tile is chosen anywhere besides own piece
                    else {

                        Piece initPiece = null;    // Piece at the initial position
                        Piece finalPiece = null;   // Piece at the final position
                        Piece checkPiece = null;   // Piece that is/will cause check on the king
                        boolean resetTurn = false; // Allow current player to have another turn due to invalid moves
                        try{
                            Log.d(TAG, "initRow"+ initRow);
                            Log.d(TAG, "initCol"+ initCol);
                            Log.d(TAG, "finalRow"+ finalRow);
                            Log.d(TAG, "finalCol"+ finalCol);
                            //checks the move of the selected piece
                            /*
                            if (mTiles[initRow][initCol].pieceName.contains("P")) {
                                initRow = swapRow(initRow);
                                finalRow = swapRow(finalRow);
                            }
                            */

                            if(mTiles[initRow][initCol].getPiece().legitMove(mTiles,initRow,initCol,finalRow,finalCol)) {
                                undoAvailable = true;
                            // Check if the movement is from a King
                            if (mTiles[initRow][initCol].pieceName.equals("wK") || mTiles[initRow][initCol].pieceName.equals("bK")) {
                                King king = (King) mTiles[initRow][initCol].getPiece();
                                // Check if king is castling
                                if (king.isCastling()) {
                                    // Check the side the king is castling to by comparing difference between columns
                                    int colDiff = finalCol - initCol;
                                    if (colDiff > 0) {
                                        // Castle the right-side rook by moving king to final position
                                        mTiles[finalRow][finalCol].setPiece(mTiles[initRow][initCol].getPiece());
                                        mTiles[initRow][initCol].selected = false;
                                        mTiles[initRow][initCol].setPiece(new WhiteSpaces("Empty"));
                                        mTiles[initRow][initCol].draw(canvas);

                                        // Move right-side rook to left of king at it's final position
                                        int rookCol = 7;
                                        mTiles[finalRow][finalCol - 1].setPiece(mTiles[initRow][rookCol].getPiece());
                                        mTiles[initRow][rookCol].selected = false;
                                        mTiles[initRow][rookCol].setPiece(new WhiteSpaces("Empty"));
                                        mTiles[initRow][rookCol].draw(canvas);

                                        //insert this data into the undo array list later

                                    } else if (colDiff < 0) {
                                        // Castle the left-side rook by moving king to final position
                                        mTiles[finalRow][finalCol].setPiece(mTiles[initRow][initCol].getPiece());
                                        mTiles[initRow][initCol].selected = false;
                                        mTiles[initRow][initCol].setPiece(new WhiteSpaces("Empty"));
                                        mTiles[initRow][initCol].draw(canvas);

                                        // Move left-side rook to left of king at it's final position
                                        int rookCol = 0;
                                        mTiles[finalRow][finalCol + 1].setPiece(mTiles[initRow][rookCol].getPiece());
                                        mTiles[initRow][rookCol].selected = false;
                                        mTiles[initRow][rookCol].setPiece(new WhiteSpaces("Empty"));
                                        mTiles[initRow][rookCol].draw(canvas);

                                        //insert this data into the undo array list later

                                    }
                                    king.removeCastle();

                                    // Move normally if king is not castling
                                } else {
                                    //store the moves
                                    storeData();
                                    mTiles[finalRow][finalCol].setPiece(mTiles[initRow][initCol].getPiece());
                                    mTiles[initRow][initCol].selected = false;
                                    mTiles[initRow][initCol].setPiece(new WhiteSpaces("Empty"));
                                    mTiles[initRow][initCol].draw(canvas);

                                }
                                //if king is not being moved, then move the selected pieces
                            } else {
                                //store the moves
                                storeData();
                                initPiece = mTiles[initRow][initCol].getPiece();      // Store init piece
                                finalPiece = mTiles[finalRow][finalCol].getPiece();   // Store final piece
                                mTiles[finalRow][finalCol].setPiece(mTiles[initRow][initCol].getPiece());
                                mTiles[initRow][initCol].selected = false;
                                mTiles[initRow][initCol].setPiece(new WhiteSpaces("Empty"));
                                mTiles[initRow][initCol].draw(canvas);

                            }  if (kingInCheck == true) {
                                    // Find if any kings have been placed into check due to that move
                                    outerloop:
                                    for (int x = 0; x < mTiles.length; x++) {
                                        for (int y = 0; y < mTiles[x].length; y++) {
                                            if (mTiles[x][y].pieceColor != currentTurn) {
                                                if (mTiles[x][y].getPiece().placeCheck(mTiles, x, y) == true) {
                                                    //take back the move since king is still in check
                                                    Toast.makeText(getContext(), currentTurn + " King still in check", Toast.LENGTH_SHORT).show();
                                                    System.out.println("Piece giving check:" + mTiles[x][y].pieceName);
                                                    checkPiece = mTiles[x][y].getPiece();
                                                    checkPiece.checkX = x;
                                                    checkPiece.checkY = y;
                                                    mTiles[initRow][initCol].setPiece(mTiles[finalRow][finalCol].getPiece());
                                                    if (finalPiece != null || finalPiece.pieceName != "Empty") {
                                                        mTiles[finalRow][finalCol].setPiece(finalPiece);
                                                    } else {
                                                        mTiles[finalRow][finalCol].setPiece(new WhiteSpaces("Empty"));
                                                    }
                                                    mTiles[finalRow][finalCol].draw(canvas);

                                                    //gives the piece the ability to make first move again
                                                    if (firstMoveChangeBack == true) {
                                                        mTiles[initRow][initCol].firstMove = true;
                                                    }
                                                    //go back to false enpassant, since move was taken back
                                                    if (enpassantChangeBack == true) {
                                                        mTiles[initRow][initCol].Enpassant = false;
                                                    }
                                                    break outerloop;//exits the entire loop
                                                    //	counterForCheck++;
                                                }
                                            }

                                        }
                                    }
                                }
                                //so if the move does occur, check opponent piece for a check on ur own king
                                else if (mTiles[finalRow][finalCol].pieceColor == currentTurn) {
                                    kingInCheck = false;
                                    outerloop:
                                    for (int x = 0; x < mTiles.length; x++) {//skip last row
                                        for (int y = 0; y < mTiles[x].length; y++) {//skip last column
                                            if (mTiles[x][y].pieceColor != currentTurn) {
                                                if (mTiles[x][y].getPiece().placeCheck(mTiles, x, y) == true) {
                                                    Toast.makeText(getContext(), "Invalid: King in check", Toast.LENGTH_SHORT).show();
                                                    resetTurn = true;
                                                    System.out.println("Piece giving check:" + mTiles[x][y].pieceName);
                                                    checkPiece = mTiles[x][y].getPiece();
                                                    checkPiece.checkX = x;
                                                    checkPiece.checkY = y;
                                                    Log.d(TAG, mTiles[x][y].pieceName + mTiles[x][y].pieceColor);
                                                    Log.d(TAG, "row:" + x + " col:" + y);
                                                    if (mTiles[finalRow][finalCol].pieceName.equals(currentTurn.charAt(0) + "K") &&
                                                            Math.abs(finalCol - initCol) == 2) {
                                                        // Place king back into position
                                                        mTiles[initRow][initCol].setPiece(mTiles[finalRow][finalCol].getPiece());
                                                        mTiles[finalRow][finalCol].setPiece(new WhiteSpaces("Empty"));
                                                        mTiles[finalRow][finalCol].draw(canvas);
                                                        int rookCol = 0;
                                                        // Place right rook back into position
                                                        if (finalCol - initCol > 0) {
                                                            rookCol = 7;
                                                            mTiles[initRow][rookCol].setPiece(mTiles[finalRow][finalCol - 1].getPiece());
                                                            mTiles[initRow][rookCol].draw(canvas);
                                                            mTiles[finalRow][finalCol - 1].setPiece(new WhiteSpaces("Empty"));
                                                            mTiles[finalRow][finalCol - 1].draw(canvas);

                                                        }
                                                        // Place left rook back into position
                                                        else if (finalCol - initCol < 0) {
                                                            rookCol = 0;
                                                            mTiles[initRow][rookCol].setPiece(mTiles[finalRow][finalCol + 1].getPiece());
                                                            mTiles[initRow][rookCol].draw(canvas);
                                                            mTiles[finalRow][finalCol + 1].setPiece(new WhiteSpaces("Empty"));
                                                            mTiles[finalRow][finalCol + 1].draw(canvas);
                                                        }
                                                    }
                                                    // For non-king pieces and kings that do not castle
                                                    else {
                                                        mTiles[initRow][initCol].setPiece(mTiles[finalRow][finalCol].getPiece());
                                                        if (finalPiece != null || finalPiece.pieceName != "Empty") {
                                                            mTiles[finalRow][finalCol].setPiece(finalPiece);
                                                        } else {
                                                            mTiles[finalRow][finalCol].setPiece(new WhiteSpaces("Empty"));
                                                        }
                                                        mTiles[finalRow][finalCol].draw(canvas);
                                                    }
                                                    //gives the piece the ability to make first move again
                                                    if (firstMoveChangeBack == true) {
                                                        mTiles[initRow][initCol].firstMove = true;
                                                    }
                                                    //Enpassant need to become false again
                                                    if (enpassantChangeBack == true) {
                                                        mTiles[initRow][initCol].Enpassant = false;
                                                    }

                                                    break outerloop;//exits the entire loop

                                                }
                                            }
                                        }
                                    }
                                }

                                //if the piece does move, then check to see if that piece can place check on opponent
                                if (mTiles[finalRow][finalCol].pieceColor.equals(currentTurn)) {
                                    if (mTiles[finalRow][finalCol].getPiece().placeCheck(mTiles, finalRow, finalCol) == true) {
                                        String tempTurn = mTiles[finalRow][finalCol].pieceColor;
                                        tempTurn = switchTurn(tempTurn);
                                        Toast.makeText(getContext(), mTiles[finalRow][finalCol].pieceName + " placed a check on " + tempTurn + "'s King", Toast.LENGTH_SHORT).show();
                                        System.out.println("Piece giving check:" + mTiles[finalRow][finalCol].pieceName);
                                        checkPiece = mTiles[finalRow][finalCol].getPiece();
                                        checkPiece.checkX = finalRow;
                                        checkPiece.checkY = finalCol;
                                        kingInCheck = true;

                                        // Find the location of the enemy king currently in check
                                        int kingRow = Chess.getKingRow(mTiles, tempTurn);
                                        int kingCol = Chess.getKingCol(mTiles, tempTurn);
                                        King king = (King) mTiles[kingRow][kingCol].getPiece();
                                        // Set check to true to prevent castling
                                        king.setCheck(true);
                                    }
                                    //add method here for checkmate
                                }
                                //if no errors occur above, end turn
                                firstSelect = true;
                                // Switch to other player turn
                                if (resetTurn == false)
                                    currentTurn = switchTurn(currentTurn);
                        }

                        }catch(Exception e){ }
                    }
                }
            }
            invalidate();
        }
        return true;
    }


    /**
     * stores initial row, col and final row, col and the initial piece and final piece into an Array, which gets stored into the ArrayList
     */
    public void storeData(){

        PieceInfo pieceInfo = new PieceInfo(initRow, initCol, finalRow, finalCol,
                mTiles[initRow][initCol].getPiece(),mTiles[finalRow][finalCol].getPiece());
        tileList.add(pieceInfo);

    }

    /**
     * Prints the chess board
     * @param tile current tiles holding the data
     */
    public void updateBoard(Tile tile[][]){
        if (isGameOver){
            return;
        }
        //Canvas canvas = new Canvas();
        Bitmap pic;
        Bitmap picResized;
        buildEmptyTiles();

        //rectWidth = tile[0][0].getTileRect().width();
        //rectHeight = tile[0][0].getTileRect().height();

        for(int x = 0; x<8; x++){
            for(int y =0; y<8; y++){
                //print the blank tiles
                if(tile[x][y].pieceName.equals("Empty")){
                    tile[x][y].draw(canvas);
                }
                else{
                    String piece = tile[x][y].pieceName;

                    //String color = tile[x][y].pieceColor;
                     switch(piece){
                        //for black pieces
                        case "bP":
                            pic = BitmapFactory.decodeResource(getResources(),R.drawable.blackpawn);
                            picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
                            tile[x][y].draw(canvas,tile[x][y].getTileRect(),picResized);
                            break;
                         case "bR":
                             pic = BitmapFactory.decodeResource(getResources(),R.drawable.blackrook);
                             picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
                             tile[x][y].draw(canvas,tile[x][y].getTileRect(),picResized);
                             break;
                         case "bN":
                             pic = BitmapFactory.decodeResource(getResources(),R.drawable.blackknight);
                             picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
                             tile[x][y].draw(canvas,tile[x][y].getTileRect(),picResized);
                             break;
                         case "bB":
                             pic = BitmapFactory.decodeResource(getResources(),R.drawable.blackbishop);
                             picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
                             tile[x][y].draw(canvas,tile[x][y].getTileRect(),picResized);
                             break;
                         case "bQ":
                             pic = BitmapFactory.decodeResource(getResources(),R.drawable.blackqueen);
                             picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
                             tile[x][y].draw(canvas,tile[x][y].getTileRect(),picResized);
                             break;
                         case "bK":
                             pic = BitmapFactory.decodeResource(getResources(),R.drawable.blackking);
                             picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
                             tile[x][y].draw(canvas,tile[x][y].getTileRect(),picResized);
                             break;
                         //for white pieces
                         case "wP":
                             pic = BitmapFactory.decodeResource(getResources(),R.drawable.whitepawn);
                             picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
                             tile[x][y].draw(canvas,tile[x][y].getTileRect(),picResized);
                             break;
                         case "wR":
                             //Log.d(TAG, rectWidth +"and" + rectHeight);
                             pic = BitmapFactory.decodeResource(getResources(),R.drawable.whiterook);
                             picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
                             tile[x][y].draw(canvas,tile[x][y].getTileRect(),picResized);
                             break;
                         case "wN":
                             pic = BitmapFactory.decodeResource(getResources(),R.drawable.whiteknight);
                             picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
                             tile[x][y].draw(canvas,tile[x][y].getTileRect(),picResized);
                             break;
                         case "wB":
                             pic = BitmapFactory.decodeResource(getResources(),R.drawable.whitebishop);
                             picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
                             tile[x][y].draw(canvas,tile[x][y].getTileRect(),picResized);
                             break;
                         case "wQ":
                             pic = BitmapFactory.decodeResource(getResources(),R.drawable.whitequeen);
                             picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
                             tile[x][y].draw(canvas,tile[x][y].getTileRect(),picResized);
                             break;
                         case "wK":
                             pic = BitmapFactory.decodeResource(getResources(),R.drawable.whiteking);
                             picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
                             tile[x][y].draw(canvas,tile[x][y].getTileRect(),picResized);
                             break;
                         default:
                             Log.d(TAG,"ERROR OCCURRED IN PRINTING BOARD");
                             break;
                    }
                }

            }
        }
    }


    private int getSquareSizeWidth(final int width) {
        return width / 8;
    }

    private int getSquareSizeHeight(final int height) {
        return height / 8;
    }

    private int getXCoord(final int x) {
        return x0 + squareSize * (flipped ? 7 - x : x);
    }

    private int getYCoord(final int y) {
        return y0 + squareSize * (flipped ? y : 7 - y);
    }

    private void computeOrigins(final int width, final int height) {
        this.x0 = (width  - squareSize * 8) / 2;
        this.y0 = (height - squareSize * 8) / 2;
    }

    /**
     * creates the pawn pieces on the board
     */
    private void createPawns()
    {
        //creates the white pawns

        for(int y = 0; y<8; y++){ //print only 8 pawns

            Bitmap pic = BitmapFactory.decodeResource(getResources(),R.drawable.whitepawn);
            Bitmap picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
            mTiles[1][y].draw(canvas,mTiles[1][y].getTileRect(), picResized);

            Pawn pawn = new Pawn("wP","white");
            mTiles[1][y].setPiece(pawn);
            //System.out.println(mTiles[y][1].pieceName);
            //System.out.println(mTiles[y][1].pieceColor);
        }

        //creates the black pawns
        for(int y=0; y<8; y++){ //print only 8 pawns
            Bitmap pic = BitmapFactory.decodeResource(getResources(),R.drawable.blackpawn);
            Bitmap picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
            mTiles[6][y].draw(canvas,mTiles[6][y].getTileRect(), picResized);
            Pawn pawn = new Pawn("bP", "black");
            mTiles[6][y].setPiece(pawn);
            //System.out.println(mTiles[y][6].pieceName);


        }
    }

    /**
     * creates the rooks on the board
     */
    private void createRooks()
    {
        Rook rook;
        //create the White rooks
        pic = BitmapFactory.decodeResource(getResources(),R.drawable.whiterook);
        picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
        mTiles[0][0].draw(canvas,mTiles[0][0].getTileRect(),picResized);
        mTiles[0][7].draw(canvas,mTiles[0][7].getTileRect(),picResized);
        rook = new Rook("wR","white");
        mTiles[0][0].setPiece(rook);
        mTiles[0][7].setPiece(rook);

        //create the Black rooks
        pic = BitmapFactory.decodeResource(getResources(),R.drawable.blackrook);
        picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
        mTiles[7][0].draw(canvas,mTiles[7][0].getTileRect(),picResized);
        mTiles[7][7].draw(canvas,mTiles[7][7].getTileRect(),picResized);
        rook = new Rook("bR","black");
        mTiles[7][0].setPiece(rook);
        mTiles[7][7].setPiece(rook);

    }

    /**
     * creating knight pieces
     */
    private void createKnights()
    {

        Knight knight;
        //create the White knight
        pic = BitmapFactory.decodeResource(getResources(),R.drawable.whiteknight);
        picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
        mTiles[0][1].draw(canvas,mTiles[0][1].getTileRect(),picResized);
        mTiles[0][6].draw(canvas,mTiles[0][6].getTileRect(),picResized);
        knight = new Knight("wN","white");
        mTiles[0][1].setPiece(knight);
        mTiles[0][6].setPiece(knight);

        //create the Black knight
        pic = BitmapFactory.decodeResource(getResources(),R.drawable.blackknight);
        picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
        mTiles[7][1].draw(canvas,mTiles[7][1].getTileRect(),picResized);
        mTiles[7][6].draw(canvas,mTiles[7][6].getTileRect(),picResized);
        knight = new Knight("bN","black");
        mTiles[7][1].setPiece(knight);
        mTiles[7][6].setPiece(knight);
    }

    /**
     * creating bishop pieces
     */
    private void createBishops()
    {
        Bishop bishop;
        //create the White Bishop
        pic = BitmapFactory.decodeResource(getResources(),R.drawable.whitebishop);
        picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
        mTiles[0][2].draw(canvas,mTiles[0][2].getTileRect(),picResized);
        mTiles[0][5].draw(canvas,mTiles[0][5].getTileRect(),picResized);
        bishop = new Bishop("wB","white");
        mTiles[0][2].setPiece(bishop);
        mTiles[0][5].setPiece(bishop);

        //create the Black Bishop
        pic = BitmapFactory.decodeResource(getResources(),R.drawable.blackbishop);
        picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
        mTiles[7][2].draw(canvas,mTiles[7][2].getTileRect(),picResized);
        mTiles[7][5].draw(canvas,mTiles[7][5].getTileRect(),picResized);
        bishop = new Bishop("bB","black");
        mTiles[7][2].setPiece(bishop);
        mTiles[7][5].setPiece(bishop);
}

    private void createKings()
    {
        King king;
        //create the White King
        pic = BitmapFactory.decodeResource(getResources(),R.drawable.whiteking);
        picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
        mTiles[0][4].draw(canvas,mTiles[0][4].getTileRect(),picResized);
        king = new King("wK","white");
        mTiles[0][4].setPiece(king);

        //create the Black King
        pic = BitmapFactory.decodeResource(getResources(),R.drawable.blackking);
        picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
        mTiles[7][4].draw(canvas,mTiles[7][4].getTileRect(),picResized);
        king = new King("bK","black");
        mTiles[7][4].setPiece(king);
    }

    private void createQueens()
    {
        Queen queen;
        //create the White Queen
        pic = BitmapFactory.decodeResource(getResources(),R.drawable.whitequeen);
        picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
        mTiles[0][3].draw(canvas,mTiles[0][3].getTileRect(),picResized);
        queen = new Queen("wQ","white");
        mTiles[0][3].setPiece(queen);

        //create the Black Queen
        pic = BitmapFactory.decodeResource(getResources(),R.drawable.blackqueen);
        picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
        mTiles[7][3].draw(canvas,mTiles[7][3].getTileRect(),picResized);
        queen = new Queen("bQ","black");
        mTiles[7][3].setPiece(queen);
    }

    /**
     * Swap values of the row
     * @param row
     * @return
     */
    private int swapRow(int row) {
        int newRow = 0;
        switch(row) {
            case 0: newRow = 7; break;
            case 1: newRow = 6; break;
            case 2: newRow = 5; break;
            case 3: newRow = 4; break;
            case 4: newRow = 3; break;
            case 5: newRow = 2; break;
            case 6: newRow = 1; break;
            case 7: newRow = 0; break;
        }
        return newRow;
    }
}
