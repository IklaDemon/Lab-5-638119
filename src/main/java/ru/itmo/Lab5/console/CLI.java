/**
 * TODO: убрать валидацию из команды
 * TODO: уменьшить InputReader (the user input handler: the if else are awful)
 */

package ru.itmo.Lab5.console;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Scanner;

import ru.itmo.Lab5.commands.*;
import ru.itmo.Lab5.enums.DragonCharacter;
import ru.itmo.Lab5.enums.DragonType;
import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * This class handles the user interaction with the app.
 * Asks input to the user and sanitizes it a bit, then gives the input to the
 * proper command
 */
public class CLI {
  private Map<String, Command> commands;
  CollectionManager collectionManager;
  Queue<String> commandBuffer;
  private String res;

  public CLI(CollectionManager collectionManager) {
    if (collectionManager == null)
      throw new NullPointerException();
    this.collectionManager = collectionManager;
    this.commandBuffer = new ArrayDeque<>();
    this.commands = new LinkedHashMap<>();
    this.commands.put("info", new Info(this.collectionManager));
    this.commands.put("show", new Show(this.collectionManager));
    this.commands.put("add", new Add(this.collectionManager));
    this.commands.put("update", new Update(this.collectionManager));
    this.commands.put("remove_by_id", new RemoveById(this.collectionManager));
    this.commands.put("clear", new Clear(this.collectionManager));
    this.commands.put("save", new Save(this.collectionManager));
    this.commands.put("add_if_max", new AddIfMax(this.collectionManager));
    this.commands.put("add_if_min", new AddIfMin(this.collectionManager));
    this.commands.put("remove_lower", new RemoveLower(this.collectionManager));
    this.commands.put("count_greater_than_cave", new CountGreaterThanCave(this.collectionManager));
    this.commands.put("print_descending", new PrintDescending(this.collectionManager));
    this.commands.put("print_field_descending_character", new PrintFieldDescendingCharacter(this.collectionManager));
    this.commands.put("exit", new Exit());
    this.commands.put("", new Blank());
    this.commands.put("help", new Help(this.commands));
  }

  public void runCLI() {
    String[] inputCommand;
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.print("> ");

      if (commandBuffer.isEmpty()) {
        try {
          commandBuffer.offer(scanner.nextLine().strip());
        } catch (NoSuchElementException e) {
          System.out.println("CTRL + D generates a NoSuchElementException with message: " + e.getMessage() + ".");
          System.out.println("App is terminated");
          scanner.close();
          return;
        } catch (IllegalStateException e) {
          System.out.println(e.getMessage());
          scanner.close();
          return;
        }
      }

      inputCommand = commandBuffer.poll().split("\\s+", 1);

      if (inputCommand[0].equals("execute_script")) {
        if (inputCommand.length != 2) {
          System.out.print("Wrong number of arguments\n");
          continue;
        }
        Script script = new Script();
        try {
          script.load(inputCommand[1]);
        } catch (IllegalArgumentException e) {
          System.out.println(e.getMessage());
          continue;
        }
        for (String string : script.getCommands()) {
          commandBuffer.offer(string);
        }
        continue;
      }

      Command command = commands.get(inputCommand[0]);
      if (command == null) {
        System.out.print(inputCommand[0] + " is not a valid command. Type 'help' for a list of valid commands\n");
      } else {

        if (command.requiresDragon() == true) {
          if (inputCommand.length == 2) {
            command.exec(inputCommand[1]);
          } else if (inputCommand.length == 1) {
            this.res = command.exec(this.getDragonFromUser(scanner));
          } else {
            System.out.println("Wrong number of arguments");
            continue;
          }
        } else {
          if (command.numberOfArgs() == (inputCommand.length - 1)) {
            this.res = command.exec(inputCommand[1]);
          } else {
            System.out.println("Wrong number of arguments");
            continue;
          }
        }
      }

      if (this.res != null && this.res.equals("exit")) {
        break;
      } else {
        System.out.print(res);
      }
    }
    scanner.close();
  }

  private String getDragonFromUser(Scanner scanner) {
    boolean loop = true;
    int state = 0;
    String args = "";
    String arg;

    while (loop) {
      switch (state) {
        case 0: // - name: not null and not empty
          System.out.print("Dragon name: ");
          arg = scanner.nextLine();
          if (arg.isBlank() || arg.isEmpty()) {
            System.out.println("Name should not be empty or null");
            continue;
          }
          args += arg.strip();
          state = 1;
          break;
        case 1: // coordinate x: any number. long
          System.out.print("Coordinate. x: ");
          arg = scanner.nextLine();
          try {
            Long.parseLong(arg);
          } catch (NumberFormatException e) {
            System.out.println("Type a valid number.");
            continue;
          }
          args += "," + arg.strip();
          state = 2;
          break;
        case 2: // coordinate y: not null. Long
          System.out.print("Coordinate. y: ");
          arg = scanner.nextLine();
          try {
            Long.parseLong(arg);
          } catch (NumberFormatException e) {
            System.out.println("Type a valid number. Cannot be null.");
            continue;
          }
          args += "," + arg.strip();
          state = 3;
          break;
        case 3: // age: can be null, > 0
          System.out.print("Age: ");
          arg = scanner.nextLine();
          if (arg.isBlank() || arg.isEmpty()) {
            args += "," + "";
            state = 4;
          } else {
            try {
              int a = Integer.parseInt(arg);
              if (a <= 0) {
                System.out.println("Age should be > 0.");
                continue;
              }
            } catch (NumberFormatException e) {
              System.out.println("Type a valid number. Should be > 0. Not null");
              continue;
            }
            args += "," + arg.strip();
            state = 4;
          }
          break;
        case 4: // wingspan: > 0.
          System.out.print("Wingspan: ");
          arg = scanner.nextLine();
          if (arg.isBlank() || arg.isEmpty()) {
            System.out.println("Wingspan should not be empty or null.");
            continue;
          }
          try {
            Double a = Double.parseDouble(arg);
            if (a <= 0) {
              System.out.println("Wingspan should be > 0");
              continue;
            }
          } catch (NumberFormatException e) {
            System.out.println("Wingspan should not be empty or null. Should be > 0");
            continue;
          }
          args += "," + arg.strip();
          state = 5;
          break;
        case 5: // type: can be null (WATER, UNDERGROUND, AIR, FIRE)
          System.out.print("Dragon Type (WATER/UNDERGROUND/AIR/FIRE): ");
          arg = scanner.nextLine();
          if (arg.isBlank() || arg.isEmpty()) {
            args += "," + "";
            state = 6;
          } else {
            try {
              DragonType.valueOf(arg.toUpperCase());
              args += "," + arg.strip().toUpperCase();
              state = 6;
            } catch (Exception e) {
              System.out.println("Invalid option.");
              continue;
            }
          }
          break;
        case 6: // character: can be null (WISE, GOOD, CHAOTIC, CHAOTIC_EVIL)
          System.out.print("Dragon Character (WISE, GOOD, CHAOTIC, CHAOTIC_EVIL): ");
          arg = scanner.nextLine();
          if (arg.isBlank() || arg.isEmpty()) {
            args += "," + " ";
            state = 7;
          } else {
            try {
              DragonCharacter.valueOf(arg.toUpperCase());
              args += "," + arg.strip().toUpperCase();
              state = 7;
            } catch (Exception e) {
              System.out.println("Invalid option.");
              continue;
            }
          }
          break;
        case 7: // cave: can be null
          System.out.print("Cave (Y/n): ");
          arg = scanner.nextLine();
          if (arg.isBlank() || arg.isEmpty() || arg.toLowerCase().equals("y")) {
            state = 8;
          } else if (arg.toLowerCase().equals("n")) {
            args += "," + "";
            state = 9;
          } else {
            continue;
          }
          break;
        case 8: // cave. number of trasures: not null > 0. Double
          System.out.print("Cave. Number of treasures: ");
          arg = scanner.nextLine();
          try {
            Long.parseLong(arg);
            args += "," + arg.strip();
            state = 9;
          } catch (NumberFormatException e) {
            System.out.println("Type a valid number. Should be > 0. Not null");
            continue;
          }
          break;
        default:
          loop = false;
          break;
      }
    }
    return args;
  }
}
