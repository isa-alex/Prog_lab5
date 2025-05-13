package commandLine.commands;

import commandLine.Console;
import exceptions.IllegalArguments;
import managers.CollectionManager;
import models.HumanBeing;

import java.util.Collection;

/**
 * Команда 'show'
 * Выводит в стандартный поток вывода все элементы коллекции в строковом представлении
 */
public class Show extends Command {
    private CollectionManager collectionManager;
    private Console console;

    public Show(Console console, CollectionManager collectionManager) {
        super("show", ": вывести в стандартный поток вывода " +
                "все элементы коллеции в строковом представлении");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Исполнить команду
     * @param args аргументы команды
     * @throws IllegalArguments пр неверных аргументах команды
     */
    @Override
    public void execute(String args) throws IllegalArguments {
        if (!args.isBlank()) throw new IllegalArguments();
        Collection<HumanBeing> collection = collectionManager.getCollection();
        if (collection == null || collection.isEmpty()) {
            console.printError("Коллекция не создана");
            return;
        }
        console.println(collection.toString());
    }
}
