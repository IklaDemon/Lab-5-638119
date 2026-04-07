package ru.itmo.Lab5.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import ru.itmo.Lab5.enums.DragonCharacter;
import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * Command that displays all non-null dragon character values
 * in descending order.
 */
public class PrintFieldDescendingCharacter implements Command {
  private CollectionManager collection;

  /**
   * Creates the command with the specified collection manager.
   *
   * @param collection collection manager used to access the collection
   * @throws NullPointerException if {@code collection} is null
   */
  public PrintFieldDescendingCharacter(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collection manager is null");
    }
    this.collection = collection;
  }

  /**
   * Executes the print_field_descending_character command.
   *
   * <p>
   * Collects all non-null values of the {@code character} field
   * and returns them in descending order.
   *
   * @param arg command arguments
   * @return character values in descending order
   */
  @Override
  public String exec(String arg) {
    String res = "";
    ArrayList<DragonCharacter> characters = collection.getCharacters();
    characters.removeIf(Objects::isNull);
    characters.sort(Collections.reverseOrder());
    for (DragonCharacter character : characters) {
      res += character.toString() + "\n";
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
    res += " - print_field_descending_character:\n";
    res += "display the values of the character field of all elements in descending order";
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
