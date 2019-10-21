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

public class EditMemo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_memo);

        EditText editText = (EditText)  findViewById(R.id.editmemo);

        Intent intent = getIntent();

        Memo memo = intent.getParcelableExtra("memo");
        int memoIdx = memo.getMemoIdx();



        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("メモ編集");
        actionBar.setHomeButtonEnabled(false);


        actionBar.setCustomView(actionView);

        DBExecute db = new DBExecute(this);
        Memo selectMemo = db.selectOneMemo(memoIdx);

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


        }

        return super.onOptionsItemSelected(item);
    }
}
