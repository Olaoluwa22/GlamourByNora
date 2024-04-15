package com.GlamourByNora.api.dto;

import com.GlamourByNora.api.util.PaystackBearer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.ValidationException;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaystackTransactionRequestDto {
    private int amount;
    private String email;
    private String reference;
    private String subAccount;
    private PaystackBearer bearer = PaystackBearer.ACCOUNT;
    private String callback_url;
    private Integer transaction_charge;
    private List<String> channel;

    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getReference() {
        return reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }
    public String getSubAccount() {
        return subAccount;
    }
    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
    }
    public PaystackBearer getBearer() {
        return bearer;
    }
    public void setBearer(PaystackBearer bearer) {
        this.bearer = bearer;
    }
    public String getCallback_url() {
        return callback_url;
    }
    public void setCallback_url(String callback_url) {
        this.callback_url = callback_url;
    }
    public Integer getTransaction_charge() {
        return transaction_charge;
    }
    public void setTransaction_charge(Integer transaction_charge) {
        this.transaction_charge = transaction_charge;
    }
    public List<String> getChannel() {
        return channel;
    }
    public void setChannel(List<String> channel) {
        if (!channel.contains("card") || !channel.contains("bank")) {
            throw new ValidationException("Invalid Channel, channel can only be 'bank' or `card`");
        }
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "PaystackTransactionRequestDto{" +
                "amount='" + amount + '\'' +
                ", email='" + email + '\'' +
                ", reference='" + reference + '\'' +
                ", subAccount='" + subAccount + '\'' +
                ", bearer=" + bearer +
                ", callback_url='" + callback_url + '\'' +
                ", transaction_charge=" + transaction_charge +
                ", channel=" + channel +
                '}';
    }
}
