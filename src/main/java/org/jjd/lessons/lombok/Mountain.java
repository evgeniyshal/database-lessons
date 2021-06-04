package org.jjd.lessons.lombok;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor // Генирирует конструктор который принимает на вход, все final поля или поля @NonNull
@ToString(callSuper = true )// Если нужно обратиться к родителю ставим callSuper
@EqualsAndHashCode(callSuper = true)
public class Mountain extends Hill{
    @Getter
    private final String name;

}