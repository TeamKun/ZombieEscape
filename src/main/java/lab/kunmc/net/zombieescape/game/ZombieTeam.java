package lab.kunmc.net.zombieescape.game;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class ZombieTeam {

  private Team team;

  public ZombieTeam(Team team) {
    this.team = team;
  }

  void add(Player player) {
    this.team.addEntry(player.getName());
  }
}
