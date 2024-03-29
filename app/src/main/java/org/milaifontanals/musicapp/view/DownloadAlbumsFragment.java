package org.milaifontanals.musicapp.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.milaifontanals.musicapp.R;
import org.milaifontanals.musicapp.adapter.AlbumAdapter;
import org.milaifontanals.musicapp.adapter.AlbumDownloadAdapter;
import org.milaifontanals.musicapp.databinding.FragmentDownloadAlbumsBinding;
import org.milaifontanals.musicapp.lastfm.LastfmAPI;
import org.milaifontanals.musicapp.model.Album;
import org.milaifontanals.musicapp.utils.GridSpacingItemDecoration;
import org.milaifontanals.musicapp.viewmodel.AlbumsViewModel;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DownloadAlbumsFragment extends Fragment {

    private AlbumsViewModel mViewModel;
    private AlbumDownloadAdapter albumDownloadAdapter;
    private FragmentDownloadAlbumsBinding binding;

    public DownloadAlbumsFragment() {
    }

    public static DownloadAlbumsFragment newInstance() {
        DownloadAlbumsFragment fragment = new DownloadAlbumsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(AlbumsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDownloadAlbumsBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();

        ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(requireContext()).denyCacheImageMultipleSizesInMemory().diskCacheSize(2048).tasksProcessingOrder(QueueProcessingType.LIFO).defaultDisplayImageOptions(DisplayImageOptions.createSimple()).build();
        ImageLoader.getInstance().init(conf);

        binding.artistName.setText(mViewModel.getCurrentArtist());

        binding.btnDownloadAlbum.setOnClickListener(v1 -> {
            Observable.fromCallable(() -> {
                Album a = LastfmAPI.getAlbumInfo(mViewModel.getCurrentArtist(), mViewModel.getCurrentAlbum().getTitle());
                mViewModel.insert(a);
                return true;
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnNext(data -> {
                /*albumDownloadAdapter.setSelectedIndex(-1);*/
               /* albumDownloadAdapter.notify();*/
                binding.downloadAlbumToolbar.animate().alpha(0).withEndAction(() -> {
                    binding.downloadAlbumToolbar.setVisibility(View.GONE);
                });
            }).subscribe();

        });

        RecyclerView rcyAlbums = binding.rcyArtistAlbums;
        rcyAlbums.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        rcyAlbums.addItemDecoration(new GridSpacingItemDecoration(3, 10, true));
        rcyAlbums.setHasFixedSize(true);

        albumDownloadAdapter = new AlbumDownloadAdapter(mViewModel.getAlbumDownloadList(), mViewModel, this);
        rcyAlbums.setAdapter(albumDownloadAdapter);

        return v;
    }
}