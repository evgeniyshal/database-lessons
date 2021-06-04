package jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

// JPA Спецификация
// все классы сущности выделяют в один пакет( которые описывают таблицу)

@Entity // если наш класс описывает базы данных( является сущность) Обязательно! // пометка класса, который будет связан с бд
@Table (name = "tb_message") // позволяет задать имя таблицы в базе данных
// через аннотации можно менять имя и указать информацию по индексам и связям с другими сущностями
public class Message {
    @Getter
    @Setter
    @Id //если отмечаем как первичный ключ, добавляем Id
    @GeneratedValue // стратегия автогенирации
    private int id; // Первичный ключ


    @Column(name = "message_text",
            //length = 1000,
            //nullable = false
            unique = true,
    columnDefinition = "VARCHAR") //Редактирование столбца, По умолчанию не может быть пустым

    @Setter
    private String text;
    @Column(nullable = false)
    @Setter
    private LocalDateTime sent;



    //каждому полю в классе по умолчанию есть место в таблице
    // Транзиентные и сериалайз не воспринимает
    // Unuque Значения по данному столбцу буду уникальными

}

