package com.GlamourByNora.api.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.logging.Log;
import org.apache.tomcat.util.http.parser.Authorization;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class PaystackVerificationData {
    private String currency;
    private String transaction_date;
    private String status;
    private String reference;
    private String gateway_response;
    private String channel;
    private String ip_address;
    private String amount;
    private Log log;
    private Authorization authorization;
    public PaystackVerificationData(){}

    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getTransaction_date() {
        return transaction_date;
    }
    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getReference() {
        return reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }
    public String getGateway_response() {
        return gateway_response;
    }
    public void setGateway_response(String gateway_response) {
        this.gateway_response = gateway_response;
    }
    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }
    public String getIp_address() {
        return ip_address;
    }
    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }
    public Log getLog() {
        return log;
    }
    public void setLog(Log log) {
        this.log = log;
    }
    public Authorization getAuthorization() {
        return authorization;
    }
    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "VerificationData{" +
                "currency='" + currency + '\'' +
                ", transaction_date='" + transaction_date + '\'' +
                ", status='" + status + '\'' +
                ", reference='" + reference + '\'' +
                ", gateway_response='" + gateway_response + '\'' +
                ", channel='" + channel + '\'' +
                ", ip_address='" + ip_address + '\'' +
                ", amount='" + amount + '\'' +
                ", authorization=" + authorization +
                '}';
    }
}
