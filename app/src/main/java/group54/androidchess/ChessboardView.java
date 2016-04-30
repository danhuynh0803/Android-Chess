package group54.androidchess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
// Import all associated chess files
import group54.androidchess.chess.*;

public final class ChessboardView extends View {
    private static final String TAG = ChessboardView.class.getSimpleName();

    private static final int COLS = 8;
    private static final int ROWS = 8;

    private final Tile[][] mTiles;
    private Canvas canvas;
    private Rect tileRect;
    int rectHeight;
    int rectWidth;
    Bitmap pic;
    Bitmap picResized;


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
    protected void onDraw(final Canvas canvas) {
        this.canvas = canvas;
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

        rectWidth = tileRect.width();
        rectHeight = tileRect.height();

        //for Creating Pieces
        createPawns();
        createRooks();
        createKnights();
        createBishops();
        createKings();
        createQueens();
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();

        Tile tile;
        for (int c = 0; c < COLS; c++) {
            for (int r = 0; r < ROWS; r++) {
                tile = mTiles[c][r];
                if (tile.isTouched(x, y)) {
                    // Set tile color currently selected to blue
                    tile.selected = true;
                }
            }
        }

        return true;
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
            //int x = tileRect.width();
            //int z = tileRect.height();
            Bitmap picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
            mTiles[y][1].draw(canvas,mTiles[y][1].getTileRect(), picResized);
            Pawn pawn = new Pawn("wp","white");
            mTiles[y][1].setPiece(pawn);
            //System.out.println(mTiles[y][1].pieceName);
            //System.out.println(mTiles[y][1].pieceColor);
        }

        //creates the black pawns
        for(int y=0; y<8; y++){ //print only 8 pawns
            Bitmap pic = BitmapFactory.decodeResource(getResources(),R.drawable.blackpawn);
            Bitmap picResized = Bitmap.createScaledBitmap(pic,rectWidth,rectHeight,true);
            mTiles[y][6].draw(canvas,mTiles[y][6].getTileRect(), picResized);
            Pawn pawn = new Pawn("bp", "black");
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


}
