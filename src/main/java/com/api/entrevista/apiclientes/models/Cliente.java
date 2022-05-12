package com.api.entrevista.apiclientes.models;

/**Classe para objetos do tipo Cliente, onde serao contidos, valores e metodos para o mesmo. Esta mapeado 
 *  em tabelas do Banco de dados (JPA) 
* @author Marcos Paulo Roque
* @version 1.0
* Data de criacao: 28/10/2021
*/


import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length=200)
	private String nome;

	@Column(nullable = false, length=200)
	private String endereco;

	@Column(nullable = false, length=100)
	private String bairro;

	@Column(nullable = false, length=2)
	private int dddTelefone;

	@Column(nullable = false, length=9)
	private int numTelefone;
	
	/**
	 * Construtor vazio para a classe Cliente
	 */
	public Cliente() {
		
	}
	
	/**
	 * Construtor que instacia a classe atraves de seus atributos
	 * @param 'id' do tipo Long identifica o codigo do Cliente
	 * @param 'nome' do tipo String representa o nome do Cliente
	 * @param 'endereco' do tipo String representa o endereco do Cliente
	 * @param 'bairro' do tipo String representa o bairro do Cliente
	 * @param 'dddTelefone' do tipo int representa o codigo de area do telefone do Cliente
	 * @param 'numTelefone' do tipo int representa o numero do telefone do Cliente
	 */
	public Cliente(Long id, String nome, String endereco, String bairro, int dddTelefone, int numTelefone) {
		super();
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
		this.bairro = bairro;
		this.dddTelefone = dddTelefone;
		this.numTelefone = numTelefone;
	}
	
	/**
	 * Retorna o identificador do Cliente
	 * @param 'null'
	 * @return 'Long' representa o numero do 'id'
 	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Metodo aplica um identificador ao Cliente
	 * @param 'Long' representa o identificador a ser aplicado ao Cliente
	 * @return null
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Retorna o nome do Cliente
	 * @param 'null'
	 * @return 'String' representa o 'nome'
 	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * Metodo aplica o nome ao Cliente
	 * @param 'String' representa o nome a ser aplicado ao Cliente
	 * @return null
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * Retorna o endereco do Cliente
	 * @param 'null'
	 * @return 'String' representa o 'endereco'
 	 */
	public String getEndereco() {
		return endereco;
	}

	/**
	 * Metodo aplica o endereco ao Cliente
	 * @param 'String' representa o endereco a ser aplicado ao Cliente
	 * @return null
	 */
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	/**
	 * Retorna o bairro do Cliente
	 * @param 'null'
	 * @return 'String' representa o 'bairro'
 	 */
	public String getBairro() {
		return bairro;
	}

	/**
	 * Metodo aplica o bairro ao Cliente
	 * @param 'String' representa o bairro a ser aplicado ao Cliente
	 * @return null
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	/**
	 * Retorna o DDD do Telefone do Cliente
	 * @param 'null'
	 * @return 'int' representa o ddd do 'telefone'
 	 */
	public int getDddTelefone() {
		return dddTelefone;
	}

	/**
	 * Metodo aplica do DDD do Telefone ao Cliente
	 * @param 'int' representa o Telefone a ser aplicado ao Cliente
	 * @return null
	 */
	public void setDddTelefone(int dddTelefone) {
		this.dddTelefone = dddTelefone;
	}

	/**
	 * Retorna o numero do Telefone do Cliente
	 * @param 'null'
	 * @return 'int' representa o numero do 'telefone'
	 */
	public int getNumTelefone() {
		return numTelefone;
	}

	/**
	 * Metodo aplica o numero do Telefone ao Cliente
	 * @param 'int' representa numero do Telefone a ser aplicado ao Cliente
	 * @return null
	 */
	public void setNumTelefone(int numTelefone) {
		this.numTelefone = numTelefone;
	}

	/**
	 * Retorna um hash para a classe Cliente
	 * @param 'null'
	 * @return 'int' identifica o numero hash do objeto Cliente
	 */
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
	
	/**
	 * Verifica a igualdade de objetos Cliente com o seu identificador 'id' aplicado como referencia
	 * @param 'Object'
	 * @return 'boolean' identifica se Objetos sao distintos entre si ou nao
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(id, other.id);
	}
	
	/**
	 * Cadeia de caracteres que descreve o objeto Cliente
	 * @param 'null'
	 * @return 'String" representa o objeto Cliente
	 */
	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", endereco=" + endereco + ", bairro=" + bairro + ", dddTelefone="
				+ dddTelefone + ", numTelefone=" + numTelefone + "]";
	}

	public boolean numerosDistintos() {

		boolean resposta = true;
		int lengthTelefone = Integer.toString(this.numTelefone).length();
		String textTelefone = Integer.toString(this.numTelefone);

		for(int posInicioValidacao = 0; posInicioValidacao < lengthTelefone; posInicioValidacao++) {
			for(int i = 0; i < lengthTelefone ; i++ ) {
				if ((i != posInicioValidacao) && textTelefone.charAt(i) == textTelefone.charAt(posInicioValidacao)) {
					resposta = false;
					break;
				}
			}

		}

		return resposta;

	}
	
}
