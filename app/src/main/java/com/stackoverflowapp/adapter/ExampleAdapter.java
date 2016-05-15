package com.stackoverflowapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.Example;
import com.stackoverflowapp.R;
import com.stackoverflowapp.adapter.viewholder.ExampleViewHolder;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Neeraj on 15-05-2016.
 */
public class ExampleAdapter extends RecyclerView.Adapter<ExampleViewHolder> {

    private final LayoutInflater mInflater;
    private final List<Example> mModels;
    Context ctx;

    public ExampleAdapter(Context context, List<Example> models) {
        ctx=context;
        mInflater = LayoutInflater.from(context);
        mModels = new ArrayList<>(models);
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = mInflater.inflate(R.layout.item_listrow, parent, false);
        return new ExampleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        final Example model = mModels.get(0);
        holder.bind(model,position,ctx);
    }

    @Override
    public int getItemCount() {
        return mModels.get(0).getItems().size();
    }



}
