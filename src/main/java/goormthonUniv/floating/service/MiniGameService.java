package goormthonUniv.floating.service;

import goormthonUniv.floating.repository.MiniGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MiniGameService {

    private final MiniGameRepository miniGameRepository;

}
