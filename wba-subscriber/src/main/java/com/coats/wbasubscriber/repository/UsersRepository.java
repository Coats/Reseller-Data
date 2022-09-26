package com.coats.wbasubscriber.repository;

import com.coats.wbasubscriber.model.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users, String > {
}
