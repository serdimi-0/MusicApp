package org.milaifontanals.musicapp.view;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.milaifontanals.musicapp.R;
import org.milaifontanals.musicapp.adapter.DownloadAdapter;
import org.milaifontanals.musicapp.databinding.FragmentDownloadMainBinding;
import org.milaifontanals.musicapp.lastfm.LastfmAPI;
import org.milaifontanals.musicapp.viewmodel.AlbumsViewModel;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DownloadMainFragment extends Fragment {

    private FragmentDownloadMainBinding binding;
    private DownloadAdapter downloadAdapter;
    private AlbumsViewModel mViewModel;
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
        mViewModel = new ViewModelProvider(requireActivity()).get(AlbumsViewModel.class);
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
            binding.downloadToolbar.animate().alpha(0f).withEndAction(() -> {
                if (binding.downloadToolbar != null)
                    binding.downloadToolbar.setVisibility(View.INVISIBLE);
            });
            search();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        });
        binding.radioAlbums.setOnClickListener(e -> {
            binding.downloadToolbar.animate().alpha(0f).withEndAction(() -> {
                if (binding.downloadToolbar != null)
                    binding.downloadToolbar.setVisibility(View.INVISIBLE);
            });
            search();
        });
        binding.radioArtists.setOnClickListener(e -> {
            binding.downloadToolbar.animate().alpha(0f).withEndAction(() -> {
                if (binding.downloadToolbar != null)
                    binding.downloadToolbar.setVisibility(View.INVISIBLE);
            });
            search();
        });

        binding.btnDownload.setOnClickListener(v1 -> {
            Log.d("TAG", mViewModel.getCurrentAlbum().toString());
            mViewModel.getCurrentAlbum().setReleaseDate(new Date());
            mViewModel.insertAlbum(mViewModel.getCurrentAlbum());
        });

        return v;
    }

    private void search(){
        String text = binding.edtSearch.getText().toString();

        if (text.isEmpty()) return;

        Observable.fromCallable(() -> {
            List<?> adapterList = null;
            if (binding.radioAlbums.isChecked()) {
                downloadAdapter = new DownloadAdapter(null, mViewModel ,this, "Album");
                adapterList = LastfmAPI.getAlbumsFromName(text);
            } else if (binding.radioArtists.isChecked()) {
                downloadAdapter = new DownloadAdapter(null, mViewModel, this, "Artist");
                adapterList = LastfmAPI.getArtists(text);
            }
            return adapterList;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnNext(data -> {
            downloadAdapter.setList((List<?>)data);
            rcy.setAdapter(downloadAdapter);
        }).subscribe();

    }
}