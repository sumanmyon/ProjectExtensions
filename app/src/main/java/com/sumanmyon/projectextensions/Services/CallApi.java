package com.sumanmyon.projectextensions.Services;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.sumanmyon.projectextensions.utils.Headers;
import com.sumanmyon.projectextensions.utils.Urls;
import com.sumanmyon.restapicall.Volley.ApiCall2;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;

import static com.sumanmyon.restapicall.Utils.ApiUrl.pendingRequestsCount;

public class CallApi implements ApiCall2.ResultListener {
    private CallApiResultListener listener;
    private Context context;
    private boolean setTrueForOffline = true;
    private String loadingMessage = "loading ...";

    private static CallApi call = new CallApi();

    public static CallApi getInstance() {
        return call;
    }

    @Override
    public void onResult(String url, boolean isSuccess, Type aClass, Object response, Exception volleyError, ProgressDialog progressDialog) {
        pendingRequestsCount--;
        try {
            if (isSuccess) {
                listener.onResult(url, response, context, aClass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (progressDialog != null && progressDialog.isShowing() && pendingRequestsCount == 0)
            progressDialog.dismiss();
    }

    @Override
    public void onNetworkFailed(String url, Object message, Type aClass, boolean getTrueForOffline) {
        if (getTrueForOffline) listener.onResult(url, message, context, aClass);
        else Toast.makeText(context, message.toString(), Toast.LENGTH_LONG).show();
    }

    public interface CallApiResultListener {
        void onResult(String url, Object data, Context context, Type aClass);
    }


    public void getFacultyClassDetail(Context context, Type modelClass, CallApiResultListener listener) {
        this.context = context;
        this.listener = listener;
        String url = Urls.getFacultyClassDetail;

        ApiCall2.getInstance().connect(context, modelClass, url, ApiCall2.Method.POST, "", null, null, null, this, loadingMessage, setTrueForOffline);
    }

    public void getHamroKishansData(Context context, Type modelClass, CallApiResultListener listener){
        this.context = context;
        this.listener = listener;
        String url = Urls.searchfarmer;
        String parameters = "?category=vegetable_farming&key=budha&order_by=name";

        ApiCall2.getInstance().connect(context, modelClass, url, ApiCall2.Method.GET, parameters, null, null, null, this, loadingMessage, setTrueForOffline);

    }

    public void getHamroKishanssocialinfoData(Context context, Type modelClass, CallApiResultListener listener){
        this.context = context;
        this.listener = listener;
        String url = Urls.socialinfo;

        HashMap<String, String> params = new HashMap<>();
        params.put("facebook", "abc");
        params.put("twitter", "sads");
        params.put("instagram", "");
        params.put("youtube", "");

        //for params
        //ApiCall2.getInstance().connect(context, modelClass, url, ApiCall2.Method.POST, "", params, new Headers().getHeaders(), object, this, loadingMessage, setTrueForOffline);


        try {
            JSONObject object = new JSONObject();
            object.put("facebook", "abc");
            object.put("twitter", "sads");
            object.put("instagram", "");
            object.put("youtube", "");

            //for json body
            ApiCall2.getInstance().connect(context, modelClass, url, ApiCall2.Method.POST, "", null, new Headers().getHeaders(), object, this, loadingMessage, setTrueForOffline);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
