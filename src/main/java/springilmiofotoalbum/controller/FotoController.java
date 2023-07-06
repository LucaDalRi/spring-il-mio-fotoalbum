package springilmiofotoalbum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springilmiofotoalbum.model.Foto;
import springilmiofotoalbum.repository.FotoRepository;

import java.util.List;

@Controller
@RequestMapping("/")
public class FotoController {

    @Autowired
    private FotoRepository fotoRepository;

    @GetMapping
    public String list(@RequestParam(name = "keyword", required = false) String searchString, Model model) {
        List<Foto> foto;

        if (searchString == null || searchString.isBlank()) {
            foto = fotoRepository.findAll();
        } else {
            foto = fotoRepository.findByTitolo(searchString);
        }
        model.addAttribute("fotoList", foto);
        model.addAttribute("searchInput", searchString == null ? "" : searchString);
        return "/home";
    }


}
