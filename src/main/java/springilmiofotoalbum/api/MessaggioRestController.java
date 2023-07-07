package springilmiofotoalbum.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import springilmiofotoalbum.exceptions.FotoNotFoundException;
import springilmiofotoalbum.model.Messaggio;
import springilmiofotoalbum.repository.MessaggioRepository;
import springilmiofotoalbum.service.MessaggioService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/messaggio")
public class MessaggioRestController {

    @Autowired
    private MessaggioRepository messaggioRepository;
    @Autowired
    private MessaggioService messaggioService;

    @GetMapping
    public List<Messaggio> index(@RequestParam Optional<String> keyword) {
        return messaggioService.getAll(keyword);
    }

    @GetMapping("/{id}")
    public Messaggio get(@PathVariable Integer id) {
        try {
            return messaggioService.getById(id);
        } catch (FotoNotFoundException | ChangeSetPersister.NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public Messaggio create(@Valid @RequestBody Messaggio messaggio) {
        return messaggioService.create(messaggio);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        messaggioRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Messaggio update(@PathVariable Integer id, @Valid @RequestBody Messaggio messaggio) {
        messaggio.setId(id);
        return messaggioRepository.save(messaggio);
    }

}
