package managers;

import exceptions.InvalidForm;
import models.HumanBeing;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


import java.util.LinkedHashSet;
import java.util.Collection;
import java.util.Date;


/**
 * Класс организующий работу с коллекцией
 */
public class CollectionManager {
    private final LinkedHashSet<HumanBeing> collection = new LinkedHashSet<>();

    /**
     * Дата создания коллекции
     */
    private LocalDateTime lastInitTime;

    /**
     * Дата последнего изменения коллекции
     */
    private LocalDateTime lastSaveTime;

    public CollectionManager() {
        this.lastInitTime = LocalDateTime.now();
        this.lastSaveTime = null;
    }

    public LinkedHashSet<HumanBeing> getCollection() {
        return collection;
    }

    /**
     * Метод скрывающий дату, если она сегодняшняя
     * @param localDateTime объект {@link LocalDateTime}
     * @return вывод даты
     */
    public static String timeFormatter(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        if (localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .equals(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))) {
            return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * Метод скрывающий дату, если она сегодняшняя
     * @param dateToConvert объект {@link Date}
     * @return вывод даты
     */
    public static String timeFormatter(Date dateToConvert) {
        LocalDateTime localDateTime = dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        if (localDateTime == null) return null;
        if (localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .equals(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))) {
            return localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        }
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getLastInitTime() {
        return timeFormatter(lastInitTime);
    }

    public String getLastSaveTime() {
        return timeFormatter(lastSaveTime);
    }

    /**
     * @return Имя типа коллекции.
     */
    public String collectionType() {
        return collection.getClass().getName();
    }

    public int collectionSize() {
        return collection.size();
    }

    public void clear() {
        this.collection.clear();
        HumanBeing.resetIdCounter();
        lastInitTime = LocalDateTime.now();
    }

    public HumanBeing getLast() {
        return collection.stream()
                .skip(collection.size() - 1)
                .findFirst()
                .orElse(null);
    }

    /**
     * @param id ID элемента
     * @return Элемент по его ID или null, если не найдено
     */
    public HumanBeing getById(long id) {
        for (HumanBeing el: collection) {
            if (el.getId().equals(id)) return el;
        }
        return null;
    }

    /**
     * Изменить элемент коллекции с таким id
     * @param id ID
     * @param newElement новый элемент
     * @trows InvalidForm Нет элемента с таким ID
     */
    public void editById(Long id, HumanBeing newElement) throws InvalidForm {
        HumanBeing pastEl = this.getById(id);
        this.removeElement(pastEl);
        newElement.setId(id);
        this.addElement(newElement);
        HumanBeing.updateId(this.getCollection());
    }

    /**
     * @param id ID элемента
     * @return Проверяет, существует ли элемент с таким ID.
     */
    public boolean checkExist(long id) {
        return collection.stream().anyMatch((x) -> x.getId().equals(id));
    }

    public void addElement(HumanBeing humanBeing) throws InvalidForm {
        this.lastSaveTime = LocalDateTime.now();
        if (!humanBeing.validate()) throw new InvalidForm();
        collection.add(humanBeing);
    }

    public void addElements(Collection<HumanBeing> collection) throws InvalidForm{
        if (collection == null) return;
        for (HumanBeing humanBeing : collection){
            this.addElement(humanBeing);
        }
    }
    public void removeElement(HumanBeing humanBeing) {
        collection.remove(humanBeing);
    }

    public void removeElements(Collection<HumanBeing> collection) {
        this.collection.removeAll(collection);
    }

    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";
        var last = getLast();

        StringBuilder info = new StringBuilder();
        for (HumanBeing humanBeing : collection) {
            info.append(humanBeing);
            if (humanBeing != last) info.append("\n\n");
        }
        return info.toString();
    }
}
