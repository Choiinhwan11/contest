package org.example.contest.domain.map.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.contest.domain.map.repository.MapRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MapServiceImpl {

    private MapRepository mapReposiotory ;

}
