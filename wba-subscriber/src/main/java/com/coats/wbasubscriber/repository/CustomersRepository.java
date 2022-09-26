package com.coats.wbasubscriber.repository;

import com.coats.wbasubscriber.model.Customers;
import org.springframework.data.repository.CrudRepository;

public interface CustomersRepository extends CrudRepository<Customers, String > {
}
