package com.lojavirtual.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lojavirtual.dto.ClienteCadastroDTO;
import com.lojavirtual.dto.ClienteDTO;
import com.lojavirtual.entity.Cidade;
import com.lojavirtual.entity.Cliente;
import com.lojavirtual.entity.Endereco;
import com.lojavirtual.enums.Perfil;
import com.lojavirtual.enums.TipoCliente;
import com.lojavirtual.repository.ClienteRepository;
import com.lojavirtual.repository.EnderecoRepository;
import com.lojavirtual.security.UserSS;
import com.lojavirtual.services.exceptions.AuthorizationException;
import com.lojavirtual.services.exceptions.DataIntegrityException;
import com.lojavirtual.services.exceptions.ObjectNotFoundException;
import com.lojavirtual.services.utils.UploadService;

@Service
public class ClienteService {

	private ClienteRepository repository;
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private UploadService uploadService;
	
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public ClienteService(ClienteRepository repository, EnderecoRepository enderecoRepository) {
		this.repository = repository;
		this.enderecoRepository = enderecoRepository;
	}
	
	public List<Cliente> findAll() {
		return repository.findAll();
	}
	
	public Cliente findById(long id) {
		UserSS user = UserService.authenticaded();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !user.getId().equals(id)) 
			throw new AuthorizationException("Acesso negado!");
		
		Optional<Cliente> cliente = repository.findById(id);
		return cliente.orElseThrow(
				() -> new ObjectNotFoundException("Cliente não encontrado! Id informado: " + id)
			);
	}
	
	public Cliente findClienteByEmail(String email) {
		UserSS user = UserService.authenticaded();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername()))
			throw new AuthorizationException("Acesso negado!");
		
		Cliente cliente = repository.findByEmail(email);
		if (cliente == null)
			throw new ObjectNotFoundException("Cliente não encontrado! Email: " + email);
		
		return cliente;
	}
	
	@Transactional
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		cliente = repository.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		return cliente;
	}
	
	public Cliente update(Cliente cliente) {
		Cliente clienteAtualizado = findById(cliente.getId());
		
		clienteAtualizado.setNome(cliente.getNome());
		clienteAtualizado.setEmail(cliente.getEmail());
		
		return repository.save(clienteAtualizado);
	}
	
	public void delete(long id) {
		findById(id);
		
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir este cliente, pois há pedidos relacionadas a ele");
		}
	}
	
	public Page<Cliente> findAllPageable(int page, int linesPerPage, String direction, String orderBy) {
		PageRequest clientesPaginados = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(clientesPaginados);
	}

	public Cliente fromDTO(@Valid ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(@Valid ClienteCadastroDTO cadastro) {
		Cliente cliente = new Cliente(null, cadastro.getNome(), cadastro.getEmail(), cadastro.getCpfOuCnpj(), encoder.encode(cadastro.getSenha()), TipoCliente.toEnum(cadastro.getTipo()));
		Cidade cidade = new Cidade(cadastro.getCidadeId(), null, null);
		Endereco endereco = new Endereco(null, cadastro.getLogradouro(), cadastro.getNumero(), cadastro.getComplemento(), cadastro.getBairro(), cadastro.getCep(), cliente, cidade);
		
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(cadastro.getTelefone1());
		
		if (cadastro.getTelefone2() != null)
			cliente.getTelefones().add(cadastro.getTelefone2());
		
		if (cadastro.getTelefone3() != null)
			cliente.getTelefones().add(cadastro.getTelefone3());
		
		return cliente;
	}

	public URI uploadProfilePicture(MultipartFile file) {
		UserSS user = UserService.authenticaded();
		
		if (user == null)
			throw new AuthorizationException("Acesso negado!");
		
		Cliente cliente = findById(user.getId());
		cliente.setImgPath(uploadService.uploadPicture(file, cliente.getId(), prefix, "/clientes_img"));
		repository.save(cliente);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{imgPath}").buildAndExpand(cliente.getImgPath())
					.toUri();
		
		return uri;
	}

	public void showProfilePicture(String clientPicture, HttpServletResponse response) {
		uploadService.showData("/clientes_img/" + clientPicture, response);
	}
	
}
