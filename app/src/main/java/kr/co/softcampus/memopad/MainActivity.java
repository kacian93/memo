package kr.co.softcampus.memopad;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_INSERTMEMO=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();

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
        NewMemo newMemo = new NewMemo();
        createMemo.setOnClickListener(newMemo);


        text_menu.setText("メモ帳");

        actionBar.setCustomView(actionView);

        ArrayList<Memo> list;

        DBExecute dbExecute = new DBExecute(this);
        list = dbExecute.selectAllMemo();


        RecyclerView recyclerView = findViewById(R.id.recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(list);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    class NewMemo implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),InsertMemo.class);
            startActivityForResult(intent, REQUEST_INSERTMEMO);
        }
    }
}
