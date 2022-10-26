package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.service;

import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.AccountDTO;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.exceptions.RecordNotFoundException;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Account;

import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.AccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepos;

    public AccountService(AccountRepository accountRepos) {
        this.accountRepos = accountRepos;

    }

    public List<AccountDTO> getAccounts(){
        List<AccountDTO> allAccounts = new ArrayList<>();
        List<Account> list = accountRepos.findAll();
        for(Account account : list){
            allAccounts.add(AccountDTO.fromAccount(account));
        }

        return allAccounts;
    }

    public AccountDTO getAccount(Long id){
        AccountDTO dto = new AccountDTO();
        Optional<Account> account =accountRepos.findById(id);
        if (account.isPresent()){
            dto = AccountDTO.fromAccount(account.get());
        }else {
            throw new UsernameNotFoundException(id.toString());
        }

        return dto;
    }

    public void deleteAccount(Long id){
        accountRepos.deleteById(id);
    }

    public AccountDTO createAccount (AccountDTO accountDTO){
        Account account = AccountDTO.toAccount(accountDTO);
        accountRepos.save(account);

        return AccountDTO.fromAccount(account);
    }

    public AccountDTO changeAccount (Long id, AccountDTO inputDto){
        if(!accountRepos.existsById(id)) throw new RecordNotFoundException(id.toString());
        Account changedAccount = AccountDTO.toAccount(inputDto);
        Account account = accountRepos.findById(id).get();

        if (!account.getNickName().equals(changedAccount.getNickName())){
            account.setNickName(changedAccount.getNickName());
        }
        if (!account.getFirstName().equals(changedAccount.getFirstName())){
            account.setFirstName(changedAccount.getFirstName());
        }
        if(!account.getLastName().equals(changedAccount.getLastName())){
            account.setLastName(changedAccount.getLastName());
        }
        if(!account.getDOB().equals(changedAccount.getDOB())){
            account.setDOB(changedAccount.getDOB());
        }
        if(!account.getProfileImage().equals(changedAccount.getProfileImage())){
            account.setProfileImage(changedAccount.getProfileImage());
        }


        return AccountDTO.fromAccount(changedAccount);
    }
}
