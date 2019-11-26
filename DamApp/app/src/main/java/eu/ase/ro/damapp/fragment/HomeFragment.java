package eu.ase.ro.damapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.damapp.AddPlayerActivity;
import eu.ase.ro.damapp.R;
import eu.ase.ro.damapp.database.model.Player;
import eu.ase.ro.damapp.util.PlayerAdapter;

import static android.app.Activity.RESULT_OK;
import static eu.ase.ro.damapp.AddPlayerActivity.ADD_PLAYER_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    public static final String PLAYERS_KEY = "playersKey";
    private static final int REQUEST_CODE_UPDATE_PLAYER = 201;
    private int selectedPlayerIndex;
    ListView lvPlayers;
    List<Player> players = new ArrayList<>();
    Intent intent;

    public HomeFragment() {
        // Required empty public constructor
    }

    public void notifyInternal() {
        PlayerAdapter playerAdapter =
                (PlayerAdapter) lvPlayers.getAdapter();
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
//        intent = getActivity().getIntent();
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


            lvPlayers.setOnItemClickListener(lvPlayersItemSelected());
        }

    }

    private AdapterView.OnItemClickListener lvPlayersItemSelected() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),
                        AddPlayerActivity.class);
                selectedPlayerIndex = position;
                intent.putExtra(AddPlayerActivity.ADD_PLAYER_KEY, players.get(position));
                startActivityForResult(intent, REQUEST_CODE_UPDATE_PLAYER);
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_UPDATE_PLAYER && resultCode == RESULT_OK
                && data != null) {
            Player player = data.getParcelableExtra(ADD_PLAYER_KEY);
            if (player != null) {
                updatePlayer(player);
                PlayerAdapter adapter = (PlayerAdapter) lvPlayers.getAdapter();
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void updatePlayer(Player player) {
        players.get(selectedPlayerIndex).setName(player.getName());
        players.get(selectedPlayerIndex).setBirthday(player.getBirthday());
        players.get(selectedPlayerIndex).setPosition(player.getPosition());
        players.get(selectedPlayerIndex).setNumber(player.getNumber());
        players.get(selectedPlayerIndex).setFavHand(player.getFavHand());

    }
}
