package eu.ase.ro.damapp.network;

import androidx.annotation.NonNull;

public class Item {
    private String team;
    private String extraInfo;
    private PlayerInfo playerInfo;

    public Item(String team, String extraInfo, PlayerInfo playerInfo) {
        this.team = team;
        this.extraInfo = extraInfo;
        this.playerInfo = playerInfo;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    @Override
    public String toString() {
        return "Item{" +
                "team='" + team + '\'' +
                ", extraInfo='" + extraInfo + '\'' +
                ", playerInfo=" + playerInfo +
                '}';
    }
}
