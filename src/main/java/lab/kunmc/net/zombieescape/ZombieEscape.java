package lab.kunmc.net.zombieescape;

import dev.kotx.flylib.FlyLib;
import lab.kunmc.net.zombieescape.command.Main;
import lab.kunmc.net.zombieescape.command.ZEEscape;
import lab.kunmc.net.zombieescape.config.Config;
import net.kunmc.lab.configlib.command.ConfigCommand;
import net.kunmc.lab.configlib.command.ConfigCommandBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class ZombieEscape extends JavaPlugin {

  public static ZombieEscape plugin;
  public static Config config;

  @Override
  public void onEnable() {
    plugin = this;

    //event
    getServer().getPluginManager().registerEvents(new Event(), this);
    // command
    config = new Config(this);
    config.saveConfig();
    config.loadConfig();

    ConfigCommand configCommand = new ConfigCommandBuilder(config).build();

    FlyLib.create(this, builder -> {
      builder.command(new Main("ze", configCommand));
      builder.command(new ZEEscape("zescape"));
    });
  }

  @Override
  public void onDisable() {
    for (Player player : Bukkit.getOnlinePlayers()) {
      player.setGlowing(false);
    }
  }
}
