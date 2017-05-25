package com.konkerlabs.platform.registry.idm.domain.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface OauthClientDetailRepository extends MongoRepository<OauthClientDetails, String> {

    /*@Query("{ 'clientId' : ?0 }")
    OauthClientDetails findOauthClientDetails(String clientId);*/

    @Query("{'tenantId': ?0, 'applicationId': ?1}")
    List<OauthClientDetails> findAllOauthClientDetailsByTenant(String tenantId, String applicationId);

    /*boolean updateOauthClientDetailsArchive(String clientId, boolean archive);*/


    /*void saveOauthClientDetails(OauthClientDetails clientDetails);

    boolean removeOauthClientDetails(OauthClientDetails clientDetails);*/

    /*void saveAuthorizationCode(AuthorizationCode authorizationCode);

    AuthorizationCode removeAuthorizationCode(String code);*/
}