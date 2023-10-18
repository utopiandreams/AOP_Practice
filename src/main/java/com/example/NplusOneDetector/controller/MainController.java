package com.example.NplusOneDetector.controller;

import com.example.NplusOneDetector.entity.Category;
import com.example.NplusOneDetector.entity.Item;
import com.example.NplusOneDetector.repository.CategoryRepository;
import com.example.NplusOneDetector.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    @GetMapping("/item")
    public String getTest() {
        Item item = itemRepository.findById(1L).orElse(null);
        String name = item.getCategory().getName();
        System.out.println("name = " + name);
        return "ok";
    }

    @PostMapping("/item")
    @Transactional
    public String saveTest(){
        Category cat = Category.builder().name("옷").build();
        categoryRepository.save(cat);
        Item item = Item.builder().name("바지").category(cat).build();
        itemRepository.save(item);
        return "ok";
    }
}
