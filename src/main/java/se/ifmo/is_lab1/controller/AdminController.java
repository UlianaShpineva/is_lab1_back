package se.ifmo.is_lab1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.ifmo.is_lab1.model.User;
import se.ifmo.is_lab1.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;


    @GetMapping("/admin-role-requests")
    public List<User> getAdminRequests() {
        return adminService.getAdminRequests();
    }

    @PostMapping("/approve-admin-role/{userId}")
    public ResponseEntity<String> approveAdminRoleRequest(@PathVariable Long userId) {
        adminService.approveAdminRoleRequest(userId);
        return new ResponseEntity<>("Admin role approved successfully.", HttpStatus.OK);
    }
}
