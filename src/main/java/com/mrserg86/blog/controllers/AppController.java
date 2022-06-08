//Задача
//
//Создать сайт блога.
//
//1. На главной странице список анонсов статей. При нажалии на кнопку "читать", открывается полная статья в новой странице
//2. Авторизованные пользователи с ролью "WRITEE" могут создавать свои статьи. Кнопка добавления на панели меню.
//3. Авторизованные пользователи с ролью "WRITEE" могут изменяит свои статьи.
//Кнопка "редактировать" под анонсом и под статьей.
//4. Авторизованные пользователи с ролью "WRITEE" могут удалять статьи. Кнопка "удалить" под анонсом и под самой статьей.
//5. Авторизованные пользователи с ролью "ADMIN" могут удалять и менять любые статьи.
//6. Любая страницы должна иметь панель меню вверху, на которой есть пункты меню
//-- "анонсы" - отображается для всех пользователей
//-- "добавить" - отображается для авторизованных пользователей с ролью "WRITER"
//-- "войти" - отображается для не аутентифицированных пользователей
//-- "выйти" - отображается для аутентифицированных пользователей

package com.mrserg86.blog.controllers;

import com.mrserg86.blog.models.Post;
import com.mrserg86.blog.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class AppController {

    @Autowired
    private PostRepository postRepository;
    public String userName;

    @GetMapping("/")
    public String blog(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal). getUsername();
            model.addAttribute("Username", username);
            userName = username;
        } else {
            String username = principal. toString();
            model.addAttribute("Username", username);
            userName = username;
        }
        return "blog";
    }

    @GetMapping("/blog/add")
    public String blogAdd() {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String fullText, Model model) {
        Post post = new Post(title, anons, fullText, userName);
        postRepository.save(post);
        return "blog";
    }

    @GetMapping("/blog{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        if(!postRepository.existsById(id)) {
            return "blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-details";
    }

    @GetMapping("/blog{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if(!postRepository.existsById(id)) {
            return "blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";
    }

    @PostMapping("/blog{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String fullText, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFullText(fullText);
        postRepository.save(post);
        return "blog";
    }

    @GetMapping("/blog{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "blog";
    }

}
