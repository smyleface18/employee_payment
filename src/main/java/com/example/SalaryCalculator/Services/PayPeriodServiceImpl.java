package com.example.SalaryCalculator.Services;

import com.example.SalaryCalculator.entities.PayPeriod;
import com.example.SalaryCalculator.entities.PeriodStatus;
import com.example.SalaryCalculator.repositories.PayPeriodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayPeriodServiceImpl {

    private final PayPeriodRepository repository;


    @Transactional
    public PayPeriod create(PayPeriod payPeriod) {
        log.info("Creating pay period from {} to {}", payPeriod.getStartDate(), payPeriod.getEndDate());
        validateDates(payPeriod);
        if (repository.existsOverlappingPeriod(payPeriod.getStartDate(), payPeriod.getEndDate(), -1L)) {
            throw new BusinessException("Pay period overlaps with an existing period.");
        }
        payPeriod.setStatus(PeriodStatus.OPEN);
        return repository.save(payPeriod);
    }


    @Transactional
    public PayPeriod update(Long id, PayPeriod payPeriod) {
        log.info("Updating pay period id: {}", id);
        PayPeriod existing = findById(id);
        if (existing.getStatus() == PeriodStatus.CLOSED || existing.getStatus() == PeriodStatus.PROCESSED) {
            throw new BusinessException("Cannot update a closed or processed period.");
        }
        validateDates(payPeriod);
        if (repository.existsOverlappingPeriod(payPeriod.getStartDate(), payPeriod.getEndDate(), id)) {
            throw new BusinessException("Pay period overlaps with an existing period.");
        }
        existing.setStartDate(payPeriod.getStartDate());
        existing.setEndDate(payPeriod.getEndDate());
        return repository.save(existing);
    }

    @Transactional(readOnly = true)
    public PayPeriod findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PayPeriod", id));
    }


    @Transactional(readOnly = true)
    public List<PayPeriod> findAll() {
        return repository.findAllByOrderByStartDateDesc();
    }


    @Transactional(readOnly = true)
    public List<PayPeriod> findByStatus(PeriodStatus status) {
        return repository.findByStatus(status);
    }


    @Transactional(readOnly = true)
    public Optional<PayPeriod> findCurrentOpen() {
        return repository.findFirstByStatusOrderByStartDateDesc(PeriodStatus.OPEN);
    }


    @Transactional
    public PayPeriod closePeriod(Long id) {
        log.info("Closing pay period id: {}", id);
        PayPeriod period = findById(id);
        if (period.getStatus() != PeriodStatus.OPEN) {
            throw new BusinessException("Only OPEN periods can be closed.");
        }
        period.setStatus(PeriodStatus.CLOSED);
        return repository.save(period);
    }


    @Transactional
    public void delete(Long id) {
        log.info("Deleting pay period id: {}", id);
        PayPeriod period = findById(id);
        if (period.getStatus() != PeriodStatus.OPEN) {
            throw new BusinessException("Only OPEN periods can be deleted.");
        }
        repository.delete(period);
    }

    // ── Private helpers ──────────────────────────────────────────
    private void validateDates(PayPeriod period) {
        if (period.getEndDate().isBefore(period.getStartDate())) {
            throw new BusinessException("End date cannot be before start date.");
        }
    }
}
