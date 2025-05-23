package commandLine.commands;

import commandLine.Console;
import commandLine.ExecuteFileManager;
import exceptions.*;
import managers.*;

import java.io.*;
import java.util.NoSuchElementException;

/**
 * Команда 'execute_script'
 * Считатывает и исполняет скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
 */
public class Execute extends Command{
    private FileManager fileManager;
    private final Console console;
    private final CommandManager commandManager;
    public Execute(Console console, FileManager fileManager, CommandManager commandManager) {
        super("execute_script", " file_name: считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        this.fileManager = fileManager;
        this.console = console;
        this.commandManager = commandManager;
    }

    /**
     * Исполнить команду
     * @param args аргументы команды
     * @throws IllegalArguments неверные аргументы команды
     * @throws CommandRunTimeError команда вызвала ошибку при исполнении
     * @throws ExitObligated требуется выход из программы
     */
    @Override
    public void execute(String args) throws CommandRunTimeError, ExitObligated, IllegalArguments {
        if (args == null || args.isEmpty()) {
            console.printError("Путь не распознан");
            return;
        }
        else console.println("Путь получен успешно");

        try {
            Console.setFileMode(true);
            ExecuteFileManager.pushFile(args);
            for (String line = ExecuteFileManager.readLine(); line != null; line = ExecuteFileManager.readLine()) {
                try{
                    commandManager.addToHistory(line);
                    String[] cmd = (line + " ").split(" ", 2);
                    if (cmd[0].isBlank()) return;
                    if (cmd[0].equals("execute_script")){
                        if(ExecuteFileManager.fileRepeat(cmd[1])){
                            console.printError("Найдена рекурсия по пути " + new File(cmd[1]).getAbsolutePath());
                            continue;
                        }
                    }
                    console.println("Выполнение команды " + cmd[0]);
                    commandManager.execute(cmd[0], cmd[1]);
                    if (cmd[0].equals("execute_script")){
                        ExecuteFileManager.popFile();
                    }
                } catch (NoSuchElementException exception) {
                    console.printError("Пользовательский ввод не обнаружен!");
                } catch (NoSuchCommand noSuchCommand) {
                    console.printError("Такой команды нет в списке");
                } catch (IllegalArguments e) {
                    console.printError("Введены неправильные аргументы команды");
                } catch (CommandRunTimeError e) {
                    console.printError("Ошибка при исполнении команды");
                }
            }
            ExecuteFileManager.popFile();
        }  catch (NoSuchCommand noSuchCommand){
            console.printError("Такой команды не существует");
        } catch (FileNotFoundException fileNotFoundException){
            console.printError("Такого файла не существует");
        } catch (IOException e) {
            console.printError("Ошибка ввода вывода");
        }
        Console.setFileMode(false);
    }
}