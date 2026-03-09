package ru.itmo.Lab5.commands;

import java.util.ArrayList;
import ru.itmo.Lab5.classes.CollectionManager;
import ru.itmo.Lab5.interfaces.Command;

/**
 * Save command
 */
public class Save implements Command {
  private CollectionManager collection;

  public Save(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collection manager is null");
    }
    this.collection = collection;
  }

  @Override
  public String exec(ArrayList<String> args) {
    String res = "";
    boolean status = collection.exportToExternalSource();
    if (status) {
      res = "File created succesfully\n";
    } else {
      res = "Problem while writing\n";
    }
    return res;
  }

  @Override
  public String usage() {
    String res = "";
    res += "\n - save:\n";
    res += "save the collection to a file\n";
    return res;
  }
}
