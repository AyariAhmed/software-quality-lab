package Entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class User {
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public Date dateOfBirth;
}
