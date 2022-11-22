package com.example.wesley.services;

import com.example.wesley.domain.Categoria;
import com.example.wesley.domain.Cliente;
import com.example.wesley.dto.CategoriaDTO;
import com.example.wesley.dto.ClienteDTO;
import com.example.wesley.repositories.ClienteRepository;
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
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    public Cliente find(Integer id){
        Optional<Cliente> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado. Id "
                + id + ", Tipo: "+ Cliente.class.getName()));
    }

    public List<Cliente> findAll(){
        return repo.findAll();
    }

    public Cliente insert(Cliente obj){
        obj.setId(null);
        return repo.save(obj);
    }

    public Cliente update(Cliente obj){
        Cliente newObj = find(obj.getId());
        updateDate(newObj, obj);
        return repo.save(obj);
    }

    private void updateDate(Cliente newObj, Cliente obj) {
        newObj.setEmail(obj.getEmail());
        newObj.setNome(obj.getNome());
    }

    public void delete(Integer id){
        find(id);
        try{
            repo.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Nao é possivel excluir porque há entidades relacionadas");
        }

    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Cliente fromDto(ClienteDTO objDto){
        return new Cliente(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null);
    }
}
