package lab.kunmc.net.zombieescape.command;

import dev.kotx.flylib.command.Command;
import dev.kotx.flylib.command.CommandContext;
import lab.kunmc.net.zombieescape.Util;
import org.jetbrains.annotations.NotNull;

public class ClearSkin extends Command {

  public ClearSkin(@NotNull String name) {
    super(name);
  }

  @Override
  public void execute(@NotNull CommandContext ctx) {
    Util.clearSkin();
    ctx.success("全員のスキンをリセットしました");
  }
}
