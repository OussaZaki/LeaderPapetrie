package utils;

public interface ITransformer<E, T> extends ITransformerExn<E, T, RuntimeException> {

    @Override
    public abstract T transformChecked(E input);

}
