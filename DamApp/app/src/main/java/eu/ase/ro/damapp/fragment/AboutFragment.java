package eu.ase.ro.damapp.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.fragment.app.Fragment;

import eu.ase.ro.damapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {
    private static final String SHARED_PREF_NAME = "sharedPrefName";
    private static final String RATING_BAR_SHARED_KEY = "ratingBarSharedKey";

    private RatingBar rbFeedback;
    private SharedPreferences preferences;

    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        rbFeedback = view.findViewById(R.id.about_rb_feedback);

        if (getActivity() != null) {
            preferences = getActivity()
                    .getSharedPreferences(
                            SHARED_PREF_NAME,
                            Context.MODE_PRIVATE);
        }

        //citire din fisier

        float value = preferences.getFloat(RATING_BAR_SHARED_KEY, -1);
        if (value != -1) {
            rbFeedback.setRating(value);
        }
        rbFeedback.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float value, boolean fromUser) {

                //salvare fisier referinta
                SharedPreferences.Editor editor = preferences.edit();
                editor.putFloat(RATING_BAR_SHARED_KEY, value);
                editor.apply();
            }
        });
    }
}
