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

import com.example.d308vacationplanner.Entities.Vacation;
import com.example.d308vacationplanner.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.searchItemViewHolder> {
    private List<Vacation> mVacations;
    private List<Vacation> mVacationsFull;
    private final Context context;

    public SearchAdapter(List<Vacation> listToSearch, Context context){
        this.mVacations = new ArrayList<>(listToSearch);
        this.mVacationsFull = new ArrayList<>(listToSearch);
        this.context = context;
    }

    public class searchItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView vacationItemView;
        searchItemViewHolder(View v) {
            super(v);
            vacationItemView = v.findViewById(R.id.textView8);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    final Vacation current = mVacations.get(position);
                    Intent intent = new Intent(context, Reports.class);
                    intent.putExtra("vacationId", current.getVacationId());
                    intent.putExtra("title", current.getTitle());
                    intent.putExtra("hotel", current.getHotel());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                    intent.putExtra("startDate", simpleDateFormat.format(current.getStartDate()));
                    intent.putExtra("endDate", simpleDateFormat.format(current.getEndDate()));
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public searchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item, parent,
                false);
        return new searchItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull searchItemViewHolder holder, int position) {
        if (mVacations != null) {
            Vacation current = mVacations.get(position);
            String name = current.getTitle();
            holder.vacationItemView.setText(name);
        }
        else {
            holder.vacationItemView.setText(R.string.no_vacations);
        }
    }

    @Override
    public int getItemCount() {
        if (mVacations != null) {
            return mVacations.size();
        }
        else return 0;
    }

    public void searchThis(String q) {
        mVacations.clear();
        if (q == null || q.trim().isEmpty()) {
            mVacations.addAll(mVacationsFull);
        }
        else {
            String match = q.toLowerCase().trim();
            for(Vacation v : mVacationsFull) {
                if(v.getTitle().toLowerCase().contains(match)) {
                    mVacations.add(v);
                }
            }
        }
        notifyDataSetChanged();
    }
}
