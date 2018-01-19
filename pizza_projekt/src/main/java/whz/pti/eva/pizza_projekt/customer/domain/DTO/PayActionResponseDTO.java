package whz.pti.eva.pizza_projekt.customer.domain.DTO;
import lombok.*;

@ToString
public class PayActionResponseDTO {
    private boolean payment;
    private String token1 = "", token2 = "", token3 = "", description = "";

    public PayActionResponseDTO() {
    }


    public PayActionResponseDTO payment(boolean payment) {
        this.payment = payment;
        return this;
    }


    public String getDescription() {
        return description;
    }

    public PayActionResponseDTO description(String description) {
        this.description = description;
        return this;
    }

}