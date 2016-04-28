package group54.androidchess;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.style.TtsSpan;
import android.util.Log;

import org.w3c.dom.Text;

public final class Tile {

    private static final String TAG = Tile.class.getSimpleName();

    // Position of the square
    private final int col;
    private final int row;
    public boolean selected = false;

    // Text for piece currently on square
    private Text pieceName;

    private Paint defaultColor;
    private Paint highlightColor;
    private Rect tileRect;

    public Tile(final int col, final int row) {
        this.col = col;
        this.row = row;

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

    public String toString() {
        return "<Tile " + "col: " + col + "row: " + row + ">";
    }

}
