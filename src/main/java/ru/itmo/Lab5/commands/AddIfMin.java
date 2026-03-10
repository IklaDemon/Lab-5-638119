package ru.itmo.Lab5.commands;

import java.util.ArrayList;

import ru.itmo.Lab5.dragon.Coordinates;
import ru.itmo.Lab5.dragon.Dragon;
import ru.itmo.Lab5.dragon.DragonCave;
import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * Command that AddIfMin a dragon
 */
public class AddIfMin implements Command {
  private CollectionManager collection;

  public AddIfMin(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collection manager is null");
    }
    this.collection = collection;
  }

  @Override
  public String exec(ArrayList<String> args) {
    String res = "";
    Dragon.Builder dragonBuilder = new Dragon.Builder();
    try {
      dragonBuilder.id(this.collection.genNewID());
    } catch (IllegalArgumentException e) {
      res += "Problem generating ID: " + e.getMessage() + "\n";
    }
    try {
      dragonBuilder.name(args.get(0));
    } catch (Exception e) {
      res += "Problem with the name: " + e.getMessage() + "\n";
    }
    try {
      Coordinates coordinate = new Coordinates(0L, 0L);
      coordinate.setX(Long.parseLong(args.get(1)));
      coordinate.setY(Long.parseLong(args.get(2)));
      dragonBuilder.coordinates(coordinate);
    } catch (Exception e) {
      res += "Problem with the coordinate: " + e.getMessage() + "\n";
    }
    dragonBuilder.creationDate(collection.genNewDate());
    try {
      if (args.get(3).isBlank() || args.get(3).isEmpty()) {
        dragonBuilder.age(null);
      } else {
        dragonBuilder.age(Integer.parseInt(args.get(3)));
      }
    } catch (Exception e) {
      res += "Problem with the age: " + e.getMessage() + "\n";
    }
    try {
      dragonBuilder.wingspan(Double.parseDouble(args.get(4)));
    } catch (Exception e) {
      res += "Problem with the wingspan: " + e.getMessage() + "\n";
    }
    try {
      if (args.get(5).isBlank() || args.get(5).isEmpty()) {
        dragonBuilder.type(null);
      } else {
        dragonBuilder.type(args.get(5));
      }
    } catch (Exception e) {
      res += "Problem with the dragon type: " + e.getMessage() + "\n";
    }
    try {
      if (args.get(6).isBlank() || args.get(6).isEmpty()) {
        dragonBuilder.character(null);
      } else {
        dragonBuilder.character(args.get(6));
      }
    } catch (Exception e) {
      res += "Problem with the dragon character: " + e.getMessage() + "\n";
    }
    try {
      if (args.get(7).isBlank() || args.get(7).isEmpty()) {
        dragonBuilder.cave(null);
      } else {
        dragonBuilder.cave(new DragonCave(Double.parseDouble(args.get(7))));
      }
    } catch (Exception e) {
      res += "Problem with the dragon cave: " + e.getMessage() + "\n";
    }
    Dragon newDragon = dragonBuilder.build();
    Dragon minDragon = collection.getMin();
    if (minDragon == null) {
      collection.addDragon(newDragon);
      res += "Added new Dragon: " + newDragon.getName() + "\n";
    } else {
      if (newDragon.compareTo(minDragon) < 0) {
        collection.addDragon(newDragon);
        res += "Added new Dragon: " + newDragon.getName() + "\n";
      } else {
        res += newDragon.getName() + " is smaller than " + minDragon.getName() + "\n";
      }
    }
    return res;
  }

  @Override
  public String usage() {
    String res = "";
    res += "\n - add_if_min {element}:\n";
    res += "Adds the element only if it is smaller than the current smallest present\n";
    return res;
  }
}
