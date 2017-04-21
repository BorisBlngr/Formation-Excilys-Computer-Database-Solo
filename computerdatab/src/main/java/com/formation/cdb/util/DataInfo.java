package com.formation.cdb.util;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.formation.cdb.service.ComputerService;

/**
 * @author excilys
 *
 */
@Repository("dataInfo")
public class DataInfo {
    @Autowired
    ComputerService computerService;

    private AtomicInteger computerCount = new AtomicInteger(0);

    /**
     * Constructeur.
     */
    @PostConstruct
    void init() {
        computerCount.set(computerService.getNbComputersDb());
    }

    /**
     * Synchronized the count value with number of element in the db computer.
     */
    public void synchronizedWithDb() {
        computerCount.set(computerService.getNbComputersDb());
    }

    public int getComputerCount() {
        return computerCount.intValue();
    }

    /**
     * Increase the computer count.
     */
    public void increaseComputerCount() {
        System.out.println("try to increase : " + getComputerCount());
        this.computerCount.incrementAndGet();
        System.out.println("try to increase : " + getComputerCount());
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
