package com.antilia.loan.common.domain;


import javax.persistence.*;
import java.util.Date;

@Entity
public class LoanRequest extends EntityBase {

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;
    private long amount;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Currency currency;
    @Column(nullable = false)
    private Date requestDate;

    public LoanRequest() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoanRequest)) return false;

        LoanRequest that = (LoanRequest) o;

        if (!user.equals(that.user)) return false;
        return requestDate.equals(that.requestDate);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + requestDate.hashCode();
        return result;
    }
}
