package com.example.shop.demo;


import com.example.shop.demo.User;
import com.example.shop.demo.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.createUser(user.getUsername(), user.getPassword(), user.getEmail());
    }

    @GetMapping("/getPasswordByEmail")
    public ResponseEntity<String> getPasswordByEmail(@RequestParam String email) {
        User user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(user.getPassword());
        }
    }

    @GetMapping("/countUsersWithEmail")
    public ResponseEntity<Integer> countUsersWithEmail(@RequestParam String email) {
        int res = 0;
        User user = userService.findByEmail(email);
        if (user == null) {
           res = 0;
        }
        else {
            res = -1;
        }
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<Boolean> changePassword(@RequestParam String email, @RequestParam String newPassword) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
        else {
            user.setPassword(newPassword);
            userService.save(user);
            return ResponseEntity.ok(true);
        }
    }

    @GetMapping("/getUserNameByEmail")
    public ResponseEntity<String> getUserNameByEmail(@RequestParam String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(user.getUsername());
        }
    }

    @GetMapping("/getNameColorByEmail")
    public ResponseEntity<String> getNameColorByEmail(@RequestParam String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(user.getNameColor());
        }
    }

    @PutMapping("/setNameColorByEmail")
    public ResponseEntity<Boolean> setNameColorByEmail(@RequestParam String email, @RequestParam String newNameColor) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
        else {
            user.setNameColor(newNameColor);
            userService.save(user);
            return ResponseEntity.ok(true);
        }
    }

    @GetMapping("/getTagByEmail")
    public ResponseEntity<String> getTagByEmail(@RequestParam String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(user.getTag());
        }
    }

    @PutMapping("/setTagByEmail")
    public ResponseEntity<Boolean> setTagByEmail(@RequestParam String email, @RequestParam String newTag) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
        else {
            user.setTag(newTag);
            userService.save(user);
            return ResponseEntity.ok(true);
        }
    }

    @GetMapping("/getTagColorByEmail")
    public ResponseEntity<String> getTagColorByEmail(@RequestParam String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(user.getTagColor());
        }
    }

    @PutMapping("/setTagColorByEmail")
    public ResponseEntity<Boolean> setTagColorByEmail(@RequestParam String email, @RequestParam String newTagColor) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
        else {
            user.setTagColor(newTagColor);
            userService.save(user);
            return ResponseEntity.ok(true);
        }
    }

}
