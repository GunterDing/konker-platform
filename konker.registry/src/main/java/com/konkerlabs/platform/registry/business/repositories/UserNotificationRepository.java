package com.konkerlabs.platform.registry.business.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.konkerlabs.platform.registry.business.model.Tenant;
import com.konkerlabs.platform.registry.business.model.UserNotification;

public interface UserNotificationRepository extends MongoRepository<UserNotification, String>{
    @Query("{ 'destination' : ?0 }")
    List<UserNotification> findByDestination(String email);
    
    @Query("{ 'destination' : ?0, 'uuid' : ?1 }")
    UserNotification getByDestinationAndUuid(String email, String uuid);
}
