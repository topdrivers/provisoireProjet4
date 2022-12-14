package com.example.mareu.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mareu.activities.DetailsMeetingActivity;
import com.example.mareu.events.DeleteMeetingEvent;
import com.example.mareu.R;
import com.example.mareu.model.Meeting;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyMeetingRecyclerViewAdapter extends   RecyclerView.Adapter<MyMeetingRecyclerViewAdapter.ViewHolder>{

    private  List<Meeting> meetingList;

    public MyMeetingRecyclerViewAdapter(List<Meeting> meetings) {
        meetingList = meetings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list_meeting_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Meeting meeting = meetingList.get(position);
        holder.meetingName.setText(meeting.getTitle());
        holder.participantEmail.setText(meeting.getParticipantList());
        holder.time.setText(meeting.getStartDate().toString("dd/MM HH:mm") );
        holder.room.setText(meeting.getRoom().getName());

        if(meeting.getStatus().equals("BEFORE")) {
            Glide.with(holder.meetingAvatar.getContext())
                    .load(R.drawable.ic_brightness_1_red_200_24dp)
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.meetingAvatar);
        }else if (meeting.getStatus().equals("AFTER")){
            Glide.with(holder.meetingAvatar.getContext())
                    .load(R.drawable.ic_brightness_1_light_green_300_24dp)
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.meetingAvatar);
        }else {
            Glide.with(holder.meetingAvatar.getContext())
                    .load(R.drawable.ic_brightness_1_amber_300_24dp)
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.meetingAvatar);
        }


        holder.deleteButton
                .setOnClickListener(v -> EventBus.getDefault()
                        .post(new DeleteMeetingEvent(meeting)));

        holder.itemView.setOnClickListener(v -> {
            Bundle bundle=new Bundle();
            bundle.putSerializable("id",meeting);
            Intent intent = new Intent(holder.itemView.getContext(), DetailsMeetingActivity.class);
            intent.putExtras(bundle);

            holder.itemView.getContext().startActivity(intent);
        });
    }



    @Override
    public int getItemCount() {
        return meetingList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.item_imageView)
        public ImageView meetingAvatar;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.item_name)
        public TextView meetingName;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.item_delete_button)
        public ImageButton deleteButton;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.item_participants_email)
        public TextView participantEmail;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.item_time_textView)
        public TextView time;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.item_room_textView)
        public TextView room;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
