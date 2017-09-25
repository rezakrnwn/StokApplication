package com.gees.geesapplication.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gees.geesapplication.R;
import com.gees.geesapplication.add.AddBarangPresenter;
import com.gees.geesapplication.add.AddBarangView;
import com.gees.geesapplication.model.items.Stok;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by ServerToko on 13/09/2017.
 */

public class StokAdapter extends RecyclerView.Adapter<StokAdapter.MyViewHolder> {

    private List<Stok> stokList;
    public OnClickInAdapter onClickInAdapter;
    public String customerName;

    /**
     * View holder class
     * */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemDate;
        TextView tvStokAkhir;
        TextView tvStokMasuk;
        TextView tvStokKeluar;
        LinearLayout llCardView;
        LinearLayout llMasuk;
        LinearLayout llKeluar;
        LinearLayout llIcon;
        ImageView ivIcon;
        Button btnDetail;

        String status;
        String tanggal;
        String stokAwal;
        String stokAkhir;
        String stokMasuk;
        String stokKeluar;
        String supplier;
        String customer;

        public MyViewHolder(View view) {
            super(view);
            llMasuk = (LinearLayout) itemView.findViewById(R.id.llMasuk);
            llKeluar = (LinearLayout) itemView.findViewById(R.id.llKeluar);
            llIcon = (LinearLayout) itemView.findViewById(R.id.llIcon);
            llCardView = (LinearLayout) itemView.findViewById(R.id.llCardView);
            tvStokMasuk = (TextView) itemView.findViewById(R.id.tvStokMasuk);
            tvStokKeluar = (TextView) itemView.findViewById(R.id.tvStokKeluar);
            tvItemDate = (TextView) itemView.findViewById(R.id.tvItemDate);
            tvStokAkhir = (TextView) itemView.findViewById(R.id.tvStokAkhir);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
            btnDetail = (Button) itemView.findViewById(R.id.btnDetail);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                    View layout = inflater.inflate(R.layout.fragment_inflater_rincian,null);

                    final TextView tvTanggal = (TextView) layout.findViewById(R.id.tvTanggal);
                    final TextView tvSupplier = (TextView) layout.findViewById(R.id.tvSupplier);
                    final TextView tvCustomer = (TextView) layout.findViewById(R.id.tvCustomer);
                    final TextView tvStokMasuk = (TextView) layout.findViewById(R.id.tvStokMasuk);
                    final TextView tvStokKeluar = (TextView) layout.findViewById(R.id.tvStokKeluar);
                    final TextView tvStokAwal = (TextView) layout.findViewById(R.id.tvStokAwal);
                    final TextView tvStokAkhir = (TextView) layout.findViewById(R.id.tvStokAkhir);
                    final LinearLayout llMasuk = (LinearLayout) layout.findViewById(R.id.llMasuk) ;
                    final LinearLayout llKeluar = (LinearLayout) layout.findViewById(R.id.llKeluar);

                    if(status == "Masuk"){
                        llMasuk.setVisibility(View.VISIBLE);
                        tvStokMasuk.setText(stokMasuk);
                        //String name = onClickInAdapter.onClickInAdapter(supplier);
                        //tvSupplier.setText(name);
                        Log.d("Ini--- ","Masuk");
                    }else{
                        llKeluar.setVisibility(View.VISIBLE);
                        tvStokKeluar.setText(stokKeluar);
                        try {
                            onClickInAdapter = (OnClickInAdapter) context;
                        } catch (ClassCastException e) {
                            throw new ClassCastException(context.toString()
                                    + " must implement OnClickInAdapter");
                        }
                        String name = onClickInAdapter.onClickInAdapter(customer);
                        tvCustomer.setText(name);
                        Log.d("Ini--- ","Keluar"+customerName);
                    }

                    tvTanggal.setText(tanggal);
                    tvStokAwal.setText(stokAwal);
                    tvStokAkhir.setText(stokAkhir);

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Detail Rincian");
                    builder.setView(layout);
                    builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public StokAdapter(List<Stok> stokList) {
        this.stokList = stokList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Stok c = stokList.get(position);
        if(c.getMasuk() != null && c.getKeluar() == null){
            holder.status = "Masuk";
            holder.llMasuk.setVisibility(View.VISIBLE);
            holder.llKeluar.setVisibility(View.GONE);
            holder.tvStokMasuk.setText(c.getMasuk());
            holder.llIcon.setBackgroundResource(R.drawable.bg_circle_green);
            holder.ivIcon.setImageResource(R.drawable.fab_add);
            holder.llCardView.setBackgroundColor(Color.parseColor("#27ae60"));
            holder.supplier = c.getIdSupplier();
            holder.stokMasuk = c.getMasuk();
        }else if (c.getMasuk() == null && c.getKeluar() != null){
            holder.status = "Keluar";
            holder.llKeluar.setVisibility(View.VISIBLE);
            holder.llMasuk.setVisibility(View.GONE);
            holder.tvStokKeluar.setText(c.getKeluar());
            holder.llIcon.setBackgroundResource(R.drawable.bg_circle_red);
            holder.ivIcon.setImageResource(R.drawable.ic_minus_30px);
            holder.llCardView.setBackgroundColor(Color.parseColor("#c0392b"));
            holder.customer = c.getIdCustomer();
            holder.stokKeluar = c.getKeluar();
        }
        /*String oldDateFormat = c.getTanggal();
        Date newDateFormat = null;
        try {
            newDateFormat = dateFormat.parse(oldDateFormat);
            oldDateFormat = dateFormat.format(newDateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        holder.tvItemDate.setText(c.getTanggal());
        holder.tvStokAkhir.setText(String.valueOf(c.getStokAkhir()));

        holder.tanggal = c.getTanggal();
        holder.stokAkhir = c.getStokAkhir();
        holder.stokAwal = c.getStokAwal();

    }

    @Override
    public int getItemCount() {
        return stokList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_stok,parent, false);
        return new MyViewHolder(v);
    }

    public interface OnClickInAdapter {
        String onClickInAdapter(String id);
    }
}
