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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        insertMeme();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==android.R.id.home){
            insertMeme();
        }
        finish();
        return super.onOptionsItemSelected(item);
    }
    public void insertMeme(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String date = sdf.format(new Date());
        if(editText.getText().toString().equals("")){
            finish();
        }else{
            DBExecute db = new DBExecute(this);
            db.insertMemo(editText.getText().toString(), date);
        }
    }
}
