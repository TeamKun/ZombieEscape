package lab.kunmc.net.zombieescape.game;

import lab.kunmc.net.zombieescape.ZombieEscape;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class ZombieTeam extends BaseTeam {

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
      this.settingState(player);
    }
  }

  void add(Player player) {
    settingState(player);
    this.team.addEntry(player.getName());
  }

  void killAll() {
    for (OfflinePlayer player : this.team.getPlayers()) {
      if (player.isOnline()) {
        ((Player) player).damage(10000);
      }
    }
  }

  private void settingState(Player player) {
    Logic.changeZombieSkin(player);
    if (ZombieEscape.config.isHumanGlowing.value()) {
      player.setGlowing(true);
    }
  }
}
