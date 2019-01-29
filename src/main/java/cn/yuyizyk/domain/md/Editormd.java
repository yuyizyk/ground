package cn.yuyizyk.domain.md;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("md")
public class Editormd {
	@GetMapping("/{file}")
	public String md(String file) {
		return "index";
	}

	@GetMapping({ "/", "" })
	public Flux<MDFile> mds() throws IOException {
		return Flux.fromStream(Files.find(Paths.get("md"), Integer.MAX_VALUE, (p, fa) -> {
			return true;
		})).map(p -> MDFile.byPath(p));
	}
}
