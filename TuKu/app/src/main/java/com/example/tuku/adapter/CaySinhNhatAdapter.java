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

public class CaySinhNhatAdapter extends BaseAdapter {

    Context context;
    ArrayList<SanPham> arrayCaySinhNhat;

    public CaySinhNhatAdapter(Context context, ArrayList<SanPham> arrayCaySinhNhat) {
        this.context = context;
        this.arrayCaySinhNhat = arrayCaySinhNhat;
    }

    @Override
    public int getCount() {
        return arrayCaySinhNhat.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayCaySinhNhat.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView tvTenCaySinhNhat, tvGiaCaySinhNhat, tvMotaCaySinhNhat;
        ImageView imgHinhCaySinhNhat;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (view==null){
            viewHolder =new ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.dong_cay, null);
            viewHolder.tvTenCaySinhNhat = view.findViewById(R.id.tvTenCay);
            viewHolder.tvGiaCaySinhNhat = view.findViewById(R.id.tvGiaCay);
            viewHolder.tvMotaCaySinhNhat = view.findViewById(R.id.tvMotaCay);
            viewHolder.imgHinhCaySinhNhat = view.findViewById(R.id.imgCay);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        SanPham sanPham= (SanPham) getItem(position);
        viewHolder.tvTenCaySinhNhat.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        viewHolder.tvGiaCaySinhNhat.setText("Giá: " + decimalFormat.format(sanPham.getGiasanpham()) +" Đ");
        viewHolder.tvMotaCaySinhNhat.setMaxLines(3);
        viewHolder.tvMotaCaySinhNhat.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.tvMotaCaySinhNhat.setText(sanPham.getMotasanpham());
        Picasso.get().load(sanPham.getHinhsanpham())
                .placeholder(R.drawable.vanphong)
                .error(R.drawable.tinhyeu)
                .into(viewHolder.imgHinhCaySinhNhat);
        return view;
    }
}
