package ru.itmo.Lab5.commands;

import java.util.ArrayList;

import ru.itmo.Lab5.dragon.Coordinates;
import ru.itmo.Lab5.dragon.Dragon;
import ru.itmo.Lab5.dragon.DragonCave;
import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * Coommand that adds a dragon
 */
public class Add implements Command {
  private CollectionManager collection;

  public Add(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collection manager is null");
    }
    this.collection = collection;
  }

  /*
   * To make a new object of Dragon i need:
   * - ID: auto
   * - name: not null and not empty
   * - coordinates not null
   * - - x: can be any number
   * - - y: not null
   * - date: auto.
   * - age: can be null, > 0.
   * - wingspan: > 0
   * - type: can be null (WATER, UNDERGROUND, AIR, FIRE)
   * - character: can be null (WISE, GOOD, CHAOTIC, CHAOTIC_EVIL)
   * - cave: can be null.
   * - - number of trreasures: not null, > 0
   */
  @Override
  public String exec(ArrayList<String> args) {
    Dragon.Builder dragonBuilder = new Dragon.Builder();
    String res = "";

    if (args.size() != 8) {
      res += "Not enough arguments for the dragon\n";
      return res;
    }

    // creating a new ID
    try {
      dragonBuilder.id(this.collection.genNewID());
    } catch (IllegalArgumentException e) {
      res += "Problem generating ID: " + e.getMessage() + "\n";
    }

    // Dragon name
    try {
      dragonBuilder.name(args.get(0));
    } catch (Exception e) {
      res += "Problem with the name: " + e.getMessage() + "\n";
    }

    // Coordinates
    try {
      Coordinates coordinate = new Coordinates(0L, 0L);
      coordinate.setX(Long.parseLong(args.get(1)));
      coordinate.setY(Long.parseLong(args.get(2)));
      dragonBuilder.coordinates(coordinate);
    } catch (Exception e) {
      res += "Problem with the coordinate: " + e.getMessage() + "\n";
    }

    // Date
    dragonBuilder.creationDate(collection.genNewDate());

    // Dragon age
    try {
      if (args.get(3).isBlank() || args.get(3).isEmpty() || args.get(3).toLowerCase().equals("null")) {
        dragonBuilder.age(null);
      } else {
        dragonBuilder.age(Integer.parseInt(args.get(3)));
      }
    } catch (Exception e) {
      res += "Problem with the age: " + e.getMessage() + "\n";
    }

    // Wingspan
    try {
      dragonBuilder.wingspan(Double.parseDouble(args.get(4)));
    } catch (Exception e) {
      res += "Problem with the wingspan: " + e.getMessage() + "\n";
    }

    // Dragon type
    try {
      if (args.get(5).isBlank() || args.get(5).isEmpty() || args.get(5).toLowerCase().equals("null")) {
        dragonBuilder.type(null);
      } else {
        dragonBuilder.type(args.get(5));
      }
    } catch (Exception e) {
      res += "Problem with the dragon type: " + e.getMessage() + "\n";
    }

    // Dragon character
    try {
      if (args.get(6).isBlank() || args.get(6).isEmpty() || args.get(6).toLowerCase().equals("null")) {
        dragonBuilder.character(null);
      } else {
        dragonBuilder.character(args.get(6));
      }
    } catch (Exception e) {
      res += "Problem with the dragon character: " + e.getMessage() + "\n";
    }

    // Cave
    try {
      if (args.get(7).isBlank() || args.get(7).isEmpty() || args.get(7).toLowerCase().equals("null")) {
        dragonBuilder.cave(null);
      } else {
        dragonBuilder.cave(new DragonCave(Double.parseDouble(args.get(7))));
      }
    } catch (Exception e) {
      res += "Problem with the dragon cave: " + e.getMessage() + "\n";
    }

    if (res.isBlank()) {
      collection.addDragon(dragonBuilder.build());
      res += "Created new Dragon: " + args.get(0) + "\n";
    }

    return res;
  }

  @Override
  public String usage() {
    String res = "";
    res += " - add {element}:\n";
    res += "Adds a new element to the collection\n";
    res += "Each attribute will be typed on a new line";
    return res;
  }
}
