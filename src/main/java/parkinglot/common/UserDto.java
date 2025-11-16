package parkinglot.common;

public class UserDto {
    private Long id;
    private String username;
    private String email;

    // Constructor cu toate câmpurile
    public UserDto(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // Doar getters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
