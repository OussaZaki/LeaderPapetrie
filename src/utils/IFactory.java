package utils;

public interface IFactory<E> {

    public abstract E createChecked();

}