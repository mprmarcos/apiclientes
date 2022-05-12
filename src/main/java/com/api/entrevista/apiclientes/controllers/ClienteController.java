package com.api.entrevista.apiclientes.controllers;

/**Classe para objetos do tipo ClienteController, onde serao contidos, valores e metodos para o mesmo. Responsavel
 * por processar as requisicões da rota '/cliente' da API
* @author Marcos Paulo Roque
* @version 1.0
* Data de criacao: 28/10/2021
*/

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.entrevista.apiclientes.models.Cliente;
import com.api.entrevista.apiclientes.repositories.ClienteRepository;

@RestController
@RequestMapping("/cliente")

public class ClienteController {

	@Autowired(required = true)
	private ClienteRepository clienteRepository;

	/**
	 * Lista todos os Clientes com cadastro ativo
	 * @param 'null'
	 * @return Um List<Cliente> armazenados em banco de dados.
	 */
	@GetMapping
	public List<Cliente> listarTodosClientes() {
		return clienteRepository.findAll();
	}
	/**
	 * Lista um Cliente com base no seu Id
	 * @param 'id' do tipo Long identifica o codigo do Cliente a ser pesquisado
	 * @return Um 'ResponseEntity<Cliente>' possui os dados da requisicao HTTP que inclui os dados do cliente com status de Ok se
	 * existir um correspondente ao Id aplicado. Ou retorna null com status de Nao Encontrado.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> listartClienteById(@PathVariable("id") long id) {
		Optional<Cliente> Cliente = clienteRepository.findById(id);

		if (Cliente.isPresent()) {

			return new ResponseEntity<>(Cliente.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	

	/**
	 * Lista um Cliente com base no seu Nome
	 * @param 'nome' do tipo String identifica o nome do Cliente a ser pesquisado
	 * @return Um 'ResponseEntity<Cliente>' possui os dados da requisicao HTTP que inclui os dados do cliente com status de Ok se
	 * existir um correspondente ao Nome aplicado. Ou retorna null com status de Nao Encontrado.
	 */
	@GetMapping("/{nome}")
	public ResponseEntity<Cliente> listarClienteByName(@PathVariable("nome") String nome) {
		Optional<Cliente> Cliente = clienteRepository.findClienteByName(nome);

		if (Cliente.isPresent()) {
			return new ResponseEntity<>(Cliente.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * O metodo verifica se o telefone pesquisado existe atraves da composicao de DDD+Numero. Dessa forma dois telefone com mesmo número,
	 * mas com DDD distintos nao sao considerados repetidos
	 * @param 'dddtelefone' do tipo int identifica o DDD do telefone a ser pesquisado
	 * @param 'telefone' do tipo int identifica o numero do telefon a ser pesquisado
	 * @return Um 'ResponseEntity<Telefone>' possui os dados da requisicao HTTP que inclui os dados do Telefone com status de Ok se
	 * existir um correspondente aos filtros de pesquisa aplicados. Ou retorna null com status de Nao Encontrado.
	 */

	public ResponseEntity<Cliente> listarTelefoneCliente(@PathVariable("dddtelefone") int dddtelefone, @PathVariable("numtelefone") int numtelefone, @PathVariable("id") Long id) {
		Optional<Cliente> Cliente = clienteRepository.findClienteByTelefone(dddtelefone,numtelefone,id);

		if (Cliente.isPresent()) {
			return new ResponseEntity<>(Cliente.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<Cliente> listarTelefoneCliente(@PathVariable("dddtelefone") int dddtelefone, @PathVariable("numtelefone") int numtelefone) {
		Optional<Cliente> Cliente = clienteRepository.findClienteByTelefone(dddtelefone,numtelefone);

		if (Cliente.isPresent()) {
			return new ResponseEntity<>(Cliente.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Metodo destinado a validar os dados do Cliente a ser cadastrado pelas seguintes regras:
	 * -Nome nao pode esta vinculado ao de outro cliente;
	 * -Nome deve possuir mais de 10 caracteres;
	 * -Nome nao pode ultrapassar 200 caracteres;
	 * -Endereco nao pode ultrapassar 200 caracteres;
	 * -Bairro nao pode ultrapassar 100 caracteres;
	 * -Telefone: DDD ou o número nao podem ser nulos;
	 * -Telefone nao pode possui numero repetidos. Regra nao se aplica ao codigo de DDD;
	 * -Telefone nao pode estar vinculado a outro cliente
	 * @param 'cliente' representa o cliente a ser validado
	 * @return 'HashMap<String, String>' contem a lista de alertas em caso de haver inconsistencias no cadastro, ou retorna 'null' se o 
	 * cliente possuir dados aptos ao cadastro
	 */
	private HashMap<String, String> clienteValido( Cliente cliente) {
		
		HashMap<String, String> alertasInserirCliente = new HashMap<>();

		if ((listarClienteByName(cliente.getNome()).getStatusCode() == HttpStatus.OK)) {
			alertasInserirCliente.put("AlertaNome1", "Ja existe um cliente cadastrado com o Nome informado");
		}
		if (cliente.getNome().length() <= 10 && cliente.getNome().length() > 200) {
			alertasInserirCliente.put("AlertaNome2", "O Nome deve possuir mais de 10 e ter no maximo 200 caracteres");
		}
		if (cliente.getEndereco().length() > 200) {
			alertasInserirCliente.put("AlertaEndereco", "O Endereco esta limitado a 200 caracteres");
		}
		if (cliente.getBairro().length() > 100) {
			alertasInserirCliente.put("AlertaBairro", "O Bairro esta limitado a 100 caracteres");
		}		
		if (Integer.toString(cliente.getNumTelefone()).length() != 9) {
			alertasInserirCliente.put("AlertaTelefone1", "O numero de telefone deve possuir 9 caracteres numericos");
		}
		if (Integer.toString(cliente.getDddTelefone()).length() != 2) {
			alertasInserirCliente.put("AlertaTelefone2", "O DDD deve possuir 2 caracteres numericos");
		}
		if (!cliente.numerosDistintos()) {
			alertasInserirCliente.put("AlertaTelefone3", "O Telefone nao pode possui numeros repetidos");
		}
		if (cliente.getNumTelefone() == 0) {
			alertasInserirCliente.put("AlertaTelefone4", "O DDD ou o numero de telefone nao podem ser nulos.");
		}
		if(cliente.getId() == null){
			if ((listarTelefoneCliente(cliente.getDddTelefone(),cliente.getNumTelefone()).getStatusCode() == HttpStatus.OK)) {
				alertasInserirCliente.put("AlertaTelefone5", "O telefone informado ja foi vinculado a outro cliente");
			}
		} else {
			if ((listarTelefoneCliente(cliente.getDddTelefone(),cliente.getNumTelefone(), cliente.getId()).getStatusCode() == HttpStatus.OK)) {
				alertasInserirCliente.put("AlertaTelefone6", "O telefone informado ja foi vinculado a outro cliente");
			}
		}

		
		return alertasInserirCliente;
	}
	
	/**
	 * O metodo Cadastra um cliente mediante validacao previa das informacoes com compoem o cliente.
	 * @param 'cliente' do tipo Cliente contem os dados a serem cadastrados
	 * @see 'clienteValido(Cliente)'
	 * @return Um 'ResponseEntity<Object>' possui os dados da requisicao HTTP que inclui os dados do Cliente cadastrado com status de Ok,
	 * Ou retorna 'HashMap<String, String>' com as inconsistencias listadas e o codido Http de conflito.
	 */
	@PostMapping
	public ResponseEntity<Object> cadastrarCliente(@RequestBody Cliente cliente) {

		HashMap<String, String> alertasInserirCliente = clienteValido(cliente);

		if (alertasInserirCliente.isEmpty()) {
			return ResponseEntity.status(HttpStatus.CREATED).body(clienteRepository.save(cliente));

		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(alertasInserirCliente);
		}

	}

	/**
	 * O metodo remove um Cliente baseado no seu 'id'
	 * @param 'id' do tipo Long identifica o cliente a ser removido
	 * @return Um 'ResponseEntity<?>' possui os dados da requisicao HTTP que inclui Ok se foi removido com sucesso, ou
	 * retorna o codigo HTTP 'Nao encontrado' se nao foi possível localiza-lo para remocao
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerCliente(@PathVariable long id) {
		return clienteRepository.findById(id).map(record -> {
			clienteRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}

	/**
	 * O metodo atualiza um Cliente baseado no seu 'id'
	 * @param 'id' do tipo Long identifica o cliente a ser atualizado
	 * @see 'clienteValido(Cliente)'
	 * @return Um 'ResponseEntity<?>' possui os dados da requisicao HTTP do cliente atualizado e o status HTTP Ok, 
	 * retorna o codigo HTTP 'Nao encontrado' se nao foi possível localiza-lo para a para tualizacao, ou retorna 
	 * retorna 'HashMap<String, String>' com as inconsistencias listadas e o codido Http de 'Nao modificado'.
	 */
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> atualizarCliente(@PathVariable("id") Long id, @RequestBody Cliente cliente) {

		HashMap<String, String> alertasInserirCliente = clienteValido(cliente);

		if (alertasInserirCliente.isEmpty()) {
			
			return clienteRepository.findClienteById(id).map(record -> {
				record.setNome(cliente.getNome());
				record.setEndereco(cliente.getEndereco());
				record.setBairro(cliente.getBairro());
				record.setDddTelefone(cliente.getDddTelefone());
				record.setNumTelefone(cliente.getNumTelefone());
	
				Cliente updated = clienteRepository.save(record);
				return ResponseEntity.ok().body(updated);
			}).orElse(
				ResponseEntity.notFound().build()			
			);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(alertasInserirCliente);
		}
		
	}

}
