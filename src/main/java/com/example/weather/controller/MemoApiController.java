package com.example.weather.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.weather.domain.Memo;
import com.example.weather.domain.User;
import com.example.weather.form.MemoCreateForm;
import com.example.weather.service.MemoService;
import com.example.weather.service.UserService;

@RestController
@RequestMapping("/api/memos")
public class MemoApiController {

    private final MemoService memoService;
    private final UserService userService;

    public MemoApiController(MemoService memoService, UserService userService) {
        this.memoService = memoService;
        this.userService = userService;
    }

    @GetMapping("")
    public List<Memo> memoList(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email);
        return memoService.getMemoListByUserId(user.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Memo> findById(@PathVariable int id, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email);
        Memo memo = memoService.findById(id);
        if (memo.getUserId() == user.getId()) {
            return ResponseEntity.ok(memo);
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @PostMapping("")
    public void create(@RequestBody MemoCreateForm createForm, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email);
        memoService.create(createForm, user.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody MemoCreateForm createForm,
            @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email);
        Memo memo = memoService.findById(id);
        if (memo.getUserId() == user.getId()) {
            memoService.update(id, createForm);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(403).build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email);
        Memo memo = memoService.findById(id);
        if (memo.getUserId() == user.getId()) {
            memoService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(403).build();
        }

    }
}
