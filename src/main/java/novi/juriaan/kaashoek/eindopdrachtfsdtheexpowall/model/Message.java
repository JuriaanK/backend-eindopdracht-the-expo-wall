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
    private String messageContent;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account accountMes;

    public Message(long messageID, Long sender, Long receiver, String messageContent, Account accountMes) {
        this.messageID = messageID;
        this.sender = sender;
        this.receiver = receiver;
        this.messageContent = messageContent;
        this.accountMes = accountMes;
    }

    public Message() {
    }

    public Message(long messageID, Long sender, Long receiver, String messageContent) {
        this.messageID = messageID;
        this.sender = sender;
        this.receiver = receiver;
        this.messageContent = messageContent;
    }

    public Message(Long sender, Long receiver, String messageContent) {
        this.sender = sender;
        this.receiver = receiver;
        this.messageContent = messageContent;
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
        return messageContent;
    }

    public void setMessage(String message) {
        this.messageContent = messageContent;
    }

    public Account getAccountMes() {
        return accountMes;
    }

    public void setAccountMes(Account accountMes) {
        this.accountMes = accountMes;
    }
}