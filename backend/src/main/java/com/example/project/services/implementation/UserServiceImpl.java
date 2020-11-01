package com.example.project.services.implementation;

import com.example.project.dtos.AddAuthuserDto;
import com.example.project.dtos.GetAuthuserDto;
import com.example.project.dtos.responses.LoginUser;
import com.example.project.enums.RoleEnum;
import com.example.project.exception.CustomException;
import com.example.project.exception.EmailAlreadyExistsException;
import com.example.project.exception.EntityDoesNotExist;
import com.example.project.exception.NotFoundException;
import com.example.project.exception.UsernameAlreadyExistsException;
import com.example.project.models.Client;
import com.example.project.models.Receipt;
import com.example.project.models.Role;
import com.example.project.models.Authuser;
import com.example.project.repositories.RoleRepository;
import com.example.project.repositories.AuthuserRepository;
import com.example.project.security.JwtTokenProvider;
import com.example.project.services.UserService;

import io.jsonwebtoken.JwtException;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class is implementation of the UserService class
 */
@Service
public class UserServiceImpl implements UserService {

    private AuthuserRepository authuserRepository;
    private RoleRepository roleRepository;
    private ConversionService conversionService;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;
	private  JavaMailSender mailSender;



    @Autowired
    public UserServiceImpl(AuthuserRepository authuserRepository, RoleRepository roleRepository, ConversionService conversionService,
                           PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager,
                           JavaMailSender mailSender) {
        this.authuserRepository = authuserRepository;
        this.roleRepository = roleRepository;
        this.conversionService = conversionService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.mailSender = mailSender;
    }

    @Override
    public LoginUser signin(String username, String password) {
    	System.out.println("SIGN IN ENTER");
        try {

            if (authuserRepository.findByUsername(username) == null) {
                throw new NotFoundException();
            }else{
            	
                System.out.println("USERNAME RECEIVED: " + username + " PASSWORD RECEIVED: "+ password);
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
                String token = jwtTokenProvider.createToken(username, authuserRepository.findByUsername(username).getRole().getName());
                JSONObject response = new JSONObject();
                response.put("token", token);
                Authuser user = authuserRepository.findByUsername(username);
                LoginUser loguser = new LoginUser(user.getId(), user.getUsername(), response.getAsString("token"));
                return loguser;
            } 

        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


    @Override
    public boolean signup(AddAuthuserDto userDto) {
        System.out.println("METHOD CALLED:");
        try {
        if (!authuserRepository.existsByUsername(userDto.getUsername())) {
        	System.out.println("USLO");
            Authuser user = conversionService.convert(userDto, Authuser.class);
            System.out.println(user);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            System.out.println(user);
            Role role = roleRepository.findByName(RoleEnum.ROLE_USER);
            System.out.println("ROLE NAME " + role.getName());
            user.setRole(roleRepository.findByName(RoleEnum.ROLE_USER));
            System.out.println("USER ROLE: " + user.getRole().getName());
            System.out.println(user.getId() + " " + user.getPassword());
            user.setEnabled(false);
        	try {
        		  authuserRepository.save(user);
            } catch (DataIntegrityViolationException e) {
                if (e.getMostSpecificCause().getClass().getName().equals("org.postgresql.util.PSQLException") && ((SQLException) e.getMostSpecificCause()).getSQLState().equals("23505"))
                	throw new  CustomException(e.getRootCause().toString(), HttpStatus.UNPROCESSABLE_ENTITY);
                throw e;
             
            }

            String token = jwtTokenProvider.createToken(user.getUsername(), user.getRole().getName() );
            
          return this.sendEmail(user.getEmail(), "Account activation", "Please click on this link if you want to activate your account http://localhost:4200/registrationConfirm?token="+token);
            
        } else {
        	System.out.println("USAO U ERROR");
            throw new UsernameAlreadyExistsException(userDto.getUsername());//change to custom
        }
        } catch (AuthenticationException e) {
            throw new CustomException("Registration failed! Please try again!", HttpStatus.UNPROCESSABLE_ENTITY);
        }
      
    }

	@Override
	public GetAuthuserDto findById(Integer id) {
	
		  GetAuthuserDto userDtos = conversionService.convert(authuserRepository.findByid(id), GetAuthuserDto.class);
		 
		    if (userDtos == null) {
	            throw new NotFoundException();
	        }
		    return userDtos;
		   
	}
	
	@Override
    public List<GetAuthuserDto> findAll() {

        List<GetAuthuserDto> userDtos = new ArrayList<>();
        for (Authuser user : authuserRepository.findAll()) {
            userDtos.add(conversionService.convert(user, GetAuthuserDto.class));
        }
        if (CollectionUtils.isEmpty(userDtos)) {
            throw new NotFoundException();
        }
        return userDtos;
    }





    @Override
    public GetAuthuserDto update(GetAuthuserDto userDto) {
        Authuser oldUser = authuserRepository.getOne(userDto.getId());
        Authuser user = conversionService.convert(userDto, Authuser.class);
        user.setPassword(oldUser.getPassword());
        user.setRole(oldUser.getRole());
        if (authuserRepository.existsById(userDto.getId())) {
            return conversionService.convert(authuserRepository.save(user), GetAuthuserDto.class);
        } else {
            throw new NotFoundException(user.getId());
        }
    }



    @Override
    public void delete(Integer id) {
        Authuser user = authuserRepository.getOne(id);
        if (authuserRepository.existsById(id)) {
            authuserRepository.deleteById(id);
        } else {
            throw new NotFoundException(id);
        }
    }
    
    @Override
   public boolean identifyUser(String username) {
    	 Authuser user = authuserRepository.findByUsername(username);
         if (authuserRepository.existsById(user.getId())) {
        	  String token = jwtTokenProvider.createToken(user.getUsername(), user.getRole().getName() );
              
              return this.sendEmail(user.getEmail(), "Changing password", "Please click on this link if you want to change your password http://localhost:4200/confirmResetPassword?token="+token);

         } else {
             throw new NotFoundException();
         	}
    }

    @Transactional
    @Override
    public LoginUser changePassword(String password, String token) {
    	
    
    		
    		try {
    			if(jwtTokenProvider.validateToken(token)) {
    				String username = jwtTokenProvider.getUsername(token);
    				Authuser user = authuserRepository.findByUsername(username);
    				 if ( user == null) {
    		                throw new NotFoundException();
    		            }else {
    		            	
    		            	user.setPassword(passwordEncoder.encode(password));
    		                GetAuthuserDto userDto = conversionService.convert(user, GetAuthuserDto.class);
    		                 authuserRepository.executeUpdate(user.getPassword(), user.getId());
    		                 
    		           	  token = jwtTokenProvider.createToken(user.getUsername(), user.getRole().getName() );

    		            	
    		            	JSONObject response = new JSONObject();
    		                response.put("token", token);
    		        		LoginUser loguser = new LoginUser(user.getId(), user.getUsername(),  response.getAsString("token"));
    		        		return loguser;
    		            }
    		            	
    			
    			}
    			 
    			} catch (JwtException | IllegalArgumentException e) {
    	            throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
    	        }
    		return null;
    		
    		
    		
    	
    }
    	

    @Override
    public GetAuthuserDto changeUserToAdmin(Integer id) {
        if (authuserRepository.existsById(id)) {
            Authuser user = authuserRepository.getOne(id);
            System.out.print(user.getId());
            if (user.getRole().getName().equals(RoleEnum.ROLE_USER)) {
                user.setRole(roleRepository.findByName(RoleEnum.ROLE_ADMIN));
            }
            return conversionService.convert(authuserRepository.save(user), GetAuthuserDto.class);
        } else {
            throw new NotFoundException();
        }
    }
    
    @Override
    public String refresh(String username) {
        return jwtTokenProvider.createToken(username, authuserRepository.findByUsername(username).getRole().getName());
    }

	@Override
	public LoginUser comfirmRegistration(String token) {
		try {
		if(jwtTokenProvider.validateToken(token)) {
			String username = jwtTokenProvider.getUsername(token);
			Authuser user = authuserRepository.findByUsername(username);
			 if ( user == null) {
	                throw new NotFoundException();
	            }else {
	            	user.setEnabled(true);
	            	System.out.println(user);
	            	conversionService.convert(authuserRepository.save(user), GetAuthuserDto.class);
	            	JSONObject response = new JSONObject();
	                response.put("token", token);
	                System.out.println(response);
	        		LoginUser loguser = new LoginUser(user.getId(), user.getUsername(),  response.getAsString("token"));
	        		return loguser;
	            }
	            	
		
		}
		 
		} catch (JwtException | IllegalArgumentException e) {
            throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
		return null;
     
	}
	
	private boolean sendEmail(String to, String subject, String text) {
		  try {
				SimpleMailMessage message = new SimpleMailMessage(); 
		    
		        message.setTo(to); 
		        message.setSubject(subject); 
		        message.setText(text);
		        mailSender.send(message);
	       		 
     	        return true;
     		 } catch (MailException e) {
     			e.printStackTrace();
     		}
		  return false;
	
		
	}

	
}
