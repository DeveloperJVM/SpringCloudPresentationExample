package example.postman.microservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("postMicroservice")
public interface PostMicroserviceFeignClient {

    @GetMapping("/availableLetters")
    String getAvailableLetters();
}
