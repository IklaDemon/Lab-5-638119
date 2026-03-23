package ru.itmo.Lab5.commands;

import java.util.ArrayList;
import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * PrintDescending command
 */
public class PrintDescending implements Command {
  private CollectionManager collection;

  public PrintDescending(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collection manager is null");
    }
    this.collection = collection;
  }

  @Override
  public String exec(ArrayList<String> args) {
    String res = "";
    res = collection.getDecreasing();
    return res;
  }

  @Override
  public String usage() {
    String res = "";
    res += " - print_descending:\n";
    res += "display the elements of a collection in descending order";
    return res;
  }

}
