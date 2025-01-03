package com.projectFolder.demo.Controller;

import com.projectFolder.demo.Mapper.LoginMapper;
import com.projectFolder.demo.Service.LoginServiceImpl;
import com.projectFolder.demo.dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    LoginMapper mapper;
    @Autowired
    private LoginServiceImpl loginServiceImpl;


    @GetMapping("/logindetails")
    public ResponseEntity<?> getUsers() {
        Map<String, List<LoginDto>> response = new HashMap<>();
        List<LoginDto> users = loginServiceImpl.getUserList();
        response.put("Users:", users);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/addUsers")
    public ResponseEntity<?> addUsers(@Valid @RequestBody LoginDto loginDto) {
        Map<String, String> response = new HashMap<>();
        String details=loginServiceImpl.addUsers(loginDto);
        if(details==null) {
            response.put("User added ", "Successfully");
        }else{
            response.put("User already exists ", "with the mailId");
            return ResponseEntity.status(400).body(response);
        }
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(String email, String password) {
        String loginResponse = loginServiceImpl.login(email, password);
        return ResponseEntity.status(200).body(loginResponse);
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email, @RequestParam String newPassword) {
        boolean isReset = loginServiceImpl.updatePassword(email, newPassword);
        if (isReset) {
            return "Password reset successfully!";
        }
        return "User not found!";
    }

    @GetMapping("/loginDetailsByEmail")
        public ResponseEntity<?> getDetailsByEmail(String email) {
            LoginDto loginResponse = loginServiceImpl.getUserByEmail(email);
            Map<String, String> failureresponse = new HashMap<>();
            if(loginResponse==null){
                failureresponse.put("User not","found");
                return ResponseEntity.status(400).body(loginResponse);
            }else{
            return ResponseEntity.status(200).body(loginResponse);
        }
    }
}
