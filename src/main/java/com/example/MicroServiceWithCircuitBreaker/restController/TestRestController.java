package com.example.MicroServiceWithCircuitBreaker.restController;

import com.example.MicroServiceWithCircuitBreaker.entity.User;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class TestRestController {

    @Autowired
    private RestTemplate restTemplate;

    public static final String USER_SERVICE="userService";

    private static final String URL = "http://localhost:9090/v1/getAllUsersFromSpringBoot";

    private int attempt=1;

    ///  for list of object with CircuitBreaker
    @GetMapping("/usersForCircuit")
    @CircuitBreaker(name =USER_SERVICE,fallbackMethod = "getAllUsers")   /// for CircuitBreaker examples
    //@Retry(name = USER_SERVICE,fallbackMethod = "getAllUsers")
    public List<User> displayUsers() {

        System.out.println("displayUsersForCircuit method called "+attempt++ +" times "+" at "+new Date());
        return restTemplate.getForObject(URL, ArrayList.class);
    }

    ///  for list of object with retry
    @GetMapping("/usersForRetry")
    //@CircuitBreaker(name =USER_SERVICE,fallbackMethod = "getAllUsers")   /// for CircuitBreaker examples
    @Retry(name = USER_SERVICE,fallbackMethod = "getAllUsers")
    public List<User> displayUsersForRetry() {

        System.out.println("displayUsersForRetry method called "+attempt++ +" times "+" at "+new Date());
        return restTemplate.getForObject(URL, ArrayList.class);
    }


    ///  for object with CircuitBreaker
    @GetMapping("/userForCircuit")
    @CircuitBreaker(name =USER_SERVICE,fallbackMethod = "getUser")   /// for CircuitBreaker examples
    //@Retry(name = USER_SERVICE,fallbackMethod = "getUser")
    public User displayUser() {

        System.out.println("displayUsersForCircuit method called "+attempt++ +" times "+" at "+new Date());
        return restTemplate.getForObject(URL, User.class);
    }

    ///  for object with retry
    @GetMapping("/userForRetry")
    //@CircuitBreaker(name =USER_SERVICE,fallbackMethod = "getUser")   /// for CircuitBreaker examples
    @Retry(name = USER_SERVICE,fallbackMethod = "getUser")
    public User displayUserForRetry() {

        System.out.println("displayUsersForRetry method called "+attempt++ +" times "+" at "+new Date());
        return restTemplate.getForObject(URL, User.class);
    }


    public List<User> getAllUsers(Exception e){
        final List<User> listObj=new ArrayList<>();
        try{
            listObj.add(new User(11,"Sudhakar11","Sudhakar11@gmail.com"));
            listObj.add(new User(12,"Sudhakar12","Sudhakar12@gmail.com"));
            listObj.add(new User(13,"Sudhakar13","Sudhakar13@gmail.com"));
        }catch (final Exception ee){
            e.printStackTrace();
        }
        return listObj;
    }

    public User getUser(final Exception e){
        return new User(11,"Sudhakar11","Sudhakar11@gmail.com");

    }

}
