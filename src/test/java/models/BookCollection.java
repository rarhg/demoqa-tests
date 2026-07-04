package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class BookCollection {

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("collectionOfIsbns")
    private List<Isbn> collectionOfIsbns;

    public BookCollection(String userId, String isbn) {
        this.userId = userId;
        this.collectionOfIsbns = Collections.singletonList(new Isbn(isbn));
    }

    @Data
    public static class Isbn {

        @JsonProperty("isbn")
        private String isbn;

        public Isbn(String isbn) {
            this.isbn = isbn;
        }
    }
}