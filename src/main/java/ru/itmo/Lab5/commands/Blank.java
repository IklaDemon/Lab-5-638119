package ru.itmo.Lab5.commands;

import java.util.ArrayList;

import ru.itmo.Lab5.interfaces.Command;

public class Blank implements Command {

  public Blank() {
    ;
  }

  @Override
  public String exec(ArrayList<String> args) {
    return "";
  }

  @Override
  public String usage() {
    return " - '':\nBlank input (just white spaces) will be ignored.";
  }
}
