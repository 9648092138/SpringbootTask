package com.op.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

import com.op.task.entity.MobileRequest;
import com.op.task.entity.Users;

import com.op.task.repository.UserRepository;
import com.sipios.springsearch.anotation.SearchSpec;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	UserRepository userrepo;
	

	
	//=====================add new users=====================//
	 @PostMapping("/users")
	  public Users createUser(@Valid @RequestBody Users user) {
		// Users useradd = mongorepo.save(user);
	    return userrepo.save(user);
	  }
	 
	 //===========================search user=====================//
	 @GetMapping("/filter")
	    public ResponseEntity<List<Users>> searchForCars(@SearchSpec Specification<Users> specs) {
	        return new ResponseEntity<>(userrepo.findAll(Specification.where(specs)), HttpStatus.OK);
	    }
	 
	 //=================update user detail ======================//
	 @PostMapping("/updateuserDetail")
		public ResponseEntity<?> updateCourse(@RequestBody Users user) {
			long userid = user.getUserId();
			System.out.println("id "+userid);
			Optional<Users> userExist = userrepo.findById(userid);
			if (userExist.isPresent()) {
				
				userExist.get().setFirstName(user.getFirstName());
				userExist.get().setLastName(user.getLastName());
				userExist.get().setDateOfBirth(user.getDateOfBirth());
				userrepo.save(userExist.get());
				return new ResponseEntity<>("user updated ", HttpStatus.OK);
				
			} else {
				
				return new ResponseEntity<>("user not found ", HttpStatus.OK);
			}

		}
	 
	 //===========user update history by user Mobile Number=============//
	 @PostMapping("/getByMobile")
	  public ResponseEntity<Users> getUsersBymob(@RequestBody MobileRequest user)
	      throws ResourceNotFoundException {
		 String mobile_number = user.getMobileNumber();
		 Users ad =new Users();
		 System.out.println(" "+mobile_number);
		 Optional<Users> userExist = userrepo.FindbyMob(mobile_number);
		 if (userExist.isPresent()== true) {
			 ad.setUserId(userExist.get().getUserId());
			 ad.setFirstName(userExist.get().getFirstName());
			 ad.setLastName(userExist.get().getLastName());
			 ad.setDateOfBirth(userExist.get().getDateOfBirth());
			 ad.setMobileNumber(userExist.get().getMobileNumber());
			 ad.setCreatedBy(userExist.get().getCreatedBy());
			 ad.setCreatedDate(userExist.get().getCreatedDate());
			 ad.setLastModifiedBy(userExist.get().getLastModifiedBy());
			 ad.setLastModifiedDate(userExist.get().getLastModifiedDate());
			 return ResponseEntity.ok().body(ad);
			 
			}else {
				return ResponseEntity.ok().body(null);
			}
	  }
	 
	 //============delete user using moblieNumber===============//
	 @PostMapping("/deteByMobileNuber")
	 public String deletuser(@RequestBody MobileRequest user) {
		 String mobile_number = user.getMobileNumber();
		 System.out.println(" "+mobile_number);
		 Optional<Users> userExist = userrepo.FindbyMob(mobile_number);
		 if (userExist.isPresent()== true) {
			 long userId = userExist.get().getUserId();
			 System.out.println(" user id "+userId);
			 try {
				userrepo.deleteById(userId);
				 return "user deleted ";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		return "user not found";
		
	 }
}
