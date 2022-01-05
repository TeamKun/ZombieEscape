package lab.kunmc.net.zombieescape.command;

import dev.kotx.flylib.command.Command;
import dev.kotx.flylib.command.CommandContext;
import lab.kunmc.net.zombieescape.game.GameManager;
import org.jetbrains.annotations.NotNull;

public class ZEEscape extends Command {

  public ZEEscape(@NotNull String name) {
    super(name);
    usage(usageBuilder -> {
      usageBuilder.entityArgument("player", true);
    });
  }

  @Override
  public void execute(@NotNull CommandContext ctx) {
    GameManager.escape(ctx);
  }
}
