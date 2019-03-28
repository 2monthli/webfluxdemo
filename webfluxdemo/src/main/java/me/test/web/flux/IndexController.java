package me.test.web.flux;
import java.time.Duration;
import java.util.Arrays;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/flux")
public class IndexController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }
    
    @GetMapping("/name/{id}")
    public String name(@PathVariable("id") String id) {
    	System.out.println("id: "+id);
        return "leap";
    }
    
    @GetMapping("/mono")
    public Mono<String> baseApi() {                  //1
        return Mono.just("Hello,Reactive Program");   //2
    }
    
    @GetMapping("/fluxstr")
    public Flux<String> getFluxString() {
        String[] dataSet = new String[]{"This is 1", "This is 2", "This is 3", "This is 4"};
        return Flux.fromIterable(Arrays.asList(dataSet));
    }
    
    @GetMapping(value = "/sse/object", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> sse() throws Exception {
        return Flux.interval(Duration.ofSeconds(1))
               .map(second ->second+" s").take(5);
    }
  
}

