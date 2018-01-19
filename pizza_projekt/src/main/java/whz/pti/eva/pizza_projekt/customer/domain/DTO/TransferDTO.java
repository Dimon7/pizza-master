package whz.pti.eva.pizza_projekt.customer.domain.DTO;

import lombok.Getter;

import java.io.Serializable;

public class TransferDTO implements Serializable{

    @Getter String to;
    @Getter int amount;

    public TransferDTO() {
    }

    public TransferDTO(String to, int amount) {
        this.to = to;
        this.amount = amount;
    }


}
