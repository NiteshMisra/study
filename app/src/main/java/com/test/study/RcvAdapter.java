package com.test.study;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RcvAdapter extends RecyclerView.Adapter<RcvAdapter.ViewHold> {

    private Context context;
    private List<StudyModel> list;

    RcvAdapter(Context ctx, List<StudyModel> list1){
        this.context = ctx;
        this.list = list1;
    }


    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.item_rcv,parent,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        return new ViewHold(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {
        final StudyModel uploadCurrent = list.get(position);
        switch (position%2){
            case 0 : {
                holder.layout.setBackgroundColor(Color.parseColor("#FCFCFC"));
                break;
            }
            case 1 : {
                holder.layout.setBackgroundColor(Color.parseColor("#F5F5F5"));
                break;
            }
        }
        holder.name.setText(uploadCurrent.getTitle());
        if (Integer.parseInt(uploadCurrent.getFreeStatus()) == 1){
            holder.view.setText("Free PDF");
            holder.buyBtn.setVisibility(View.GONE);
            holder.rupee.setVisibility(View.GONE);
        }else{
            String s = uploadCurrent.getPrice();
            holder.view.setText(s);
            holder.buyBtn.setVisibility(View.VISIBLE);
            holder.rupee.setVisibility(View.VISIBLE);
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(uploadCurrent.getFreeStatus()) != 1){
                    if (context instanceof MainActivity){
                        ((MainActivity)context).startPayment(Integer.parseInt(uploadCurrent.getId()),uploadCurrent.getTitle(),Integer.parseInt(uploadCurrent.getPrice()) * 100);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHold extends RecyclerView.ViewHolder{

        ViewHold(View itemView){
            super(itemView);
        }

        TextView name = itemView.findViewById(R.id.study_item_name);
        TextView view = itemView.findViewById(R.id.study_item_view_Btn);
        ConstraintLayout layout  = itemView.findViewById(R.id.study_item_layout);
        TextView buyBtn = itemView.findViewById(R.id.study_item_buy_btn);
        ImageView rupee = itemView.findViewById(R.id.study_item_rupee);
    }

}
