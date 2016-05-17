package com.stackoverflowapp.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.Example;
import com.example.Item;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedStmt;
import com.j256.ormlite.table.TableUtils;
import com.ormlitedbmodel.ItemTableModel;
import com.stackoverflowapp.R;
import com.stackoverflowapp.fragments.MainFragment;
import com.stackoverflowapp.helper.ConnectionDetector;
import com.stackoverflowapp.helper.DatabaseHelper;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Created by Neeraj on 15-05-2016.
 */
public class MainActivity extends AppCompatActivity {
    ProgressDialog mDialog;
    Context con;
    public static String tagged="android";//An string to keep track of for tagged for Stackexchange API search.
    String VallidationMessage = "";
    // Connection detector
    ConnectionDetector cd; //An class to check for network connection.
    public static List<Example> posts; //An static Example class list to keep track single instant of data.
    HttpURLConnection urlConnection; //An HttpURLConnection to keep track of for network communication.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        con=this;
        cd = new ConnectionDetector(getApplicationContext());
        mDialog = new ProgressDialog(MainActivity.this,R.style.MyTheme);
        mDialog.setCancelable(false);
        mDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        mDialog.setIndeterminate(true);
    }

        @Override
        protected void onStart() {
                super.onStart();
            // Check if Internet present
                if (cd.isConnectingToInternet()) {
                    //An method to keep track of API response.
                    AsyncMethod(tagged);
                } else {
                    //Fetch Data from loca db if available
                    try {

                        posts = getSearchDataFromDB();

                    } catch (Exception e){
                        Log.e("MainActivity", e.getLocalizedMessage(), e);
                    } finally {

                        if(posts == null) {
                            posts = new ArrayList<Example>();
                        }
                        //calling fragment instance .
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, MainFragment.newInstance())
                                .commit();
                    }
                }
        }


    private void blockinternet() {
        // TODO Auto-generated method stub

        VallidationMessage = "No Network";
        runOnUiThread(ShowAlert);// An ui thread to show alert dialog.
    }

    final Runnable ShowAlert = new Runnable() {
        public void run() {
            new AlertDialog.Builder(con)
                    .setTitle("Validation")
                    .setMessage(VallidationMessage)
                    .setNeutralButton("Close",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // do nothing – it will close on its own
                                }
                            }).show();

        }
    };

    private class DownloadTask extends AsyncTask<String, Void, List<Item>> {

        @Override
        protected List<Item> doInBackground(String... params) {

                try {
                    String baseURL = "https://api.stackexchange.com/2.2/search?order=desc&sort=activity&tagged="+tagged+"&site=stackoverflow";
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
                    posts = new ArrayList<Example>();
                //Instruct GSON to parse as a Post array (which we convert into a list)
                    posts = Arrays.asList(gson.fromJson(sb.toString(), Example.class));

                } catch (Exception e) {
                        e.printStackTrace();
                } finally {
                        if (urlConnection != null) {
                                urlConnection.disconnect();
                        }

                }

            List<Item> itemObj = new ArrayList<Item>();
            if(posts != null && posts.size() > 0){
                Example exampleObj = posts.get(0);
                itemObj = exampleObj.getItems();

            }
            return itemObj;
        }

        @Override
        protected void onPostExecute(List<Item> itemObj) {
            // TODO Auto-generated method stub
            super.onPostExecute(itemObj);
            try {

                testOutOrmLiteDatabase(itemObj);
                //calling fragment instance .
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, MainFragment.newInstance())
                        .commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            mDialog.dismiss();

        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            mDialog.show();
        }
    }

    public void  AsyncMethod(String taggedstr){
        tagged=taggedstr;
        // Check if Internet present
        if (cd.isConnectingToInternet()) {
            new DownloadTask().execute("");
        } else {
            //Fetch Data from loca db if available
            try {

                posts = getSearchDataFromDB();

            } catch (Exception e){
                Log.e("MainActivity", e.getLocalizedMessage(), e);
            } finally {

                if(posts == null) {
                    posts = new ArrayList<Example>();
                }
//calling fragment instance .
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, MainFragment.newInstance())
                        .commit();
            }
        }

    }

  //testOutOrmLiteDatabase to keep track of Database object and create DB stackoverflow.db.
    private void testOutOrmLiteDatabase(List<Item> itemList) throws SQLException {
        DatabaseHelper todoOpenDatabaseHelper = OpenHelperManager.getHelper(this,
                DatabaseHelper.class);
        DeleteAll();
        Dao<ItemTableModel, Long> todoDao = todoOpenDatabaseHelper.getDao();
        if(itemList != null)
        for(Item itemObj : itemList){
            ItemTableModel itemTableObj = new ItemTableModel();
            itemTableObj.getMappedItemTableObject(itemObj);

            todoDao.create(itemTableObj);
        }

    }
    //An method to delete data for offline so no duplicate get create.
    private void DeleteAll() throws SQLException {
        DatabaseHelper todoOpenDatabaseHelper = OpenHelperManager.getHelper(this,
                DatabaseHelper.class);

        Dao<ItemTableModel, Long> todoDao = todoOpenDatabaseHelper.getDao();



        List<ItemTableModel> objList = todoDao.queryBuilder().distinct().query();
        if(objList != null && objList.size() > 0){
            List<Item> itemList = new ArrayList<Item>();
            for(ItemTableModel item : objList){

                todoDao.delete(item);

            }

        }else{
            blockinternet();
        }


    }
//An method to get data for offline.
    private List<Example> getSearchDataFromDB() throws SQLException {
        DatabaseHelper todoOpenDatabaseHelper = OpenHelperManager.getHelper(this,
                DatabaseHelper.class);

        Dao<ItemTableModel, Long> todoDao = todoOpenDatabaseHelper.getDao();
        List<Example> postsList = new ArrayList<Example>();
        Example exmpleObj = new Example();


        List<ItemTableModel> objList = todoDao.queryBuilder().distinct().query();
        if(objList != null && objList.size() > 0){
            List<Item> itemList = new ArrayList<Item>();
            for(ItemTableModel item : objList){
                if(!itemList.contains(item.getItemObject())) {
                    itemList.add(item.getItemObject());
                }
            }
            exmpleObj.setItems(itemList);
        }else{
            blockinternet();
        }
        postsList.add(exmpleObj);
        return postsList;
    }
}
