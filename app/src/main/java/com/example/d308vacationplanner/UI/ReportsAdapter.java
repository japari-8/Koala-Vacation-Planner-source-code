package com.example.d308vacationplanner.UI;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.Entities.Excursion;
import com.example.d308vacationplanner.R;

import java.util.List;
import java.util.Locale;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ReportsViewHolder> {
    private List<Excursion> mExcursions;
    private final Context context;
    private final LayoutInflater mInflator;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

    public ReportsAdapter(Context context){
        mInflator = LayoutInflater.from(context);
        this.context = context;
    }

    public class ReportsViewHolder extends RecyclerView.ViewHolder{
        private final TextView excursionItemView;
        private final TextView excursionItemView2;

        public ReportsViewHolder(@NonNull View itemView) {
            super(itemView);
            excursionItemView = itemView.findViewById(R.id.textView3);
            excursionItemView2 = itemView.findViewById(R.id.textView4);
        }
    }

    @NonNull
    @Override
    public ReportsAdapter.ReportsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.excursion_list, parent, false);
        return new ReportsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportsAdapter.ReportsViewHolder holder, int position) {
        if (mExcursions != null){
            Excursion current = mExcursions.get(position);
            String name = current.getTitle();
            String date = simpleDateFormat.format(current.getDate());
            //long vacationId = current.getVacationId();
            holder.excursionItemView.setText(name);
            holder.excursionItemView2.setText(date);
        }
        else {
            holder.excursionItemView.setText(R.string.no_excursions);
            holder.excursionItemView2.setText(R.string.no_excursion_id);
        }
    }

    @Override
    public int getItemCount() {
        if (mExcursions != null) {
            return mExcursions.size();
        } else return 0;
    }

    public void setmExcursions(List<Excursion> excursions) {
        mExcursions = excursions;
        notifyDataSetChanged();
    }

}
