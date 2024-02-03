package org.milaifontanals.musicapp.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.milaifontanals.musicapp.adapter.DownloadAdapter;
import org.milaifontanals.musicapp.databinding.FragmentDownloadMainBinding;
import org.milaifontanals.musicapp.lastfm.LastfmAPI;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DownloadMainFragment extends Fragment {

    private FragmentDownloadMainBinding binding;
    private DownloadAdapter downloadAdapter;
    private RecyclerView rcy;

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

        rcy = binding.rcyDownload;
        rcy.setLayoutManager(new LinearLayoutManager(getContext()));
        rcy.setHasFixedSize(true);



        binding.btnSearch.setOnClickListener(e -> {
            search();
        });
        binding.radioAlbums.setOnClickListener(e -> {
            search();
        });
        binding.radioArtists.setOnClickListener(e -> {
            search();
        });


        return v;
    }

    private void search(){
        String text = binding.edtSearch.getText().toString();

        if (text.isEmpty()) return;

        Observable.fromCallable(() -> {
            List<?> adapterList = null;
            if (binding.radioAlbums.isChecked()) {
                downloadAdapter = new DownloadAdapter(null, this, "Album");
                adapterList = LastfmAPI.getAlbumsFromName(text);
            } else if (binding.radioArtists.isChecked()) {
                downloadAdapter = new DownloadAdapter(null, this, "Artist");
                adapterList = LastfmAPI.getArtists(text);
            }
            return adapterList;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnNext(data -> {
            downloadAdapter.setList((List<?>)data);
            rcy.setAdapter(downloadAdapter);
        }).subscribe();

    }
}