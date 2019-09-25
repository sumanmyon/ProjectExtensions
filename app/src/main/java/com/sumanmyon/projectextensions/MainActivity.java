package com.sumanmyon.projectextensions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.sumanmyon.projectextensions.Model.FacultyClassDetailModel;
import com.sumanmyon.projectextensions.Model.HamroKishan;
import com.sumanmyon.projectextensions.Services.CallApi;
import com.sumanmyon.restapicall.Utils.ApiUrl;

import java.lang.reflect.Type;


public class MainActivity extends AppCompatActivity implements CallApi.CallApiResultListener {
    private FacultyClassDetailModel facultyClassDetailModels = new FacultyClassDetailModel();
    private HamroKishan.Search hamroKishan = new HamroKishan.Search();
    private HamroKishan.SocialMedia socialMedia = new HamroKishan.SocialMedia();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //softSchool();
        hamrokishan();
        //hamrokishansocialinfo();
    }

    private void softSchool(){
        ApiUrl.BASE_URL = "http://192.168.100.102:400/api/data/";

        CallApi.getInstance().getFacultyClassDetail(this, FacultyClassDetailModel.class, this);
        ApiUrl.pendingRequestsCount++;
    }

    private void hamrokishan(){
        //hamrakisan.com/mobile/api/searchfarmer?category=vegetable_farming&key=budha&order_by=name
        ApiUrl.BASE_URL = "http://hamrakisan.com/mobile/api/";

        CallApi.getInstance().getHamroKishansData(this, HamroKishan.Search.class, this);
        ApiUrl.pendingRequestsCount++;
    }

    private void hamrokishansocialinfo(){
        //hamrakisan.com/mobile/api/searchfarmer?category=vegetable_farming&key=budha&order_by=name
        ApiUrl.BASE_URL = "http://hamrakisan.com/mobile/api/";

        CallApi.getInstance().getHamroKishanssocialinfoData(this, HamroKishan.SocialMedia.class, this);
        ApiUrl.pendingRequestsCount++;
    }

    @Override
    public void onResult(String url, Object data, Context context, Type aClass) {
        if(data != null) {
            if(aClass == FacultyClassDetailModel.class) {
                facultyClassDetailModels = (FacultyClassDetailModel) data;
                showToast(facultyClassDetailModels.Status);
            }else if(aClass == HamroKishan.Search.class){
                hamroKishan = (HamroKishan.Search) data;
                showToast(hamroKishan.data.get(0).name);
            }else if(aClass == HamroKishan.SocialMedia.class){
                socialMedia = (HamroKishan.SocialMedia) data;
                showToast(socialMedia.data.facebook);
            }
        }
    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
