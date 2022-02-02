package lab.kunmc.net.zombieescape;

import dev.kotx.flylib.FlyLib;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lab.kunmc.net.zombieescape.command.Main;
import lab.kunmc.net.zombieescape.command.ZEEscape;
import lab.kunmc.net.zombieescape.config.Config;
import lab.kunmc.net.zombieescape.game.Event;
import net.kunmc.lab.configlib.ConfigCommand;
import net.kunmc.lab.configlib.ConfigCommandBuilder;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

public final class ZombieEscape extends JavaPlugin {

  public static ZombieEscape plugin;
  public static Config config;

  @Override
  public void onEnable() {
    plugin = this;

    //event
    getServer().getPluginManager().registerEvents(new Event(), this);
    // command
    config = new Config(this, this.getWeaponsNameList());
    config.saveConfigIfAbsent();
    config.loadConfig();

    ConfigCommand configCommand = new ConfigCommandBuilder(config).build();

    FlyLib.create(this, builder -> {
      builder.command(new Main("ze", configCommand));
      builder.command(new ZEEscape("escape"));
    });

    getWeaponsNameList();
  }

  private List<String> getWeaponsNameList() {
    File qualityArmory = new File(getDataFolder().getParentFile(), "QualityArmory");
    List<String> nameList = new ArrayList<>();

    for (File childDirectory : qualityArmory.listFiles()) {
      if (!childDirectory.isDirectory()) {
        continue;
      }
      for (File settingsFile : childDirectory.listFiles()) {
        if (!settingsFile.isFile() || !FilenameUtils.getExtension(settingsFile.getName())
            .equals("yml")) {
          continue;
        }

        // yaml読み込み
        String weaponName = getWeaponName(settingsFile);
        if (weaponName != null) {
          nameList.add(getWeaponName(settingsFile));
        }
      }
    }
    return nameList;
  }

  private String getWeaponName(File yamlFile) {
    String confFilePath = yamlFile.getPath();
    String encoding = "UTF-8";

    File file = new File(confFilePath);
    FileInputStream input;
    InputStreamReader stream;
    try {
      input = new FileInputStream(file);
      stream = new InputStreamReader(input, encoding);
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      System.out.println(e.getClass().getName() +
          " fail open file " + confFilePath);
      return null;
    }

    Yaml yaml = new Yaml();
    Map yamlMap = yaml.load(stream);

    return (String) yamlMap.get("name");
  }
}
