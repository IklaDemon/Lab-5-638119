package ru.itmo.Lab5.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import ru.itmo.Lab5.enums.DragonCharacter;
import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * PrintFieldDescendingCharacter command
 */
public class PrintFieldDescendingCharacter implements Command {
  private CollectionManager collection;

  public PrintFieldDescendingCharacter(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collection manager is null");
    }
    this.collection = collection;
  }

  @Override
  public String exec(ArrayList<String> args) {
    String res = "";
    ArrayList<DragonCharacter> characters = collection.getCharacters();
    characters.removeIf(Objects::isNull);
    characters.sort(Collections.reverseOrder());
    for (DragonCharacter character : characters) {
      res += character.toString() + "\n";
    }
    return res;
  }

  @Override
  public String usage() {
    String res = "";
    res += " - print_field_descending_character:\n";
    res += "display the values of the character field of all elements in descending order";
    return res;
  }
}
