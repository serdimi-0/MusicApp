package org.milaifontanals.musicapp.adapter;


import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.milaifontanals.musicapp.R;
import org.milaifontanals.musicapp.lastfm.LastfmAPI;
import org.milaifontanals.musicapp.model.Album;
import org.milaifontanals.musicapp.model.Artist;
import org.milaifontanals.musicapp.view.DownloadMainFragmentDirections;
import org.milaifontanals.musicapp.viewmodel.AlbumsViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ViewHolder> {

    private List<?> list;
    private Fragment context;
    private AlbumsViewModel mViewModel;
    private int selectedIndex = -1;
    private String type;
    ImageLoader il;

    public DownloadAdapter(List<Artist> list, AlbumsViewModel mViewModel,Fragment context, String type) {
        this.list = list;
        this.mViewModel = mViewModel;
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


            holder.itemView.setOnClickListener(e -> {
                NavDirections n = DownloadMainFragmentDirections.actionDownloadMainFragmentToDownloadAlbumsFragment();
                NavController nav = NavHostFragment.findNavController(context);

                Observable.fromCallable(() -> {
                    List<Album> albumList = LastfmAPI.getAlbumsFromArtist(currentArtist.getId());
                    if(albumList == null) albumList = new ArrayList<>();
                    return albumList;
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnNext(data -> {
                    if (!data.isEmpty()) {
                        mViewModel.setAlbumDownloadList(data);
                        mViewModel.setCurrentArtist(currentArtist.getName());
                        nav.navigate(n);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context.requireContext());
                        builder.setTitle("Something went wrong");
                        builder.setPositiveButton("Ok",null);
                        builder.setMessage("This artist has no albums. Please, select another one.");
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }).subscribe();
            });

        } else if (type.equals("Album")) {
            Album currentAlbum = (Album) list.get(position);

            holder.itemView.setOnClickListener(e -> {
                int posicioAnterior = this.selectedIndex;
                this.selectedIndex = holder.getAdapterPosition();
                this.notifyItemChanged(posicioAnterior);
                this.notifyItemChanged(selectedIndex);

                mViewModel.setCurrentAlbum(currentAlbum);

                holder.itemView.getRootView().findViewById(R.id.downloadToolbar).setVisibility(View.VISIBLE);
                holder.itemView.getRootView().findViewById(R.id.downloadToolbar).animate().alpha(1f);
            });

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

            if (position == this.selectedIndex) {
                holder.itemView.findViewById(R.id.rowDownload).setBackgroundResource(R.color.lightPink);
            } else {
                holder.itemView.findViewById(R.id.rowDownload).setBackgroundResource(R.color.light);
            }
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
