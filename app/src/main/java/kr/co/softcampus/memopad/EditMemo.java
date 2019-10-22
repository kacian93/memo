package kr.co.softcampus.memopad;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditMemo extends AppCompatActivity {
    Memo selectMemo = null;
    EditText editText;
    DBExecute db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_memo);

         editText = (EditText)  findViewById(R.id.editmemo);

        Intent intent = getIntent();

        Memo memo = intent.getParcelableExtra("memo");
        int memoIdx = memo.getMemoIdx();



        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("メモ編集");
        actionBar.setHomeButtonEnabled(false);

        db = new DBExecute(this);
        selectMemo = db.selectOneMemo(memoIdx);

        editText.setText(selectMemo.getMemoContent());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.deleteIcon){


        }else if(id == android.R.id.home){
            if(editText.getText().equals("")){
                db.deleteMemo(selectMemo.memoIdx);
            }
            if(!editText.getText().equals(selectMemo.getMemoContent())){
                SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String today = sdf.format(new Date());
                db.updateMemo(selectMemo.memoIdx, editText.getText().toString(),today);

            }
        }

        finish();
        return super.onOptionsItemSelected(item);
    }
}
