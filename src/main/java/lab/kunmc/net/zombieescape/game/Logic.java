package lab.kunmc.net.zombieescape.game;

import lab.kunmc.net.zombieescape.Util;
import lab.kunmc.net.zombieescape.ZombieEscape;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Logic {

  static void changeZombieSkin(Player player) {
    if (!ZombieEscape.config.isSkinChange.value()) {
      return;
    }
    Util.changeSkin(player, ZombieEscape.config.toSkinPlayerName.value());
  }

  static void clearPlayerState(Player player) {
    player.setGlowing(false);
    player.setMaxHealth(20);
    player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
  }
}
