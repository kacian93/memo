package kr.co.softcampus.memopad;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
        int memoIdx = memo==null?0:memo.getMemoIdx();
        selectMemo = memo;
        //actionバーを作る
        ActionBar actionBar = getSupportActionBar();

        db = new DBExecute(this);
        if(memoIdx != 0) {

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("メモ編集");
            actionBar.setHomeButtonEnabled(false);

            editText.setText(memo.getMemoContent());

        }else{
            actionBar.setHomeButtonEnabled(false);
            actionBar.setTitle("新規メモ");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void deleteMemoClick(){
        final String currContent  = editText.getText().toString();
        //メモがある場合
        if(selectMemo!=null ) {
            final String prevContent = selectMemo.getMemoContent();
            //内容を全部消した場合
            if (currContent.equals("")|| currContent.length()<1) {
                db.deleteMemo(selectMemo.memoIdx);
            }
            else if(!currContent.equals(prevContent)){
                final Date now = new Date();



                db.updateMemo(selectMemo.memoIdx, currContent,now);
            }
            finish();
        }
        //メモが既存にない場合(selectMemo==null)
        else{
            //内容を入れた場合
            if(!currContent.equals("")) {
                final Date now = new Date();


                db.insertMemo(editText.getText().toString(), now);
                finish();
            }
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
