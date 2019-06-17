package com.buymesth.app.repositories;

import com.buymesth.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


   // @Query("SELECT u FROM user u JOIN FETCH u.role JOIN FETCH u.userDetail WHERE u.email=:email")
    Optional<User> findByEmail(@Param("email") String email);

   // @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM user u WHERE u.email = :email ")
    Boolean existsByEmail(@Param("email") String email);


    @Query("select u from user u inner join u.operations o inner join o.status inner join o.type where u.id=:id")
    Optional<User> findByIdWithOperations(Long id);

    Long countByEnabled(Boolean enabled);

    Long countByLocked(Boolean locked);

    @Query(value = "select to_char(date_trunc('month', signup_date), 'Month') as \"Month\", count(*) as \"Total\" " +
            "from user_account where to_char(signup_date,'YYYY') > to_char(date('now') - interval '1 year','YYYY') " +
            "group by date_trunc('month', signup_date) ORDER BY date_trunc('month', signup_date)", nativeQuery = true)
    List<Object[]> countUserRegisteredInCurrentYear();

    @Query(value = "select country, count(*) as total from user_detail group by country", nativeQuery = true)
    List<Object[]> countUserByCountry();

}
