package aksr.downloadfilewithasynctask;

import android.app.Application;
import android.support.annotation.NonNull;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import aksr.downloadfilewithasynctask.json.others.OkHttp3Stack;

public class App extends Application {
    private RequestQueue mRequestQueue;
    private static App sInstance;
    public static App getInstance() {
        return sInstance;
    }

    private static void addRequest(@NonNull final Request<?> request) {
        getInstance().getVolleyRequestQueue().add(request);
    }

    public static void addRequest(@NonNull final Request<?> request, @NonNull final String tag) {
        request.setTag(tag);
        addRequest(request);
    }

    public static void cancelAllRequests(@NonNull final String tag) {
        getInstance().getVolleyRequestQueue().cancelAll(tag);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;



    }
    @NonNull
    public RequestQueue getVolleyRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this, new OkHttp3Stack());
        }
        return mRequestQueue;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


}
