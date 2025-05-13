package commandLine.commands;

import commandLine.Console;
import exceptions.IllegalArguments;
import managers.CollectionManager;
import models.HumanBeing;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class PrintDescending extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public PrintDescending(Console console, CollectionManager collectionManager) {
        super("print_descending", "вывести элементы коллекции в порядке убывания");
        this.console = console;
        this.collectionManager = collectionManager;
    }

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

        Comparator<HumanBeing> descendingComparator = (h1, h2) -> {
            return Long.compare(h2.getId(), h1.getId());
        };

        List<HumanBeing> sortedList = collection.stream()
                .sorted(descendingComparator)
                .collect(Collectors.toList());

        console.println("Персонажи в обратном порядке добавления:");
        sortedList.forEach(human -> {
            console.println(human.getName());
        });
    }
}