package lab.kunmc.net.zombieescape.game;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scoreboard.Team;

public class Game implements Listener {

  private HumanTeam humanTeam;
  private ZombieTeam zombieTeam;

  Game(Team humanTeam, Team zombieTeam) {
    this.humanTeam = new HumanTeam(humanTeam);
    this.zombieTeam = new ZombieTeam(zombieTeam);
  }

  void addZombie(Player player) {
    this.zombieTeam.add(player);
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

    this.zombieTeam.add(player);
  }
}
