package com.example.consumirrestconvolley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private EditText edtxtId;
    private TextView edtxtInfo;
    private ImageView imgFind;

    private RequestQueue requestQue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtxtId=(EditText) findViewById(R.id.dtxTypeId);
        imgFind=(ImageButton) findViewById(R.id.imgBtnFind);
        edtxtInfo=(TextView) findViewById(R.id.txtvInfo);
        edtxtInfo.setMovementMethod(new ScrollingMovementMethod());

        requestQue=Volley.newRequestQueue(this);

        imgFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCover();

            }
        });

    }
    private void searchCover(){
    String url="https://revistas.uteq.edu.ec/ws/issues.php?j_id="+edtxtId.getText().toString();
        JsonArrayRequest requestJson=new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                    showCoverText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Error al conectarse:"+error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }

        );
        requestQue.add(requestJson);
    };



    private void showCoverText(JSONArray jArray){
        edtxtInfo.setText("");
        for(int i=0;i<jArray.length();i++){
            try{
                JSONObject objectJson=new JSONObject(jArray.get(i).toString());

                edtxtInfo.append("ID: "+objectJson.getString("issue_id")+"\n");
                edtxtInfo.append("VOLUMEN: "+objectJson.getString("volume")+"\n");
                edtxtInfo.append("NÚMERO: "+objectJson.getString("number")+"\n");
                edtxtInfo.append("AÑO: "+objectJson.getString("year")+"\n");
                edtxtInfo.append("FECHA DE PUBLICACIÓN: "+objectJson.getString("date_published")+"\n");
                edtxtInfo.append("TITULO: "+objectJson.getString("title")+"\n");
                edtxtInfo.append("URL: "+objectJson.getString("doi")+"\n");
                edtxtInfo.append("IMAGE: "+objectJson.getString("cover")+"\n");
                edtxtInfo.append("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓\n");
            }
            catch (JSONException e){
                Toast.makeText(this,"Error al cargar lista: "+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }

    };



}