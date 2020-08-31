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

public class CayTieuCanhAdapter extends BaseAdapter {

    Context context;
    ArrayList<SanPham> arrayCayTieuCanh;

    public CayTieuCanhAdapter(Context context, ArrayList<SanPham> arrayCayTieuCanh) {
        this.context = context;
        this.arrayCayTieuCanh = arrayCayTieuCanh;
    }

    @Override
    public int getCount() {
        return arrayCayTieuCanh.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayCayTieuCanh.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView tvTenCayTieuCanh, tvGiaCayTieuCanh, tvMotaCayTieuCanh;
        ImageView imgHinhCayTieuCanh;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (view==null){
            viewHolder =new ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.dong_cay, null);
            viewHolder.tvTenCayTieuCanh = view.findViewById(R.id.tvTenCay);
            viewHolder.tvGiaCayTieuCanh = view.findViewById(R.id.tvGiaCay);
            viewHolder.tvMotaCayTieuCanh = view.findViewById(R.id.tvMotaCay);
            viewHolder.imgHinhCayTieuCanh = view.findViewById(R.id.imgCay);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        SanPham sanPham= (SanPham) getItem(position);
        viewHolder.tvTenCayTieuCanh.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        viewHolder.tvGiaCayTieuCanh.setText("Giá: " + decimalFormat.format(sanPham.getGiasanpham()) +" Đ");
        viewHolder.tvMotaCayTieuCanh.setMaxLines(3);
        viewHolder.tvMotaCayTieuCanh.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.tvMotaCayTieuCanh.setText(sanPham.getMotasanpham());
        Picasso.get().load(sanPham.getHinhsanpham())
                .placeholder(R.drawable.vanphong)
                .error(R.drawable.tinhyeu)
                .into(viewHolder.imgHinhCayTieuCanh);
        return view;
    }
}
