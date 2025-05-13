package commandLine.commands;

import commandLine.Console;
import exceptions.IllegalArguments;
import managers.CollectionManager;
import models.HumanBeing;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда 'print_field_ascending_mood'
 * Выводит значения поля mood всех элементов в порядке возрастания (чем хуже настроение, тем оно больше)
 */
public class PrintAscending extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public PrintAscending(Console console, CollectionManager collectionManager) {
        super("print_asc_mood",
                " : вывести значения поля mood всех элементов в порядке возрастания (ухудшения настроения)");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Исполнить команду
     * @param args аргументы команды
     * @throws IllegalArguments при неверных аргументах команды
     */
    @Override
    public void execute(String args) throws IllegalArguments {
        if (!args.isBlank()) {
            throw new IllegalArguments();
        }

        LinkedHashSet<HumanBeing> collection = collectionManager.getCollection();

        if (collection.isEmpty()) {
            console.println("Коллекция пуста");
            return;
        }

        List<HumanBeing> sortedByMood = collection.stream()
                .filter(h -> h.getMood() != null)
                .sorted((h1, h2) -> h1.getMood().compareTo(h2.getMood()))
                .collect(Collectors.toList());

        if (sortedByMood.isEmpty()) {
            console.println("В коллекции нет персонажей с настроением");
            return;
        }

        console.println("Настроения персонажей в порядке ухудшения:");
        for (HumanBeing h : sortedByMood) {
            console.println(h.getName() + ": " + h.getMood());
        }

        console.println("\nВсего значений: " + sortedByMood.size());
        console.println("Уникальных настроений: " +
                sortedByMood.stream().map(HumanBeing::getMood).distinct().count());
    }
}