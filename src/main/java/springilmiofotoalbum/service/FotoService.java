package springilmiofotoalbum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import springilmiofotoalbum.model.Foto;
import springilmiofotoalbum.repository.FotoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FotoService {

    @Autowired
    FotoRepository fotoRepository;

    public List<Foto> getAll(Optional<String> keywordOpt) {
        if (keywordOpt.isEmpty()) {
            return fotoRepository.findAll();
        } else {
            return fotoRepository.findByTitolo(keywordOpt.get());
        }
    }

    public Foto getById(Integer id) throws ChangeSetPersister.NotFoundException {
        Optional<Foto> fotoOpt = fotoRepository.findById(id);
        if (fotoOpt.isPresent()) {
            return fotoOpt.get();
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }

    public Foto create(Foto foto) throws IllegalArgumentException {
        if (!isUniqueId(foto)) {
            throw new IllegalArgumentException(String.valueOf(foto.getId()));
        }
        Foto fotoToPersist = new Foto();
        fotoToPersist.setId(foto.getId());
        fotoToPersist.setTitolo(foto.getTitolo());
        fotoToPersist.setDescrizione(foto.getDescrizione());
        fotoToPersist.setUrl(foto.getUrl());
        fotoToPersist.setVisibile(foto.getVisibile());
        return fotoRepository.save(fotoToPersist);
    }

    private boolean isUniqueId(Foto formFoto) {
        Optional<Foto> result = fotoRepository.findById(formFoto.getId());
        return result.isEmpty();

    }
}
