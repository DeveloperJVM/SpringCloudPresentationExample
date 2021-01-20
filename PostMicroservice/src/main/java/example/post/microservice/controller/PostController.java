package example.post.microservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PostController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/availableLetters")
    public ResponseEntity<String> availableLetters(){
        System.out.println("availableLetters mapping works ....");
        return ResponseEntity.ok().body("[Letter1, Letter2, Letter3]");
    }

    @GetMapping("/getDeliveredLettersCountFromPostman")
    @HystrixCommand(fallbackMethod = "getDeliveredLettersCountFromPostmanFailedMethod")
    public ResponseEntity<Integer> getDeliveredLettersCountFromPostman(){
        Integer result = restTemplate.getForObject("http://postmanMicroservice/deliveredLettersCount", Integer.class);
        return ResponseEntity.ok().body(result);
    }

    private ResponseEntity<Integer> getDeliveredLettersCountFromPostmanFailedMethod(){
        return ResponseEntity.ok().body(-1);
    }
}
