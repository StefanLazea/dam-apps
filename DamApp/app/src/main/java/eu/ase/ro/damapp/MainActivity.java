package eu.ase.ro.damapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.damapp.database.model.Player;
import eu.ase.ro.damapp.database.service.PlayerService;
import eu.ase.ro.damapp.fragment.AboutFragment;
import eu.ase.ro.damapp.fragment.HomeFragment;
import eu.ase.ro.damapp.fragment.TransferMarketFragment;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_ADD_PLAYER = 200;
    DrawerLayout drawerLayout;
    FloatingActionButton fabAddPlayer;
    NavigationView navigationView;
    Fragment currentFragment;
    ArrayList<Player> players = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configNavigation();
        initComponents();
        getPlayersFromDb();
        openDefaultFragment(savedInstanceState);
    }

    private void openDefaultFragment(Bundle savedInstanceState) {
        //savedInstanceState este null daca s-a deschis prima data activitate. La back nu este null
        if (savedInstanceState == null) {
            currentFragment = createHomeFragment();
            openFragment();
            navigationView.setCheckedItem(R.id.main_nav_home);
        }
    }

    private void initComponents() {
        //asociere componenta java corespondent UI
        fabAddPlayer = findViewById(R.id.main_fab_add_player);
        fabAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =
                        new Intent(getApplicationContext(),
                                AddPlayerActivity.class);
                //startActivity(intent);
                startActivityForResult(intent,
                        REQUEST_CODE_ADD_PLAYER);
            }
        });

        //asociere navigationView
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(navigationItemEvent());
    }

    private NavigationView
            .OnNavigationItemSelectedListener navigationItemEvent() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(
                    @NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.main_nav_home) {
                    //optiune acasa
                    currentFragment = createHomeFragment();
                } else if (menuItem.getItemId() == R.id.main_nav_transfer_market) {
                    //optiune Transfer Market
                    currentFragment = new TransferMarketFragment();
                } else {
                    //optiunea despre
                    currentFragment = new AboutFragment();
                }
                //adaugare fragment
                openFragment();
                //inchidere meniu lateral
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        };
    }

    private void openFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame_container, currentFragment)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    @Nullable Intent data) {
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
                insertPlayerIntoDb(player);
            }
        }
    }

    private Fragment createHomeFragment() {
        Fragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(HomeFragment.PLAYERS_KEY, players);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void configNavigation() {
        //initializare toolbar - bara de actiune
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //initializare drawer layout - panou meniu lateral
        drawerLayout = findViewById(R.id.drawer_layout);
        //legare meniu lateral cu bara actiune
        // + eveniment de deschidere
        //creare instanta utilitara
        ActionBarDrawerToggle actionBar =
                new ActionBarDrawerToggle(
                        this,
                        drawerLayout,
                        toolbar,
                        R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close);
        //atasare eveniment
        drawerLayout.addDrawerListener(actionBar);
        //sincronizare actionBartoggle
        actionBar.syncState();
    }


    @SuppressLint("StaticFieldLeak")
    private void insertPlayerIntoDb(Player player) {
        new PlayerService.Insert(getApplicationContext()) {

            @Override
            protected void onPostExecute(Player result) {
                if (result != null) {
                    players.add(result);
                    notifyAdapter();
                }
            }
        }.execute(player);
    }

    @SuppressLint("StaticFieldLeak")
    private void getPlayersFromDb() {
        new PlayerService.GetAll(getApplicationContext()) {
            @Override
            protected void onPostExecute(List<Player> results) {
                if (results != null) {
                    players.clear();
                    players.addAll(results);
                    notifyAdapter();
                }
            }
        }.execute();
    }

    private void notifyAdapter() {
        if (currentFragment instanceof HomeFragment) {
            ((HomeFragment) currentFragment).notifyInternal();
        }
    }
}
