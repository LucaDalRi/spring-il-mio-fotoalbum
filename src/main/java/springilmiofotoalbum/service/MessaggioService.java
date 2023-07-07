package springilmiofotoalbum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import springilmiofotoalbum.model.Messaggio;
import springilmiofotoalbum.repository.MessaggioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessaggioService {
    @Autowired
    MessaggioRepository messaggioRepository;

    public List<Messaggio> getAll(Optional<String> keywordOpt) {
        if (keywordOpt.isEmpty()) {
            return messaggioRepository.findAll();
        } else {
            return messaggioRepository.findByEmail(keywordOpt.get());
        }
    }

    public Messaggio getById(Integer id) throws ChangeSetPersister.NotFoundException {
        Optional<Messaggio> messaggioOpt = messaggioRepository.findById(id);
        if (messaggioOpt.isPresent()) {
            return messaggioOpt.get();
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }

    public Messaggio create(Messaggio messaggio) throws IllegalArgumentException {
        if (!isUniqueId(messaggio)) {
            throw new IllegalArgumentException(String.valueOf(messaggio.getId()));
        }
        Messaggio messaggioToPersist = new Messaggio();
        messaggioToPersist.setId(messaggio.getId());
        messaggioToPersist.setEmail(messaggio.getEmail());
        messaggioToPersist.setTesto(messaggio.getTesto());
        return messaggioRepository.save(messaggioToPersist);
    }

    private boolean isUniqueId(Messaggio formMessaggio) {
        Optional<Messaggio> result = messaggioRepository.findById(formMessaggio.getId());
        return result.isEmpty();

    }
}
