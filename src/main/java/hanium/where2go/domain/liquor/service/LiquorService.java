package hanium.where2go.domain.liquor.service;

import hanium.where2go.domain.liquor.dto.LiquorDto;
import hanium.where2go.domain.liquor.entity.Liquor;
import hanium.where2go.domain.liquor.repository.LiquorRepository;
import hanium.where2go.global.response.BaseException;
import hanium.where2go.global.response.ExceptionCode;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LiquorService {

    private final LiquorRepository liquorRepository;

    @Transactional
    public void createLiquorService(LiquorDto liquorDto) {
        Liquor liquor = Liquor.builder()
                .liquorName(liquorDto.getLiquorName())
                .build();

        liquorRepository.save(liquor);
    }

    @Transactional
    public void updateLiquor(LiquorDto liquorDto, Long liquorId) {
        Liquor findLiquor = findLiquorByLiquorId(liquorId);
        findLiquor.changeLiquorName(liquorDto.getLiquorName());
    }

    @Transactional
    public void deleteLiquor(Long liquorId) {
        liquorRepository.delete(findLiquorByLiquorId(liquorId));
    }

    public LiquorDto getLiquorById(Long liquorId) {
        Liquor findLiquor = findLiquorByLiquorId(liquorId);

        return LiquorDto.builder()
                .liquorId(findLiquor.getId())
                .liquorName(findLiquor.getLiquorName())
                .build();
    }

    public List<LiquorDto> getLiquors() {
        return findLiquors().stream().map(
                liquor -> LiquorDto.builder()
                        .liquorId(liquor.getId())
                        .liquorName(liquor.getLiquorName())
                        .build()
        ).collect(Collectors.toList());
    }

    private Liquor findLiquorByLiquorId(Long liquorId) {
        return liquorRepository.findById(liquorId)
                .orElseThrow(() -> new BaseException(ExceptionCode.LIQUOR_NOT_FOUND));
    }

    private List<Liquor> findLiquors() {
        return liquorRepository.findAll();
    }
}
