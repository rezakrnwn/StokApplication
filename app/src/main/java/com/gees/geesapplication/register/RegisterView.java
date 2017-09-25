package com.gees.geesapplication.register;

import com.gees.geesapplication.base.BaseView;

/**
 * Created by SERVER on 12/09/2017.
 */

public interface RegisterView extends BaseView {

    void onSuccess();
    void onFailed();
    void noConnection();
}
