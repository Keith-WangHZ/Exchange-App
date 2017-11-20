package com.example.huazi.campusexchange.utils;

import android.util.Log;

import com.example.huazi.campusexchange.model.bean.CityModel;
import com.example.huazi.campusexchange.model.bean.DistrictModel;
import com.example.huazi.campusexchange.model.bean.ProvinceModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huazi on 2017/11/7.
 * Email:wanghuazhi_beijing@163.com
 */

public class NetUtils {

    private static String result;

    public static String httpUrlConnGet(String name,String password){
        Log.d("huazi", "httpUrlConnGet");
        HttpURLConnection urlConnection = null;
        URL url = null;
        try {
            String urlStr = "http://172.168.88.33:8080/getName?name="+name;
            url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            Log.d("huazi111", "urlConnection.getResponseCode()= " + urlConnection.getResponseCode());
            if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                InputStream in = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line = null;
                StringBuffer buffer = new StringBuffer();
                while((line=br.readLine())!=null){
                    buffer.append(line);
                }
                in.close();
                br.close();
                result = buffer.toString();
                Log.d("huazi111", "result = " + result);
//                mHandler.sendEmptyMessage(USERLOGIN_SUCCESS);
                /*if(result.equals("ok")){
                    mHandler.sendEmptyMessage(USERLOGIN_SUCCESS);
                }else{
//                    mHandler.sendEmptyMessage(USERLOGIN_FAILED);
                }*/
                return result;
            }else{
//                mHandler.sendEmptyMessage(CONN_FIALED);
            }
        } catch (Exception e) {
            Log.d("huazi111", "exception"+e.getMessage()+e.toString());
//            mHandler.sendEmptyMessage(CONN_FIALED);
        }finally{
            urlConnection.disconnect();
            return result;
        }
    }
    public static List<ProvinceModel> httpUrlConnGetCity(int name){
        Log.d("huazi", "httpUrlConnGet");
        HttpURLConnection urlConnection = null;
        URL url = null;
        List<ProvinceModel> provinceModel = new ArrayList<ProvinceModel>();
        try {
            String urlStr = "http://172.168.88.33:8080/getCityName?level="+name;
//            String urlStr = "http://192.168.1.2:8080/getCityName?level="+name;
            url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            Log.d("huazi111", "urlConnection.getResponseCode()= " + urlConnection.getResponseCode());
            if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                InputStream in = urlConnection.getInputStream();
                /*BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line = null;
                StringBuffer buffer = new StringBuffer();
                while((line=br.readLine())!=null){
                    buffer.append(line);
                }
                in.close();
                br.close();*/
                result = inputStreamToString(in);
                int status = urlConnection.getResponseCode();
                Log.d("huazi111", "result = " + result + ", status = " + status);

                switch (status){
                    case 200:

                        provinceModel = parseResult(result);
                        break;
                    default:
                        break;

                }
//                mHandler.sendEmptyMessage(USERLOGIN_SUCCESS);
                /*if(result.equals("ok")){
                    mHandler.sendEmptyMessage(USERLOGIN_SUCCESS);
                }else{
//                    mHandler.sendEmptyMessage(USERLOGIN_FAILED);
                }*/
//                return result;
            }else{

//                mHandler.sendEmptyMessage(CONN_FIALED);
            }
        } catch (Exception e) {
            Log.d("huazi111", "exception"+e.getMessage()+e.toString());
//            mHandler.sendEmptyMessage(CONN_FIALED);
        }finally{
            urlConnection.disconnect();
            Log.d("huazi111", "provinceModel = " + provinceModel);
            return provinceModel;
        }
    }

    public static List<CityModel> httpUrlConnGetCitys(int name){
        Log.d("huazi", "httpUrlConnGet");
        HttpURLConnection urlConnection = null;
        URL url = null;
        List<CityModel> cityModel = new ArrayList<CityModel>();
        try {
            String urlStr = "http://172.168.88.33:8080/getCitysName?parentID="+name;
            url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            Log.d("huazi111", "urlConnection.getResponseCode()= " + urlConnection.getResponseCode());
            if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                InputStream in = urlConnection.getInputStream();
                result = inputStreamToString(in);
                int status = urlConnection.getResponseCode();
                Log.d("huazi111", "result = " + result + ", status = " + status);

                switch (status){
                    case 200:
                        cityModel = parseResult2(result);
                        break;
                    default:
                        break;

                }
            }else{

            }
        } catch (Exception e) {
            Log.d("huazi111", "exception"+e.getMessage()+e.toString());
        }finally{
            urlConnection.disconnect();
            return cityModel;
        }
    }

    public static List<DistrictModel> httpUrlConnGetAreas(int name){
        Log.d("huazi", "httpUrlConnGet");
        HttpURLConnection urlConnection = null;
        URL url = null;
        List<DistrictModel> areaModel = new ArrayList<DistrictModel>();

        try {
            String urlStr = "http://172.168.88.33:8080/getCitysName?parentID="+name;
            url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            Log.d("huazi111", "urlConnection.getResponseCode()= " + urlConnection.getResponseCode());
            if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                InputStream in = urlConnection.getInputStream();
                result = inputStreamToString(in);
                int status = urlConnection.getResponseCode();
                Log.d("huazi111", "result = " + result + ", status = " + status);

                switch (status){
                    case 200:
                        areaModel = parseResult3(result);
                        break;
                    default:
                        break;

                }
            }else{

            }
        } catch (Exception e) {
            Log.d("huazi111", "exception"+e.getMessage()+e.toString());
        }finally{
            urlConnection.disconnect();
            return areaModel;
        }
    }

    private static List<ProvinceModel> parseResult(String result) {
        List<ProvinceModel> provinceDatas = new ArrayList<ProvinceModel>();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for(int i = 0;i<jsonArray.length();i++){
                ProvinceModel provinceData = new ProvinceModel();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Log.d("huazi1112","json = "+ jsonObject.getString("name"));
                provinceData.setCityID(jsonObject.getInt("cityID"));
                provinceData.setParentId(jsonObject.getInt("parentId"));
                provinceData.setLevel(jsonObject.getInt("level"));
                provinceData.setName(jsonObject.getString("name"));
                provinceData.setPinyin(jsonObject.getString("pinyin"));
                provinceDatas.add(provinceData);
            }

        }catch (Exception e){
            Log.d("huazi1112","json = error , " + e.toString());

        }
        return provinceDatas;
    }

    private static List<CityModel> parseResult2(String result) {
        List<CityModel> cityDatas = new ArrayList<CityModel>();
        try{
            JSONArray jsonArray = new JSONArray(result);
            for(int i = 0;i<jsonArray.length();i++){
                CityModel cityData = new CityModel();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                cityData.setCityID(jsonObject.getInt("cityID"));
                cityData.setParentId(jsonObject.getInt("parentId"));
                cityData.setLevel(jsonObject.getInt("level"));
                cityData.setName(jsonObject.getString("name"));
                cityData.setPinyin(jsonObject.getString("pinyin"));
                cityDatas.add(cityData);
            }
        }catch (Exception e){

        }
        return cityDatas;
    }
    private static List<DistrictModel> parseResult3(String result) {
        List<DistrictModel> areaDatas = new ArrayList<DistrictModel>();
        try{
            JSONArray jsonArray = new JSONArray(result);
            for(int i = 0;i<jsonArray.length();i++){
                DistrictModel areaData = new DistrictModel();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                areaData.setCityID(jsonObject.getInt("cityID"));
                areaData.setParentId(jsonObject.getInt("parentId"));
                areaData.setLevel(jsonObject.getInt("level"));
                areaData.setName(jsonObject.getString("name"));
                areaData.setPinyin(jsonObject.getString("pinyin"));
                areaDatas.add(areaData);
            }
        }catch (Exception e){

        }
        return areaDatas;
    }

    private static String inputStreamToString(InputStream in) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer))!=-1){
            os.write(buffer,0,len);
        }
        in.close();
        String state = os.toString();
        os.close();
        return state;
    }


}
