package com.example.huazi.campusexchange.ui.activitys.entrance;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huazi.campusexchange.R;
import com.example.huazi.campusexchange.adapter.AreaAdapter;
import com.example.huazi.campusexchange.adapter.CitysAdapter;
import com.example.huazi.campusexchange.adapter.ProvinceAdapter;
import com.example.huazi.campusexchange.callbacks.OnWheelChangedListener;
import com.example.huazi.campusexchange.model.bean.CityModel;
import com.example.huazi.campusexchange.model.bean.DistrictModel;
import com.example.huazi.campusexchange.model.bean.ProvinceModel;
import com.example.huazi.campusexchange.ui.base.BaseActivity;
import com.example.huazi.campusexchange.ui.view.WheelView;
import com.example.huazi.campusexchange.utils.CityDataHelper;
import com.example.huazi.campusexchange.utils.NetUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huazi on 2017/11/9.
 * Email:wanghuazhi_beijing@163.com
 */

public class ChooseSchool extends BaseActivity implements View.OnClickListener, OnWheelChangedListener {

    //popupwindow
    private PopupWindow mPopupWindow;
    private WheelView provinceView;
    private WheelView cityView;
    private WheelView districtView;
    private List<ProvinceModel> provinceDatas = new ArrayList<>();
    private List<CityModel> cityDatas = new ArrayList<CityModel>();
    private List<DistrictModel> districtDatas = new ArrayList<>();
    private String mCurrentProvince;
    private String mCurrentCity;
    private String mCurrentDistrict;
    private TextView btn_myinfo_sure, btn_myinfo_cancel;
    private ProvinceAdapter provinceAdapter;
    private CitysAdapter citysAdapter;
    private AreaAdapter areaAdapter;
    private SQLiteDatabase db;
    private CityDataHelper dataHelper;
    private final int TEXTSIZE = 17;//选择器的字体大小
    private Button bt;
    private View view;
    private int initCity;
    private int initArea;
    private int cityCount;
    private int areaCount;
    private int areaCountUpdate;
    private TextView schoolName;
    private ImageView schoolLogo;
    private Button sureBtn;


    @Override
    protected void onCreate() {
        setContentView(R.layout.choose_school);
        view = LayoutInflater.from(this).inflate(R.layout.choose_school, null);
        schoolName = (TextView) this.findViewById(R.id.school_name);
        schoolLogo = (ImageView) this.findViewById(R.id.school_logol);
        sureBtn = (Button)this.findViewById(R.id.sure_btn);
        sureBtn.setOnClickListener(this);
        bt = (Button) this.findViewById(R.id.bt);
        bt.setOnClickListener(this);
    }


    public void initPopupWindow() {

        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_locationchoose, null);
        mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(R.style.popup_locationchoose_bottom);

        //  pickText = (TextView)popupView.findViewById(R.id.tv_pickText);
        provinceView = (WheelView) popupView.findViewById(R.id.provinceView);
        cityView = (WheelView) popupView.findViewById(R.id.cityView);
        districtView = (WheelView) popupView.findViewById(R.id.districtView);

        //确定或者取消
        btn_myinfo_sure = (TextView) popupView.findViewById(R.id.btn_myinfo_sure);
        btn_myinfo_cancel = (TextView) popupView.findViewById(R.id.btn_myinfo_cancel);
        btn_myinfo_cancel.setOnClickListener(this);
        btn_myinfo_sure.setOnClickListener(this);

        // 设置可见条目数量
        provinceView.setVisibleItems(7);
        cityView.setVisibleItems(7);
        districtView.setVisibleItems(7);

        // 添加change事件
        provinceView.addChangingListener(this);
        // 添加change事件
        cityView.addChangingListener(this);
        // 添加change事件
        districtView.addChangingListener(this);

        initpopData();

    }

    private void initpopData() {
        //初始化数据
//            dataHelper = CityDataHelper.getInstance(this);
//            db = dataHelper.openDataBase();
//            provinceDatas = dataHelper.getProvice(db);
        final Handler areaHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 200:
                        Toast.makeText(ChooseSchool.this, "success", Toast.LENGTH_LONG).show();
                        DistrictModel districtModel;
                        districtDatas.clear();
                        for (int i = 0; i < areaCount; i++) {
                            districtModel = (DistrictModel) msg.getData().getSerializable("areaModel" + i);
                            Log.d("huazi1112", "areaModel = " + districtModel);
                            districtDatas.add(districtModel);
                        }
                        Log.d("huazi111", "area size = " + districtDatas.size());

                        //wheelview的适配器代码
                        provinceAdapter = new ProvinceAdapter(ChooseSchool.this, provinceDatas);
                        provinceAdapter.setTextSize(TEXTSIZE);//设置字体大小
                        provinceView.setViewAdapter(provinceAdapter);

                        updateCitys();
                        updateAreas();
                        break;
                    case 400:
                        Toast.makeText(ChooseSchool.this, "fail", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };
        final Thread areaThread = new Thread() {
            @Override
            public void run() {
                Log.d("huazi111", "initArea = " + initArea);
                List<DistrictModel> result = NetUtils.httpUrlConnGetAreas(initArea);
                if (null != result && !result.equals("0")) {
                    Message message = new Message();
                    message.what = 200;
                    Bundle bundle = new Bundle();
                    areaCount = 0;
//                        DistrictModel area = new DistrictModel(result.getCityID(),result.getParentId(),result.getLevel(),result.getName(),result.getPinyin());
                    for (int i = 0; i < result.size(); i++) {
                        areaCount++;
                        bundle.putSerializable("areaModel" + i, result.get(i));
                    }
                    bundle.putSerializable("areaModel", result.get(0));
                    message.setData(bundle);
                    areaHandler.sendMessage(message);
                } else {
                    areaHandler.sendEmptyMessage(400);
                }
            }
        };

        final Handler cityHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 200:
                        Toast.makeText(ChooseSchool.this, "success", Toast.LENGTH_LONG).show();
                        CityModel cityModel;
                        cityDatas.clear();
                        for (int i = 0; i < cityCount; i++) {
                            cityModel = (CityModel) msg.getData().getSerializable("cityModel" + i);
                            Log.d("huazi1112", "cityModel = " + cityModel);
                            cityDatas.add(cityModel);
                        }
                        Log.d("huazi111", "city size = " + cityDatas.size());

                            /*if (provinceDatas.size() > 0) {

                                //弹出popup时，省wheelview中当前的省其实就是省集合的第一个
                                mCurrentProvince = provinceDatas.get(0).getName();

                                //根据省cityid查询到第一个省下面市的集合
                                cityDatas = dataHelper.getCityByParentId(db, provinceDatas.get(0).getCityID()+"");
                            }*/
                        if (cityDatas.size() > 0) {
                            //根据市cityid查询到第一个市集合下面区的集合
                            initArea = cityDatas.get(0).getLevel();
                            areaThread.start();
//                                districtDatas = dataHelper.getDistrictById(db, cityDatas.get(0).getCityID()+"");

                        }
                        break;
                    case 400:
                        Toast.makeText(ChooseSchool.this, "fail", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };
        final Thread cityThread = new Thread() {
            @Override
            public void run() {
                Log.d("huazi111", "initCity = " + initCity);
                List<CityModel> result = NetUtils.httpUrlConnGetCitys(initCity);
                if (null != result && !result.equals("0")) {
                    Message message = new Message();
                    message.what = 200;
                    Bundle bundle = new Bundle();
                    cityCount = 0;
//                        CityModel city = new CityModel(result.getCityID(),result.getParentId(),result.getLevel(),result.getName(),result.getPinyin());
                    for (int i = 0; i < result.size(); i++) {
                        cityCount++;
                        bundle.putSerializable("cityModel" + i, result.get(i));
                    }
                    message.setData(bundle);
                    cityHandler.sendMessage(message);
                } else {
                    cityHandler.sendEmptyMessage(400);
                }
            }
        };

        final Handler provinceHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 200:
                        Toast.makeText(ChooseSchool.this, "success", Toast.LENGTH_LONG).show();
                        provinceDatas.clear();
                        ProvinceModel result = (ProvinceModel) msg.getData().getSerializable("provinceModel");
                        ProvinceModel province = new ProvinceModel(result.getCityID(), result.getParentId(), result.getLevel(), result.getName(), result.getPinyin());
                        provinceDatas.add(province);
                        Log.d("huazi111", "province size = " + provinceDatas.size());

                        if (provinceDatas.size() > 0) {

                            //弹出popup时，省wheelview中当前的省其实就是省集合的第一个
                            mCurrentProvince = provinceDatas.get(0).getName();

                            //根据省cityid查询到第一个省下面市的集合
                            initCity = provinceDatas.get(0).getLevel();
                            Log.d("huazi111", "fuck = " + provinceDatas.get(0).getName());
//                                cityDatas = dataHelper.getCityByParentId(db, provinceDatas.get(0).getCityID()+"");
                            cityThread.start();
                        }

                        break;
                    case 400:
                        Toast.makeText(ChooseSchool.this, "fail", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

        new Thread() {
            @Override
            public void run() {
                List<ProvinceModel> result = NetUtils.httpUrlConnGetCity(1);
                Log.d("huazi1112", "cityId = " + result.get(0).getName());
                if (null != result && !result.equals("0")) {
                    Message message = new Message();
                    message.what = 200;
                    Bundle bundle = new Bundle();
//                        ProvinceModel province = new ProvinceModel(result.getCityID(),result.getParentId(),result.getLevel(),result.getName(),result.getPinyin());
                    bundle.putSerializable("provinceModel", result.get(0));
                    message.setData(bundle);
                    provinceHandler.sendMessage(message);
                } else {
                    provinceHandler.sendEmptyMessage(400);
                }
            }
        }.start();
    }

    private void updateCitys() {
        int pCurrent = provinceView.getCurrentItem();
            /*if (provinceDatas.size() > 0) {
                //这里是必须的的，上面得到的集合只是第一个省下面所有市的集合及第一个市下面所有区的集合
                //这里得到的是相应省下面对应市的集合
                cityDatas = dataHelper.getCityByParentId(db, provinceDatas.get(pCurrent).getCityID()+"");
            } else {
                cityDatas.clear();
            }*/
        if (provinceDatas.size() > 0) {
            citysAdapter = new CitysAdapter(this, cityDatas);
            citysAdapter.setTextSize(TEXTSIZE);
            cityView.setViewAdapter(citysAdapter);
            if (cityDatas.size() > 0) {
                //默认省下面 市wheelview滑动第一个，显示第一个市
                cityView.setCurrentItem(0);
                mCurrentCity = cityDatas.get(0).getName();
            } else {
                mCurrentCity = "";
            }
        } else {
            cityDatas.clear();
        }
           /* citysAdapter = new CitysAdapter(this, cityDatas);
            citysAdapter.setTextSize(TEXTSIZE);
            cityView.setViewAdapter(citysAdapter);
            if (cityDatas.size() > 0) {
                //默认省下面 市wheelview滑动第一个，显示第一个市
                cityView.setCurrentItem(0);
                mCurrentCity = cityDatas.get(0).getName();
            } else {
                mCurrentCity = "";
            }*/
        updateAreas();
    }


    private void updateAreas() {
        final int cCurrent = cityView.getCurrentItem();
           /* if (cityDatas.size() > 0) {
                districtDatas = dataHelper.getDistrictById(db, cityDatas.get(cCurrent).getCityID()+"");
            } else {
                districtDatas.clear();
            }*/
        if (cityDatas.size() > 0) {
            final Handler areaHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case 200:
                            Toast.makeText(ChooseSchool.this, "success", Toast.LENGTH_LONG).show();
                            DistrictModel districtModel;
                            districtDatas.clear();
                            Log.d("huazi1113", "areaCountUpdate = " + areaCount);
                            for (int i = 0; i < areaCountUpdate; i++) {
                                districtModel = (DistrictModel) msg.getData().getSerializable("areaModel" + i);
                                if (districtModel != null) {
                                    Log.d("huazi1113", "areaModel = " + districtModel.getName());
                                    districtDatas.add(districtModel);
                                }

                            }
                            Log.d("huazi1113", "area size = " + districtDatas.size());

                            //wheelview的适配器代码
                               /*provinceAdapter = new ProvinceAdapter(ChooseSchool.this, provinceDatas);
                               provinceAdapter.setTextSize(TEXTSIZE);//设置字体大小
                               provinceView.setViewAdapter(provinceAdapter);

                               updateCitys();
                               updateAreas();*/
                            areaAdapter = new AreaAdapter(ChooseSchool.this, districtDatas);
                            areaAdapter.setTextSize(TEXTSIZE);
                            districtView.setViewAdapter(areaAdapter);
                            Log.d("huazi1114", "districtDatas.size() = " + districtDatas.size() + ", name = " + districtDatas.get(0).getName());
                            if (districtDatas.size() > 0) {
                                mCurrentDistrict = districtDatas.get(0).getName();
                                districtView.setCurrentItem(0);
                            } else {
                                mCurrentDistrict = "";
                            }
                            break;
                        case 400:
                            Toast.makeText(ChooseSchool.this, "fail", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            };
            final Thread areaThread = new Thread() {
                @Override
                public void run() {
                    Log.d("huazi1113", "currentArea = " + cityDatas.get(cCurrent).getLevel());
                    List<DistrictModel> result = NetUtils.httpUrlConnGetAreas(cityDatas.get(cCurrent).getCityID());
                    if (null != result && !result.equals("0")) {
                        Message message = new Message();
                        message.what = 200;
                        Bundle bundle = new Bundle();
//                        DistrictModel area = new DistrictModel(result.getCityID(),result.getParentId(),result.getLevel(),result.getName(),result.getPinyin());
                        for (int i = 0; i < result.size(); i++) {
                            areaCountUpdate++;
                            bundle.putSerializable("areaModel" + i, result.get(i));
                        }
                        bundle.putSerializable("areaModel", result.get(0));
                        message.setData(bundle);
                        areaHandler.sendMessage(message);
                    } else {
                        areaHandler.sendEmptyMessage(400);
                    }
                }
            };
            areaThread.start();
        } else {
            districtDatas.clear();
        }
           /* areaAdapter = new AreaAdapter(this, districtDatas);
            areaAdapter.setTextSize(TEXTSIZE);
            districtView.setViewAdapter(areaAdapter);
            Log.d("huazi1114", "districtDatas.size() = " + districtDatas.size() + ", name = " + districtDatas.get(0).getName());
            if (districtDatas.size() > 0) {
                mCurrentDistrict = districtDatas.get(0).getName();
                districtView.setCurrentItem(0);
            } else {
                mCurrentDistrict = "";
            }*/
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.bt:
                initPopupWindow();
                mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;

            case R.id.btn_myinfo_cancel:

                mPopupWindow.dismiss();

                break;

            case R.id.btn_myinfo_sure:
                String schoolNameStr = "您所选择的学校是：";
                switch (mCurrentDistrict) {
                    case "中国人民大学":
                        schoolLogo.setBackground(getResources().getDrawable(R.drawable.renmin));
                        schoolName.setText(schoolNameStr+mCurrentDistrict);
                        break;
                    case "中国地质大学":
                        schoolLogo.setBackground(getResources().getDrawable(R.drawable.dizhi));
                        schoolName.setText(schoolNameStr+mCurrentDistrict);
                        break;
                    case "清华大学":
                        schoolLogo.setBackground(getResources().getDrawable(R.drawable.qinghua));
                        schoolName.setText(schoolNameStr+mCurrentDistrict);
                        break;
                    case "北京大学":
                        schoolLogo.setBackground(getResources().getDrawable(R.drawable.beijing));
                        schoolName.setText(schoolNameStr+mCurrentDistrict);
                        break;
                    default:
                        break;


                }
                sureBtn.setVisibility(View.VISIBLE);
                mPopupWindow.dismiss();
                break;

            case R.id.sure_btn:
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result", mCurrentDistrict);
                ChooseSchool.this.setResult(1,resultIntent);
                ChooseSchool.this.finish();
                break;

        }

    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {

        if (wheel == provinceView) {
            mCurrentProvince = provinceDatas.get(newValue).getName();
            updateCitys();
        }
        if (wheel == cityView) {
            mCurrentCity = cityDatas.get(newValue).getName();
            updateAreas();
        }
        if (wheel == districtView) {
            mCurrentDistrict = districtDatas.get(newValue).getName();
        }

    }
}
