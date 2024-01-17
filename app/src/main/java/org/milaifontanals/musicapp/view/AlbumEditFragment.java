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

    private static final String ARG_ID = "id";

    private FragmentAlbumEditBinding binding;
    private long mId;
    private AlbumsViewModel mViewModel;
    private Album currentAlbum;

    public AlbumEditFragment() {
        // Required empty public constructor
    }

    public static AlbumEditFragment newInstance(String param1, String param2) {
        AlbumEditFragment fragment = new AlbumEditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(AlbumsViewModel.class);
        if (getArguments() != null) {
            mId = getArguments().getLong(ARG_ID);
            currentAlbum = mViewModel.getSavedAlbums().stream().filter(obj -> obj.getId() == mId).findFirst().orElse(null);
        }
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