package com.coats.resellersubscriber.repository;

import com.coats.resellersubscriber.model.ResellerBulkOrderLine;
import org.springframework.data.repository.CrudRepository;

public interface ResellerBulkOrderLineRepository  extends CrudRepository<ResellerBulkOrderLine, String > {
}
