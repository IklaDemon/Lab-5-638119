package ru.itmo.Lab5.commands;

import java.util.ArrayList;

import ru.itmo.Lab5.dragon.Coordinates;
import ru.itmo.Lab5.dragon.Dragon;
import ru.itmo.Lab5.dragon.DragonCave;
import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * Coommand that addsIfMax a dragon
 */
public class AddIfMax implements Command {
  private CollectionManager collection;

  public AddIfMax(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collection manager is null");
    }
    this.collection = collection;
  }

  @Override
  public String exec(String arg) {
    String res = "";
    // Dragon.Builder dragonBuilder = new Dragon.Builder();
    //
    // if (args.size() != 8) {
    // res += "Not enough arguments for the dragon\n";
    // return res;
    // }
    //
    // try {
    // dragonBuilder.id(this.collection.genNewID());
    // } catch (IllegalArgumentException e) {
    // res += "Problem generating ID: " + e.getMessage() + "\n";
    // }
    // try {
    // dragonBuilder.name(args.get(0));
    // } catch (Exception e) {
    // res += "Problem with the name: " + e.getMessage() + "\n";
    // }
    // try {
    // Coordinates coordinate = new Coordinates(0L, 0L);
    // coordinate.setX(Long.parseLong(args.get(1)));
    // coordinate.setY(Long.parseLong(args.get(2)));
    // dragonBuilder.coordinates(coordinate);
    // } catch (Exception e) {
    // res += "Problem with the coordinate: " + e.getMessage() + "\n";
    // }
    // dragonBuilder.creationDate(collection.genNewDate());
    // try {
    // if (args.get(3).isBlank() || args.get(3).isEmpty() ||
    // args.get(3).toLowerCase().equals("null")) {
    // dragonBuilder.age(null);
    // } else {
    // dragonBuilder.age(Integer.parseInt(args.get(3)));
    // }
    // } catch (Exception e) {
    // res += "Problem with the age: " + e.getMessage() + "\n";
    // }
    // try {
    // dragonBuilder.wingspan(Double.parseDouble(args.get(4)));
    // } catch (Exception e) {
    // res += "Problem with the wingspan: " + e.getMessage() + "\n";
    // }
    // try {
    // if (args.get(5).isBlank() || args.get(5).isEmpty() ||
    // args.get(5).toLowerCase().equals("null")) {
    // dragonBuilder.type(null);
    // } else {
    // dragonBuilder.type(args.get(5));
    // }
    // } catch (Exception e) {
    // res += "Problem with the dragon type: " + e.getMessage() + "\n";
    // }
    // try {
    // if (args.get(6).isBlank() || args.get(6).isEmpty() ||
    // args.get(6).toLowerCase().equals("null")) {
    // dragonBuilder.character(null);
    // } else {
    // dragonBuilder.character(args.get(6));
    // }
    // } catch (Exception e) {
    // res += "Problem with the dragon character: " + e.getMessage() + "\n";
    // }
    // try {
    // if (args.get(7).isBlank() || args.get(7).isEmpty() ||
    // args.get(7).toLowerCase().equals("null")) {
    // dragonBuilder.cave(null);
    // } else {
    // dragonBuilder.cave(new DragonCave(Double.parseDouble(args.get(7))));
    // }
    // } catch (Exception e) {
    // res += "Problem with the dragon cave: " + e.getMessage() + "\n";
    // }
    //
    // Dragon newDragon = dragonBuilder.build();
    // Dragon maxDragon = collection.getMax();
    //
    // if (maxDragon == null) {
    // collection.addDragon(newDragon);
    // res += "Added new Dragon: " + newDragon.getName() + "\n";
    // } else {
    // if (newDragon.compareTo(maxDragon) > 0) {
    // collection.addDragon(newDragon);
    // res += "Added new Dragon: " + newDragon.getName() + "\n";
    // } else {
    // res += newDragon.getName() + " is smaller than " + maxDragon.getName() +
    // "\n";
    // }
    // }

    return res;
  }

  @Override
  public String usage() {
    String res = "";
    res += " - add_if_max {element}:\n";
    res += "Adds the element only if it is bigger than the current biggest present";
    return res;
  }

  @Override
  public int numberOfArgs() {
    return 1;
  }

  @Override
  public boolean requiresDragon() {
    return true;
  }
}
