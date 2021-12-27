package lab.kunmc.net.zombieescape.command;

import dev.kotx.flylib.command.CommandContext;

public class CommandResult {

  private String message;
  private boolean isSucceed;
  private CommandContext ctx;

  public CommandResult(String message, boolean isSucceed, CommandContext ctx) {
    this.message = message;
    this.isSucceed = isSucceed;
    this.ctx = ctx;
  }
}
