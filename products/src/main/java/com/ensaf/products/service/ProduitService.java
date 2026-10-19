package com.ensaf.products.service;

import com.ensaf.products.dto.ProduitRequestDTO;
import com.ensaf.products.dto.ProduitResponseDTO;
import com.ensaf.products.exception.ResourceNotFoundException;
import com.ensaf.products.model.*;
import com.ensaf.products.repository.*;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProduitService {
    private final ProduitRepository produitRepository;
    private final TagRepository tagRepository;
    private final CategorieRepository categorieRepository;
    private final DetailProduitRepository detailProduitRepository;
    private final FournisseurRepository fournisseurRepository;

    //Retourner tous les produits existants
    public List<ProduitResponseDTO> getAll(){
        return produitRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    //Retourner un produit par son Id
    public ProduitResponseDTO getById(Long id) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé , id: " + id));
        return mapToResponseDTO(produit);
    }

    //Créer un nouveau produit
    public ProduitResponseDTO create(ProduitRequestDTO request) {
        //On créer un nouveau Objet produit
        Produit produit = mapToEntity(request);
        //On enregistre le produit
        Produit saved = produitRepository.save(produit);
        //On transforme suivant la forme de Réponse définit dans la classe ProduitResponseDTO
        return mapToResponseDTO(saved);
    }

    //Modifier les informations d'un produit
    public ProduitResponseDTO update(Long id, ProduitRequestDTO request) {
        //On récupère l'objet produit par son Id s'il existe
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé , id: " + id));

        //On modifie les attributs simples par les nouvelles informations
        produit.setNom(request.getNom());
        produit.setDescription(request.getDescription());
        produit.setPrice(request.getPrix());
        produit.setCodeBar(request.getCodeBar());
        produit.setDateExpiration(request.getDateExpiration());

        //On modifie la catégorie du produit
        if (request.getCategorieId() != null) {
            //La Requete envoie seulement l'id du catégorie , donc on l'utilise pour trouver la catégorie
            Categorie categorie = categorieRepository.findById(request.getCategorieId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Categorie non trouvée  avec  id: " + request.getCategorieId()));
            //On change la catégorie existante par la catégorie trouvée
            produit.setCategorie(categorie);
        }

        // On repete la meme chose avec Fournisseur
        if (request.getFournisseurId() != null) {
            Fournisseur fournisseur = fournisseurRepository.findById(request.getFournisseurId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Fournisseur non trouvé avec  id: " + request.getFournisseurId()));
            produit.setFournisseur(fournisseur);
        }

        //Modifier les détails du produit
        if (request.getDetailProduit() != null) {
            //On récupère les détails du produit actuels
            DetailProduit detail = produit.getDetailProduit();
            if (detail == null) {
                detail = new DetailProduit();
            }
            //On modifie chaque information de l'objet detail par les informations de la requete
            detail.setFabricant(request.getDetailProduit().getFabricant());
            detail.setPaysOrigine(request.getDetailProduit().getPaysOrigine());
            detail.setComposition(request.getDetailProduit().getComposition());
            detail.setDosage(request.getDetailProduit().getDosage());
            detail.setIndications(request.getDetailProduit().getIndications());
            produit.setDetail(detail);
        }

        //Pour les tags on les supprime puis on remplace par les nouveaux tag
        if (request.getTagIds() != null) {
            Set<Tag> oldTags = new HashSet<>(produit.getTags());
            oldTags.forEach(produit::removeTag);

            //Créer la liste  des Tags de la requete
            Set<Tag> newTags = new HashSet<>(tagRepository.findAllById(request.getTagIds()));
            if (newTags.size() != request.getTagIds().size()) {
                throw new ResourceNotFoundException("Tag non trouvé");
            }
            //ajouter les nouveaux tags
            newTags.forEach(produit::addTag);
        }


        //Enregistrer le nouveaux objet produit modifié
        Produit updated = produitRepository.save(produit);
        return mapToResponseDTO(updated);
    }

    //Supprimer un produit
    public void delete(Long id) {
        //Vérifier si le produit avec cet id existe
        if (!produitRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produit non trouvé , id: " + id);
        }
        //Supprimer
        produitRepository.deleteById(id);
    }
    //Mapper responsable de transformer un Objet ProduitResponseDto vers un Objet Produit
    private Produit mapToEntity(ProduitRequestDTO request) {
        //Créer un nouveau objet produit
        Produit produit = new Produit();
        //Copier les attributs simples
        produit.setNom(request.getNom());
        produit.setDescription(request.getDescription());
        produit.setPrice(request.getPrix());
        produit.setCodeBar(request.getCodeBar());
        produit.setDateExpiration(request.getDateExpiration());

        // Trouver la catégorie en utilisant l'id
        if (request.getCategorieId() != null) {
            Categorie categorie = categorieRepository.findById(request.getCategorieId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Categorie non trouvé avec id: " + request.getCategorieId()));
            //Affecter l'objet categorie trouvée à l'objet produit
            produit.setCategorie(categorie);
        }

        //Trovuer le fournisseur en utilisant l'id
        if (request.getFournisseurId() != null) {
            Fournisseur fournisseur = fournisseurRepository.findById(request.getFournisseurId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Fournisseur non trouvé avec id: " + request.getFournisseurId()));
            //Affecter l'objet fournisseur à l'objet produit
            produit.setFournisseur(fournisseur);
        }


        if (request.getDetailProduit() != null) {
            //Créer un nouveau objet DetailProduit
            DetailProduit detail = new DetailProduit();
            //Copier les informations
            detail.setFabricant(request.getDetailProduit().getFabricant());
            detail.setPaysOrigine(request.getDetailProduit().getPaysOrigine());
            detail.setComposition(request.getDetailProduit().getComposition());
            detail.setDosage(request.getDetailProduit().getDosage());
            detail.setIndications(request.getDetailProduit().getIndications());
            //Enregistrer le nouveau objet detail dans produit
            produit.setDetail(detail);
        }

        //Créer un Set des tags et affecter à l'objet produit
        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            Set<Tag> tags = new HashSet<>(tagRepository.findAllById(request.getTagIds()));
            if (tags.size() != request.getTagIds().size()) {
                throw new ResourceNotFoundException("Tag non trouvé");
            }
            tags.forEach(produit::addTag);
        }

        return produit;
    }


    //Mapper responsable de transformer un Objet Porduit vers un Objet ProduitResponseDto
    private ProduitResponseDTO mapToResponseDTO(Produit produit) {
        ProduitResponseDTO dto = new ProduitResponseDTO();

        // Copier les attributs simples
        dto.setId(produit.getId());
        dto.setNom(produit.getNom());
        dto.setDescription(produit.getDescription());
        dto.setPrix(produit.getPrice());
        dto.setCodeBarre(produit.getCodeBar());
        dto.setDateExpiration(produit.getDateExpiration());

        // ProduitResponseDTO on enregistre seulement le nom de la catégorie et pas tout l'objet categorie
        if (produit.getCategorie() != null) {
            dto.setCategorieNom(produit.getCategorie().getNom());
        }

        // ProduitResponseDTO on enregistre seulement le nom du fournisseur et pas tout l'objet fournisseur

        if (produit.getFournisseur() != null) {
            dto.setFournisseurNom(produit.getFournisseur().getNom());
        }

        //Copier chaque information des détails du produit
        if (produit.getDetailProduit() != null) {
            dto.setFabricant(produit.getDetailProduit().getFabricant());
            dto.setPaysOrigine(produit.getDetailProduit().getPaysOrigine());
            dto.setComposition(produit.getDetailProduit().getComposition());
            dto.setDosage(produit.getDetailProduit().getDosage());
            dto.setIndications(produit.getDetailProduit().getIndications());
        }

        // Copier la Set des Tags
        if (produit.getTags() != null && !produit.getTags().isEmpty()) {
            dto.setTags(
                    produit.getTags().stream()
                            .map(tag -> tag.getLibelle())
                            .collect(Collectors.toSet())
            );
        } else {
            dto.setTags(new HashSet<>());
        }

        return dto;
    }


}
