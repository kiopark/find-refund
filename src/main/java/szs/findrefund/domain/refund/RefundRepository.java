package szs.findrefund.domain.refund;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefundRepository extends JpaRepository<Refund, Long> {

//  Optional<Refund> findByUserId(String userId);
}