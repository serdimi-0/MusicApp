package org.milaifontanals.musicapp.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.milaifontanals.musicapp.R;
import org.milaifontanals.musicapp.dao.TrackDao;
import org.milaifontanals.musicapp.model.Album;
import org.milaifontanals.musicapp.model.Track;
import org.milaifontanals.musicapp.view.AlbumListFragmentDirections;
import org.milaifontanals.musicapp.viewmodel.AlbumsViewModel;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private List<Album> list;
    private AppCompatActivity activity;
    private Fragment context;
    private AlbumsViewModel mViewModel;
    private int selectedIndex = -1;
    ImageLoader il;

    public AlbumAdapter(List<Album> list, AlbumsViewModel albumsViewModel,Fragment context, AppCompatActivity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
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
    public AlbumAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View card;
        card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_album, parent, false);
        return new ViewHolder(card);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumAdapter.ViewHolder holder, int position) {

        Album currentAlbum = list.get(position);
        mViewModel.setCurrentAlbum(currentAlbum);

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
        holder.albumArtist.setText(currentAlbum.getArtist());
        holder.albumYear.setText("" + currentAlbum.getYear());

        holder.itemView.setOnClickListener(e -> {

            LiveData<List<Track>> tracks = mViewModel.getTracks(currentAlbum);

            tracks.observe(context.getViewLifecycleOwner(), t -> {

                currentAlbum.setTrackList(t);
                mViewModel.setCurrentAlbum(currentAlbum);

                this.notifyItemChanged(this.selectedIndex);

                if (this.selectedIndex == -1) {
                    NavDirections n = AlbumListFragmentDirections.actionAlbumListFragmentToTracklistFragment();
                    NavController nav = NavHostFragment.findNavController(context);
                    nav.navigate(n);

                } else {
                    holder.itemView.getRootView().findViewById(R.id.albumToolbar).animate().alpha(0f).withEndAction(() -> {
                        if (holder.itemView.getRootView().findViewById(R.id.albumToolbar) != null)
                            holder.itemView.getRootView().findViewById(R.id.albumToolbar).setVisibility(View.INVISIBLE);
                    });
                }
                this.selectedIndex = -1;

            });
        });

        holder.itemView.setOnLongClickListener(e -> {
            int posicioAnterior = this.selectedIndex;
            this.selectedIndex = holder.getAdapterPosition();
            this.notifyItemChanged(posicioAnterior);
            this.notifyItemChanged(selectedIndex);

            holder.itemView.getRootView().findViewById(R.id.albumToolbar).setVisibility(View.VISIBLE);
            holder.itemView.getRootView().findViewById(R.id.albumToolbar).animate().alpha(1f);


            return true;
        });

        if (position == this.selectedIndex) {
            holder.itemView.findViewById(R.id.linearLayout).setBackgroundResource(R.color.lightPink);
        } else {
            holder.itemView.findViewById(R.id.linearLayout).setBackgroundResource(R.color.light);
        }
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
