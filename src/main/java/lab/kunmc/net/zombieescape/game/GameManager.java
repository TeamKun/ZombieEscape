package lab.kunmc.net.zombieescape.game;

import dev.kotx.flylib.command.CommandContext;
import java.util.List;
import lab.kunmc.net.zombieescape.Util;
import lab.kunmc.net.zombieescape.ZombieEscape;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class GameManager {
   static Game game;

  /**
   * ゲーム開始
   */
  public static void start(CommandContext ctx) {
    Team humanTeam = ZombieEscape.config.humanTeam.value();
    Team zombieTeam = ZombieEscape.config.zombieTeam.value();

    if (game != null) {
      ctx.fail("すでにゲーム実行中です");
      return;
    }

    if (humanTeam == null) {
      ctx.fail("人間チームがセットされていません");
      return;
    }

    if (zombieTeam == null) {
      ctx.fail("ゾンビチームがセットされていません");
      return;
    }

    if (humanTeam.equals(zombieTeam)) {
      ctx.fail("人間チームとゾンビチームに同じチームがセットされています");
      return;
    }

    for (Player player : Bukkit.getOnlinePlayers()) {
      Logic.clearPlayerState(player);
    }
    Util.clearSkin();
    game = new Game(humanTeam, zombieTeam);
    Bukkit.getServer().getPluginManager().registerEvents(game, ZombieEscape.plugin);
    game.runTaskTimerAsynchronously(ZombieEscape.plugin, 0, 0);

    ctx.success("ゲームを開始します");
  }

  /**
   * ゲーム強制終了
   */
  public static void stop(CommandContext ctx) {
    if (game == null) {
      ctx.fail("ゲーム実行中ではありません");
      return;
    }
    Util.clearSkin();
    resetPlayerState();
    game.stopTimer();
    game.cancel();
    game = null;
    ctx.success("ゲームを終了します");
  }

  static void eradicationEnd() {
    resetPlayerState();
    game.stopTimer();
    game.cancel();
    game = null;
    Util.sendTitleAll("生存者が全滅した", null, 20, 100, 20);
  }

  private static void resetPlayerState() {
    Util.clearGlowing();
    Util.clearEffect();
    Util.resetHealth();
    Util.clearInventory();
  }

  public static void escape(CommandContext ctx) {
    if (game == null) {
      return;
    }
    for (Object arg : ((List) ctx.getTypedArgs().get(0))) {
      if (arg instanceof Player) {
        Player sender = (Player) arg;
        Util.log(sender.getName());
        if (!game.escape(sender)) {
          return;
        }

        if (!game.isTimerRunning()) {
          game.cancel();
        }
      }
    }
  }
}
