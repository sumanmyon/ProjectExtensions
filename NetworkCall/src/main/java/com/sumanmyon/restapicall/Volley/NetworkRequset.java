package com.sumanmyon.restapicall.Volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.sumanmyon.restapicall.Utils.ApiUrl;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class NetworkRequset extends Request<JSONObject> {
    private final String TAG = "NetworkRequest";

    private Response.Listener<JSONObject> listener;
    private Map<String, String> params;
    private Map<String, String> headers;
    private JSONObject body;
    private String url = "";

    public NetworkRequset(String url, int method, String parameters, Map<String, String> params, Map<String, String> headers, JSONObject body, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, ApiUrl.BASE_URL + url + parameters, errorListener);

        try {
            this.url = url;
            this.params = params;
            this.headers = headers;
            this.body = body;
            this.listener = listener;

            displayParams(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (headers == null) return new HashMap<>();
        else if (headers != null) {
            return headers;
        }
        return super.getHeaders();
    }

    @Override
    public Map<String, String> getParams() {
        if (params == null) return new HashMap<>();
        return params;
    }

    //    pass Json Body:
//    {
//        "username": "Shozib@gmail.com",
//            "password": "Shozib123"
//    }
//    JSONObject jsonBody = new JSONObject();
//    jsonBody.put("username", "Shozib@gmail.com");
//    jsonBody.put("password", "Shozib123");
//    final String mRequestBody = jsonBody.toString();

    @Override
    public String getBodyContentType() {
        return "application/json; charset=utf-8";
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        String mRequestBody = null;
        try {
            if (body != null) {
                mRequestBody = body.toString();
            } else if (object != null) {
                mRequestBody = object.toString();
            }
            return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");

        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
            return null;
        }
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data);
            Log.e(TAG, "jsonString==" + jsonString);
            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));

        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        try {
            Log.e(TAG, "jsonObject==" + response.toString(4));
            listener.onResponse(response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    JSONObject object;

    private void displayParams(String url) {
        try {
            object = new JSONObject();
            Log.e(TAG, "URL==" + url);

            if (params == null) return;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                Log.e(TAG, "key==" + key + ", value==" + value);

                object.put(key, value);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
