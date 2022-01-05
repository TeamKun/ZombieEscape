package lab.kunmc.net.zombieescape.game;

import dev.kotx.flylib.command.CommandContext;
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

    game = new Game(humanTeam, zombieTeam);
    Bukkit.getServer().getPluginManager().registerEvents(game, ZombieEscape.plugin);
    game.runTaskTimerAsynchronously(ZombieEscape.plugin, 0, 0);
    Util.clearSkin();
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

    game.cancel();
    game = null;
    ctx.success("ゲームを終了します");
  }

  static void eradicationEnd() {
    game.cancel();
    game = null;
    Util.sendTitleAll("全滅した", null, 20, 60, 20);
  }

  public static void escape(CommandContext ctx) {
    Player sender = Bukkit.getPlayer(ctx.getArgs().get(0));
    if (sender == null) {
      return;
    }
    game.escape(sender);
    game = null;
  }
}
