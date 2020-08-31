package com.example.tuku.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tuku.R;
import com.example.tuku.model.GioHang;
import com.example.tuku.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSPActivity extends AppCompatActivity {

    Toolbar toolbarChiTietSP;
    ImageView imgChiTietSP;
    TextView tvTenChiTietSP, tvGiaChiTietSP, tvMoTaChiTietSP ;
    Spinner spinnerChiTietSP;
    Button btnThemVaoGioHang;

    int id = 0;
    String tenchitiet = "";
    String hinhchitiet = "";
    int giachitiet = 0;
    String motachitiet = "";
    int idspchitiet = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_s_p);

        AnhXa();
        ActionToolBar();
        GetInFo();
        CatchEventSpinner();
        EventButton();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_giohang, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_giohang:
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void EventButton() {
        btnThemVaoGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mangGiohang.size()>0){
                    int sl = Integer.parseInt(spinnerChiTietSP.getSelectedItem().toString());
                    boolean exits = false;
                    for (int i = 0; i<MainActivity.mangGiohang.size(); i++){
                        if (MainActivity.mangGiohang.get(i).getIdsp() == id){
                            MainActivity.mangGiohang.get(i)
                                    .setSoluongsp(MainActivity.mangGiohang
                                            .get(i).getSoluongsp()+sl);
                            if (MainActivity.mangGiohang.get(i).getSoluongsp()>=10){
                                MainActivity.mangGiohang.get(i).setSoluongsp(10);
                            }
                            MainActivity.mangGiohang.get(i).setGiasp(giachitiet*MainActivity.mangGiohang.get(i).getSoluongsp());
                            exits = true;
                        }
                    }
                    if (exits==false){
                        int soluong = Integer.parseInt(spinnerChiTietSP.getSelectedItem().toString());
                        long giamoi = soluong * giachitiet;
                        MainActivity.mangGiohang.add(new GioHang(id,tenchitiet,giamoi,hinhchitiet,soluong));

                    }

                }else {
                    int soluong = Integer.parseInt(spinnerChiTietSP.getSelectedItem().toString());
                    long giamoi = soluong * giachitiet;
                    MainActivity.mangGiohang.add(new GioHang(id,tenchitiet,giamoi,hinhchitiet,soluong));

                }
                Intent intent=new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
            }
        });

    }

    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spinnerChiTietSP.setAdapter(arrayAdapter);
    }

    private void GetInFo() {
        SanPham sanPham= (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanPham.getId();
        tenchitiet = sanPham.getTensanpham();
        hinhchitiet = sanPham.getHinhsanpham();
        giachitiet = sanPham.getGiasanpham();
        motachitiet = sanPham.getMotasanpham();
        idspchitiet = sanPham.getIdsanpham();

        tvTenChiTietSP.setText(tenchitiet);
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        tvGiaChiTietSP.setText("Giá " + decimalFormat.format(giachitiet)+ " Đ");
        tvMoTaChiTietSP.setText(motachitiet);
        Picasso.get().load(hinhchitiet)
                .placeholder(R.drawable.vanphong)
                .error(R.drawable.tinhyeu)
                .into(imgChiTietSP);

    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarChiTietSP);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTietSP.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void AnhXa() {

        toolbarChiTietSP = findViewById(R.id.toolBarChiTietSP);
        imgChiTietSP = findViewById(R.id.imgHinhChiTietSP);
        tvTenChiTietSP = findViewById(R.id.tvTenChiTietSP);
        tvGiaChiTietSP = findViewById(R.id.tvGiaChiTietSP);
        tvMoTaChiTietSP = findViewById(R.id.tvMoTaChiTietSP);
        spinnerChiTietSP = findViewById(R.id.spinnerChiTietSP);
        btnThemVaoGioHang = findViewById(R.id.btnThemVaoGioHang);
    }
}