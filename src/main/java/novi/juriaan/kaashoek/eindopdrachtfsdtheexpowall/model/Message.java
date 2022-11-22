package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long messageID;
    @Column
    private Long sender;
    @Column
    private Long receiver;
    @Column
    private String message;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account accountMes;

    public Message(long messageID, Long sender, Long receiver, String message, Account accountMes) {
        this.messageID = messageID;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.accountMes = accountMes;
    }

    public Message() {
    }

    public long getMessageID() {
        return messageID;
    }

    public void setMessageID(long messageID) {
        this.messageID = messageID;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Account getAccountMes() {
        return accountMes;
    }

    public void setAccountMes(Account accountMes) {
        this.accountMes = accountMes;
    }
}