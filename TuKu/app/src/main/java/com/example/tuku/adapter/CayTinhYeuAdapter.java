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

public class CayTinhYeuAdapter extends BaseAdapter {

    Context context;
    ArrayList<SanPham> arrayCayTinhYeu;

    public CayTinhYeuAdapter(Context context, ArrayList<SanPham> arrayCayTinhYeu) {
        this.context = context;
        this.arrayCayTinhYeu = arrayCayTinhYeu;
    }

    @Override
    public int getCount() {
        return arrayCayTinhYeu.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayCayTinhYeu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView tvTenCayTinhYeu, tvGiaCayTinhYeu, tvMotaCayTinhYeu;
        ImageView imgHinhCayTinhYeu;

    }



    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (view==null){
            viewHolder =new ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.dong_cay, null);
            viewHolder.tvTenCayTinhYeu = view.findViewById(R.id.tvTenCay);
            viewHolder.tvGiaCayTinhYeu = view.findViewById(R.id.tvGiaCay);
            viewHolder.tvMotaCayTinhYeu = view.findViewById(R.id.tvMotaCay);
            viewHolder.imgHinhCayTinhYeu = view.findViewById(R.id.imgCay);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        SanPham sanPham= (SanPham) getItem(position);
        viewHolder.tvTenCayTinhYeu.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        viewHolder.tvGiaCayTinhYeu.setText("Giá: " + decimalFormat.format(sanPham.getGiasanpham()) +" Đ");
        viewHolder.tvMotaCayTinhYeu.setMaxLines(3);
        viewHolder.tvMotaCayTinhYeu.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.tvMotaCayTinhYeu.setText(sanPham.getMotasanpham());
        Picasso.get().load(sanPham.getHinhsanpham())
                .placeholder(R.drawable.vanphong)
                .error(R.drawable.tinhyeu)
                .into(viewHolder.imgHinhCayTinhYeu);
        return view;
    }
}
