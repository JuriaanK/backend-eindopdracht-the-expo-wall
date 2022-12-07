package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.service;

import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.AccountDTO;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.exceptions.RecordNotFoundException;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.Account;

import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model.User;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.AccountRepository;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.repository.UserRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepos;
    private final UserRepository userRepos;

    public AccountService(AccountRepository accountRepos, UserRepository userRepos) {
        this.accountRepos = accountRepos;

        this.userRepos = userRepos;
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

    public AccountDTO createAccount (AccountDTO accountDTO) {
        Account account = AccountDTO.toAccount(accountDTO);
        Account savedAccount = accountRepos.save(account);

        User linkedUser = savedAccount.getUser();

        String UserID = linkedUser.getUsername();

        User getLinkedUser = userRepos.findById(UserID).get();

        getLinkedUser.setAccountID(savedAccount.getId());

        userRepos.save(getLinkedUser);


        return AccountDTO.fromAccount(savedAccount);
    }

    public Account uploadProfileImage(Long id, MultipartFile file) throws IOException {
        if(!accountRepos.existsById(id)) throw new RecordNotFoundException(id.toString());
        String name = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Account account = accountRepos.findById(id).get();
        account.setImageName(name);
        account.setProfileImage(file.getBytes());

        accountRepos.save(account);

        return account;
    }

    public AccountDTO changeAccount (Long id, AccountDTO inputDto){
        Account changedAccount = AccountDTO.toAccount(inputDto);
        Account account = accountRepos.findById(id).get();

        if(accountRepos.findById(id).isPresent()) {

            if (!account.getFirstName().equals(changedAccount.getFirstName())) {
            account.setFirstName(changedAccount.getFirstName());
            }
            if (!account.getLastName().equals(changedAccount.getLastName())) {
            account.setLastName(changedAccount.getLastName());
            }
            if (!account.getDOB().equals(changedAccount.getDOB())) {
            account.setDOB(changedAccount.getDOB());
            }
            if(account.getProfileImage() != null){
            if (!account.getProfileImage().equals(changedAccount.getProfileImage())) {
                account.setProfileImage(changedAccount.getProfileImage());
            }}

            accountRepos.save(changedAccount);
         }
        return AccountDTO.fromAccount(changedAccount);
    }
}
