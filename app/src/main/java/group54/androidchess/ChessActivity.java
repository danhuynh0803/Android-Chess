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

    }
}
