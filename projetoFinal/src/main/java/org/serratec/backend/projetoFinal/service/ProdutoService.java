package org.serratec.backend.projetoFinal.service;

import java.util.List;
import java.util.Optional;

import org.serratec.backend.projetoFinal.exception.ParametroObrigatorioException;
import org.serratec.backend.projetoFinal.exception.ProdutoNotFoundException;
import org.serratec.backend.projetoFinal.model.Produto;
import org.serratec.backend.projetoFinal.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	public Produto inserir(Produto produto) {
		Optional<Produto> produtoNoBanco = produtoRepository.findByNome(produto.getNome());
		if(produtoNoBanco.isPresent()) 
			produtoNoBanco.get().setQuantidadeEstoque(produtoNoBanco.get().getQuantidadeEstoque() 
			+ produto.getQuantidadeEstoque() );
		return produtoRepository.save(produto);
	}
	
	public List<Produto> listar() {
		return produtoRepository.findAll();
	}
	
	public Produto listarPorCodigo(Integer codigo) throws ProdutoNotFoundException {
		Optional<Produto> opProduto = produtoRepository.findById(codigo);

		if(opProduto.isPresent()) {
			return opProduto.get();
		}
		
		throw new ProdutoNotFoundException("Cliente com id " + codigo + " não encontrada");
	}
	
	public Produto substituir(Integer codigo, Produto produto) throws ParametroObrigatorioException, ProdutoNotFoundException {
		if(produto == null) throw new ParametroObrigatorioException("Campo 'Produto' é obrigatório");
		
		Produto produtoNoBanco = listarPorCodigo(codigo);
		
		if(produto.getFuncionario() != null) {
			produtoNoBanco.setFuncionario(produto.getFuncionario());
		}
		
		if(produto.getCategoria() != null) {
			produtoNoBanco.setCategoria(produto.getCategoria());
		}
		
		if(produto.getCodigoProduto() != null) {
			produtoNoBanco.setCodigoProduto(produto.getCodigoProduto());
		}
		
		if(produto.getDataFabricacao() != null) {
			produtoNoBanco.setDataFabricacao(produto.getDataFabricacao());
		}
		
		if(produto.getDescricao() != null) {
			produtoNoBanco.setDescricao(produto.getDescricao());
		}
		
		if(produto.getNome() != null) {
			produtoNoBanco.setNome(produto.getNome());
		}
		
		if(produto.getQuantidadeEstoque() != null) {
			produtoNoBanco.setQuantidadeEstoque(produto.getQuantidadeEstoque());
		}
		
		if(produto.getValorUnitario() != null) {
			produtoNoBanco.setValorUnitario(produto.getValorUnitario());
		}		
		
		return produtoRepository.save(produtoNoBanco);
	}
	
	public void deletar(Integer codigo) throws ProdutoNotFoundException{
		Produto produtoNoBanco = listarPorCodigo(codigo);
		produtoRepository.delete(produtoNoBanco);
	}
}
