package ru.itmo.Lab5.interfaces;

/**
 * Represents a command that can be executed from the command-line interface.
 *
 * <p>
 * Each command defines the number of required arguments, whether a dragon
 * object is needed, usage information, and execution logic.
 */
public interface Command {
  /**
   * Returns the number of arguments required by this command.
   *
   * @return required number of arguments
   */
  public int numberOfArgs();

  /**
   * Indicates whether this command requires dragon data for execution.
   *
   * @return {@code true} if the command requires a dragon, {@code false}
   *         otherwise
   */
  public boolean requiresDragon();

  /**
   * Returns the usage description of this command.
   *
   * @return usage string
   */
  public String usage();

  /**
   * Executes the command with the specified argument.
   *
   * @param arg command argument
   * @return execution result message
   */
  public String exec(String arg);
}
