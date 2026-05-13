package com.example.d308vacationplanner.UI;

import static android.content.ContentValues.TAG;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.Entities.Vacation;
import com.example.d308vacationplanner.R;
import com.google.android.material.tabs.TabLayout;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder> {
    private List<Vacation> mVacations;
    private final Context context;
    private final LayoutInflater mInflator;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

    public VacationAdapter(Context context){
        mInflator = LayoutInflater.from(context);
        this.context = context;
    }

    public class VacationViewHolder extends RecyclerView.ViewHolder{
        private final TextView vacationItemView;
        private final TextView vacationItemView2;

        public VacationViewHolder(@NonNull View itemView) {
            super(itemView);
            vacationItemView = itemView.findViewById(R.id.textView);
            vacationItemView2 = itemView.findViewById(R.id.textView16);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAbsoluteAdapterPosition();
                    final Vacation current = mVacations.get(position);
                    Intent intent = new Intent(context, VacationDetails.class);
                    intent.putExtra("vacationId", current.getVacationId());
                    intent.putExtra("title", current.getTitle());
                    intent.putExtra("hotel", current.getHotel());
                    //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                    intent.putExtra("startDate", sdf.format(current.getStartDate()));
                    intent.putExtra("endDate", sdf.format(current.getEndDate()));
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
            String returnedStringday = setDaysLeft(current.getStartDate());
            holder.vacationItemView.setText(name);
            holder.vacationItemView2.setText(returnedStringday);

        }
        else {
            holder.vacationItemView.setText(R.string.no_vacations);
            holder.vacationItemView2.setText(R.string.no_vacations);
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

    //This method sets the vacation countdown in days
    public String setDaysLeft(Date vacaDate) {
        Date today = new Date();
        LocalDate ld = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Date dateNoTime = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());

        String daysLeft = "";
        if (dateNoTime.before(vacaDate)) {
            long numInSecs = vacaDate.getTime() - dateNoTime.getTime();
            TimeUnit t = TimeUnit.DAYS;
            long numDays = t.convert(numInSecs, TimeUnit.MILLISECONDS);
            if(numDays > 1) {
                daysLeft = numDays + " days";
            }
            else daysLeft = String.valueOf(numDays) + " day";
        }
        else if(dateNoTime.equals(vacaDate)) {
            daysLeft = "Lets Go!";
        }

        return daysLeft;
    }

}
