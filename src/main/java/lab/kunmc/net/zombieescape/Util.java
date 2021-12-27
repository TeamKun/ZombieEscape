package lab.kunmc.net.zombieescape;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Team;

public class Util {

  public static Team getTeam(String teamName) {
    return Bukkit.getScoreboardManager().getMainScoreboard().getTeam(teamName);
  }
}
