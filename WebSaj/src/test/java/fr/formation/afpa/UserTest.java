package fr.formation.afpa;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import fr.formation.afpa.dao.IUserDao;
import fr.formation.afpa.domain.UserProfile;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserTest {
	private static Logger log = Logger.getLogger(UserTest.class);
	
	@Autowired
	private IUserDao dao;

	@Test
    public void should_find_no_tutorials_if_repository_is_empty() {
        log.info("I'm starting");
        System.out.println("+++++++++++++++++++++should_find_no_tutorials_if_repository_is_empty+++++++++++++++++++");
        Iterable<UserProfile> userTest = dao.findAll();

 

        assertThat(userTest).isEmpty();
    }

}
