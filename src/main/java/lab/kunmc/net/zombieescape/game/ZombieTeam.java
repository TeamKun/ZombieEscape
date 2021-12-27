package lab.kunmc.net.zombieescape.game;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class ZombieTeam extends BaasTeam {

  public ZombieTeam(Team team) {
    super(team);
  }

  void add(Player player) {
    this.team.addEntry(player.getName());
  }
}
