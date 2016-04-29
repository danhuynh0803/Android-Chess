package group54.androidchess;

import android.content.Context;
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
                mTiles[c][r] = new Tile(c, r);
            }
        }
    }

    @Override
    protected void onDraw(final Canvas canvas) {
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

                final Rect tileRect = new Rect(
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

}
