package springilmiofotoalbum.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springilmiofotoalbum.model.Foto;
import springilmiofotoalbum.repository.FotoRepository;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("detail/{id}")
    public String detail(@PathVariable("id") Integer fotoId, Model model) {

        Optional<Foto> result = fotoRepository.findById(fotoId);
        if (result.isPresent()) {
            model.addAttribute("foto", result.get());
            return "/detail";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("foto", new Foto());
        return "/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("foto") Foto formFoto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/create";
        }
        fotoRepository.save(formFoto);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Foto foto = getFotoById(id);
        model.addAttribute("foto", foto);
        return "/edit";
    }

    @PostMapping("/edit/{id}")
    public String doEdit(
            @PathVariable Integer id,
            @Valid @ModelAttribute("pizza") Foto formFoto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        Foto fotoToEdit = getFotoById(id);
        if (bindingResult.hasErrors()) {
            return "/edit";
        }
        formFoto.setId(fotoToEdit.getId());
        fotoRepository.save(formFoto);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Foto fotoToDelete = getFotoById(id);
        fotoRepository.delete(fotoToDelete);
        return "redirect:/";
    }


    private Foto getFotoById(Integer id) {
        Optional<Foto> result = fotoRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Foto con id " + id + " non trovato");
        }
        return result.get();
    }
}
