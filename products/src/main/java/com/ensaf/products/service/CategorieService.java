package com.ensaf.products.service;

import com.ensaf.products.dto.CategorieDTO;
import com.ensaf.products.exception.ResourceNotFoundException;
import com.ensaf.products.model.Categorie;
import com.ensaf.products.model.Produit;
import com.ensaf.products.repository.CategorieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategorieService {
    private final CategorieRepository categorieRepository;

    @Transactional(readOnly = true)
    //Récuperer tous les catégories
    public List<CategorieDTO> getAll() {
        //Chaque catégorie est transformé suivant la forme definit dans la classe CategorieDTO
        return categorieRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    //Récuperer une categorie par ID
    public CategorieDTO getById(Long id) {
        Categorie categorie = categorieRepository.findById(id)
                //si on ne trouve pas on elève une exception
                .orElseThrow(() -> new ResourceNotFoundException("Categorie non trouvé"));
        return mapToDTO(categorie);
    }


    public CategorieDTO create(CategorieDTO dto) {
        Categorie categorie = new Categorie();
        categorie.setNom(dto.getNom());
        categorie.setDescription(dto.getDescription());
        Categorie saved = categorieRepository.save(categorie);
        return mapToDTO(saved);
    }

    //Modifier les infos d'un categorie
    @Transactional
    public CategorieDTO update(Long id, CategorieDTO dto) {
        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categorie non trouvée"));
        //On modifie les informations de l'objet si trouvé par les nouveaux informations
        categorie.setNom(dto.getNom());
        categorie.setDescription(dto.getDescription());
        //sauvegarde de l'objet modifié
        Categorie updated = categorieRepository.save(categorie);
        return mapToDTO(updated);
    }

    //Supprimer une catégorie
    @Transactional
    public void delete(Long id) {
        if (!categorieRepository.existsById(id)) {
            //si la catergorie n'existe pas -> Exception
            throw new ResourceNotFoundException("Categorie non trouvée");
        }
        //Suppression
        categorieRepository.deleteById(id);
    }

    private CategorieDTO mapToDTO(Categorie categorie) {
        //Créer un objet suivant la format definie pour CategorieDTO
        CategorieDTO dto = new CategorieDTO();
        //remplir cet objet avec les informations de notre object categorie
        dto.setId(categorie.getId());
        dto.setNom(categorie.getNom());
        dto.setDescription(categorie.getDescription());

        // On prend les noms des produits seulement , pas besoins de tout l'objet Produit
        if (categorie.getProduits() != null) {
            dto.setProduitNoms(categorie.getProduits().stream()
                    .map(Produit::getNom)
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}
