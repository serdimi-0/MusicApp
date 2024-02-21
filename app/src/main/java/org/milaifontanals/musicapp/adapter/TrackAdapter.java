package org.milaifontanals.musicapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import org.milaifontanals.musicapp.R;
import org.milaifontanals.musicapp.model.Track;
import org.milaifontanals.musicapp.view.AlbumListFragmentDirections;
import org.milaifontanals.musicapp.view.TrackEditDialogFragmentDirections;
import org.milaifontanals.musicapp.view.TracklistFragmentDirections;
import org.milaifontanals.musicapp.viewmodel.AlbumsViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {

    private List<Track> trackList;
    Fragment context;
    private AlbumsViewModel mViewModel;
    private int selectedIndex = -1;

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public List<Track> getTrackList() {
        return trackList;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }
    public TrackAdapter(List<Track> trackList, Fragment context, AlbumsViewModel albumsViewModel) {
        this.trackList = trackList;
        this.mViewModel = albumsViewModel;
        this.context = context;
    }

    @NonNull
    @Override
    public TrackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row;
        row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_track, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackAdapter.ViewHolder holder, int position) {
        Track currentTrack = trackList.get(position);
        holder.trackNumber.setText("" + currentTrack.getNumber());
        holder.trackTitle.setText(currentTrack.getTitle());

        Date d = new Date(currentTrack.getDuration());
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        holder.trackDuration.setText(sdf.format(d));

        if (currentTrack.isFav()) {
            holder.trackFav.setImageResource(R.drawable.heart_active);
        } else {
            holder.trackFav.setImageResource(R.drawable.heart_unactive);
        }

        holder.trackFav.setOnClickListener(e -> {
            if (currentTrack.isFav()) {
                currentTrack.setFav(false);
                holder.trackFav.setImageResource(R.drawable.heart_unactive);
            } else {
                currentTrack.setFav(true);
                holder.trackFav.setImageResource(R.drawable.heart_active);
            }
            mViewModel.updateTrack(currentTrack);
        });

        holder.itemView.setOnClickListener(e -> {
            this.notifyItemChanged(selectedIndex);
            selectedIndex = -1;
            holder.itemView.getRootView().findViewById(R.id.trackToolbar).animate().alpha(0f).withEndAction(() -> {
                if (holder.itemView.getRootView().findViewById(R.id.trackToolbar) != null)
                    holder.itemView.getRootView().findViewById(R.id.trackToolbar).setVisibility(View.INVISIBLE);
            });

        });
        
        holder.itemView.setOnLongClickListener(e -> {
            int posicioAnterior = this.selectedIndex;
            this.selectedIndex = holder.getAdapterPosition();
            this.notifyItemChanged(posicioAnterior);
            this.notifyItemChanged(selectedIndex);
            
            mViewModel.setCurrentTrack(currentTrack);
            
            holder.itemView.getRootView().findViewById(R.id.trackToolbar).setVisibility(View.VISIBLE);
            holder.itemView.getRootView().findViewById(R.id.trackToolbar).animate().alpha(1f);
            
            return true;
        });

        if (position == this.selectedIndex) {
            holder.itemView.findViewById(R.id.trackRow).setBackgroundResource(R.color.lightPink);
        } else {
            holder.itemView.findViewById(R.id.trackRow).setBackgroundResource(R.color.light);
        }

    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView trackNumber;
        ImageView trackFav;
        TextView trackTitle;
        TextView trackDuration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            trackNumber = itemView.findViewById(R.id.trackNumber);
            trackTitle = itemView.findViewById(R.id.trackTitle);
            trackDuration = itemView.findViewById(R.id.trackDuration);
            trackFav = itemView.findViewById(R.id.trackFav);
        }
    }
}
