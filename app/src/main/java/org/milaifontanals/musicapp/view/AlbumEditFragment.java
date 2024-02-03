package org.milaifontanals.musicapp.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import org.milaifontanals.musicapp.R;
import org.milaifontanals.musicapp.databinding.FragmentAlbumEditBinding;
import org.milaifontanals.musicapp.model.Album;
import org.milaifontanals.musicapp.viewmodel.AlbumsViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumEditFragment extends Fragment {

    private FragmentAlbumEditBinding binding;
    private AlbumsViewModel mViewModel;
    private Album currentAlbum;

    public AlbumEditFragment() {
        // Required empty public constructor
    }

    public static AlbumEditFragment newInstance() {
        AlbumEditFragment fragment = new AlbumEditFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(AlbumsViewModel.class);
        currentAlbum = mViewModel.getCurrentAlbum();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAlbumEditBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();


        binding.albumEditImg.setImageBitmap(currentAlbum.getImgBitmap());
        binding.albumEditName.setText(currentAlbum.getTitle());
        binding.albumEditArtist.setText(currentAlbum.getArtist());


        /*setSupportActionBar(findViewById(R.id.albumEditToolbar));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        return v;
    }
}