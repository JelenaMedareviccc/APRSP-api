package com.example.project.scheduledTasks;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.project.models.Receipt;
import com.example.project.repositories.ReceiptRepository;
import com.example.project.services.ReceiptService;

@Component
public class ScheduledTasks {

	private final ReceiptRepository receiptRepository;
	private  JavaMailSender mailSender;
	private final ReceiptService receiptService;
	
	@Autowired
	 public ScheduledTasks(ReceiptRepository receiptRepository, JavaMailSender mailSender, ReceiptService receiptService) {
		this.receiptRepository = receiptRepository;
		this.mailSender = mailSender;
		this.receiptService = receiptService;
	}

	@Scheduled(cron = "0 0 18 * * *")
	public void reportCurrentTime() {
		List<Receipt> receipts = this.receiptRepository.findAll();
		
		LocalDate now = LocalDate.now();
	
		for(Receipt receipt : receipts) {
			LocalDate date = receipt.getDateOfIssue().plusMonths(receipt.getTimeLimit());
			if(receipt.getDebt()>0) {
			if(date.getYear() - now.getYear() == 0 || (date.getYear() - now.getYear() ==1 && date.getMonthValue() == 1 && now.getMonthValue()==12 )) {
				if(date.getMonthValue() -now.getMonthValue() == 1 || date.getMonthValue() -now.getMonthValue() == 2 || date.getMonthValue() - now.getMonthValue() ==-11) {
				if(now.plusDays(30).getDayOfMonth() == date.getDayOfMonth() || now.plusDays(15).getDayOfMonth() == date.getDayOfMonth()) {
					
						String messageText = "Dear " + receipt.getClient().getName() + " This is a friendly reminder that the following invoice " + receipt.getReceiptId() + " have less than 30 days to be paid! Your debt is" + receipt.getDebt()+  receipt.getClient().getCompany().getCurrency()+ ". We would appriciate if you could send us the payment as soos as possible. Your due day is "+ receipt.getDateOfIssue().plusDays(receipt.getTimeLimit())+". If you have already sent the payment, please disregard this letter. Otherwise, please note in case you late with your payment, we need to calculate you payment sum again but now we will include past-due interest. Please feel free to contact us if you need any further information! Thank you, " + receipt.getClient().getCompany().getName();
						String subject ="Notice about your payment";
						this.sendMail(receipt.getClient().getEmail(), messageText, subject);
					
				}
					
					
				}
				
			}
			
			if(date.equals(now)) {
				
				String messageText = "Dear " + receipt.getClient().getName() + " This is a friendly reminder that the following invoice " + receipt.getReceiptId() + "  needs to be paid today " + receipt.getDateOfIssue()+"! Your debt is " + receipt.getDebt() + receipt.getClient().getCompany().getCurrency()+" We would appriciate if you could send us the payment as soos as possible. If you have already sent the payment, please disregard this letter. Otherwise, please note in case you late with your payment, we need to calculate you payment sum again but now we will include past-due interest. Please feel free to contact us if you need any further information! Thank you, " + receipt.getClient().getCompany().getName();
				String subject ="Notice about your payment";
				this.sendMail(receipt.getClient().getEmail(), messageText, subject);
				
				
			}
			
			if(now.isAfter(date)) {
				
				setPercentageInterestToReceipt(receipt.getClient().getEmail(), date, receipt);
				
			}
			}
			
	
		}
		
	}
	 
	 private void sendMail(String email, String messageText, String subject) throws MailException {
		 try {
		 SimpleMailMessage message = new SimpleMailMessage(); 
	        message.setSubject(subject); 
	        message.setText(messageText);
	        mailSender.send(message);
		 } catch (MailException e) {
			e.printStackTrace();
		}
	 }
	 
	 private void setPercentageInterestToReceipt(String email, LocalDate date, Receipt receipt) {
		 LocalDate now = LocalDate.now();
		 int percentage;
		 int numOfLateDays;
		
			
		 if(date.plusDays(7).equals(now)) {
			 percentage =5;
			 numOfLateDays =7;
				receipt.setPercentageInterest(5);
				
			} else if(date.plusDays(30).equals(now)) {
				percentage =15;
				 numOfLateDays =30;	
				receipt.setPercentageInterest(15);
			} else if(date.plusDays(60).equals(now)) {
				percentage =25;
				 numOfLateDays =60;	
				receipt.setPercentageInterest(25);
			} else if(date.plusDays(90).equals(now)) {
				percentage =35;
				 numOfLateDays =90;
				receipt.setPercentageInterest(35);
			} else if(date.plusDays(120).equals(now)) {
				percentage =50;
				 numOfLateDays =120;
				receipt.setPercentageInterest(50);
			} else if(date.plusDays(150).equals(now)) {
				percentage =60;
				 numOfLateDays =150;
				receipt.setPercentageInterest(60);
			} else if(date.plusDays(200).equals(now)) {
				percentage =70;
				 numOfLateDays =200;
				receipt.setPercentageInterest(70);
			} else 	if(date.plusDays(250).equals(now)) {
				percentage =80;
				 numOfLateDays =250;
				receipt.setPercentageInterest(80);
			} else 	if(date.plusDays(300).equals(now)) {
				percentage =90;
				 numOfLateDays =300;
				receipt.setPercentageInterest(90);
			} else {
				percentage =100;
				 numOfLateDays =301;
				receipt.setPercentageInterest(100);
			}
			String messageText = "Dear " + receipt.getClient().getName() + " This is a friendly reminder that the invoice " + receipt.getReceiptId() + " in the amount of " + receipt.getDateOfIssue()+" is currently past due! We would appriciate if you could send us the payment as soos as possible. If you have already sent the payment, please disregard this letter. Otherwise, please note because you are late with your payment, we need to calculate you payment sum again. You are late " + numOfLateDays + " and your past-due interest is now " + percentage +"%, so your currently debt is " + (receipt.getDebt()+ receipt.getDebt()*percentage/100) + receipt.getClient().getCompany().getCurrency()+ " . Please feel free to contact us if you need any further information! Thank you, " + receipt.getClient().getCompany().getName();
		
			String subject ="Notice about your payment";
			this.receiptService.update(receipt);
			this.sendMail(email, messageText, subject);	

		 
		 
	 }

}
