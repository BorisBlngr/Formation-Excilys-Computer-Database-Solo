package com.formation.cdb.util;

import java.util.concurrent.atomic.AtomicInteger;

import com.formation.cdb.service.ComputerService;

/**
 * @author excilys
 *
 */
public enum DataInfo {
    INSTANCE;

    private AtomicInteger computerCount = new AtomicInteger(0);

    /**
     * Constructeur.
     */
    DataInfo() {
        computerCount.set(ComputerService.INSTANCE.getNbComputersDb());
    }

    /**
     * Synchronized the count value with number of element in the db computer.
     */
    public void synchronizedWithDb() {
        computerCount.set(ComputerService.INSTANCE.getNbComputersDb());
    }

    public int getComputerCount() {
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
