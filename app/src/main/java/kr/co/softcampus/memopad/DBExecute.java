package kr.co.softcampus.memopad;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    }
    public ArrayList<Memo> selectAllMemo() {
        SQLiteDatabase db= openDB();
        String sql = "SELECT * FROM memo";
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

                Memo memo = new Memo(textData, dateDate);

                list.add(memo);
            }
        return list;

    }
    public SQLiteDatabase openDB(){
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db= helper.getWritableDatabase();

        return db;
    }
}
