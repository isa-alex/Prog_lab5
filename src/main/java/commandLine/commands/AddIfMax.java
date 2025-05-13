package commandLine.commands;

import commandLine.Console;
import exceptions.*;
import managers.CollectionManager;
import models.HumanBeing;
import models.form.HumanBeingForm;

import java.util.Objects;

/**
 * Команда 'add_if_max'
 * Добавляет элемент в коллекцию если он больше максимального
 * Для определения максимальности берется поле скорости
 */
public class AddIfMax extends Command{
    private CollectionManager collectionManager;
    private Console console;

    public AddIfMax(Console console, CollectionManager collectionManager) {
        super("add_if_max", " {element}: добавить персонажа в коллекцию если его скорость больше максимальной");
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
            HumanBeing newElement = new HumanBeingForm(console).build();
            console.println("Создание персонажа прошло успешно!");
            if (newElement.compareTo(collectionManager.getCollection().stream()
                    .filter(Objects::nonNull)
                    .max(HumanBeing::compareTo)
                    .orElse(null)) >= 1)
            {
                collectionManager.addElement(newElement);
                console.println("Персонаж успешно добавлен");
            } else {
                console.println("Скорость вашего персонажа меньше максимальной скорости имеющихся, персонаж не добавлен");
            }
        } catch (InvalidForm invalidForm) {
            console.printError("Поля персонажа не валидны! Персонаж не создан!");
        }  catch (ExceptionInFileMode e){
            console.printError("Поля в файле не валидны! Персонаж не создан");
        }
    }
}