package commandLine.commands;

import exceptions.ExceptionInFileMode;
import exceptions.IllegalArguments;
import managers.CollectionManager;
import commandLine.Console;
import models.HumanBeing;
import exceptions.InvalidForm;
import models.form.HumanBeingForm;

/**
 * Команда 'update'
 * Обновляет значение элемента коллекции, id которого равен заданному
 */
public class Update extends Command{
    private CollectionManager collectionManager;
    private Console console;

    public Update(Console console, CollectionManager collectionManager) {
        super("update", " {element} : обновить значение элемента коллекции, id которого равен заданному");
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
        class NoSuchId extends RuntimeException{

        }
        try {
            long id = Integer.parseInt(args.trim());
            if (!collectionManager.checkExist(id)) throw new NoSuchId();
            console.println("Создание нового персонажа");
            HumanBeing newHumanBeing = new HumanBeingForm(console).build();
            collectionManager.editById(id, newHumanBeing);
            console.println("Создание нового персонажа прошло успешно!");
        } catch (NoSuchId err) {
            console.printError("В коллекции нет персонажа с таким id");
        } catch (InvalidForm invalidForm) {
            console.printError("Поля персонажа не валидны! Персонаж не создан!");
        } catch (NumberFormatException exception) {
            console.printError("id должно быть числом");
        } catch (ExceptionInFileMode e){
            console.printError("Поля в файле не валидны! Персонаж не создан");
        }
    }
}