package com.example.zedcrest.medmanager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ZEDCREST on 12/04/2018.
 */

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MyViewHolder> {

    private List<Medication> medicationList;
    private AdapterView.OnItemClickListener onItemClickListener;
   // private AdapterView.OnItemLongClickListener onItemClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView name,description;

        public MyViewHolder(View view){
            super(view);
           name = (TextView)view.findViewById(R.id.list_name);
            description = (TextView)view.findViewById(R.id.list_description);
        }

    }




    public  MedicationAdapter(List<Medication> medications, AdapterView.OnItemClickListener onItemClickListener){
        this.medicationList = medications;
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public MedicationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MedicationAdapter.MyViewHolder holder, final int position) {
        Medication medication = medicationList.get(position);
        holder.name.setText(medication.getName());
        holder.description.setText(medication.getDescription());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(null, view, position, view.getId());
            }
        });


    }

    @Override
    public int getItemCount() {
        return medicationList.size();
    }


}
