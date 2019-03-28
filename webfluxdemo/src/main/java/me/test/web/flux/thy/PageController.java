package me.test.web.flux.thy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import reactor.core.publisher.Mono;

//@Controller
//@RequestMapping("/flux/page")
public class PageController {

	@GetMapping("/index")
    public Mono<String> hello(final Model model) {
        model.addAttribute("message", "hello webflux thy");
       
        String path = "index";
        return Mono.create(monoSink -> monoSink.success(path));
    }
}
