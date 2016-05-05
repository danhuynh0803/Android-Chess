package group54.androidchess;


/**
 * @author Ammar Hussain
 * @author Danny Huynh
 *
 */
import android.app.Application;
import android.os.Environment;

import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.ObjectInputStream;
        import java.io.ObjectOutputStream;
        import java.io.Serializable;
        import java.util.ArrayList;


public class StorageList implements Serializable{

    /**
     *
     */private static final long serialVersionUID = 3169579517134610494L;


    private ArrayList<Movements> movementSavingList;

    public static final String storeDir = "gameData";
    public static final String storeFile = "gameList.dat";
    //File file = new File(Environment.getRootDirectory().getPath()+"/gameList.dat");

    private int x;

    /**
     * list to hold the data to save
     */
    public StorageList(){
        movementSavingList = new ArrayList<Movements>();
    }


    /**
     *
     * @param p adding a user to save one by one
     */
    public void addGameToSavedList(Movements p){
        movementSavingList.add(p);
    }
    /**
     *
     * @return gets the size of the user list needed for save
     */
    public int getGameStorageListSize(){
        return movementSavingList.size();
    }

    /**
     *
     * @return gets the saved list
     */
    public ArrayList<Movements> getSavedList(){
        return movementSavingList;
    }

    /**
     *
     * @param game save the selected game
     * @throws IOException if any problems occur
     */
    public static void write(StorageList game)throws IOException{

        //ObjectOutputStream saveTo = new ObjectOutputStream(new FileOutputStream(storeDir+File.separator+storeFile));
        ObjectOutputStream saveTo = new ObjectOutputStream(new FileOutputStream(ChessActivity.newFile));
        saveTo.writeObject(game);

    }
    /**
     *
     * @param
     * @throws IOException
     */
    public static void write()throws IOException{
        ObjectOutputStream saveTo = new ObjectOutputStream(new FileOutputStream(storeDir +File.separator+storeFile));

    }

    /**
     *
     * @return the user info from the file
     * @throws IOException catches any errors
     * @throws ClassNotFoundException catches any errors
     */
    public static StorageList read()throws IOException, ClassNotFoundException{
        ObjectInputStream loadFrom = new ObjectInputStream(
                new FileInputStream(ChessActivity.newFile ));
        return (StorageList) loadFrom.readObject();

    }


}
