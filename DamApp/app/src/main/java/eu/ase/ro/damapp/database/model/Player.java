package eu.ase.ro.damapp.database.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import eu.ase.ro.damapp.AddPlayerActivity;

@Entity(tableName = "players")
public class Player implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "birthday")
    private Date birthday;
    @ColumnInfo(name = "number")
    private Integer number;
    @ColumnInfo(name = "position")
    private String position;
    @ColumnInfo(name = "favorite_hand")
    private String favHand;

    public Player(long id, String name, Date birthday, Integer number, String position, String favHand) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.number = number;
        this.position = position;
        this.favHand = favHand;
    }

    @Ignore
    public Player(String name, Date birthday, Integer number, String position, String favHand) {
        this.name = name;
        this.birthday = birthday;
        this.number = number;
        this.position = position;
        this.favHand = favHand;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getFavHand() {
        return favHand;
    }

    public void setFavHand(String favHand) {
        this.favHand = favHand;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", number=" + number +
                ", position='" + position + '\'' +
                ", favHand='" + favHand + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        //contie?true:false
        String dateStr = this.birthday != null
                ? new SimpleDateFormat(
                AddPlayerActivity.DATE_FORMAT,
                Locale.US).format(this.birthday)
                : null;
        dest.writeString(dateStr);
        dest.writeInt(number);
        dest.writeString(position);
        dest.writeString(favHand);
    }

    private Player(Parcel in) {
        this.name = in.readString();
        try {
            this.birthday = new SimpleDateFormat(
                    AddPlayerActivity.DATE_FORMAT,
                    Locale.US).parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.number = in.readInt();
        this.position = in.readString();
        this.favHand = in.readString();
    }

    public static Creator<Player> CREATOR =
            new Creator<Player>() {
                @Override
                public Player createFromParcel(Parcel source) {
                    return new Player(source);
                }

                @Override
                public Player[] newArray(int size) {
                    return new Player[size];
                }
            };
}
