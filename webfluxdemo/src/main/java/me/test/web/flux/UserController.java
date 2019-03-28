package me.test.web.flux;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;


@Controller
@RequestMapping("/flux")
public class UserController {

	@GetMapping("/user")
	@ResponseBody
    public String test(String id,String name) {
		System.out.println(id+" "+name);
        return id+" "+name;
    }
	
	
	@GetMapping("/user2")
	@ResponseBody
    public String test2(Param p) {
		System.out.println(p.getId()+" "+p.getName());
        return p.getId()+" "+p.getName();
    }
	
	@PostMapping("/user3")
	@ResponseBody
    public String test3(Param p) {
		System.out.println(p.getId()+" "+p.getName());
        return p.getId()+" "+p.getName();
    }
	
	@PostMapping("/user4")
	@ResponseBody
    public Mono<String> test5(@RequestPart("file") FilePart filePart) throws IOException {
		System.out.println(filePart.filename());
        Path tempFile = Files.createTempFile("test", filePart.filename());

        
        AsynchronousFileChannel channel =
                AsynchronousFileChannel.open(tempFile, StandardOpenOption.WRITE);
        DataBufferUtils.write(filePart.content(), channel, 0)
                .doOnComplete(() -> {
                    System.out.println("finish");
                })
            .subscribe();

      
//        filePart.transferTo(tempFile.toFile());

        System.out.println(tempFile.toString());
        return Mono.just(filePart.filename());
    }
	
	
	/*@RequestMapping("/toIndex")
	public String toIndex(Model model){
		model.addAttribute("message", "Hello Spring MVC 5!");
	    return "index.html";
	}*/
	
	@RequestMapping("/home")//参数还没能回传
	public Mono<ServerResponse> init(ServerRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "aqiu");
        map.put("time", new Date());
        List<String> list = new ArrayList<String>();
        list.add("param1");
        list.add("param2");
        list.add("param3");
        map.put("list", list);
        //返回thymeleaf模版页面
        return ServerResponse.ok().render("index", map);
    }
	
}

class Param{
	private String id;
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
