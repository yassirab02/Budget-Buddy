package com.yassir.budgetbuddy.debt.service;

import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.debt.Debt;
import com.yassir.budgetbuddy.debt.repository.DebtRepository;
import com.yassir.budgetbuddy.debt.repository.DebtSpecification;
import com.yassir.budgetbuddy.debt.controller.DebtMapper;
import com.yassir.budgetbuddy.debt.controller.DebtRequest;
import com.yassir.budgetbuddy.debt.controller.DebtResponse;
import com.yassir.budgetbuddy.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DebtServiceImpl implements DebtService{

    private final DebtMapper debtMapper;
    private final DebtRepository repository;

    @Override
    public Integer addOrUpdateDebt(DebtRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Debt debt = debtMapper.toDebt(request);
        debt.setOwner(user);
        return repository.save(debt).getId();
    }

    @Override
    public PageResponse<DebtResponse> findAllDebtsByOwner(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<Debt> debts = repository.findAll(DebtSpecification.withOwnerId(user.getId()), pageable);
        List<DebtResponse> debtResponse = debts.stream()
                .map(debtMapper::toDebtResponse)
                .toList();
        return new PageResponse<>(
                debtResponse,
                debts.getNumber(),
                debts.getSize(),
                debts.getTotalElements(),
                debts.getTotalPages(),
                debts.isFirst(),
                debts.isLast()
        );
    }

    @Override
    public PageResponse<DebtResponse> findDebtsByOwnerAndPaidStatus(int page, int size, Authentication connectedUser, boolean paidStatus) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Debt> debts = repository.findDebtsByOwnerAndPaidStatus(user.getId(),paidStatus,pageable );
        List<DebtResponse> debtResponse = debts.stream()
                .map(debtMapper::toDebtResponse)
                .toList();
        return new PageResponse<>(
                debtResponse,
                debts.getNumber(),
                debts.getSize(),
                debts.getTotalElements(),
                debts.getTotalPages(),
                debts.isFirst(),
                debts.isLast()
        );
    }

    @Override
    public void deleteDebt(Integer debtId, Authentication connectedUser) {
        Debt debt = repository.findById(debtId)
                .orElseThrow(() -> new EntityNotFoundException("No Debt found with the Id : " + debtId));
        User user = ((User) connectedUser.getPrincipal());
        if (!debt.getOwner().getId().equals(user.getId())) {
            throw new IllegalArgumentException("You are not allowed to delete this Budget");
        }
        repository.delete(debt);
    }

    @Override
    public DebtResponse findDebtById(Integer debtId) {
        Debt debt = repository.findById(debtId)
                .orElseThrow(() -> new EntityNotFoundException("No Debt found with the Id : " + debtId));
        return debtMapper.toDebtResponse(debt);
    }

    @Override
    public BigDecimal getTotalAmountDebtByUser(Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        List<Debt> debts =  repository.findDebtsByOwnerId(user.getId());
        return debts.stream()
                .map(Debt::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
