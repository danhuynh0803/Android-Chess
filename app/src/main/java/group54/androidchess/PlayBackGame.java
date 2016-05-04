package group54.androidchess;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import group54.androidchess.chess.Piece;
import group54.androidchess.chess.WhiteSpaces;

/**
 * Created by Ammar on 5/4/2016.
 */
public class PlayBackGame extends AppCompatActivity {

    private ArrayList<PieceInfo> movementReplayList = new ArrayList<PieceInfo>();
    public Piece initPiece;
    public Piece finalPiece;
    Handler handler = new Handler(Looper.getMainLooper());
    static Runnable r;
    View view;
    public int counter =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ReplayView replayView = new ReplayView(this);
        setContentView(R.layout.recording);
        view =findViewById(R.id.ReplayView);


        //find the correct game that was selected from the saved list
        for (int x = 0; x < ChessActivity.savedGames.getSavedList().size(); x++) {
            String savedDataCompiled = "Game Title: " + ChessActivity.savedGames.getSavedList().get(x).getGameTitle() +
                    "\n" + "Date: "
                    + ChessActivity.savedGames.getSavedList().get(x).getDate();
            if (savedDataCompiled.equals(Replay.selectedFromList)) {
                Log.d(PlayBackGame.class.getSimpleName(), "Matched from list");
                movementReplayList = ChessActivity.savedGames.getSavedList().get(x).getTileList();
            }
        }

        //make sure list is not empty
        if (movementReplayList.isEmpty()) {
            Toast.makeText(replayView.getContext(), "No Game Selected", Toast.LENGTH_SHORT).show();
        } else {

            replayView.buildEmptyTiles();
            replayView.createPawns();
            replayView.createRooks();
            replayView.createKnights();
            replayView.createBishops();
            replayView.createKings();
            replayView.createQueens();
            replayView.updateBoard(ReplayView.mTiles2);
            //view.setWillNotDraw(true);

            r = new Runnable() {
                @Override
                public void run() {


                    //get the movement info

                    try {
                        Canvas canvas = new Canvas();
                        initPiece = movementReplayList.get(counter).getInitialPiece();
                        finalPiece = movementReplayList.get(counter).getFinalPiece();
                        int inRow = movementReplayList.get(counter).getiRow();
                        int inCol = movementReplayList.get(counter).getiCol();
                        int finRow = movementReplayList.get(counter).getfRow();
                        int finCol = movementReplayList.get(counter).getfCol();
                        //replace the initial pieces with blank tile
                        ReplayView.mTiles2[inRow][inCol].setPiece(new WhiteSpaces("Empty"));
                        ReplayView.mTiles2[inRow][inCol].draw(canvas);
                        //place the final piece
                        ReplayView.mTiles2[finRow][finCol].setPiece(initPiece);
                        counter++;
                        view.postInvalidate();
                        replayView.postInvalidate();
                        handler.postDelayed(r, 3000);
                        Log.d(PlayBackGame.class.getSimpleName(), "running thread");
                        //view.postInvalidate();
                    }catch (Exception a){}

                }
            };handler.postDelayed(r,3000);
            view.postInvalidate();



            int size = movementReplayList.size();
            int x = 0;
            while (x < size) {

                    try {
                        r.run();
                        //make the move

                        //update the board
                        //replayView.invalidate();
                        x++;
                    }catch(Exception a){a.printStackTrace();}
                //counter++;
            }
        }

    }








}
