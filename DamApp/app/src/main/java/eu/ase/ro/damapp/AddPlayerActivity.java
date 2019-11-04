package eu.ase.ro.damapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import eu.ase.ro.damapp.util.Player;

public class AddPlayerActivity extends AppCompatActivity {
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String ADD_PLAYER_KEY = "addPlayerKey";
    public static final int REQUEST_CODE_ADD_PLAYER = 200;
    TextInputEditText etName;
    TextInputEditText etBirthday;
    TextInputEditText etNumber;
    Spinner spnPositions;
    RadioGroup rgFavHand;
    Button btnSave;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);
        initComponents();
        intent = getIntent();
    }

    private void initComponents() {
        etName = findViewById(R.id.add_player_et_name);
        etBirthday = findViewById(R.id.add_player_et_birthday);
        etNumber = findViewById(R.id.add_player_et_number);
        rgFavHand = findViewById(R.id.add_player_rg_fav_hand);
        btnSave = findViewById(R.id.add_player_btn_save);

        spnPositions = findViewById(R.id.add_player_spn_positions);
        //adapter
        ArrayAdapter<CharSequence> positionsAdapter =
                ArrayAdapter.createFromResource(getApplicationContext(), R.array.add_player_positions,
                        R.layout.support_simple_spinner_dropdown_item);
        spnPositions.setAdapter(positionsAdapter);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validare
                if (validate()) {
                    //construire obiect
                    Player player = createPlayerFromView();
                    Toast.makeText(getApplicationContext(),
                            player.toString(),
                            Toast.LENGTH_LONG)
                            .show();
                    //transfer parametru
                    intent.putExtra(ADD_PLAYER_KEY, player);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_PLAYER
                && resultCode == RESULT_OK
                && data != null) {
            Player player = data.getParcelableExtra(AddPlayerActivity
                    .ADD_PLAYER_KEY);
            if (player != null) {
                Toast.makeText(getApplicationContext(),
                        player.toString(),
                        Toast.LENGTH_LONG).show();
//                addName(player.getName());
//                TextView textView = view.findViewById(R.id.lv_players_row_tv_name);
//        if (name != null && !name.trim().isEmpty()) {
//            textView.setText(name);
//        } else {
//            textView.setText(R.string.player_adapter_no_content);
//        }
                System.out.println("hello from the other side" + player);
                TextView textView = findViewById(R.id.add_player_et_name);
                textView.setText("ura");
            }
        }
    }


    private Player createPlayerFromView() {
        String name = etName.getText().toString();
        Date birthday = null;
        try {
            birthday = new SimpleDateFormat(DATE_FORMAT, Locale.US)
                    .parse(etBirthday.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Integer number = Integer.parseInt(etNumber.getText()
                .toString());
        String position = spnPositions.getSelectedItem().toString();
        RadioButton selectedHand =
                findViewById(rgFavHand.getCheckedRadioButtonId());
        String favHand = selectedHand.getText().toString();

        return new Player(name, birthday, number, position, favHand);
    }

    private boolean validate() {
        if (etName.getText() == null
                || etName.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    R.string.add_player_name_error_message,
                    Toast.LENGTH_LONG)
                    .show();
            return false;
        }

        if (etNumber.getText() == null
                || etNumber.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    R.string.add_player_number_error_message,
                    Toast.LENGTH_LONG)
                    .show();
            return false;
        }

        if (etBirthday.getText() == null
                || etBirthday.getText().toString()
                .trim().isEmpty()
                || !validateDate(etBirthday.
                getText().toString())) {
            Toast.makeText(getApplicationContext(),
                    R.string.add_player_birthday_error_message,
                    Toast.LENGTH_LONG)
                    .show();
            return false;
        }

        return true;
    }

    private boolean validateDate(String strDate) {
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(DATE_FORMAT, Locale.US);
        try {
            simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
