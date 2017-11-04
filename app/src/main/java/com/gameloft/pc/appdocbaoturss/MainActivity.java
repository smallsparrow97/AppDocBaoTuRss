package com.gameloft.pc.appdocbaoturss;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private CustomAdapter customAdapter;
    private ArrayList<DocBao> mangDocBao;

    private DrawerLayout dlMenu;
    private ImageButton btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dlMenu = (DrawerLayout) findViewById(R.id.dlMenu);
        btnMenu = (ImageButton) findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(this);

        listView = (ListView) findViewById(R.id.listView);
        mangDocBao = new ArrayList<DocBao>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadData().execute("https://vnexpress.net/rss/tin-moi-nhat.rss");
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("link", mangDocBao.get(position).link);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        dlMenu.openDrawer(Gravity.LEFT);
    }

    class ReadData extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... params) {
            return docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            NodeList nodeListDescription = document.getElementsByTagName("description");
            String hinhanh = "";
            String title = "";
            String link = "";
            for (int i=0; i<nodeList.getLength(); i++){
                String cData = nodeListDescription.item(i + 1).getTextContent();
                Pattern p;
                if (i<4){
                    p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                }
                else {
                    p = Pattern.compile("<img[^>]+data-original\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                }
                Matcher matcher = p.matcher(cData);
                if (matcher.find()){
                    hinhanh = matcher.group(1);
                    Log.d("hinhanh", hinhanh + "......" +i);
                }
                Element element = (Element) nodeList.item(i);
                title = parser.getValue(element, "title");
                link = parser.getValue(element, "link");
                mangDocBao.add(new DocBao(title, link, hinhanh));

            }
            customAdapter = new CustomAdapter(MainActivity.this, android.R.layout.simple_list_item_1, mangDocBao);
            listView.setAdapter(customAdapter);
            super.onPostExecute(s);

        }
    }

    private String docNoiDung_Tu_URL(String theUrl){
        StringBuilder content = new StringBuilder();
        try    {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)    {
            e.printStackTrace();
        }
        return content.toString();
    }
}
