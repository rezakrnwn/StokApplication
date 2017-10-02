package com.gees.geesapplication.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.gees.geesapplication.base.BaseView;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

/**
 * Created by SERVER on 11/09/2017.
 */

public class ApiClient implements BaseView {

    public static final String BASE_URL = "http://berkahjayapamungkas.com/";
    private static OkHttpClient httpClient = null;
    private static Retrofit retrofit = null;
    private static Context mContext;
    private static String cacheControl;

    public static Retrofit getClient(Context context) {
        File httpCacheDirectory = new File(context.getCacheDir(),  "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        mContext = context;

        if (retrofit == null) {
            /*httpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR)
                    .addInterceptor(OFFLINE_INTERCEPTOR)
                    .addInterceptor(new LoggingInterceptor())
                    .cache(cache)
                    .build();*/

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    //.client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static final Interceptor REWRITE_RESPONSE_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            cacheControl = originalResponse.header("Cache-Control");

            if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                    cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + 10)
                        .build();
            } else {
                return originalResponse;
            }

        }
    };

    private static final Interceptor OFFLINE_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            if (!isNetworkAvailable()) {
                Log.d(TAG, "rewriting request");

                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }

            return chain.proceed(request);

        }
    };


    public static boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo  = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public Context getContext() {
        return null;
    }
}
