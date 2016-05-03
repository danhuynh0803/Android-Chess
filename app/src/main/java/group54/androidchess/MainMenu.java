package group54.androidchess;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Danny Huynh on 4/25/2016.
 */
public class MainMenu extends AppCompatActivity {

    private boolean isRecording = false;
    private ArrayList<String> gameReplay = new ArrayList<String>();
    private Replay[] replay = new Replay[10];          // Store the 10 most recent games

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
    }

    public void playOnClick(View v)
    {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, ChessActivity.class);
        startActivity(intent);
    }

    public void recordOnClick(View v)
    {
        Bundle bundle = new Bundle();
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
