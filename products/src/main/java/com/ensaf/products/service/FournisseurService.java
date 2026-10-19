package com.ensaf.products.service;

import com.ensaf.products.dto.FournisseurDTO;
import com.ensaf.products.exception.ResourceNotFoundException;
import com.ensaf.products.model.Fournisseur;
import com.ensaf.products.model.Produit;
import com.ensaf.products.repository.FournisseurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FournisseurService {

    private final FournisseurRepository fournisseurRepository;

    // Récupérer tous les fournisseurs
    @Transactional(readOnly = true)
    public List<FournisseurDTO> getAll() {
        return fournisseurRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Récupérer un fournisseur par son ID
    @Transactional(readOnly = true)
    public FournisseurDTO getById(Long id) {
        Fournisseur fournisseur = fournisseurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur non trouvé , id: " + id));
        return mapToDTO(fournisseur);
    }

    // Créer un nouveau fournisseur
    @Transactional
    public FournisseurDTO create(FournisseurDTO dto) {
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setNom(dto.getNom());
        fournisseur.setContact(dto.getContact());
        fournisseur.setAdresse(dto.getAdresse());
        Fournisseur saved = fournisseurRepository.save(fournisseur);
        return mapToDTO(saved);
    }

    // Modifier un fournisseur existant
    @Transactional
    public FournisseurDTO update(Long id, FournisseurDTO dto) {
        Fournisseur fournisseur = fournisseurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur non trouvé , id: " + id));

        fournisseur.setNom(dto.getNom());
        fournisseur.setContact(dto.getContact());
        fournisseur.setAdresse(dto.getAdresse());

        Fournisseur updated = fournisseurRepository.save(fournisseur);
        return mapToDTO(updated);
    }

    // Supprimer un fournisseur
    @Transactional
    public void delete(Long id) {
        if (!fournisseurRepository.existsById(id)) {
            throw new ResourceNotFoundException("Fournisseur non trouvé , id: " + id);
        }
        fournisseurRepository.deleteById(id);
    }

    // Mapper Fournisseur → FournisseurDTO
    private FournisseurDTO mapToDTO(Fournisseur fournisseur) {
        FournisseurDTO dto = new FournisseurDTO();
        dto.setId(fournisseur.getId());
        dto.setNom(fournisseur.getNom());
        dto.setContact(fournisseur.getContact());
        dto.setAdresse(fournisseur.getAdresse());

        // On prend seulement les noms des produits fournis
        if (fournisseur.getProduits() != null) {
            dto.setProduitNoms(fournisseur.getProduits().stream()
                    .map(Produit::getNom)
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}