package com.antilia.loan.common.domain;


import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames={"user_id","currency"})})
@org.hibernate.annotations.Table(
        appliesTo = "LoanerData",
        indexes = {
                @Index(name="LoanerData_user_lastLoanDate_index", columnNames = {"user_id", "lastLoanDate"}),
                @Index(name="LoanerData_currency_lastLoanDate_index", columnNames = {"currency", "lastLoanDate"})
        })
public class LoanerData  extends EntityBase {

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
    private double rate;
    private long available;
    /**
     * Last date when user loaned some money,
     */
    @Column(nullable = false)
    private Date lastLoanDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Currency currency;


    public LoanerData() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public long getAvailable() {
        return available;
    }

    public void setAvailable(long available) {
        this.available = available;
    }

    public Date getLastLoanDate() {
        return lastLoanDate;
    }

    public void setLastLoanDate(Date lastLoanDate) {
        this.lastLoanDate = lastLoanDate;
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
        if (!(o instanceof LoanerData)) return false;

        LoanerData that = (LoanerData) o;

        if (!user.equals(that.user)) return false;
        return currency == that.currency;
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + currency.hashCode();
        return result;
    }
}

