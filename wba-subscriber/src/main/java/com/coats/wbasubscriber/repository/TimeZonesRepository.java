package com.coats.wbasubscriber.repository;

import com.coats.wbasubscriber.model.TimeZones;
import org.springframework.data.repository.CrudRepository;

public interface TimeZonesRepository extends CrudRepository<TimeZones, String > {
}
