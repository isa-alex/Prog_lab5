package commandLine.commands;

import exceptions.ExitObligated;

/**
 * Команда 'exit'
 * завершить программу (без сохранения в файл)
 */
public class Exit extends Command{
    public Exit(){
        super("exit", ": завершить программу (без сохранения в файл)");
    }

    /**
     * Исполнить команду
     * @param args аргументы команды
     * @throws ExitObligated нужен выход из программы
     */
    @Override
    public void execute(String args) throws ExitObligated{
        throw new ExitObligated();
    }
}