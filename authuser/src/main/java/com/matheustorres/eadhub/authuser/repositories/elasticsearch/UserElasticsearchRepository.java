package com.matheustorres.eadhub.authuser.repositories.elasticsearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.matheustorres.eadhub.authuser.documents.UserDocument;

public interface UserElasticsearchRepository extends ElasticsearchRepository<UserDocument, String> {
    Page<UserDocument> findByUsernameContainingOrFullNameContaining(String username, String fullName, Pageable pageable);
}
