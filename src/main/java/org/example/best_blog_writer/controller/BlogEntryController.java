package org.example.best_blog_writer.controller;

import java.util.List;
import org.example.best_blog_writer.model.BlogEntry;
import org.example.best_blog_writer.service.BlogEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BlogEntryController {
  @Autowired
  private BlogEntryService service;

  // Get mapping to show the home page
  @GetMapping("/")
  public String home(Model model){
    model.addAttribute("entries",service.getAllBlogEntries() );
    return "home";
  }

  // Post mapping to submit a blog entry
  @PostMapping("/submit")
  public String submitBlogEntry(
     @RequestParam("title") String title,
      @RequestParam("author") String author,
      @RequestParam("content") String content,
      Model model
  ){
  try{
    service.saveBlogEntry(title, author, content);
    model.addAttribute("message", "Blog successfully submitted!");
  } catch (IllegalArgumentException e) {
    model.addAttribute("error", e.getMessage());
    e.printStackTrace();
  }
  return "submitResult";
  }

  // Show the submit page
  @GetMapping("/submit")
  public String showSubmitForm(){
    return "submit";
  }

  // Get mapping to show the results (the top blog entries)
  @GetMapping("/results")
  public String showResults(Model model){
   List<BlogEntry> topEntries = service.getTopBlogEntries();
   model.addAttribute("topEntries",topEntries );
   return  "results";
  }
}
