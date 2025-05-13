package managers;

import commandLine.commands.Command;
import exceptions.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Менеджер команд
 * Реализует паттерн программирования Command
 */
public class CommandManager {
    /**
     * Поле для хранения команд в виде Имя-Команда
     */
    private final HashMap<String, Command> commands = new HashMap<>();

    /**
     * Поле для исторри команд
     */
    private final List<String> commandHistory = new ArrayList<>();

    private void addCommand(Command command) {
        this.commands.put(command.getName(), command);
    }
    public void addCommand(Collection<Command> commands) {
        this.commands.putAll(commands.stream()
                .collect(Collectors.toMap(Command::getName, s ->s)));
    }
    public Collection<Command> getCommands() {
        return commands.values();
    }

    public void addToHistory(String line) {
        if (line.isBlank()) return;
        this.commandHistory.add(line);
    }

    public List<String> getCommandHistory(){
        return commandHistory;
    }

    /**
     * Выполняет команду
     * @param name название команды
     * @param args аргументы команды
     * @throws NoSuchCommand при отсутствии такой команды
     * @throws IllegalArguments при неверных аргументах команды
     * @throws CommandRunTimeError команда выдала ошибку при исполнении
     * @throws ExitObligated команда вызвала выход из программы
     */
    public void execute(String name, String args) throws
            NoSuchCommand, IllegalArguments, CommandRunTimeError, ExitObligated {
        Command command = commands.get(name);
        if (command == null) throw new NoSuchCommand();
        command.execute(args);
    }
}
