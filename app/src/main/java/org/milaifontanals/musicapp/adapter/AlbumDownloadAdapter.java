package org.milaifontanals.musicapp.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.milaifontanals.musicapp.R;
import org.milaifontanals.musicapp.model.Album;
import org.milaifontanals.musicapp.viewmodel.AlbumsViewModel;

import java.util.List;

public class AlbumDownloadAdapter extends RecyclerView.Adapter<AlbumDownloadAdapter.ViewHolder> {

    private List<Album> list;
    private Fragment context;
    private AlbumsViewModel mViewModel;
    ImageLoader il;
    private int selectedIndex = -1;

    public AlbumDownloadAdapter(List<Album> list, AlbumsViewModel albumsViewModel,Fragment context) {
        this.list = list;
        this.context = context;
        this.mViewModel = albumsViewModel;
        il = ImageLoader.getInstance();
    }


    public List<Album> getList() {
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
    public AlbumDownloadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View card;
        card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_album_download, parent, false);
        return new AlbumDownloadAdapter.ViewHolder(card);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumDownloadAdapter.ViewHolder holder, int position) {
        Album currentAlbum = list.get(position);
        if (currentAlbum.getImgBitmap() != null) {
            holder.albumImg.setImageBitmap(currentAlbum.getImgBitmap());
        } else {
            il.loadImage(currentAlbum.getImgSrc(), new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    currentAlbum.setImgBitmap(loadedImage);
                    holder.albumImg.setImageBitmap(loadedImage);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                }
            });
        }
        holder.albumTitle.setText(currentAlbum.getTitle());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView albumImg;
        TextView albumTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            albumImg = itemView.findViewById(R.id.albumImg);
            albumTitle = itemView.findViewById(R.id.albumTitle);
        }
    }
}
