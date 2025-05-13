package commandLine.commands;

import commandLine.Console;
import exceptions.IllegalArguments;
import managers.FileManager;

/**
 * Команда 'save'
 * Сохраняет коллекцию в файл
 */
public class Save extends Command{
    private FileManager fileManager;
    private Console console;

    public Save(Console console, FileManager fileManager) {
        super("save", ": сохранить коллекцию в файл");
        this.fileManager = fileManager;
        this.console = console;
    }

    /**
     * Исполнить команду
     * @param args аргументы команды
     * @throws IllegalArguments неверные аргументы команды
     */
    @Override
    public void execute(String args) throws IllegalArguments {
        if (!args.isBlank()) throw new IllegalArguments();
        fileManager.saveObjects();
        console.println("Песонажи сохранены успешно");
    }
}