package commandLine.commands;

import commandLine.Console;
import exceptions.IllegalArguments;
import managers.CollectionManager;
import models.*;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Команда 'filter_greater'_than_weapon_type weaponType
 * Выводит элементы, значение поля weaponType которых больше заданного
 */
public class FilterGreater extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public FilterGreater(Console console, CollectionManager collectionManager) {
        super("filt_gr_weapon",
                " weaponType : вывести персонажей, у которых оружие круче заданного");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Исполнить команду
     * @param args аргументы команды
     * @throws IllegalArguments неверные аргументы команды
     */
    @Override
    public void execute(String args) throws IllegalArguments {
        if (args.isBlank()) {
            throw new IllegalArguments();
        }

        try {
            WeaponType inputWeaponType = WeaponType.valueOf(args.trim().toUpperCase());

            LinkedHashSet<HumanBeing> collection = collectionManager.getCollection();

            Set<HumanBeing> filtered = collection.stream()
                    .filter(human -> human.getWeaponType() != null)
                    .filter(human -> human.getWeaponType().ordinal() > inputWeaponType.ordinal())
                    .collect(Collectors.toCollection(LinkedHashSet::new));

            if (filtered.isEmpty()) {
                console.println("Нет персонажей с оружием круче чем " + inputWeaponType);
            } else {
                console.println("Персонажи с оружием круче " + inputWeaponType + ":");
                filtered.forEach(human ->
                        console.println(human.getName() + ": оружие - " + human.getWeaponType()));
            }

        } catch (IllegalArgumentException e) {
            throw new IllegalArguments();
        }
    }
}