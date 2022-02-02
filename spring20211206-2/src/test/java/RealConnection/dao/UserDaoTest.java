package RealConnection.dao;

import RealConnection.domain.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/web/WEB-INF/applicationContext.xml")
public class UserDaoTest {



    @Autowired
    private UserDao dao;

   /* @Autowired
    private SimpleDriverDataSource simpleDriverDataSource;*/

    private User user1;
    private User user2;
    private User user3;

    @Before
    public void setup() {


        //given
        this.user1= new User("1","1n","pass1");
        this.user2 =new User("2","2n","pass2");
        this.user3 =new User("3","3n","pass3");

    }

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {

        //when
        dao.allDelete();
        assertThat(dao.getCount(),is(0));


        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount(),is(2));

        //then
        User result1 =dao.getUser(user1.getId());
        assertThat(result1.getName(),is(user1.getName()));
        assertThat(result1.getPassword(),is(user1.getPassword()));
        System.out.println(result1.toString());

        User result2 =dao.getUser(user2.getId());
        assertThat(result2.getName(),is(user2.getName()));
        assertThat(result2.getPassword(),is(user2.getPassword()));
        System.out.println(result2.toString());

    }

    @Test
    public void count() throws SQLException, ClassNotFoundException {
        dao.allDelete();
        assertThat(dao.getCount(),is(0));

        dao.add(user1);
        assertThat(dao.getCount(),is(1));

        dao.add(user2);
        assertThat(dao.getCount(),is(2));
    }

    @Test(expected = EmptyResultDataAccessException.class) // 이방식은 junit 4버전에서 사용하는 테스트기법이다.
    public void getFailure() throws SQLException, ClassNotFoundException {


        dao.allDelete();
        assertThat(dao.getCount(),is(0));
        dao.getUser("없는 id");
        //다음과같이 junit 5 에서는 람다로 사용
//        assertThrows(EmptyResultDataAccessException.class, () -> {  //junit 5 일때 사용하는 테스트 기법
//            dao.getUser("없는 id");
//
//        });



    }

    @After
    public void clear() throws SQLException, ClassNotFoundException {
        dao.allDelete();

    }


}
