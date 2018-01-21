package whz.pti.eva.pizza_projekt.customer.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CustomerCreateForm {

    @NotEmpty @Getter @Setter private String firstName;

    @NotEmpty @Getter @Setter private String lastName;

    @NotEmpty @Getter @Setter private String loginName = "";

    @NotEmpty @Getter @Setter private String password = "";

    @NotEmpty @Getter @Setter private String passwordRepeated = "";

}
