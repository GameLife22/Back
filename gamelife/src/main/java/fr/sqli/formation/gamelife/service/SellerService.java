package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.request.SellerGameRequest;
import fr.sqli.formation.gamelife.dto.response.GameResponse;
import fr.sqli.formation.gamelife.entity.GameEntity;
import fr.sqli.formation.gamelife.entity.SellerGameEntity;
import fr.sqli.formation.gamelife.repository.IGameRepository;
import fr.sqli.formation.gamelife.repository.ISellerGameRepository;
import fr.sqli.formation.gamelife.utility.converter.ISellerConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.SequencedCollection;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SellerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SellerService.class);
    @Autowired
    private ISellerGameRepository iSellerGameRepository;
    @Autowired
    private IGameRepository gameRepository;
    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;

    public SellerGameEntity ajouterProduitRevendeur(SellerGameRequest pSellerGameRequest) throws Exception {
        SellerGameEntity SellerGameEntity = new SellerGameEntity();
        if(pSellerGameRequest.getStock() <= 0 ){
            throw new Exception("Stock incorrect");
        }else {
            SellerGameEntity.setStock(pSellerGameRequest.getStock());
        }
        if(pSellerGameRequest.getPrix().compareTo(BigDecimal.ZERO) <= 0 ){
            throw new Exception("Prix incorrect");
        }else {
            SellerGameEntity.setPrix(pSellerGameRequest.getPrix());
        }
        if(pSellerGameRequest.getEtat() == null){
            throw new Exception("Etat incorrect");
        } else{
            SellerGameEntity.setEtat(pSellerGameRequest.getEtat());
        }
        SellerGameEntity.setProduit(ISellerConverter.entityFromDtoOut(gameService.getGameById(pSellerGameRequest.getIdProduit())));
        SellerGameEntity.setUtilisateur(userService.getUtilisateurById(pSellerGameRequest.getIdUtilisateur()));

        return iSellerGameRepository.save(SellerGameEntity);
    }

    public SellerGameEntity modifierProduitRevendeur(UUID pProduitRevendeurID, SellerGameRequest pProduitRequete) {
        Optional<SellerGameEntity> optionalSellerGameEntity = iSellerGameRepository.findById(pProduitRevendeurID);
        if (optionalSellerGameEntity.isEmpty()) {
            LOGGER.error("Produit revendeur non trouvé");
            return null; // or throw an exception
        }

        SellerGameEntity SellerGameEntity = optionalSellerGameEntity.get();
        //<TODO> : check if the stock is not negative, prix is not negative and etat is not null
        SellerGameEntity.setStock(pProduitRequete.getStock());
        SellerGameEntity.setPrix(pProduitRequete.getPrix());

        iSellerGameRepository.save(SellerGameEntity);

        return SellerGameEntity;
    }

    public void supprimerProduitRevendeur(UUID produitRevendeurID) {
        iSellerGameRepository.deleteById(produitRevendeurID);
    }


    public SellerGameEntity recupererProduitRevendeur(UUID produitRevendeurID) {
        Optional<SellerGameEntity> SellerGameEntity = iSellerGameRepository.findById(produitRevendeurID);
        if (SellerGameEntity.isPresent()) {
            return SellerGameEntity.get();
        }
        else {
            LOGGER.error("Produit revendeur non trouvé");
            return null;
        }
    }

    public List<SellerGameEntity> recupererProduitsRevendeurUtilisateur(UUID utilisateurID) throws Exception {
        List<SellerGameEntity> SellerGameEntity = iSellerGameRepository.findAllByUtilisateur(utilisateurID);
        LOGGER.info("Get recupererProduitsRevendeurUtilisateur products: " );
        if (SellerGameEntity != null) {
            return SellerGameEntity;
        }
        else {
            LOGGER.error("Produit revendeur non trouvé");
            return null;
        }
    }
    public List<GameEntity> getAllProduitByRevendeur(UUID revendeurId) throws Exception {


        // Retrieve all products
        List<GameEntity> allProducts = getAllGames();
        LOGGER.info("Get all products: " );
        // Retrieve all products that the reseller already has
        List<GameEntity> revendeurProducts = recupererProduitsRevendeurUtilisateur(revendeurId)
                .stream()
                .map(SellerGameEntity::getProduit)
                .toList();

        // Subtract the list of products that the reseller already has from the list of all products

        return allProducts.stream()
                .filter(product -> revendeurProducts.stream().noneMatch(revendeurProduct -> revendeurProduct.getId().equals(product.getId())))
                .collect(Collectors.toList());
    }

    public List<SellerGameEntity> recupererProduitsRevendeur() {
        return iSellerGameRepository.findAll();
    }

    public List<GameEntity> getAllGames() {
        return gameRepository.findAll();
    }
}
