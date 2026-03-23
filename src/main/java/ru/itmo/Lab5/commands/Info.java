package ru.itmo.Lab5.commands;

import java.util.ArrayList;
import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * Info command
 */
public class Info implements Command {
  private CollectionManager collection;

  public Info(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collection manager is null");
    }
    this.collection = collection;
  }

  @Override
  public String exec(ArrayList<String> args) {
    String info = this.collection.info();
    return info;
  }

  @Override
  public String usage() {
    String res = "";
    res += " - info:\n";
    res += "Prints information about the collection:";
    return res;
  }
}
