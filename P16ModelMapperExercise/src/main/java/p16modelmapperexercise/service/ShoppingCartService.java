package p16modelmapperexercise.service;

import p16modelmapperexercise.service.DTOs.CartItemDTO;

public interface ShoppingCartService {

    String addItem(CartItemDTO item);
    String deleteItem(CartItemDTO item);
    String buyItem();
}
