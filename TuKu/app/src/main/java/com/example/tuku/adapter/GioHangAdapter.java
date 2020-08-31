package com.example.tuku.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tuku.R;
import com.example.tuku.activity.GioHangActivity;
import com.example.tuku.activity.MainActivity;
import com.example.tuku.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.example.tuku.activity.GioHangActivity.EventUltil;


public class GioHangAdapter extends BaseAdapter {

    Context context;
    ArrayList<GioHang> gioHangArrayList;

    public GioHangAdapter(Context context, ArrayList<GioHang> gioHangArrayList) {
        this.context = context;
        this.gioHangArrayList = gioHangArrayList;
    }

    @Override
    public int getCount() {
        return gioHangArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return gioHangArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView tvTenSPGioHang, tvGiaSPGioHang;
        ImageView imgHinhSPGioHang, imgXoaSPGioHang;
        Button btnTru, btnSo, btnCong;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view ==null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view= layoutInflater.inflate(R.layout.dong_gio_hang, null);
            viewHolder.tvTenSPGioHang = view.findViewById(R.id.tvTenSPGioHang);
            viewHolder.tvGiaSPGioHang = view.findViewById(R.id.tvGiaSPGioHang);
            viewHolder.imgHinhSPGioHang = view.findViewById(R.id.imgHinhSPGioHang);

            viewHolder.btnCong = view.findViewById(R.id.btnCong);
            viewHolder.btnSo = view.findViewById(R.id.btnSo);
            viewHolder.btnTru = view.findViewById(R.id.btnTru);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        GioHang gioHang= (GioHang) getItem(position);
        viewHolder.tvTenSPGioHang.setText(gioHang.getTensp());
        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
        viewHolder.tvGiaSPGioHang.setText(decimalFormat.format(gioHang.getGiasp())+" Đ");
        Picasso.get().load(gioHang.getHinhsp())
                .placeholder(R.drawable.vanphong)
                .error(R.drawable.tinhyeu)
                .into(viewHolder.imgHinhSPGioHang);
        viewHolder.btnSo.setText(gioHang.getSoluongsp()+"");

        int sl = Integer.parseInt(viewHolder.btnSo.getText().toString());
        if (sl>=10){
            viewHolder.btnCong.setVisibility(View.INVISIBLE);
            viewHolder.btnTru.setVisibility(View.VISIBLE);
        }else if (sl<=1){
            viewHolder.btnCong.setVisibility(View.VISIBLE);
            viewHolder.btnTru.setVisibility(View.INVISIBLE);
        }else if (sl>=1){
            viewHolder.btnCong.setVisibility(View.VISIBLE);
            viewHolder.btnTru.setVisibility(View.VISIBLE);
        }

        final ViewHolder finalViewHolder = viewHolder;

        viewHolder.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat= Integer.parseInt(finalViewHolder.btnSo.getText().toString())+1;
                int slhientai = MainActivity.mangGiohang.get(position).getSoluongsp();
                long giahientai = MainActivity.mangGiohang.get(position).getGiasp();
                // công thức tính
                MainActivity.mangGiohang.get(position).setSoluongsp(slmoinhat);
                long giamoinhat = (giahientai * slmoinhat) / slhientai;
                MainActivity.mangGiohang.get(position).setGiasp(giamoinhat);

                DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
                finalViewHolder.tvGiaSPGioHang.setText(decimalFormat.format(giamoinhat)+" Đ");

                EventUltil();
                if (slmoinhat > 9){
                    finalViewHolder.btnCong.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnTru.setVisibility(View.VISIBLE);
                    finalViewHolder.btnSo.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewHolder.btnCong.setVisibility(View.VISIBLE);
                    finalViewHolder.btnTru.setVisibility(View.VISIBLE);
                    finalViewHolder.btnSo.setText(String.valueOf(slmoinhat));
                }

            }
        });

        viewHolder.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat= Integer.parseInt(finalViewHolder.btnSo.getText().toString())-1;
                int slhientai = MainActivity.mangGiohang.get(position).getSoluongsp();
                long giahientai = MainActivity.mangGiohang.get(position).getGiasp();
                // công thức tính
                MainActivity.mangGiohang.get(position).setSoluongsp(slmoinhat);
                long giamoinhat = (giahientai * slmoinhat) / slhientai;
                MainActivity.mangGiohang.get(position).setGiasp(giamoinhat);

                DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
                finalViewHolder.tvGiaSPGioHang.setText(decimalFormat.format(giamoinhat)+" Đ");

                EventUltil();
                if (slmoinhat < 2){
                    finalViewHolder.btnCong.setVisibility(View.VISIBLE);
                    finalViewHolder.btnTru.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnSo.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewHolder.btnCong.setVisibility(View.VISIBLE);
                    finalViewHolder.btnTru.setVisibility(View.VISIBLE);
                    finalViewHolder.btnSo.setText(String.valueOf(slmoinhat));
                }
            }
        });


        return view;
    }
}
