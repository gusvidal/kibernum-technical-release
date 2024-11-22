package SpringSecurityJwt;

import SpringSecurityJwt.library.repository.BookRepository;
import SpringSecurityJwt.library.repository.StudentRepository;
import SpringSecurityJwt.model.Roles;
import SpringSecurityJwt.repository.IRolesRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class SpringSecurityJwtApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(SpringSecurityJwtApplication.class, args);

		IRolesRepository repo = context.getBean(IRolesRepository.class);
		BookRepository bookRepo = context.getBean(BookRepository.class);
		StudentRepository studRepo = context.getBean(StudentRepository.class);

		Roles rol1 = new Roles(1L,"ADMIN");
		Roles rol2 = new Roles(2L,"USER");
		repo.save(rol1);
		repo.save(rol2);

	}

}
