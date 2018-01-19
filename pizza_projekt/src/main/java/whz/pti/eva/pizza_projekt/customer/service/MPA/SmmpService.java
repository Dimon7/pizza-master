package whz.pti.eva.pizza_projekt.customer.service.MPA;

import whz.pti.eva.pizza_projekt.customer.domain.DTO.PayActionResponseDTO;

public interface SmmpService {
    PayActionResponseDTO doPayAction(String from, String to, String pcontent);
}
