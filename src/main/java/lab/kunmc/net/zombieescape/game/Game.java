package lab.kunmc.net.zombieescape.game;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class Game {

  private HumanTeam humanTeam;
  private ZombieTeam zombieTeam;

  Game(Team humanTeam, Team zombieTeam) {
    this.humanTeam = new HumanTeam(humanTeam);
    this.zombieTeam = new ZombieTeam(zombieTeam);
  }

  void addZombie(Player player) {
    this.zombieTeam.add(player);
  }

}
