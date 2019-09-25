package com.sumanmyon.restapicall.Volley;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sumanmyon.restapicall.OfflineStorage.DataStore;
import com.sumanmyon.restapicall.R;
import com.sumanmyon.restapicall.Utils.NetworkConnection;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ApiCall2 {
    private final String TAG = "ApiService";

    private boolean showDialog = true;
    private ProgressDialog progressDialog;


    private static ApiCall2 call = new ApiCall2();

    public static ApiCall2 getInstance() {
        return call;
    }

    public void connect(final Context context, final Type modelClass, final String url, final int method, String parameters, HashMap<String, String> params, Map<String, String> headers, JSONObject body, final ResultListener listener, String loadMessage, final boolean setTrueForOffline) {
        final String offlineStorageFile = URLEncoder.encode(url);

        try {
            if (new NetworkConnection(context).isConnectionSuccess()) {
                if (!loadMessage.equals("")) {
                    if (showDialog)
                        loadMessage = (loadMessage != null) ? loadMessage : context.getString(R.string.Loading);
                    showDialog(context, loadMessage);
                }

                NetworkRequset requset = new NetworkRequset(url, method, parameters, params, headers, body, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse() called");

                        if (setTrueForOffline)
                            DataStore.store(context, offlineStorageFile + method, new Gson().fromJson(response.toString(), modelClass));
                        listener.onResult(url, true, modelClass,  new Gson().fromJson(response.toString(), modelClass), null, progressDialog);
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse() called");
                        Exception exception = error;

                        if (setTrueForOffline) {
                            progressDialog.dismiss();
                            listener.onNetworkFailed(url, forOffline(context, offlineStorageFile, method, modelClass), modelClass, setTrueForOffline);
                        }else
                            listener.onResult(url, false, modelClass, null, exception, progressDialog);
                    }
                });

                requset.setRetryPolicy(new DefaultRetryPolicy(50000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                Volley.newRequestQueue(context).add(requset);

            } else {
                if (setTrueForOffline)
                    listener.onNetworkFailed(url, forOffline(context, url, method, modelClass), modelClass, setTrueForOffline);
                else
                    listener.onNetworkFailed(url, context.getString(R.string.Network_error), modelClass, setTrueForOffline);
            }

        } catch (Exception exception) {
            Log.e(TAG, exception.getMessage());

            if (setTrueForOffline){
                listener.onNetworkFailed(url, forOffline(context, offlineStorageFile, method, modelClass), modelClass, setTrueForOffline);
            } else
                listener.onResult(url, false, modelClass, null, exception, progressDialog);

        }
    }

    private Object forOffline(Context context, String url, int method, Type type){
        Object object = DataStore.get(context, url + method, type);
        if(object == null){
            return null;
        }
        if(object.toString() == "null"){
            object = null;
        }
        return object;
    }

    private void showDialog(Context context, String loadMessage) {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(loadMessage);
            progressDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public interface ResultListener {
        void onResult(String url, boolean isSuccess, Type aClass, Object jsonObject, Exception volleyError, ProgressDialog progressDialog);

        void onNetworkFailed(String url, Object message, Type aClass, boolean getTrueForOffline);
    }

    public interface Method {
        int DEPRECATED_GET_OR_POST = -1;
        int GET = 0;
        int POST = 1;
        int PUT = 2;
        int DELETE = 3;
        int HEAD = 4;
        int OPTIONS = 5;
        int TRACE = 6;
        int PATCH = 7;
    }
}
