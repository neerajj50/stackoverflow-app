package com.stackoverflowapp.adapter.viewholder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Example;
import com.stackoverflowapp.R;
import com.stackoverflowapp.activities.AnswerActivity;
import com.stackoverflowapp.helper.ConnectionDetector;
/**
 * Created by Neeraj on 15-05-2016.
 */
public class ExampleViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvText,tvtxtvote,tvtxtanswer,tvtxtauthor;
    // Connection detector
    ConnectionDetector cd;
    Context ctx;
    public ExampleViewHolder(View itemView) {
        super(itemView);

        tvText = (TextView) itemView.findViewById(R.id.tvTexttitle);
        tvtxtvote = (TextView) itemView.findViewById(R.id.tvTextvotes);
        tvtxtanswer = (TextView) itemView.findViewById(R.id.tvTextanswers);
        tvtxtauthor = (TextView) itemView.findViewById(R.id.tvTextauthor);

    }

    public void bind(final Example model, final int pos, final Context ctx) {
        this.ctx=ctx;
        cd = new ConnectionDetector(ctx);
        if(model.getItems().size()==0)
        {
            tvText.setText("No Data");

        }else {
            tvText.setText(Html.fromHtml(model.getItems().get(pos).getTitle()));
            tvtxtvote.setText(Html.fromHtml(String.valueOf(model.getItems().get(pos).getScore()))+ "\n votes");
            tvtxtanswer.setText(Html.fromHtml(String.valueOf(model.getItems().get(pos).getAnswerCount())) + "\n answer");
            tvtxtauthor.setText(Html.fromHtml(model.getItems().get(pos).getOwner().getDisplayName()));
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if Internet present
                if (cd.isConnectingToInternet()) {
                    Intent intent = new Intent(ctx, AnswerActivity.class);
                    intent.putExtra("questionid", model.getItems().get(pos).getQuestionId());
                    intent.putExtra("question", model.getItems().get(pos).getTitle());
                    ctx.startActivity(intent);
                }else{
                    Toast.makeText(ctx,"Internet connection not avialable!",Toast.LENGTH_LONG).show();

                }

            }
        });
    }
}
