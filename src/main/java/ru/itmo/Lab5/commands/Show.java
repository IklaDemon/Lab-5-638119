package ru.itmo.Lab5.commands;

import java.util.ArrayList;
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
  public String exec(ArrayList<String> args) {
    String show = this.collection.toString();
    return show;
  }

  @Override
  public String usage() {
    String res = "";
    res += "\n - show:\n";
    res += "Shows all elements from collection:\n";
    return res;
  }
}
