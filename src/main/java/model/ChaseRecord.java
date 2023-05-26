package model;

import java.time.LocalDate;

public class ChaseRecord {
    public LocalDate date;
    public String desc;
    public Double amt;
    public Double balance;

    @Override
    public String toString() {
        return "ChaseRecord{" +
                "date=" + date +
                ", desc='" + desc + '\'' +
                ", amt=" + amt +
                ", balance=" + balance +
                '}';
    }
}
