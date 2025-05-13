package commandLine.commands;

import commandLine.Console;
import managers.CollectionManager;
import exceptions.*;
import models.form.HumanBeingForm;

/**
 * команда 'add {element}'
 * добавить новый элемент с заданным ключом
 */
public class AddElement extends Command {
    private CollectionManager collectionManager;
    private Console console;

    public AddElement(Console console, CollectionManager collectionManager) {
        super("add", " {element}: добавить новый элемент с заданным ключом");
        this.collectionManager = collectionManager;
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
        try {
            console.println("Создание нового персонажа");
            collectionManager.addElement(new HumanBeingForm(console).build());
        } catch (InvalidForm invalidForm) {
            console.printError("Поля персонажа не валидны! Персонаж не создан :(");
        } catch (ExceptionInFileMode e) {
            console.printError("Поля в файле не валидны! Персонаж не создан");
        }
    }
}
