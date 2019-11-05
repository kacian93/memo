package kr.co.softcampus.memopad;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {
    private ArrayList<Memo> mData = null;
    private ArrayList<Memo> mDataFull = null;

    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text1;
        TextView text2;
        TextView idx;
        Memo data;

        public ViewHolder(View itemView) {
            super(itemView);
            idx = itemView.findViewById(R.id.memoidx);
            text1 = itemView.findViewById(R.id.memoContent);
            text2 = itemView.findViewById(R.id.memodate);

        }

        void onBind(final Memo data) {
            this.data = data;
            text1.setText(data.getMemoContent());
            text1.setGravity(Gravity.LEFT);
            //2行に作るために
            String date = data.getMemoDate().replace(' ', '\n');
            text2.setText(date);
            idx.setText(String.valueOf(data.getMemoIdx()));
            idx.setVisibility(View.GONE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context = v.getContext();
                    Intent intent = new Intent(context, EditMemo.class);

                    intent.putExtra("memo", data);
                    context.startActivity(intent);
                }
            });

        }
    }


    public RecyclerViewAdapter(ArrayList<Memo> mData) {
        this.mData = mData;
        mDataFull = new ArrayList<>(mData);
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
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

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Memo> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mDataFull);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Memo memo : mDataFull) {
                    if (memo.getMemoContent().toLowerCase().contains(filterPattern)) {
                        filteredList.add(memo);
                    }
                }
            }

            FilterResults result = new FilterResults();
            result.values = filteredList;

            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mData.clear();
            mData.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
