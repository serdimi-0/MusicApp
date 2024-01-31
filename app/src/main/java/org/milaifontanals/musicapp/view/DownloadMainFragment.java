package org.milaifontanals.musicapp.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import org.milaifontanals.musicapp.adapter.ArtistAdapter;
import org.milaifontanals.musicapp.databinding.FragmentDownloadMainBinding;
import org.milaifontanals.musicapp.lastfm.LastfmAPI;
import org.milaifontanals.musicapp.model.Album;
import org.milaifontanals.musicapp.model.Artist;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DownloadMainFragment extends Fragment {

    private FragmentDownloadMainBinding binding;
    private ArtistAdapter artistAdapter;

    public DownloadMainFragment() {
    }

    public static DownloadMainFragment newInstance() {
        DownloadMainFragment fragment = new DownloadMainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDownloadMainBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();

        ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(requireContext()).denyCacheImageMultipleSizesInMemory().diskCacheSize(2048).tasksProcessingOrder(QueueProcessingType.LIFO).defaultDisplayImageOptions(DisplayImageOptions.createSimple()).build();
        ImageLoader.getInstance().init(conf);

        RecyclerView rcy = binding.rcyDownload;
        rcy.setLayoutManager(new LinearLayoutManager(getContext()));
        rcy.setHasFixedSize(true);


        artistAdapter = new ArtistAdapter(null, this);


        binding.btnSearch.setOnClickListener(e -> {
            String text = binding.edtSearch.getText().toString();

            if (text.isEmpty()) return;

            Observable.fromCallable(() -> {
                if (binding.radioAlbums.isChecked()) {
                    return LastfmAPI.getAlbums(text);
                } else if (binding.radioArtists.isChecked()) {
                    return LastfmAPI.getArtists(text);
                }
                return null;
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnNext(data -> {
                if (data instanceof List<?>) {
                    List<?> dataList = (List<?>) data;
                    if (binding.radioAlbums.isChecked()) {
                        Log.d("TAG", dataList.toString());
                    } else if (binding.radioArtists.isChecked()) {
                        rcy.setAdapter(artistAdapter);
                        artistAdapter.setList((List<Artist>) dataList);
                        Log.d("TAG", dataList.toString());
                    }
                }
            }).subscribe();

        });


        return v;
    }
}