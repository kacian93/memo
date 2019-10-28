package kr.co.softcampus.memopad;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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
    public void onBackPressed() {
        super.onBackPressed();
        deleteMemoClick();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.deleteIcon){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("削除しますか？");
            builder.setMessage("本当に削除しますか？");

            DialogListener listener = new DialogListener();

            builder.setPositiveButton("はい",listener);
            builder.setNegativeButton("いいえ", null);

            builder.show();

        }
        if(id == android.R.id.home){
            deleteMemoClick();
        }

        return super.onOptionsItemSelected(item);
    }
    public void deleteMemoClick(){
        if(editText.getText().toString().equals("")){
            db.deleteMemo(selectMemo.memoIdx);
            finish();
        }
        if(!editText.getText().equals(selectMemo.getMemoContent())){
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String today = sdf.format(new Date());
            db.updateMemo(selectMemo.memoIdx, editText.getText().toString(),today);

            finish();
        }
    }
    private class DialogListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    db.deleteMemo(selectMemo.getMemoIdx());
                    finish();
                    break;
            }
        }
    }
}
