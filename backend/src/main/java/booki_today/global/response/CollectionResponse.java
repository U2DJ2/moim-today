package booki_today.global.response;

public record CollectionResponse<T> (
        T data
) {
    public static <T> CollectionResponse<T> of(final T data) {
        return new CollectionResponse<>(data);
    }
}
