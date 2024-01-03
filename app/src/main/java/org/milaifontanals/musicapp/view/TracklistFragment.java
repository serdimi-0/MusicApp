package org.milaifontanals.musicapp.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.milaifontanals.musicapp.R;
import org.milaifontanals.musicapp.adapter.TrackAdapter;
import org.milaifontanals.musicapp.databinding.FragmentAlbumListBinding;
import org.milaifontanals.musicapp.databinding.FragmentTracklistBinding;
import org.milaifontanals.musicapp.model.Album;
import org.milaifontanals.musicapp.viewmodel.AlbumsViewModel;

public class TracklistFragment extends Fragment {

    private static final String ARG_ID = "id";
    private FragmentTracklistBinding binding;
    private AlbumsViewModel mViewModel;
    private TrackAdapter trackAdapter;
    private Album currentAlbum;

    private int mId;

    public TracklistFragment() {
        // Required empty public constructor
    }


    public static TracklistFragment newInstance(int id) {
        TracklistFragment fragment = new TracklistFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(AlbumsViewModel.class);
        if (getArguments() != null) {
            mId = getArguments().getInt(ARG_ID);
            Log.d("TAG",""+mId);
            currentAlbum = mViewModel.getSavedAlbums().stream().filter(obj -> obj.getId() == mId).findFirst().orElse(null);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTracklistBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();

        ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(v.getContext())
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(2048)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .build();
        ImageLoader.getInstance().init(conf);

        /*
         * Info del album
         * */
        binding.albumArtistTracklist.setText(currentAlbum.getArtist());
        binding.albumTitleTracklist.setText(currentAlbum.getTitle());
        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(currentAlbum.getImgSrc(),(ImageView) binding.albumImgTracklist);

        /*
         * Tracklist
         * */

     /* Lo del boton patras ya no va con fragment
        setSupportActionBar(findViewById(R.id.tracklistToolbar));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        trackAdapter = new TrackAdapter(currentAlbum.getTrackList(),v.getContext());
        RecyclerView rcyTracks = binding.rcyTracklist;
        rcyTracks.setLayoutManager(new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false));
        rcyTracks.setHasFixedSize(true);
        rcyTracks.setAdapter(trackAdapter);

        return v;
    }
}