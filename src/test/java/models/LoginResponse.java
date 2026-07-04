package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class LoginResponse {

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("token")
    private String token;

    @JsonProperty("expires")
    private String expires;

    @JsonProperty("created_date")
    private String createdDate;

    @JsonProperty("isActive")
    private boolean isActive;

    @JsonProperty("books")
    private List<Book> books;
}