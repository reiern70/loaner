package com.antilia.loan.common.domain;

import com.antilia.util.file.FileUtils;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames={"user_id","currency"})})
@org.hibernate.annotations.Table(
        appliesTo = "LoanerData",
        indexes = {
                @Index(name="LoanerData_user_lastLoanDate_index", columnNames = {"user_id", "lastLoanDate"}),
                @Index(name="LoanerData_currency_lastLoanDate_index", columnNames = {"currency", "lastLoanDate"}),
                @Index(name="LoanerData_currency_rate_lastLoanDate_index", columnNames = {"currency", "rate", "lastLoanDate"})
        })
public class LoanerData  extends EntityBase {

    @ManyToOne(fetch = FetchType.EAGER)
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

    /**
     * Flag used to mark a loaner as being currently used to calculate a loan request. So, this user is not part
     * of any other loand computation at the same time.
     */
    private boolean beingUsedForLoan = false;

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

    public boolean deduceAmount(long amount) {
        if(amount <= this.available) {
            available = available - amount;
            return true;
        }
        return false;
    }

    public long deduceAllAmount() {
        long temp = this.available;
        available = 0L;
        return temp;
    }

    public boolean isBeingUsedForLoan() {
        return beingUsedForLoan;
    }

    public void setBeingUsedForLoan(boolean beingUsedForLoan) {
        this.beingUsedForLoan = beingUsedForLoan;
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

    public static List<LoanerData> fromResources() {
        List<LoanerData> loanerDatas = new ArrayList<LoanerData>();
        for(String line: FileUtils.readLines(LoanerData.class.getResourceAsStream("Market-Data01.csv"))){
            LoanerData loanerData = new LoanerData();
            String[] values = line.split(",");
            User user = new User();
            String[] names = values[0].split("\\s+");
            user.setName(names[0]);
            user.setLastName(names[1]);
            user.setEmail(user.getName()+"."+user.getLastName()+"@loaner.com");
            user.setRole(UserRole.loaner);
            loanerData.setUser(user);
            loanerData.setLastLoanDate(new Date());
            loanerData.setRate(Double.parseDouble(values[1]));
            loanerData.setAvailable(Long.parseLong(values[2]));
            loanerData.setCurrency(Currency.BritishPounds);
            loanerDatas.add(loanerData);
        }
        return loanerDatas;
    }

    @Override
    public String toString() {
        return "LoanerData{" +
                "user=" + user +
                ", rate=" + rate +
                ", available=" + available +
                ", lastLoanDate=" + lastLoanDate +
                ", currency=" + currency +
                '}';
    }
}

