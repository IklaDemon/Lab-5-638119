package ru.itmo.Lab5.console;

import java.util.ArrayDeque;
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
 * Handles user interaction through the command-line interface.
 *
 * <p>
 * This class reads user input, dispatches commands, supports script execution,
 * and collects dragon data interactively when required.
 */
public class CLI {
  private Map<String, Command> commands;
  CollectionManager collectionManager;
  Queue<String> commandBuffer;
  private String res;

  /**
   * Creates a CLI instance and registers all available commands.
   *
   * @param collectionManager collection manager used by the commands
   * @throws NullPointerException if {@code collectionManager} is null
   */
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

  /**
   * Starts the command-line interface loop.
   *
   * <p>
   * Reads commands from standard input or from the internal command buffer,
   * executes them, and prints the result until the exit command is received
   * or the input stream is closed.
   */
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

      inputCommand = commandBuffer.poll().split("\\s+", 2);

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
        continue;
      } else {

        String arg = "";

        for (int i = 1; i < inputCommand.length; i++) {
          arg += inputCommand[i];
          if (i < (inputCommand.length - 1)) {
            arg += " ";
          }
        }

        if (command.requiresDragon() && (arg.split(" ").length == (command.numberOfArgs() - 1) || arg.isBlank())) {
          arg += " " + this.getDragonFromUser(scanner);
          arg = arg.strip();
        }
        this.res = command.exec(arg);
      }

      if (this.res != null && this.res.equals("exit")) {
        break;
      } else {
        System.out.print(res);
      }
    }
    scanner.close();
  }

  /**
   * Interactively reads dragon field values from the user.
   *
   * <p>
   * The method validates each entered value and returns a comma-separated
   * string representation of the dragon data.
   *
   * @param scanner scanner used to read user input
   * @return comma-separated dragon data
   */
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
          if (arg.matches(".*\\s+.*")) {
            System.out.println("White spaces are not allowed");
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
            args += "," + "null";
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
            args += "," + "null";
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
            args += "," + "null";
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
            args += "," + "null";
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
