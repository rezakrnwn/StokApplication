package com.gees.geesapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gees.geesapplication.R;
import com.gees.geesapplication.detail.DetailActivity;
import com.gees.geesapplication.model.items.Barang;
import com.gees.geesapplication.model.items.Stok;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by SERVER on 11/09/2017.
 */

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Barang> barangList = null;
    private List<Barang> barangListCopy;
    private ArrayList<Barang> barangArrayList = new ArrayList<Barang>();

    public ItemAdapter(List<Barang> barangList, ArrayList<Barang> barangArrayList){
        this.barangList = barangList;
        //this.barangListCopy = barangList;
        //this.barangArrayList.addAll(barangList);
        //Log.d("BARANGLIST ",""+barangList.get(0).getNama());
        this.barangArrayList = barangArrayList;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;
        TextView tvItemStock;
        TextView tvLastUpdate;

        String idItem;
        String itemName;
        String itemStock;
        double dItemStock;
        int intIdItem;

        List<Stok> stokList = new ArrayList<>();

        public CardViewHolder(View itemView) {
            super(itemView);
            tvItemName = (TextView) itemView.findViewById(R.id.tvItemName);
            tvItemStock = (TextView) itemView.findViewById(R.id.tvItemStock);
            tvLastUpdate = (TextView) itemView.findViewById(R.id.tvLastUpdate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dItemStock = Double.parseDouble(itemStock);
                    intIdItem = Integer.parseInt(idItem);
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("itemName",itemName);
                    intent.putExtra("itemStock",dItemStock);
                    intent.putExtra("itemId",intIdItem);
                    context.startActivity(intent);
                }
            });
        }
    }

    public class StokHolder extends RecyclerView.ViewHolder {
        TextView tvItemDate;
        TextView tvStokAkhir;

        List<Stok> stokList = new ArrayList<>();

        public StokHolder(View itemView) {
            super(itemView);
            tvItemDate = (TextView) itemView.findViewById(R.id.tvItemDate);
            tvStokAkhir = (TextView) itemView.findViewById(R.id.tvStokAkhir);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item, viewGroup, false);
                return new CardViewHolder(view);
            case 1:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_stok, viewGroup, false);
                return new StokHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (barangList != null) {
            Barang object = barangList.get(position);
            if (object != null) {
                return object.getRecyclerType();
            }
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (barangList.get(position).getRecyclerType()){
            case 0:
                //((CardViewHolder) holder).mBookCategory = bookList.get(position).getBookCategory();

                ((CardViewHolder) holder).stokList = barangList.get(position).getStok();
                ((CardViewHolder) holder).idItem = barangList.get(position).getId();
                ((CardViewHolder) holder).tvItemName.setText(barangList.get(position).getNama());
                ((CardViewHolder) holder).itemName = barangList.get(position).getNama();

                if(!((CardViewHolder) holder).stokList.isEmpty()){
                    ArrayList<Date> dateList = new ArrayList<>();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    for(int i=0;i<((CardViewHolder) holder).stokList.size();i++){
                        //Collections.max(((CardViewHolder) holder).stokList.get(i).getLastUpdate().to);
                        String strDate = ((CardViewHolder) holder).stokList.get(i).getLastUpdate();
                        try {
                            //List<Date> date = (List<Date>) dateFormat.parse(strDate);
                            //dateList = dateFormat.parse(strDate);
                            dateList.add(dateFormat.parse(strDate));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    Date maxDate = Collections.max(dateList);
                    Date currentTime = Calendar.getInstance().getTime();

                    long updateDateMillis = getDateInMillis(dateFormat.format(maxDate));
                    long currentDateMillis = getDateInMillis(dateFormat.format(currentTime));
                    String relativeTime = (String) DateUtils.getRelativeTimeSpanString
                            (updateDateMillis,currentDateMillis,DateUtils.MINUTE_IN_MILLIS);
                    ((CardViewHolder) holder).tvLastUpdate.setText("Last Update: "+relativeTime);

                }else{
                    ((CardViewHolder) holder).tvLastUpdate.setText("");
                }

                if(barangList.get(position).getStokAkhir() == null){
                    ((CardViewHolder) holder).itemStock = "0.00";
                    ((CardViewHolder) holder).tvItemStock.setText("Stok : 0.00");
                }else{
                    ((CardViewHolder) holder).tvItemStock.setText("Stok : "+
                            barangList.get(position).getStokBarang());
                    ((CardViewHolder) holder).itemStock = barangList.get(position).getStokBarang();
                }
                break;
            case 1:
                ((StokHolder) holder).stokList = barangList.get(position).getStok();
                break;
        }
    }

    public static long getDateInMillis(String srcDate) {
        SimpleDateFormat desiredFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");

        long dateInMillis = 0;
        try {
            Date date = desiredFormat.parse(srcDate);
            dateInMillis = date.getTime();
            return dateInMillis;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void filter2(String text) {
        List<Barang> barangListCopy;
        barangListCopy = barangList;
        barangList.clear();
        if(text.isEmpty()){
            barangList.addAll(barangListCopy);
        } else{
            text = text.toLowerCase();
            for(Barang item: barangListCopy){
                if(item.getNama().toLowerCase().contains(text)){
                    barangList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        Log.d("GetSearch: ",charText);
        barangList.clear();
        if (charText.length() == 0) {
            barangList.addAll(barangArrayList);
            Log.d("FILTER : ","if = 0");
        } else {
            Log.d("FILTER : ","Mulai mencari");
            for (Barang wp : barangArrayList) {
                Log.d("Loop: ",wp.getNama());
                if (wp.getNama().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    barangList.add(wp);
                    Log.d("FILTER : ","else");
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return barangList.size();
    }
}
