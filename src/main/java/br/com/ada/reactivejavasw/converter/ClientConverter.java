package br.com.ada.reactivejavasw.converter;

import br.com.ada.reactivejavasw.dto.ClientDTO;
import br.com.ada.reactivejavasw.model.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientConverter {
    public ClientDTO toClientDTO(Client client){
        return new ClientDTO(client.getName(), client.getEmail(), client.getAge());
    }

    public Client toClient(ClientDTO clientDTO){
        return new Client(clientDTO.getName(), clientDTO.getEmail(), clientDTO.getAge());
    }
}
