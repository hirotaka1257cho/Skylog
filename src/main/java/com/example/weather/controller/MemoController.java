package com.example.weather.controller;

import java.time.LocalDate;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.weather.domain.Memo;
import com.example.weather.domain.User;
import com.example.weather.form.MemoCreateForm;
import com.example.weather.service.MemoService;
import com.example.weather.service.UserService;

@Controller
@RequestMapping("/memos")
public class MemoController {

    private final MemoService memoService;
    private final UserService userService;

    public MemoController(MemoService memoService, UserService userService) {
        this.memoService = memoService;
        this.userService = userService;
    }

    @GetMapping("")
    public String memoList(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email);
        model.addAttribute("list", memoService.getMemoListByUserId(user.getId()));
        return "memos/list";
    }

    @GetMapping("/create")
    public String create(MemoCreateForm createForm, Model model) {
        createForm.setDates(LocalDate.now());
        model.addAttribute("createForm", createForm);
        return "memos/create";
    }

    @PostMapping("/create")
    public String create(
            @ModelAttribute("createForm") @Validated MemoCreateForm createForm,
            BindingResult result,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        System.out.println("エラー数: " + result.getErrorCount());

        if (result.hasErrors()) {
            return "memos/create"; // ← addAttribute不要
        }

        String email = userDetails.getUsername();
        User user = userService.findByEmail(email);
        memoService.create(createForm, user.getId());
        return "redirect:/memos";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable int id, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        Memo memo = memoService.findById(id);
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email);
        if (memo.getUserId() == user.getId()) {
            model.addAttribute("detail", memo);
            return "memos/detail";
        } else {
            return "error";
        }

    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        Memo memo = memoService.findById(id);
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email);
        if (memo.getUserId() == user.getId()) {
            MemoCreateForm createForm = new MemoCreateForm();
            createForm.setDates(memo.getDates().toLocalDate());
            createForm.setTexts(memo.getTexts());
            createForm.setCity(memo.getCity());

            model.addAttribute("createForm", createForm);
            model.addAttribute("memoId", memo.getId());
            return "memos/edit";
        } else {
            return "error";
        }

    }

    @PostMapping("/{id}/edit")
    public String edit(@ModelAttribute("createForm") @Validated MemoCreateForm createForm,
            BindingResult result, @PathVariable int id,
            @AuthenticationPrincipal UserDetails userDetails, Model model) {
        Memo memo = memoService.findById(id);
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email);

        if (result.hasErrors()) {
            model.addAttribute("memoId", memo.getId());
            return "memos/edit";
        }

        if (memo.getUserId() == user.getId()) {
            memoService.update(id, createForm);
            return "redirect:/memos/" + id;
        } else {
            return "error";
        }

    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable int id, @AuthenticationPrincipal UserDetails userDetails) {
        Memo memo = memoService.findById(id);
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email);
        if (memo.getUserId() == user.getId()) {
            memoService.deleteById(id);
            return "redirect:/memos";
        } else {
            return "error";
        }

    }
}
