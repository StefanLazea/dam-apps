package eu.ase.ro.damapp.database.service;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import eu.ase.ro.damapp.database.DatabaseManager;
import eu.ase.ro.damapp.database.dao.PlayerDao;
import eu.ase.ro.damapp.database.model.Player;

public class PlayerService {
    private static PlayerDao playerDao;

    public static class GetAll extends AsyncTask<Void, Void, List<Player>> {

        public GetAll(Context context) {
            playerDao = DatabaseManager
                    .getInstance(context)
                    .getPlayerDao();
        }

        @Override
        protected List<Player> doInBackground(Void... voids) {
            return playerDao.getAll();
        }
    }

    public static class Insert extends AsyncTask<Player, Void, Player> {

        public Insert(Context context) {
            playerDao = DatabaseManager
                    .getInstance(context)
                    .getPlayerDao();
        }

        @Override
        protected Player doInBackground(Player... players) {
            if (players != null && players.length != 1) {
                return null;
            }
            Player player = players[0];

            long id = playerDao.insert(player);
            if (id != -1) {
                player.setId(id);
                return player;
            }
            return null;
        }
    }

    public static class Update extends AsyncTask<Player, Void, Integer> {

        public Update(Context context) {
            playerDao = DatabaseManager.getInstance(context).getPlayerDao();
        }

        @Override
        protected Integer doInBackground(Player... players) {
            if (players == null || players.length != 1) {
                return -1;
            }
            return playerDao.update(players[0]);
        }
    }

    public static class Delete extends AsyncTask<Player, Void, Integer> {
        public Delete(Context context) {
            playerDao = DatabaseManager.getInstance(context).getPlayerDao();
        }

        @Override
        protected Integer doInBackground(Player... players) {
            if (players == null || players.length != 1) {
                return -1;
            }
            return playerDao.delete(players[0]);
        }
    }
}
