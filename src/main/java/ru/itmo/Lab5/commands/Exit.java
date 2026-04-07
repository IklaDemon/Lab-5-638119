package ru.itmo.Lab5.commands;

import ru.itmo.Lab5.interfaces.Command;

/**
 * Command that terminates the program without saving.
 */
public class Exit implements Command {
  /**
   * Creates an exit command.
   */
  public Exit() {
    ;
  }

  /**
   * Executes the exit command.
   *
   * <p>
   * Returns a special value used to terminate the application.
   *
   * @param args command arguments
   * @return exit signal string
   */
  @Override
  public String exec(String args) {
    return "exit";
  }

  /**
   * Returns the usage description of the command.
   *
   * @return usage string
   */
  @Override
  public String usage() {
    return " - exit:\nExits the program without saving";
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
