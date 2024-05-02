package org.example.best_blog_writer.service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.example.best_blog_writer.model.BlogEntry;
import org.example.best_blog_writer.repository.BlogEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogEntryService {
   @Autowired
   private BlogEntryRepository repository;

   // save blog entry
  public BlogEntry saveBlogEntry(String title, String author, String content) throws IllegalArgumentException {
    if(content.length()!=500) throw new IllegalArgumentException("Blog Entry content must exactly be 500 characters long");
    if(!(content.contains(title) && title.isBlank())) throw new IllegalArgumentException("Blog Entry content must include the title");
    // check the number of references , minimum 3
    if(countReferences(content) < 3) throw new IllegalArgumentException("Blog Entry content must include at least three references");
    return repository.save(new BlogEntry(title, author, content));
  }

  // get All entries
  public List<BlogEntry> getAllBlogEntries(){
    return repository.findAll();
  }

  // get top blog entries
  public List<BlogEntry> getTopBlogEntries(){
    List<BlogEntry> entries = repository.findAll();
    Stream<BlogEntry> entriesStream = entries.stream();
    List<BlogEntry> topEntries = entriesStream
        .sorted((e1, e2) -> Integer.compare(countReferences(e2.getContent()), countReferences(e1.getContent())))
        .collect(Collectors.toList());

    List<BlogEntry> topThree = new ArrayList<>();
    // take first three elements if there is at least three entries
    // or take every thing
    if(topEntries.size() >= 3 ) topThree = topEntries.subList(0,3);
    else topThree = topEntries;

    return topThree;

  }

  //Helper : count number of references in content
  private int countReferences(String content){
    if(content.contains("ref:")) return content.split("ref:").length-1;
    else return 0;
  }
}
