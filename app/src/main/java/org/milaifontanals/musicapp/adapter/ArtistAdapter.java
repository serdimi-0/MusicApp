package org.milaifontanals.musicapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.milaifontanals.musicapp.R;
import org.milaifontanals.musicapp.model.Artist;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder>{

    private List<Artist> list;
    private Fragment context;
    private int selectedIndex = -1;
    ImageLoader il;

    public ArtistAdapter(List<Artist> list, Fragment context) {
        this.list = list;
        this.context = context;
        il = ImageLoader.getInstance();
    }

    public void setList(List<Artist> list) {
        this.list = list;
    }

    public List<Artist> getList() {
        return list;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    @NonNull
    @Override
    public ArtistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row;
        row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_artist, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistAdapter.ViewHolder holder, int position) {
        Artist currentArtist = list.get(position);

        if (currentArtist.getImgBitmap() != null) {
            holder.imgArtist.setImageBitmap(currentArtist.getImgBitmap());
        } else {
            il.displayImage(currentArtist.getImgSrc(), holder.imgArtist, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, android.graphics.Bitmap loadedImage) {
                    currentArtist.setImgBitmap(loadedImage);
                    holder.imgArtist.setImageBitmap(loadedImage);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                }
            });
        }

        holder.txtArtistName.setText(currentArtist.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgArtist;
        TextView txtArtistName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgArtist = itemView.findViewById(R.id.artistImage);
            txtArtistName = itemView.findViewById(R.id.artistName);
        }
    }
}
