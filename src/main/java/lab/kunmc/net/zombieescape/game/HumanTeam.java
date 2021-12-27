package lab.kunmc.net.zombieescape.game;

import static org.bukkit.Bukkit.getServer;

import lab.kunmc.net.zombieescape.ZombieEscape;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scoreboard.Team;

public class HumanTeam implements Listener {

  private Team team;

  public HumanTeam(Team team) {
    this.team = team;
    getServer().getPluginManager().registerEvents(this, ZombieEscape.plugin);
  }

  @EventHandler(ignoreCancelled = true)
  public void onPlayerDeath(PlayerDeathEvent event) {
    Player player = event.getEntity();
    if (!team.hasEntry(player.getName())) {
      return;
    }

    GameManager.addZombie(player);
    event.setCancelled(true);
  }
}
