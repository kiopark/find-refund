package szs.findrefund.domain.income;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IncomeRepository extends JpaRepository<Income, Long> {

  Optional<Income> findByRegNo(String regNo);
}