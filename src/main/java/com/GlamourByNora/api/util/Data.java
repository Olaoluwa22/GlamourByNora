package com.GlamourByNora.api.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.tomcat.util.http.parser.Authorization;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Data {
    private String currency;
    private String transaction_date;
    private String status;
    private String domain;
    private String reference;
    private String gateway_response;
    private String channel;
    private String ip_address;
    private BigDecimal amount;
    private String plan;
    private String paid_at;
    private Authorization authorization;
    public Data(){}

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
    public Authorization getAuthorization() {
        return authorization;
    }
    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public String getDomain() {
        return domain;
    }
    public void setDomain(String domain) {
        this.domain = domain;
    }
    public String getPlan() {
        return plan;
    }
    public void setPlan(String plan) {
        this.plan = plan;
    }
    public String getPaid_at() {
        return paid_at;
    }
    public void setPaid_at(String paid_at) {
        this.paid_at = paid_at;
    }
    @Override
    public String toString() {
        return "Data{" +
                "currency='" + currency + '\'' +
                ", transaction_date='" + transaction_date + '\'' +
                ", status='" + status + '\'' +
                ", domain='" + domain + '\'' +
                ", reference='" + reference + '\'' +
                ", gateway_response='" + gateway_response + '\'' +
                ", channel='" + channel + '\'' +
                ", ip_address='" + ip_address + '\'' +
                ", amount='" + amount + '\'' +
                ", plan='" + plan + '\'' +
                ", paid_at='" + paid_at + '\'' +
                ", authorization=" + authorization +
                '}';
    }
}
