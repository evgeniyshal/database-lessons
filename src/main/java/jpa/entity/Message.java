package jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

// JPA Спецификация
// все классы сущности выделяют в один пакет( которые описывают таблицу)

@Entity // если наш класс описывает базы данных( является сущность) Обязательно! // пометка класса, который будет связан с бд
@Table (name = "tb_message") // позволяет задать имя таблицы в базе данных
// через аннотации можно менять имя и указать информацию по индексам и связям с другими сущностями
public class Message {
    private int id;
    @Column(name = "message_text", length = 1000, nullable = false) // По умолчанию не может быть пустым
    private String text;
    private LocalDateTime sent;



    //каждому полю в классе по умолчанию есть место в таблице
    // Транзиентные и сериалайз не воспринимает
    // unuque Значения по данному столбцу буду уникальными

}

