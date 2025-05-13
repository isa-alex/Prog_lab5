package commandLine.commands;

import commandLine.*;
import exceptions.IllegalArguments;
import managers.CollectionManager;

/**
 * Команда 'info'
 * Выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
 */
public class Info extends Command {
    private CollectionManager collectionManager;
    private Console console;

    public Info (Console console, CollectionManager collectionManager) {
        super("info", ": вывести в стандартный поток вывода информацию " +
                "о коллекции (тип, дата инициализации, количество элементов и т.д.");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Исполнить команду
     * @param args аргументы команды
     * @throws IllegalArguments при неверных аргументах команды
     */
    @Override
    public void execute(String args) throws IllegalArguments {
        if (!args.isBlank()) throw new IllegalArguments();
        String lastInitTime = (collectionManager. getLastInitTime() == null)
                ? "В сессии коллекция не инициализирована "
                : collectionManager.getLastInitTime().toString();
        String lastSaveTime = (collectionManager.getLastSaveTime() == null)
                ? "В сессии коллекция не инициализирована "
                : collectionManager.getLastSaveTime().toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Сведения о коллекции: \n")
                .append("Тип: " + collectionManager.collectionType() + "\n")
                .append("Количество элементов:  " + collectionManager.collectionSize() + "\n")
                .append("Дата последней инициализации: " + lastInitTime + "\n")
                .append("Дата последней изменения: " + lastSaveTime + "\n");
        console.println(stringBuilder.toString());
    }
}
