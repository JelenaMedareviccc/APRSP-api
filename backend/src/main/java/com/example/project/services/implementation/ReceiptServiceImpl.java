package com.example.project.services.implementation;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.project.dtos.responses.ReceiptResponse;
import com.example.project.exception.CustomException;
import com.example.project.exception.EntityDoesNotExist;
import com.example.project.models.Item;
import com.example.project.models.Payment;
import com.example.project.models.Receipt;
import com.example.project.repositories.ReceiptRepository;
import com.example.project.services.ReceiptService;


@Service
public class ReceiptServiceImpl implements ReceiptService {
	
	private final ReceiptRepository receiptRepository;
	
	
	public ReceiptServiceImpl(ReceiptRepository receiptRepository) {
		this.receiptRepository = receiptRepository;
		
	}
	
	@Override
	public List<ReceiptResponse> findAll() {
		return  mapToReceiptResponseList(getReceiptSettingAmoutAndDebt(receiptRepository.findAll()));

	}

	@Override
	public ReceiptResponse create(Receipt receipt) {
		try {
			receipt.setYear(String.valueOf(receipt.getDateOfIssue().getYear()));
			return mapToReceiptResponse(receiptRepository.save(receipt));
       } catch (DataIntegrityViolationException e) {
           if (e.getMostSpecificCause().getClass().getName().equals("org.postgresql.util.PSQLException") && ((SQLException) e.getMostSpecificCause()).getSQLState().equals("23505"))
           	throw new  CustomException(e.getRootCause().toString(), HttpStatus.UNPROCESSABLE_ENTITY);
           throw e;
        
       }
		
	
	}

	@Override
	public void delete(Integer id) {
		
		Receipt receipt = receiptRepository.findById(id)
				.orElseThrow(()-> new EntityDoesNotExist(id));
		
		receiptRepository.delete(receipt);
	}

	@Override
	public ReceiptResponse update(Receipt receipt) {
		
	receiptRepository.findById(receipt.getReceiptId())
				.orElseThrow(()-> new EntityDoesNotExist(receipt.getReceiptId()));
		return mapToReceiptResponse(receiptRepository.save(receipt));
	}

	@Override
	public ReceiptResponse findById(Integer id) {
		
		Receipt receipt =  receiptRepository.findById(id)
				.orElseThrow(()-> new EntityDoesNotExist(id));
	receipt = setTotalAmoutAndDebt(receipt);
		return mapToReceiptResponse(receipt);
	}

	@Override
	public List<ReceiptResponse> findByClientId(Integer clientId) {
		
		List<Receipt> receipts = receiptRepository.findByClientClientId(clientId);
		receipts =getReceiptSettingAmoutAndDebt(receipts);
		return mapToReceiptResponseList(receipts);
	}

	
	public double totalAmount(int id) {
		Receipt receipt = receiptRepository.findById(id).orElseThrow(()-> new EntityDoesNotExist(id));	

		List<Item> items= receipt.getItems();
		double total = 0;
		for(int i=0; i<items.size(); i++) {
			total += items.get(i).getTotalPrice();
			
		}
		
		return total;
	}
	

	public Receipt setTotalAmoutAndDebt(Receipt receipt) {
		
			double amount = this.totalAmount(receipt.getReceiptId());
			double totalAmount;
			if(receipt.getPercentageInterest()!=0) {
				totalAmount = this.calculateTotalAmountWithInterest(amount, receipt.getPercentageInterest());
			} else {
				totalAmount = amount;
			}
			receipt.setTotalAmount(totalAmount);
			if(receipt.getPayments()!=null) {
				double totalPayment = paymentsTotal(receipt);
				receipt.setDebt(totalAmount - totalPayment);
			} else {
				receipt.setDebt(totalAmount);

			}
			
		return receipt;
		
	}
	
	public double calculateTotalAmountWithInterest(double totalAmount, int percentageInterest) {

		return (totalAmount + totalAmount*percentageInterest/100);
		 
	}
	
	public List<Receipt> getReceiptSettingAmoutAndDebt(List<Receipt> receipts){
		for(int i=0;i<receipts.size();i++) {
			setTotalAmoutAndDebt(receipts.get(i));
		}
		
		return receipts;
	}
	
	
	
	public double paymentsTotal(Receipt receipt) {
		List<Payment> payments = receipt.getPayments();
		double totalPayment = 0;
		for(int j=0;j<payments.size();j++) {
			totalPayment+=payments.get(j).getAmount();
		}
		return totalPayment;
		
	}
	
	@Override
	public List<ReceiptResponse> filterLastYear(Integer id) {
	
		
		List<Receipt> receipts = receiptRepository.findByClientClientId(id);
		List<Receipt> filteredReceipts = new ArrayList<Receipt>();
		for (int i = 0; i <= receipts.size() - 1; i++) {
			if (receipts.get(i).getDateOfIssue().getYear() == 2019) {
				filteredReceipts.add(receipts.get(i));
			}
		}
		return mapToReceiptResponseList(getReceiptSettingAmoutAndDebt(filteredReceipts));
	}
	
	@Override
	public List<ReceiptResponse> filterLast365Days(Integer id) {
		List<Receipt> receipts = receiptRepository.findByClientClientId(id);
		List<Receipt> filteredReceipts = new ArrayList<Receipt>();
		final LocalDate newDate = LocalDate.now(ZoneId.of("UTC")).minus(Period.ofDays(365));
		for (int i = 0; i <= receipts.size() - 1; i++) {
			if (receipts.get(i).getDateOfIssue().isAfter(newDate)) {
				filteredReceipts.add(receipts.get(i));
			}
		}
		filteredReceipts = getReceiptSettingAmoutAndDebt(filteredReceipts);
		return mapToReceiptResponseList(filteredReceipts);
	}

	@Override
	public List<ReceiptResponse> filterBetweenTwoDates(Integer id, String startDate, String endDate) {
		List<Receipt> receipts = receiptRepository.findByClientClientId(id);
		List<Receipt> filteredReceipts = new ArrayList<Receipt>();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate startingDate = LocalDate.parse(startDate, df);
		LocalDate endingDate = LocalDate.parse(endDate, df);
		for (int i = 0; i <= receipts.size() -1; i++) {
			if (receipts.get(i).getDateOfIssue().isAfter(startingDate) && receipts.get(i).getDateOfIssue().isBefore(endingDate)) {
				filteredReceipts.add(receipts.get(i));
			}
		}
		filteredReceipts = getReceiptSettingAmoutAndDebt(filteredReceipts);
		return mapToReceiptResponseList(filteredReceipts);
	}
	
	@Override
	public List<ReceiptResponse> findByAuthuserId(Integer userId) {
		return mapToReceiptResponseList(this.getReceiptSettingAmoutAndDebt(receiptRepository.findAllByClientCompanyAuthuserId(userId)));
	}
	
    private List<ReceiptResponse> mapToReceiptResponseList(List<Receipt> receipts) {
        return receipts.stream().map(this::mapToReceiptResponse).collect(Collectors.toList());
    }

    private ReceiptResponse mapToReceiptResponse(Receipt receipt) {
        return ReceiptResponse.builder()
            .receiptId(receipt.getReceiptId())
            .dateOfIssue(receipt.getDateOfIssue())
            .timeLimit(receipt.getTimeLimit())
            .client(receipt.getClient())
            .debt(receipt.getDebt())
            .percentageInterest(receipt.getPercentageInterest())
            .totalAmount(receipt.getTotalAmount())
            .receiptNumber(receipt.getReceiptNumber())
            .year(receipt.getYear())
            .build();
    }

	@Override
	public List<ReceiptResponse> filterByYear(Integer id, String year) {
		String startDate = "01/01/"+ year;
		LocalDate now = LocalDate.now();
		String endDate;
		if(year.equals(now.getYear())) {
			endDate = now.toString();
		} else {
			endDate = "12/31/"+ year;
		}
		return this.filterBetweenTwoDates(id, startDate, endDate);
		
	}
}


