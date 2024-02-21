package org.milaifontanals.musicapp.view;

import android.content.DialogInterface;
import android.database.Observable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.milaifontanals.musicapp.utils.GridSpacingItemDecoration;
import org.milaifontanals.musicapp.adapter.AlbumAdapter;
import org.milaifontanals.musicapp.databinding.FragmentAlbumListBinding;
import org.milaifontanals.musicapp.model.Album;
import org.milaifontanals.musicapp.viewmodel.AlbumsViewModel;

import java.util.List;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class AlbumListFragment extends Fragment {

    private AlbumAdapter albumAdapter;
    private FragmentAlbumListBinding binding;
    private AlbumsViewModel mViewModel;
    private NavController navController;

    public AlbumListFragment() {
        // Required empty public constructor
    }


    public static AlbumListFragment newInstance(String param1, String param2) {
        AlbumListFragment fragment = new AlbumListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(AlbumsViewModel.class);
        /*mViewModel.initialInserts();*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAlbumListBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();

        ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(requireContext()).denyCacheImageMultipleSizesInMemory().diskCacheSize(2048).tasksProcessingOrder(QueueProcessingType.LIFO).defaultDisplayImageOptions(DisplayImageOptions.createSimple()).build();
        ImageLoader.getInstance().init(conf);


        getAlbums();
        RecyclerView rcyAlbums = binding.rcyAlbums;
        rcyAlbums.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        rcyAlbums.addItemDecoration(new GridSpacingItemDecoration(2, 50, true));
        rcyAlbums.setHasFixedSize(true);

        binding.btnEditAlbum.setOnClickListener(e -> {

            Handler handler = new Handler(Looper.getMainLooper());

            Album selectedAlbum = albumAdapter.getList().get(albumAdapter.getSelectedIndex());

            NavDirections n = AlbumListFragmentDirections.actionAlbumListFragmentToAlbumEditFragment();
            NavController nav = NavHostFragment.findNavController(this);
            nav.navigate(n);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    albumAdapter.notifyItemChanged(albumAdapter.getSelectedIndex());
                    albumAdapter.setSelectedIndex(-1);
                    binding.albumToolbar.setVisibility(View.INVISIBLE);
                }
            }, 1000);

        });

        binding.btnRmAlbum.setOnClickListener(e -> {

            Album selectedAlbum = albumAdapter.getList().get(albumAdapter.getSelectedIndex());

            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setCancelable(true);
            builder.setTitle("Delete album");
            builder.setMessage("Are you sure you want to delete " + selectedAlbum.getTitle() + "?");
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    albumAdapter.getList().remove(selectedAlbum);
                    mViewModel.deleteAlbum(selectedAlbum);

                    albumAdapter.notifyItemRemoved(albumAdapter.getSelectedIndex());
                    albumAdapter.setSelectedIndex(-1);

                    binding.albumToolbar.animate().alpha(0f).withEndAction(() -> {
                        if (binding.albumToolbar != null)
                            binding.albumToolbar.setVisibility(View.INVISIBLE);
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

        binding.fab.setOnClickListener(e -> {
            NavController nav = NavHostFragment.findNavController(this);
            mViewModel.setCurrentAlbum(null);
            nav.navigate(AlbumListFragmentDirections.actionAlbumListFragmentToAlbumEditFragment());
        });

        return v;
    }

    private void getAlbums() {

        LiveData<List<Album>> users = mViewModel.getSavedAlbums();
        users.observe(getViewLifecycleOwner(), albums -> {

            albumAdapter = new AlbumAdapter(albums, mViewModel,this, (AppCompatActivity) getActivity());
            binding.rcyAlbums.setAdapter(albumAdapter);
        });
    }
}