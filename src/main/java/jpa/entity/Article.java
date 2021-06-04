package jpa.entity;



import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_articles")
public class Article { // Чтобы посмотреть первичный ключ
  
    @EmbeddedId
    @Getter
    @Setter
    private ArticleKey key;
    
    
    @Column(nullable = false, columnDefinition = "TEXT")
    @Getter
    @Setter
    public String text;
    // делаем составной первичный ключ name| surname | text?, 
    // имена и фамили повоторятся могут, но не сочетания, опишем их через аннтоации


    @Embeddable
    @EqualsAndHashCode
    public static class ArticleKey implements Serializable {

        @Column(length = 30)
        @Getter
        @Setter
        private String name;

        @Getter
        @Setter
        @Column(length = 100)
        private String surname;
        
        
        
    }
    
    
}
