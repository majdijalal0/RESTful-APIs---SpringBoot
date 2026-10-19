package com.ensaf.products.service;

import com.ensaf.products.dto.TagDTO;
import com.ensaf.products.exception.ResourceNotFoundException;
import com.ensaf.products.model.Tag;
import com.ensaf.products.repository.TagRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    @Transactional(readOnly = true)
    //Retourner la liste de tous les Tags ,après les transformer suivant la format définit dans la classe TagDTO
    public List<TagDTO> getAll() {
        return tagRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    //Retourner un Tag par son Id, si non trouvé on eleve une exception
    @Transactional(readOnly = true)
    public TagDTO getById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag non trouvé , id : " + id));
        return mapToDTO(tag);
    }


    //Créer un nouveau Tag
    @Transactional
    public TagDTO create(TagDTO dto) {
        Tag tag = new Tag();
        tag.setLibelle(dto.getLibelle());
        return mapToDTO(tagRepository.save(tag));
    }

    //Supprimer un Tag
    @Transactional
    public void delete(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tag non trouvé , id: " + id);
        }
        tagRepository.deleteById(id);
    }


    //Mapper Tag -> TagDTO
    private TagDTO mapToDTO(Tag tag) {
        TagDTO dto = new TagDTO();
        dto.setId(tag.getId());
        dto.setLibelle(tag.getLibelle());
        return dto;
    }
}
