package group54.androidchess;

import java.util.ArrayList;

import group54.androidchess.chess.Piece;

/**
 * Created by Ammar on 5/2/2016.
 */
public class PieceInfo {

    public ArrayList listPieceInfo;
    public int iRow;
    public int iCol;
    public int fRow;
    public int fCol;
    public Piece initialPiece;
    public Piece finalPiece;

    /**
     *
     * @param a initial Row
     * @param b initial Column
     * @param c final Row
     * @param d final Column
     * @param initialPiece piece before movement
     * @param finalPiece piece after movement
     */
    public PieceInfo(int a, int b, int c, int d, Piece initialPiece, Piece finalPiece){

        this.iRow = a;
        this.iCol = b;
        this.fRow = c;
        this.fCol = d;
        this.initialPiece = initialPiece;
        this.finalPiece = finalPiece;
    }
    public PieceInfo(int a, int b, Piece initialPiece){
        this.iRow = a;
        this.iCol = b;
        this.initialPiece = initialPiece;
    }

    public int getiRow(){
        return this.iRow;
    }

    public int getiCol(){
        return this.iCol;
    }

    public int getfRow(){
        return this.fRow;
    }

    public int getfCol(){
        return this.fCol;
    }
    public Piece getInitialPiece(){
        return this.initialPiece;
    }
    public Piece getFinalPiece(){
        return this.finalPiece;
    }

}
