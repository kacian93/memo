package kr.co.softcampus.memopad;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_INSERTMEMO=100;
    TextView noneMemo;
    DBExecute dbExecute;
    ArrayList<Memo> list=null;
    NewMemo newMemo = new NewMemo();

    @Override
    protected void onResume() {
        super.onResume();
        showMemo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        noneMemo = (TextView) findViewById(R.id.noneMemo);

        //actionbarをカスタマイズができるように
        actionBar.setDisplayShowCustomEnabled(true);

        //既存のものを隠す
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);


        //actionbarを代替するViewを作る
        LayoutInflater layoutInflater = getLayoutInflater();
        View actionView =layoutInflater.inflate(R.layout.main_menu,null);

        TextView text_menu = (TextView)actionView.findViewById(R.id.textView);
        ImageButton createMemo= (ImageButton) actionView.findViewById(R.id.createMemo);

        //+ボタンを押すと新しいメモが生成します。
        createMemo.setOnClickListener(newMemo);


        text_menu.setText("メモ帳");

        actionBar.setCustomView(actionView);
        showMemo();



    }


    public void showMemo(){
        dbExecute = new DBExecute(this);
        list = dbExecute.selectAllMemo();


        if(!list.isEmpty()) {
            noneMemo.setText("");
            RecyclerView recyclerView = findViewById(R.id.recylerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(list);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else{
            noneMemo.setText("メモがありません");
            noneMemo.setGravity(Gravity.CENTER);
        }


    }
    class NewMemo implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),InsertMemo.class);
            startActivityForResult(intent, REQUEST_INSERTMEMO);
        }
    }
}
