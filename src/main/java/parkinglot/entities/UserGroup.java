package parkinglot.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "usergroups")
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // SCHIMBAT AICI!
    private Long id;

    private String username;

    @Column(name = "usergroup")
    private String userGroup;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }
}