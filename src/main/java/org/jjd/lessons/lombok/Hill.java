package org.jjd.lessons.lombok;


import lombok.*;

import java.util.HashSet;

//если нужно сгенирировать toString, добавляем перед классом

@ToString (exclude = "climbers") // пишем если хотим исключить

@EqualsAndHashCode (exclude = "climbers")
// если сгенирируем, то по умолч. войдут все поля
// текущего класса, если надо сключить то exclude
public class Hill {
    // чтобы сгенирировать геттер ставим аннтоацию
    @Getter // генерация геттера intHeight
    @Setter // генерация сеттера void setHeight
    private int height;

    @Getter
    private final HashSet<Climber> climbers = new HashSet<>();

    @SneakyThrows // при вызове можем исключения не обрабатывать, но будет выброшен RuntimeException
    // Можно добавить перед аргументами или к полю, проверку на Null, генерруетя Exception
    public void walk(@NonNull Climber climber){
        if (climber.getAge() < 18) throw new Exception("Исключение по возрасту");
        climbers.add(climber);
    }
} //для взаимодествия с бд используется JDBC java DataBase Connectivity.
// Чтобы взаимодествовать нужны драйвера соотв. ( в качестве зависимости)
