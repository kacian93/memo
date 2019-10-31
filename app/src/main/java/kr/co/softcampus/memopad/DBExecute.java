package kr.co.softcampus.memopad;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DBExecute  {
    Context context;
    ArrayList<Memo> list;
    DBExecute(Context context){
        this.context = context;
    }


    public void insertMemo(String contextData, String dateData){
        SQLiteDatabase db = openDB();

        String sql = "insert into memo(textData, dateDate)" +
                "values (?,?)";
        String [] data = {contextData, dateData};
        db.execSQL(sql,data);

        db.close();
    }
    public ArrayList<Memo> selectAllMemo() {
        SQLiteDatabase db= openDB();
        String sql = "SELECT * FROM memo ORDER BY DATEDATE DESC";
        list= new ArrayList<>();

        Cursor c = null;
        c= db.rawQuery(sql,null);


            while (c.moveToNext()) {
                //Indexnumberを抽出する。
                int idx_pos = c.getColumnIndex("idx");
                int textData_pos = c.getColumnIndex("textData");
                int dateDate_pos = c.getColumnIndex("dateDate");


                //indexnumberを通じてデターをもらう
                int idx = c.getInt(idx_pos);
                String textData = c.getString(textData_pos);
                String dateDate = c.getString(dateDate_pos);

                Memo memo = new Memo(textData, dateDate, idx);

                list.add(memo);
            }
            db.close();
        return list;

    }
    public SQLiteDatabase openDB(){
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db= helper.getWritableDatabase();

        return db;
    }

    public Memo selectOneMemo(int idx){
        SQLiteDatabase db= openDB();
        String sql = "SELECT * FROM memo where idx = "+idx;
        Cursor c= db.rawQuery(sql,null);


        c.moveToNext();
        int idx_pos=  c.getColumnIndex("idx");
        int textData_pos = c.getColumnIndex("textData");
        int dateData_pos=  c.getColumnIndex("dateDate");

        int idx2 = c.getInt(idx_pos);
        String textData = c.getString(textData_pos);
        String dateData = c.getString(dateData_pos);



        Memo memo = new Memo(textData, dateData,idx2);


        db.close();
        return memo;
    }

    public void deleteMemo(int idx){
        SQLiteDatabase db= openDB();
        String sql = "delete from memo where idx = "+idx;
        db.execSQL(sql);
        db.close();
    }

    public void updateMemo(int memoIdx, String text, String today) {
        SQLiteDatabase db = openDB();
        String sql = "UPDATE memo SET textData = ?, datedate = ? where idx =?";
        String[] data = {text,today, String.valueOf(memoIdx) };

        db.execSQL(sql, data);

        db.close();
    }
}
