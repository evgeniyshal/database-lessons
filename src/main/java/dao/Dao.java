package dao;

import java.util.List;

public interface Dao<T, PK> {
    // Интерфейс делают Дженерик, T - тип данных сущности, второй первичного ключа - PK -(PrimaryKey)

    // Добавление в таблицу
    void add (T entity);

    //Получение по PK
    T getByPK(PK pk);

    //Получение всех записпей
    List<T> getAll();

    //Обновление данных
    void update(T entity);

    //удаление по первчиному ключу
    void deleteByPk(PK pk);



}
