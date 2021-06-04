package jpa.entity;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class JPA {
    public static void main(String[] args) {

        Message message = new Message();
        message.setText("текст сообщения...");
        message.setSent(LocalDateTime.now());

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("java");

        EntityManager manager = factory.createEntityManager(); // Управляет всеми классами сущностями(Обнов, удал, доб, изм)

        //Добавление в таблицу
        manager.getTransaction().begin(); // Старт транзакции
        manager.persist(message);
        manager.getTransaction().commit(); // Подтвердить транзакцию, чтобы было добавление объекта
        Article.ArticleKey key = new Article.ArticleKey();
        key.setName("name");
        key.setSurname("surname");

        Article article = new Article();
        article.setKey(key);
        article.setText("Текст статьи");

        manager.getTransaction().begin();
        manager.persist(article);
        manager.getTransaction().commit();

        Article articleFromDB = manager.find(Article.class, key);
        System.out.println(articleFromDB.getKey().getName());

        // Обновление (не добавление) инфа уже существует в таблице
        articleFromDB.setText("Новый текст");
        manager.merge(articleFromDB);

        Article art = manager.find(Article.class, key);
        System.out.println(art.getText());

        //состояние сущностей
        // когда объект создан, сущность new, она не находится под (EntityManager) менеджером сущностей.
        // persist(message) - переходит в состояние managed (или управляемая)
        // merge(message) - найдет сущность в своей мапе и поменяет данные
        //

    }
}
