package org.milaifontanals.musicappnofragments.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.milaifontanals.musicappnofragments.EditAlbumActivity;
import org.milaifontanals.musicappnofragments.GridSpacingItemDecoration;
import org.milaifontanals.musicappnofragments.R;
import org.milaifontanals.musicappnofragments.adapter.AlbumAdapter;
import org.milaifontanals.musicappnofragments.databinding.FragmentAlbumListBinding;
import org.milaifontanals.musicappnofragments.model.Album;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumListFragment extends Fragment {

    private AlbumAdapter albumAdapter;
    private FragmentAlbumListBinding binding;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*View v = inflater.inflate(R.layout.fragment_album_list, container, false);*/
        binding = FragmentAlbumListBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();

        ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(requireContext())
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(2048)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .build();
        ImageLoader.getInstance().init(conf);

        Album.generateAlbumList();

        albumAdapter = new AlbumAdapter(Album.getAlbumList(), requireContext());
        RecyclerView rcyAlbums = binding.rcyAlbums;
        rcyAlbums.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        rcyAlbums.addItemDecoration(new GridSpacingItemDecoration(2, 50, true));
        rcyAlbums.setHasFixedSize(true);
        rcyAlbums.setAdapter(albumAdapter);

        binding.btnEditAlbum.setOnClickListener(e -> {

            Handler handler = new Handler(Looper.getMainLooper());

            Album selectedAlbum = albumAdapter.getList().get(albumAdapter.getSelectedIndex());
            Intent i = new Intent(requireContext(), EditAlbumActivity.class);
            i.putExtra("albumId", selectedAlbum.getId());
            this.startActivity(i);

            // Timeout para que no desaparezca la toolbar antes de llegar al Intent
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
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Album.getAlbumList().remove(selectedAlbum);

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

        return v;
    }
}