package lab.kunmc.net.zombieescape.game;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class ZombieTeam extends BaasTeam {

  public ZombieTeam(Team team) {
    super(team);
  }

  @Override
  void init() {
    for (OfflinePlayer offlinePlayer : this.team.getPlayers()) {
      if (!offlinePlayer.isOnline()) {
        continue;
      }
      Player player = (Player) offlinePlayer;
      Logic.changeZombieSkin(player);
    }
  }

  void add(Player player) {
    settingZombieState(player);
    this.team.addEntry(player.getName());
  }

  void killAll() {
    for (OfflinePlayer player : this.team.getPlayers()) {
      if (player.isOnline()) {
        ((Player) player).damage(10000);
      }
    }
  }

  private void settingZombieState(Player player) {
    Logic.changeZombieSkin(player);
  }
}
