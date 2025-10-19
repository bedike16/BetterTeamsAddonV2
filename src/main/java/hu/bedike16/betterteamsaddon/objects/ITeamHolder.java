package hu.bedike16.betterteamsaddon.objects;

import com.booksaw.betterTeams.Team;

public interface ITeamHolder {

    Team getTeam();
    int getKills();
    int getDeaths();
    double getDamages();

    void load();
    void save();

    int setKills(int kills);
    int setDeaths(int deaths);
    double setDamages(double damage);

    int addKills();
    int addDeaths();
    double addDamage(double damage);
}
