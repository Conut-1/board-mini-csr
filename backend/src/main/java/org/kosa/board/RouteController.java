package org.kosa.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouteController {
    @GetMapping({"/post/**", "/member/**"})
    public String forward() {
        return "forward:/index.html";
    }
}
