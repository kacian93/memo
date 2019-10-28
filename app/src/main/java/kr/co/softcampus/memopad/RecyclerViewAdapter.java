package kr.co.softcampus.memopad;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        Context context;
        TextView text1;
        TextView text2;
        TextView idx;

        public ViewHolder(View itemView) {
            super(itemView);
            idx = itemView.findViewById(R.id.memoidx);
            text1 = itemView.findViewById(R.id.memoContent);
            text2 = itemView.findViewById(R.id.memodate);


        }
        void onBind(Memo data) {
            if(data.getMemoContent().length()>30) {
                text1.setText(data.getMemoContent().substring(0, 30)+"…");

            }else{
                text1.setText(data.getMemoContent());
            }
            text1.setGravity(Gravity.LEFT);
            //2行に作るために
            String date = data.getMemoDate().replace(' ','\n');
            text2.setText(date);
            idx.setText(String.valueOf(data.getMemoIdx()));
            idx.setVisibility(View.GONE);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Log.d("test","RecylerView Adapter idx "+ idx.getText().toString() );
                    context = itemView.getContext();
                    Intent intent = new Intent(context, EditMemo.class);

                    Memo memo = new Memo();
                    memo.memoContent = text1.getText().toString();
                    memo.memoDate = text2.getText().toString();
                    memo.memoIdx = Integer.parseInt(idx.getText().toString());

                    intent.putExtra("memo",memo);
                    context.startActivity(intent);


                }
            });

        }
    }


    public RecyclerViewAdapter(ArrayList<Memo> mData) {
        this.mData = mData;
    }

    private ArrayList<Memo> mData = null;

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.list_view, parent, false);
        RecyclerViewAdapter.ViewHolder vh = new RecyclerViewAdapter.ViewHolder(view);


        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.onBind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}
