package ru.itmo.Lab5.commands;

import java.util.Map;
import ru.itmo.Lab5.interfaces.Command;

/**
 * Command that displays usage information for all available commands.
 */
public class Help implements Command {
  private Map<String, Command> commands;

  /**
   * Creates the command with the specified command registry.
   *
   * @param commands map of available commands
   * @throws NullPointerException if {@code commands} is null
   */
  public Help(Map<String, Command> commands) {
    if (commands == null)
      throw new NullPointerException("Commands is null");
    this.commands = commands;
  }

  /**
   * Executes the help command.
   *
   * <p>
   * Returns usage information for all registered commands.
   *
   * @param args command arguments
   * @return help text for available commands
   */
  @Override
  public String exec(String args) {
    String res = "";

    for (Command command : this.commands.values()) {
      res += command.usage() + "\n";
    }

    return res;
  }

  /**
   * Returns the usage description of the command.
   *
   * @return usage string
   */
  @Override
  public String usage() {
    String res = "";
    res += " - help:\n";
    res += "Shows help about available commands";
    return res;
  }

  /**
   * Returns the number of arguments required by this command.
   *
   * @return {@code 0}
   */
  @Override
  public int numberOfArgs() {
    return 0;
  }

  /**
   * Indicates that this command does not require dragon data.
   *
   * @return {@code false}
   */
  @Override
  public boolean requiresDragon() {
    return false;
  }
}
