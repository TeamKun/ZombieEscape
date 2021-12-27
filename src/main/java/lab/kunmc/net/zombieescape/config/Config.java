package lab.kunmc.net.zombieescape.config;

import dev.kotx.flylib.command.UsageBuilder;
import java.util.ArrayList;
import java.util.List;
import net.kunmc.lab.configlib.config.BaseConfig;
import net.kunmc.lab.configlib.value.BlockDataValue;
import net.kunmc.lab.configlib.value.StringValue;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

public class Config extends BaseConfig {

  public BlockDataValue surviveBlock = new BlockDataValue(Material.EMERALD_BLOCK.createBlockData());
  public StringValue humanTeam = new StringValue(null);
  public StringValue zombieTeam = new StringValue(null);

  public Config(@NotNull Plugin plugin) {
    super(plugin, "config");

    List<String> teams = new ArrayList<>();
    for (Team team : Bukkit.getScoreboardManager().getMainScoreboard().getTeams()) {
      teams.add(team.getName());
    }

    humanTeam.appendArgument(new UsageBuilder().selectionArgument("team", teams));
    zombieTeam.appendArgument(new UsageBuilder().selectionArgument("team", teams));
  }
}
