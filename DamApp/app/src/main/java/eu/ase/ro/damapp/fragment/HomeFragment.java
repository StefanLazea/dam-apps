package eu.ase.ro.damapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

import eu.ase.ro.damapp.AddPlayerActivity;
import eu.ase.ro.damapp.R;
import eu.ase.ro.damapp.util.Player;
import eu.ase.ro.damapp.util.PlayerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    public static final String PLAYERS_KEY = "playersKey";
    public static int PLAYER_MODIFY_POSITION;
    ListView lvPlayers;
    List<Player> players = new ArrayList<>();
    Intent intent;

    public HomeFragment() {
        // Required empty public constructor
    }

    public void notifyInternal(){
        PlayerAdapter playerAdapter =
                (PlayerAdapter)lvPlayers.getAdapter();
        playerAdapter.notifyDataSetChanged();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater
                .inflate(R.layout.fragment_home, container,
                        false);
        initComponents(view);
        intent = getActivity().getIntent();
        return view;
    }

    private void initComponents(View view) {
        lvPlayers = view.findViewById(R.id.home_lv_players);
        //preiau lista de jucatori
        if (getArguments() != null) {
            players = getArguments().getParcelableArrayList(PLAYERS_KEY);
        }
        //adaugare adapter pe listview
        if (getContext() != null) {
            PlayerAdapter adapter = new PlayerAdapter(getContext(),
                    R.layout.lv_row_view,
                    players,
                    getLayoutInflater());
            lvPlayers.setAdapter(adapter);


            lvPlayers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Player player = (Player)lvPlayers.getItemAtPosition(position);
                    System.out.println("boule" + player);
                    PLAYER_MODIFY_POSITION = position;
                    intent.putExtra(AddPlayerActivity.ADD_PLAYER_KEY, player);
                    startActivityForResult(intent, AddPlayerActivity.RESULT_OK);
                    onDestroy();
                }
            });
        }


    }

}
