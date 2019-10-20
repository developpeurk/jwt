package org.sid;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.sid.entities.AppRole;
import org.sid.entities.AppUser;
import org.sid.entities.Task;
import org.sid.etities.dao.TaskRepository;
import org.sid.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class JwtSrpingSecApplication implements CommandLineRunner {
    @Autowired
	private TaskRepository taskRepository;
    @Autowired
    private AccountService accountService;
    
	public static void main(String[] args) {
		SpringApplication.run(JwtSrpingSecApplication.class, args);
	}
	
	
	@Bean
	public BCryptPasswordEncoder getBCPE() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... args) throws Exception {
		
		accountService.saveUser(new AppUser(null, "admin", "1234", null));
		accountService.saveUser(new AppUser(null, "user", "1234", null));
		accountService.saveRole(new AppRole(null, "ADMIN"));
		accountService.saveRole(new AppRole(null, "USER"));

		
		accountService.addRoleToUser("admin", "ADMIN");
		accountService.addRoleToUser("admin", "USER");
		accountService.addRoleToUser("user", "USER");

		


		
		Stream.of("T1","T2", "T3").forEach(t->{
			taskRepository.save(new Task(null, t));
		});
		taskRepository.findAll().forEach(t->{
			System.out.println(t.getTaskName());
		});
		
		
		
		
	}

}
