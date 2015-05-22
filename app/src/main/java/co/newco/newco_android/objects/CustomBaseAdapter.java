package co.newco.newco_android.objects;

/**
 * Created by yass8n on 5/22/15.
 */

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public abstract class CustomBaseAdapter<T> extends BaseAdapter {
    protected ArrayList<T> models;

    public CustomBaseAdapter(ArrayList<T> models) {
        this.models = new ArrayList<T>(models);
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup viewGroup);

}