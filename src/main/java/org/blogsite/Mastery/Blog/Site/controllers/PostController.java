package org.blogsite.Mastery.Blog.Site.controllers;

import javax.annotation.Resource;

import org.blogsite.Mastery.Blog.Site.Repositories.AuthorRepository;
import org.blogsite.Mastery.Blog.Site.Repositories.CategoryRepository;
import org.blogsite.Mastery.Blog.Site.Repositories.PostRepository;
import org.blogsite.Mastery.Blog.Site.Repositories.TagRepository;
import org.blogsite.Mastery.Blog.Site.models.Author;
import org.blogsite.Mastery.Blog.Site.models.Category;
import org.blogsite.Mastery.Blog.Site.models.Post;
import org.blogsite.Mastery.Blog.Site.models.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {

	@Resource
	PostRepository postRepo;
	@Resource
	CategoryRepository catRepo;
	@Resource
	TagRepository tagRepo;
	@Resource
	AuthorRepository authorRepo;

	//when hits allPost pg finds/displays all posts on this pg.
	@GetMapping("/allPost")
	public String allPost(Model model) {
		model.addAttribute("Posts", postRepo.findAll());
		return "allPost";
	}

	// links "NEW BLOG" in Nav to new blog post page
	// finds all auth, cat, tags, for drop down menu on new blog pg
	@GetMapping("/newBlog")
	public String newBlog(Model model) {
		model.addAttribute("Authors", authorRepo.findAll());
		model.addAttribute("Categories", catRepo.findAll());
		model.addAttribute("Tags", tagRepo.findAll());
		return "newBlog";
	}

	// populate that form on new page with all other posts
	@PostMapping("/newBlog")
	public String submitPost(String postTitle, String body, String authorName, String postCategory, String tagName) {
		
		//must instantiate objects that composed a post
		Author author = authorRepo.findByAuthorName(authorName);
		Category category = catRepo.findByPostCategory(postCategory);
		Tag tag = tagRepo.findByTagName(tagName);
		
		postRepo.save(new Post(postTitle, body, author, category, tag));
		return "redirect:/allPost";
	}

	// get specific post
	@GetMapping("/allPost/{id}")
	public String getPost(@PathVariable Long id, Model model) {
		model.addAttribute("SinglePost", postRepo.findById(id).get());
		return "specPost";
	}
}
