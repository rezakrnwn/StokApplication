package com.gees.geesapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gees.geesapplication.R;

import java.util.List;

/**
 * Created by ServerToko on 20/09/2017.
 */

public class ShelfAdapter extends RecyclerView.Adapter<ShelfAdapter.MyViewHolder> implements iShelfAdapter{

    private List<String> shelfList;

    /**
     * View holder class
     * */
    public ShelfAdapter(List<String> shelfList) {
        this.shelfList = shelfList;
    }

    @Override
    public void onChangeShelf(int shelfId) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvShelfName;
        String shelfId;

        public MyViewHolder(View view) {
            super(view);

            tvShelfName = (TextView) itemView.findViewById(R.id.tvShelfName);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onChangeShelf(Integer.parseInt(shelfId));
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String shelf = shelfList.get(position);
        //ShelfStokData shelf = shelfList.get(position);
        //holder.tvShelfName.setText(shelf.getName());
        //holder.shelfId = shelf.getIdShelf();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_shelf,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return shelfList.size();
    }
}
