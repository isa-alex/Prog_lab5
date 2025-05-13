package commandLine.commands;

import exceptions.IllegalArguments;
import managers.CollectionManager;
import commandLine.Console;

/**
 * Команда 'remove_by_id'
 * Удаляет элемент из коллекции по его id
 */
public class RemoveById extends Command {
    private CollectionManager collectionManager;
    private Console console;

    public RemoveById(Console console, CollectionManager collectionManager) {
        super("remove_by_id", " id: удалить элемент из коллекции по его id");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Исполнить команду
     * @param args аргументы команды
     * @throws IllegalArguments неверные аргументы команды
     */
    @Override
    public void execute(String args) throws IllegalArguments{
        if (args.isBlank()) throw new IllegalArguments();
        class NoSuchId extends RuntimeException {
        }
        try {
            int id = Integer.parseInt(args.trim());
            if (!collectionManager.checkExist(id)) throw new NoSuchId();
            collectionManager.removeElement(collectionManager.getById(id));
            console.println("Персонаж удален успешно");
        } catch (NoSuchId err) {
            console.printError("В коллекции нет персонажа с таким id");
        } catch (NumberFormatException exception) {
            console.printError("id должно быть числом");
        }
    }
}