package com.gees.geesapplication.detail;

import android.os.Environment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.gees.geesapplication.adapter.ShelfAdapter;
import com.gees.geesapplication.adapter.StokAdapter;
import com.gees.geesapplication.base.BasePresenter;
import com.gees.geesapplication.model.customer.Customer;
import com.gees.geesapplication.model.customer.CustomerDatum;
import com.gees.geesapplication.model.items.Barang;
import com.gees.geesapplication.model.items.Items;
import com.gees.geesapplication.model.items.Stok;
import com.gees.geesapplication.model.shelf.Shelf;
import com.gees.geesapplication.model.shelf.ShelfDatum;
import com.gees.geesapplication.model.stok.AddStokBarang;
import com.gees.geesapplication.model.stok.RemoveStokBarang;
import com.gees.geesapplication.model.supplier.Supplier;
import com.gees.geesapplication.model.supplier.SupplierDatum;
import com.gees.geesapplication.network.ApiClient;
import com.gees.geesapplication.network.ApiService;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by SERVER on 12/09/2017.
 */

public class DetailPresenter implements BasePresenter<DetailView> {

    DetailView detailView;
    StokAdapter stokAdapter;
    ShelfAdapter shelfAdapter;
    List<Stok> stokList = new ArrayList<>();
    String nameSupplier;
    String customerName;
    //List<ShelfStokData> shelfData = new ArrayList<>();

    List<ShelfDatum> shelfDatumList = new ArrayList<>();
    List<SupplierDatum> supplierDatumList = new ArrayList<>();
    List<CustomerDatum> customerDatumList = new ArrayList<>();
    List<CustomerDatum> customerData = new ArrayList<>();

    LinearLayoutManager mLayoutManager;
    LinearLayoutManager mLayoutManagerShelf;

    private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 24,
            Font.BOLD);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

    DetailPresenterInterface detailPresenterInterface;

    @Override
    public void attachView(DetailView view) {
        this.detailView = view;
    }

    @Override
    public void detachView() {
        this.detailView = null;
    }

    @Override
    public boolean isViewAttached() {
        return this.detailView != null;
    }

    void addStock(int barangId,int shelfId, int supplierId, double qty, String tanggal, String token){
        Retrofit retrofit = ApiClient.getClient(detailView.getContext());
        ApiService apiService = retrofit.create(ApiService.class);
        Call<AddStokBarang> addStokCall = apiService.addStok(barangId,shelfId,supplierId,qty,tanggal,token);
        addStokCall.enqueue(new Callback<AddStokBarang>() {
            @Override
            public void onResponse(Call<AddStokBarang> call, retrofit2.Response<AddStokBarang> response) {

                if(response.body() != null){
                    AddStokBarang addStokBarang = response.body();
                    Boolean status = addStokBarang.getStatus();

                    if(status){
                        Toast.makeText(detailView.getContext(),"Berhasil Menambah Stok",
                                Toast.LENGTH_LONG).show();
                        detailView.onSuccessAdd();
                    }else{
                        Toast.makeText(detailView.getContext(),"Input Gagal",
                                Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(detailView.getContext(),"Cek Koneksi Internet",
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<AddStokBarang> call, Throwable t) {
            }
        });
    }

    void removeStock(int barangId,int shelfId, int customerId, double qty, String tanggal, String token){
        Retrofit retrofit = ApiClient.getClient(detailView.getContext());
        ApiService apiService = retrofit.create(ApiService.class);
        Call<RemoveStokBarang> removeStokCall = apiService.removeStok(barangId,shelfId,customerId,qty,tanggal,token);
        removeStokCall.enqueue(new Callback<RemoveStokBarang>() {
            @Override
            public void onResponse(Call<RemoveStokBarang> call, retrofit2.Response<RemoveStokBarang> response) {

                if(response.body() != null){
                    RemoveStokBarang removeStokBarang = response.body();
                    Boolean status = removeStokBarang.getStatus();

                    if(status){
                        Toast.makeText(detailView.getContext(),"Berhasil Menghapus Stok",
                                Toast.LENGTH_LONG).show();
                        detailView.onSuccessRemove();
                    }else{
                        Toast.makeText(detailView.getContext(),"Input Gagal",
                                Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(detailView.getContext(),"Cek Koneksi Internet",
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<RemoveStokBarang> call, Throwable t) {
            }
        });
    }

    public void getDetailStok(final boolean refresh, final String id, String token){

        Retrofit retrofit = ApiClient.getClient(detailView.getContext());
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Items> itemsCall = apiService.getSingleItem(token);
        itemsCall.enqueue(new Callback<Items>() {
            @Override
            public void onResponse(Call<Items> call, Response<Items> response) {

                Log.d("M A S U K","response");

                if(response.body() != null){
                    Items items = response.body();
                    List<Barang> barangList = items.getBarang();
                    List<Stok> stokList2 = new ArrayList<Stok>();
                    List<Stok> stokListDesc = new ArrayList<Stok>();
                    List<String> shelfList = new ArrayList<String>();
                    ArrayList<Integer> arrayListId = new ArrayList<Integer>();
                    ArrayList<String> filterShelfId = new ArrayList<String>();

                    if(items.getStatus()){
                        for(int i=0;i<barangList.size();i++){
                            if(barangList.get(i).getId().equals(id)){
                                stokList2 = barangList.get(i).getStok();
                            }
                        }

                        if(!stokList2.isEmpty()){
                            for(int m=0;m<stokList2.size();m++){
                                shelfList.add(stokList2.get(m).getIdShelf());
                            }
                            for(int j=0;j<shelfList.size();j++){
                                if(!shelfList.get(j).equals("0")){
                                    if(filterShelfId.isEmpty()){
                                        filterShelfId.add(shelfList.get(j));
                                    }else{
                                        for(int y=0;y<filterShelfId.size();y++){
                                            if(filterShelfId.get(y).compareTo(shelfList.get(j))>0){
                                                filterShelfId.add(shelfList.get(j));
                                            }
                                        }
                                    }
                                }
                            }
                            for(int u=0;u<filterShelfId.size();u++){
                                Log.d("Shelf ",""+filterShelfId.get(u));
                            }

                            for(int y=stokList2.size()-1;y>=0;y--){
                                stokListDesc.add(stokList2.get(y));
                            }
                        }

                        for (int c=0;c<stokList2.size();c++){
                            arrayListId.add(Integer.parseInt(stokList2.get(c).getId()));
                        }

                        if(!arrayListId.isEmpty()){
                            int maxId = Collections.max(arrayListId);
                            //Log.d("MAX ID ",""+maxId);

                            for(int x=0;x<stokList2.size();x++){
                                if(String.valueOf(maxId).equals(stokList2.get(x).getId())){
                                    detailView.updateStok(stokList2.get(x).getStokAkhir());
                                }
                            }
                        }

                        stokList.clear();
                        stokList.addAll(stokListDesc);
                        stokAdapter.notifyDataSetChanged();

                        try {
                            detailPresenterInterface = (DetailPresenterInterface) detailView.getContext();
                        } catch (ClassCastException e) {
                            throw new ClassCastException(detailView.getContext()
                                    + " must implement interface");
                        }
                        detailPresenterInterface.getStokList(stokList);
                    }else{
                        Toast.makeText(detailView.getContext(), "Data Kosong",
                                Toast.LENGTH_LONG).show();
                    }
                    if(refresh) {
                        detailView.processFinish();
                    }
                }else{
                    if(refresh) {
                        detailView.processFinish();
                    }
                    Toast.makeText(detailView.getContext(), "Cek koneksi internet Anda",
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Items> call, Throwable t) {
                Log.d("M A S U K","failure");
            }

        });
    }

    /*public void getDetailStokByShelf(final boolean refresh, final int idBarang,
                                     final int idShelf, String token){

        Retrofit retrofit = ApiClient.getClient(detailView.getContext());
        ApiService apiService = retrofit.create(ApiService.class);
        Call<StokById> itemsCall = apiService.getStokById(idBarang,idShelf,token);
        itemsCall.enqueue(new Callback<StokById>() {
            @Override
            public void onResponse(Call<StokById> call, Response<StokById> response) {

                Log.d("M A S U K","response");

                if(response.body() != null){
                    StokById items = response.body();
                    List<Stok> s = items.getStok();

                    if(items.getStatus()){

                        stokList.clear();
                        stokList.addAll(s);
                        stokAdapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(detailView.getContext(), "Data Kosong",
                                Toast.LENGTH_LONG).show();
                    }
                    if(refresh) {
                        detailView.processFinish();
                    }
                }else{
                    if(refresh) {
                        detailView.processFinish();
                    }
                    Toast.makeText(detailView.getContext(), "Cek koneksi internet Anda",
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<StokById> call, Throwable t) {
                Log.d("M A S U K","failure");
            }

        });
    }*/

    public void getSyncAdapter(RecyclerView recyclerView){
        mLayoutManager = new LinearLayoutManager(detailView.getContext());
        stokAdapter = new StokAdapter(stokList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(stokAdapter);
    }

    /*public void getListShelf(final int id, String token){
        *//*AddShelf addShelf = new AddShelf("0","Semua");
        stringList.add(addShelf);*//*
        shelfData.clear();
        *//*ShelfStokData sfd = new ShelfStokData("0","Semua");
        shelfData.add(sfd);*//*
        Retrofit retrofit = ApiClient.getClient(detailView.getContext());
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ShelfStok> itemsCall = apiService.getShelfStok(id,token);
        itemsCall.enqueue(new Callback<ShelfStok>() {
            @Override
            public void onResponse(Call<ShelfStok> call, Response<ShelfStok> response) {

                Log.d("M A S U K","response");

                if(response.body() != null){
                    ShelfStok shelfStok = response.body();
                    List<ShelfStokData> shelfStokData2 = shelfStok.getData();

                    if(shelfStok.getStatus()){
                        //shelfList.add("Semua");
                        *//*Log.d("SizeShelf ", ""+shelfData.size());
                        Log.d("SizeShelf2 ", ""+shelfStokData2.size());*//*
                        //shelfData.addAll(shelfStokData2);
                        //shelfAdapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(detailView.getContext(), "Data Kosong",
                                Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(detailView.getContext(), "Cek koneksi internet Anda",
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ShelfStok> call, Throwable t) {
                Log.d("M A S U K","failure");
            }

        });
    }
*/
    public void getSyncAdapterShelf(RecyclerView recyclerView){
        mLayoutManagerShelf = new LinearLayoutManager(detailView.getContext(),LinearLayoutManager.HORIZONTAL,false);
        //shelfAdapter = new ShelfAdapter(shelfData);
        //stokAdapter = new StokAdapter(stokList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManagerShelf);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(shelfAdapter);
    }

    void createPdf(int reportType,String namaBarang, String jumlahStok, List<Stok> stokList) {

        // TODO Auto-generated method stub
        Document document = new Document();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/StokBarang";

        File dir = new File(path);
        if(!dir.exists())
            dir.mkdirs();

        Log.d("PDFCreator", "PDF Path: " + path);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDate = sdf.format(Calendar.getInstance().getTime());

        File file = new File(dir, currentDate+".pdf");
        File file2 = new File(dir, currentDate);
        String oldPath = file.getPath();
        String newPath = file2.getPath();
        try {

            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(document, fOut);

            //open the document
            document.open();
            Paragraph p1 = null;
            if(reportType==0){
                p1 = new Paragraph("Laporan Stok Masuk",titleFont);
            }else if(reportType==1){
                p1 = new Paragraph("Laporan Penjualan Stok",titleFont);
            }

            Font paraFont= new Font(Font.FontFamily.COURIER);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p1.setFont(paraFont);

            //add paragraph to document
            document.add(p1);

            Paragraph p2 = new Paragraph();
            addEmptyLine(p2,3);

            Phrase phTglLaporan = new Phrase();
            phTglLaporan.add(new Chunk("Tanggal Laporan: ",subFont));
            phTglLaporan.add(new Chunk(currentDate));
            p2.add(new Paragraph(phTglLaporan));

            Phrase phNamaBarang = new Phrase();
            phNamaBarang.add(new Chunk("Nama Barang: ",subFont));
            phNamaBarang.add(new Chunk(namaBarang));
            p2.add(new Paragraph(phNamaBarang));

            Phrase phJumlahStok = new Phrase();
            phJumlahStok.add(new Chunk("Jumlah Stok: ",subFont));
            phJumlahStok.add(new Chunk(jumlahStok));
            p2.add(new Paragraph(phJumlahStok));

            addEmptyLine(p2,2);

            PdfPTable table = new PdfPTable(5);
            PdfPCell c1 = new PdfPCell(new Phrase("Tanggal"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            if(reportType==0){
                c1 = new PdfPCell(new Phrase("Id Supplier"));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("Stok Masuk"));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
            }else if(reportType==1){
                c1 = new PdfPCell(new Phrase("Id Customer"));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("Stok Keluar"));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
            }

            c1 = new PdfPCell(new Phrase("Stok Awal"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Stok Akhir"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            table.setHeaderRows(1);

            for (int i=0;i<stokList.size();i++){
                if(reportType==0){
                    if(stokList.get(i).getKeluar() == null){
                        DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = inputFormatter.parse(stokList.get(i).getDateStamp());

                        DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
                        String output = outputFormatter.format(date);

                        c1 = new PdfPCell(new Phrase(output));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(c1);

                        c1 = new PdfPCell(new Phrase(stokList.get(i).getIdSupplier()));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(c1);

                        c1 = new PdfPCell(new Phrase(stokList.get(i).getMasuk()));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(c1);

                        c1 = new PdfPCell(new Phrase(stokList.get(i).getStokAwal()));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(c1);

                        c1 = new PdfPCell(new Phrase(stokList.get(i).getStokAkhir()));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(c1);
                    }
                }else if(reportType==1){
                    if(stokList.get(i).getMasuk()==null){
                        DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = inputFormatter.parse(stokList.get(i).getDateStamp());

                        DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
                        String output = outputFormatter.format(date);

                        c1 = new PdfPCell(new Phrase(output));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(c1);

                        c1 = new PdfPCell(new Phrase(stokList.get(i).getIdCustomer()));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(c1);

                        c1 = new PdfPCell(new Phrase(stokList.get(i).getKeluar()));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(c1);

                        c1 = new PdfPCell(new Phrase(stokList.get(i).getStokAwal()));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(c1);

                        c1 = new PdfPCell(new Phrase(stokList.get(i).getStokAkhir()));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(c1);
                    }
                }
            }

            /*for(int c=0;c<150;c++){
                table.addCell("Test");
                table.addCell("Test");
                table.addCell("Test");
                table.addCell("Test");
                table.addCell("Test");
            }*/

            /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), R.drawable.ic_launcher);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , stream);
            Image myImg = Image.getInstance(stream.toByteArray());
            myImg.setAlignment(Image.MIDDLE);

            //add image to document
            document.add(myImg);*/
            p2.add(new PdfPTable(table));
            document.add(p2);
            //document.add(table);


        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally
        {
            document.close();
        }

        watermarkPdf(oldPath,newPath);
        if(file != null){
            detailView.openPDF(file);
        }

    }

    void watermarkPdf(String path, String newPath)  {
        PdfReader reader = null;
        try {
            reader = new PdfReader(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int n = reader.getNumberOfPages();
        PdfStamper stamper = null;
        try {
            stamper = new PdfStamper(reader, new FileOutputStream(newPath+"_watermark.pdf"));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // text watermark
        Font f = new Font(Font.FontFamily.HELVETICA, 30);
        Phrase p = new Phrase("Berkah Jaya Pamungkas", f);
        // image watermark
        /*Image img = Image.getInstance(IMG);
        float w = img.getScaledWidth();
        float h = img.getScaledHeight();*/
        // transparency
        PdfGState gs1 = new PdfGState();
        gs1.setFillOpacity(0.5f);
        // properties
        PdfContentByte over;
        Rectangle pagesize;
        float x, y;
        // loop over every page
        for (int i = 1; i <= n; i++) {
            pagesize = reader.getPageSizeWithRotation(i);
            x = (pagesize.getLeft() + pagesize.getRight()) / 2;
            y = (pagesize.getTop() + pagesize.getBottom()) / 2;
            over = stamper.getOverContent(i);
            over.saveState();
            over.setGState(gs1);
            ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, x, y, 0);
            //over.addImage(img, w, 0, 0, h, x - (w / 2), y - (h / 2));
            over.restoreState();
        }
        try {
            stamper.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        reader.close();
        /*File file = new File(newPath);
        if(file != null){
            detailView.openPDF(file);
        }*/
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    void getShelf(String token){
        final List<String> stringList = new ArrayList<>();
        Retrofit retrofit = ApiClient.getClient(detailView.getContext());
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Shelf> shelfCall = apiService.getShelf(token);
        shelfCall.enqueue(new Callback<Shelf>() {
            @Override
            public void onResponse(Call<Shelf> call, Response<Shelf> response) {
                if(response.body()!=null){
                    Shelf shelf = response.body();

                    if(shelf.getStatus()){
                        shelfDatumList = shelf.getData();
                        for(int i=0;i<shelfDatumList.size();i++){
                            stringList.add(shelfDatumList.get(i).getName());
                        }
                        detailView.getShelfList(shelfDatumList,stringList);
                    }else{
                        /*Toast.makeText(detailView.getContext(),"Data masih kosong",
                                Toast.LENGTH_SHORT).show();*/
                    }

                }else{
                    Toast.makeText(detailView.getContext(), "Cek koneksi internet anda",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Shelf> call, Throwable t) {
                detailView.onError();
            }
        });
    }

    void getSupplier(String token){
        final List<String> supplierList = new ArrayList<>();
        Retrofit retrofit = ApiClient.getClient(detailView.getContext());
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Supplier> supplierCall = apiService.getSupplier(token);
        supplierCall.enqueue(new Callback<Supplier>() {
            @Override
            public void onResponse(Call<Supplier> call, Response<Supplier> response) {
                if(response.body()!=null){
                    Supplier supplier = response.body();
                    if(supplier.getStatus()){
                        supplierDatumList = supplier.getData();
                        for(int i=0;i<supplierDatumList.size();i++){
                            supplierList.add(supplierDatumList.get(i).getNama());
                        }
                        detailView.getSupplierList(supplierDatumList,supplierList);
                    }else{
                        //Toast.makeText(detailView.getContext(), "Data masih kosong", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(detailView.getContext(), "Cek koneksi internet anda",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Supplier> call, Throwable t) {
                detailView.onError();
            }
        });
    }

    void getCustomer(final int status, String token){
        final List<String> customerList = new ArrayList<>();
        Retrofit retrofit = ApiClient.getClient(detailView.getContext());
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Customer> customerCall = apiService.getCustomer(token);
        customerCall.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if(response.body()!=null){
                    Customer customer = response.body();

                    if(customer.getStatus()){
                        customerDatumList = customer.getData();
                        for(int i=0;i<customerDatumList.size();i++){
                            customerList.add(customerDatumList.get(i).getId()+" - "+
                                    customerDatumList.get(i).getNama());
                        }
                        if(status == 1){
                            detailView.getCustomerList(customerDatumList,customerList);
                        }else{
                            detailView.getCustomer(customerDatumList);

                        }
                    }else {
                        Toast.makeText(detailView.getContext(), "Data masih kosong",
                                Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(detailView.getContext(), "Cek koneksi internet anda",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                detailView.onError();
            }
        });
    }

    String getDetailSupplier(String id,String token){
        Retrofit retrofit = ApiClient.getClient(detailView.getContext());
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Supplier> supplierCall = apiService.getDetailSupplier(id,token);
        supplierCall.enqueue(new Callback<Supplier>() {
            @Override
            public void onResponse(Call<Supplier> call, Response<Supplier> response) {

                if (response.body()!=null){
                    Supplier supplier = response.body();
                    if(supplier.getStatus()){
                        List<SupplierDatum> supplierDatum = supplier.getData();
                        Log.d("Supplier name ",""+supplierDatum.get(0).getNama());
                        nameSupplier = supplierDatum.get(0).getNama();
                    }else{
                    }

                }else{

                }
            }

            @Override
            public void onFailure(Call<Supplier> call, Throwable t) {
                detailView.onError();
            }
        });
        return nameSupplier;
    }

    String getDetailCustomer(String id,String token){
        Retrofit retrofit = ApiClient.getClient(detailView.getContext());
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Customer> customerCall = apiService.getCustomerDetail(id,token);
        customerCall.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if(response.body()!=null){
                    Customer customer = response.body();
                    if(customer.getStatus()){
                        List<CustomerDatum> customerData = customer.getData();
                        customerName = customerData.get(0).getNama();
                    }else{
                        detailView.onEmpty();
                    }
                }else{
                    detailView.noConnection();
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {

            }
        });
        return customerName;
    }

    public interface DetailPresenterInterface{
        void getStokList(List<Stok> stokList);
        void getCustomerDetail(String name);
    };

}
