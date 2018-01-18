package whz.pti.eva.pizza_projekt.customer.domain.validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import whz.pti.eva.pizza_projekt.customer.domain.CustomerCreateForm;
import whz.pti.eva.pizza_projekt.customer.service.CustomerServiceImpl;

@Component
public class CustomerCreateFormValidator implements Validator{

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerCreateFormValidator.class);

    @Autowired
    private CustomerServiceImpl customerService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(CustomerCreateForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        CustomerCreateForm form = (CustomerCreateForm) target;
        validatePasswords(errors, form);
        validateLoginName(errors, form);

    }

    private void validatePasswords(Errors errors, CustomerCreateForm form) {
        if (!form.getPassword().equals(form.getPasswordRepeated())) {
            System.out.println("pass");
            errors.reject("password.no_match", "Passwords do not match");
        }
    }


    private void validateLoginName(Errors errors, CustomerCreateForm form){
        if(customerService.getCustomerByLoginName(form.getLoginName()).isPresent()){
            System.out.println("loginName exist");
            errors.reject("loginName.exists", "User with this LoginName already exists");
        }

    }
    /*private void validateEmail(Errors errors, UserCreateForm form) {
        if (userService.getUserByEmail(form.getEmail()).isPresent()) {
          errors.reject("email.exists", "User with this email already exists");
        }
    }*/
}

