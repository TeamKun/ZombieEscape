package lab.kunmc.net.zombieescape.game;

import java.util.List;
import lab.kunmc.net.zombieescape.Util;
import lab.kunmc.net.zombieescape.ZombieEscape;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Team;

public class Game extends BukkitRunnable implements Listener {

  private HumanTeam humanTeam;
  private ZombieTeam zombieTeam;

  private BukkitTask escapeTimer;

  Game(Team humanTeam, Team zombieTeam) {
    this.humanTeam = new HumanTeam(humanTeam);
    this.zombieTeam = new ZombieTeam(zombieTeam);
  }

  void escape(Player sender) {
    if (!this.humanTeam.hasPlayer(sender)) {
      return;
    }

    this.escapeTimer = new BukkitRunnable() {
      private int remainingTime = ZombieEscape.config.escapeWaitingTime.value();

      @Override
      public void run() {
        // 秒数表示
        for (Player player : Bukkit.getOnlinePlayers()) {
          player.sendActionBar(Component.text("脱出まで残り" + remainingTime + "秒"));
        }

        remainingTime--;
        if (remainingTime < 0) {
          List<Player> survivors = humanTeam.escape();
          zombieTeam.killAll();

          if (survivors.size() == 0) {
            Util.sendTitleAll("全滅した", null, 20, 100, 20);
          } else {
            Util.sendTitleAll("脱出成功!", "生存者" + survivors.size() + "人", 20, 100, 20);

            Util.broadcast("========生存者一覧========");
            for (Player survivor : survivors) {
              Util.broadcast(survivor.getName());
            }
            Util.broadcast("=======================");
          }
          this.cancel();
          GameManager.game = null;
        }
      }
    }.runTaskTimerAsynchronously(ZombieEscape.plugin, 0, 20);

  }

  @EventHandler(ignoreCancelled = true)
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();

    if (this.humanTeam.hasPlayer(player)) {
      player.setGlowing(ZombieEscape.config.isHumanGlowing.value());
      return;
    }

    if (this.zombieTeam.hasPlayer(player)) {
      player.setGlowing(ZombieEscape.config.isZombieGlowing.value());
      return;
    }
  }

  @EventHandler(ignoreCancelled = true)
  public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
    if (!(event.getEntity() instanceof Player && event.getDamager() instanceof Player)) {
      return;
    }
    Player player = (Player) event.getEntity();
    Player damager = (Player) event.getDamager();

    if (!(this.humanTeam.hasPlayer(player) && this.zombieTeam.hasPlayer(damager))) {
      return;
    }

    // 人間が死亡
    this.zombieTeam.add(player);
  }

  void stopTimer() {
    if (this.isTimerRunning()) {
      this.escapeTimer.cancel();
      this.escapeTimer = null;
    }
  }

  boolean isTimerRunning() {
    return this.escapeTimer != null;
  }

  @Override
  public void run() {
    if (this.humanTeam.isEradication()) {
      // ゲーム終了
      GameManager.eradicationEnd();
    }
  }
}
