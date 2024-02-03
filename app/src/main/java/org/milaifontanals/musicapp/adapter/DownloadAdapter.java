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
import org.milaifontanals.musicapp.model.Album;
import org.milaifontanals.musicapp.model.Artist;

import java.util.List;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ViewHolder> {

    private List<?> list;
    private Fragment context;
    private int selectedIndex = -1;
    private String type;
    ImageLoader il;

    public DownloadAdapter(List<Artist> list, Fragment context, String type) {
        this.list = list;
        this.context = context;
        this.type = type;
        il = ImageLoader.getInstance();
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public List<?> getList() {
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
    public DownloadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row;
        row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_download, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadAdapter.ViewHolder holder, int position) {

        if (type.equals("Artist")) {
            Artist currentArtist = (Artist) list.get(position);

            if (currentArtist.getImgBitmap() != null) {
                holder.imgDownload.setImageBitmap(currentArtist.getImgBitmap());
            } else {
                il.displayImage(currentArtist.getImgSrc(), holder.imgDownload, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, android.graphics.Bitmap loadedImage) {
                        currentArtist.setImgBitmap(loadedImage);
                        holder.imgDownload.setImageBitmap(loadedImage);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                    }
                });
            }

            holder.txtDownloadName.setText(currentArtist.getName());
        } else if (type.equals("Album")){
            Album currentAlbum = (Album) list.get(position);

            if (currentAlbum.getImgBitmap() != null) {
                holder.imgDownload.setImageBitmap(currentAlbum.getImgBitmap());
            } else {
                il.displayImage(currentAlbum.getImgSrc(), holder.imgDownload, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, android.graphics.Bitmap loadedImage) {
                        currentAlbum.setImgBitmap(loadedImage);
                        holder.imgDownload.setImageBitmap(loadedImage);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                    }
                });
            }

            holder.txtDownloadName.setText(currentAlbum.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgDownload;
        TextView txtDownloadName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDownload = itemView.findViewById(R.id.downloadImage);
            txtDownloadName = itemView.findViewById(R.id.downloadName);
        }
    }
}
