package ru.itmo.Lab5.interfaces;

import java.util.ArrayList;

/*
 * For scalability purposes, it's better to pass to these classes args.
 * The console input should be handled by another pice of code. Maybe
 * "UserInterface" it will get the input and call the Commands.
 * UserInterface should probably just check that the scanner is properly working.
 * The commands and collection mangers should then check the user inputs are correct
 */

public interface Command {
  public String exec(ArrayList<String> args);

  public String usage();
}
