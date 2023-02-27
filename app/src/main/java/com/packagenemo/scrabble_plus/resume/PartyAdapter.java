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

/**
 * Adaptateur permettant de convertir les données en élément du menu déroulant
 */
public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.ViewHolder>{
    private List<PartyData> partiesData;

    public PartyAdapter(List<PartyData> partiesData) {
        this.partiesData = partiesData;
    }

    /**
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return
     */
    @NonNull
    @Override
    public PartyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // On créé une view à partir d'un fichier xml template
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.resume_list_item, parent, false);
        PartyAdapter.ViewHolder viewHolder = new PartyAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    /**
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull PartyAdapter.ViewHolder holder, int position) {
        // On récupère la donnée à la position donnée
        final PartyData data = partiesData.get(position);

        // On modifie le ViewHolder pour y affihcer nos données dessus
        holder.partyName.setText(data.getPartyLabel()); // Le nom de la partie
        holder.partyImg.setImageResource(data.getPartyImgId()); // L'image
        holder.partyState.setText(data.getPartyState()); // L'état de la partie
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Permet d'afficher un message à chaque fois que l'utilisateur clique sur un élément de la liste déroulante
                Toast.makeText(view.getContext(), "Click sur player : " + data.getPartyLabel(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Permet de récupérer le nombre d'éléments dans le menu déroulant
     * @return nombre d'éléments du menu déroulant
     */
    @Override
    public int getItemCount() {
        return partiesData.size();
    }

    /**
     * Permet de mettre à jour le menu déroulat lors de l'ajout d'une donnée
     * Cette donnée sera insérée à la fin de menu déroulant
     * @param pData donnée à insérer
     */
    public void update(PartyData pData) {
        partiesData.add(pData);
        this.notifyItemInserted(this.getItemCount());
    }

    /**
     * Permet de supprimer une donnée du menu déroulant à une position donnée
     * @param position position de la donnée à supprimer
     */
    public void delete(int position) {
        partiesData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, partiesData.size());
    }


    /**
     * Classe représentant la structure xml des données à afficher
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView partyImg;
        public TextView partyName;
        public TextView partyState;
        public RelativeLayout relativeLayout;

        /**
         * Récupère tous les éléments à modifier en fonction des données du template xml
         * @param itemView template
         */
        public ViewHolder(View itemView) {
            super(itemView);
            this.partyImg = (ImageView) itemView.findViewById(R.id.resumeItemImg);
            this.partyName = (TextView) itemView.findViewById(R.id.resumePartyLabel);
            this.partyState = (TextView) itemView.findViewById(R.id.resumeStateLabel);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.resumeItemRelativeLayout);
        }
    }
}
