package hanium.where2go.domain.customer.repository;

import hanium.where2go.domain.customer.entity.Customer;
import hanium.where2go.domain.customer.entity.Transaction;
import hanium.where2go.domain.customer.entity.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("select t from Transaction t where t.customer = :customer ")
    Page<Transaction> findTransaction(@Param("customer") Customer customer, Pageable pageable);

    @Query("select t from Transaction t where t.customer = :customer and t.type = :type")
    Page<Transaction> findTransactionByType(@Param("customer") Customer customer, @Param("type") TransactionType type, Pageable pageable);

    @Query("select t from Transaction t where t.customer = :customer and t.createdAt >= :date")
    Page<Transaction> findTransactionByDate(@Param("customer") Customer customer, @Param("date") LocalDateTime date, Pageable pageable);

    @Query("select t from Transaction t where t.customer = :customer and t.type = :type and t.createdAt >= :date")
    Page<Transaction> findTransactionByTypeAndDate(@Param("customer") Customer customer,@Param("type") TransactionType type, @Param("date") LocalDateTime date, Pageable pageable);
}
