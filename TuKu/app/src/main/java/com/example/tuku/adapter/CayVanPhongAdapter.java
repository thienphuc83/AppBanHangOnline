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

public class CayVanPhongAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arrayCayVanPhong;

    public CayVanPhongAdapter(Context context, ArrayList<SanPham> arrayCayVanPhong) {
        this.context = context;
        this.arrayCayVanPhong = arrayCayVanPhong;
    }

    @Override
    public int getCount() {
        return arrayCayVanPhong.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayCayVanPhong.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView tvTenCayVanPhong, tvGiaCayVanPhong, tvMotaCayVanPhong;
        ImageView imgHinhCayVanPhong;

    }



    @Override
    public View getView(int position, View view, ViewGroup parent) {
        CayVanPhongAdapter.ViewHolder viewHolder=null;
        if (view==null){
            viewHolder =new CayVanPhongAdapter.ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.dong_cay, null);
            viewHolder.tvTenCayVanPhong = view.findViewById(R.id.tvTenCay);
            viewHolder.tvGiaCayVanPhong = view.findViewById(R.id.tvGiaCay);
            viewHolder.tvMotaCayVanPhong = view.findViewById(R.id.tvMotaCay);
            viewHolder.imgHinhCayVanPhong = view.findViewById(R.id.imgCay);
            view.setTag(viewHolder);
        }else {
            viewHolder = (CayVanPhongAdapter.ViewHolder) view.getTag();
        }
        SanPham sanPham= (SanPham) getItem(position);
        viewHolder.tvTenCayVanPhong.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        viewHolder.tvGiaCayVanPhong.setText("Giá: " + decimalFormat.format(sanPham.getGiasanpham()) +" Đ");
        viewHolder.tvMotaCayVanPhong.setMaxLines(3);
        viewHolder.tvMotaCayVanPhong.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.tvMotaCayVanPhong.setText(sanPham.getMotasanpham());
        Picasso.get().load(sanPham.getHinhsanpham())
                .placeholder(R.drawable.vanphong)
                .error(R.drawable.tinhyeu)
                .into(viewHolder.imgHinhCayVanPhong);
        return view;
    }
}
