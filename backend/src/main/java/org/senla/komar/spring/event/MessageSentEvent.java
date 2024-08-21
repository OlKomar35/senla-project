package org.senla.komar.spring.event;


import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.senla.komar.spring.enums.BookingStatus;
import org.senla.komar.spring.enums.DeliveryChannel;
import org.senla.komar.spring.enums.MessageType;
import org.senla.komar.spring.enums.PaymentStatus;
import org.senla.komar.spring.enums.TypeFood;
import org.senla.komar.spring.enums.TypePayment;

/**
 * @author Olga Komar
 * <p>
 * Created at 03.08.2024
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageSentEvent { ;
  private MessageType messageType;
  private DeliveryChannel deliveryChannel;
  private Long userId;
  private Map<String , Object> messageData;
}
