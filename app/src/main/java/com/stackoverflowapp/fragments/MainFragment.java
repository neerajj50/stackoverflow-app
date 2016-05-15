package com.stackoverflowapp.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.Example;
import com.stackoverflowapp.R;
import com.stackoverflowapp.activities.MainActivity;
import com.stackoverflowapp.adapter.ExampleAdapter;
import com.stackoverflowapp.helper.ConnectionDetector;
import java.util.List;
/**
 * Created by Neeraj on 15-05-2016.
 */
public class MainFragment extends Fragment implements SearchView.OnQueryTextListener {

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    ConnectionDetector cd;// Connection detector
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);// recyclerview refrence
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        cd = new ConnectionDetector(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ExampleAdapter(getActivity(), MainActivity.posts);//Adapter to bind data
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        // Get the search close button image view
        ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ((MainActivity) getActivity()).AsyncMethod(MainActivity.tagged);
                    mAdapter = new ExampleAdapter(getActivity(), MainActivity.posts);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public boolean onQueryTextChange(String query) {
        List<Example> filteredModelList = filter(MainActivity.posts, query);
        if ((filteredModelList.get(0).getItems().size() == 0 && query.length() == 0)) {
            ((MainActivity) getActivity()).AsyncMethod(MainActivity.tagged);
            mAdapter = new ExampleAdapter(getActivity(), MainActivity.posts);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        } else {

            mAdapter = new ExampleAdapter(getActivity(), filteredModelList);

            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (!cd.isConnectingToInternet()) {
            blockinternet();
        } else {
            ((MainActivity) getActivity()).AsyncMethod(query);
            mAdapter = new ExampleAdapter(getActivity(), MainActivity.posts);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
        return true;
    }

    private List<Example> filter(List<Example> models, String query) {
        query = query.toLowerCase();

        final List<Example> filteredModelList = models;
        for (Example model : models) {
            for (int count = 0; count < model.getItems().size(); count++) {
                final String text = model.getItems().get(count).getTitle().toLowerCase();
                if (!text.contains(query)) {

                    filteredModelList.get(0).getItems().remove(count);

                }

                if (filteredModelList.get(0).getItems().size() > 20) {
                    for (int sizecount = 20; sizecount < filteredModelList.get(0).getItems().size(); sizecount++) {
                        filteredModelList.get(0).getItems().remove(sizecount);
                    }
                }
            }
        }
        return filteredModelList;
    }

    private void blockinternet() {
        // TODO Auto-generated method stub
        getActivity().runOnUiThread(ShowAlert);
    }

    final Runnable ShowAlert = new Runnable() {
        public void run() {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Validation")
                    .setMessage("No Network")
                    .setNeutralButton("Close",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // do nothing – it will close on its own
                                }
                            }).show();

        }
    };
}
