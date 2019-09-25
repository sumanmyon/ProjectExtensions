package com.sumanmyon.restapicall.Volley;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.sumanmyon.restapicall.R;
import com.sumanmyon.restapicall.Utils.NetworkConnection;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class ApiCall {
    private final String TAG = "ApiService";

    private boolean showDialog = true;
    private ProgressDialog progressDialog;

    private static ApiCall call = new ApiCall();

    public static ApiCall getInstance() {
        return call;
    }

    public void connect(Context context, final Class<?> aClass, final String url, int method, String parameters, HashMap<String, String> params, Map<String, String> headers,JSONObject body, final ResultListener listener, String loadMessage) {
        try {
            if (new NetworkConnection(context).isConnectionSuccess()) {
                if(!loadMessage.equals("")) {
                    if (showDialog)
                        loadMessage = (loadMessage != null) ? loadMessage : context.getString(R.string.Loading);
                    showDialog(context, loadMessage);
                }

                NetworkRequset requset = new NetworkRequset(url, method, parameters, params, headers, body, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse() called");
                        listener.onResult(url, true, aClass, response, null, progressDialog);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse() called");
                        Exception exception = error;
                        listener.onResult(url, false, aClass,null,exception, progressDialog);
                    }
                });
                requset.setRetryPolicy(new DefaultRetryPolicy(50000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                Volley.newRequestQueue(context).add(requset);
            } else {
                listener.onNetworkFailed(url, context.getString(R.string.Network_error), aClass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        void onResult(String url, boolean isSuccess, Class<?> aClass, JSONObject jsonObject, Exception volleyError, ProgressDialog progressDialog);

        void onNetworkFailed(String url, String message, Class<?> aClass);
    }

    public interface Method{
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
