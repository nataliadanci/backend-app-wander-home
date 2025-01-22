package demo.transformers;

import demo.dto.PhotoDTO;
import demo.entity.Photo;
import org.springframework.stereotype.Component;

@Component
public class PhotoTransformer implements Transformer<Photo, PhotoDTO>{

    @Override
    public PhotoDTO fromEntity(Photo photo){

        PhotoDTO photoDTO = new PhotoDTO();

        photoDTO.setPhotoId(photo.getPhotoId());
        photoDTO.setUrl(photo.getUrl());

        return photoDTO;
    }

    @Override
    public Photo fromDTO(PhotoDTO photoDTO){

        Photo photoEntity = new Photo();

        photoEntity.setPhotoId(photoDTO.getPhotoId());
        photoEntity.setUrl(photoDTO.getUrl());

        return photoEntity;
    }
}
