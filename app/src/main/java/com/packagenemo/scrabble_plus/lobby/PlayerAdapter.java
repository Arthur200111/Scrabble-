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

/**
 * Adaptateur permettant de convertir les données en élément du menu déroulant
 */
public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder>{
    private List<PlayerData> playersData;

    public PlayerAdapter(List<PlayerData> playersData) {
        this.playersData = playersData;
    }

    /**
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // On créé une view à partir d'un fichier xml template
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.lobby_list_item, parent, false);
        PlayerAdapter.ViewHolder viewHolder = new PlayerAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    /**
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull PlayerAdapter.ViewHolder holder, int position) {
        // On récupère la donnée à la position donnée
        final PlayerData data = playersData.get(position);

        // On modifie le ViewHolder pour y affihcer nos données dessus
        holder.textView.setText(data.getPlayerName()); // Le nom du joueur
        holder.imageView.setImageResource(data.getPlayerImgId()); // L'image du joueur
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Permet d'afficher un message à chaque fois que l'utilisateur clique sur un élément de la liste déroulante
                Toast.makeText(view.getContext(), "Click sur player : "+data.getPlayerName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Permet de récupérer le nombre d'éléments dans le menu déroulant
     * @return nombre d'éléments du menu déroulant
     */
    @Override
    public int getItemCount() {
        return playersData.size();
    }

    /**
     * Permet de mettre à jour le menu déroulat lors de l'ajout d'une donnée
     * Cette donnée sera insérée à la fin de menu déroulant
     * @param pData donnée à insérer
     */
    public void update(PlayerData pData) {
        playersData.add(pData);
        this.notifyItemInserted(this.getItemCount());
    }

    /**
     * Permet de supprimer une donnée du menu déroulant à une position donnée
     * @param position position de la donnée à supprimer
     */
    public void delete(int position) {
        playersData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,playersData.size());
    }

    /**
     * Classe représentant la structure xml des données à afficher
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public RelativeLayout relativeLayout;
        /**
         * Récupère tous les éléments à modifier en fonction des données du template xml
         * @param itemView template
         */
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.lobbyItemImg);
            this.textView = (TextView) itemView.findViewById(R.id.lobbyItemText);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.lobbyItemRelativeLayout);
        }
    }
}
