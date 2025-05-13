package commandLine.commands;

import managers.CollectionManager;
import commandLine.Console;

/**
 * Команда 'clear'
 * Очищает коллекцию
 */
public class Clear extends Command{
    private CollectionManager collectionManager;
    private Console console;

    public Clear(Console console, CollectionManager collectionManager) {
        super("clear", ": очистить коллекцию");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Исполнить команду
     * @param args аргументы команды
     */
    @Override
    public void execute(String args) {
        collectionManager.clear();
        console.println("Все персонажи удалены");
    }
}