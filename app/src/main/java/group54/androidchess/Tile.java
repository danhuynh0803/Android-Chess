package group54.androidchess;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.style.TtsSpan;
import android.util.Log;

import org.w3c.dom.Text;

import group54.androidchess.chess.Pawn;
import group54.androidchess.chess.Piece;

public final class Tile {

    private static final String TAG = Tile.class.getSimpleName();

    // Position of the square
    private final int col;
    private final int row;
    public boolean selected = false;

    // Text for piece currently on square
    //private Text pieceName2;
    private Piece piece;

    private Paint defaultColor;
    private Paint highlightColor;
    private Rect tileRect;

    public String pieceName;
    public String pieceColor;
    public Boolean firstMove;
    public Boolean Enpassant;

    public Tile(final int col, final int row, Piece piece) {
        this.col = col;
        this.row = row;

        this.piece = piece;
        this.pieceName = piece.pieceName;
        this.pieceColor = piece.pieceColor;
        this.firstMove = piece.firstMove;
        this.Enpassant = piece.Enpassant;

        this.defaultColor = new Paint();
        this.highlightColor = new Paint();
        highlightColor.setColor(Color.BLUE);

        if (isDark())
            defaultColor.setColor(Color.LTGRAY);
        else
            defaultColor.setColor(Color.WHITE);
    }

    /**
     * Draw a rectangle at specified dimensions and positon with color
     * @param canvas
     */
    public void draw(final Canvas canvas) {
        if (selected)
            canvas.drawRect(tileRect, highlightColor);
        else
            canvas.drawRect(tileRect, defaultColor);
    }
    /**
     * Fills in the rectangle with the appropriate picture of piece
     * @param canvas setting the canvas to display the image
     * @param tileRect the selected rectangle
     * @param pic the image of the piece
     */
    public void draw( Canvas canvas, Rect tileRect, Bitmap pic) {
        //this.canvas = canvas;

        canvas.drawBitmap(pic,tileRect.left,tileRect.top, new Paint(Color.RED));

    }

    /**
     * assigns the tile a certain piece
     * @param piece the piece to assign the tile to
     */
    public void setPiece(Piece piece){
        this.piece = piece;
        this.pieceName = piece.pieceName;
        this.pieceColor = piece.pieceColor;
        this.firstMove = piece.firstMove;
        this.Enpassant = piece.Enpassant;
    }

    /**
     * Check if the square should be darkened
     * @return
     */
    public boolean isDark() {
        return (col + row) % 2 == 0;
    }

    public boolean isTouched(final int x, final int y) {
        return tileRect.contains(x, y);
    }

    public void handleTouch() {
        Log.d(TAG, "col: " + col + "," + "row: " + row);
    }

    public void setTileRect(final Rect tileRect) {
        this.tileRect = tileRect;
    }

    /**
     * Find's the Rectangle associated with a tile
     * @return the tile's specific rectangle
     */
    public Rect getTileRect(){
        return this.tileRect;
    }
    public String toString() {
        return "<Tile " + "col: " + col + "row: " + row + ">";
    }

}
