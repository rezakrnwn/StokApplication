package com.gees.geesapplication.detail;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.gees.geesapplication.R;
import com.gees.geesapplication.adapter.StokAdapter;
import com.gees.geesapplication.adapter.iShelfAdapter;
import com.gees.geesapplication.config.SharedPreferenceLogin;
import com.gees.geesapplication.main.MainActivity;
import com.gees.geesapplication.model.customer.CustomerDatum;
import com.gees.geesapplication.model.items.Stok;
import com.gees.geesapplication.model.shelf.ShelfDatum;
import com.gees.geesapplication.model.supplier.Supplier;
import com.gees.geesapplication.model.supplier.SupplierDatum;
import com.gees.geesapplication.network.ApiClient;
import com.gees.geesapplication.network.ApiService;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by SERVER on 12/09/2017.
 */

public class DetailActivity extends AppCompatActivity implements DetailView,StokAdapter.OnClickInAdapter,DetailPresenter.DetailPresenterInterface {

    @BindView(R.id.tvNamaBarang) TextView tvNamaBarang;
    @BindView(R.id.tvStockBarang) TextView tvStockBarang;
    @BindView(R.id.tvStockBarang2) TextView tvStockBarang2;
    @BindView(R.id.rvStok) RecyclerView rvStok;
    //@BindView(R.id.rvShelf) RecyclerView rvShelf;
    @BindView(R.id.pgBar) ProgressBar progressBar;
    @BindView(R.id.swipeToRefresh) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;

    Spinner spShelf;
    Spinner spSupplier;
    Spinner spCustomer;
    Spinner spReport;

    FloatingActionMenu famMenu;
    FloatingActionButton famTambah;
    FloatingActionButton famKurang;

    int itemId;
    String itemName;
    double itemStock;
    String strItemStock;
    int shelfId;
    int supplierId;
    int customerId;
    long reportId;
    String dateTime;
    String strDate;
    String strTime;
    String customerName = null;

    //Shelf
    List<String> shelfList = new ArrayList<>();
    List<ShelfDatum> shelfDatumList = new ArrayList<>();

    //Supplier
    List<String> supplierList = new ArrayList<>();
    List<SupplierDatum> supplierDatumList = new ArrayList<>();

    //Customer
    List<String> customerList = new ArrayList<>();
    List<CustomerDatum> customerDatumList = new ArrayList<>();

    //Stok
    List<Stok> stokList = new ArrayList<>();

    //Report
    List<String> reportTypeList = new ArrayList<>();

    Calendar calendar = Calendar.getInstance();

    SharedPreferenceLogin sharedPreferenceLogin = new SharedPreferenceLogin();
    DetailPresenter detailPresenter = new DetailPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("Detail Barang");
        ButterKnife.bind(this);
        getBundle();
        initToolbar();

        strTime = initCurrentTime();
        strDate = initCurrentDate();

        detailPresenter.attachView(this);
        detailPresenter.getSyncAdapter(rvStok);
        detailPresenter.getDetailStok(false,String.valueOf(itemId),
                sharedPreferenceLogin.getValue(getApplicationContext()).getInt("company",0),
                sharedPreferenceLogin.getValue(getApplicationContext()).getString("apiToken",""));
        detailPresenter.getCustomer(0,sharedPreferenceLogin.
                getValue(getApplicationContext()).getInt("company",0),sharedPreferenceLogin.
                getValue(getApplicationContext()).getString("apiToken",""));

        tvNamaBarang.setText(itemName);
        tvStockBarang.setText(myFormat(itemStock));
        tvStockBarang.setVisibility(View.GONE);

        famTambah = (FloatingActionButton) findViewById(R.id.menu_plus);
        famKurang = (FloatingActionButton) findViewById(R.id.menu_minus);
        famMenu = (FloatingActionMenu) findViewById(R.id.menuFam);

        authRoleView();

        detailPresenter.attachView(this);

        famTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                famMenu.close(true);
                inflaterStok(1,"Tambah Stok");
            }
        });

        famKurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                famMenu.close(true);
                inflaterStok(2,"Jual Barang");
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                detailPresenter.getDetailStok(true,String.valueOf(itemId),
                        sharedPreferenceLogin.getValue(getApplicationContext()).getInt("company",0),
                        sharedPreferenceLogin.getValue(getApplicationContext()).getString("apiToken",""));
            }
        });

    }

    void authRoleView(){
        if(sharedPreferenceLogin.getValue(this).getInt("roleId",0) == 1){

        }else if(sharedPreferenceLogin.getValue(this).getInt("roleId",0) == 2){
            Log.d("GG ","2");
            famKurang.setVisibility(View.GONE);
        }else if(sharedPreferenceLogin.getValue(this).getInt("roleId",0) == 3){
            Log.d("GG ","3");
            famTambah.setVisibility(View.GONE);
        }
    }

    void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailActivity.this,MainActivity.class));
                overridePendingTransition(R.anim.slide_to_left, R.anim.stay);
                /*onBackPressed();*/
            }
        });
    }

    void getBundle(){
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            itemId = 0;
            itemName = null;
            itemStock = 0.00;
            strItemStock = Double.toString(itemStock);
        } else {
            itemId = extras.getInt("itemId");
            itemName = extras.getString("itemName");
            itemStock = extras.getDouble("itemStock");
            strItemStock = Double.toString(itemStock);
        }
    }

    @OnClick(R.id.btnAddStock)
    void btnAddStock(){
        detailPresenter.attachView(this);
        inflaterStok(1,"Tambah Stok");
    }

    @OnClick(R.id.btnRemoveStock)
    void btnRemoveStock(){
        detailPresenter.attachView(this);
        inflaterStok(2,"Jual Barang");
    }

    @OnClick(R.id.btnFinish)
    void btnFinish(){
        startActivity(new Intent(DetailActivity.this, MainActivity.class));
    }

    void inflaterStok(final int code,String title){
        strDate = null;
        strTime = null;
        detailPresenter.getShelf(sharedPreferenceLogin.getValue(getApplicationContext()).getInt("company",0)
                ,sharedPreferenceLogin.getValue(getApplicationContext()).getString("apiToken",""));

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.fragment_inflater_stok,null);

        final EditText etDateStok = (EditText) layout.findViewById(R.id.etDateStok);
        final EditText etTimeStok = (EditText) layout.findViewById(R.id.etTimeStok);
        final EditText etStokBarang = (EditText) layout.findViewById(R.id.etStokBarang);
        final LinearLayout llCustomer = (LinearLayout) layout.findViewById(R.id.llCustomer);
        final LinearLayout llSupplier = (LinearLayout) layout.findViewById(R.id.llSupplier);
        final LinearLayout llShelf = (LinearLayout) layout.findViewById(R.id.llShelf);
        spShelf = (Spinner) layout.findViewById(R.id.spShelf);
        spSupplier = (Spinner) layout.findViewById(R.id.spSupplier);
        spCustomer = (Spinner) layout.findViewById(R.id.spCustomer);


        spShelf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                shelfId = shelfDatumList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        DateFormat df = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        String currentDate = sdf.format(Calendar.getInstance().getTime());
        String currentTime = df.format(Calendar.getInstance().getTime());

        DateFormat df2 = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        String currentDate2 = sdf2.format(Calendar.getInstance().getTime());
        String currentTime2 = df2.format(Calendar.getInstance().getTime());

        etDateStok.setText(currentDate);
        etTimeStok.setText(currentTime);

        strTime = currentTime2;
        strDate = currentDate2;

        if(code == 1){
            detailPresenter.getSupplier(sharedPreferenceLogin.
                    getValue(getApplicationContext()).getString("apiToken",""));
            llSupplier.setVisibility(View.VISIBLE);
            spSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    supplierId = supplierDatumList.get(position).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }else{
            detailPresenter.getCustomer(1,sharedPreferenceLogin.
                    getValue(getApplicationContext()).getInt("company",0),sharedPreferenceLogin.
                    getValue(getApplicationContext()).getString("apiToken",""));
            llCustomer.setVisibility(View.VISIBLE);
            llShelf.setVisibility(View.GONE);
            spCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    customerId = customerDatumList.get(position).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String dateFormatEt = "dd-MM-yyyy";
                String dateFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
                strDate = sdf.format(calendar.getTime());
                sdf = new SimpleDateFormat(dateFormatEt, Locale.getDefault());
                etDateStok.setText(sdf.format(calendar.getTime()));
            }
        };

        etDateStok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strDate = null;
                new DatePickerDialog(DetailActivity.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etTimeStok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                strTime = null;
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(DetailActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        etTimeStok.setText( selectedHour + ":" + selectedMinute);
                        strTime = selectedHour + ":" + selectedMinute + ":00";
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setView(layout);
        builder.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                famMenu.clearAnimation();
                dateTime = strDate +" "+ strTime;
                if (code == 1 && !etStokBarang.getText().toString().isEmpty()){
                    double stokBarang = Double.parseDouble(etStokBarang.getText().toString());
                    detailPresenter.addStock(sharedPreferenceLogin.getValue(getApplicationContext()).getInt("company",0),
                            itemId,shelfId,supplierId,stokBarang,dateTime,
                            sharedPreferenceLogin.getValue(getApplicationContext()).getString("apiToken",""));
                }else if (code == 2 && !etStokBarang.getText().toString().isEmpty()){
                    double stokBarang = Double.parseDouble(etStokBarang.getText().toString());
                    detailPresenter.removeStock(sharedPreferenceLogin.getValue(getApplicationContext()).getInt("company",0),
                            itemId,shelfId,customerId,stokBarang,dateTime,
                            sharedPreferenceLogin.getValue(getApplicationContext()).getString("apiToken",""));
                }else{
                    Toast.makeText(getApplicationContext(),"Proses Gagal",Toast.LENGTH_LONG).show();
                }

                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    String initCurrentDate(){
        String currentDate = "";
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat,Locale.getDefault());
        currentDate = sdf.format(calendar.getTime());

        return currentDate;
    }

    String initCurrentTime(){
        String currentTime = "";
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        currentTime = calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + ":" +
                calendar.get(Calendar.SECOND);

        return currentTime;
    }

    void inflaterExport(){

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.fragment_inflater_export_pdf,null);

        spReport = (Spinner) layout.findViewById(R.id.spReport);
        Button btnPDF = (Button) layout.findViewById(R.id.btnPDF);

        initReportSpinner();

        btnPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reportId==0){
                    detailPresenter.createPdf(0,itemName,strItemStock,stokList);
                }else if(reportId==1){
                    detailPresenter.createPdf(1,itemName,strItemStock,stokList);
                }
            }
        });

        spReport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reportId = parent.getItemIdAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Export to PDF");
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

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onSuccessAdd() {
        detailPresenter.getDetailStok(false,String.valueOf(itemId),
                sharedPreferenceLogin.getValue(getApplicationContext()).getInt("company",0),
                sharedPreferenceLogin.getValue(getApplicationContext()).getString("apiToken",""));
    }

    @Override
    public void onSuccessRemove() {
        detailPresenter.getDetailStok(false,String.valueOf(itemId),
                sharedPreferenceLogin.getValue(getApplicationContext()).getInt("company",0),
                sharedPreferenceLogin.getValue(getApplicationContext()).getString("apiToken",""));
    }

    @Override
    public void onProcessing() {
        //progressBar.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btnDone)
    void btnDone(){
        startActivity(new Intent(DetailActivity.this, MainActivity.class));
    }

    @OnClick(R.id.btnExport)
    void btnExport() {
        inflaterExport();
    }

    @Override
    public void processFinish() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Server Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmpty() {
        Toast.makeText(this, "Data masih kosong", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void noConnection(){
        Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openPDF(File file) {
        Intent iOpenPdf = new Intent(Intent.ACTION_VIEW);
        iOpenPdf.setDataAndType(Uri.fromFile(file),"application/pdf");
        iOpenPdf.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(iOpenPdf);
    }

    @Override
    public void getShelfList(List<ShelfDatum> shelfDatumList, List<String> stringList) {
        this.shelfDatumList = shelfDatumList;
        shelfList = stringList;
        initSpinner();
    }

    @Override
    public void getSupplierList(List<SupplierDatum> supplierDatumList, List<String> supplierList) {
        this.supplierDatumList = supplierDatumList;
        this.supplierList = supplierList;
        initSupplierSpinner();
    }

    @Override
    public void getCustomerList(List<CustomerDatum> customerDatumList, List<String> customerList) {
        this.customerDatumList = customerDatumList;
        this.customerList = customerList;
        initCustomerSpinner();
    }

    @Override
    public void getCustomer(List<CustomerDatum> customerDatumList) {
        this.customerDatumList = customerDatumList;
        Log.v("-",""+customerDatumList.size());
    }

    @Override
    public void updateStok(String stok) {
        tvStockBarang2.setText(stok);
    }

    public static String myFormat(double number) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(number).replaceAll("\\.00$", "");
    }

    void initSpinner(){
        ArrayAdapter<String> shelfAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, shelfList);
        shelfAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spShelf.setAdapter(shelfAdapter);
    }

    void initSupplierSpinner(){
        ArrayAdapter<String> supplierAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, supplierList);
        supplierAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSupplier.setAdapter(supplierAdapter);
    }

    void initCustomerSpinner(){
        ArrayAdapter<String> customerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, customerList);
        customerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCustomer.setAdapter(customerAdapter);
    }

    void initReportSpinner(){
        reportTypeList.clear();
        reportTypeList.add("Laporan Stok Masuk");
        reportTypeList.add("Laporan Stok Penjualan");
        ArrayAdapter<String> reportAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, reportTypeList);
        reportAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spReport.setAdapter(reportAdapter);
    }

    @Override
    public String onClickInAdapter(String id) {
        for(int i=0;i<customerDatumList.size();i++){
            Log.d("NJXXX",""+customerDatumList.get(i).getId()+" == "+id);
            if(customerDatumList.get(i).getId().equals(Integer.parseInt(id))){
                customerName = customerDatumList.get(i).getNama();

            }
        }
        return customerName;
    }

    @Override
    public void getStokList(List<Stok> stokList) {
        this.stokList = stokList;
    }
}
