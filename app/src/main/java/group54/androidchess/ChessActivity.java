package group54.androidchess;

// Import all associated chess files
import group54.androidchess.chess.*;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class ChessActivity extends AppCompatActivity {
    int currentTurn = 0;
    public static TextView white;
    public static TextView black;
    public static TextView turn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ChessboardView chessboardView = new ChessboardView(this);
        //setContentView(chessboardView);
        setContentView(R.layout.activity_main);
        final Button undoBtn = (Button) findViewById(R.id.undo);
        final Button helpBtn = (Button) findViewById(R.id.help);
        final Button resignBtn = (Button) findViewById(R.id.resign);
        final Button drawBtn = (Button) findViewById(R.id.draw);
        final View chessboardView1 = (View) findViewById(R.id.ChessboardView);

        resignBtn.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v){
                v = chessboardView1;
                white = (TextView) chessboardView1.getRootView().findViewById(R.id.whiteTurnTextView);
                black = (TextView) chessboardView1.getRootView().findViewById(R.id.blackTurnTextView);
                turn = (TextView) chessboardView1.getRootView().findViewById(R.id.turn);

                AlertDialog.Builder builder = new AlertDialog.Builder(ChessActivity.this);
                builder.setTitle(R.string.app_name);
                builder.setMessage("Do you wish to resign?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        chessboardView.isGameOver = true;
                        // Check which player resigned
                        if (white.getVisibility() == View.VISIBLE) {
                            white.setVisibility(View.GONE);
                            black.setVisibility(View.GONE);
                            turn.setText(R.string.black_win);
                        } else {
                            white.setVisibility(View.GONE);
                            black.setVisibility(View.GONE);
                            turn.setText(R.string.white_win);
                        }
                        drawBtn.setVisibility(View.GONE);      // Hide buttons after game ends
                        resignBtn.setVisibility(View.GONE);
                        undoBtn.setVisibility(View.GONE);
                        helpBtn.setVisibility(View.GONE);

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

                chessboardView1.invalidate();
            }
        });

        drawBtn.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v){
                v = chessboardView1;
                white = (TextView) chessboardView1.getRootView().findViewById(R.id.whiteTurnTextView);
                black = (TextView) chessboardView1.getRootView().findViewById(R.id.blackTurnTextView);
                turn = (TextView) chessboardView1.getRootView().findViewById(R.id.turn);

                AlertDialog.Builder builder = new AlertDialog.Builder(ChessActivity.this);
                builder.setTitle(R.string.app_name);
                builder.setMessage("Do you accept making the match a draw?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        chessboardView.isGameOver = true;
                        // Create additional dialog asking if game should be restarted or if player wants to return to menu
                        white.setVisibility(View.INVISIBLE);
                        black.setVisibility(View.INVISIBLE);
                        turn.setText("Draw");
                        drawBtn.setVisibility(View.GONE);     // Hide buttons after game ends
                        resignBtn.setVisibility(View.GONE);
                        undoBtn.setVisibility(View.GONE);
                        helpBtn.setVisibility(View.GONE);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                chessboardView1.invalidate();
            }
        });


        undoBtn.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v){
                v = chessboardView1;
                white = (TextView) chessboardView1.getRootView().findViewById(R.id.whiteTurnTextView);
                black = (TextView) chessboardView1.getRootView().findViewById(R.id.blackTurnTextView);
                chessboardView.onClick();
                //ChessboardView.undoSelection=true;
                ChessboardView.firstSelect = true;
                chessboardView1.invalidate();

                }
       });

        helpBtn.setOnClickListener( new View.OnClickListener() {

            public void onClick(View v) {

                v = chessboardView1;
                white = (TextView) chessboardView1.getRootView().findViewById(R.id.whiteTurnTextView);
                black = (TextView) chessboardView1.getRootView().findViewById(R.id.blackTurnTextView);

                //this will hold the current players pieces
                ArrayList<PieceInfo> holdPlayersPieces = new ArrayList<PieceInfo>();
                //this will hold all the movements for each of the pieces
                ArrayList<PieceInfo> everyPieceInfo = new ArrayList<PieceInfo>();

                //lets find all the available pieces for the current player
                for(int x =0;x<8; x++){
                    for(int y=0; y<8; y++){
                        if(ChessboardView.mTiles[x][y].pieceColor ==ChessboardView.currentTurn){
                            PieceInfo pieceInfo = new PieceInfo(x, y, ChessboardView.mTiles[x][y].getPiece());
                            holdPlayersPieces.add(pieceInfo);
                        }
                    }
                }
                //now let's record all the legit movements by each of the pieces
                try{
                for(int z =0; z<holdPlayersPieces.size()-1;z++){
                    for(int x=0; x<8; x++){
                        for(int y =0; y<8; y++){

                            if( (holdPlayersPieces.get(z).getiRow()!= x && holdPlayersPieces.get(z).getiCol() != y)
                                 &&   holdPlayersPieces.get(z).getInitialPiece().legitMove(ChessboardView.mTiles,holdPlayersPieces.get(z).getiRow(),
                                    holdPlayersPieces.get(z).getiCol(),x,y)){
                                //Records the initial position and final position
                                PieceInfo pieceInfo = new PieceInfo(holdPlayersPieces.get(z).getiRow(), holdPlayersPieces.get(z).getiCol(),x,y
                                        , ChessboardView.mTiles[holdPlayersPieces.get(z).getiRow()][holdPlayersPieces.get(z).getiCol()].getPiece()
                                ,ChessboardView.mTiles[x][y].getPiece());
                                //store the info in the array
                                everyPieceInfo.add(pieceInfo);

                            }
                        }
                    }
                }
                }catch(Exception e){}
                Canvas canvas = new Canvas();
                //now randomly choose a movement from the arraylist everyPieceInfo
                Random random = new Random();
                if(!everyPieceInfo.isEmpty()) {
                    int totalOptions = everyPieceInfo.size() - 1;
                    int n = random.nextInt(totalOptions) + 0;

                    //int n = (int) (Math.random()) * totalOptions + 0;
                    Piece initialPiece = everyPieceInfo.get(n).getInitialPiece();
                    Piece finalPiece = everyPieceInfo.get(n).getFinalPiece();
                    int iRow = everyPieceInfo.get(n).getiRow();
                    int iCol = everyPieceInfo.get(n).getiCol();
                    int fRow = everyPieceInfo.get(n).getfRow();
                    int fCol = everyPieceInfo.get(n).getfCol();
                    ChessboardView.undoAvailable=true;
                    storeData(iRow,iCol,fRow,fCol);
                    ChessboardView.mTiles[iRow][iCol].setPiece(new WhiteSpaces("Empty"));
                    ChessboardView.mTiles[iRow][iCol].draw(canvas);
                    ChessboardView.mTiles[fRow][fCol].setPiece(initialPiece);


                    if (ChessboardView.currentTurn.equals("white")){

                        white.setVisibility(View.INVISIBLE);
                        black.setVisibility(View.VISIBLE);
                        ChessboardView.currentTurn = "black";
                    }
                    else {
                        white.setVisibility(View.VISIBLE);
                        black.setVisibility(View.INVISIBLE);
                        ChessboardView.currentTurn = "white";
                    }
                    chessboardView1.invalidate();
                }
                else{
                    Toast.makeText(chessboardView1.getContext(), "Cannot Help at the Moment", Toast.LENGTH_SHORT).show();
                }


            }
        });



    }

    /**
     * stores initial row, col and final row, col and the initial piece and final piece into an Array, which gets stored into the ArrayList
     */
    private void storeData(int a, int b, int c, int d){

        PieceInfo pieceInfo = new PieceInfo(a, b, c, d,
                ChessboardView.mTiles[a][b].getPiece(),ChessboardView.mTiles[c][d].getPiece());
        ChessboardView.tileList.add(pieceInfo);

    }
}
