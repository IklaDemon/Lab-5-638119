package ru.itmo.Lab5;

import ru.itmo.Lab5.console.CLI;
import ru.itmo.Lab5.manager.CollectionManager;
import ru.itmo.Lab5.reader.XMLParser;
import ru.itmo.Lab5.writer.XMLWriter;

/**
 * Starts the application and initializes the main components required for work
 * with the collection.
 *
 * <p>
 * The method reads the {@code path_to_xml} environment variable, creates
 * the parser, writer and collection manager, and then starts the command-line
 * interface. If the environment variable is missing or empty, usage
 * instructions
 * are printed to the console.
 */
public class App {

  /**
   * Launches the application.
   *
   * <p>
   * Checks the {@code path_to_xml} environment variable, initializes the
   * XML parser, XML writer and collection manager, and starts the command-line
   * interface.
   *
   * @param args command-line arguments passed to the application
   */
  public static void main(String[] args) {
    try {
      String env = System.getenv("path_to_xml");
      if (env != null && !env.isBlank()) {
        XMLParser xmlParser = new XMLParser(env);
        XMLWriter xmlWriter = new XMLWriter(env);
        CollectionManager collectionManager = new CollectionManager(xmlParser, xmlWriter, env);

        // getting the commands from the user...
        CLI userInputHandler = new CLI(collectionManager);
        userInputHandler.runCLI();

      } else {
        System.out.println("No environment variable set");
        System.out.println("Please set 'export path_to_xml=[path_to_xml]' on Linux Bash/Fish.");
        System.out.println("Please set 'set path_to_xml=[path_to_xml]'    on Windows CMD.");
        System.out.println("Please set '$env:path_to_xml=[path_to_xml]'   on Windows PowerShell.");
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}
