package com.example.d308vacationplanner.UI;

import static androidx.core.content.ContextCompat.startActivity;

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

import java.util.List;
import java.util.Locale;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder> {
    private List<Vacation> mVacations;
    private final Context context;
    private final LayoutInflater mInflator;

    public VacationAdapter(Context context){
        mInflator = LayoutInflater.from(context);
        this.context = context;
    }

    public class VacationViewHolder extends RecyclerView.ViewHolder{
        private final TextView vacationItemView;

        public VacationViewHolder(@NonNull View itemView) {
            super(itemView);
            vacationItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAbsoluteAdapterPosition();
                    final Vacation current = mVacations.get(position);
                    Intent intent = new Intent(context, VacationDetails.class);
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
    public VacationAdapter.VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.vacation_list, parent, false);
        return new VacationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VacationAdapter.VacationViewHolder holder, int position) {
        if (mVacations != null){
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
        if(mVacations != null){
            return mVacations.size();
        }
        else  return 0;

    }

    public void setVacations(List<Vacation> vacations){
        mVacations = vacations;
        notifyDataSetChanged();
    }

}
