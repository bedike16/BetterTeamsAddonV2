package hu.bedike16.betterteamsaddon;

import hu.bedike16.betterteamsaddon.utils.ConfigUtils;

public class ConfigData {

    private String noTeamPlaceholder;
    private int kdrRound;

    public ConfigData() {
        reload();
    }

    public void reload() {
        this.noTeamPlaceholder = ConfigUtils.getOrSet("no_team_placeholder", "-");
        this.kdrRound = ConfigUtils.getOrSet("kdr_round", 2);
    }

    public String getNoTeamPlaceholder() {
        return noTeamPlaceholder;
    }

    public int getKdrRound() {
        return kdrRound;
    }
}