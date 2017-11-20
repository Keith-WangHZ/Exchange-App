package com.example.huazi.campusexchange.adapter;

        import android.content.Context;
        import android.util.Log;

        import com.example.huazi.campusexchange.model.bean.CityModel;

        import java.util.List;

/**
 * Created by huazi on 16/1/7.
 */
public class CitysAdapter extends AbstractWheelTextAdapter {
    public List<CityModel> mList;
    private Context mContext;
    public CitysAdapter(Context context, List<CityModel> list) {
        super(context);
        mList=list;
        mContext=context;
    }

    @Override
    protected CharSequence getItemText(int index) {
        Log.d("huazi1115", "getItemText is called ! index = " + index);
        CityModel cityModel=mList.get(index);
        return cityModel.getName();
    }

    @Override
    public int getItemsCount() {
        return mList.size();
    }
}
