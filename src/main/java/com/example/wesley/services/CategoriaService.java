package com.example.wesley.services;

import com.example.wesley.domain.Categoria;
import com.example.wesley.domain.Cliente;
import com.example.wesley.dto.CategoriaDTO;
import com.example.wesley.repositories.CategoriaRepository;
import com.example.wesley.services.exceptions.DataIntegrityException;
import com.example.wesley.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Categoria find(Integer id) {
        Optional<Categoria> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado. Id "
                + id + ", Tipo: " + Categoria.class.getName()));
    }

    public List<Categoria> findAll() {
        return repo.findAll();
    }

    public Categoria insert(Categoria obj) {
        obj.setId(null);
        return repo.save(obj);
    }

    public Categoria update(Categoria obj){
        Categoria newObj = find(obj.getId());
        updateDate(newObj, obj);
        return repo.save(obj);
    }

    private void updateDate(Categoria newObj, Categoria obj) {
        newObj.setNome(obj.getNome());
    }

    public void delete(Integer id) {
        find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Nao é possivel excluir uma categoria que possui produtos");
        }

    }

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Categoria fromDto(CategoriaDTO objDto) {
        return new Categoria(objDto.getId(), objDto.getName());
    }

}
