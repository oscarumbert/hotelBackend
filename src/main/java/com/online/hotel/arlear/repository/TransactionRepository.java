package com.online.hotel.arlear.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online.hotel.arlear.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Long>{

}
