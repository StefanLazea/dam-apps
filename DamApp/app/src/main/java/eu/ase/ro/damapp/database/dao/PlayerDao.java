package eu.ase.ro.damapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eu.ase.ro.damapp.database.model.Player;

//legatura cu baza de date; se va folosi doar de conexiune
//operatii dml
@Dao
public interface PlayerDao {
    @Query("SELECT * FROM players")
    List<Player> getAll();

    @Insert
    long insert(Player player);

    @Update
    int update(Player player);

    @Delete
    int delete(Player player);
}
