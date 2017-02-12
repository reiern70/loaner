package com.antilia.loan.common.domain;

import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames={"user_id","answerDate"})})
@org.hibernate.annotations.Table(
        appliesTo = "LoanAnswer",
        indexes = {
                @Index(name="LoanAnswer_user_answerDate_index", columnNames = {"user_id", "answerDate"}),
                @Index(name="LoanAnswer_currency_answerDate_index", columnNames = {"currency", "answerDate"})
        })
public class LoanAnswer extends EntityBase {

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(nullable = false)
    private Date answerDate;
    private double rate;
    private double monthlyPayment;
    private double totalPayment;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Currency currency;

    public LoanAnswer() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(Date answerDate) {
        this.answerDate = answerDate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
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
        if (!(o instanceof LoanAnswer)) return false;

        LoanAnswer that = (LoanAnswer) o;

        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (answerDate != null ? !answerDate.equals(that.answerDate) : that.answerDate != null) return false;
        return currency == that.currency;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (answerDate != null ? answerDate.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }
}
