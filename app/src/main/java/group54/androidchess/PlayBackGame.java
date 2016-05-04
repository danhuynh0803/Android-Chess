package group54.androidchess;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import group54.androidchess.chess.Piece;
import group54.androidchess.chess.WhiteSpaces;

/**
 * Created by Ammar on 5/4/2016.
 */
public class PlayBackGame extends AppCompatActivity {

    private ArrayList<PieceInfo> movementReplayList = new ArrayList<PieceInfo>();
    public Piece initPiece;
    public Piece finalPiece;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ReplayView replayView = new ReplayView(this);
        setContentView(R.layout.recording);
        //final View replayView = (View) findViewById(R.id.ReplayView);

        //find the correct game that was selected from the saved list
        for(int x=0; x<ChessActivity.savedGames.getSavedList().size();x++){
            String savedDataCompiled = "Game Title: "+ChessActivity.savedGames.getSavedList().get(x).getGameTitle()+
                    "\n"+"Date: "
                    +ChessActivity.savedGames.getSavedList().get(x).getDate();
            if(savedDataCompiled.equals(Replay.selectedFromList)){
                Log.d(PlayBackGame.class.getSimpleName(),"Matched from list");
                movementReplayList=ChessActivity.savedGames.getSavedList().get(x).getTileList();
            }
        }

        //make sure list is not empty
        if(movementReplayList.isEmpty()){
            Toast.makeText(replayView.getContext(), "No Game Selected", Toast.LENGTH_SHORT).show();
        }
        else{
            for(int x =0; x<movementReplayList.size();x++){
               try {
                   Canvas canvas = new Canvas();
                   //get the movement info
                   initPiece = movementReplayList.get(x).getInitialPiece();
                   finalPiece = movementReplayList.get(x).getFinalPiece();
                   int inRow = movementReplayList.get(x).getiRow();
                   int inCol = movementReplayList.get(x).getiCol();
                   int finRow = movementReplayList.get(x).getfRow();
                   int finCol = movementReplayList.get(x).getfCol();
                   //replace the initial pieces with blank tile
                   ReplayView.mTiles2[inRow][inCol].setPiece(new WhiteSpaces("Empty"));
                   ReplayView.mTiles2[inRow][inCol].draw(canvas);
                   //place the final piece
                   ReplayView.mTiles2[finRow][finCol].setPiece(finalPiece);
                   //update the board
                   replayView.invalidate();
               }catch(Exception e){
                   Toast.makeText(replayView.getContext(), "Error with game replay", Toast.LENGTH_SHORT).show();
               }

            }

        }



    }





}
