package commandLine.commands;

import commandLine.Console;
import exceptions.ExceptionInFileMode;
import exceptions.IllegalArguments;
import managers.CollectionManager;
import models.HumanBeing;
import models.form.HumanBeingForm;

import java.util.Collection;
import java.util.Objects;

/**
 * Команда 'remove_lower'
 * Удаляет из коллекции все элементы, меньшие, чем заданный
 */
public class RemoveLower extends Command{
    private CollectionManager collectionManager;
    private Console console;

    public RemoveLower(Console console, CollectionManager collectionManager) {
        super("remove_lower", " {element} : удалить из коллекции все элементы, меньшие, чем заданный");
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
        class NoElements extends RuntimeException{

        }
        try {
            console.println("Создание персонажа");
            HumanBeing newElement = new HumanBeingForm(console).build();
            console.println("Создание персонажа прошло успешно!");
            Collection<HumanBeing> toRemove = collectionManager.getCollection().stream()
                    .filter(Objects::nonNull)
                    .filter(HumanBeing -> HumanBeing.compareTo(newElement) < 0)
                    .toList();
            collectionManager.removeElements(toRemove);
            console.println("Персонажи со скоростью меньше заданной");
        } catch (NoElements e){
            console.printError("В коллекции нет персонажей");
        } catch (ExceptionInFileMode e){
            console.printError("Поля в файле не валидны! Персонаж не создан");
        }
    }
}