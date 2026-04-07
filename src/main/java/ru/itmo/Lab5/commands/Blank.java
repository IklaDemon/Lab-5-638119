package ru.itmo.Lab5.commands;

import ru.itmo.Lab5.interfaces.Command;

/**
 * Command that ignores blank input.
 */
public class Blank implements Command {

  /**
   * Creates a blank command.
   */
  public Blank() {
    ;
  }

  /**
   * Executes the blank command.
   *
   * <p>
   * This command performs no action and returns an empty string.
   *
   * @param args command arguments
   * @return empty result string
   */
  @Override
  public String exec(String args) {
    return "";
  }

  /**
   * Returns the usage description of the command.
   *
   * @return usage string
   */
  @Override
  public String usage() {
    return " - '':\nBlank input (just white spaces) will be ignored.";
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
