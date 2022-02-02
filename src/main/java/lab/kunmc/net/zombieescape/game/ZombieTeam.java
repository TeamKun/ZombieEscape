package lab.kunmc.net.zombieescape.game;

import lab.kunmc.net.zombieescape.ZombieEscape;
import lab.kunmc.net.zombieescape.config.Config;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
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
    Config config = ZombieEscape.config;
    if (config.isZombieGlowing.value()) {
      player.setGlowing(true);
    }

    player.getInventory().clear();
    player.setMaxHealth(config.zombieHealth.value());
    player.setHealth(config.zombieHealth.value());
    player.addPotionEffect(
        new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,
            Integer.MAX_VALUE,
            config.zombieResistanceLevel.value()));
  }
}
