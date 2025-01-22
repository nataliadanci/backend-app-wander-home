package demo.transformers;

public interface DTOTransformer<FromDTO,ToDTO> {
    ToDTO transform(FromDTO fromDTO);
}
