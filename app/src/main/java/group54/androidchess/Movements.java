package group54.androidchess;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by Ammar on 5/3/2016.
 */
public class Movements implements Serializable{

    private static final long serialVersionUID = 0L;

    private String gameTitle;
    private String date;
    private ArrayList<PieceInfo> tileList;
    private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    public Movements(String title, ArrayList<PieceInfo> tileList){
        this.gameTitle = title;
        Calendar cal = Calendar.getInstance();
        this.date = dateFormat.format(cal.getTime());
        this.tileList = tileList;
    }

    public String getGameTitle(){
        return this.gameTitle;
    }
    public String getDate(){
        return this.date;
    }
    public ArrayList<PieceInfo> getTileList(){
        return this.tileList;
    }

}
