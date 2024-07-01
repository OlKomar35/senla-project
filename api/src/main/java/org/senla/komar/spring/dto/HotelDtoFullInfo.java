package org.senla.komar.spring.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.senla.komar.spring.enums.TypeHotel;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelDtoFullInfo {
    private Long id;

    @NotBlank(message = "Название гостиницы не может быть пустым")
    @Size(min = 2, max = 100, message = "Не верный размер")
    private String name;

    @NotNull
    private AddressDto address;

    @NotBlank(message = "Не введен номер телефона")
    @Size(min = 7, max = 30, message = "Не верный размер номера")
    private String phoneNumber;

    @NotBlank(message = "Не введен электронный адрес")
    @Email(message = "Не является адресом электронной почты")
    @Size(min = 5, max = 50, message = "Не верный размер электронной почты")
    private String email;

    private String webSite;

    @DecimalMin(value = "0.00", inclusive = true, message = "Значение должно быть не меньше 0")
    @DecimalMin(value = "10.00", inclusive = true, message = "Значение должно быть не больше 10")
    private BigDecimal rank;

    @NotNull
    private TypeHotel typeHotel;
    
    private  List<FeedbackDto> feedbacks;
}
