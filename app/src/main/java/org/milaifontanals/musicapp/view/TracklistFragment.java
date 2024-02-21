package org.milaifontanals.musicapp.view;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
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
import org.milaifontanals.musicapp.model.Track;
import org.milaifontanals.musicapp.viewmodel.AlbumsViewModel;

public class TracklistFragment extends Fragment {

    private FragmentTracklistBinding binding;
    private AlbumsViewModel mViewModel;
    private TrackAdapter trackAdapter;
    private Album currentAlbum;


    public TracklistFragment() {
        // Required empty public constructor
    }


    public static TracklistFragment newInstance(int id) {
        TracklistFragment fragment = new TracklistFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(AlbumsViewModel.class);
        currentAlbum = mViewModel.getCurrentAlbum();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTracklistBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();

        ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(v.getContext()).denyCacheImageMultipleSizesInMemory().diskCacheSize(2048).tasksProcessingOrder(QueueProcessingType.LIFO).defaultDisplayImageOptions(DisplayImageOptions.createSimple()).build();
        ImageLoader.getInstance().init(conf);

        /*
         * Info del album
         * */
        binding.albumArtistTracklist.setText(currentAlbum.getArtist());
        binding.albumTitleTracklist.setText(currentAlbum.getTitle());
        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(currentAlbum.getImgSrc(), (ImageView) binding.albumImgTracklist);

        /*
         * Tracklist
         * */

      /*Lo del boton patras ya no va con fragment
        setSupportActionBar(findViewById(R.id.tracklistToolbar));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        trackAdapter = new TrackAdapter(currentAlbum.getTrackList(), this, mViewModel);
        trackAdapter.getTrackList().sort((t1, t2) -> t1.getNumber() - t2.getNumber());
        RecyclerView rcyTracks = binding.rcyTracklist;
        rcyTracks.setLayoutManager(new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false));
        rcyTracks.setHasFixedSize(true);
        rcyTracks.setAdapter(trackAdapter);

        mViewModel.setTrackAdapter(trackAdapter);

        binding.fab.setOnClickListener(e -> {
            mViewModel.setCurrentTrack(null);
            NavDirections n = TracklistFragmentDirections.actionTracklistFragmentToTrackEditDialogFragment();
            NavController nav = NavHostFragment.findNavController(this);
            nav.navigate(n);

        });

        binding.btnEditTrack.setOnClickListener(e -> {
            if (trackAdapter.getSelectedIndex() != -1) {
                NavDirections n = TracklistFragmentDirections.actionTracklistFragmentToTrackEditDialogFragment();
                NavController nav = NavHostFragment.findNavController(this);
                nav.navigate(n);
            }
        });

        binding.btnRmTrack.setOnClickListener(e -> {

            Track selectedTrack = mViewModel.getCurrentTrack();
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setCancelable(true);
            builder.setTitle("Delete track");
            builder.setMessage("Are you sure you want to delete " + selectedTrack.getTitle() + "?");
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    trackAdapter.getTrackList().remove(selectedTrack);
                    mViewModel.deleteTrack(selectedTrack);

                    trackAdapter.notifyItemRemoved(trackAdapter.getSelectedIndex());
                    trackAdapter.setSelectedIndex(-1);

                    binding.trackToolbar.animate().alpha(0f).withEndAction(() -> {
                        if (binding.trackToolbar != null)
                            binding.trackToolbar.setVisibility(View.INVISIBLE);
                    });
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        return v;
    }
}