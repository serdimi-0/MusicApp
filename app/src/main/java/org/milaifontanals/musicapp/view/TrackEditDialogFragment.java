package org.milaifontanals.musicapp.view;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.milaifontanals.musicapp.R;
import org.milaifontanals.musicapp.model.Track;
import org.milaifontanals.musicapp.viewmodel.AlbumsViewModel;

public class TrackEditDialogFragment extends DialogFragment {


    AlbumsViewModel mViewModel;
    Track currentTrack;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(AlbumsViewModel.class);
        currentTrack = mViewModel.getCurrentTrack();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_track,
                container, false);

        NumberPicker minutePicker = v.findViewById(R.id.minuteSpinner);
        NumberPicker secondPicker = v.findViewById(R.id.secondSpinner);
        EditText title = v.findViewById(R.id.edtTitle);
        TextView number = v.findViewById(R.id.txtTrackNumber);
        Button save = v.findViewById(R.id.btnSave);
        Button cancel = v.findViewById(R.id.btnCancel);
        ImageButton up = v.findViewById(R.id.btnUp);
        ImageButton down = v.findViewById(R.id.btnDown);


        minutePicker.setMaxValue(59);
        minutePicker.setMinValue(0);
        secondPicker.setMaxValue(59);
        secondPicker.setMinValue(0);

        if(currentTrack!=null){
            minutePicker.setValue(currentTrack.getDuration() / 60000);
            secondPicker.setValue((currentTrack.getDuration() % 60000) / 1000);
            title.setText(currentTrack.getTitle());
            number.setText(String.valueOf(currentTrack.getNumber()));
        }

        up.setOnClickListener(e -> {
            int n = Integer.parseInt(number.getText().toString());
            number.setText(String.valueOf(n+1));
        });
        down.setOnClickListener(e -> {
            int n = Integer.parseInt(number.getText().toString());
            number.setText(String.valueOf(n-1));
        });

        cancel.setOnClickListener(e -> dismiss());


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setWidthPercent(80);
    }

    public void setWidthPercent(int percentage) {
        float percent = (float) percentage / 100;
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        Rect rect = new Rect(0, 0, dm.widthPixels, dm.heightPixels);
        float percentWidth = rect.width() * percent;
        this.getDialog().getWindow().setLayout((int) percentWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}