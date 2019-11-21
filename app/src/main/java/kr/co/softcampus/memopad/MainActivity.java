package kr.co.softcampus.memopad;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_INSERTMEMO=100;
    TextView noneMemo;
    DBExecute dbExecute;
    MenuItem searchItem;
    String prevSearch;
    SearchView searchView;
    boolean isClosed=true;
    ArrayList<Memo> list= new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter = new RecyclerViewAdapter(list);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = getLayoutInflater().from(this).inflate(R.layout.activity_main, null);
        setContentView(view);


        ActionBar actionBar = getSupportActionBar();
        noneMemo = (TextView) findViewById(R.id.noneMemo);

        //actionbarをカスタマイズができるように
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setTitle("メモ帳");

        //既存のものを隠す
        actionBar.setDisplayHomeAsUpEnabled(false);


    }
    @Override
    protected void onResume() {
        if(list.isEmpty()||prevSearch==null||prevSearch.length()<1||prevSearch.equals(""))
            showMemo();

        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);


        searchItem = menu.findItem(R.id.search);

        //検索のページの住所を持っておく

        searchView=  (SearchView) findViewById(R.id.searchText);

        Button closeBtn = new Button(this);
        closeBtn.setBackgroundResource(R.drawable.ic_menu_close_clear_cancel_background);




        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //検索のボタンを押した場合イベント処理


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                prevSearch = newText;

                list = dbExecute.searchMemo(newText);
                if(newText.equals("")|| newText.length()==0){
                    list=dbExecute.selectAllMemo();
                }
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.createMemo:
                Intent intent = new Intent(getApplicationContext(),EditMemo.class);
                startActivityForResult(intent, REQUEST_INSERTMEMO);
                break;
            case R.id.search:

                closeOrShow(isClosed);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(isClosed==false){
            closeOrShow(isClosed);
            return;
        }
        super.onBackPressed();
    }

    public void showMemo(){
        dbExecute = new DBExecute(this);
        list = dbExecute.selectAllMemo();
        adapter = new RecyclerViewAdapter(list);
        adapter.notifyDataSetChanged();


        recyclerView = findViewById(R.id.recylerview);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        if(!list.isEmpty()) {
            noneMemo.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }else{
            noneMemo.setVisibility(View.VISIBLE);
            noneMemo.setText("メモがありません");
            noneMemo.setGravity(Gravity.CENTER);
        }


    }
    public void closeOrShow(boolean currStat){
        isClosed = currStat;
        if(isClosed==true) {
            recyclerView.setPadding(0, searchView.getHeight()+50, 0, 0);
            searchView.setVisibility(View.VISIBLE);
            searchView.onActionViewExpanded();
            searchView.callOnClick();
            searchView.bringToFront();
            isClosed=false;
            Log.d("test","open");
        }else{
            searchView.setVisibility(View.INVISIBLE);
            searchView.setQuery("",false);
            recyclerView.setPadding(0,0,0,0);
            Log.d("test","close");
            isClosed=true;
        }
    }
}
