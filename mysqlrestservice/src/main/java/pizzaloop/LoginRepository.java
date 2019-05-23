package pizzaloop;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
@Transactional
public interface LoginRepository extends CrudRepository<Login, Integer> {
    List<Login> findByUserid(Integer userid);
    List<Login> deleteByUserid(Integer userid);
}

