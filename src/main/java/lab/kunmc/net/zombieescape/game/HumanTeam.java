package lab.kunmc.net.zombieescape.game;

import java.util.ArrayList;
import java.util.List;
import lab.kunmc.net.zombieescape.ZombieEscape;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class HumanTeam extends BaseTeam {

  public HumanTeam(Team team) {
    super(team);
  }

  boolean isEradication() {
    return this.team.getSize() == 0;
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

  private void settingState(Player player) {
    if (ZombieEscape.config.isHumanGlowing.value()) {
      player.setGlowing(true);
    }
  }

  List<Player> escape() {
    List<Player> survivors = new ArrayList<>();
    for (OfflinePlayer offlinePlayer : this.team.getPlayers()) {
      if (!offlinePlayer.isOnline()) {
        continue;
      }

      Player player = offlinePlayer.getPlayer();
      if (!isSuccessfulEscape(player)) {
        player.damage(10000);
      }

      survivors.add(player);
    }
    return survivors;
  }

  private boolean isSuccessfulEscape(Player player) {
    Location loc = player.getLocation();
    int y = -1;
    while (true) {
      Block block = loc.add(0, y, 0).getBlock();

      // 生存成功
      if (block.getBlockData().equals(ZombieEscape.config.surviveBlock.value())) {
        return true;
      }

      // 生存失敗
      if (!block.getBlockData().equals(Material.AIR.createBlockData()) && !block.getBlockData()
          .equals(Material.CAVE_AIR.createBlockData())) {
        return false;
      }
      y -= 1;
    }
  }
}
