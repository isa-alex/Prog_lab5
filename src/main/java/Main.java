import exceptions.ExitObligated;
import managers.*;
import commandLine.Console;
import commandLine.commands.*;

import java.util.List;

public class Main {
    public static void main(String[] args){
        Console console = new Console();
        CollectionManager collectionManager = new CollectionManager();
        FileManager fileManager = new FileManager(console, collectionManager);
        CommandManager commandManager = new CommandManager();
        try{
            fileManager.findFile();
            fileManager.createObjects();
        } catch (ExitObligated e){
            console.println("До свидания!");
            return;
        }

        commandManager.addCommand(List.of(
                new Help(console, commandManager),
                new Info(console, collectionManager),
                new Show(console, collectionManager),
                new AddElement(console, collectionManager),
                new Update(console, collectionManager),
                new RemoveById(console, collectionManager),
                new Clear(console, collectionManager),
                new Save(console, fileManager),
                new Execute(console, fileManager, commandManager),
                new Exit(),
                new AddIfMax(console, collectionManager),
                new RemoveLower(console, collectionManager),
                new History(console, commandManager),
                new FilterGreater(console, collectionManager),
                new PrintDescending(console, collectionManager),
                new PrintAscending(console, collectionManager)
        ));
        new RuntimeManager(console, commandManager).interactiveMode();
    }
}