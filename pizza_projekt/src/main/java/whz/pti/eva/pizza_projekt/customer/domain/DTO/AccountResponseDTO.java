package whz.pti.eva.pizza_projekt.customer.domain.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class AccountResponseDTO implements Serializable {

    @Getter @Setter  private String code;

    public AccountResponseDTO() {
    }

    public AccountResponseDTO(String code) {
        this.code = code;
    }

}