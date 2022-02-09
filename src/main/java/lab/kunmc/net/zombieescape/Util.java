package lab.kunmc.net.zombieescape;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;
import xyz.haoshoku.nick.api.NickAPI;

public class Util {

  public static Team getTeam(String teamName) {
    return Bukkit.getScoreboardManager().getMainScoreboard().getTeam(teamName);
  }

  /**
   * 全員にタイトルを表示
   */
  public static void sendTitleAll(String title, String subtitle, int fadeIn, int stay,
      int fadeOut) {
    Bukkit.getOnlinePlayers().forEach(player -> {
      player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
    });
  }

  /**
   * タイトルをクリア
   */
  public static void clearTitle() {
    Bukkit.getOnlinePlayers().forEach(player -> {
      player.sendTitle("", "", 0, 0, 0);
    });
  }

  /**
   * 全員にメッセージを表示
   */
  public static void broadcast(String message) {
    Bukkit.broadcast(Component.text(message));
  }

  /**
   * ログ出力
   */
  public static void log(String message) {
    Bukkit.getLogger().info(message);
  }

  /**
   * スキンを変更する
   */
  public static void changeSkin(Player player, String playerName) {
    NickAPI.setSkin(player, playerName);
    NickAPI.refreshPlayer(player);
  }

  /**
   * 全員のスキンをリセットする
   */
  public static void clearSkin() {
    for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
      if (offlinePlayer.isOnline()) {
        Player player = (Player) offlinePlayer;
        NickAPI.resetSkin(player);
        NickAPI.refreshPlayer(player);
      }
    }
  }

  /**
   * 全員の発光を止める
   */
  public static void clearGlowing() {
    for (Player player : Bukkit.getOnlinePlayers()) {
      player.setGlowing(false);
    }
  }

  /**
   * 全員の耐久エフェクトを解除する
   */
  public static void clearEffect() {
    new BukkitRunnable() {
      @Override
      public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
          player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        }
      }
    }.runTask(ZombieEscape.plugin);
  }

  /**
   * 全員の体力をもとに戻す
   */
  public static void resetHealth() {
    for (Player player : Bukkit.getOnlinePlayers()) {
      player.setMaxHealth(20);
    }
  }

  /**
   * 全員のアイテムをクリア
   */
  public static void clearInventory() {
    for (Player player : Bukkit.getOnlinePlayers()) {
      player.getInventory().clear();
    }
  }

  public static void killSync(Player player) {
    new BukkitRunnable() {
      @Override
      public void run() {
        player.damage(1000);
      }
    }.runTask(ZombieEscape.plugin);
  }
}
