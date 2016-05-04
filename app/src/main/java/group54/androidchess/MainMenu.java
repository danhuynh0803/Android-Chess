package group54.androidchess;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

import group54.androidchess.chess.Chess;

/**
 * Created by Danny Huynh on 4/25/2016.
 */
public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
    }

    public void playOnClick(View v)
    {
        Bundle bundle = new Bundle();
        ChessboardView.isGameOver = false;
        ChessboardView.currentTurn = "white";
        ChessboardView.undoAvailable = true;
        ChessboardView.kingInCheck = false;
        ChessboardView.firstSelect = true;
        Intent intent = new Intent(this, ChessActivity.class);
        startActivity(intent);
    }

    public void replayOnClick(View v)
    {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, Replay.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
