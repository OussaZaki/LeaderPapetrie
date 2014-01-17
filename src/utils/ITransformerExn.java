package utils;

public interface ITransformerExn<E, T, X extends Exception> {

    public abstract T transformChecked(E input) throws X;

}
