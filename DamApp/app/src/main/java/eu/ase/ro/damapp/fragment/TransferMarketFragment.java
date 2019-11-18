package eu.ase.ro.damapp.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.damapp.R;
import eu.ase.ro.damapp.network.HttpManager;
import eu.ase.ro.damapp.network.HttpResponse;
import eu.ase.ro.damapp.network.Item;
import eu.ase.ro.damapp.network.JsonParser;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransferMarketFragment extends Fragment {
    private static final String URL = "http://api.myjson.com/bins/16f54z";
    private HttpResponse httpResponse;

    private Button btnGoalkeeper;
    private Button btnCenter;
    private Button btnInter;
    private Button btnWinger;

    private ListView lvResponse;
    private List<Item> selectedResponse = new ArrayList<>();


    public TransferMarketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transfer_market, container, false);

        initComponents(view);

        new HttpManager() {
            @Override
            protected void onPostExecute(String s) {
                httpResponse = JsonParser.parseJson(s);
                if (httpResponse != null) {
                    Toast.makeText(getContext(),  httpResponse.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }.execute(URL);


        return view;
    }

    private void initComponents(View view) {
        btnGoalkeeper = view.findViewById(R.id.transfer_market_btn_goalkeeper);
        btnCenter = view.findViewById(R.id.transfer_market_btn_center);
        btnInter = view.findViewById(R.id.transfer_market_btn_inter);
        btnWinger = view.findViewById(R.id.transfer_market_btn_winger);

        lvResponse = view.findViewById(R.id.transfer_market_lv_response);

        ArrayAdapter<Item> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, selectedResponse);
        //atasare adapter

        lvResponse.setAdapter(adapter);
        unSelectedButtons();

        //btn goalkeeper wvent

        btnGoalkeeper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (httpResponse != null && httpResponse.getGoalkeeper() != null) {
                    //adaugare pe listview
                    unSelectedButtons();
                    selectButtonColor(btnGoalkeeper);
                    selectResponse(httpResponse.getGoalkeeper());
                }

            }
        });

        btnCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (httpResponse != null && httpResponse.getCenter() != null) {
                    unSelectedButtons();
                    selectButtonColor(btnCenter);
                    selectResponse(httpResponse.getCenter());
                }
            }
        });

        btnInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (httpResponse != null && httpResponse.getInter() != null) {
                    unSelectedButtons();
                    selectButtonColor(btnInter);
                    selectResponse(httpResponse.getInter());
                }
            }
        });

        btnWinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (httpResponse != null && httpResponse.getWinger() != null) {
                    unSelectedButtons();
                    selectButtonColor(btnWinger);
                    selectResponse(httpResponse.getWinger());
                }
            }
        });
    }

    private void selectResponse(List<Item> list) {
        selectedResponse.clear();
        selectedResponse.addAll(list);
        ArrayAdapter<Item> adapter = (ArrayAdapter<Item>) lvResponse.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private void unSelectedButtons(){
        btnGoalkeeper.setBackgroundColor(Color.GRAY);
        btnWinger.setBackgroundColor(Color.GRAY);
        btnInter.setBackgroundColor(Color.GRAY);
        btnCenter.setBackgroundColor(Color.GRAY);
    }

    private void selectButtonColor(Button button){
        button.setBackgroundColor(Color.GREEN);
    }
}
