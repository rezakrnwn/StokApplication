package com.gees.geesapplication.add;

import com.gees.geesapplication.base.BaseView;
import com.gees.geesapplication.model.satuan.SatuanDatum;

import java.util.List;

/**
 * Created by SERVER on 12/09/2017.
 */

public interface AddBarangView extends BaseView {
    void onSuccess();
    void onFailed();
    void getArrayList(List<SatuanDatum> satuanDatumList, List<String> stringList);
    void noConnection();
}
