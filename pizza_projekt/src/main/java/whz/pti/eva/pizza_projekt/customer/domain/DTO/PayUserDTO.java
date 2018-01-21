package whz.pti.eva.pizza_projekt.customer.domain.DTO;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.ToString;

@JsonPropertyOrder({ "id", "name", "state", "account" })
@ToString
public class PayUserDTO {


    private String name;

    public PayUserDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}


