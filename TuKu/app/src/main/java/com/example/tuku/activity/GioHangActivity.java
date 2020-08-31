package com.example.tuku.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tuku.R;
import com.example.tuku.adapter.GioHangAdapter;
import com.example.tuku.ultil.CheckConnection;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {

    Toolbar toolbarGioHang;
    TextView tvThongBao;
    static TextView tvTongTien;
    ListView lvSPGioHang;
    Button btnThanhToan, btnTiepTucMua;
    GioHangAdapter gioHangAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        AnhXa();
        ActionToolBar();
        CheckData();
        EventUltil();
        CatOnClickItemListView();
        EventClickButton();
    }

    private void EventClickButton() {
        btnTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mangGiohang.size()>0){
                    Intent intent=new Intent(getApplicationContext(),ThongTinKHActivity.class);
                    startActivity(intent);

                }else {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Giỏ hàng của bạn chưa có sản phẩm!");
                }


            }
        });
    }

    private void CatOnClickItemListView() {

        lvSPGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder= new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này!");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (MainActivity.mangGiohang.size()<=0){
                            tvThongBao.setVisibility(View.VISIBLE);
                        }else {
                            MainActivity.mangGiohang.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            EventUltil();
                            if (MainActivity.mangGiohang.size()<=0){
                                tvThongBao.setVisibility(View.VISIBLE);
                            }else {
                                tvThongBao.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                EventUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gioHangAdapter.notifyDataSetChanged();
                        EventUltil();
                    }
                });
                builder.show();
                return true;
            }
        });

    }

    public static void EventUltil() {
        long tongtien= 0;
        for (int i = 0; i< MainActivity.mangGiohang.size(); i++){
            tongtien+=MainActivity.mangGiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        tvTongTien.setText(decimalFormat.format(tongtien)+" Đ");
    }

    private void CheckData() {
        if (MainActivity.mangGiohang.size()<=0){
            gioHangAdapter.notifyDataSetChanged();
            tvThongBao.setVisibility(View.VISIBLE);
            lvSPGioHang.setVisibility(View.INVISIBLE);
        }else {
            gioHangAdapter.notifyDataSetChanged();
            tvThongBao.setVisibility(View.INVISIBLE);
            lvSPGioHang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void AnhXa() {
        toolbarGioHang = findViewById(R.id.toolbarGioHang);
        tvTongTien = findViewById(R.id.tvTongTien);
        tvThongBao = findViewById(R.id.tvThongBao);
        lvSPGioHang = findViewById(R.id.lvGioHang);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        btnTiepTucMua = findViewById(R.id.btnTiepTucMua);

        gioHangAdapter = new GioHangAdapter(getApplicationContext(), MainActivity.mangGiohang);
        lvSPGioHang.setAdapter(gioHangAdapter);
    }
}