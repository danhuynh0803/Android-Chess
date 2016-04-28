package group54.androidchess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ChessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChessboardView chessboardView = new ChessboardView(this);
        //setContentView(chessboardView);
        setContentView(R.layout.activity_main);
    }


    private void setPlayerTurnText()
    {

    }
}
