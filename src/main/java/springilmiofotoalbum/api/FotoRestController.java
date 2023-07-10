package springilmiofotoalbum.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import springilmiofotoalbum.exceptions.FotoNotFoundException;
import springilmiofotoalbum.model.Foto;
import springilmiofotoalbum.repository.FotoRepository;
import springilmiofotoalbum.service.FotoService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/foto")
public class FotoRestController {

    @Autowired
    private FotoRepository fotoRepository;
    @Autowired
    private FotoService fotoService;

    @GetMapping
    public List<Foto> index(@RequestParam Optional<String> keyword) {
        return fotoService.getAll(keyword);
    }

    @GetMapping("/{id}")
    public Foto get(@PathVariable Integer id) {
        try {
            return fotoService.getById(id);
        } catch (FotoNotFoundException | ChangeSetPersister.NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public Foto create(@Valid @RequestBody Foto foto) {
        return fotoService.create(foto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        fotoRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Foto update(@PathVariable Integer id, @Valid @RequestBody Foto foto) {
        foto.setId(id);
        return fotoRepository.save(foto);
    }

}