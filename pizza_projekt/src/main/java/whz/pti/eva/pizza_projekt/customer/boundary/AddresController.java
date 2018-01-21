package whz.pti.eva.pizza_projekt.customer.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import whz.pti.eva.pizza_projekt.customer.domain.*;
import whz.pti.eva.pizza_projekt.customer.domain.repo.AddressRepository;
import whz.pti.eva.pizza_projekt.customer.domain.repo.CustomerRepository;
import whz.pti.eva.pizza_projekt.customer.service.CustomerServiceImpl;


@Controller
public class AddresController {

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping("/start_edit_addres_data")
    public String startEditAddresData(@RequestParam String id,  Model model ){

        Address address = customerService.getAddressesById( Integer.parseInt(id) );

        model.addAttribute("currentAddress", address);
        return "edit_address";
    }

    @RequestMapping("finish_edit_addres_data")
    public String finishEditAddresData(@RequestParam String id ,
                                       @RequestParam String street ,
                                       @RequestParam String housenumber,
                                       @RequestParam String town,
                                       @RequestParam String  zipCode) {

        customerService.editAddress(Integer.parseInt(id), street, housenumber, town, zipCode);
        return "redirect:user";
    }

    @RequestMapping("delete_addres")
    public String deleteAddres( @RequestParam String id, @RequestParam String loginName  ){

        Customer customer = customerService.getCustomerByLoginName(loginName).get();
        Address address = customerService.getAddressesById(Integer.parseInt(id));
        customer.remove(address);

        customerService.deleteAdres(Integer.parseInt(id));


        return "redirect:user";
    }

    @RequestMapping(value="finish_add_addres", method = RequestMethod.POST)
    public String finishAddAddres(
                                   @RequestParam String street ,
                                   @RequestParam String housenumber,
                                   @RequestParam String town,
                                   @RequestParam String zipCode,
                                   @RequestParam String loginName){
        Address address = new Address();
        address.setHousenumber(housenumber);
        address.setStreet(street);
        address.setTown(town);
        address.setZipCode(zipCode);
        addressRepository.save(address);


        Customer customer = customerService.getCustomerByLoginName(loginName).get();
        customer.setAddresses(address);
        customerRepository.save(customer);
        return "redirect:user";
    }

    @RequestMapping("add_addres")
    public ModelAndView addAddres(  @RequestParam String loginName){

        return new ModelAndView("add_address", "loginName", loginName);

    }
}
