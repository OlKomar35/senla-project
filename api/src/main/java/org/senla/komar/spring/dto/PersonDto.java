package org.senla.komar.spring.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.senla.komar.spring.validation.annotation.RussianCapitalLetters;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDto {

    private Long id;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(min = 2, max = 35, message = "Не верный размер")
    private String surname;

    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 35, message = "Не верная длина")
    private String firstname;

    private String middleName;

    @NotBlank(message = "Не введена серия паспорта")
    @Size(min = 2, max = 5)
   // @RussianCapitalLetters
    private String passportSeries;

    @NotNull(message = "Не введен номер паспорта")
    @Positive(message = "Не может иметь отрицательное значение")
    private int passportNumber;


    @NotBlank(message = "Не введен номер телефона")
    @Size(min = 7, max = 30, message = "Не верный размер номера")
    private String phoneNumber;

    @NotBlank(message = "Не введен электронный адрес")
    @Email(message = "Не является адресом электронной почты")
    @Size(min = 5, max = 50, message = "Не верный размер электронной почты")
    private String email;

    @JsonIgnore
    private String login;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String confirmPassword;

    private Set<RoleDto> roles;
}
