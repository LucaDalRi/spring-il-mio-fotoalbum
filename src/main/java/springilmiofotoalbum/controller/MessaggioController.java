package springilmiofotoalbum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springilmiofotoalbum.model.Messaggio;
import springilmiofotoalbum.repository.MessaggioRepository;

import java.util.List;

@Controller
@RequestMapping("/messaggio")
public class MessaggioController {
    @Autowired
    MessaggioRepository messaggioRepository;

    @GetMapping
    public String list(Model model) {
        List<Messaggio> messaggio = messaggioRepository.findAll();
        model.addAttribute("messaggioList", messaggio);
        return "/messaggio/index";
    }
}
