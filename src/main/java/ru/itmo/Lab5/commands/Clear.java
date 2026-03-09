package ru.itmo.Lab5.commands;

import java.util.ArrayList;
import ru.itmo.Lab5.classes.CollectionManager;
import ru.itmo.Lab5.interfaces.Command;

/**
 * Clear command
 */
public class Clear implements Command {
  private CollectionManager collection;

  public Clear(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collection manager is null");
    }
    this.collection = collection;
  }

  @Override
  public String exec(ArrayList<String> args) {
    collection.clear();
    return "Collection cleared";
  }

  @Override
  public String usage() {
    String res = "";
    res += "\n - add {element}:\n";
    res += "Adds a new element to the collection\n";
    res += "Each attribute will be typed on a new line\n";
    return res;
  }
}
