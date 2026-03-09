# Lab5

Console application for interactive management of a collection of `Dragon` objects.

## Requirements

- Java 17 or newer
- Maven 3.9 or newer

## Environment Variable

Before running the application, set the `path_to_xml` environment variable to the path of the XML data file.

### Linux Bash / Fish
```bash
export path_to_xml=./data.xml
```
### Windows CMD
```bash
set path_to_xml=.\data.xml
```
### Windows PowerShell
```bash
$env:path_to_xml=".\data.xml"
```


## Compile
```Bash
mvn package
```

## Compile
```Bash
mvn install
```

## Run
```Bash
java -cp target/Lab5-1.0-SNAPSHOT.jar ru.itmo.Lab5.App
```

## Clean
```bash
mvn clean
```
