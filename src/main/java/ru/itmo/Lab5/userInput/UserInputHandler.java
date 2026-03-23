/**
 * TODO: For the the script command I should be able to call more scripts until a loop is not created...
 * TODO: I should try to find out how to execute all of this using a hashmap...
 * TODO: When I run a script, and in the script is present the add or update command. All the data shoul be in-line
 * TODO: I will have to test everything better
 */

package ru.itmo.Lab5.userInput;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Scanner;

import ru.itmo.Lab5.commands.Add;
import ru.itmo.Lab5.commands.AddIfMax;
import ru.itmo.Lab5.commands.AddIfMin;
import ru.itmo.Lab5.commands.Blank;
import ru.itmo.Lab5.commands.Clear;
import ru.itmo.Lab5.commands.CountGreaterThanCave;
import ru.itmo.Lab5.commands.Exit;
import ru.itmo.Lab5.commands.Help;
import ru.itmo.Lab5.commands.Info;
import ru.itmo.Lab5.commands.PrintDescending;
import ru.itmo.Lab5.commands.PrintFieldDescendingCharacter;
import ru.itmo.Lab5.commands.RemoveById;
import ru.itmo.Lab5.commands.RemoveLower;
import ru.itmo.Lab5.commands.Save;
import ru.itmo.Lab5.commands.Script;
import ru.itmo.Lab5.commands.Show;
import ru.itmo.Lab5.commands.Update;
import ru.itmo.Lab5.enums.DragonCharacter;
import ru.itmo.Lab5.enums.DragonType;
import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * This class handles the user interaction with the app.
 * Asks input to the user and sanitizes it a bit, then gives the input to the
 * proper command
 */
public class UserInputHandler {
  private Map<String, Command> commands;
  CollectionManager collectionManager;
  Queue<String> commandBuffer;
  private String res;

  public UserInputHandler(CollectionManager collectionManager) {
    if (collectionManager == null)
      throw new NullPointerException();
    this.collectionManager = collectionManager;
    this.commandBuffer = new ArrayDeque<>();
    this.commands = new LinkedHashMap<>();
    this.commands.put("info", new Info(this.collectionManager));
    this.commands.put("show", new Show(this.collectionManager));
    this.commands.put("add", new Add(this.collectionManager));
    this.commands.put("update", new Update(this.collectionManager));
    this.commands.put("remove", new RemoveById(this.collectionManager));
    this.commands.put("clear", new Clear(this.collectionManager));
    this.commands.put("save", new Save(this.collectionManager));
    // this.commands.put("executes", new ExecuteScript(this.collectionManager));
    this.commands.put("addmax", new AddIfMax(this.collectionManager));
    this.commands.put("addmin", new AddIfMin(this.collectionManager));
    this.commands.put("removel", new RemoveLower(this.collectionManager));
    this.commands.put("countgtc", new CountGreaterThanCave(this.collectionManager));
    this.commands.put("printd", new PrintDescending(this.collectionManager));
    this.commands.put("printfdc", new PrintFieldDescendingCharacter(this.collectionManager));
    this.commands.put("exit", new Exit());
    this.commands.put("", new Blank());
    this.commands.put("help", new Help(this.commands));
  }

  public void runCLI() {
    ArrayList<String> args = new ArrayList<String>();
    String[] command;
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

      command = commandBuffer.poll().split("\\s+");

      if (command[0].equals("executes")) {
        Script script = new Script();
        script.load("./script-1.txt");
        System.out.println(script.getCommands().toString());
        continue;
      }

      Command out = commands.get(command[0]);
      if (out == null) {
        System.out.print(command[0] + " is not a valid command. Type 'help' for a list of valid commands\n");
      } else {
        this.res = out.exec(args);
      }

      if (this.res != null && this.res.equals("exit")) {
        break;
      } else {
        System.out.print(res);
      }

      // switch (command[0]) {
      // case "":
      // continue;
      //
      // case "help":
      // Help help = new Help(collectionManager);
      // System.out.print(help.exec(null) + "\n");
      // break;
      //
      // case "info":
      // Info info = new Info(collectionManager);
      // System.out.print(info.exec(null));
      // break;
      //
      // case "show":
      // Show show = new Show(collectionManager);
      // System.out.print(show.exec(null));
      // break;
      //
      // case "add":
      // Add add = new Add(collectionManager);
      // System.out.print(add.exec(this.getDragonFromUser(scanner)));
      // break;
      //
      // case "update":
      // if (command.length < 2) {
      // System.out.println("Not enough arguments");
      // continue;
      // }
      // try {
      // Long.parseLong(command[1]);
      // } catch (Exception e) {
      // System.out.println("Invalid id");
      // continue;
      // }
      // args = this.getDragonFromUser(scanner);
      // args.add(command[1]);
      // Update update = new Update(collectionManager);
      // System.out.print(update.exec(args));
      // break;
      //
      // // + ID
      // case "remove_by_id":
      // if (command.length < 2) {
      // System.out.println("Not enough arguments");
      // continue;
      // }
      // try {
      // Long.parseLong(command[1]);
      // } catch (Exception e) {
      // System.out.println("Long is needed for second argument");
      // }
      // args.clear();
      // args.add(command[1]);
      // RemoveById removeById = new RemoveById(collectionManager);
      // System.out.print(removeById.exec(args));
      // break;
      //
      // case "clear":
      // Clear clear = new Clear(collectionManager);
      // System.out.println(clear.exec(args));
      // break;
      //
      // case "save":
      // Save save = new Save(collectionManager);
      // System.out.println(save.exec(null));
      // break;
      //
      // // TODO: fix the script
      // case "execute_script":
      // Set<String> scriptFilesHistory = new HashSet<String>(); // I should use this
      //
      // if (command.length < 2) {
      // System.out.println("Not enough arguments");
      // continue;
      // }
      // File file = new File(command[1]);
      // if (!file.exists() || file.isDirectory()) {
      // System.out.println("invalid file");
      // continue;
      // }
      // try (Scanner fileReader = new Scanner(file)) {
      // while (fileReader.hasNextLine()) {
      // String line = fileReader.nextLine().strip();
      // if (!line.isEmpty() && !line.contains("execute_script")) {
      // System.out.println(line);
      // commandBuffer.offer(line);
      // }
      // }
      // } catch (FileNotFoundException e) {
      // System.out.println("invalid file: " + e.getMessage());
      // }
      // break;
      //
      // case "exit":
      // exit = true;
      // continue;
      //
      // case "add_if_max":
      // AddIfMax addIfMax = new AddIfMax(collectionManager);
      // System.out.println(addIfMax.exec(this.getDragonFromUser(scanner)));
      // continue;
      //
      // case "add_if_min":
      // AddIfMin addIfMin = new AddIfMin(collectionManager);
      // System.out.println(addIfMin.exec(this.getDragonFromUser(scanner)));
      // continue;
      //
      // case "remove_lower":
      // RemoveLower removeLower = new RemoveLower(collectionManager);
      // System.out.println(removeLower.exec(this.getDragonFromUser(scanner)));
      // continue;
      //
      // case "count_greater_than_cave":
      // if (command.length < 2) {
      // System.out.println("Not enough arguments");
      // continue;
      // }
      // try {
      // Double.parseDouble(command[1]);
      // } catch (Exception e) {
      // System.out.println("Double is needed as second argument");
      // continue;
      // }
      // args.clear();
      // args.add(command[1]);
      // CountGreaterThanCave countGreaterThanCave = new
      // CountGreaterThanCave(collectionManager);
      // System.out.println(countGreaterThanCave.exec(args));
      // continue;
      //
      // case "print_descending":
      // PrintDescending printDescending = new PrintDescending(collectionManager);
      // System.out.println(printDescending.exec(null));
      // continue;
      //
      // case "print_field_descending_character":
      // PrintFieldDescendingCharacter printFieldDescendingCharacter = new
      // PrintFieldDescendingCharacter(
      // collectionManager);
      // System.out.println(printFieldDescendingCharacter.exec(null));
      // continue;
      //
      // default:
      // System.out.println("Invalid input. Type help for set of available commands");
      // continue;
      // }
    }

    scanner.close();
  }

  private ArrayList<String> getDragonFromUser(Scanner scanner) {
    boolean loop = true;
    int state = 0;
    ArrayList<String> args = new ArrayList<String>();
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
          args.add(arg.strip());
          state = 1;
          break;
        case 1: // coordinate x: any number. long
          System.out.print("Coordinate. x: ");
          arg = scanner.nextLine();
          try {
            Long.parseLong(arg);
          } catch (NumberFormatException e) {
            System.out.println("Type a valid number");
            continue;
          }
          args.add(arg.strip());
          state = 2;
          break;
        case 2: // coordinate y: not null. Long
          System.out.print("Coordinate. y: ");
          arg = scanner.nextLine();
          try {
            Long.parseLong(arg);
          } catch (NumberFormatException e) {
            System.out.println("Type a valid number");
            continue;
          }
          args.add(arg.strip());
          state = 3;
          break;
        case 3: // age: can be null, > 0
          System.out.print("Age: ");
          arg = scanner.nextLine();
          if (arg.isBlank() || arg.isEmpty()) {
            args.add("");
            state = 4;
          } else {
            try {
              int a = Integer.parseInt(arg);
              if (a <= 0) {
                System.out.println("Age should be > 0");
                continue;
              }
            } catch (NumberFormatException e) {
              System.out.println("Type a valid number");
              continue;
            }
            args.add(arg.strip());
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
            System.out.println("Type a valid number");
            continue;
          }
          args.add(arg.strip());
          state = 5;
          break;
        case 5: // type: can be null (WATER, UNDERGROUND, AIR, FIRE)
          System.out.print("Dragon Type (WATER/UNDERGROUND/AIR/FIRE): ");
          arg = scanner.nextLine();
          if (arg.isBlank() || arg.isEmpty()) {
            args.add("");
            state = 6;
          } else {
            try {
              DragonType.valueOf(arg);
              args.add(arg.strip());
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
            args.add("");
            state = 7;
          } else {
            try {
              DragonCharacter.valueOf(arg);
              args.add(arg.strip());
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
            args.add("");
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
            args.add(arg.strip());
            state = 9;
          } catch (NumberFormatException e) {
            System.out.println("Type a valid number");
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
