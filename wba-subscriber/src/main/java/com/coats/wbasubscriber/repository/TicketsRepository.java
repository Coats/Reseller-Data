package com.coats.wbasubscriber.repository;

import com.coats.wbasubscriber.model.Tickets;
import org.springframework.data.repository.CrudRepository;

public interface TicketsRepository extends CrudRepository<Tickets, String > {
}
