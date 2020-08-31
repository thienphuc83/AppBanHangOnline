package com.example.tuku.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tuku.R;
import com.example.tuku.model.Loaisp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaispAdapter extends BaseAdapter {
    ArrayList<Loaisp> loaispArrayList;
    Context context;

    public LoaispAdapter(ArrayList<Loaisp> loaispArrayList, Context context) {
        this.loaispArrayList = loaispArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return loaispArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return loaispArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView tvTenLoaisp;
        ImageView imgHinhLoaisp;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder  viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_list_loaisp, null);

            viewHolder.tvTenLoaisp = view.findViewById(R.id.tvTenLoaisp);
            viewHolder.imgHinhLoaisp = view.findViewById(R.id.imgHinhLoaisp);
            view.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) view.getTag();

        }
        Loaisp loaisp = (Loaisp) getItem(position);
        viewHolder.tvTenLoaisp.setText(loaisp.getTenloaisp());
        Picasso.get().load(loaisp.getHinhloaisp())
                .placeholder(R.drawable.deban)
                .error(R.drawable.tinhyeu)
                .into(viewHolder.imgHinhLoaisp);

        return view;
    }
}
