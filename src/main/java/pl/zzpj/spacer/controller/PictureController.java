package pl.zzpj.spacer.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.zzpj.spacer.dto.PictureDto;
import pl.zzpj.spacer.dto.mapper.PictureMapper;
import pl.zzpj.spacer.exception.PictureException;
import pl.zzpj.spacer.service.interfaces.PictureService;
import pl.zzpj.spacer.util.I18n;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class PictureController {

    private final PictureService pictureService;

    private final PictureMapper pictureMapper;

    @GetMapping("picture/{uuid}")
    public ResponseEntity getPicture(@NotNull @PathVariable("uuid") String uuid) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(pictureMapper.toDto(pictureService.getPicture(uuid)));
        } catch (PictureException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("pictures")
    public ResponseEntity<List<PictureDto>> getAllPictures() {
        return ResponseEntity.status(HttpStatus.OK).body(pictureService.getAll()
                .stream()
                .map(pictureMapper::toDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("pictures/{tag}")
    public ResponseEntity<List<PictureDto>> getPicturesByTag(@NotNull @PathVariable("tag") String tag) {
        return ResponseEntity.status(HttpStatus.OK).body(pictureService.getAllByTag(tag)
                .stream()
                .map(pictureMapper::toDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("pictures/{start}/{end}")
    public ResponseEntity getPicturesByDate(@NotNull @PathVariable("start") String start,
                                            @NotNull @PathVariable("end") String end) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        Date endDate;
        try {
            startDate = sdf.parse(start);
            endDate = sdf.parse(end);
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(I18n.DATE_PARSE_EXCEPTION);
        }
        return ResponseEntity.status(HttpStatus.OK).body(pictureService.getAllBetweenDate(startDate, endDate)
                .stream()
                .map(pictureMapper::toDto)
                .collect(Collectors.toList()));
    }

    @PostMapping("picture/add")
    public ResponseEntity<String> addPicture(@RequestBody PictureDto pictureDto) {
        try {
            pictureService.addPicture(pictureMapper.toEntity(pictureDto));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (PictureException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("picture/{uuid}/tags/add")
    public ResponseEntity<String> addPictureTags(@NotNull @PathVariable("uuid") String uuid, @RequestBody Set<String> tagSetDto) {
        try {
            pictureService.addPictureTags(uuid, tagSetDto);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (PictureException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("picture/{uuid}/tags/delete")
    public ResponseEntity<String> deletePictureTags(@NotNull @PathVariable("uuid") String uuid, @RequestBody Set<String> tagSetDto) {
        try {
            pictureService.deletePictureTags(uuid, tagSetDto);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (PictureException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
