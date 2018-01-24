package com.testsample.testsample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Dot on 1/18/2018.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.MyViewHolder> {

    private List<PersonDetails> pList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName, txtMobile;

        public MyViewHolder(View view)
        {
            super(view);
            txtName = (TextView) view.findViewById(R.id.txtName);
            txtMobile = (TextView) view.findViewById(R.id.txtMobile);

        }
    }


    public PersonAdapter(List<PersonDetails> pList) {
        this.pList = pList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_item, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PersonDetails pd = pList.get(position);
        holder.txtName.setText(pd.getName());
        holder.txtMobile.setText(pd.getMobile());


    }

    @Override
    public int getItemCount() {
        return pList.size();
    }


}
