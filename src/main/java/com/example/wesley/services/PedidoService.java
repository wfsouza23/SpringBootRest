package com.example.wesley.services;

import com.example.wesley.domain.ItemPedido;
import com.example.wesley.domain.PagamentoComBoleto;
import com.example.wesley.domain.Pedido;
import com.example.wesley.enums.EstadoPagamento;
import com.example.wesley.repositories.ItemPedidoRepository;
import com.example.wesley.repositories.PagamentoRepository;
import com.example.wesley.repositories.PedidoRepository;
import com.example.wesley.repositories.ProdutoRepository;
import com.example.wesley.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Pedido find(Integer id){
        Optional<Pedido> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado. Id "
                + id + ", Tipo: "+ Pedido.class.getName()));
    }

    @Transactional
    public Pedido insert(Pedido obj){
        obj.setId(null);
        obj.setInstance(new Date());
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if (obj.getPagamento() instanceof PagamentoComBoleto){
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, obj.getInstance());
        }

        obj = repo.save(obj);
        pagamentoRepository.save(obj.getPagamento());

        for(ItemPedido it : obj.getItens()){
            it.setDesconto(0.0);
            it.setPreco(produtoService.find(it.getProduto().getId()).getPreco());
            it.setPedido(obj);
        }

        itemPedidoRepository.saveAll(obj.getItens());
        return obj;
    }
}
