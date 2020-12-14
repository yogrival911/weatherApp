package com.yogdroidtech.weather;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    CoronaData coronaData;
    public MyAdapter(Context context, CoronaData coronaData) {
        this.context = context;
        this.coronaData = coronaData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false);

        return new MyAdapter.MyViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.i("yogesh", position+"recycer count");
        holder.textViewState.setText(coronaData.getStatewise().get(position).getState());
        holder.textViewStateTotal.setText(coronaData.getStatewise().get(position).getConfirmed()+"");
        holder.textViewStateDeaths.setText(coronaData.getStatewise().get(position).getDeaths()+"");
        holder.textViewStateRecov.setText(coronaData.getStatewise().get(position).getRecovered()+"");


    }

    @Override
    public int getItemCount() {

            return coronaData.getStatewise().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewState,textViewStateTotal, textViewStateDeaths, textViewStateRecov;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStateTotal = (TextView)itemView.findViewById(R.id.textViewStateTotal);
            textViewStateDeaths = (TextView)itemView.findViewById(R.id.textViewStateDeath);
            textViewStateRecov = (TextView)itemView.findViewById(R.id.textViewStateRecov);
            textViewState = (TextView)itemView.findViewById(R.id.textViewState);

        }
    }
}
