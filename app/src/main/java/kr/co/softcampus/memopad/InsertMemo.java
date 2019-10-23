package kr.co.softcampus.memopad;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InsertMemo extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_memo);

        editText = (EditText)findViewById(R.id.memContent);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeButtonEnabled(false);
        actionBar.setTitle("新規メモ");
        actionBar.setDisplayHomeAsUpEnabled(true);
        //git doesnt work...
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String date = sdf.format(new Date());

        if(id==android.R.id.home){
            if(editText.getText().toString().equals("")){
                finish();
            }else{
                DBExecute db = new DBExecute(this);
                db.insertMemo(editText.getText().toString(), date.toString());
            }

        }
        finish();
        return super.onOptionsItemSelected(item);
    }
}
