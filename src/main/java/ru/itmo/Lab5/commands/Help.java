package ru.itmo.Lab5.commands;

import java.util.ArrayList;
import ru.itmo.Lab5.interfaces.Command;
import ru.itmo.Lab5.manager.CollectionManager;

/**
 * Help command
 */
public class Help implements Command {
  private CollectionManager collection;

  public Help(CollectionManager collection) {
    if (collection == null) {
      throw new NullPointerException("Collection manager is null");
    }
    this.collection = collection;
  }

  @Override
  public String exec(ArrayList<String> args) {
    String res = "";

    res += this.usage();

    Info info = new Info(collection);
    res += info.usage();

    Show show = new Show(collection);
    res += show.usage();

    Add add = new Add(collection);
    res += add.usage();

    Update update = new Update(collection);
    res += update.usage();

    RemoveById removeById = new RemoveById(collection);
    res += removeById.usage();

    Clear clear = new Clear(collection);
    res += clear.usage();

    Save save = new Save(collection);
    res += save.usage();

    res += "\n - execute_script file_name:\n";
    res += "Read and execute a script from the specified file. The script contains commands in the same form as they are entered by the user in interactive mode.\n";

    res += "\n - exit:\n";
    res += "Exit the app without saving.\n";

    AddIfMax addIfMax = new AddIfMax(collection);
    res += addIfMax.usage();

    AddIfMin addIfMin = new AddIfMin(collection);
    res += addIfMin.usage();

    RemoveLower removeLower = new RemoveLower(collection);
    res += removeLower.usage();

    CountGreaterThanCave countGreaterThanCave = new CountGreaterThanCave(collection);
    res += countGreaterThanCave.usage();

    PrintDescending printDescending = new PrintDescending(collection);
    res += printDescending.usage();

    PrintFieldDescendingCharacter printFieldDescendingCharacter = new PrintFieldDescendingCharacter(collection);
    res += printFieldDescendingCharacter.usage();

    return res;
  }

  @Override
  public String usage() {
    String res = "";
    res += "\n - help:\n";
    res += "Shows help about available commands\n";
    return res;
  }
}
