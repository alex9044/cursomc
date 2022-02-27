package com.alexmoscato.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexmoscato.cursomc.domain.ItemPedido;
import com.alexmoscato.cursomc.domain.PagamentoComBoleto;
import com.alexmoscato.cursomc.domain.Pedido;
import com.alexmoscato.cursomc.domain.enums.EstadoPagamento;
import com.alexmoscato.cursomc.repositories.ItemPedidoRepository;
import com.alexmoscato.cursomc.repositories.PagamentoRepository;
import com.alexmoscato.cursomc.repositories.PedidoRepository;
import com.alexmoscato.cursomc.services.exceptions.*;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;

	// Method para buscar um pedido por Id.
	public Pedido findId(Integer id) {
		Optional<Pedido> objeto = repository.findById(id);
		return objeto.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! ID:" + id + ", tipo: " + Pedido.class.getName()));
	}

	// Metodo para inserir um novo pedido
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.findId(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.findId(ip.getProduto().getId()).getPreco());
			ip.setProduto(produtoService.findId(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		System.out.println(obj);
		return obj;
	}
}
