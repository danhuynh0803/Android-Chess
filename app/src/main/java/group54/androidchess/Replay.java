package group54.androidchess;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Danny Huynh on 4/25/2016.
 */
public class Replay extends AppCompatActivity {

    ArrayList<String> moves = new ArrayList<String>();
    int currentMove = 0;

    protected void OnCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replay);
    }

    public void nextMove()
    {
        if (currentMove < moves.size())
            currentMove += 1;
    }

    public void prevMove()
    {
        if (currentMove > 0)
            currentMove -= 1;
    }


}
