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

public class CayDeBanAdapter extends BaseAdapter {

    Context context;
    ArrayList<SanPham> arrayCayDeBan;

    public CayDeBanAdapter(Context context, ArrayList<SanPham> arrayCayDeBan) {
        this.context = context;
        this.arrayCayDeBan = arrayCayDeBan;
    }

    @Override
    public int getCount() {
        return arrayCayDeBan.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayCayDeBan.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView tvTenCayDeBan, tvGiaCayDeBan, tvMotaCayDeBan;
        ImageView imgHinhCayDeBan;

    }



    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (view==null){
            viewHolder =new ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.dong_cay, null);
            viewHolder.tvTenCayDeBan = view.findViewById(R.id.tvTenCay);
            viewHolder.tvGiaCayDeBan = view.findViewById(R.id.tvGiaCay);
            viewHolder.tvMotaCayDeBan = view.findViewById(R.id.tvMotaCay);
            viewHolder.imgHinhCayDeBan = view.findViewById(R.id.imgCay);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        SanPham sanPham= (SanPham) getItem(position);
        viewHolder.tvTenCayDeBan.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        viewHolder.tvGiaCayDeBan.setText("Giá: " + decimalFormat.format(sanPham.getGiasanpham()) +" Đ");
        viewHolder.tvMotaCayDeBan.setMaxLines(3);
        viewHolder.tvMotaCayDeBan.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.tvMotaCayDeBan.setText(sanPham.getMotasanpham());
        Picasso.get().load(sanPham.getHinhsanpham())
                .placeholder(R.drawable.vanphong)
                .error(R.drawable.tinhyeu)
                .into(viewHolder.imgHinhCayDeBan);
        return view;
    }
}
