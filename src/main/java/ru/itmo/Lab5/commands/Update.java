package ru.itmo.Lab5.commands;

import java.util.ArrayList;
import ru.itmo.Lab5.dragon.Coordinates;
import ru.itmo.Lab5.dragon.Dragon;
import ru.itmo.Lab5.dragon.DragonCave;
import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * Update command
 */
public class Update implements Command {
  private CollectionManager collection;

  public Update(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collection manager is null");
    }
    this.collection = collection;
  }

  @Override
  public String exec(ArrayList<String> args) {

    long ID;
    Dragon.Builder dragonBuilder = new Dragon.Builder();
    String res = "";

    // creating a new ID
    try {
      ID = Long.parseLong(args.get(8));
      dragonBuilder.id(ID);
    } catch (IllegalArgumentException e) {
      res += "Problem with the id: " + e.getMessage() + "\n";
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

    // Dragon age
    try {
      if (args.get(3).isBlank() || args.get(3).isEmpty()) {
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
      if (args.get(5).isBlank() || args.get(5).isEmpty()) {
        dragonBuilder.type(null);
      } else {
        dragonBuilder.type(args.get(5));
      }
    } catch (Exception e) {
      res += "Problem with the dragon type: " + e.getMessage() + "\n";
    }

    // Dragon character
    try {
      if (args.get(6).isBlank() || args.get(6).isEmpty()) {
        dragonBuilder.character(null);
      } else {
        dragonBuilder.character(args.get(6));
      }
    } catch (Exception e) {
      res += "Problem with the dragon character: " + e.getMessage() + "\n";
    }

    // Cave
    try {
      if (args.get(7).isBlank() || args.get(7).isEmpty()) {
        dragonBuilder.cave(null);
      } else {
        dragonBuilder.cave(new DragonCave(Double.parseDouble(args.get(7))));
      }
    } catch (Exception e) {
      res += "Problem with the dragon cave: " + e.getMessage() + "\n";
    }

    try {
      dragonBuilder.creationDate(collection.findById(Long.parseLong(args.get(8))).getCreationDate());
      collection.update(Long.parseLong(args.get(8)), dragonBuilder.build());
    } catch (Exception e) {
      res += "Not possible to update: " + e.getMessage() + "\n";
    }

    if (res.isBlank()) {
      res += "Dragon updated" + "\n";
    }

    return res;
  }

  @Override
  public String usage() {
    String res = "";
    res += "\n - update id {element}:\n";
    res += "Updates the element with the specified id\n";
    res += "Each attribute will be typed on a new line\n";
    return res;
  }
}
