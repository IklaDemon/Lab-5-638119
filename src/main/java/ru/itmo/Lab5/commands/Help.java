package ru.itmo.Lab5.commands;

import java.util.ArrayList;
import java.util.Map;
import ru.itmo.Lab5.interfaces.Command;

/**
 * Help command
 */
public class Help implements Command {
  private Map<String, Command> commands;

  public Help(Map<String, Command> commands) {
    if (commands == null)
      throw new NullPointerException("Commands is null");
    this.commands = commands;
  }

  @Override
  public String exec(ArrayList<String> args) {
    String res = "";

    for (Command command : this.commands.values()) {
      res += command.usage() + "\n";
    }

    return res;
  }

  @Override
  public String usage() {
    String res = "";
    res += " - help:\n";
    res += "Shows help about available commands";
    return res;
  }
}
