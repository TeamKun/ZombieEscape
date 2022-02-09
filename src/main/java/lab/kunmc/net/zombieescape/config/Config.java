package lab.kunmc.net.zombieescape.config;

import java.util.List;
import net.kunmc.lab.configlib.BaseConfig;
import net.kunmc.lab.configlib.value.BlockDataValue;
import net.kunmc.lab.configlib.value.BooleanValue;
import net.kunmc.lab.configlib.value.IntegerValue;
import net.kunmc.lab.configlib.value.StringListValue;
import net.kunmc.lab.configlib.value.StringValue;
import net.kunmc.lab.configlib.value.TeamValue;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class Config extends BaseConfig {

  public BlockDataValue surviveBlock = new BlockDataValue(Material.EMERALD_BLOCK.createBlockData());
  public TeamValue humanTeam = new TeamValue();
  public TeamValue zombieTeam = new TeamValue();
  public StringValue toSkinPlayerName = new StringValue("Bosa2");
  public BooleanValue isSkinChange = new BooleanValue(true);
  public BooleanValue isHumanGlowing = new BooleanValue(false);
  public BooleanValue isZombieGlowing = new BooleanValue(true);
  public IntegerValue zombieHealth = new IntegerValue(100);
  public StringListValue initialGuns = new StringListValue();
  public IntegerValue escapeWaitingTime = new IntegerValue(30);
  public BooleanValue allowButtonPressZombie = new BooleanValue(true);

  public Config(@NotNull Plugin plugin, List<String> weaponNameList) {
    super(plugin);
    for (String name : weaponNameList) {
      this.initialGuns.addAllowableString(name);
    }
  }
}
