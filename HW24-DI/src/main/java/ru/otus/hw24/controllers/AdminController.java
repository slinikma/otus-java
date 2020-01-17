package ru.otus.hw24.controllers;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.hw24.domain.User;
import ru.otus.hw24.repository.UserRepository;

import java.io.IOException;

// Контроллер аннотирован Component, спринг создаст объект этого класса
// + ожидает найти @GetMapping
@Controller
@RequiredArgsConstructor
public class AdminController {

  // Подставит спринг
  private final UserRepository userRepository;

  // GET /admin -> GET /admin
  @GetMapping(path = {"/", "/admin"})
  public String adminView(@NotNull Model model) {
    return "admin_main.html";
  }

  // GET /admin?action=showUsers -> GET /admin/user/list
  @GetMapping(path = {"/admin/user/list"})
  public String userListView(@NotNull Model model) {
    this.userRepository.getAllUsers().forEach(user -> {
      model.addAttribute("users", userRepository.getAllUsers());
    });

    return "admin_show_users.html";
  }

  // GET /admin?action=createUser -> GET /admin/user/create
  @GetMapping(path = {"/admin/user/create"})
  public String userCreateView(@NotNull Model model) {
    model.addAttribute("user", new User());
    return "admin_create_user.html";
  }

  @PostMapping("/admin/user/save")
  public RedirectView userSave(@NotNull @ModelAttribute User user) {
    this.userRepository.save(user.getLogin(), user.getPassword());
    return new RedirectView("/admin/user/list", true);
  }
}
