package group54.androidchess;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Danny Huynh on 4/25/2016.
 */
public class Replay extends AppCompatActivity {

    private ListView nameListView;
    private ListView dateListView;
    private ArrayList<String> gameNameList;
    private ArrayList<String> dateList;
    private ArrayList<String> tempList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replay);

        final Button nameSortBtn = (Button) findViewById(R.id.nameSortBtn);
        final Button dateSortBtn = (Button) findViewById(R.id.dateSortBtn);
        View view = new View(this);

        nameListView = (ListView)findViewById(R.id.gameListView);
        //dateListView = (ListView) findViewById(R.id.dateListView);
        gameNameList = new ArrayList<String>();
        dateList = new ArrayList<String>();
        tempList = new ArrayList<String>();
        //populate the lists
        for(int x = 0; x<ChessActivity.savedGames.getSavedList().size(); x++){
            String savedDataCompiled = "Game Title: "+ChessActivity.savedGames.getSavedList().get(x).getGameTitle()+
                    "\n"+"Date: "
                    +ChessActivity.savedGames.getSavedList().get(x).getDate();
            gameNameList.add(savedDataCompiled);
            dateList.add(ChessActivity.savedGames.getSavedList().get(x).getDate());
        }
        //instantiate the adapters
       // View v = new View(getApplicationContext());
        final ArrayAdapter<String> gameArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,gameNameList);
        //ArrayAdapter<String> dateArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dateList);
        //final ArrayAdapter<String> gameArrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tempList);
        //set the adapters
        Log.d(Replay.this.toString(),"saved games:"+ChessActivity.savedGames.getSavedList().size());
        nameListView.setAdapter(gameArrayAdapter);
        //dateListView.setAdapter(dateArrayAdapter);
        //show the data in listview
        if(ChessActivity.savedGames.getSavedList().isEmpty()){
            Toast.makeText(view.getContext(), "No Game Data Available", Toast.LENGTH_SHORT).show();

        }
        Log.d(Replay.this.toString(),""+gameNameList.size());
        Log.d(Replay.this.toString(),""+nameListView.getCount());


        /**
         * sorting via game titles
         */
        nameSortBtn.
                setOnClickListener( new View.OnClickListener() {
                                            public void onClick(View v) {
                                                if(gameNameList.isEmpty()){
                                                    Toast.makeText(v.getContext(), "Nothing to Sort Here", Toast.LENGTH_SHORT).show();
                                                }
                                                else {
                                                    Toast.makeText(v.getContext(), "List Sorted", Toast.LENGTH_SHORT).show();
                                                    Collections.sort(gameNameList, String.CASE_INSENSITIVE_ORDER);
                                                    gameArrayAdapter.notifyDataSetChanged();
                                                }

                                            }
                });

        //sorting via game dates
        dateSortBtn.
                setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        if(gameNameList.isEmpty()){
                            Toast.makeText(v.getContext(), "Nothing to Sort Here", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(v.getContext(), "List Sorted", Toast.LENGTH_SHORT).show();
                            Collections.sort(dateList);
                            ArrayList<String> tempList = new ArrayList<String>();
                            for(int x=0;x<dateList.size();x++){
                                for(int y=0; y<gameNameList.size();y++){
                                    if(gameNameList.get(y).contains(dateList.get(x))){
                                        tempList.add(gameNameList.get(y));
                                        Log.d(v.getContext().toString(),"contained");
                                    }
                                }
                            }
                            //gameNameList=tempList;
                            gameNameList.clear();
                            for(int x=0; x<tempList.size();x++){
                                gameNameList.add(tempList.get(x));
                            }
                            gameArrayAdapter.notifyDataSetChanged();
                        }

                    }
                });

       // nameListView.invalidate();
        //dateListView.invalidate();
    }







}
