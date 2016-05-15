package com.stackoverflowapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.Example;
import com.example.NewExample;
import com.stackoverflowapp.R;

import java.util.List;

/**
 * Created by Neeraj on 15-05-2016.
 */
public class CustomAdapter extends BaseAdapter implements View.OnClickListener {
    List<NewExample> posts;
    Context ctx;
    private static LayoutInflater inflater=null;
    public CustomAdapter(List<NewExample> posts, Context ctx){
        this.posts=posts;
        this.ctx=ctx;
        /***********  Layout inflator to call external xml layout () ***********/
        inflater = (LayoutInflater)ctx.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public int getCount() {
        if(this.posts.get(0).getItems().size()==0)
        return 1;
        else
        return this.posts.get(0).getItems().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {

        View itemView = convertView;
        final ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            itemView = inflater.inflate(R.layout.item_listrow, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.tvText = (TextView) itemView.findViewById(R.id.tvTexttitle);
            holder.tvtxtvote = (TextView) itemView.findViewById(R.id.tvTextvotes);
            holder.tvtxtanswer = (TextView) itemView.findViewById(R.id.tvTextanswers);
            holder.tvtxtauthor = (TextView) itemView.findViewById(R.id.tvTextauthor);

            /************  Set holder with LayoutInflater ************/
            itemView.setTag( holder );
        }
        else
            holder=(ViewHolder)itemView.getTag();
        if(posts.get(0).getItems().size()==0)
        {
            holder.tvText.setText("No Data");
            holder.tvtxtvote.setVisibility(View.GONE);
            holder.tvtxtanswer.setVisibility(View.GONE);
            holder.tvtxtauthor.setVisibility(View.GONE);
        }
        else
        {
            holder.tvText.setText(Html.fromHtml("http://stackoverflow.com/A/" + posts.get(0).getItems().get(pos).getAcceptedAnswerId()));
            holder.tvText.setTag(Html.fromHtml("http://stackoverflow.com/A/" + posts.get(0).getItems().get(pos).getAcceptedAnswerId()));
            holder.tvtxtvote.setText(Html.fromHtml(String.valueOf(posts.get(0).getItems().get(pos).getScore())) + "\n votes");
            holder.tvtxtanswer.setText("accepted answer");
            holder.tvtxtanswer.setVisibility(View.GONE);
            holder.tvtxtauthor.setText(Html.fromHtml(posts.get(0).getItems().get(pos).getOwner().getDisplayName()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse( holder.tvText.getTag().toString()));
                    ctx.startActivity(browserIntent);
                }
            });
        }
        return itemView;
    }

    public static class ViewHolder{

        public  TextView tvText,tvtxtvote,tvtxtanswer,tvtxtauthor;

    }
}
