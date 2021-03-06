package example.postman.microservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import example.postman.microservice.feignClient.PostMicroserviceFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@RestController
public class PostmanController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PostMicroserviceFeignClient postMicroserviceFeignClient;


    @GetMapping("/getAvailableLettersFromPost")
    @HystrixCommand(fallbackMethod = "getAvailableLettersFromPostFailedMethod")
    public ResponseEntity<String> getAvailableLettersFromPost(){
//        String result = restTemplate.getForObject("http://postMicroservice/availableLetters", String.class);
        String result = postMicroserviceFeignClient.getAvailableLetters();
        return ResponseEntity.ok().body(result);
    }

    private ResponseEntity<String> getAvailableLettersFromPostFailedMethod(){
        return ResponseEntity.ok().body("sorry post microservice isn't available right now \n please try later");
    }

    @GetMapping("/deliveredLettersCount")
    public ResponseEntity<Integer> deliverLettersCount(){
        System.out.println("deliverLettersCount mapping works ....");
        return ResponseEntity.ok().body(new Random().nextInt(100));
    }

    private ResponseEntity<Integer> getAvailableLettersCountFromPostFailedMethod(){
        return ResponseEntity.ok().body(-1);
    }
}
