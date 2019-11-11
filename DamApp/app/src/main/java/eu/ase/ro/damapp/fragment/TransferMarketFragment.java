package eu.ase.ro.damapp.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import eu.ase.ro.damapp.R;
import eu.ase.ro.damapp.network.HttpManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransferMarketFragment extends Fragment {
    private static final String URL = "http://api.myjson.com/bins/16f54z";

    public TransferMarketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transfer_market, container, false);

        new HttpManager() {
            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
            }
        }.execute(URL);

        return view;
    }

}
