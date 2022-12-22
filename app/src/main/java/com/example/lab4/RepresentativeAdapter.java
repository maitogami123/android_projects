package com.example.lab4;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RepresentativeAdapter extends RecyclerView.Adapter<RepresentativeAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<Representative> mRepresentatives;

    public RepresentativeAdapter(Context mContext, ArrayList<Representative> mRepresentatives) {
        this.mContext = mContext;
        this.mRepresentatives = mRepresentatives;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View representativeView;
        private TextView mRepresentativeOffice;
        private TextView mRepresentativeName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            representativeView = itemView;
            mRepresentativeOffice = itemView.findViewById(R.id.representative_office);
            mRepresentativeName = itemView.findViewById(R.id.representative_name);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View representativeView = inflater.inflate(R.layout.government_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(representativeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Representative representative = mRepresentatives.get(position);

        //TODO: thêm phương thức để set text ra ngoài view
        holder.mRepresentativeOffice.setText(representative.getOffice());
        String nameAndParty = representative.getName() + " (" + representative.getParty() + ")";
        holder.mRepresentativeName.setText(nameAndParty);


        holder.representativeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: click vào item chuyển hướng sang view mới xem chi thiết thông tin item vừa chọn
                Intent intent = new Intent(mContext, OfficialActivity.class);
                representative.packageIntent(intent, representative.getOffice(), representative.getName(),
                        representative.getParty(), representative.getImgUri(), representative.getAddress(),
                        representative.getPhoneNumber(), representative.getEmailAddress(),
                        representative.getWebsite(), representative.getFacebook(), representative.getTwitter(),
                        representative.getGooglePlus(), representative.getYoutubeChannel(),representative.getState());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRepresentatives.size();
    }

}
