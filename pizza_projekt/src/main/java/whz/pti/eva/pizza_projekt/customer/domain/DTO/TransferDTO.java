package whz.pti.eva.pizza_projekt.customer.domain.DTO;

import lombok.Getter;

import java.io.Serializable;

public class TransferDTO implements Serializable{

    @Getter String to;
    @Getter double amount;

    public TransferDTO() {
    }

    public TransferDTO(String to, double amount) {
        this.to = to;
        this.amount = amount;
    }


}
