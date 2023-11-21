package org.milaifontanals.musicappnofragments.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.milaifontanals.musicappnofragments.R;
import org.milaifontanals.musicappnofragments.model.Track;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {

    private List<Track> trackList;
    private Context context;

    public TrackAdapter(List<Track> trackList, Context context) {
        this.trackList = trackList;
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

        if (currentTrack.isFav()){
            holder.trackFav.setImageResource(R.drawable.heart_active);
        } else {
            holder.trackFav.setImageResource(R.drawable.heart_unactive);
        }

        holder.trackFav.setOnClickListener(e -> {
            if (currentTrack.isFav()){
                currentTrack.setFav(false);
                holder.trackFav.setImageResource(R.drawable.heart_unactive);
            } else {
                currentTrack.setFav(true);
                holder.trackFav.setImageResource(R.drawable.heart_active);
            }
        });

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
