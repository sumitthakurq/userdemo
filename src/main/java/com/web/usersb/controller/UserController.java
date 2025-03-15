package com.web.usersb.controller;



import com.web.usersb.constant.UserConstant;
import com.web.usersb.pojo.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
//@slf
public class UserController {

   private static final Logger logger = LoggerFactory.getLogger(UserController.class);
List<UserDTO> users = new ArrayList<>();

@GetMapping("/")
public ResponseEntity<String> getData(){
    System.out.println("Inside getData ::: ");
    logger.info("Inside getData Logger   :: ");
    return new ResponseEntity<String>("Service is working fine",HttpStatus.OK);
}
@PostMapping("/saveUser")
    public ResponseEntity<String> saveUser(@RequestBody UserDTO userDto){
        logger.info("Inside Info Logger   :: "+userDto.getId());
        logger.debug("Inside debug Logger :: ");
        logger.error("Inside error Logger :: ");
users.add(userDto);
        return ResponseEntity.status(OK).body(UserConstant.RECORD_SAVED);
    }
    @GetMapping("/getAllUser")
    public ResponseEntity<List<UserDTO>> getUserList(){
        logger.info("Inside getUserList, info Logger :: ");
    return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<List<UserDTO>> getUserById(@PathVariable("id") int id){
        logger.info("Inside getUserById, info Logger :: "+id);
     UserDTO user = users.stream().filter(userDto->userDto.getId()==id).findFirst().orElseGet(()->new UserDTO());
        return new ResponseEntity(user, HttpStatus.OK);
    }
    @DeleteMapping("/deleteUsers")
    public ResponseEntity<String> deleteUsers(){
        logger.info("Inside deleteUsers, info Logger :: ");
        users.clear();
        return new ResponseEntity(UserConstant.DELETE_RECORD, HttpStatus.OK);
    }

}
