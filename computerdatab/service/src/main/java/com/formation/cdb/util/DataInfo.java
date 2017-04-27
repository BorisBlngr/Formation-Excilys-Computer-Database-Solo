package com.formation.cdb.util;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.formation.cdb.service.ComputerService;

@Repository("dataInfo")
public class DataInfo {
    @Autowired
    ComputerService computerService;

    private AtomicInteger computerCount = new AtomicInteger(0);

    /**
     * Synchronized the count value with number of element in the db computer.
     */
    public void synchronizedWithDb() {
        computerCount.set(computerService.getNbComputersDb());
    }

    /**
     * @return computerCount
     */
    public int getComputerCount() {
        if (computerCount.intValue() == 0) {
            synchronizedWithDb();
        }
        return computerCount.intValue();
    }

    /**
     * Increase the computer count.
     */
    public void increaseComputerCount() {
        this.computerCount.incrementAndGet();
    }

    /**
     * Decrease the computer count.
     */
    public void decreaseComputerCount() {
        this.computerCount.decrementAndGet();
    }

    @Override
    public String toString() {
        return "DataInfo [computerCount=" + computerCount + "]";
    }

}
