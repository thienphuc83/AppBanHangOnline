package com.example.tuku.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tuku.R;
import com.example.tuku.adapter.CayDeBanAdapter;
import com.example.tuku.model.SanPham;
import com.example.tuku.ultil.CheckConnection;
import com.example.tuku.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CayDeBanActivity extends AppCompatActivity {

    Toolbar toolbarCayDeBan;
    ListView lvCayDeBan;
    CayDeBanAdapter cayDeBanAdapter;
    ArrayList<SanPham> mangCayDeBan;
    int idCDB = 0;
    int page = 1;
    View footerview;
    boolean isLoading = false;
    myHandle handle;
    boolean limitData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cay_de_ban);

        AnhXa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            GetIdloaisp();
            ActionToolBar();
            GetData(page);
            LoadMoreData();
        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại internet!");
            finish();
        }
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

    private void LoadMoreData() {
        lvCayDeBan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSPActivity.class);
                intent.putExtra("thongtinsanpham", mangCayDeBan.get(position));
                startActivity(intent);
            }
        });

        lvCayDeBan.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount !=0 && isLoading == false && limitData == false){
                    isLoading=true;
                    ThreadData threadData =new ThreadData();
                    threadData.start();
                }
            }
        });

    }

    private void GetData(int Page) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String duongdan= Server.duongdansanpham+page;
        StringRequest stringRequest= new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int Id = 0;
                String Tencdb = "";
                String Hinhcdb = "";
                int Giacdb = 0;
                String Motacdb = "";
                int Idloaicdb = 0;
                if (response!=null && response.length() != 2){
                    lvCayDeBan.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray=new JSONArray(response);
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Id = jsonObject.getInt("id");
                            Tencdb = jsonObject.getString("tensp");
                            Hinhcdb = jsonObject.getString("hinhsp");
                            Giacdb = jsonObject.getInt("giasp");
                            Motacdb = jsonObject.getString("motasp");
                            Idloaicdb = jsonObject.getInt("idsanpham");
                            mangCayDeBan.add(new SanPham(Id,Tencdb,Hinhcdb,Giacdb,Motacdb,Idloaicdb));
                            cayDeBanAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    limitData= true;
                    lvCayDeBan.removeFooterView(footerview);
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Đã hết dữ liệu!");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idsanpham", String.valueOf(idCDB));

                return param;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarCayDeBan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarCayDeBan.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void GetIdloaisp() {
        idCDB = getIntent().getIntExtra("idloaisanpham", -1);
        Log.d("giatriloaisanpham", idCDB+"");
    }

    private void AnhXa() {
        toolbarCayDeBan = findViewById(R.id.toolBarCayDeBan);
        lvCayDeBan =findViewById(R.id.lvCayDeBan);

        mangCayDeBan =new ArrayList<>();
        cayDeBanAdapter =new CayDeBanAdapter(getApplicationContext(),mangCayDeBan);
        lvCayDeBan.setAdapter(cayDeBanAdapter);

        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar, null);

        handle =new myHandle();
    }

    public class myHandle extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    lvCayDeBan.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
                    isLoading =false;
                    break;
            }

            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread{
        @Override
        public void run() {
            handle.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message=handle.obtainMessage(1);
            handle.sendMessage(message);
            super.run();
        }
    }
}