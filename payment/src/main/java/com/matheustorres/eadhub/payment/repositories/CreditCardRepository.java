package com.matheustorres.eadhub.payment.repositories;

import java.util.UUID;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.matheustorres.eadhub.payment.domain.models.CreditCard;
import com.matheustorres.eadhub.payment.domain.models.User;

public interface CreditCardRepository extends JpaRepository<CreditCard, UUID>, JpaSpecificationExecutor<CreditCard> {
    Optional<CreditCard> findByUser(User user);
}
