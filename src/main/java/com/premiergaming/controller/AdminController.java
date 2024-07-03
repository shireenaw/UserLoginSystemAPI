package com.premiergaming.controller;

import com.premiergaming.model.Error;
import com.premiergaming.model.dto.UsersDTO;
import com.premiergaming.service.interfaces.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {
    @Autowired
    IUsersService usersService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/createUser")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView createUser() {
        ModelAndView mav = new ModelAndView("admin/createUser");
        UsersDTO user = new UsersDTO();
        mav.addObject(user);
        return mav;
    }

    @PostMapping("/createNewUser")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> saveUser(@RequestBody UsersDTO user) {
        String errorMessage = "";
        if (user.getFirstName().isBlank() || user.getLastName().isBlank() || user.getEmail().isBlank()
                || user.getPassword().isBlank()  || user.getRole().isBlank()) {
            return new ResponseEntity<String>("Please fill in required fields", HttpStatus.OK);
        }else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            UsersDTO existingUser = usersService.getUserByEmail(user.getEmail());
            if (existingUser == null) {
                UsersDTO result = usersService.create(user);
                if (result.getUserId() > 0) {
                    return new ResponseEntity<String>("User created successfully", HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<String>("User is existed", HttpStatus.BAD_REQUEST);
            }
        }
        return null;
    }

    @GetMapping("/view/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UsersDTO> getUserByUserId(@PathVariable("userId") Integer userId) {
        UsersDTO user = usersService.getByUserId(userId);
        if (user != null) {
            return new ResponseEntity<UsersDTO>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getAllUser")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UsersDTO>> getAllUser() {
        List<UsersDTO> users = usersService.getAll();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/deleteUserByUserId/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteUserByUserId(@PathVariable("userId") Integer userId) {
        UsersDTO user = usersService.getByUserId(userId);
        if (user != null) {
            usersService.deleteUserByUserId(userId);
        }else {
            return new ResponseEntity<>("User is not existed.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("User deleted successfully.", HttpStatus.OK);
    }



}
