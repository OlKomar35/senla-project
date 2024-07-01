package org.senla.komar.spring.exception;


public class EntityIllegalArgumentException extends IllegalArgumentException {

    public <K> EntityIllegalArgumentException(String simpleName, K id) {
        switch (simpleName) {
            case "Address" -> throw new AddressIllegalArgumentException("Не существует адреса с указанным id=" + id);
            case "Person" -> throw new PersonIllegalArgumentException("Не существует человека с указанным id=" + id);
            default -> throw new IllegalArgumentException("Неизвестный тип сущности: " + simpleName);
        }
    }

}
