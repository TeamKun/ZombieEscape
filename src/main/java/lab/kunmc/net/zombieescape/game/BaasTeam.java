package lab.kunmc.net.zombieescape.game;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public abstract class BaasTeam {

  protected Team team;

  public BaasTeam(Team team) {
    this.team = team;
  }

  public boolean hasPlayer(Player player) {
    return this.team.hasEntry(player.getName());
  }
}
