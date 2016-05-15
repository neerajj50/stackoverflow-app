package com.stackoverflowapp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.NewExample;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stackoverflowapp.R;
import com.stackoverflowapp.adapter.CustomAdapter;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Neeraj on 15-05-2016.
 */
public class AnswerActivity extends Activity {
    ListView list;
    ProgressDialog mDialog;
    CustomAdapter adapter;
    public  List<NewExample> posts;
    HttpURLConnection urlConnection;
    int questionid=0;
    TextView heading;
    ImageView back;
    Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list_view_android_example);
        ctx=this;
        mDialog = new ProgressDialog(AnswerActivity.this,R.style.MyTheme);
        mDialog.setCancelable(false);
        mDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        mDialog.setIndeterminate(true);
        questionid=getIntent().getExtras().getInt("questionid");
        heading= ( TextView )findViewById( R.id.heading );
        heading.setText(Html.fromHtml("  Question: "+ getIntent().getExtras().getString("question")));
        list= ( ListView )findViewById( R.id.list );
        back= ( ImageView )findViewById( R.id.backbutton );
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        new DownloadWebAudioTask().execute("");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        SearchView search = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, AnswerActivity.class)));
        search.setQueryHint(getResources().getString(R.string.enter_a_word));
        return true;
    }

    private class DownloadWebAudioTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                String baseURL = "https://api.stackexchange.com/2.2/questions/"+questionid+"/answers?order=desc&sort=activity&site=stackoverflow";
                URL url1 = new URL(baseURL );
                urlConnection = (HttpURLConnection) url1
                        .openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader isw = new InputStreamReader(in);

                int data = isw.read();
                StringBuffer sb=new StringBuffer();
                while (data != -1) {
                    char current = (char) data;
                    data = isw.read();
                    sb.append(current);

                }

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a"); //Format of our JSON dates
                Gson gson = gsonBuilder.create();
                posts = new ArrayList<NewExample>();
                //Instruct GSON to parse as a Post array (which we convert into a list)
                posts = Arrays.asList(gson.fromJson(sb.toString(), NewExample.class));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            /**************** Create Custom Adapter *********/
            adapter=new CustomAdapter( posts,ctx );
            list.setAdapter(adapter);
            mDialog.dismiss();

        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            mDialog.show();
        }
    }
}
