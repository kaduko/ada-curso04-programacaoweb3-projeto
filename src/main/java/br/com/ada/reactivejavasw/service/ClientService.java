package br.com.ada.reactivejavasw.service;

import br.com.ada.reactivejavasw.converter.ClientConverter;
import br.com.ada.reactivejavasw.dto.ClientDTO;
import br.com.ada.reactivejavasw.dto.ResponseDTO;
import br.com.ada.reactivejavasw.model.Client;
import br.com.ada.reactivejavasw.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ClientService {

    @Autowired
    private ClientConverter clientConverter;

    @Autowired
    private ClientRepository clientRepository;
    public Mono<ResponseDTO> create(ClientDTO clientDTO){

        Client client = this.clientConverter.toClient(clientDTO);

        Mono<Client> productMono = this.clientRepository.save(client);

        return productMono
                .map((clientDocument) -> new ResponseDTO("Cliente cadastrado com suecesso.",
                        this.clientConverter.toClientDTO(clientDocument),
                        LocalDateTime.now()))
                .onErrorReturn(new ResponseDTO("Erro ao cadastrar cliente.",
                        new ClientDTO(),
                        LocalDateTime.now()));

    }

    public Mono<ResponseDTO<ClientDTO>> findByEmail(String code){
        Mono<Client> productMono = this.clientRepository.findByEmail(code);

        return productMono
                .map(client -> new ResponseDTO("Busca por email retornada com sucesso.",
                        this.clientConverter.toClientDTO(client),
                        LocalDateTime.now()
                ));

    }

    public Flux<ResponseDTO<ClientDTO>> getAll(){
        Flux<Client> productFlux = this.clientRepository.findAll();
        return productFlux
                .map(client -> new ResponseDTO("Listagem de produtos retornada com sucesso.",
                        this.clientConverter.toClientDTO(client),
                        LocalDateTime.now()
                ));
    }

    public Mono<ResponseDTO> deleteByEmail(String code) {
        return this.clientRepository
                .deleteByEmail(code)
                .map((product) -> new ResponseDTO<>("Cliente removido com sucesso!",
                        null,
                        LocalDateTime.now()));
    }

    public Mono<ResponseDTO> update(ClientDTO clientDTO) {
        Mono<Client> productMono = this.clientRepository.findByEmail(clientDTO.getEmail());

        return productMono.flatMap((existingClient) -> {
            existingClient.setName(clientDTO.getName());
            existingClient.setEmail(clientDTO.getEmail());
            existingClient.setAge(clientDTO.getAge());
            return this.clientRepository.save(existingClient);
        }).map(client -> new ResponseDTO<>("Cliente alterado com sucesso!",
                this.clientConverter.toClientDTO(client),
                LocalDateTime.now()));
    }
}
