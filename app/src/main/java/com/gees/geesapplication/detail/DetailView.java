package com.gees.geesapplication.detail;

import com.gees.geesapplication.base.BaseView;
import com.gees.geesapplication.model.customer.CustomerDatum;
import com.gees.geesapplication.model.shelf.ShelfDatum;
import com.gees.geesapplication.model.supplier.SupplierDatum;

import java.io.File;
import java.util.List;

/**
 * Created by SERVER on 12/09/2017.
 */

public interface DetailView extends BaseView {
    void onSuccessAdd();
    void onSuccessRemove();
    void onProcessing();
    void processFinish();
    void onError();
    void onEmpty();
    void noConnection();
    void openPDF(File file);
    void getShelfList(List<ShelfDatum> shelfDatumList,List<String> stringList);
    void getSupplierList(List<SupplierDatum> supplierDatumList,List<String> supplierList);
    void getCustomerList(List<CustomerDatum> customerDatumList,List<String> customerList);
    void getCustomer(List<CustomerDatum> customerDatumList);
    void updateStok(String stok);
}
