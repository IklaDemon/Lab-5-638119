package ru.itmo.Lab5.commands;

import java.util.ArrayList;
import ru.itmo.Lab5.classes.CollectionManager;
import ru.itmo.Lab5.interfaces.Command;

/**
 * RemoveById command
 */
public class RemoveById implements Command {
  private CollectionManager collection;

  public RemoveById(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collection manager is null");
    }
    this.collection = collection;
  }

  @Override
  public String exec(ArrayList<String> args) {
    String res = "";

    try {
      long id = Long.parseLong(args.get(0));
      collection.remove(id);
    } catch (Exception e) {
      res += "Problem with the id: " + e.getMessage() + "\n";
    }

    if (res.isBlank() || res.isEmpty()) {
      res += "Dragon removed\n";
    }

    return res;
  }

  @Override
  public String usage() {
    String res = "";
    res += "\n - remove_by_id id:\n";
    res += "Removes the element with the specified id\n";
    return res;
  }
}
