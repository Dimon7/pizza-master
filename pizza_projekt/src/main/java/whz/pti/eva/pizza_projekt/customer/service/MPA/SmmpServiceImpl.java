package whz.pti.eva.pizza_projekt.customer.service.MPA;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import whz.pti.eva.pizza_projekt.customer.domain.DTO.AccountResponseDTO;
import whz.pti.eva.pizza_projekt.customer.domain.DTO.PayActionResponseDTO;
import whz.pti.eva.pizza_projekt.customer.domain.DTO.TransferDTO;

@Service
public class SmmpServiceImpl implements SmmpService {

//    @Autowired
//    private MessageSource messageSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(SmmpServiceImpl.class);

    @Value("${my.smmp.url}")
    String MyUrl;

    @Value("${my.smmp.plainCreds}")
    String plainCreds;

    @Override
    public PayActionResponseDTO doPayAction(String from, String to, String pcontent) {
        String token1 ="", token2 ="", token3 ="";

        PayActionResponseDTO payActionResponse =
                new PayActionResponseDTO().payment(false).description("unbekanntes Problem. Transfer nicht erfolgreich");
//                new PayActionResponseDTO().payment(false).description(messageSource.getMessage("unbekanntes Problem. Transfer nicht erfolgreich", null,  LocaleContextHolder.getLocale())); //my.action.unknownProblem

        String[] tokens = pcontent.split("\\s+");
        if (tokens.length == 0) return payActionResponse.description("falsche Syntax");
        if (tokens.length >= 4) return payActionResponse.description("falsche Syntax - Eingabe zu lang / zuviele Worte");
//        if (tokens.length == 0) return payActionResponse.description(messageSource.getMessage("falsche Syntax", null,  LocaleContextHolder.getLocale())); //my.action.badSyntax
//        if (tokens.length >= 4) return payActionResponse.description(messageSource.getMessage("falsche Syntax - Eingabe zu lang / zuviele Worte", null,  LocaleContextHolder.getLocale())); //my.action.badSyntaxInpuToLong

        for (int i=0; i<tokens.length; i++) {
            if (i == 0) token1 = tokens[0];
            if (i == 1) token2 = tokens[1];
            if (i == 2) token3 = tokens[2];
        }

        if ((token1.equals("get") || token1.equals("delete") || token1.equals("open") || token1.equals("suspend")) && (token2.equals("")) && (token3.equals(""))) {
            return smmpAccountCommunication(token1, token2, token3, from, payActionResponse);
        }

        if ((token1.equals("transfer")) && (!token2.equals(from))) {
            try {
//                System.out.println(Integer.valueOf(token3));
            } catch (Exception e) {
                return payActionResponse.description("falsche Syntax (Betrag ist keine Zahl)");
//                return payActionResponse.description(messageSource.getMessage("falsche Syntax (Betrag ist keine Zahl)", null,  LocaleContextHolder.getLocale())); //my.action.badSyntaxAmountNotANumber
            }
            return smmpAccountCommunication(token1, token2, token3, from, payActionResponse);
        }

        return payActionResponse.description("die Eingabe ist nicht korrekt");
//        return payActionResponse.description(messageSource.getMessage("die Eingabe ist nicht korrekt", null,  LocaleContextHolder.getLocale())); //my.action.cannotbedone
    }

    private PayActionResponseDTO smmpAccountCommunication(String token1, String token2, String token3, String from, PayActionResponseDTO payActionResponse) {

        String uriReturn;
        ResponseEntity<?> response = null;
        RestTemplate restTemplate = new RestTemplate();
//        String plainCreds = plainCredsUser;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        try {
            switch (token1) {
                case "get":
                    uriReturn = MyUrl + from + "/account";
                    response = restTemplate.exchange(uriReturn, HttpMethod.GET, request, AccountResponseDTO.class);
                    break;
                case "delete":
                    uriReturn = MyUrl + from + "/deleted";
                    response = restTemplate.exchange(uriReturn, HttpMethod.DELETE, request, AccountResponseDTO.class);
                    break;
                case "open":
                    uriReturn = MyUrl + from + "/opened";
                    response = restTemplate.exchange(uriReturn, HttpMethod.PUT, request, AccountResponseDTO.class);
                    break;
                case "suspend":
                    uriReturn = MyUrl + from + "/suspended";
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<String> requestPut = new HttpEntity<>("suspended", headers);
                    response = restTemplate.exchange(uriReturn, HttpMethod.PUT, requestPut, AccountResponseDTO.class);
                    break;
                case "transfer":
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    TransferDTO transferDTO = new TransferDTO(token2, Integer.valueOf(token3));
                    HttpEntity<TransferDTO> requestPost = new HttpEntity<>(transferDTO, headers);
                    uriReturn = MyUrl + from + "/payment";
                    response = restTemplate.exchange(uriReturn, HttpMethod.POST, requestPost, AccountResponseDTO.class);
                    break;
                default:
                    return payActionResponse.description("falsche Syntax - Befehl unbekannt !");
//            return payActionResponse.description(messageSource.getMessage("falsche Syntax - Befehl unbekannt !", null,  LocaleContextHolder.getLocale())); //my.action.badSyntaxUnkownCommand
            }

        } catch(ResourceAccessException e){
            response = new ResponseEntity<Object>(new AccountResponseDTO(token1 +" " + "get ist nicht erfolgreich durchgeführt :: vlt. smmp-dienst nicht erreichbar"), HttpStatus.OK);
//            response = new ResponseEntity<Object>(new AccountResponseDTO(token1 +" " + messageSource.getMessage("get ist nicht erfolgreich durchgeführt :: vlt. Smmf nicht erreichbar", null,  LocaleContextHolder.getLocale())), HttpStatus.OK); //my.action.getNotSuccessful
        }

        catch(Exception e){
            response = new ResponseEntity<Object>(new AccountResponseDTO(token1+" " + " ist nicht erfolgreich durchgeführt :: vlt. Empfänger unbekannt"), HttpStatus.OK);
//            response = new ResponseEntity<Object>(new AccountResponseDTO(token1+" " + messageSource.getMessage("transfer ist nicht erfolgreich durchgeführt :: vlt. Empfänger unbekannt", null,  LocaleContextHolder.getLocale())), HttpStatus.OK); //my.action.transferNotSuccessful
        }

        AccountResponseDTO accountResponse = (AccountResponseDTO) response.getBody();
        payActionResponse.description(accountResponse.getCode());

        if (response.getStatusCode().equals(HttpStatus.OK))
        {
            payActionResponse.payment(true);
        }
        else {
            payActionResponse.payment(false);
        }
        return payActionResponse;
    }

}