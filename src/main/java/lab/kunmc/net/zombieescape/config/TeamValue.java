package lab.kunmc.net.zombieescape.config;

import dev.kotx.flylib.command.UsageBuilder;
import java.util.function.Consumer;
import net.kunmc.lab.configlib.value.StringValue;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Team;

public class TeamValue extends StringValue {

  public TeamValue(String value) {
    super(value);
  }

  public TeamValue(String value, Consumer<String> onSet) {
    super(value, onSet);
  }

  public TeamValue(String value, int min, int max) {
    super(value, min, max);
  }

  public TeamValue(String value, int min, int max,
      Consumer<String> onSet) {
    super(value, min, max, onSet);
  }

  @Override
  public void appendArgument(UsageBuilder builder) {
    builder.textArgument("team", suggestionBuilder -> {
      for (Team team : Bukkit.getScoreboardManager().getMainScoreboard().getTeams()) {
        suggestionBuilder.suggest(team.getName());
      }
    });
  }
}
