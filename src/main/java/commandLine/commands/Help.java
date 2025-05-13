package commandLine.commands;

import exceptions.IllegalArguments;
import managers.CommandManager;
import commandLine.*;

/**
 * команда 'help'
 * вывести справку по доступным командам
 */
public class Help extends Command {
    private CommandManager commandManager;
    private Console console;
    public Help (Console console, CommandManager commandManager) {
        super("help", ": вывести справку по доступным командам");
        this.commandManager = commandManager;
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
        commandManager.getCommands().forEach(command -> console.println(command.getName() + command.getDescription()));
    }
}
