package com.matheustorres.eadhub.course.repositories.elasticsearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.matheustorres.eadhub.course.documents.CourseDocument;

public interface CourseElasticsearchRepository extends ElasticsearchRepository<CourseDocument, String> {
    Page<CourseDocument> findByNameContainingOrDescriptionContaining(String name, String description, Pageable pageable);
}
