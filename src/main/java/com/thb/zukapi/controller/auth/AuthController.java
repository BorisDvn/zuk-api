package com.thb.zukapi.controller.auth;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thb.zukapi.dtos.person.PersonWriteTO;
import com.thb.zukapi.services.AdminService;
import com.thb.zukapi.services.HelperService;
import com.thb.zukapi.services.SeekerService;
import com.thb.zukapi.services.UserService;
import com.thb.zukapi.transfertobjects.user.SigninTO;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("zuk-api/v1/auth")
public class AuthController {

	@Autowired
	UserService userService;
	
	@Autowired
	HelperService helperService;
	
	@Autowired
	SeekerService seekerService;
	
	@Autowired
	AdminService adminService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody SigninTO loginRequest) {

		return userService.signIn(loginRequest);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody PersonWriteTO signUpRequest) {
		
		// make the signup according the rolet ype
		switch (signUpRequest.getRole()) {
			case "SEEKER":
				return ResponseEntity.ok(seekerService.addSeeker(signUpRequest));
			case "HELPER":
				return ResponseEntity.ok(helperService.addHelper(signUpRequest));
			case "ADMIN":
				return ResponseEntity.ok(adminService.addAdmin(signUpRequest));
			default:
				return new ResponseEntity<>("User Type does not exist", HttpStatus.BAD_REQUEST);
		}

	}
}
