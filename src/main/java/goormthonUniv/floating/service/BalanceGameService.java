package goormthonUniv.floating.service;

import goormthonUniv.floating.repository.BalanceGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceGameService {
    private final BalanceGameRepository balanceGameRepository;
}
