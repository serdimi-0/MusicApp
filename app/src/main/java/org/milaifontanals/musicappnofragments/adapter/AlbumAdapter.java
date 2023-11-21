package org.milaifontanals.musicappnofragments.adapter;

import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.milaifontanals.musicappnofragments.R;
import org.milaifontanals.musicappnofragments.TracklistActivity;
import org.milaifontanals.musicappnofragments.model.Album;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private List<Album> list;
    private Context context;

    public AlbumAdapter(List<Album> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AlbumAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View card;
        card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_album, parent, false);
        return new ViewHolder(card);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumAdapter.ViewHolder holder, int position) {
        Album currentAlbum = list.get(position);
        holder.albumTitle.setText(currentAlbum.getTitle());
        holder.albumArtist.setText(currentAlbum.getArtist());
        holder.albumYear.setText("" +currentAlbum.getYear());

        ImageLoader il = ImageLoader.getInstance();
        il.displayImage(currentAlbum.getImgSrc(), holder.albumImg);

        holder.itemView.setOnClickListener(e -> {
                Intent i = new Intent(context, TracklistActivity.class);
                i.putExtra("albumId",currentAlbum.getId());
                context.startActivity(i);
        });

        holder.itemView.setOnLongClickListener(e -> {
            holder.itemView.findViewById(R.id.albumToolbar).setVisibility(View.VISIBLE);

            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView albumImg;
        TextView albumTitle;
        TextView albumArtist;
        TextView albumYear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            albumImg = itemView.findViewById(R.id.albumImg);
            albumTitle = itemView.findViewById(R.id.albumTitle);
            albumArtist = itemView.findViewById(R.id.albumArtist);
            albumYear = itemView.findViewById(R.id.albumYear);
        }
    }
}
