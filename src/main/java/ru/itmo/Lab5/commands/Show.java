package ru.itmo.Lab5.commands;

import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * Show command
 */
public class Show implements Command {
  private CollectionManager collection;

  public Show(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collectin manager is null");
    }
    this.collection = collection;
  }

  @Override
  public String exec(String arg) {
    String show = this.collection.toString();
    return show;
  }

  @Override
  public String usage() {
    String res = "";
    res += " - show:\n";
    res += "Shows all elements from collection:";
    return res;
  }

  @Override
  public int numberOfArgs() {
    return 0;
  }

  @Override
  public boolean requiresDragon() {
    return false;
  }
}
