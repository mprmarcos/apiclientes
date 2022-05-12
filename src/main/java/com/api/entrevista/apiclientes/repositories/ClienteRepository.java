package com.api.entrevista.apiclientes.repositories;

/**Classe para objetos do tipo ClienteRepository, onde serao contidos, valores e metodos para o mesmo. Realiza a trocar de informacoes com a Base de Dados
* @author Marcos Paulo Roque
* @version 1.0
* Data de criacao: 28/10/2021
*/

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.entrevista.apiclientes.models.Cliente;

/**
 * Responsavel pelas transacoes do objeto cliente com a base de dados
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	@Query("select c from Cliente c where c.nome like ?1")
    Optional<Cliente> findClienteByName(String nome);

    @Query("select c from Cliente c where c.id like ?1")
    Optional<Cliente> findClienteById(Long id);

    @Query("select c from Cliente c where c.dddTelefone = ?1 and c.numTelefone = ?2 and c.id <> ?3")
    Optional<Cliente> findClienteByTelefone(int dddTelefone, int numTelefone, Long id);

    @Query("select c from Cliente c where c.dddTelefone = ?1 and c.numTelefone = ?2")
    Optional<Cliente> findClienteByTelefone(int dddTelefone, int numTelefone);
}
