package kr.co.softcampus.memopad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text1;
        TextView text2;

        public ViewHolder(View itemView) {
            super(itemView);

            text1 = itemView.findViewById(R.id.memoContent);
            text2 = itemView.findViewById(R.id.memodate);
        }

        void onBind(Memo data) {
            text1.setText(data.getMemoContent());
            text2.setText(data.getMemoDate());
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
