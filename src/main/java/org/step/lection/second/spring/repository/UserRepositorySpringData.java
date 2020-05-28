package org.step.lection.second.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.step.lection.second.spring.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.step.lection.second.spring.model.User.USER_MESSAGES_ENTITY_GRAPH;
import static org.step.lection.second.spring.util.ConstantUtil.UNIVERSAL_QUERY_USERNAME;

@Repository
public interface UserRepositorySpringData extends JpaRepository<User, UUID>, BaseEntityRepository<User> {

    Optional<User> findByUsername(String username);

    @Query("select u from User u")
    Stream<User> findAllCustomMethod();

    @Query(name = "User.findByUsername")
    Optional<User> findByUsernameNamedQuery(String username);

    @Query("select u from User u where u.username like %?1")
    List<User> findByUsernameLike(String username);

    @Query(nativeQuery = true, value = "SELECT * FROM USERS WHERE USERNAME = ?1")
    Optional<User> findByUsernameWithNativeQuery(String username);

    @Query(nativeQuery = true,
            value = "SELECT * FROM USERS WHERE USERNAME = ?1",
            countQuery = "SELECT count(*) FROM USERS WHERE USERNAME = ?1")
    Page<User> findAllByUsernameWithPage(String username, Pageable pageable);

    @Query("select u from User u where u.username=:username")
    Optional<User> findByUsernameWithParam(@Param("username") String username);

    @Query(UNIVERSAL_QUERY_USERNAME)
    Optional<User> findByUsernameWithSpEL(String username);

    @Modifying(clearAutomatically = true)
    @Query("update User u set u.password = ?1 where u.username = ?2")
    int updateUserPassword(String password, String username);

    @EntityGraph(value = USER_MESSAGES_ENTITY_GRAPH, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select u from User u where u.username = ?1")
    Optional<User> findByUsernameWithNamedEntityGraph(String username);

    @EntityGraph(attributePaths = {"messageList", "courseRatingList"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("select u from User u where u.username = ?1")
    Optional<User> fundByUsernameWithEntityGraph(String username);
}
