package group54.androidchess;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Danny Huynh on 4/25/2016.
 */
public class Replay extends AppCompatActivity {

    private ListView nameListView;
    private ListView dateListView;
    private ArrayList<String> gameNameList;
    private ArrayList<String> dateList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replay);

        nameListView = (ListView)findViewById(R.id.gameListView);
        dateListView = (ListView) findViewById(R.id.dateListView);
        gameNameList = new ArrayList<String>();
        dateList = new ArrayList<String>();

        //populate the lists
        for(int x = 0; x<ChessActivity.savedGames.getSavedList().size(); x++){
            gameNameList.add(ChessActivity.savedGames.getSavedList().get(x).getGameTitle());
            dateList.add(ChessActivity.savedGames.getSavedList().get(x).getDate());
        }
        //instantiate the adapters
       // View v = new View(getApplicationContext());
        ArrayAdapter<String> gameArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,gameNameList);
        ArrayAdapter<String> dateArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dateList);

        //set the adapters
        Log.d(Replay.this.toString(),""+ChessActivity.savedGames.getSavedList().size());
        nameListView.setAdapter(gameArrayAdapter);
        dateListView.setAdapter(dateArrayAdapter);
        //show the data in listview
        if(ChessActivity.savedGames.getSavedList().isEmpty()){

        }
        Log.d(Replay.this.toString(),""+gameNameList.size());
        Log.d(Replay.this.toString(),""+nameListView.getCount());


       // nameListView.invalidate();
        //dateListView.invalidate();
    }






}
