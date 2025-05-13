package managers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.security.AnyTypePermission;
import commandLine.Console;
import commandLine.Printable;
import exceptions.ExitObligated;
import exceptions.InvalidForm;
import models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;


/**
 * Класс реализующий работу с файлами
 * @author isa_alex
 */
public class FileManager {
    private String text;
    private final Printable console;
    private final CollectionManager collectionManager;
    private final XStream xStream = new XStream();

    /**
     * В конструкторе задаются алиасы для библиотеки {@link XStream} которая используется для работы с xml
     * @param console Пользовательский ввод-вывод
     * @param collectionManager Работа с коллекцией
     */
    public FileManager(Console console, CollectionManager collectionManager) {
        this.console = console;
        this.collectionManager = collectionManager;

        this.xStream.alias("HumanBeing", HumanBeing.class);
        this.xStream.alias("LinkedHashSet", CollectionManager.class);
        this.xStream.addPermission(AnyTypePermission.ANY);
        this.xStream.addImplicitCollection(CollectionManager.class, "collection");
    }

    /**
     * Обращение к переменным среды и чтение файла в поле по указанному пути
     * @throws ExitObligated если путь - null или отсутствует, программа заканчивает выполнение
     */
    public void findFile() throws ExitObligated{
        String file_path = System.getenv("file_path");
        if (file_path == null || file_path.isEmpty()) {
            console.printError("Путь должен быть в переменных окружения в переменной 'file_path'");
            throw new ExitObligated();
        }
        else console.println("Путь получен успешно");

        File file = new File(file_path);
        BufferedInputStream bis;
        FileInputStream fis;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            while (bis.available() > 0) {
                stringBuilder.append((char) bis.read());
            }
            fis.close();
            bis.close();
            if (stringBuilder.isEmpty()) {
                console.printError("Файл пустой");
                this.text = "</LinkedHashSet>";
                return;
            }
            this.text = stringBuilder.toString();
        } catch (FileNotFoundException fnfe) {
            console.printError("Такого файла не найдено");
            throw new ExitObligated();
        } catch (IOException ioe) {
            console.printError("Ошибка ввода/вывода" + ioe);
            throw new ExitObligated();
        }
    }

    /**
     * Создание объектов в консольном менеджере
     * @throws ExitObligated Если объекты в файле невалидны выходим из программы
     */
    public void createObjects() throws ExitObligated{
        try{
            XStream xstream = new XStream();
            xstream.alias("HumanBeing", HumanBeing.class);
            xstream.alias("LinkedHashSet", CollectionManager.class);
            xstream.addPermission(AnyTypePermission.ANY);
            xstream.addImplicitCollection(CollectionManager.class, "collection");
            CollectionManager collectionManagerWithObjects = (CollectionManager) xstream.fromXML(this.text);

            for(HumanBeing s : collectionManagerWithObjects.getCollection()){
                if (this.collectionManager.checkExist(s.getId())){
                    console.printError("В файле повторяются айди!");
                    throw new ExitObligated();
                }
                this.collectionManager.addElement(s);
            }
            this.collectionManager.addElements(collectionManagerWithObjects.getCollection());
        } catch (InvalidForm | StreamException e) {
            console.printError("Объекты в файле не валидны");
        }
        HumanBeing.updateId(collectionManager.getCollection());
    }

    /**
     * Сохраняем коллекцию из менеджера в файл
     */
    public void saveObjects(){
        String file_path = System.getenv("file_path");
        if (file_path == null || file_path.isEmpty())
            console.printError("Путь должен быть в переменных окружения в перменной 'file_path'");
        else console.println("Путь получен успешно");
        try{
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file_path));
            out.write(this.xStream.toXML(collectionManager)
                    .getBytes(StandardCharsets.UTF_8));
            out.close();
        } catch (FileNotFoundException e) {
            console.printError("Файл не существует");
        }catch (IOException e){
            console.printError("Ошибка ввода вывода");
        }
    }
}