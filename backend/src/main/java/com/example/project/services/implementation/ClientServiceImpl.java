package com.example.project.services.implementation;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.project.dtos.responses.ClientResponse;
import com.example.project.dtos.responses.ClientsPaymentResponse;
import com.example.project.exception.CustomException;
import com.example.project.exception.EntityDoesNotExist;
import com.example.project.models.Client;
import com.example.project.models.Payment;
import com.example.project.repositories.AuthuserRepository;
import com.example.project.repositories.ClientRepository;
import com.example.project.repositories.PaymentRepository;
import com.example.project.services.ClientService;

@Service
public class ClientServiceImpl implements ClientService {
	
	private final ClientRepository clientRepository;
	private final AuthuserRepository authRepository;
	private final PaymentRepository paymentRepository;

	
	public ClientServiceImpl(ClientRepository clientRepository, AuthuserRepository authuserRepository, PaymentRepository paymentRepository) {
		this.clientRepository = clientRepository;
		this.authRepository = authuserRepository;
		this.paymentRepository = paymentRepository;
	}
	
	
	@Override
	public List<ClientResponse> findAll() {
		
		return mapToClientResponseList(clientRepository.findAll());
	}

	@Override
	public ClientResponse create(Client client) {
		try {
			return mapToClientResponse(clientRepository.save(client));
        } catch (DataIntegrityViolationException e) {
            if (e.getMostSpecificCause().getClass().getName().equals("org.postgresql.util.PSQLException") && ((SQLException) e.getMostSpecificCause()).getSQLState().equals("23505"))
            	throw new  CustomException(e.getRootCause().toString(), HttpStatus.UNPROCESSABLE_ENTITY);
            throw e;
         
        }
	
	}

	@Override
	public void delete(Integer id) {
		
		Client client = clientRepository.findById(id)
					.orElseThrow(()-> new EntityDoesNotExist(id));
			
		clientRepository.delete(client);	
	}

	@Override
	public ClientResponse update(Client client) {
		
		clientRepository.findById(client.getClientId())
				.orElseThrow(()-> new EntityDoesNotExist(client.getClientId()));
		
		return mapToClientResponse(clientRepository.save(client));
	}

	@Override
	public ClientResponse findById(Integer id) {
		
		Client client =  clientRepository.findById(id)
				.orElseThrow(()-> new EntityDoesNotExist(id));
		return mapToClientResponse(client);
	}

	@Override
	public List<ClientResponse> findByNameContainingIgnoreCase(String prefix) {
		return mapToClientResponseList(clientRepository.findByNameContainingIgnoreCase(prefix));

	}

	@Override
	public List<ClientResponse> findByCompanyId(Integer companyId) {
		return mapToClientResponseList(clientRepository.findByCompanyCompanyId(companyId));
	}
	
    private List<ClientResponse> mapToClientResponseList(List<Client> clients) {
        return clients.stream().map(this::mapToClientResponse).collect(Collectors.toList());
    }

    private ClientResponse mapToClientResponse(Client client) {
        return ClientResponse.builder()
            .clientId(client.getClientId())
            .name(client.getName())
            .accountNumber(client.getAccountNumber())
            .contact(client.getContact())
            .address(client.getAddress())
            .email(client.getEmail())
            .clientRegNumber(client.getClientRegNumber())
            .company(client.getCompany())
            .build();
    }

	@Override
	public List<ClientResponse> findByAuthuserId(Integer userId) {
		return mapToClientResponseList(clientRepository.findAllByCompanyAuthuserId(userId));
	}
	
	@Override
	public List<ClientsPaymentResponse> getPaymentPercentagePerYear(Integer companyid, String year) {
		List<ClientResponse> clients = findByCompanyId(companyid);
		List<ClientsPaymentResponse> clientsPaymment = new ArrayList<>();
		for(int i =0;i<clients.size();i++) {
			List<Payment> payments = this.paymentRepository.findAllByReceiptClientClientId(clients.get(i).getClientId());
			payments = payments.stream().filter(payment -> payment.getDateOfIssue().getYear() == Integer.parseInt(year)).collect(Collectors.toList());
			double sum = payments.stream().mapToDouble(amount -> amount.getAmount()).reduce(0, (a,b) -> a+b);
			double percentage = sum/getTotalAmount(year, companyid) *100;

			clientsPaymment.add(new ClientsPaymentResponse(clients.get(i).getClientId(), clients.get(i).getName(), Math.round(sum*100)/100, Math.round(percentage * 100.0) / 100.0));
		}
		 return clientsPaymment;
		
		
	}
	
	private double getTotalAmount(String year, Integer companyId) {
		List<Payment> payments = paymentRepository.findAllByReceiptClientCompanyCompanyId(companyId);
		return payments.stream().filter(payment -> payment.getDateOfIssue().getYear() ==Integer.parseInt(year)).mapToDouble(amount -> amount.getAmount()).reduce(0, (a,b) -> a+b);	
	}
}
