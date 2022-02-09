package lab.kunmc.net.zombieescape.game;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public abstract class BaseTeam {

  protected Team team;

  public BaseTeam(Team team) {
    this.team = team;
    this.init();
  }

  public boolean hasPlayer(Player player) {
    return this.team.hasEntry(player.getName());
  }

  public Set<Player> playerList() {
    Set<Player> players = new HashSet<>();
    for (OfflinePlayer offlinePlayer : this.team.getPlayers()) {
      if (offlinePlayer.isOnline()) {
        players.add((Player) offlinePlayer);
      }
    }
    return players;
  }

  abstract void init();
}
