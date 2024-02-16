package org.milaifontanals.musicapp.view;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.milaifontanals.musicapp.R;
import org.milaifontanals.musicapp.databinding.FragmentAlbumEditBinding;
import org.milaifontanals.musicapp.model.Album;
import org.milaifontanals.musicapp.viewmodel.AlbumsViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumEditFragment extends Fragment {

    private FragmentAlbumEditBinding binding;
    private AlbumsViewModel mViewModel;
    private Album currentAlbum;
    private Calendar currentDate;

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
        currentDate = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        mViewModel = new ViewModelProvider(requireActivity()).get(AlbumsViewModel.class);
        currentAlbum = mViewModel.getCurrentAlbum();
        if (currentAlbum == null) {
            currentDate.setTime(new Date());
        } else {
            currentDate.setTime(currentAlbum.getReleaseDate());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAlbumEditBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(v.getContext())
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(2048)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .build();
        ImageLoader.getInstance().init(conf);

        ImageLoader loader = ImageLoader.getInstance();
        if (currentAlbum != null) {
            loader.displayImage(currentAlbum.getImgSrc(), (ImageView) binding.albumEditImg);
            binding.albumEditName.setText(currentAlbum.getTitle());
            binding.albumEditArtist.setText(currentAlbum.getArtist());
            binding.albumEditDate.setText(sdf.format(currentAlbum.getReleaseDate()));
            Log.d("AlbumEditFragment", "onCreateView: " + currentDate);
        }
        binding.datePickerBtn.setOnClickListener(e -> {

            DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), R.style.MySpinnerDatePickerStyle, (view, year, month, dayOfMonth) -> {
                binding.albumEditDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                binding.albumEditSave.setVisibility(View.VISIBLE);
                try {
                    currentDate.setTime(sdf.parse(dayOfMonth + "/" + (month + 1) + "/" + year));
                } catch (ParseException ex) {
                }
            }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.show();
            Button positiveButton = datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE);
            positiveButton.setTextColor(ContextCompat.getColor(v.getContext(), android.R.color.black));

            Button negativeButton = datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE);
            negativeButton.setTextColor(ContextCompat.getColor(v.getContext(), android.R.color.black));
        });

        binding.albumEditName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (currentAlbum != null) {
                    if (!binding.albumEditName.getText().toString().equals(currentAlbum.getTitle()) &&
                            !binding.albumEditName.getText().toString().isEmpty()) {
                        binding.albumEditSave.setVisibility(View.VISIBLE);
                    } else {
                        if (binding.albumEditName.getText().toString().isEmpty()) {
                            binding.albumEditName.setError("Album name is required");
                        }
                        binding.albumEditSave.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        binding.albumEditArtist.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (currentAlbum != null) {
                    if (!binding.albumEditArtist.getText().toString().equals(currentAlbum.getArtist()) &&
                            !binding.albumEditArtist.getText().toString().isEmpty()) {
                        binding.albumEditSave.setVisibility(View.VISIBLE);
                    } else {
                        if (binding.albumEditArtist.getText().toString().isEmpty()) {
                            binding.albumEditArtist.setError("Album artist is required");
                        }
                        binding.albumEditSave.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });


        binding.albumEditSave.setOnClickListener(e -> {
            if (currentAlbum != null) {
                currentAlbum.setTitle(binding.albumEditName.getText().toString());
                currentAlbum.setArtist(binding.albumEditArtist.getText().toString());
                currentAlbum.setReleaseDate(currentDate.getTime());
                mViewModel.updateAlbum(currentAlbum);
                binding.albumEditSave.setVisibility(View.INVISIBLE);
            } else{
                Album newAlbum = new Album(generateId(),binding.albumEditName.getText().toString(),
                        binding.albumEditArtist.getText().toString(),
                        currentDate.getTime());
                mViewModel.insertAlbum(newAlbum);
                binding.albumEditSave.setVisibility(View.INVISIBLE);

            }
        });

        /*setSupportActionBar(findViewById(R.id.albumEditToolbar));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        return v;
    }

    private String generateId() {
        return String.valueOf(System.currentTimeMillis() + (Math.random() * 100));
    }
}