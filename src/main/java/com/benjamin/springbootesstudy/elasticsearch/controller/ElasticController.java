package com.benjamin.springbootesstudy.elasticsearch.controller;

import com.benjamin.springbootesstudy.elasticsearch.document.BookBean;
import com.benjamin.springbootesstudy.elasticsearch.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ElasticController {
    @Autowired
    private BookService bookService;

    @GetMapping("/book/{id}")
    @ResponseBody
    public BookBean getBookById(@PathVariable String id) {
        Optional<BookBean> opt = bookService.findById(id);
        BookBean book = opt.get();
        System.out.println(book);
        return book;
    }

    @PostMapping("/save")
    @ResponseBody
    public void Save(@RequestBody BookBean req) {
        BookBean book;
        if (req.getId() == null) {
            book = new BookBean("1", "ES入门教程", "程裕强", "2018-10-01");
        } else {
            book = new BookBean(req.getId(), req.getTitle(), req.getAuthor(), req.getPostDate());
        }
        System.out.println(book);
        bookService.save(book);
    }

    @GetMapping("/searchByAuthor")
    @ResponseBody
    public Page<BookBean> searchByAuthor(@RequestParam(value = "author") String author) {
        return bookService.findByAuthor(author,
                PageRequest.of(0,
                        10));
    }

}