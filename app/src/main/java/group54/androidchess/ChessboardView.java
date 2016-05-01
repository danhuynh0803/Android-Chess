package group54.androidchess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
// Import all associated chess files
import group54.androidchess.chess.*;

public final class ChessboardView extends View {
    private static final String TAG = ChessboardView.class.getSimpleName();

    private static final int COLS = 8;
    private static final int ROWS = 8;

    private final Tile[][] mTiles;
    private Canvas canvas = new Canvas();
    private Rect tileRect;
    int rectHeight= 54;
    int rectWidth = 54;
    Bitmap pic;
    Bitmap picResized;

    int initialX = -1;
    int initialY= -1;
    public boolean firstTime = true; //to limit creation of more pieces
    public boolean firstSelect = true; //selecting the first time
    private int x0 = 0;
    private int y0 = 0;
    private int squareSize = 0;
    private boolean tileSelected = false;

    /** 'true' if black is facing player. */
    private boolean flipped = false;

    public ChessboardView(final Context context) {
        super(context);
        this.mTiles = new Tile[COLS][ROWS];

        setFocusable(true);

        buildTiles();
    }

    public ChessboardView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        this.mTiles = new Tile[COLS][ROWS];

        setFocusable(true);

        buildTiles();
    }

    private void buildTiles() {
        for (int c = 0; c < COLS; c++) {
            for (int r = 0; r < ROWS; r++) {
                mTiles[c][r] = new Tile(c, r, new WhiteSpaces("Empty"));
            }
        }
    }

    @Override
    protected void onDraw( Canvas canvas) {
        this.canvas = canvas;

        if(firstTime) {
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

        for (int c = 0; c < COLS; c++) {
            for (int r = 0; r < ROWS; r++) {
                final int xCoord = getXCoord(c);
                final int yCoord = getYCoord(r);

                tileRect = new Rect(
                        xCoord,               // left
                        yCoord,               // top
                        xCoord + squareSize,  // right
                        yCoord + squareSize   // bottom
                );

                mTiles[c][r].setTileRect(tileRect);
                mTiles[c][r].draw(canvas);
            }
        }
    }


    @Override
    public boolean onTouchEvent(final MotionEvent event) {
       final int action = event.getAction();
        if(action ==MotionEvent.ACTION_DOWN) {
            Canvas canvas = new Canvas();
            //eventPointerID = event.getPointerId(0);

            //highlight the first selection
            if (firstSelect) {
                initialX = (int) event.getX();
                initialY = (int) event.getY();
                for (int c = 0; c < COLS; c++) {
                    for (int r = 0; r < ROWS; r++) {

                        if (mTiles[c][r].isTouched(initialX, initialY)) {
                            //if an empty tile is selected, pop an error
                            if(mTiles[c][r].pieceName.equals("Empty")){
                                Toast.makeText(getContext(), "Please Select a Piece", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                // Set tile color currently selected to blue
                                mTiles[c][r].selected = true;
                                mTiles[c][r].draw(canvas);
                                Log.d(TAG, mTiles[c][r].pieceName);
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
                    for (int c = 0; c < COLS; c++) {
                        for (int r = 0; r < ROWS; r++) {

                            if (mTiles[c][r].isTouched(finalX, finalY)) {
                                // Set tile color back to default color
                                mTiles[c][r].selected = false;
                                mTiles[c][r].draw(canvas);
                                firstSelect = true;
                            }
                        }
                    }
                }
                else{

                    int initCol=-1; //for initial Tile within mTiles array
                    int initRow=-1;

                    int finalCol=-1; //for final Tile within mTiles array
                    int finalRow=-1;

                    //to get tile locations for intial tile and final tile
                    for (int c = 0; c < COLS; c++) {
                        for (int r = 0; r < ROWS; r++) {

                            if (mTiles[c][r].isTouched(initialX, initialY)){
                                    initCol = c;
                                    initRow = r;
                            }
                            if(mTiles[c][r].isTouched(finalX,finalY)){
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
                    else if(mTiles[initCol][initRow].pieceColor.equals(mTiles[finalCol][finalRow].pieceColor)){
                        Toast.makeText(getContext(), "Cannot kill your own piece!", Toast.LENGTH_SHORT).show();
                        mTiles[initCol][initRow].selected = false;
                        mTiles[initCol][initRow].draw(canvas);
                        firstSelect = true;

                    }
                    //if final tile is chosen anywhere besides own piece
                    else {
                        Log.d(TAG,""+mTiles[initCol][initRow].firstMove);
                        //just for now to cancel the highlight
                       // mTiles[initCol][initRow].selected = false;
                        //mTiles[initCol][initRow].draw(canvas);
                        //firstSelect = true;
                        //Toast.makeText(getContext(), "im here", Toast.LENGTH_SHORT).show();
                        //check if the movement is correct
                        try{
                        if(mTiles[initCol][initRow].getPiece().legitMove(mTiles,initCol,initRow,finalCol,finalRow)) {
                            //then perform the movement
                            Log.d(TAG, "" + mTiles[initCol][initRow].firstMove);
                            mTiles[finalCol][finalRow].setPiece(mTiles[initCol][initRow].getPiece());
                            mTiles[initCol][initRow].selected = false;
                            mTiles[initCol][initRow].setPiece(new WhiteSpaces("Empty"));
                            mTiles[initCol][initRow].draw(canvas);
                            Toast.makeText(getContext(), "im here", Toast.LENGTH_SHORT).show();
                            firstSelect = true;


                            //continue from here
                        }

                        }catch(Exception e){
                        }




                    }

                }


                //for (int c = 0; c < COLS; c++) {
                //  for (int r = 0; r < ROWS; r++) {

            }
            invalidate();


        }
        return true;
    }

    /**
     * Prints the chess board
     * @param tile current tiles holding the data
     */
    private void updateBoard(Tile tile[][]){
        //Canvas canvas = new Canvas();
        Bitmap pic;
        Bitmap picResized;
        buildEmptyTiles();

        rectWidth = tile[0][0].getTileRect().width();
        rectHeight = tile[0][0].getTileRect().height();

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
            mTiles[y][1].draw(canvas,mTiles[y][1].getTileRect(), picResized);

            Pawn pawn = new Pawn("wP","white");
            mTiles[y][1].setPiece(pawn);
            //System.out.println(mTiles[y][1].pieceName);
            //System.out.println(mTiles[y][1].pieceColor);
        }

        //creates the black pawns
        for(int y=0; y<8; y++){ //print only 8 pawns
            Bitmap pic = BitmapFactory.decodeResource(getResources(),R.drawable.blackpawn);
            Bitmap picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
            mTiles[y][6].draw(canvas,mTiles[y][6].getTileRect(), picResized);
            Pawn pawn = new Pawn("bP", "black");
            mTiles[y][6].setPiece(pawn);
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
        mTiles[7][0].draw(canvas,mTiles[7][0].getTileRect(),picResized);
        rook = new Rook("wR","white");
        mTiles[0][0].setPiece(rook);
        mTiles[7][0].setPiece(rook);

        //create the Black rooks
        pic = BitmapFactory.decodeResource(getResources(),R.drawable.blackrook);
        picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
        mTiles[0][7].draw(canvas,mTiles[0][7].getTileRect(),picResized);
        mTiles[7][7].draw(canvas,mTiles[7][7].getTileRect(),picResized);
        rook = new Rook("bR","black");
        mTiles[0][7].setPiece(rook);
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
        mTiles[1][0].draw(canvas,mTiles[1][0].getTileRect(),picResized);
        mTiles[6][0].draw(canvas,mTiles[6][0].getTileRect(),picResized);
        knight = new Knight("wN","white");
        mTiles[1][0].setPiece(knight);
        mTiles[6][0].setPiece(knight);

        //create the Black knight
        pic = BitmapFactory.decodeResource(getResources(),R.drawable.blackknight);
        picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
        mTiles[1][7].draw(canvas,mTiles[1][7].getTileRect(),picResized);
        mTiles[6][7].draw(canvas,mTiles[6][7].getTileRect(),picResized);
        knight = new Knight("bN","black");
        mTiles[1][7].setPiece(knight);
        mTiles[6][7].setPiece(knight);
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
        mTiles[2][0].draw(canvas,mTiles[2][0].getTileRect(),picResized);
        mTiles[5][0].draw(canvas,mTiles[5][0].getTileRect(),picResized);
        bishop = new Bishop("wB","white");
        mTiles[2][0].setPiece(bishop);
        mTiles[5][0].setPiece(bishop);

        //create the Black Bishop
        pic = BitmapFactory.decodeResource(getResources(),R.drawable.blackbishop);
        picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
        mTiles[2][7].draw(canvas,mTiles[2][7].getTileRect(),picResized);
        mTiles[5][7].draw(canvas,mTiles[5][7].getTileRect(),picResized);
        bishop = new Bishop("bB","black");
        mTiles[2][7].setPiece(bishop);
        mTiles[5][7].setPiece(bishop);
    }

    private void createKings()
    {
        King king;
        //create the White King
        pic = BitmapFactory.decodeResource(getResources(),R.drawable.whiteking);
        picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
        mTiles[4][0].draw(canvas,mTiles[4][0].getTileRect(),picResized);
        king = new King("wK","white");
        mTiles[4][0].setPiece(king);

        //create the Black King
        pic = BitmapFactory.decodeResource(getResources(),R.drawable.blackking);
        picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
        mTiles[4][7].draw(canvas,mTiles[4][7].getTileRect(),picResized);
        king = new King("bK","black");
        mTiles[4][7].setPiece(king);
    }

    private void createQueens()
    {
        Queen queen;
        //create the White Queen
        pic = BitmapFactory.decodeResource(getResources(),R.drawable.whitequeen);
        picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
        mTiles[3][0].draw(canvas,mTiles[3][0].getTileRect(),picResized);
        queen = new Queen("wQ","white");
        mTiles[3][0].setPiece(queen);

        //create the Black Queen
        pic = BitmapFactory.decodeResource(getResources(),R.drawable.blackqueen);
        picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
        mTiles[3][7].draw(canvas,mTiles[3][7].getTileRect(),picResized);
        queen = new Queen("bQ","black");
        mTiles[3][7].setPiece(queen);
    }
    private void moveQueen(){

            Queen queen;
            //create the White Queen
            pic = BitmapFactory.decodeResource(getResources(),R.drawable.whitequeen);
            picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
            mTiles[0][7].draw(canvas);
            mTiles[0][7].draw(canvas,mTiles[0][7].getTileRect(),picResized);
            queen = new Queen("wQ","white");
            mTiles[0][7].setPiece(queen);
    }


}
