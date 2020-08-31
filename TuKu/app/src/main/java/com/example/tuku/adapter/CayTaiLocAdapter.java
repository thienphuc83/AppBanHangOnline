package com.example.tuku.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tuku.R;
import com.example.tuku.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CayTaiLocAdapter extends BaseAdapter {

    Context context;
    ArrayList<SanPham> arrayCayTaiLoc;

    public CayTaiLocAdapter(Context context, ArrayList<SanPham> arrayCayTaiLoc) {
        this.context = context;
        this.arrayCayTaiLoc = arrayCayTaiLoc;
    }

    @Override
    public int getCount() {
        return arrayCayTaiLoc.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayCayTaiLoc.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView tvTenCayTaiLoc, tvGiaCayTaiLoc, tvMotaCayTaiLoc;
        ImageView imgHinhCayTaiLoc;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (view==null){
            viewHolder =new ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.dong_cay, null);
            viewHolder.tvTenCayTaiLoc = view.findViewById(R.id.tvTenCay);
            viewHolder.tvGiaCayTaiLoc = view.findViewById(R.id.tvGiaCay);
            viewHolder.tvMotaCayTaiLoc = view.findViewById(R.id.tvMotaCay);
            viewHolder.imgHinhCayTaiLoc = view.findViewById(R.id.imgCay);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        SanPham sanPham= (SanPham) getItem(position);
        viewHolder.tvTenCayTaiLoc.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        viewHolder.tvGiaCayTaiLoc.setText("Giá: " + decimalFormat.format(sanPham.getGiasanpham()) +" Đ");
        viewHolder.tvMotaCayTaiLoc.setMaxLines(3);
        viewHolder.tvMotaCayTaiLoc.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.tvMotaCayTaiLoc.setText(sanPham.getMotasanpham());
        Picasso.get().load(sanPham.getHinhsanpham())
                .placeholder(R.drawable.vanphong)
                .error(R.drawable.tinhyeu)
                .into(viewHolder.imgHinhCayTaiLoc);
        return view;
    }
}
