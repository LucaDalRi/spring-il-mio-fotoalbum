package springilmiofotoalbum.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import springilmiofotoalbum.model.Categoria;
import springilmiofotoalbum.model.Foto;
import springilmiofotoalbum.repository.CategorieRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/categoria")
public class CategorieController {

    @Autowired
    private CategorieRepository categorieRepository;

    @GetMapping
    public String index(Model model, @RequestParam("edit") Optional<Integer> categoriaId) {
        List<Categoria> categoriaList = categorieRepository.findAll();
        model.addAttribute("categoria", categoriaList);
        Categoria categoriaObj;
        if (categoriaId.isPresent()) {
            Optional<Categoria> categoriaDb = categorieRepository.findById(categoriaId.get());
            if (categoriaDb.isPresent()) {
                categoriaObj = categoriaDb.get();
            } else {
                categoriaObj = new Categoria();
            }
        } else {
            categoriaObj = new Categoria();
        }
        model.addAttribute("categoriaObj", categoriaObj);
        return "/categoria/index";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("categoriaObj") Categoria formCategoria,
                       BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categoria", categorieRepository.findAll());
            return "/categoria/index";
        }
        categorieRepository.save(formCategoria);
        return "redirect:/categoria";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Optional<Categoria> result = categorieRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Categoria categoriaToDelete = result.get();
        for (Foto foto : categoriaToDelete.getFoto()) {
            foto.getCategoria().remove(categoriaToDelete);
        }
        categorieRepository.deleteById(id);
        return "redirect:/categoria";
    }

}
