package com.packagenemo.scrabble_plus.lobby;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.packagenemo.scrabble_plus.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder>{
    private List<PlayerData> playersData;

    public PlayerAdapter(List<PlayerData> playersData) {
        this.playersData = playersData;
    }

    @Override
    public PlayerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.lobby_list_item, parent, false);
        PlayerAdapter.ViewHolder viewHolder = new PlayerAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerAdapter.ViewHolder holder, int position) {
        final PlayerData data = playersData.get(position);
        holder.textView.setText(data.getPlayerName());
        holder.imageView.setImageResource(data.getPlayerImgId());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Click sur player : "+data.getPlayerName(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return playersData.size();
    }

    public void update(PlayerData pData) {
        playersData.add(pData);
        this.notifyItemInserted(this.getItemCount());
    }

    public void delete(int position) {
        playersData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,playersData.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.lobbyItemImg);
            this.textView = (TextView) itemView.findViewById(R.id.lobbyItemText);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.lobbyItemRelativeLayout);
        }
    }
}
