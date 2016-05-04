package group54.androidchess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


import java.util.ArrayList;

import group54.androidchess.chess.Bishop;
import group54.androidchess.chess.King;
import group54.androidchess.chess.Knight;
import group54.androidchess.chess.Pawn;
import group54.androidchess.chess.Queen;
import group54.androidchess.chess.Rook;
import group54.androidchess.chess.WhiteSpaces;


/**
 * Created by Ammar on 5/4/2016.
 */
public final class ReplayView extends View {
    private static final String TAG = ReplayView.class.getSimpleName();
    public static Tile[][] mTiles2;
    private static final int COLS = 8;
    private static final int ROWS = 8;
    private Canvas canvas = new Canvas();
    private Rect tileRect;
    int rectHeight= 54;
    int rectWidth = 54;
    Bitmap pic;
    Bitmap picResized;
    // Tile coordinates
    private int x0 = 0;
    private int y0 = 0;
    private int squareSize = 0;
    public boolean firstTime2 = true;   //to limit creation of more pieces
    private boolean flipped = false;



    public ReplayView(final Context context) {
        super(context);
        this.mTiles2 = new Tile[ROWS][COLS];

        setFocusable(true);

        buildTiles();
    }

    public ReplayView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        this.mTiles2 = new Tile[ROWS][COLS];

        setFocusable(true);

        buildTiles();
    }
    private void buildTiles() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                mTiles2[r][c] = new Tile(r, c, new WhiteSpaces("Empty"));
            }
        }
    }

    @Override
    protected void onDraw( Canvas canvas) {
        this.canvas = canvas;

        if (firstTime2) {

            //for Creating Pieces
            buildEmptyTiles();
            createPawns();
            createRooks();
            createKnights();
            createBishops();
            createKings();
            createQueens();

            firstTime2 = false;

        }
        else{
            updateBoard(mTiles2);
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

                mTiles2[r][c].setTileRect(tileRect);
                mTiles2[r][c].draw(canvas);
            }
        }
    }

    /**
     * Prints the chess board
     * @param tile current tiles holding the data
     */
    public void updateBoard(Tile tile[][]){

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
            mTiles2[1][y].draw(canvas,mTiles2[1][y].getTileRect(), picResized);

            Pawn pawn = new Pawn("wP","white");
            mTiles2[1][y].setPiece(pawn);
            //System.out.println(mTiles2[y][1].pieceName);
            //System.out.println(mTiles2[y][1].pieceColor);
        }

        //creates the black pawns
        for(int y=0; y<8; y++){ //print only 8 pawns
            Bitmap pic = BitmapFactory.decodeResource(getResources(),R.drawable.blackpawn);
            Bitmap picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
            mTiles2[6][y].draw(canvas,mTiles2[6][y].getTileRect(), picResized);
            Pawn pawn = new Pawn("bP", "black");
            mTiles2[6][y].setPiece(pawn);
            //System.out.println(mTiles2[y][6].pieceName);


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
        mTiles2[0][0].draw(canvas,mTiles2[0][0].getTileRect(),picResized);
        mTiles2[0][7].draw(canvas,mTiles2[0][7].getTileRect(),picResized);
        rook = new Rook("wR","white");
        mTiles2[0][0].setPiece(rook);
        mTiles2[0][7].setPiece(rook);

        //create the Black rooks
        pic = BitmapFactory.decodeResource(getResources(),R.drawable.blackrook);
        picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
        mTiles2[7][0].draw(canvas,mTiles2[7][0].getTileRect(),picResized);
        mTiles2[7][7].draw(canvas,mTiles2[7][7].getTileRect(),picResized);
        rook = new Rook("bR","black");
        mTiles2[7][0].setPiece(rook);
        mTiles2[7][7].setPiece(rook);

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
        mTiles2[0][1].draw(canvas,mTiles2[1][0].getTileRect(),picResized);
        mTiles2[0][6].draw(canvas,mTiles2[6][0].getTileRect(),picResized);
        knight = new Knight("wN","white");
        mTiles2[0][1].setPiece(knight);
        mTiles2[0][6].setPiece(knight);

        //create the Black knight
        pic = BitmapFactory.decodeResource(getResources(),R.drawable.blackknight);
        picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
        mTiles2[7][1].draw(canvas,mTiles2[7][1].getTileRect(),picResized);
        mTiles2[7][6].draw(canvas,mTiles2[7][6].getTileRect(),picResized);
        knight = new Knight("bN","black");
        mTiles2[7][1].setPiece(knight);
        mTiles2[7][6].setPiece(knight);
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
        mTiles2[0][2].draw(canvas,mTiles2[0][2].getTileRect(),picResized);
        mTiles2[0][5].draw(canvas,mTiles2[0][5].getTileRect(),picResized);
        bishop = new Bishop("wB","white");
        mTiles2[0][2].setPiece(bishop);
        mTiles2[0][5].setPiece(bishop);

        //create the Black Bishop
        pic = BitmapFactory.decodeResource(getResources(),R.drawable.blackbishop);
        picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
        mTiles2[7][2].draw(canvas,mTiles2[7][2].getTileRect(),picResized);
        mTiles2[7][5].draw(canvas,mTiles2[7][5].getTileRect(),picResized);
        bishop = new Bishop("bB","black");
        mTiles2[7][2].setPiece(bishop);
        mTiles2[7][5].setPiece(bishop);
    }

    private void createKings()
    {
        King king;
        //create the White King
        pic = BitmapFactory.decodeResource(getResources(),R.drawable.whiteking);
        picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
        mTiles2[0][4].draw(canvas,mTiles2[0][4].getTileRect(),picResized);
        king = new King("wK","white");
        mTiles2[0][4].setPiece(king);

        //create the Black King
        pic = BitmapFactory.decodeResource(getResources(),R.drawable.blackking);
        picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
        mTiles2[7][4].draw(canvas,mTiles2[4][7].getTileRect(),picResized);
        king = new King("bK","black");
        mTiles2[7][4].setPiece(king);
    }

    private void createQueens()
    {
        Queen queen;
        //create the White Queen
        pic = BitmapFactory.decodeResource(getResources(),R.drawable.whitequeen);
        picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
        mTiles2[0][3].draw(canvas,mTiles2[0][3].getTileRect(),picResized);
        queen = new Queen("wQ","white");
        mTiles2[0][3].setPiece(queen);

        //create the Black Queen
        pic = BitmapFactory.decodeResource(getResources(),R.drawable.blackqueen);
        picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
        mTiles2[7][3].draw(canvas,mTiles2[7][3].getTileRect(),picResized);
        queen = new Queen("bQ","black");
        mTiles2[7][3].setPiece(queen);
    }
    
}
