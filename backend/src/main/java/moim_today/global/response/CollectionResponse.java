package moim_today.global.response;

public record CollectionResponse<T> (
        T data
) {
    public static <T> CollectionResponse<T> from(final T data) {
        return new CollectionResponse<>(data);
    }
}
