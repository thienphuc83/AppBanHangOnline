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

public class CayThuySinhAdapter extends BaseAdapter {

    Context context;
    ArrayList<SanPham> arrayCayThuySinh;

    public CayThuySinhAdapter(Context context, ArrayList<SanPham> arrayCayThuySinh) {
        this.context = context;
        this.arrayCayThuySinh = arrayCayThuySinh;
    }

    @Override
    public int getCount() {
        return arrayCayThuySinh.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayCayThuySinh.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView tvTenCayThuySinh, tvGiaCayThuySinh, tvMotaCayThuySinh;
        ImageView imgHinhCayThuySinh;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (view==null){
            viewHolder =new CayThuySinhAdapter.ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.dong_cay, null);
            viewHolder.tvTenCayThuySinh = view.findViewById(R.id.tvTenCay);
            viewHolder.tvGiaCayThuySinh = view.findViewById(R.id.tvGiaCay);
            viewHolder.tvMotaCayThuySinh = view.findViewById(R.id.tvMotaCay);
            viewHolder.imgHinhCayThuySinh = view.findViewById(R.id.imgCay);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        SanPham sanPham= (SanPham) getItem(position);
        viewHolder.tvTenCayThuySinh.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        viewHolder.tvGiaCayThuySinh.setText("Giá: " + decimalFormat.format(sanPham.getGiasanpham()) +" Đ");
        viewHolder.tvMotaCayThuySinh.setMaxLines(3);
        viewHolder.tvMotaCayThuySinh.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.tvMotaCayThuySinh.setText(sanPham.getMotasanpham());
        Picasso.get().load(sanPham.getHinhsanpham())
                .placeholder(R.drawable.vanphong)
                .error(R.drawable.tinhyeu)
                .into(viewHolder.imgHinhCayThuySinh);
        return view;
    }
}
