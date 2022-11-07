package novi.juriaan.kaashoek.eindopdrachtfsdtheexpowall.model;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long messageID;
    @Column
    private String sender;
    @Column
    private String receiver;
    @Column
    private String message;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account accountMes;

    public Message(long messageID, String sender, String receiver, String message, Account accountMes) {
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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
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