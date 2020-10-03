package th.ac.ku.atm.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import th.ac.ku.atm.model.BankAccount;
import java.util.Arrays;
import java.util.List;

@Service
public class BankAccountService {
    private RestTemplate restTemplate;

    public BankAccountService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void openAccount(BankAccount bankAccount) {
        String url = "http://localhost:8091/api/bankaccount";
        restTemplate.postForObject(url, bankAccount, BankAccount.class);
    }

    public List<BankAccount> getCustomerBankAccount(int customerId) {
        String url = "http://localhost:8091/api/bankaccount/customer/" +
                customerId;
        ResponseEntity<BankAccount[]> response =
                restTemplate.getForEntity(url, BankAccount[].class);

        BankAccount[] accounts = response.getBody();

        return Arrays.asList(accounts);
    }

    public List<BankAccount> getBankAccounts() {
        String url = "http://localhost:8091/api/bankaccount";

        ResponseEntity<BankAccount[]> response =
                restTemplate.getForEntity(url, BankAccount[].class);

        BankAccount[] accounts = response.getBody();
        return Arrays.asList(accounts);
    }

    public BankAccount getBankAccount(int id) {
        String url = "http://localhost:8091/api/bankaccount/" + id;

        ResponseEntity<BankAccount> response =
                restTemplate.getForEntity(url, BankAccount.class);

        return response.getBody();
    }


    public void editBankAccount(BankAccount bankAccount) {
        String url = "http://localhost:8091/api/bankaccount/" +
                bankAccount.getId();
        restTemplate.put(url, bankAccount);
    }

    public void deleteBankAccount(int id) {
        String url = "http://localhost:8091/api/bankaccount/" + id;
        restTemplate.delete(url);
    }

    public void depositBankAccount(double balance, BankAccount bankAccount) {
        String url = "http://localhost:8091/api/bankaccount/" +
                bankAccount.getId() + "/deposit?deposit=" + balance;
        restTemplate.put(url, bankAccount);
    }

    public void withdrawBankAccount(double balance, BankAccount bankAccount) {
        String url = "http://localhost:8091/api/bankaccount/" +
                bankAccount.getId() + "/withdraw?withdraw=" + balance;
        restTemplate.put(url, bankAccount);
    }

    private String hash(String pin) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(pin, salt);
    }
}
