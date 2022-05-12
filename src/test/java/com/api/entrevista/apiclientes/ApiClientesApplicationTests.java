package com.api.entrevista.apiclientes;

import com.api.entrevista.apiclientes.controllers.ClienteController;
import com.api.entrevista.apiclientes.models.Cliente;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ApiClientesApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClienteController clienteController;

    @Test
    void cadastroCorreto() throws Exception {

        Cliente clientePost = new Cliente(Long.parseLong("1"), "Cliente Teste 1",
                        "Endereco do cliente Teste", "Bairro do cliente Teste",
                                31, 123456789);

        Cliente clienteReturn = (Cliente) clienteController.cadastrarCliente(clientePost).getBody();;

        Assertions.assertEquals(clienteReturn.getId(), 1);
        Assertions.assertEquals(clienteReturn.getNome(), "Cliente Teste 1");
        Assertions.assertEquals(clienteReturn.getEndereco(), "Endereco do cliente Teste");
        Assertions.assertEquals(clienteReturn.getBairro(), "Bairro do cliente Teste");
        Assertions.assertEquals(clienteReturn.getDddTelefone(), 31);
        Assertions.assertEquals(clienteReturn.getNumTelefone(), 123456789);
    }

    @Test
    void cadastroTelefoneNumerosRepetidos() throws Exception {
        Cliente clientePost = new Cliente(null, "Cliente Teste",
                "Endereco do cliente Teste", "Bairro do cliente Teste",
                31, 123456779);
        ResponseEntity<Object> erro =  clienteController.cadastrarCliente(clientePost);
        Assertions.assertEquals(erro.getStatusCodeValue(),HttpStatus.CONFLICT.value());
    }

    @Test
    void cadastroTelefone9Caracteres() throws Exception {
        Cliente clientePost = new Cliente(null, "Cliente Teste",
                "Endereco do cliente Teste", "Bairro do cliente Teste",
                31, 1234567);
        ResponseEntity<Object> erro =  clienteController.cadastrarCliente(clientePost);
        Assertions.assertEquals(erro.getStatusCodeValue(),HttpStatus.CONFLICT.value());
    }

    @Test
    void cadastroTelefoneRepedtido() throws Exception {
        Cliente clientePost1 = new Cliente(null, "Cliente Teste",
                "Endereco do cliente Teste", "Bairro do cliente Teste",
                31, 123456789);
        Cliente clientePost2 = new Cliente(null, "Cliente Teste 2",
                "Endereco do cliente Teste", "Bairro do cliente Teste",
                31, 123456789);
        clienteController.cadastrarCliente(clientePost1);
        ResponseEntity<Object> erro =  clienteController.cadastrarCliente(clientePost2);
        Assertions.assertEquals(erro.getStatusCodeValue(),HttpStatus.CONFLICT.value());
    }

    @Test
    void cadastroNomeRepedtido() throws Exception {
        Cliente clientePost1 = new Cliente(null, "Cliente Teste",
                "Endereco do cliente Teste", "Bairro do cliente Teste",
                31, 123456789);
        Cliente clientePost2 = new Cliente(null, "Cliente Teste",
                "Endereco do cliente Teste", "Bairro do cliente Teste",
                31, 123456798);
        clienteController.cadastrarCliente(clientePost1);
        ResponseEntity<Object> erro =  clienteController.cadastrarCliente(clientePost2);
        Assertions.assertEquals(erro.getStatusCodeValue(),HttpStatus.CONFLICT.value());
    }

    @Test
    void removerClienteInexistente() throws Exception {
        ResponseEntity<?> erro = clienteController.removerCliente(Long.parseLong("1"));
        Assertions.assertEquals(erro.getStatusCodeValue(),HttpStatus.NOT_FOUND.value());
    }

    @Test
    void atualizarClienteInexistente() throws Exception {
        Cliente clientePut = new Cliente(null, "Cliente Teste",
                "Endereco do cliente Teste", "Bairro do cliente Teste",
                31, 123456789);
        ResponseEntity<?> erro = clienteController.atualizarCliente(Long.parseLong("10"),clientePut);
        Assertions.assertEquals(erro.getStatusCodeValue(),HttpStatus.NOT_FOUND.value());
    }
}
