package ru.itmo.Lab5.commands;

import java.util.ArrayList;
import ru.itmo.Lab5.classes.CollectionManager;
import ru.itmo.Lab5.interfaces.Command;

/**
 * Weird command that does what the exercise asked for
 */
public class CountGreaterThanCave implements Command {
  private CollectionManager collection;

  public CountGreaterThanCave(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collection manager is null");
    }
    this.collection = collection;
  }

  @Override
  public String exec(ArrayList<String> args) {
    String res = "";
    double treasures = 0;
    try {
      treasures = Double.parseDouble(args.get(0));
      int n = collection.nGreaterThanCave(treasures);
      if (n <= 0)
        return "Argument should be > 0";
      return n + "\n";
    } catch (Exception e) {
      res += "Invalid argument: " + e.getMessage() + "\n";
    }
    return res;
  }

  @Override
  public String usage() {
    String res = "";
    res += "\n - count_greater_than_cave cave:\n";
    res += "display the number of elements whose cave field value is greater than the specified value\n";
    return res;
  }

}
