package managers;

import exceptions.CommandRunTimeError;
import exceptions.ExitObligated;
import exceptions.IllegalArguments;
import exceptions.NoSuchCommand;
import commandLine.*;

import java.util.*;

/**
 * Класс обработки пользовательского ввода
 * @author isa_alex
 */
public class RuntimeManager {
    private final Printable console;
    private final CommandManager commandManager;

    public RuntimeManager(Console console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
    }

    /**
     * Перманентная работа с пользователем и выполнение команд
     */
    public void interactiveMode(){
        Scanner userScanner = ScannerManager.getUserScanner();
        while (true) {
            try{
                if (!userScanner.hasNext()) throw new ExitObligated();
                String userCommand = userScanner.nextLine().trim() + " "; // прибавляем пробел, чтобы split выдал два элемента в массиве
                this.launch(userCommand.split(" ", 2));
                commandManager.addToHistory(userCommand);
            } catch (NoSuchElementException exception) {
                console.printError("Пользовательский ввод не обнаружен!");
            } catch (NoSuchCommand noSuchCommand) {
                console.printError("Такой команды нет в списке");
            } catch (IllegalArguments e) {
                console.printError("Введены неправильные аргументы команды");
            } catch (CommandRunTimeError e) {
                console.printError("Ошибка при исполнении команды");
            } catch (ExitObligated exitObliged){
                console.println("До свидания!");
                return;
            }
        }
    }

    /**
     * Триггер выполнения команды из {@link CommandManager}
     * @param userCommand массив из 2 элементов, первый - название команды, второй - аргументы
     * @throws NoSuchCommand несуществующая команда
     * @throws ExitObligated команда привела к окончанию работы программы
     * @throws IllegalArguments команда содержит неверные аргументы
     * @throws CommandRunTimeError команда выдала ошибку во время выполнения
     */
    public void launch(String[] userCommand) throws NoSuchCommand, ExitObligated, IllegalArguments, CommandRunTimeError {
        if (userCommand[0].equals("")) return;
        commandManager.execute(userCommand[0], userCommand[1]);
    }
}