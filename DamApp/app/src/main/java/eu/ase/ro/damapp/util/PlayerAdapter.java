package eu.ase.ro.damapp.util;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import eu.ase.ro.damapp.AddPlayerActivity;
import eu.ase.ro.damapp.R;

public class PlayerAdapter extends ArrayAdapter<Player> {
    private Context context;
    private int resource;
    private List<Player> players;
    private LayoutInflater layoutInflater;

    public PlayerAdapter(@NonNull Context context,
                         int resource,
                         List<Player> players,
                         LayoutInflater layoutInflater

    ) {
        //sa i spun si sursa de date
        super(context, resource, players);
        this.context = context;
        this.resource = resource;
        this.players = players;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {


        //definim instanta Java pentru layout-ul creat
        //inflater
        //parent - ptr ca il folosim la nivelul listview ului folosit
        //ultima; pentru alte tipuri de afisare
        View view = layoutInflater.inflate(resource, parent, false);

        //identificare obiect
        Player player = players.get(position);
        if (player != null) {
            //adaugare informatii pe ecran
            addName(view, player.getName());
            addBirthday(view, player.getBirthday());
            addFavHand(view, player.getFavHand());
            addNumber(view, player.getNumber());
            addPostition(view, player.getPosition());
        }

        return view;
        // return super.getView(position, convertView,
        //             parent);
    }

    private void addName(View view, String name) {
        TextView textView = view.findViewById(R.id.lv_players_row_tv_name);
        if (name != null && !name.trim().isEmpty()) {
            textView.setText(name);
        } else {
            textView.setText(R.string.player_adapter_no_content);
        }
    }

    private void addBirthday(View view, Date birthday) {
        TextView textView = view.findViewById(R.id.lv_players_row_tv_birthday);

        if (birthday != null) {
            textView.setText(new SimpleDateFormat(AddPlayerActivity.DATE_FORMAT, Locale.ENGLISH).format(birthday));
        } else {
            textView.setText((R.string.player_adapter_no_content));
        }
    }

    //mereu sa fie convertit la string, pentru ca altfel o sa l considere ca o resursa
    private void addNumber(View view, Integer number) {
        TextView textView = view.findViewById(R.id.lv_players_row_tv_number);

        if (number != null) {
            textView.setText(String.valueOf(number));
        } else {
            textView.setText((R.string.player_adapter_no_content));
        }
    }

    private void addPostition(View view, String position) {
        TextView textView = view.findViewById(R.id.lv_players_row_tv_position);
        if (position != null && !position.trim().isEmpty()) {
            textView.setText(position);
        } else {
            textView.setText(R.string.player_adapter_no_content);
        }
    }

    private void addFavHand(View view, String favHand) {
        TextView textView = view.findViewById(R.id.lv_players_row_tv_fav_hand);
        if (favHand != null && !favHand.trim().isEmpty()) {
            textView.setText(favHand);
        } else {
            textView.setText(R.string.player_adapter_no_content);
        }
    }

}
