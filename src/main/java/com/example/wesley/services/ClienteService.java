package com.example.wesley.services;

import com.example.wesley.domain.Cidade;
import com.example.wesley.domain.Cliente;
import com.example.wesley.domain.Endereco;
import com.example.wesley.dto.ClienteDTO;
import com.example.wesley.dto.ClienteNewDTO;
import com.example.wesley.enums.TipoCliente;
import com.example.wesley.repositories.ClienteRepository;
import com.example.wesley.services.exceptions.DataIntegrityException;
import com.example.wesley.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private BCryptPasswordEncoder pe;

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

    @Transactional
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
            throw new DataIntegrityException("Nao é possivel excluir porque há pedidos relacionados");
        }

    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Cliente fromDto(ClienteDTO objDto){
        return new Cliente(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null, null);
    }

    public Cliente fromDto(ClienteNewDTO objDto){
        Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
        Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
        Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
        cli.getEnderecos().add(end);
        cli.getTelefones().add(objDto.getTelefone1());
        if (objDto.getTelefone2()!=null) {
            cli.getTelefones().add(objDto.getTelefone2());
        }
        if (objDto.getTelefone3()!=null) {
            cli.getTelefones().add(objDto.getTelefone3());
        }
        return cli;
    }
}
