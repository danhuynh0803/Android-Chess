package group54.androidchess;

// Import all associated chess files
import group54.androidchess.chess.*;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ChessActivity extends AppCompatActivity {

    int currentTurn = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChessboardView chessboardView = new ChessboardView(this);
        //setContentView(chessboardView);
        setContentView(R.layout.activity_main);

        TextView turnText = (TextView)findViewById(R.id.turnTextView);
        setTurnText(turnText);

        // Start game loop

    }

    private void startGame()
    {

    }



    private void setTurnText(TextView text)
    {
        if (currentTurn % 2 == 0) {
            text.setText("White's Turn");
        }
        else
            text.setText("Black's Turn");
    }
}
