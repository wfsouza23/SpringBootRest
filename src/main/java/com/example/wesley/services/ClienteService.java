package com.example.wesley.services;

import com.example.wesley.domain.Cliente;
import com.example.wesley.repositories.ClienteRepository;
import com.example.wesley.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
