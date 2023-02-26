package com.packagenemo.scrabble_plus.resume;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.packagenemo.scrabble_plus.R;

import java.util.List;

public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.ViewHolder>{
    private List<PartyData> partiesData;

    public PartyAdapter(List<PartyData> partiesData) {
        this.partiesData = partiesData;
    }

    @NonNull
    @Override
    public PartyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.resume_list_item, parent, false);
        PartyAdapter.ViewHolder viewHolder = new PartyAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PartyAdapter.ViewHolder holder, int position) {
        final PartyData data = partiesData.get(position);
        holder.partyName.setText(data.getPartyLabel());
        holder.partyImg.setImageResource(data.getPartyImgId());
        holder.partyState.setText(data.getPartyState());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Click sur player : "+data.getPartyLabel(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return partiesData.size();
    }

    public void update(PartyData pData) {
        partiesData.add(pData);
        this.notifyItemInserted(this.getItemCount());
    }

    public void delete(int position) {
        partiesData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, partiesData.size());
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView partyImg;
        public TextView partyName;
        public TextView partyState;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.partyImg = (ImageView) itemView.findViewById(R.id.resumeItemImg);
            this.partyName = (TextView) itemView.findViewById(R.id.resumePartyLabel);
            this.partyState = (TextView) itemView.findViewById(R.id.resumeStateLabel);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.resumeItemRelativeLayout);
        }
    }
}
