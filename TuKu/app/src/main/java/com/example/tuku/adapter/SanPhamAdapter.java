package com.example.tuku.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuku.R;
import com.example.tuku.activity.ChiTietSPActivity;
import com.example.tuku.model.SanPham;
import com.example.tuku.ultil.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemHolder> {

    Context context;
    ArrayList<SanPham> sanPhamArrayList;

    public SanPhamAdapter(Context context, ArrayList<SanPham> sanPhamArrayList) {
        this.context = context;
        this.sanPhamArrayList = sanPhamArrayList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinhat, null);

        ItemHolder itemHolder=new ItemHolder(v);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        SanPham sanPham=sanPhamArrayList.get(position);
        holder.tvTensp.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        holder.tvGiasp.setText("Giá: " + decimalFormat.format(sanPham.getGiasanpham()) +" Đ");

        Picasso.get().load(sanPham.getHinhsanpham())
                .placeholder(R.drawable.thuysinh)
                .error(R.drawable.tinhyeu)
                .into(holder.imgHinhsp);

    }

    @Override
    public int getItemCount() {
        return sanPhamArrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imgHinhsp;
        public TextView tvTensp, tvGiasp;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhsp = itemView.findViewById(R.id.imgHinhspmoinhat);
            tvTensp = itemView.findViewById(R.id.tvTenspmoinhat);
            tvGiasp = itemView.findViewById(R.id.tvGiaspmoinhat);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ChiTietSPActivity.class);
                    intent.putExtra("thongtinsanpham", sanPhamArrayList.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });
        }
    }
}
