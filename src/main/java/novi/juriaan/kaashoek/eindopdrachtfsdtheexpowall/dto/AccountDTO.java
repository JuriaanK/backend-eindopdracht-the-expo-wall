package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto;

import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Account;

import java.time.LocalDate;


public class AccountDTO {

    public Long id;
    public String nickName;
    public String firstName;
    public String lastName;
    public LocalDate DOB;

    public static AccountDTO fromAccount(Account account){
        AccountDTO dto = new AccountDTO();

        dto.id = account.getId();
        dto.nickName = account.getNickName();
        dto.firstName = account.getFirstName();
        dto.lastName = account.getLastName();
        dto.DOB = account.getDOB();

        return dto;
    }

    public static Account toAccount(AccountDTO accountDTO){
        Account account = new Account();

        account.setId(accountDTO.id);
        account.setNickName(accountDTO.nickName);
        account.setFirstName(accountDTO.firstName);
        account.setLastName(accountDTO.lastName);
        account.setDOB(accountDTO.DOB);

        return account;
    }

}
