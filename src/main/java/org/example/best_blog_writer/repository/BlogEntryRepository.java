package org.example.best_blog_writer.repository;

import org.example.best_blog_writer.model.BlogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogEntryRepository extends JpaRepository<BlogEntry, Long> {

}
