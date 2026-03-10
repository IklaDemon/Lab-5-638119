package ru.itmo.Lab5.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.PriorityQueue;

import ru.itmo.Lab5.dragon.Dragon;
import ru.itmo.Lab5.interfaces.Writer;

/**
 * Writes Dragon objects to an XML file.
 */
public class XMLWriter implements Writer {
  private String filePath;

  public XMLWriter(String filePath) {
    setFilePath(filePath);
  }

  @Override
  public boolean write(PriorityQueue<Dragon> input) {
    try (FileWriter writer = new FileWriter(filePath, false)) {
      writer.write("<dragons>\n");
      for (Dragon dragon : input) {
        writer.write("    <dragon>\n");
        writer.write("        <id>" + dragon.getId() + "</id>\n");
        writer.write("        <name>" + dragon.getName() + "</name>\n");
        writer.write("        <coordinates>\n");
        writer.write("            <x>" + dragon.getCoordinates().getX() + "</x>\n");
        writer.write("            <y>" + dragon.getCoordinates().getY() + "</y>\n");
        writer.write("        </coordinates>\n");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        writer.write("        <creationDate>" + formatter.format(dragon.getCreationDate()) + "</creationDate>\n");
        if (dragon.getAge() == null)
          writer.write("        <age></age>\n");
        else
          writer.write("        <age>" + dragon.getAge() + "</age>\n");
        writer.write("        <wingspan>" + dragon.getWingspan() + "</wingspan>\n");
        if (dragon.getType() == null)
          writer.write("        <type></type>\n");
        else
          writer.write("        <type>" + dragon.getType().name() + "</type>\n");
        if (dragon.getCharacter() == null)
          writer.write("        <character></character>\n");
        else
          writer.write("        <character>" + dragon.getCharacter().name() + "</character>\n");
        if (dragon.getCave() == null) {
          writer.write("        <cave></cave>\n");
        } else {
          writer.write("        <cave>\n");
          writer.write("            <numberOfTreasures>" +
              dragon.getCave().getNumberOfTreasures() +
              "</numberOfTreasures>\n");
          writer.write("        </cave>\n");
        }
        writer.write("    </dragon>\n");
      }
      writer.write("</dragons>\n");
    } catch (IOException e) {
      System.out.println("Error while opening file [" + filePath + "]: " + e.getMessage());
      return false;
    }
    return true;
  }

  /*
   * In this setter I'm just checking if I can write the file if exists...
   * If file does not exists then process can just create it (in this case
   * permissions should not be a problem).
   */
  public void setFilePath(String filePath) {
    if (filePath == null) {
      throw new NullPointerException("filePath is null. Should not be null");
    }
    if (filePath.equals("")) {
      throw new IllegalArgumentException("filePath = " + filePath + ". Should not be empty");
    }

    File file = new File(filePath);
    if (file.exists() && file.isFile()) {
      if (file.canWrite())
        this.filePath = filePath;
      else
        throw new IllegalArgumentException("no write permission");
    }
  }
}
