package commandLine;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Scanner;


/**
 * Класс для хранения файл менеджера для команды execute
 */
public class ExecuteFileManager implements UserInput {
    private static final LinkedHashSet<String> pathQueue = new LinkedHashSet<>();
    private static final LinkedHashSet<Scanner> fileReaders = new LinkedHashSet<>();
    
    public static void pushFile(String path) throws FileNotFoundException {
        pathQueue.add(new File(path).getAbsolutePath());
        fileReaders.add(new Scanner(new InputStreamReader(new FileInputStream(path))));
    }

    public static File getFile() {
        return new File(Objects.requireNonNull(pathQueue.stream()
                .skip(pathQueue.size() - 1)
                .findFirst()
                .orElse(null)));
    }

    public static String readLine() throws IOException {
        return fileReaders.stream().findFirst().get().nextLine();
    }

    public static void popFile() throws IOException {
        Scanner scanner = fileReaders.stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("Нет файла"));;
        scanner.close();
        fileReaders.remove(scanner);

        String path = pathQueue.stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("Нет пути"));;
        pathQueue.remove(path);
    }

    public static boolean fileRepeat(String path) {
        return pathQueue.contains(new File(path).getAbsolutePath());
    }

    @Override
    public String nextLine() {
        try {
            return readLine();
        } catch (IOException e) {
            return "";
        }
    }

}
