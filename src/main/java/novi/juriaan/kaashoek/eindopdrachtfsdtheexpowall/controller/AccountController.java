package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.controller;

import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.dto.AccountDTO;
import novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<AccountDTO>> getAccounts(){

        List<AccountDTO> accountDTOs = accountService.getAccounts();

        return ResponseEntity.ok().body(accountDTOs);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable("id") Long id){

        AccountDTO optionalAccount = accountService.getAccount(id);

        return ResponseEntity.ok().body(optionalAccount);
    }

    @PostMapping(value = "")
    public ResponseEntity<AccountDTO> createAccount (@RequestBody AccountDTO accountDTO){

        AccountDTO newAccount = accountService.createAccount(accountDTO);

        return ResponseEntity.created(null).body(newAccount);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateAccount (@PathVariable Long id, @RequestBody AccountDTO updatedAccountDTO){

        AccountDTO accountDTO = accountService.changeAccount(id, updatedAccountDTO);

        return ResponseEntity.ok().body(accountDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteAccount(@PathVariable Long id){

        accountService.deleteAccount(id);

        return ResponseEntity.noContent().build();
    }

}
