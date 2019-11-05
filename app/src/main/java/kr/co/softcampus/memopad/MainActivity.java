package kr.co.softcampus.memopad;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_INSERTMEMO=100;
    TextView noneMemo;
    DBExecute dbExecute;
    ArrayList<Memo> list= new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter = new RecyclerViewAdapter(list);
    @Override
    protected void onResume() {
        showMemo();

        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = getLayoutInflater().from(this).inflate(R.layout.activity_main, null);
        setContentView(view);


        ActionBar actionBar = getSupportActionBar();
        noneMemo = (TextView) findViewById(R.id.noneMemo);

        //actionbarをカスタマイズができるように
        actionBar.setDisplayShowCustomEnabled(true);

        //既存のものを隠す
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);


        MenuItem searchItem = menu.findItem(R.id.search);
        MenuItem createMemo=  menu.findItem(R.id.createMemo);
        SearchView searchView = (SearchView) searchItem.getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

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

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
            noneMemo.setText("");
            adapter.notifyDataSetChanged();
        }else{
            noneMemo.setText("メモがありません");
            noneMemo.setGravity(Gravity.CENTER);
        }


    }
}
