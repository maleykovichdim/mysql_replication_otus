import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
//@AllArgsConstructor
public class Message {


    public Message(String firstName, String surname, String birthdate, String gender, String city, String email, String password) {
        this.name = firstName;
        this.surname = surname;
        this.birthdate = birthdate;
        this.gender = gender;
        this.city = city;
        this.email = email;
        this.password = password;
    }
    String name;
    String surname;
    String birthdate;
    String gender;
    String city;
    String email;
    String password;
}
