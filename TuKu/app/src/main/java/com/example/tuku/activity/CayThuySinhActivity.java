package com.example.tuku.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.tuku.adapter.CayThuySinhAdapter;
import com.example.tuku.model.SanPham;
import com.example.tuku.ultil.CheckConnection;
import com.example.tuku.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CayThuySinhActivity extends AppCompatActivity {

    Toolbar toolbarCayThuySinh;
    ListView lvCayThuySinh;
    CayThuySinhAdapter cayThuySinhAdapter;
    ArrayList<SanPham> mangCayThuySinh;
    int idCTS = 0;
    int page = 1;
    View footerview;
    boolean isLoading = false;
    myHandle handle;
    boolean limitData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cay_thuy_sinh);

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
        lvCayThuySinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSPActivity.class);
                intent.putExtra("thongtinsanpham", mangCayThuySinh.get(position));
                startActivity(intent);
            }
        });

        lvCayThuySinh.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                String Tencts = "";
                String Hinhcts = "";
                int Giacts = 0;
                String Motacts = "";
                int Idloaicts = 0;
                if (response!=null && response.length() != 2){
                    lvCayThuySinh.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray=new JSONArray(response);
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Id = jsonObject.getInt("id");
                            Tencts = jsonObject.getString("tensp");
                            Hinhcts = jsonObject.getString("hinhsp");
                            Giacts = jsonObject.getInt("giasp");
                            Motacts = jsonObject.getString("motasp");
                            Idloaicts = jsonObject.getInt("idsanpham");
                            mangCayThuySinh.add(new SanPham(Id,Tencts,Hinhcts,Giacts,Motacts,Idloaicts));
                            cayThuySinhAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    limitData= true;
                    lvCayThuySinh.removeFooterView(footerview);
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
                param.put("idsanpham", String.valueOf(idCTS));

                return param;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarCayThuySinh);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarCayThuySinh.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void GetIdloaisp() {
        idCTS = getIntent().getIntExtra("idloaisanpham", -1);
        Log.d("giatriloaisanpham", idCTS+"");
    }

    private void AnhXa() {

        toolbarCayThuySinh = findViewById(R.id.toolBarCayThuySinh);
        lvCayThuySinh =findViewById(R.id.lvCayThuySinh);

        mangCayThuySinh =new ArrayList<>();
        cayThuySinhAdapter = new CayThuySinhAdapter(getApplicationContext(), mangCayThuySinh);
        lvCayThuySinh.setAdapter(cayThuySinhAdapter);

        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar, null);

        handle =new myHandle();
    }

    public class myHandle extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    lvCayThuySinh.addFooterView(footerview);
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