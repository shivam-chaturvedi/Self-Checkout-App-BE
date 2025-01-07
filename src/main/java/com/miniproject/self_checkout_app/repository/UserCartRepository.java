package com.miniproject.self_checkout_app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.miniproject.self_checkout_app.model.UserCart;
import com.miniproject.self_checkout_app.model.User;
import com.miniproject.self_checkout_app.model.StoreCart;

@Repository
public interface UserCartRepository extends JpaRepository<UserCart, Long> {

    // Fetch UserCart by associated User
    List<UserCart> findByUser(User user);

    // Fetch UserCart by active status
    List<UserCart> findByIsActive(boolean isActive);

    // Fetch UserCart by associated StoreCart
    List<UserCart> findByStoreCart(StoreCart storeCart);

    // Fetch UserCart by User and active status
    List<UserCart> findByUserAndIsActive(User user, boolean isActive);

    // Fetch UserCart by StoreCart and active status
    List<UserCart> findByStoreCartAndIsActive(StoreCart storeCart, boolean isActive);

    // Fetch UserCart by User, StoreCart, and active status
    List<UserCart> findByUserAndStoreCartAndIsActive(User user, StoreCart storeCart, boolean isActive);
}
