package com.gees.geesapplication.auth;

import com.gees.geesapplication.base.BaseView;

/**
 * Created by SERVER on 11/09/2017.
 */

public interface LoginView extends BaseView {
    void onSuccess();
    void onProcess();
    void onFailed();
}
