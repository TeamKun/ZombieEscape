package lab.kunmc.net.zombieescape.game;

import dev.kotx.flylib.command.CommandContext;
import java.util.List;
import lab.kunmc.net.zombieescape.Util;
import lab.kunmc.net.zombieescape.ZombieEscape;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class GameManager {

  private static Game game;

  /**
   * ゲーム開始
   */
  public static void start(CommandContext ctx) {
    String humanTeamName = ZombieEscape.config.humanTeam.value();
    String zombieTeamName = ZombieEscape.config.zombieTeam.value();

    if (game != null) {
      ctx.fail("すでにゲーム実行中です");
      return;
    }

    if (humanTeamName == null) {
      ctx.fail("人間チームがセットされていません");
      return;
    }

    if (zombieTeamName == null) {
      ctx.fail("ゾンビチームがセットされていません");
      return;
    }

    if (humanTeamName.equals(zombieTeamName)) {
      ctx.fail("人間チームとゾンビチームに同じチームがセットされています");
      return;
    }

    Team humanTeam = Util.getTeam(humanTeamName);
    Team zombieTeam = Util.getTeam(zombieTeamName);

    if (humanTeam == null) {
      ctx.fail(humanTeamName + "チームは存在しません");
      return;
    }

    if (zombieTeam == null) {
      ctx.fail(zombieTeamName + "チームは存在しません");
      return;
    }

    Util.clearSkin();
    game = new Game(humanTeam, zombieTeam);
    Bukkit.getServer().getPluginManager().registerEvents(game, ZombieEscape.plugin);
    game.runTaskTimerAsynchronously(ZombieEscape.plugin, 0, 0);

    ctx.success("ゲームを開始します");
  }

  /**
   * ゲーム終了
   */
  public static void stop(CommandContext ctx) {
    if (game == null) {
      ctx.fail("ゲーム実行中ではありません");
      return;
    }
    Util.clearSkin();
    game.cancel();
    game = null;
    ctx.success("ゲームを終了します");
  }

  static void eradicationEnd() {
    game.cancel();
    game = null;
    Util.sendTitleAll("全滅した", null, 20, 100, 20);
  }

  public static void escape(CommandContext ctx) {
    if (game == null) {
      return;
    }
    for (Object arg : ((List) ctx.getTypedArgs().get(0))) {
      if (arg instanceof Player) {
        Player sender = (Player) arg;
        Util.log(sender.getName());
        game.escape(sender);
        game = null;
      }
    }
  }
}
