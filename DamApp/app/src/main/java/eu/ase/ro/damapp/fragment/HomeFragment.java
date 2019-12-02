package eu.ase.ro.damapp.fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.damapp.AddPlayerActivity;
import eu.ase.ro.damapp.R;
import eu.ase.ro.damapp.database.model.Player;
import eu.ase.ro.damapp.database.service.PlayerService;
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
            lvPlayers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent,
                                               View view,
                                               final int position,
                                               long id
                ) {
                    buildAlertDialog(position);
                    return true;
                }
            });
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
                updatePlayerIntoDb(player);
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

    @SuppressLint("StaticFieldLeak")
    private void updatePlayerIntoDb(final Player player) {
        player.setId(players.get(selectedPlayerIndex).getId());
        new PlayerService.Update(getContext()) {
            @Override
            protected void onPostExecute(Integer result) {
                if (result == 1) {
                    updatePlayer(player);
                    PlayerAdapter adapter = (PlayerAdapter) lvPlayers.getAdapter();
                    adapter.notifyDataSetChanged();
                }
            }
        }.execute(player);
    }

    @SuppressLint("StaticFieldLeak")
    private void  deletePlayerFromDb(final int index){
        new PlayerService.Delete(getContext()){
            @Override
            protected void onPostExecute(Integer result) {
                if(result == 1){
                    players.remove((index));
                    notifyInternal();
                }else{
                    Toast.makeText(getContext(), R.string.home_delete_not_succeded, Toast.LENGTH_LONG).show();
                }
            }
        }.execute(players.get(index));
    }

    private void buildAlertDialog(final int position){
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle(R.string.home_fragment_alert_title)
                .setMessage(getString(R.string.home_fragment_alert_dialog_message,
                        players.get(position).getName()))
                .setPositiveButton(R.string.home_fragment_alert_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletePlayerFromDb(position);
                    }
                })
                .setNegativeButton(R.string.home_fragment_alert_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), R.string.home_fragment_cancel_message, Toast.LENGTH_LONG).show();
                    }
                }).create();
        dialog.show();
    }
}
