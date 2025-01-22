package demo.transformers;

import demo.dto.FavoriteDTO;
import demo.entity.Favorite;
import org.springframework.stereotype.Component;

@Component
public class FavoriteTransformer implements Transformer<Favorite, FavoriteDTO>{

    @Override
    public FavoriteDTO fromEntity(Favorite favorite){
        //trebuie conditia cu null?

        FavoriteDTO favoriteDTO = new FavoriteDTO();

        favoriteDTO.setFavoriteId(favorite.getFavoriteId());

        return favoriteDTO;
    }

    @Override
    public Favorite fromDTO(FavoriteDTO favoriteDTO){

        Favorite favoriteEntity = new Favorite();

        favoriteEntity.setFavoriteId(favoriteDTO.getFavoriteId());

        return favoriteEntity;
    }
}
