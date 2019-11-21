package kr.co.softcampus.memopad;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.util.Log;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBExecute  {
    Context context;
    ArrayList<Memo> list;
    DBExecute(Context context){
        this.context = context;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ParsePosition pos = new ParsePosition(0);


    public void insertMemo(String contextData, Date dateData){
        SQLiteDatabase db = openDB();

        String sql = "insert into memo(textData, dateDate)" +
                "values (?,?)";
        Object [] data = {contextData, dateData};
        db.execSQL(sql,data);

        db.close();
    }
    public ArrayList<Memo> selectAllMemo() {
        SQLiteDatabase db= openDB();
        String sql = "SELECT * FROM memo ORDER BY DATEDATE DESC";
        list= new ArrayList<>();

        final Cursor c= db.rawQuery(sql,null);


            while (c.moveToNext()) {
                //Indexnumberを抽出する。
                int idx_pos = c.getColumnIndex("idx");
                int textData_pos = c.getColumnIndex("textData");
                int dateDate_pos = c.getColumnIndex("dateDate");


                //indexnumberを通じてデターをもらう
                int idx = c.getInt(idx_pos);
                String textData = c.getString(textData_pos);
                String dateDate = c.getString(dateDate_pos);



                Date time = changeDateToStr(dateDate);
                Memo memo = new Memo(textData,time, idx);

                list.add(memo);
            }
            c.close();
            db.close();
        return list;

    }
    public SQLiteDatabase openDB(){
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db= helper.getWritableDatabase();

        return db;
    }

    public ArrayList<Memo> searchMemo(String prevSearch){
        SQLiteDatabase db= openDB();
        String sql = "select * from memo where textData like '%"+prevSearch+"%'";
        ArrayList<Memo> searchContent = new ArrayList<>();

        final Cursor  c=  db.rawQuery(sql, null);

        while(c.moveToNext()){
            int idx_pos=  c.getColumnIndex("idx");
            int textData_pos = c.getColumnIndex("textData");
            int dateData_pos=  c.getColumnIndex("dateDate");

            int idx2 = c.getInt(idx_pos);
            String textData = c.getString(textData_pos);
            String dateData = c.getString(dateData_pos);

            Date time = changeDateToStr(dateData);
            Memo memo = new Memo(textData,time, idx2);

            searchContent.add(memo);
        }

        c.close();
        db.close();
        return searchContent;
    }
    public void deleteMemo(int idx){
        SQLiteDatabase db= openDB();
        String sql = "delete from memo where idx = "+idx;
        db.execSQL(sql);
        db.close();
    }

    public void updateMemo(int memoIdx, String text, Date today) {
        final SQLiteDatabase db = openDB();
        final String sql = "UPDATE memo SET textData = ?, datedate = ? where idx =?";
        Object[] data = {text,today, String.valueOf(memoIdx) };

        db.execSQL(sql, data);

        db.close();
    }
    public Date changeDateToStr(String time){

        DateFormat origin = new SimpleDateFormat("EEE MMM dd hh:mm:ss 'GMT' yyyy");
        Date timeDate =null;
        try {
            timeDate = origin.parse(time);

        } catch (ParseException e) {
            e.printStackTrace();

        }
        return timeDate;
    }
}
